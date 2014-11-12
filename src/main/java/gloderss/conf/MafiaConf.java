package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_MAFIA, namespace = Constants.TAG_NAMESPACE)
public class MafiaConf {
	
	private int			numberMafiosi;
	
	private double	wealth;
	
	private double	demandMean;
	
	private double	demandStDev;
	
	private double	extortionLevel;
	
	private double	punishmentSeverity;
	
	private double	punishmentProbability;
	
	private double	minimumBenefit;
	
	private double	maximumBenefit;
	
	private double	pentitiProbability;
	
	
	public int getNumberMafiosi() {
		return this.numberMafiosi;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_NUMBER_MAFIOSI)
	public void setNumberMafiosi(int numberMafiosi) {
		this.numberMafiosi = numberMafiosi;
	}
	
	
	public double getWealth() {
		return this.wealth;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_WEALTH)
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public double getDemandMean() {
		return this.demandMean;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_DEMAND_MEAN)
	public void setDemandMean(double demandMean) {
		this.demandMean = demandMean;
	}
	
	
	public double getDemandStDev() {
		return this.demandStDev;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA_DEMAND_STANDARD_DEVIATION)
	public void setDemandStDev(double demandStDev) {
		this.demandStDev = demandStDev;
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
}