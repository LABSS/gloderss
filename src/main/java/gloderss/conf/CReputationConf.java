package gloderss.conf;

import javax.xml.bind.annotation.XmlElement;
import gloderss.Constants;

public class CReputationConf {
  
  private double entrepreneurRep;
  
  private double entrepreneurRepThreshold;
  
  
  public double getEntrepreneurRep() {
    return this.entrepreneurRep;
  }
  
  
  @XmlElement ( name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR )
  public void setEntrepreneurRep( double entrepreneurRep ) {
    this.entrepreneurRep = entrepreneurRep;
  }
  
  
  public double getEntrepreneurRepThreshold() {
    return this.entrepreneurRepThreshold;
  }
  
  
  @XmlElement (
      name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_THRESHOLD )
  public void setEntrepreneurRepThreshold( double entrepreneurRepThreshold ) {
    this.entrepreneurRepThreshold = entrepreneurRepThreshold;
  }
}