package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class CReputationConf {
	
	private Double	entrepreneurPayer;
	
	
	public Double getEntrepreneurPayer() {
		return this.entrepreneurPayer;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_PAYER)
	public void setEntrepreneurPayer(Double entrepreneurPayer) {
		this.entrepreneurPayer = entrepreneurPayer;
	}
}