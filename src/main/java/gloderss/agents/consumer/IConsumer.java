package gloderss.agents.consumer;

public interface IConsumer {
	
	public void initializeSim();
	
	
	public void receiveStateSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void consumerSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
	
	
	public void decideBuy();
}