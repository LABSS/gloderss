package gloderss.rating;

import java.util.HashMap;
import java.util.Map;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenounceExtortionAffiliatedAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotDenouncePunishmentAffiliatedAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.ReputationInfoAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;

/**
 * Reputation of Entrepreneurs as Non-Extortion Payer and Extortion/Punishment
 * Denouncer
 */
public class EntrepreneurRating extends ReputationAbstract {
  
  private Map<Integer, Double> value;
  
  
  public EntrepreneurRating( double unknownValue ) {
    super( unknownValue );
    
    this.value = new HashMap<Integer, Double>();
  }
  
  
  @Override
  public boolean isUnknown( int... target ) {
    
    if ( this.value.containsKey( target ) ) {
      return false;
    }
    
    return false;
  }
  
  
  @Override
  public double getReputation( int... target ) {
    
    if ( this.value.containsKey( target ) ) {
      return this.value.get( target );
    }
    
    return this.unknownValue;
  }
  
  
  @Override
  public void setReputation( int target, double value ) {
    this.value.put( target, value );
  }
  
  
  @Override
  public void updateReputation( Object action ) {
    
    // State compensation
    if ( action instanceof StateCompensationAction ) {
      
      int entrepreneurId = (int) ((StateCompensationAction) action)
          .getParam( StateCompensationAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // State punishment
    } else if ( action instanceof StatePunishmentAction ) {
      
      int entrepreneurId = (int) ((StatePunishmentAction) action)
          .getParam( StatePunishmentAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Affiliation accepted
    } else if ( action instanceof AffiliationAcceptedAction ) {
      
      int entrepreneurId = (int) ((AffiliationAcceptedAction) action)
          .getParam( AffiliationAcceptedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Affiliation denied
    } else if ( action instanceof AffiliationDeniedAction ) {
      
      int entrepreneurId = (int) ((AffiliationDeniedAction) action)
          .getParam( AffiliationDeniedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Collaboration request
    } else if ( action instanceof CollaborationRequestAction ) {
      
      int entrepreneurId = (int) ((CollaborationRequestAction) action)
          .getParam( CollaborationRequestAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Collaborate
    } else if ( action instanceof CollaborateAction ) {
      
      int entrepreneurId = (int) ((CollaborateAction) action)
          .getParam( CollaborateAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Denounce extortion
    } else if ( action instanceof DenounceExtortionAction ) {
      
      int entrepreneurId = (int) ((DenounceExtortionAction) action)
          .getParam( DenounceExtortionAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Denounce extortion affiliated
    } else if ( action instanceof DenounceExtortionAffiliatedAction ) {
      
      int entrepreneurId = (int) ((DenounceExtortionAffiliatedAction) action)
          .getParam( DenounceExtortionAffiliatedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Not denounce extortion
    } else if ( action instanceof NotDenounceExtortionAction ) {
      
      int entrepreneurId = (int) ((NotDenounceExtortionAction) action)
          .getParam( NotDenounceExtortionAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Not denounce extortion Affiliated
    } else if ( action instanceof NotDenounceExtortionAffiliatedAction ) {
      
      int entrepreneurId = (int) ((NotDenounceExtortionAffiliatedAction) action)
          .getParam(
              NotDenounceExtortionAffiliatedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Denounce punishment
    } else if ( action instanceof DenouncePunishmentAction ) {
      
      int entrepreneurId = (int) ((DenouncePunishmentAction) action)
          .getParam( DenouncePunishmentAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Denounce punishment Affiliated
    } else if ( action instanceof DenouncePunishmentAffiliatedAction ) {
      
      int entrepreneurId = (int) ((DenouncePunishmentAffiliatedAction) action)
          .getParam( DenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Not denounce punishment
    } else if ( action instanceof NotDenouncePunishmentAction ) {
      
      int entrepreneurId = (int) ((NotDenouncePunishmentAction) action)
          .getParam( NotDenouncePunishmentAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Not denounce punishment Affiliated
    } else if ( action instanceof NotDenouncePunishmentAffiliatedAction ) {
      
      int entrepreneurId = (int) ((NotDenouncePunishmentAffiliatedAction) action)
          .getParam(
              NotDenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Mafia benefit
    } else if ( action instanceof MafiaBenefitAction ) {
      
      int entrepreneurId = (int) ((MafiaBenefitAction) action)
          .getParam( MafiaBenefitAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Mafia punishment
    } else if ( action instanceof MafiaPunishmentAction ) {
      
      int entrepreneurId = (int) ((MafiaPunishmentAction) action)
          .getParam( MafiaPunishmentAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Pay extortion
    } else if ( action instanceof PayExtortionAction ) {
      
      int entrepreneurId = (int) ((PayExtortionAction) action)
          .getParam( PayExtortionAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MIN );
      
      // Not Pay extortion
    } else if ( action instanceof NotPayExtortionAction ) {
      
      int entrepreneurId = (int) ((NotPayExtortionAction) action)
          .getParam( NotPayExtortionAction.Param.ENTREPRENEUR_ID );
      
      this.setReputation( entrepreneurId, ReputationAbstract.MAX );
      
      // Reputation information
    } else if ( action instanceof ReputationInfoAction ) {
      
      int entrepreneurId = (int) ((ReputationInfoAction) action)
          .getParam( ReputationInfoAction.Param.ENTREPRENEUR_ID );
      
      double reputation = (double) ((ReputationInfoAction) action)
          .getParam( ReputationInfoAction.Param.REPUTATION );
      
      if ( !this.isUnknown( entrepreneurId ) ) {
        reputation = (this.getReputation( entrepreneurId ) + reputation) / 2;
      }
      this.setReputation( entrepreneurId, reputation );
    }
  }
}