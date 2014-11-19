package gloderss.agents.state;

import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.PentitiAction;
import gloderss.actions.ReleaseInvestigationAction;

public interface IStateOrg {
	
	public void initializeSim();
	
	
	public void decideInvestigateExtortion(DenounceExtortionAction action);
	
	
	public void decideInvestigatePunishment(DenouncePunishmentAction action);
	
	
	public void releaseInvestigation(ReleaseInvestigationAction action);
	
	
	public void decideCustody(CaptureMafiosoAction action);
	
	
	public void decideImprisonment(CaptureMafiosoAction action);
	
	
	public void receivePentiti(PentitiAction action);
	
	
	public void decideCollaborationRequest();
	
	
	public void receiveCollaboration();
	
	
	public void decideStatePunishment();
	
	
	public void decideStateCompensation();
	
	
	public void stateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
}