package gloderss.conf;

import static org.junit.Assert.*;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ScenarioTest {
  
  @Test
  public void scenarioTest() {
    boolean valid = false;
    
    try {
      SchemaFactory factory = SchemaFactory
          .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      
      File file = new File("./src/main/resources/conf/scenario.xsd");
      System.out.println(file.exists());
      
      file = new File("/data/workspace/gloders/gloderss/output/85/scenario.xml");
      System.out.println(file.exists());
      
      Schema schema = factory.newSchema(
          new StreamSource("./src/main/resources/conf/scenario.xsd"));
      
      Validator validator = schema.newValidator();
      validator
          .validate(new StreamSource("/data/workspace/gloders/gloderss/output/85/scenario.xml"));
      
      valid = true;
      
    } catch(SAXException e) {
    	System.out.println(e.getMessage());
    } catch(Exception e) {
    	System.out.println(e.getMessage());
    }
    
    System.out.println(valid);
    assertTrue(valid);
  }
  
}