package gloderss.agents.entrepreneur.normative.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.modules.classifier.EventClassifierAbstract;
import gloderss.Constants;
import gloderss.Constants.Norms;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.NormativeInfoAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
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
			
			// Denounce Extortion
			if(content instanceof DenounceExtortionAction) {
				
				DenounceExtortionAction action = (DenounceExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(DenounceExtortionAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not denounce Extortion
			} else if(content instanceof NotDenounceExtortionAction) {
				
				NotDenounceExtortionAction action = (NotDenounceExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(NotDenounceExtortionAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(NotDenounceExtortionAction.Param.STATE_ID),
						this.agentId, action);
				
				// Denounce Punishment
			} else if(content instanceof DenouncePunishmentAction) {
				
				DenouncePunishmentAction action = (DenouncePunishmentAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(DenouncePunishmentAction.Param.STATE_ID),
						this.agentId, action);
				
				// Not Denounce Punishment
			} else if(content instanceof NotDenouncePunishmentAction) {
				
				NotDenouncePunishmentAction action = (NotDenouncePunishmentAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(NotDenouncePunishmentAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(NotDenouncePunishmentAction.Param.STATE_ID),
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
				
				// Normative Information
			} else if(content instanceof NormativeInfoAction) {
				
				NormativeInfoAction action = (NormativeInfoAction) content;
				
				String normInfo = (String) action
						.getParam(NormativeInfoAction.Param.NORMATIVE_INFO);
				
				if(normInfo.equalsIgnoreCase(Constants.Norms.DENOUNCE.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.DENOUNCE.ordinal());
					
				} else if(normInfo
						.equalsIgnoreCase(Constants.Norms.NOT_DENOUNCE.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.NOT_DENOUNCE.ordinal());
					
				} else if(normInfo.equalsIgnoreCase(Constants.Norms.PAY_EXTORTION
						.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.PAY_EXTORTION.ordinal());
					
				} else if(normInfo.equalsIgnoreCase(Constants.Norms.NOT_PAY_EXTORTION
						.name())) {
					
					entity = new NormativeEvent(msg.getTime(),
							(int) action.getParam(NormativeInfoAction.Param.AGENT_ID),
							this.agentId, this.agentId,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							Norms.NOT_PAY_EXTORTION.ordinal());
					
				}
			}
		}
		
		return entity;
	}
}