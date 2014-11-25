package gloderss.agents.intermediaryOrg;

import gloderss.actions.AffiliateRequestAction;

public interface IIntermediaryOrg {
	
	public void initializeSim();
	
	
	public void receiveAffiliation(AffiliateRequestAction action);
	
	
	public void spreadInformation();
}