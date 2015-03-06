package gloderss.conf;

import gloderss.Constants;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = Constants.TAG_ENTREPRENEUR, namespace = Constants.TAG_NAMESPACE)
public class EntrepreneurConf {
	
	// Number of entrepreneurs
	private int															numberEntrepreneurs;
	
	// Logging timeunit
	private int															loggingTimeUnit;
	
	// Initial wealth
	private double													wealth;
	
	// Periodicity of receiving wage
	private String													periodicityWagePDF;
	
	// Minimum wage
	private double													minimumWage;
	
	// Maximum wage
	private double													maximumWage;
	
	// Variation on the wage at each round
	private double													variationWage;
	
	// Minimum wage
	private double													minimumPrice;
	
	// Maximum wage
	private double													maximumPrice;
	
	// Variation on the wage at each round
	private double													variationPrice;
	
	// Balance between risk and opportunity
	private double													denounceAlpha;
	
	// Probability of collaborating to the Police if requested
	private double													collaborationProbability;
	
	private double													affiliateThreshold;
	
	// Affiliated to IO
	private boolean													affiliated;
	
	// Initial reputation values
	private EReputationConf									reputationConf;
	
	// EMIL-A Normative XML filename
	private String													normativeXML;
	
	// EMIL-A Normative XSD filename
	private String													normativeXSD;
	
	// Individual weight
	private double													individualWeight;
	
	// Normative weight
	private double													normativeWeight;
	
	// Initial norm salience
	private Map<Integer, NormSalienceConf>	salienceConf;
	
	
	public int getNumberEntrepreneurs() {
		return this.numberEntrepreneurs;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_NUMBER_ENTREPRENEURS)
	public void setNumberEntrepreneurs(int numberEntrepreneurs) {
		this.numberEntrepreneurs = numberEntrepreneurs;
	}
	
	
	public int getLoggingTimeUnit() {
		return this.loggingTimeUnit;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_LOGGING_TIME_UNIT)
	public void setLoggingTimeUnit(int loggingTimeUnit) {
		this.loggingTimeUnit = loggingTimeUnit;
	}
	
	
	public double getWealth() {
		return this.wealth;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_WEALTH)
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public String getPeriodicityWagePDF() {
		return this.periodicityWagePDF;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_PERIODICITY_WAGE_PDF)
	public void setPeriodicityWagePDF(String periodicityWagePDF) {
		this.periodicityWagePDF = periodicityWagePDF;
	}
	
	
	public double getMinimumWage() {
		return this.minimumWage;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_MINIMUM_WAGE)
	public void setMinimumWage(double minimumWage) {
		this.minimumWage = minimumWage;
	}
	
	
	public double getMaximumWage() {
		return this.maximumWage;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_MAXIMUM_WAGE)
	public void setMaximumWage(double maximumWage) {
		this.maximumWage = maximumWage;
	}
	
	
	public double getVariationWage() {
		return this.variationWage;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_VARIATION_WAGE)
	public void setVariationWage(double variationWage) {
		this.variationWage = variationWage;
	}
	
	
	public double getMinimumPrice() {
		return this.minimumPrice;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_MINIMUM_PRICE)
	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	
	
	public double getMaximumPrice() {
		return this.maximumPrice;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_MAXIMUM_PRICE)
	public void setMaximumPrice(double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	
	
	public double getVariationPrice() {
		return this.variationPrice;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_VARIATION_PRICE)
	public void setVariationPrice(double variationPrice) {
		this.variationPrice = variationPrice;
	}
	
	
	public double getDenounceAlpha() {
		return this.denounceAlpha;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_DENOUNCE_ALPHA)
	public void setDenounceAlpha(double denounceAlpha) {
		this.denounceAlpha = denounceAlpha;
	}
	
	
	public double getCollaborationProbability() {
		return this.collaborationProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_COLLABORATION_PROBABILITY)
	public void setCollaborationProbability(double collaborationProbability) {
		this.collaborationProbability = collaborationProbability;
	}
	
	
	public double getAffiliateThreshold() {
		return this.affiliateThreshold;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_AFFILIATE_THRESHOLD)
	public void setAffiliateThreshold(double affiliateThreshold) {
		this.affiliateThreshold = affiliateThreshold;
	}
	
	
	public boolean getAffiliated() {
		return this.affiliated;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_AFFILIATED)
	public void setAffiliated(boolean affiliated) {
		this.affiliated = affiliated;
	}
	
	
	public EReputationConf getReputationConf() {
		return this.reputationConf;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_REPUTATION)
	public void setReputationConf(EReputationConf reputationConf) {
		this.reputationConf = reputationConf;
	}
	
	
	public String getNormativeXML() {
		return this.normativeXML;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_XML)
	public void setNormativeXML(String normativeXML) {
		this.normativeXML = normativeXML;
	}
	
	
	public String getNormativeXSD() {
		return this.normativeXSD;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_XSD)
	public void setNormativeXSD(String normativeXSD) {
		this.normativeXSD = normativeXSD;
	}
	
	
	public double getIndividualWeight() {
		return this.individualWeight;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_INDIVIDUAL_WEIGHT)
	public void setIndividualWeight(double individualWeight) {
		this.individualWeight = individualWeight;
	}
	
	
	public double getNormativeWeight() {
		return this.normativeWeight;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_NORMATIVE_WEIGHT)
	public void setNormativeWeight(double normativeWeight) {
		this.normativeWeight = normativeWeight;
	}
	
	
	public Map<Integer, NormSalienceConf> getSalienceConf() {
		return this.salienceConf;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_NORMS_SALIENCE)
	@XmlJavaTypeAdapter(NormSalienceAdapter.class)
	public void setSaliences(Map<Integer, NormSalienceConf> salienceConf) {
		this.salienceConf = salienceConf;
	}
}