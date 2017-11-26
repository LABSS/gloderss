package gloderss.conf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import gloderss.Constants;

@XmlRootElement (
    name = Constants.TAG_GENERAL,
    namespace = Constants.TAG_NAMESPACE )
public class GeneralConf {
  
  private int           numReplications;
  
  private double        numCycles;
  
  private String        networkTopology;
  
  private String        citizensDistribution;
  
  private List<Integer> seedsConf = new ArrayList<Integer>();
  
  private OutputConf    outputConf;
  
  private FilenameConf  filenameConf;
  
  
  public int getNumberReplications() {
    return this.numReplications;
  }
  
  
  @XmlElement ( name = Constants.TAG_NUMBER_REPLICATIONS )
  public void setNumberReplications( int numReplications ) {
    this.numReplications = numReplications;
  }
  
  
  public double getNumberCycles() {
    return this.numCycles;
  }
  
  
  @XmlElement ( name = Constants.TAG_NUMBER_CYCLES )
  public void setNumberCycles( double numCycles ) {
    this.numCycles = numCycles;
  }
  
  
  public String getNetworkTopology() {
    return this.networkTopology;
  }
  
  
  @XmlElement ( name = Constants.TAG_NETWORK_TOPOLOGY )
  public void setNetworkTopology( String networkTopology ) {
    this.networkTopology = networkTopology;
  }
  
  
  public String getCitizensDistribution() {
    return this.citizensDistribution;
  }
  
  
  @XmlElement ( name = Constants.TAG_CITIZENS_DISTRIBUTION )
  public void setCitizensDistribution( String citizensDistribution ) {
    this.citizensDistribution = citizensDistribution;
  }
  
  
  public List<Integer> getSeedsConf() {
    return this.seedsConf;
  }
  
  
  @XmlElementWrapper ( name = Constants.TAG_SEEDS )
  @XmlElement ( name = Constants.TAG_SEED )
  public void setSeedsConf( List<Integer> seedsConf ) {
    this.seedsConf = seedsConf;
  }
  
  
  public OutputConf getOutputConf() {
    return this.outputConf;
  }
  
  
  @XmlElement ( name = Constants.TAG_OUTPUT )
  public void setOutputConf( OutputConf outputConf ) {
    this.outputConf = outputConf;
  }
  
  
  public FilenameConf getFilenameConf() {
    return this.filenameConf;
  }
  
  
  @XmlElement ( name = Constants.TAG_FILENAME )
  public void setFilenameConf( FilenameConf filenameConf ) {
    this.filenameConf = filenameConf;
  }
}