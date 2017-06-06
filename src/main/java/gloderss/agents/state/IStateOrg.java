package gloderss.agents.state;

import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.NotCollaborateAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReleaseInvestigationAction;

public interface IStateOrg {
  
  public void initializeSim();
  
  
  public void decideInvestigateExtortion(DenounceExtortionAction action);
  
  
  public void decideInvestigateExtortionAffiliated(
      DenounceExtortionAffiliatedAction action);
  
  
  public void decideInvestigatePunishment(DenouncePunishmentAction action);
  
  
  public void decideInvestigatePunishmentAffiliated(
      DenouncePunishmentAffiliatedAction action);
  
  
  public void releaseInvestigation(ReleaseInvestigationAction action);
  
  
  public void decideCustody(CaptureMafiosoAction action);
  
  
  public void decideConviction(CaptureMafiosoAction action);
  
  
  public void receivePentito(PentitoAction action);
  
  
  public void receiveCollaboration(CollaborateAction action);
  
  
  public void decideStatePunishment(NotCollaborateAction action);
  
  
  public void decideStateCompensation();
  
  
  public void spreadNormativeInformation();
  
  
  public void finalizeSim();
}