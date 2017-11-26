package gloderss.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import gloderss.Constants;

@XmlRootElement (
    name = Constants.TAG_COMMUNICATION_ACTION,
    namespace = Constants.TAG_NAMESPACE )
public class ActionConf {
  
  private String name;
  
  private String type;
  
  
  public String getName() {
    return this.name;
  }
  
  
  @XmlAttribute ( name = Constants.TAG_COMMUNICATION_ACTION_NAME )
  public void setName( String name ) {
    this.name = name;
  }
  
  
  public String getType() {
    return this.type;
  }
  
  
  @XmlValue
  public void setType( String type ) {
    this.type = type;
  }
}