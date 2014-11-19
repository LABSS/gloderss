package gloderss.agents.state;

import gloderss.actions.SpecificInvestigationAction;

public interface IPoliceOfficer {
	
	public void initializeSim();
	
	
	public void generalInvestigation();
	
	
	public void specificInvestigation(SpecificInvestigationAction action);
}