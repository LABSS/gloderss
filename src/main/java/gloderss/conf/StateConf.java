package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_STATE, namespace = Constants.TAG_NAMESPACE)
public class StateConf {
	
	private int			numberPoliceOfficers;
	
	private String	generalInvestigationDurationPDF;
	
	private String	specificInvestigationDurationPDF;
	
	private double	investigateProbability;
	
	private double	captureProbability;
	
	private String	custodyDurationPDF;
	
	private double	convictionProbability;
	
	private String	imprisonmentDurationPDF;
	
	private double	noCollaborationPunishmentProbability;
	
	private double	noCollaborationPunishment;
	
	private String	timeToCompensationPDF;
	
	private double	resourceFondo;
	
	private String	periodicityFondoPDF;
	
	private double	proportionTransferFondo;
	
	private String	informationSpreadPDF;
	
	private double	proportionCustomers;
	
	private double	proportionEntrepreneurs;
	
	
	public int getNumberPoliceOfficers() {
		return numberPoliceOfficers;
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
	
	
	public String getSpecificInvestigationDurationPDF() {
		return this.specificInvestigationDurationPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_SPECIFIC_INVESTIGATION_DURATION_PDF)
	public void setSpecificInvestigationDurationPDF(
			String specificInvestigationDurationPDF) {
		this.specificInvestigationDurationPDF = specificInvestigationDurationPDF;
	}
	
	
	public double getInvestigateProbability() {
		return this.investigateProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_INVESTIGATE_PROBABILITY)
	public void setInvestigateProbability(double investigateProbability) {
		this.investigateProbability = investigateProbability;
	}
	
	
	public double getCaptureProbability() {
		return captureProbability;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_CAPTURE_PROBABILITY)
	public void setCaptureProbability(double captureProbability) {
		this.captureProbability = captureProbability;
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
	
	
	public String getInformationSpreadPDF() {
		return this.informationSpreadPDF;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_INFORMATION_SPREAD_PDF)
	public void setInformationSpreadPDF(String informationSpreadPDF) {
		this.informationSpreadPDF = informationSpreadPDF;
	}
	
	
	public double getProportionCustomers() {
		return this.proportionCustomers;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_CUSTOMERS)
	public void setProportionCustomers(double proportionCustomers) {
		this.proportionCustomers = proportionCustomers;
	}
	
	
	public double getProportionEntrepreneurs() {
		return this.proportionEntrepreneurs;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE_PROPORTION_ENTREPRENEURS)
	public void setProportionEntrepreneurs(double proportionEntrepreneurs) {
		this.proportionEntrepreneurs = proportionEntrepreneurs;
	}
}