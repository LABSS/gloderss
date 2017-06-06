package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_NORMATIVE_NORM_SALIENCE)
public class NormSalienceConf {
  
  // Norm identification
  private int     id;
  
  private boolean active;
  
  private int     compliance;
  
  private int     violation;
  
  private int     obsCompliance;
  
  private int     obsViolation;
  
  private int     punishment;
  
  private int     sanction;
  
  private int     invocationCompliance;
  
  private int     invocationViolation;
  
  
  public int getId() {
    return this.id;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_ID)
  public void setId(int id) {
    this.id = id;
  }
  
  
  public boolean getActive() {
    return this.active;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_ACTIVE)
  public void setActive(boolean active) {
    this.active = active;
  }
  
  
  public int getCompliance() {
    return this.compliance;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_COMPLIANCE)
  public void setCompliance(int compliance) {
    this.compliance = compliance;
  }
  
  
  public int getViolation() {
    return this.violation;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_VIOLATION)
  public void setViolation(int violation) {
    this.violation = violation;
  }
  
  
  public int getObsCompliance() {
    return this.obsCompliance;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_OBS_COMPLIANCE)
  public void setObsCompliance(int obsCompliance) {
    this.obsCompliance = obsCompliance;
  }
  
  
  public int getObsViolation() {
    return this.obsViolation;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_OBS_VIOLATION)
  public void setObsViolation(int obsViolation) {
    this.obsViolation = obsViolation;
  }
  
  
  public int getPunishment() {
    return this.punishment;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_PUNISHMENT)
  public void setPunishment(int punishment) {
    this.punishment = punishment;
  }
  
  
  public int getSanction() {
    return this.sanction;
  }
  
  
  @XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_SANCTION)
  public void setSanction(int sanction) {
    this.sanction = sanction;
  }
  
  
  public int getInvocationCompliance() {
    return this.invocationCompliance;
  }
  
  
  @XmlAttribute(
      name = Constants.TAG_NORMATIVE_NORM_INITIAL_INVOCATION_COMPLIANCE)
  public void setInvocationCompliance(int invocationCompliance) {
    this.invocationCompliance = invocationCompliance;
  }
  
  
  public int getInvocationViolation() {
    return this.invocationViolation;
  }
  
  
  @XmlAttribute(
      name = Constants.TAG_NORMATIVE_NORM_INITIAL_INVOCATION_VIOLATION)
  public void setInvocationViolation(int invocationViolation) {
    this.invocationViolation = invocationViolation;
  }
}