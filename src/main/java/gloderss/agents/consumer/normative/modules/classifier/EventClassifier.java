package gloderss.agents.consumer.normative.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.modules.classifier.EventClassifierAbstract;
import gloderss.Constants;
import gloderss.Constants.Norms;
import gloderss.actions.BuyNotPayExtortionAction;
import gloderss.actions.BuyPayExtortionAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NormativeInfoAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenounceExtortionAffiliatedAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotDenouncePunishmentAffiliatedAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.communication.Message;

public class EventClassifier extends EventClassifierAbstract {
	
	public EventClassifier(Integer agentId) {
		super(agentId);
	}
	
	
	@Override
	public NormativeEventEntityAbstract classify(Object event) {
		
		NormativeEventEntityAbstract entity = null;
		if(event instanceof Message) {
			
			Message msg = (Message) event;
			
			Object content = msg.getContent();
			
			// Buy from extortion payer
			if(content instanceof BuyPayExtortionAction) {
				
				BuyPayExtortionAction action = (BuyPayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(BuyPayExtortionAction.Param.CONSUMER_ID),
						(int) action.getParam(BuyPayExtortionAction.Param.ENTREPRENEUR_ID),
						this.agentId, action);
				
				// Buy from non extortion payer
			} else if(content instanceof BuyNotPayExtortionAction) {
				
				BuyNotPayExtortionAction action = (BuyNotPayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(BuyNotPayExtortionAction.Param.CONSUMER_ID),
						(int) action
								.getParam(BuyNotPayExtortionAction.Param.ENTREPRENEUR_ID),
						this.agentId, action);
				
				// Denounce Extortion
			} else if(content instanceof DenounceExtortionAction) {
				
				DenounceExtortionAction action = (DenounceExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(DenounceExtortionAction.Param.STATE_ID),
						this.agentId, action);
				
				// Denounce Extortion Affiliated
			} else if(content instanceof DenounceExtortionAffiliatedAction) {
				
				DenounceExtortionAffiliatedAction action = (DenounceExtortionAffiliatedAction) content;
				
				entity = new ActionEvent(
						msg.getTime(),
						(int) action
								.getParam(DenounceExtortionAffiliatedAction.Param.ENTREPRENEUR_ID),
						(int) action
								.getParam(DenounceExtortionAffiliatedAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not denounce Extortion
			} else if(content instanceof NotDenounceExtortionAction) {
				
				NotDenounceExtortionAction action = (NotDenounceExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(NotDenounceExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(NotDenounceExtortionAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not denounce Extortion Affiliated
			} else if(content instanceof NotDenounceExtortionAffiliatedAction) {
				
				NotDenounceExtortionAffiliatedAction action = (NotDenounceExtortionAffiliatedAction) content;
				
				entity = new ActionEvent(
						msg.getTime(),
						(int) action
								.getParam(NotDenounceExtortionAffiliatedAction.Param.ENTREPRENEUR_ID),
						(int) action
								.getParam(NotDenounceExtortionAffiliatedAction.Param.STATE_ID),
						this.agentId, action);
				
				// Denounce Punishment
			} else if(content instanceof DenouncePunishmentAction) {
				
				DenouncePunishmentAction action = (DenouncePunishmentAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(DenouncePunishmentAction.Param.STATE_ID),
						this.agentId, action);
				
				// Denounce Punishment Affiliated
			} else if(content instanceof DenouncePunishmentAffiliatedAction) {
				
				DenouncePunishmentAffiliatedAction action = (DenouncePunishmentAffiliatedAction) content;
				
				entity = new ActionEvent(
						msg.getTime(),
						(int) action
								.getParam(DenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID),
						(int) action
								.getParam(DenouncePunishmentAffiliatedAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not Denounce Punishment
			} else if(content instanceof NotDenouncePunishmentAction) {
				
				NotDenouncePunishmentAction action = (NotDenouncePunishmentAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(NotDenouncePunishmentAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(NotDenouncePunishmentAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not Denounce Punishment Affiliated
			} else if(content instanceof NotDenouncePunishmentAffiliatedAction) {
				
				NotDenouncePunishmentAffiliatedAction action = (NotDenouncePunishmentAffiliatedAction) content;
				
				entity = new ActionEvent(
						msg.getTime(),
						(int) action
								.getParam(NotDenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID),
						(int) action
								.getParam(NotDenouncePunishmentAffiliatedAction.Param.STATE_ID),
						this.agentId, action);
				
				// Pay Extortion
			} else if(content instanceof PayExtortionAction) {
				
				PayExtortionAction action = (PayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(PayExtortionAction.Param.MAFIOSO_ID),
						this.agentId, action);
				
				// Not Pay Extortion
			} else if(content instanceof NotPayExtortionAction) {
				
				NotPayExtortionAction action = (NotPayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(NotPayExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(NotPayExtortionAction.Param.MAFIOSO_ID),
						this.agentId, action);
				
				// State punishment
			} else if(content instanceof StatePunishmentAction) {
				
				StatePunishmentAction action = (StatePunishmentAction) content;
				
				entity = new NormativeEvent(msg.getTime(),
						(int) action.getParam(StatePunishmentAction.Param.STATE_ID),
						(int) action.getParam(StatePunishmentAction.Param.ENTREPRENEUR_ID),
						this.agentId, NormativeEventType.PUNISHMENT_OBSERVED,
						Norms.PAY_EXTORTION.ordinal());
				
				// Mafia punishment
			} else if(content instanceof MafiaPunishmentAction) {
				
				MafiaPunishmentAction action = (MafiaPunishmentAction) content;
				
				entity = new NormativeEvent(msg.getTime(),
						(int) action.getParam(MafiaPunishmentAction.Param.MAFIOSO_ID),
						(int) action.getParam(MafiaPunishmentAction.Param.ENTREPRENEUR_ID),
						this.agentId, NormativeEventType.PUNISHMENT_OBSERVED,
						Norms.NOT_PAY_EXTORTION.ordinal());
				
				// Normative Information
			} else if(content instanceof NormativeInfoAction) {
				
				NormativeInfoAction action = (NormativeInfoAction) content;
				
				String normInfo = (String) action
						.getParam(NormativeInfoAction.Param.NORMATIVE_INFO);
				
				if(normInfo
						.equalsIgnoreCase(Constants.Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS
								.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal());
					
				} else if(normInfo
						.equalsIgnoreCase(Constants.Norms.BUY_FROM_PAYING_ENTREPRENEURS
								.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal());
					
				}
				
				// Message
			} else if(content instanceof Message) {
				
				Message otherMsg = (Message) content;
				Object contentMsg = otherMsg.getContent();
				
				// Denounce Punishment
				if(contentMsg instanceof DenouncePunishmentAction) {
					
					DenouncePunishmentAction action = (DenouncePunishmentAction) contentMsg;
					
					entity = new ActionEvent(otherMsg.getTime(),
							(int) action
									.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID),
							(int) action.getParam(DenouncePunishmentAction.Param.STATE_ID),
							msg.getSender(), action);
					
					// Denounce Punishment Affiliated
				} else if(contentMsg instanceof DenouncePunishmentAffiliatedAction) {
					
					DenouncePunishmentAffiliatedAction action = (DenouncePunishmentAffiliatedAction) contentMsg;
					
					entity = new ActionEvent(
							otherMsg.getTime(),
							(int) action
									.getParam(DenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID),
							(int) action
									.getParam(DenouncePunishmentAffiliatedAction.Param.STATE_ID),
							msg.getSender(), action);
					
					// State punishment
				} else if(contentMsg instanceof StatePunishmentAction) {
					
					StatePunishmentAction action = (StatePunishmentAction) contentMsg;
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(StatePunishmentAction.Param.STATE_ID),
							(int) action
									.getParam(StatePunishmentAction.Param.ENTREPRENEUR_ID),
							msg.getSender(), NormativeEventType.PUNISHMENT_OBSERVED,
							Norms.PAY_EXTORTION.ordinal());
					
					// Mafia punishment
				} else if(contentMsg instanceof MafiaPunishmentAction) {
					
					MafiaPunishmentAction action = (MafiaPunishmentAction) contentMsg;
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(MafiaPunishmentAction.Param.MAFIOSO_ID),
							(int) action
									.getParam(MafiaPunishmentAction.Param.ENTREPRENEUR_ID),
							msg.getSender(), NormativeEventType.PUNISHMENT_OBSERVED,
							Norms.NOT_PAY_EXTORTION.ordinal());
					
				}
			}
		}
		
		return entity;
	}
}