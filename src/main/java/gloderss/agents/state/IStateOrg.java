package gloderss.agents.state;

public interface IStateOrg {
	
	public void initializeSim();
	
	
	public void receiveDenouceExtortion();
	
	
	public void decideInvestigateExtortion();
	
	
	public void receiveDenoucePunishment();
	
	
	public void decideInvestigatePunishment();
	
	
	public void receivePentiti();
	
	
	public void decideCollaborationRequest();
	
	
	public void receiveCollaboration();
	
	
	public void decideStatePunishment();
	
	
	public void decideStateCompensation();
	
	
	public void stateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
}