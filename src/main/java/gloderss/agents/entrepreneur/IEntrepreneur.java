package gloderss.agents.entrepreneur;

import gloderss.actions.BuyProductAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.CollectAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;

public interface IEntrepreneur {
  
  public void initializeSim();
  
  
  public void receiveWage();
  
  
  public void decidePayment( ExtortionAction action );
  
  
  public void decideDenounceExtortion( ExtortionAction action );
  
  
  public void collectExtortion( CollectAction action );
  
  
  public void receiveMafiaBenefit( MafiaBenefitAction action );
  
  
  public void receiveMafiaPunishment( MafiaPunishmentAction action );
  
  
  public void decideDenouncePunishment( MafiaPunishmentAction action );
  
  
  public void decideCollaboration( CollaborationRequestAction action );
  
  
  public void receiveStatePunishment( StatePunishmentAction action );
  
  
  public void receiveStateCompensation( StateCompensationAction action );
  
  
  public void receiveBuy( BuyProductAction action );
  
  
  public void decideAffiliation();
  
  
  public void finalizeSim();
}