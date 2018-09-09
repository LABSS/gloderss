package gloderss.conf;

import javax.xml.bind.annotation.XmlElement;
import gloderss.Constants;

public class FilenameConf {
  
  private String extortion;
  
  private String compensation;
  
  private String purchase;
  
  private String normative;
  
  private String entrepreneur;
  
  private String consumer;
  
  private String mafia;
  
  private String mafiosi;
  
  private String state;
  
  private String investigation;
  
  private String intermediaryOrganization;
  
  
  public String getExtortion() {
    return this.extortion;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_EXTORTION )
  public void setExtortion( String extortion ) {
    this.extortion = extortion;
  }
  
  
  public String getCompensation() {
    return this.compensation;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_COMPENSATION )
  public void setCompensation( String compensation ) {
    this.compensation = compensation;
  }
  
  
  public String getPurchase() {
    return this.purchase;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_PURCHASE )
  public void setPurchase( String purchase ) {
    this.purchase = purchase;
  }
  
  
  public String getNormative() {
    return this.normative;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_NORMATIVE )
  public void setNormative( String normative ) {
    this.normative = normative;
  }
  
  
  public String getEntrepreneur() {
    return this.entrepreneur;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_ENTREPRENEUR )
  public void setEntrepreneur( String entrepreneur ) {
    this.entrepreneur = entrepreneur;
  }
  
  
  public String getConsumer() {
    return this.consumer;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_CONSUMER )
  public void setConsumer( String consumer ) {
    this.consumer = consumer;
  }
  
  
  public String getMafia() {
    return this.mafia;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_MAFIA )
  public void setMafia( String mafia ) {
    this.mafia = mafia;
  }
  
  
  public String getMafiosi() {
    return this.mafiosi;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_MAFIOSI )
  public void setMafiosi( String mafiosi ) {
    this.mafiosi = mafiosi;
  }
  
  
  public String getState() {
    return this.state;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_STATE )
  public void setState( String state ) {
    this.state = state;
  }
  
  
  public String getInvestigation() {
    return this.investigation;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_INVESTIGATION )
  public void setInvestigation( String investigation ) {
    this.investigation = investigation;
  }
  
  
  public String getIntermediaryOrganization() {
    return this.intermediaryOrganization;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME_INTERMEDIARY_ORGANIZATION )
  public void setIntermediaryOrganization( String intermediaryOrganization ) {
    this.intermediaryOrganization = intermediaryOrganization;
  }
}