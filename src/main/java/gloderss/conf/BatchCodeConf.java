package gloderss.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import gloderss.Constants;

@XmlRootElement (
    name = Constants.TAG_BATCH_CONTENT,
    namespace = Constants.TAG_NAMESPACE )
public class BatchCodeConf {
  
  private String code;
  
  private String content;
  
  
  public String getCode() {
    return this.code;
  }
  
  
  @XmlAttribute ( name = Constants.TAG_BATCH_CODE )
  public void setCode( String code ) {
    this.code = code;
  }
  
  
  public String getContent() {
    return this.content;
  }
  
  
  @XmlValue
  public void setContent( String content ) {
    this.content = content;
  }
}