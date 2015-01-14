package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class CSanctionConf {
	
	private double	threshold;
	
	private double	discernability;
	
	
	public double getThreshold() {
		return this.threshold;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_SANCTION_THRESHOLD)
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	
	public double getDiscernability() {
		return this.discernability;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_SANCTION_DISCERNABILITY)
	public void setDiscernability(double discernability) {
		this.discernability = discernability;
	}
}