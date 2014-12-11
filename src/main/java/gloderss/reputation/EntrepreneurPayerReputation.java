package gloderss.reputation;

import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import java.util.HashMap;
import java.util.Map;

public class EntrepreneurPayerReputation extends ReputationAbstract {
	
	private Map<Integer, Double>	value;
	
	
	public EntrepreneurPayerReputation(double unknownValue) {
		super(unknownValue);
		
		this.value = new HashMap<Integer, Double>();
	}
	
	
	@Override
	public boolean isUnknown(int... target) {
		
		if(this.value.containsKey(target)) {
			return false;
		}
		
		return false;
	}
	
	
	@Override
	public double getReputation(int... target) {
		
		if(this.value.containsKey(target)) {
			return this.value.get(target);
		}
		
		return this.unknownValue;
	}
	
	
	@Override
	public void setReputation(int target, double value) {
		this.value.put(target, value);
	}
	
	
	@Override
	public void updateReputation(Object action) {
		
		boolean updated = false;
		// Denounce punishment
		if(action instanceof DenouncePunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((DenouncePunishmentAction) action)
					.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// State compensation
		} else if(action instanceof StateCompensationAction) {
			updated = true;
			
			int entrepreneurId = (int) ((StateCompensationAction) action)
					.getParam(StateCompensationAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// State punishment
		} else if(action instanceof StatePunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((StatePunishmentAction) action)
					.getParam(StatePunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Affiliation accepted
		} else if(action instanceof AffiliationAcceptedAction) {
			updated = true;
			
			int entrepreneurId = (int) ((AffiliationAcceptedAction) action)
					.getParam(AffiliationAcceptedAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// Affiliation denied
		} else if(action instanceof AffiliationDeniedAction) {
			updated = true;
			
			int entrepreneurId = (int) ((AffiliationDeniedAction) action)
					.getParam(AffiliationDeniedAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Collaboration request
		} else if(action instanceof CollaborationRequestAction) {
			updated = true;
			
			int entrepreneurId = (int) ((CollaborationRequestAction) action)
					.getParam(CollaborationRequestAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Collaborate
		} else if(action instanceof CollaborateAction) {
			updated = true;
			
			int entrepreneurId = (int) ((CollaborateAction) action)
					.getParam(CollaborateAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Denounce extortion
		} else if(action instanceof DenounceExtortionAction) {
			updated = true;
			
			int entrepreneurId = (int) ((DenounceExtortionAction) action)
					.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// Not denounce extortion
		} else if(action instanceof NotDenounceExtortionAction) {
			updated = true;
			
			int entrepreneurId = (int) ((NotDenounceExtortionAction) action)
					.getParam(NotDenounceExtortionAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Denounce punishment
		} else if(action instanceof DenouncePunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((DenouncePunishmentAction) action)
					.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// Not denounce punishment
		} else if(action instanceof NotDenouncePunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((NotDenouncePunishmentAction) action)
					.getParam(NotDenouncePunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// Mafia benefit
		} else if(action instanceof MafiaBenefitAction) {
			updated = true;
			
			int entrepreneurId = (int) ((MafiaBenefitAction) action)
					.getParam(MafiaBenefitAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Mafia punishment
		} else if(action instanceof MafiaPunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((MafiaPunishmentAction) action)
					.getParam(MafiaPunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// Pay extortion
		} else if(action instanceof PayExtortionAction) {
			updated = true;
			
			int entrepreneurId = (int) ((PayExtortionAction) action)
					.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
			// Not Pay extortion
		} else if(action instanceof NotPayExtortionAction) {
			updated = true;
			
			int entrepreneurId = (int) ((NotPayExtortionAction) action)
					.getParam(NotPayExtortionAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// State compensation
		} else if(action instanceof StateCompensationAction) {
			updated = true;
			
			int entrepreneurId = (int) ((StateCompensationAction) action)
					.getParam(StateCompensationAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MAX);
			
			// State punishment
		} else if(action instanceof StatePunishmentAction) {
			updated = true;
			
			int entrepreneurId = (int) ((StatePunishmentAction) action)
					.getParam(StatePunishmentAction.Param.ENTREPRENEUR_ID);
			
			this.setReputation(entrepreneurId, ReputationAbstract.MIN);
			
		}
		
		if(updated) {
			// NOTHING
		}
		
	}
}