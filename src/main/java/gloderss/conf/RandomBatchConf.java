package gloderss.conf;

import gloderss.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

@XmlRootElement(name = Constants.TAG_RANDOM_BATCH)
public class RandomBatchConf {
  
  private String                      randomBatchOutputDir;
  
  private String                      randomBatchOutputFile;
  
  private Integer                     randomBatchReplica;
  
  private String                      randomBatchXMLScenario;
  
  private String                      randomBatchXSDScenario;
  
  private List<RandomBatchChangeConf> randomBatchChanges = new ArrayList<RandomBatchChangeConf>();
  
  
  /**
   * Process XML random batch configuration file
   * 
   * @param xmlFilename
   *          XML configuration file
   * @param xsdFilename
   *          XSD configuration filename
   * @return Random Batch configuration
   */
  public static RandomBatchConf getRandomBatchConf(String xmlFilename,
      String xsdFilename) {
    
    RandomBatchConf randomBatchConf = new RandomBatchConf();
    
    File xmlFile = new File(xmlFilename);
    File xsdFile = new File(xsdFilename);
    
    if((xmlFile.exists()) && (xsdFile.exists())) {
      
      if(isValid(xmlFilename, xsdFilename)) {
        
        try {
          JAXBContext jaxbContext = JAXBContext
              .newInstance(RandomBatchConf.class);
          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
          
          randomBatchConf = (RandomBatchConf) jaxbUnmarshaller
              .unmarshal(xmlFile);
          
        } catch(JAXBException e) {
          e.printStackTrace();
        }
        
      }
    }
    
    return randomBatchConf;
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
  public static boolean isValid(String xmlFilename, String xsdFilename) {
    boolean valid = false;
    
    try {
      SchemaFactory factory = SchemaFactory
          .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = factory.newSchema(new StreamSource(xsdFilename));
      
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFilename));
      
      valid = true;
      
    } catch(SAXException e) {
    } catch(Exception e) {
    }
    
    return valid;
  }
  
  
  public String getRandomBatchOutputDir() {
    return this.randomBatchOutputDir;
  }
  
  
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_OUTPUT_DIR)
  public void setRandomBatchOutputDir(String randomBatchOutputDir) {
    this.randomBatchOutputDir = randomBatchOutputDir;
  }
  
  
  public String getRandomBatchOutputFile() {
    return this.randomBatchOutputFile;
  }
  
  
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_OUTPUT_FILE)
  public void setRandomBatchOutputFile(String randomBatchOutputFile) {
    this.randomBatchOutputFile = randomBatchOutputFile;
  }
  
  
  public Integer getRandomBatchReplica() {
    return this.randomBatchReplica;
  }
  
  
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_REPLICA)
  public void setRandomBatchReplica(Integer randomBatchReplica) {
    this.randomBatchReplica = randomBatchReplica;
  }
  
  
  public String getRandomBatchXMLScenario() {
    return this.randomBatchXMLScenario;
  }
  
  
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_XML_SCENARIO)
  public void setRandomBatchXMLScenario(String randomBatchXMLScenario) {
    this.randomBatchXMLScenario = randomBatchXMLScenario;
  }
  
  
  public String getRandomBatchXSDScenario() {
    return this.randomBatchXSDScenario;
  }
  
  
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_XSD_SCENARIO)
  public void setRandomBatchXSDScenario(String randomBatchXSDScenario) {
    this.randomBatchXSDScenario = randomBatchXSDScenario;
  }
  
  
  public List<RandomBatchChangeConf> getRandomBatchChanges() {
    return this.randomBatchChanges;
  }
  
  
  @XmlElementWrapper(name = Constants.TAG_RANDOM_BATCH_CHANGES)
  @XmlElement(name = Constants.TAG_RANDOM_BATCH_CHANGE)
  public void
      setRandomBatchChanges(List<RandomBatchChangeConf> randomBatchChanges) {
    this.randomBatchChanges = randomBatchChanges;
  }
}