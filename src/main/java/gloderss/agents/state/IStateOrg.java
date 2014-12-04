package gloderss.agents.state;

import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.NotCollaborateAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReleaseInvestigationAction;

public interface IStateOrg {
	
	public void initializeSim();
	
	
	public void decideInvestigateExtortion(DenounceExtortionAction action);
	
	
	public void decideInvestigatePunishment(DenouncePunishmentAction action);
	
	
	public void releaseInvestigation(ReleaseInvestigationAction action);
	
	
	public void decideCustody(CaptureMafiosoAction action);
	
	
	public void decideImprisonment(CaptureMafiosoAction action);
	
	
	public void receivePentiti(PentitoAction action);
	
	
	public void receiveCollaboration(CollaborateAction action);
	
	
	public void decideStatePunishment(NotCollaborateAction action);
	
	
	public void decideStateCompensation();
	
	
	public void spreadInformation();
}