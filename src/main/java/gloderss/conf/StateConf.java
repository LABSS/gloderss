package gloderss.conf;

import java.util.ArrayList;
import java.util.List;
import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_STATE, namespace = Constants.TAG_NAMESPACE)
public class StateConf {
	
	private int								numberPoliceOfficers;
	
	private String						generalInvestigationDurationPDF;
	
	private String						bureaucraticActivityDurationPDF;
	
	private String						specificInvestigationDurationPDF;
	
	private double						specificInvestigationProbability;
	
	private double						captureProbability;
	
	private double						evidenceProbability;
	
	private String						custodyDurationPDF;
	
	private double						convictionProbability;
	
	private String						collaborationConvictionFunction;
	
	private String						imprisonmentDurationPDF;
	
	private double						noCollaborationPunishmentProbability;
	
	private double						noCollaborationPunishment;
	
	private String						timeToCompensationPDF;
	
	private double						resourceFondo;
	
	private String						periodicityFondoPDF;
	
	private double						proportionTransferFondo;
	
	private String						spreadInfoFunction;
	
	private String						proportionConsumers;
	
	private String						proportionEntrepreneurs;
	
	// Changes configuration
	private List<ChangeConf>	changesConf	= new ArrayList<ChangeConf>();
	
	
	public int getNumberPoliceOfficers() {
		return this.numberPoliceOfficers;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_NUMBER_POLICE_OFFICERS)
	public void setNumberPoliceOfficers(int numberPoliceOfficers) {
		this.numberPoliceOfficers = numberPoliceOfficers;
	}
	
	
	public String getGeneralInvestigationDurationPDF() {
		return this.generalInvestigationDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_GENERAL_INVESTIGATION_DURATION_PDF)
	public void setGeneralInvestigationDurationPDF(
			String generalInvestigationDurationPDF) {
		this.generalInvestigationDurationPDF = generalInvestigationDurationPDF;
	}
	
	
	public String getBureaucraticActivityDurationPDF() {
		return this.bureaucraticActivityDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_BUREAUCRATIC_ACTIVITY_DURATION_PDF)
	public void setBureaucraticActivityDurationPDF(
			String bureaucraticActivityDurationPDF) {
		this.bureaucraticActivityDurationPDF = bureaucraticActivityDurationPDF;
	}
	
	
	public String getSpecificInvestigationDurationPDF() {
		return this.specificInvestigationDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_SPECIFIC_INVESTIGATION_DURATION_PDF)
	public void setSpecificInvestigationDurationPDF(
			String specificInvestigationDurationPDF) {
		this.specificInvestigationDurationPDF = specificInvestigationDurationPDF;
	}
	
	
	public double getSpecificInvestigationProbability() {
		return this.specificInvestigationProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_SPECIFIC_INVESTIGATION_PROBABILITY)
	public void setSpecificInvestigationProbability(
			double specificInvestigationProbability) {
		this.specificInvestigationProbability = specificInvestigationProbability;
	}
	
	
	public double getCaptureProbability() {
		return this.captureProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_CAPTURE_PROBABILITY)
	public void setCaptureProbability(double captureProbability) {
		this.captureProbability = captureProbability;
	}
	
	
	public double getEvidenceProbability() {
		return this.evidenceProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_EVIDENCE_PROBABILITY)
	public void setEvidenceProbability(double evidenceProbability) {
		this.evidenceProbability = evidenceProbability;
	}
	
	
	public String getCustodyDurationPDF() {
		return this.custodyDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_CUSTODY_DURATION_PDF)
	public void setCustodyDurationPDF(String custodyDurationPDF) {
		this.custodyDurationPDF = custodyDurationPDF;
	}
	
	
	public double getConvictionProbability() {
		return this.convictionProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_CONVICTION_PROBABILITY)
	public void setConvictionProbability(double convictionProbability) {
		this.convictionProbability = convictionProbability;
	}
	
	
	public String getCollaborationConvictionFunction() {
		return this.collaborationConvictionFunction;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_COLLABORATION_CONVICTION_FUNCTION)
	public void setCollaborationConvictionFunction(
			String collaborationConvictionFunction) {
		this.collaborationConvictionFunction = collaborationConvictionFunction;
	}
	
	
	public String getImprisonmentDurationPDF() {
		return this.imprisonmentDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_IMPRISONMENT_DURATION_PDF)
	public void setImprisonmentDurationPDF(String imprisonmentDurationPDF) {
		this.imprisonmentDurationPDF = imprisonmentDurationPDF;
	}
	
	
	public double getNoCollaborationPunishmentProbability() {
		return this.noCollaborationPunishmentProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_NO_COLLABORATION_PUNISHMENT_PROBABILITY)
	public void setNoCollaborationPunishmentProbability(
			double noCollaborationPunishmentProbability) {
		this.noCollaborationPunishmentProbability = noCollaborationPunishmentProbability;
	}
	
	
	public double getNoCollaborationPunishment() {
		return this.noCollaborationPunishment;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_NO_COLLABORATION_PUNISHMENT)
	public void setNoCollaborationPunishment(double noCollaborationPunishment) {
		this.noCollaborationPunishment = noCollaborationPunishment;
	}
	
	
	public String getTimeToCompensationPDF() {
		return this.timeToCompensationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_TIME_TO_COMPENSATION_PDF)
	public void setTimeToCompensationPDFPDF(String timeToCompensationPDF) {
		this.timeToCompensationPDF = timeToCompensationPDF;
	}
	
	
	public double getResourceFondo() {
		return this.resourceFondo;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_RESOURCE_FONDO)
	public void setResourceFondo(double resourceFondo) {
		this.resourceFondo = resourceFondo;
	}
	
	
	public String getPeriodicityFondoPDF() {
		return this.periodicityFondoPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PERIODICITY_FONDO_PDF)
	public void setPeriodicityFondoPDF(String periodicityFondoPDF) {
		this.periodicityFondoPDF = periodicityFondoPDF;
	}
	
	
	public double getProportionTransferFondo() {
		return this.proportionTransferFondo;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_TRANSFER_FONDO)
	public void setProportionTransferFondo(double proportionTransferFondo) {
		this.proportionTransferFondo = proportionTransferFondo;
	}
	
	
	public String getSpreadInfoFunction() {
		return this.spreadInfoFunction;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_SPREAD_INFO_FUNCTION)
	public void setSpreadInfoFunction(String spreadInfoFunction) {
		this.spreadInfoFunction = spreadInfoFunction;
	}
	
	
	public String getProportionConsumers() {
		return this.proportionConsumers;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_CONSUMERS)
	public void setProportionConsumers(String proportionConsumers) {
		this.proportionConsumers = proportionConsumers;
	}
	
	
	public String getProportionEntrepreneurs() {
		return this.proportionEntrepreneurs;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_ENTREPRENEURS)
	public void setProportionEntrepreneurs(String proportionEntrepreneurs) {
		this.proportionEntrepreneurs = proportionEntrepreneurs;
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