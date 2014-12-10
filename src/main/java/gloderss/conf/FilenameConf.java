package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class FilenameConf {
	
	private String	extortion;
	
	private String	punishment;
	
	private String	purchase;
	
	private String	intermediaryOrganization;
	
	private String	mafiaOrg;
	
	private String	mafioso;
	
	private String	stateOrg;
	
	private String	policeOfficer;
	
	
	public String getExtortion() {
		return this.extortion;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_EXTORTION)
	public void setExtortion(String extortion) {
		this.extortion = extortion;
	}
	
	
	public String getPunishment() {
		return this.punishment;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_PUNISHMENT)
	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}
	
	
	public String getPurchase() {
		return this.purchase;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_PURCHASE)
	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}
	
	
	public String getIntermediaryOrganization() {
		return this.intermediaryOrganization;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_INTERMEDIARY_ORGANIZATION)
	public void setIntermediaryOrganization(String intermediaryOrganization) {
		this.intermediaryOrganization = intermediaryOrganization;
	}
	
	
	public String getMafiaOrg() {
		return this.mafiaOrg;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_MAFIA_ORG)
	public void setMafiaOrg(String mafiaOrg) {
		this.mafiaOrg = mafiaOrg;
	}
	
	
	public String getMafioso() {
		return this.mafioso;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_MAFIOSO)
	public void setMafioso(String mafioso) {
		this.mafioso = mafioso;
	}
	
	
	public String getStateOrg() {
		return this.stateOrg;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_STATE_ORG)
	public void setStateOrg(String stateOrg) {
		this.stateOrg = stateOrg;
	}
	
	
	public String getPoliceOfficer() {
		return this.policeOfficer;
	}
	
	
	@XmlElement(name = Constants.TAG_FILENAME_POLICE_OFFICER)
	public void setPoliceOfficer(String policeOfficer) {
		this.policeOfficer = policeOfficer;
	}
}