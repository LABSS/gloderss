package gloderss.agents.intermediaryOrg;

import gloderss.actions.AffiliateRequestAction;

public interface IIntermediaryOrg {
	
	public void initializeSim();
	
	
	public void receiveAffiliationRequest(AffiliateRequestAction action);
	
	
	public void spreadNormativeInformation();
	
	
	public void finalizeSim();
}