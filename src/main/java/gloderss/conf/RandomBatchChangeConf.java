package gloderss.conf;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import gloderss.Constants;

@XmlRootElement (
    name = Constants.TAG_RANDOM_BATCH_CHANGE,
    namespace = Constants.TAG_NAMESPACE )
public class RandomBatchChangeConf {
  
  private String name;
  
  private String parameter;
  
  private String type;
  
  private String value;
  
  
  public String getName() {
    return this.name;
  }
  
  
  @XmlElement ( name = Constants.TAG_RANDOM_BATCH_NAME )
  public void setName( String name ) {
    this.name = name;
  }
  
  
  public String getParameter() {
    return this.parameter;
  }
  
  
  @XmlElement ( name = Constants.TAG_RANDOM_BATCH_PARAMETER )
  public void setParameter( String parameter ) {
    this.parameter = parameter;
  }
  
  
  public String getType() {
    return this.type;
  }
  
  
  @XmlElement ( name = Constants.TAG_RANDOM_BATCH_TYPE )
  public void setType( String type ) {
    this.type = type;
  }
  
  
  public String getValue() {
    return this.value;
  }
  
  
  @XmlElement ( name = Constants.TAG_RANDOM_BATCH_VALUE )
  public void setValue( String value ) {
    this.value = value;
  }
  
  
  public String toString() {
    String str;
    
    str = "[Parameter = " + this.parameter + "] [Type = " + this.type
        + "] [Value = " + this.value + "]";
    
    return str;
  }
}