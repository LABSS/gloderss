package gloderss.conf;

import gloderss.Constants;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_MAFIA, namespace = Constants.TAG_NAMESPACE)
public class MafiaConf {
	
	private int								numberMafiosi;
	
	private int								loggingTimeUnit;
	
	private double						wealth;
	
	private String						demandPDF;
	
	private double						demandAffiliatedProbability;
	
	private double						extortionLevel;
	
	private double						punishmentSeverity;
	
	private String						collectionPDF;
	
	private double						punishmentProbability;
	
	private double						minimumBenefit;
	
	private double						maximumBenefit;
	
	private double						pentitiProbability;
	
	private double						recruitingThreshold;
	
	private double						recruitingProbability;
	
	// Changes configuration
	private List<ChangeConf>	changesConf	= new ArrayList<ChangeConf>();
	
	
	public int getNumberMafiosi() {
		return this.numberMafiosi;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_NUMBER_MAFIOSI)
	public void setNumberMafiosi(int numberMafiosi) {
		this.numberMafiosi = numberMafiosi;
	}
	
	
	public int getLoggingTimeUnit() {
		return this.loggingTimeUnit;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_LOGGING_TIME_UNIT)
	public void setLoggingTimeUnit(int loggingTimeUnit) {
		this.loggingTimeUnit = loggingTimeUnit;
	}
	
	
	public double getWealth() {
		return this.wealth;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_WEALTH)
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public String getDemandPDF() {
		return this.demandPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_DEMAND_PDF)
	public void setDemandPDF(String demandPDF) {
		this.demandPDF = demandPDF;
	}
	
	
	public double getDemandAffiliatedProbability() {
		return this.demandAffiliatedProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_DEMAND_AFFILIATED_PROBABILITY)
	public void setDemandAffiliatedProbability(double demandAffiliatedProbability) {
		this.demandAffiliatedProbability = demandAffiliatedProbability;
	}
	
	
	public double getExtortionLevel() {
		return this.extortionLevel;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_EXTORTION_LEVEL)
	public void setExtortionLevel(double extortionLevel) {
		this.extortionLevel = extortionLevel;
	}
	
	
	public double getPunishmentSeverity() {
		return this.punishmentSeverity;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_PUNISHMENT_SEVERITY)
	public void setPunishmentSeverity(double punishmentSeverity) {
		this.punishmentSeverity = punishmentSeverity;
	}
	
	
	public String getCollectionPDF() {
		return this.collectionPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_COLLECTION_PDF)
	public void setCollectionPDF(String collectionPDF) {
		this.collectionPDF = collectionPDF;
	}
	
	
	public double getPunishmentProbability() {
		return this.punishmentProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_PUNISHMENT_PROBABILITY)
	public void setPunishmentProbability(double punishmentProbability) {
		this.punishmentProbability = punishmentProbability;
	}
	
	
	public double getMinimumBenefit() {
		return this.minimumBenefit;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_MINIMUM_BENEFIT)
	public void setMinimumBenefit(double minimumBenefit) {
		this.minimumBenefit = minimumBenefit;
	}
	
	
	public double getMaximumBenefit() {
		return this.maximumBenefit;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_MAXIMUM_BENEFIT)
	public void setMaximumBenefit(double maximumBenefit) {
		this.maximumBenefit = maximumBenefit;
	}
	
	
	public double getPentitiProbability() {
		return this.pentitiProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_PENTITI_PROBABILITY)
	public void setPentitiProbability(double pentitiProbability) {
		this.pentitiProbability = pentitiProbability;
	}
	
	
	public double getRecruitingThreshold() {
		return this.recruitingThreshold;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_RECRUITING_THRESHOLD)
	public void setRecruitingThreshold(double recruitingThreshold) {
		this.recruitingThreshold = recruitingThreshold;
	}
	
	
	public double getRecruitingProbability() {
		return this.recruitingProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_RECRUITING_PROBABILITY)
	public void setRecruitingProbability(double recruitingProbability) {
		this.recruitingProbability = recruitingProbability;
	}
	
	
	public List<ChangeConf> getChangesConf() {
		return this.changesConf;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_CHANGES)
	@XmlElement(name = Constants.TAG_CHANGE)
	public void setChangesConf(List<ChangeConf> changesConf) {
		this.changesConf = changesConf;
	}
}