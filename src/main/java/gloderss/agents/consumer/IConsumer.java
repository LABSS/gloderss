package gloderss.agents.consumer;

import gloderss.actions.DeliverProductAction;

public interface IConsumer {
	
	public void initializeSim();
	
	
	public void receiveStateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void consumerSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
	
	
	public void buyProduct();
	
	
	public void receiveProduct(DeliverProductAction action);
}