package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class CReputationConf {
	
	private double	entrepreneurPayer;
	
	
	public double getEntrepreneurPayer() {
		return this.entrepreneurPayer;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_PAYER)
	public void setEntrepreneurPayer(double entrepreneurPayer) {
		this.entrepreneurPayer = entrepreneurPayer;
	}
}