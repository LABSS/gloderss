package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_INTERMEDIARY_ORG, namespace = Constants.TAG_NAMESPACE)
public class IntermediaryOrgConf {
	
	private String	timeToAffiliatePDF;
	
	private String	spreadInfoFunction;
	
	private String	proportionConsumers;
	
	private String	proportionEntrepreneurs;
	
	
	public String getTimeToAffiliatePDF() {
		return this.timeToAffiliatePDF;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_TIME_TO_AFFILIATE_PDF)
	public void setTimeToAffiliatePDF(String timeToAffiliatePDF) {
		this.timeToAffiliatePDF = timeToAffiliatePDF;
	}
	
	
	public String getSpreadInfoFunction() {
		return this.spreadInfoFunction;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_SPREAD_INFO_FUNCTION)
	public void setSlope(String spreadInfoFunction) {
		this.spreadInfoFunction = spreadInfoFunction;
	}
	
	
	public String getProportionConsumers() {
		return this.proportionConsumers;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_PROPORTION_CONSUMERS)
	public void setProportionConsumers(String proportionConsumers) {
		this.proportionConsumers = proportionConsumers;
	}
	
	
	public String getProportionEntrepreneurs() {
		return this.proportionEntrepreneurs;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_PROPORTION_ENTREPRENEURS)
	public void setProportionEntrepreneurs(String proportionEntrepreneurs) {
		this.proportionEntrepreneurs = proportionEntrepreneurs;
	}
}