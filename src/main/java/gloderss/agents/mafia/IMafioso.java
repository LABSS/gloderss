package gloderss.agents.mafia;

import gloderss.actions.CustodyAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;

public interface IMafioso {
	
	public void initializeSim();
	
	
	public void decideExtortion();
	
	
	public void collectExtortion();
	
	
	public void receivePayment(PayExtortionAction action);
	
	
	public void decidePunishment(NotPayExtortionAction action);
	
	
	public void decideBenefit(PayExtortionAction action);
	
	
	public void custody(CustodyAction action);
	
	
	public void releaseCustody();
	
	
	public void imprisonment(ImprisonmentAction action);
	
	
	public void releasePrison();
	
	
	public void decidePentito();
	
	
	public void receiveStateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
}