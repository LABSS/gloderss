package gloderss.conf;

import javax.xml.bind.annotation.XmlElement;
import gloderss.Constants;

public class EReputationConf {
  
  private double stateProtector;
  
  private double statePunisher;
  
  private double mafiaPunisher;
  
  
  public double getStateProtector() {
    return this.stateProtector;
  }
  
  
  @XmlElement ( name = Constants.TAG_ENTREPRENEUR_REPUTATION_STATE_PROTECTOR )
  public void setStateProtector( double stateProtector ) {
    this.stateProtector = stateProtector;
  }
  
  
  public double getStatePunisher() {
    return this.statePunisher;
  }
  
  
  @XmlElement ( name = Constants.TAG_ENTREPRENEUR_REPUTATION_STATE_PUNISHER )
  public void setStatePunisher( double statePunisher ) {
    this.statePunisher = statePunisher;
  }
  
  
  public double getMafiaPunisher() {
    return this.mafiaPunisher;
  }
  
  
  @XmlElement ( name = Constants.TAG_ENTREPRENEUR_REPUTATION_MAFIA_PUNISHER )
  public void setMafiaPunisher( double mafiaPunisher ) {
    this.mafiaPunisher = mafiaPunisher;
  }
}