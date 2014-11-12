package gloderss.conf;

import gloderss.Constants;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_GENERAL, namespace = Constants.TAG_NAMESPACE)
public class GeneralConf {
	
	private OutputConf		outputConf;
	
	private int						numReplications;
	
	private List<Integer>	seedsConf	= new ArrayList<Integer>();
	
	private double				numCycles;
	
	
	public OutputConf getOutputConf() {
		return this.outputConf;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT)
	public void setOutputConf(OutputConf outputConf) {
		this.outputConf = outputConf;
	}
	
	
	public int getNumberReplications() {
		return this.numReplications;
	}
	
	
	@XmlElement(name = Constants.TAG_NUMBER_REPLICATIONS)
	public void setNumberReplications(int numReplications) {
		this.numReplications = numReplications;
	}
	
	
	public List<Integer> getSeedsConf() {
		return this.seedsConf;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_SEEDS)
	@XmlElement(name = Constants.TAG_SEED)
	public void setSeedsConf(List<Integer> seedsConf) {
		this.seedsConf = seedsConf;
	}
	
	
	public double getNumberCycles() {
		return this.numCycles;
	}
	
	
	@XmlElement(name = Constants.TAG_NUMBER_CYCLES)
	public void setNumberCycles(double numCycles) {
		this.numCycles = numCycles;
	}
}