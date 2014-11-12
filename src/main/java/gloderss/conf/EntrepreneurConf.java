package gloderss.conf;

import gloderss.Constants;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = Constants.TAG_ENTREPRENEUR, namespace = Constants.TAG_NAMESPACE)
public class EntrepreneurConf {
	
	// Number of entrepreneurs
	private int										number;
	
	// Initial wealth
	private double								wealth;
	
	// Periodicity of receiving wage
	private int										periodicityWage;
	
	// Minimum wage
	private double								minimumWage;
	
	// Maximum wage
	private double								maximumWage;
	
	// Variation on the wage at each round
	private double								variationWage;
	
	// Probability of collaborating to the Police if requested
	private double								collaborationProbability;
	
	// Affiliated to IO
	private boolean								affiliated;
	
	// Initial reputation values
	private EReputationConf				reputationConf;
	
	// EMIL-A Normative XML filename
	private String								normativeXML;
	
	// EMIL-A Normative XSD filename
	private String								normativeXSD;
	
	// Individual weight
	private double								individualWeight;
	
	// Normative weight
	private double								normativeWeight;
	
	// Initial norm salience
	private Map<Integer, Double>	saliences;
	
	
	public int getNumber() {
		return this.number;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_NUMBER_ENTREPRENEURS)
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	public double getWealth() {
		return this.wealth;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_WEALTH)
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public int getPeriodicityWage() {
		return this.periodicityWage;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_PERIODICITY_WAGE)
	public void setPeriodicityWage(int periodicityWage) {
		this.periodicityWage = periodicityWage;
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
	
	
	public Double getVariationWage() {
		return variationWage;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_VARIATION_WAGE)
	public void setVariationWage(double variationWage) {
		this.variationWage = variationWage;
	}
	
	
	public double getCollaborationProbability() {
		return this.collaborationProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_ENTREPRENEUR_COLLABORATION_PROBABILITY)
	public void setCollaborationProbability(double collaborationProbability) {
		this.collaborationProbability = collaborationProbability;
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
	
	
	public Map<Integer, Double> getSaliences() {
		return this.saliences;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_NORMS_SALIENCE)
	@XmlJavaTypeAdapter(NormSalienceAdapter.class)
	public void setSaliences(Map<Integer, Double> saliences) {
		this.saliences = saliences;
	}
}