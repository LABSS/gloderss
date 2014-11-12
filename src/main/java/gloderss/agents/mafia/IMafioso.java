package gloderss.agents.mafia;

import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;

public interface IMafioso {
	
	public void initializeSim();
	
	
	public void decideExtortion();
	
	
	public void receivePayment(PayExtortionAction action);
	
	
	public void decidePunishment(NotPayExtortionAction action);
	
	
	public void decideBenefit(PayExtortionAction action);
	
	
	public void decidePentiti();
	
	
	public void receiveStateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
}