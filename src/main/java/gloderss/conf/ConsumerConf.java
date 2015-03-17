package gloderss.conf;

import gloderss.Constants;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = Constants.TAG_CONSUMER, namespace = Constants.TAG_NAMESPACE)
public class ConsumerConf {
	
	// Number of consumers
	private int															numberConsumers;
	
	// Logging timeunit
	private int															loggingTimeUnit;
	
	// Clustered
	private boolean													clustered;
	
	// Buying PDF
	private String													buyPDF;
	
	// Number of Entrepreneurs to search
	private int															numberEntrepreneursSearch;
	
	// Initial reputation values
	private CReputationConf									reputationConf;
	
	// Sanction information
	private CSanctionConf										sanctionConf;
	
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
	
	
	public int getNumberConsumers() {
		return this.numberConsumers;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_NUMBER_CONSUMERS)
	public void setNumberConsumers(int numberConsumers) {
		this.numberConsumers = numberConsumers;
	}
	
	
	public int getLoggingTimeUnit() {
		return this.loggingTimeUnit;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_LOGGING_TIME_UNIT)
	public void setLoggingTimeUnit(int loggingTimeUnit) {
		this.loggingTimeUnit = loggingTimeUnit;
	}
	
	
	public boolean getClustered() {
		return this.clustered;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_CLUSTERED)
	public void setClustered(boolean clustered) {
		this.clustered = clustered;
	}
	
	
	public String getBuyPDF() {
		return this.buyPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_BUY_PDF)
	public void setBuyPDF(String buyPDF) {
		this.buyPDF = buyPDF;
	}
	
	
	public int getNumberEntrepreneursSearch() {
		return this.numberEntrepreneursSearch;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_NUMBER_ENTREPRENEURS_SEARCH)
	public void setNumberEntrepreneursSearch(int numberEntrepreneursSearch) {
		this.numberEntrepreneursSearch = numberEntrepreneursSearch;
	}
	
	
	public CReputationConf getReputationConf() {
		return this.reputationConf;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_REPUTATION)
	public void setReputationConf(CReputationConf reputationConf) {
		this.reputationConf = reputationConf;
	}
	
	
	public CSanctionConf getSanctionConf() {
		return this.sanctionConf;
	}
	
	
	@XmlElement(name = Constants.TAG_CONSUMER_SANCTION)
	public void setSanctionConf(CSanctionConf sanctionConf) {
		this.sanctionConf = sanctionConf;
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
		if(individualWeight < 0.0) {
			this.individualWeight = 0.0;
		} else if(individualWeight > 1.0) {
			this.individualWeight = 1.0;
		} else {
			this.individualWeight = individualWeight;
		}
	}
	
	
	public double getNormativeWeight() {
		return this.normativeWeight;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_NORMATIVE_WEIGHT)
	public void setNormativeWeight(double normativeWeight) {
		if(normativeWeight < 0.0) {
			this.normativeWeight = 0.0;
		} else if(normativeWeight > 1.0) {
			this.normativeWeight = 1.0;
		} else {
			this.normativeWeight = normativeWeight;
		}
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