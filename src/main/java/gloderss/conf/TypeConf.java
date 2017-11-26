package gloderss.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import gloderss.Constants;

@XmlRootElement (
    name = Constants.TAG_COMMUNICATION_TYPE,
    namespace = Constants.TAG_NAMESPACE )
public class TypeConf {
  
  private String name;
  
  private double probability;
  
  
  public String getName() {
    return this.name;
  }
  
  
  @XmlAttribute ( name = Constants.TAG_COMMUNICATION_TYPE_NAME )
  public void setName( String name ) {
    this.name = name;
  }
  
  
  public double getProbability() {
    return this.probability;
  }
  
  
  @XmlValue
  public void setProbability( double probability ) {
    this.probability = probability;
  }
}