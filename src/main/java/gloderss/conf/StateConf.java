package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_STATE, namespace = Constants.TAG_NAMESPACE)
public class StateConf {
	
	private int			numberPoliceOfficers;
	
	private int			investigativeDuration;
	
	private double	captureProbability;
	
	private double	incarcerationProbability;
	
	private int			incarcerationDuration;
	
	private double	proportionTransferFondo;
	
	
	public int getNumberPoliceOfficers() {
		return numberPoliceOfficers;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_NUMBER_POLICE_OFFICERS)
	public void setNumberPoliceOfficers(int numberPoliceOfficers) {
		this.numberPoliceOfficers = numberPoliceOfficers;
	}
	
	
	public int getInvestigativeDuration() {
		return this.investigativeDuration;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_INVESTIGATIVE_DURATION)
	public void setInvestigativeDuration(int investigativeDuration) {
		this.investigativeDuration = investigativeDuration;
	}
	
	
	public double getCaptureProbability() {
		return captureProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_CAPTURE_PROBABILITY)
	public void setCaptureProbability(double captureProbability) {
		this.captureProbability = captureProbability;
	}
	
	
	public double getIncarcerationProbability() {
		return this.incarcerationProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_INCARCERATION_PROBABILITY)
	public void setIncarcerationProbability(double incarcerationProbability) {
		this.incarcerationProbability = incarcerationProbability;
	}
	
	
	public int getIncarcerationDuration() {
		return this.incarcerationDuration;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_INCARCERATION_DURATION)
	public void setIncarcerationDuration(int incarcerationDuration) {
		this.incarcerationDuration = incarcerationDuration;
	}
	
	
	public double getProportionTransferFondo() {
		return this.proportionTransferFondo;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_TRANSFER_FONDO)
	public void setProportionTransferFondo(double proportionTransferFondo) {
		this.proportionTransferFondo = proportionTransferFondo;
	}
}