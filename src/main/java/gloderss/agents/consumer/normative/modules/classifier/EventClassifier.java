package gloderss.agents.consumer.normative.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.modules.classifier.EventClassifierAbstract;
import gloderss.actions.BuyNotPayExtortionAction;
import gloderss.actions.BuyPayExtortionAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
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
			
			// Buy extortion payer
			if(content instanceof BuyPayExtortionAction) {
				
				BuyPayExtortionAction action = (BuyPayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(BuyPayExtortionAction.Param.CONSUMER_ID),
						(int) action.getParam(BuyPayExtortionAction.Param.ENTREPRENEUR_ID),
						this.agentId, action);
				
				// Buy non extortion payer
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
				
				// Denounce Punishment
			} else if(content instanceof DenouncePunishmentAction) {
				
				DenouncePunishmentAction action = (DenouncePunishmentAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action
								.getParam(DenouncePunishmentAction.Param.ENTREPRENEUR_ID),
						(int) action.getParam(DenouncePunishmentAction.Param.STATE_ID),
						this.agentId, action);
				
				// Pay Extortion
			} else if(content instanceof PayExtortionAction) {
				
				PayExtortionAction action = (PayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(PayExtortionAction.Param.VICTIM_ID),
						(int) action.getParam(PayExtortionAction.Param.MAFIOSO_ID),
						this.agentId, action);
				
				// Not Pay Extortion
			} else if(content instanceof NotPayExtortionAction) {
				
				NotPayExtortionAction action = (NotPayExtortionAction) content;
				
				entity = new ActionEvent(msg.getTime(),
						(int) action.getParam(NotPayExtortionAction.Param.VICTIM_ID),
						(int) action.getParam(NotPayExtortionAction.Param.MAFIOSO_ID),
						this.agentId, action);
				
			}
		}
		
		return entity;
	}
}