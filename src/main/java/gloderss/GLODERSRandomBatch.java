package gloderss;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import gloderss.conf.RandomBatchChangeConf;
import gloderss.conf.RandomBatchConf;
import gloderss.main.RunSimulation;
import gloderss.util.random.RandomUtil;

public class GLODERSRandomBatch {
  
  private final static Logger logger = LoggerFactory
      .getLogger( GLODERSRandomBatch.class );
  
  
  public static void main( String[] args )
      throws SAXException, IOException, ParserConfigurationException,
      XPathExpressionException, TransformerException {
    
    if ( args.length < 1 ) {
      System.out.println(
          "Syntax: GLODERSRandomBatch [XML Random Batch Filename] [XSD Random Batch Filename]" );
      System.exit( 1 );
    }
    
    String xmlFilename = args[0];
    String xsdFilename = args[1];
    
    boolean valid = RandomBatchConf.isValid( xmlFilename, xsdFilename );
    logger.debug( "[XML VALID] " + valid );
    
    if ( !valid ) {
      System.out.println( "INVALID XML FILE" );
      System.exit( 1 );
    }
    
    RandomBatchConf conf = RandomBatchConf.getRandomBatchConf( xmlFilename,
        xsdFilename );
    
    // Create a general configuration output file
    List<String> configurations = new ArrayList<String>();
    String line = "replica";
    for ( RandomBatchChangeConf change : conf.getRandomBatchChanges() ) {
      line += ";" + change.getName();
    }
    configurations.add( line );
    
    // Original scenario configuration file
    String scenarioFile = (new File( conf.getRandomBatchXMLScenario() ))
        .getName();
    
    XPathExpression expr;
    Node node;
    double min;
    double max;
    double value = 0;
    String strValue;
    String newScenarioFile;
    String[] values;
    String[] mValues;
    String[] sValues;
    NumberFormat decimalFormatter = new DecimalFormat( "#0.00" );
    NumberFormat integerFormatter = new DecimalFormat( "#0" );
    for ( int i = 0; i < conf.getRandomBatchReplica(); i++ ) {
      System.out.println( "Replica: " + i );
      
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse( conf.getRandomBatchXMLScenario() );
      
      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      
      line = String.valueOf( i );
      
      for ( RandomBatchChangeConf change : conf.getRandomBatchChanges() ) {
        
        expr = xpath.compile( change.getParameter() );
        
        strValue = "";
        switch ( change.getType() ) {
          case "FIXED":
            values = change.getValue().split( "," );
            int numValues = values.length;
            if ( numValues > 0 ) {
              strValue = values[RandomUtil.nextIntFromTo( 0, numValues - 1 )];
            } else {
              strValue = "0";
            }
            break;
          case "CONSTANT-RANDOM":
            values = change.getValue().split( "," );
            if ( values.length == 2 ) {
              min = Integer.parseInt( values[0] );
              max = Integer.parseInt( values[1] );
              
              value = RandomUtil.nextIntFromTo( (int) min, (int) max );
              
              strValue = "CONSTANT(" + integerFormatter.format( value ) + ")";
            }
            break;
          case "UNIFORM":
            values = change.getValue().split( "," );
            if ( values.length == 2 ) {
              min = Double.valueOf( values[0] );
              max = Double.valueOf( values[1] );
              
              value = RandomUtil.nextDoubleFromTo( min, max );
              
              strValue = decimalFormatter.format( value );
            }
            break;
          case "NORMAL-RANDOM":
            values = change.getValue().split( "!" );
            if ( values.length >= 2 ) {
              mValues = values[0].split( "," );
              min = Integer.parseInt( mValues[0] );
              max = Integer.parseInt( mValues[1] );
              value = RandomUtil.nextIntFromTo( (int) min, (int) max );
              
              strValue = "NORMAL(" + integerFormatter.format( value ) + ",";
              
              sValues = values[1].split( "," );
              min = Integer.parseInt( sValues[0] );
              max = Integer.parseInt( sValues[1] );
              value = RandomUtil.nextIntFromTo( (int) min, (int) max );
              
              strValue += integerFormatter.format( value ) + ")";
            }
            break;
        }
        
        node = (Node) expr.evaluate( doc, XPathConstants.NODE );
        
        node.setTextContent( strValue );
        line += ";" + strValue;
      }
      configurations.add( line );
      
      // Update the Scenario's output directory
      String newOutputDir = conf.getRandomBatchOutputDir() + File.separator
          + String.valueOf( i );
      (new File( newOutputDir )).mkdirs();
      
      expr = xpath.compile( "/scenario/general/output/directory" );
      node = (Node) expr.evaluate( doc, XPathConstants.NODE );
      node.setTextContent( newOutputDir );
      
      // Generate the new configuration file
      newScenarioFile = newOutputDir + File.separator + scenarioFile;
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource( doc );
      StreamResult result = new StreamResult( new File( newScenarioFile ) );
      transformer.transform( source, result );
      
      // Run the simulation
      long startTime = System.currentTimeMillis();
      logger.debug( "[START_TIME] " + startTime );
      RunSimulation gloderss = new RunSimulation( newScenarioFile,
          conf.getRandomBatchXSDScenario() );
      gloderss.run();
      
      long endTime = System.currentTimeMillis();
      logger.debug( "[END_TIME] " + endTime );
      
      logger.debug( "[ELAPSED] " + (endTime - startTime) );
    }
    
    Path path = Paths.get( conf.getRandomBatchOutputDir() + File.separator
        + conf.getRandomBatchOutputFile() );
    Files.write( path, configurations, Charset.forName( "UTF-8" ) );
  }
  
  
  /**
   * Check the validity of the XML file against an XSD file
   * 
   * @param xmlFilename
   *          XML filename
   * @param xsdFilename
   *          XSD filename
   * @return True if valid, False otherwise
   */
  public static boolean isValid( String xmlFilename, String xsdFilename ) {
    boolean valid = false;
    
    try {
      SchemaFactory factory = SchemaFactory
          .newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
      Schema schema = factory.newSchema( new StreamSource( xsdFilename ) );
      
      Validator validator = schema.newValidator();
      validator.validate( new StreamSource( xmlFilename ) );
      
      valid = true;
      
    } catch ( SAXException e ) {
    } catch ( Exception e ) {
    }
    
    return valid;
  }
}