package gloderss.agents.entrepreneur;

import gloderss.actions.ExtortionAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;

public interface IEntrepreneur {
	
	public void initializeSim();
	
	
	public void receiveWage();
	
	
	public void decidePayment(ExtortionAction action);
	
	
	public void decideDenounceExtortion(ExtortionAction action);
	
	
	public void receiveMafiaBenefit(MafiaBenefitAction action);
	
	
	public void receiveMafiaPunishment(MafiaPunishmentAction action);
	
	
	public void decideDenouncePunishment(MafiaPunishmentAction action);
	
	
	public void decideCollaboration();
	
	
	public void receiveStatePunishment();
	
	
	public void receiveStateCompensation();
	
	
	public void receiveStateSpreadInformation();
	
	
	public void entrepreneurSpreadInformation();
	
	
	public void receiveEntrepreurSpreadInformation();
	
	
	public void receiveIOSpreadInformation();
	
	
	public void receiveConsumerSpreadInformation();
	
	
	public void receiveBuy();
	
	
	public void decideAffiliation();
}