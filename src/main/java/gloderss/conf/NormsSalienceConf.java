package gloderss.conf;

import gloderss.Constants;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_NORMATIVE_NORMS_SALIENCE)
public class NormsSalienceConf {
  
  private List<NormSalienceConf> normsSalience;
  
  
  public List<NormSalienceConf> getNormsSalience() {
    return this.normsSalience;
  }
  
  
  @XmlElement(name = Constants.TAG_NORMATIVE_NORM_SALIENCE)
  public void setNormsSalience(List<NormSalienceConf> normsSalience) {
    this.normsSalience = normsSalience;
  }
}