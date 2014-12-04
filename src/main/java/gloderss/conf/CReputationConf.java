package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class CReputationConf {
	
	private double	entrepreneurPayer;
	
	private double	entrepreneurPayerThreshold;
	
	
	public double getEntrepreneurPayer() {
		return this.entrepreneurPayer;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_PAYER)
	public void setEntrepreneurPayer(double entrepreneurPayer) {
		this.entrepreneurPayer = entrepreneurPayer;
	}
	
	
	public double getEntrepreneurPayerThreshold() {
		return this.entrepreneurPayerThreshold;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_PAYER_THRESHOLD)
	public void setEntrepreneurPayerThreshold(double entrepreneurPayerThreshold) {
		this.entrepreneurPayerThreshold = entrepreneurPayerThreshold;
	}
}