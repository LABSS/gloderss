package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_INTERMEDIARY_ORG, namespace = Constants.TAG_NAMESPACE)
public class IntermediaryOrgConf {
	
	private String	timeToAffiliatePDF;
	
	private double	slope;
	
	private double	proportionCustomers;
	
	private double	proportionEntrepreneurs;
	
	
	public String getTimeToAffiliatePDF() {
		return this.timeToAffiliatePDF;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_TIME_TO_AFFILIATE_PDF)
	public void setTimeToAffiliatePDF(String timeToAffiliatePDF) {
		this.timeToAffiliatePDF = timeToAffiliatePDF;
	}
	
	
	public double getSlope() {
		return this.slope;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_SLOPE)
	public void setSlope(double slope) {
		this.slope = slope;
	}
	
	
	public double getProportionCustomers() {
		return this.proportionCustomers;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_PROPORTION_CUSTOMERS)
	public void setProportionCustomers(double proportionCustomers) {
		this.proportionCustomers = proportionCustomers;
	}
	
	
	public double getProportionEntrepreneurs() {
		return this.proportionEntrepreneurs;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG_PROPORTION_ENTREPRENEURS)
	public void setProportionEntrepreneurs(double proportionEntrepreneurs) {
		this.proportionEntrepreneurs = proportionEntrepreneurs;
	}
}