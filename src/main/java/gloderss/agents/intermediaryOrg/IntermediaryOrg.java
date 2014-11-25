package gloderss.agents.intermediaryOrg;

import gloderss.Constants;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliateRequestAction;
import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.NormativeInfoSpreadAction;
import gloderss.actions.PentitiAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.AbstractAgent;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.IntermediaryOrgConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntermediaryOrg extends AbstractAgent implements IIntermediaryOrg {
	
	private IntermediaryOrgConf							conf;
	
	private Map<Integer, ConsumerAgent>			consumers;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private PDFAbstract											timeToAffiliatePDF;
	
	private PDFAbstract											spreadInformationPDF;
	
	private List<Integer>										affiliated;
	
	
	/**
	 * Intermediary Organization constructor
	 * 
	 * @param id
	 *          Intermediary Organization identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Intermediary organization configuration
	 * @param consumers
	 *          List of consumers
	 * @param entrepreneurs
	 *          List of entrepreneurs
	 * @return none
	 */
	public IntermediaryOrg(Integer id, EventSimulator simulator,
			IntermediaryOrgConf conf, Map<Integer, ConsumerAgent> consumers,
			Map<Integer, EntrepreneurAgent> entrepreneurs) {
		super(id, simulator);
		
		this.conf = conf;
		
		this.consumers = consumers;
		this.entrepreneurs = entrepreneurs;
		
		this.timeToAffiliatePDF = PDFAbstract.getInstance(conf
				.getTimeToAffiliatePDF());
		
		this.spreadInformationPDF = PDFAbstract.getInstance(conf
				.getInformationSpreadPDF());
		
		this.affiliated = new ArrayList<Integer>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		Event event = new Event(this.simulator.now()
				+ this.timeToAffiliatePDF.nextValue(), this,
				Constants.EVENT_SPREAD_INFORMATION);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void receiveAffiliation(AffiliateRequestAction action) {
		
		int entrepreneurId = (int) action
				.getParam(AffiliateRequestAction.Param.ENTREPRENEUR_ID);
		
		if(!this.affiliated.contains(entrepreneurId)) {
			this.affiliated.add(entrepreneurId);
		}
	}
	
	
	@Override
	public void spreadInformation() {
		
		// Spread information to Consumers
		NormativeInfoSpreadAction notBuyPayExtortion = new NormativeInfoSpreadAction(
				this.id, Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.name());
		
		int numConsumers = (int) (this.consumers.size() * this.conf
				.getProportionCustomers());
		List<Integer> consumerIds = new ArrayList<Integer>();
		while(consumerIds.size() < numConsumers) {
			
			int consumerId = (int) this.consumers.keySet().toArray()[RandomUtil
					.nextIntFromTo(0, (this.consumers.size() - 1))];
			
			if(!consumerIds.contains(consumerId)) {
				consumerIds.add(consumerId);
				
				Message msg = new Message(this.simulator.now(), this.id, consumerId,
						notBuyPayExtortion);
				this.sendMsg(msg);
			}
		}
		
		NormativeInfoSpreadAction notPayExtortion = new NormativeInfoSpreadAction(
				this.id, Norms.NOT_PAY_EXTORTION.name());
		
		NormativeInfoSpreadAction denounceExtortion = new NormativeInfoSpreadAction(
				this.id, Norms.DENOUNCE_EXTORTION.name());
		
		int numEntrepreneurs = (int) (this.entrepreneurs.size() * this.conf
				.getProportionEntrepreneurs());
		List<Integer> entrepreneurIds = new ArrayList<Integer>();
		int qtyEntrepreneurs = this.entrepreneurs.size();
		while(entrepreneurIds.size() < numEntrepreneurs) {
			
			int entrepreneurId = (int) this.entrepreneurs.keySet().toArray()[RandomUtil
					.nextIntFromTo(0, (qtyEntrepreneurs - 1))];
			
			if(!entrepreneurIds.contains(entrepreneurId)) {
				entrepreneurIds.add(entrepreneurId);
				
				Message msg = new Message(this.simulator.now(), this.id,
						entrepreneurId, notPayExtortion);
				this.sendMsg(msg);
				
				msg = new Message(this.simulator.now(), this.id, entrepreneurId,
						denounceExtortion);
				this.sendMsg(msg);
			}
		}
		
		Event event = new Event(this.simulator.now()
				+ this.spreadInformationPDF.nextValue(), this,
				Constants.EVENT_SPREAD_INFORMATION);
		this.simulator.insert(event);
	}
	
	
	/*******************************
	 * 
	 * Handle communication requests
	 * 
	 *******************************/
	
	@Override
	public synchronized void handleMessage(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (msg.getReceiver().contains(this.id))) {
			
			if(content instanceof AffiliateRequestAction) {
				this.receiveAffiliation((AffiliateRequestAction) content);
				
			} else if(content instanceof CaptureMafiosoAction) {
				// TODO
				
			} else if(content instanceof CollaborateAction) {
				// TODO
				
			} else if(content instanceof DenounceExtortionAction) {
				// TODO
				
			} else if(content instanceof DenouncePunishmentAction) {
				// TODO
				
			} else if(content instanceof ImprisonmentAction) {
				// TODO
				
			} else if(content instanceof PentitiAction) {
				// TODO
				
			} else if(content instanceof StateCompensationAction) {
				// TODO
				
			} else if(content instanceof StatePunishmentAction) {
				// TODO
				
			}
		}
	}
	
	
	@Override
	public Object handleInfo(InfoAbstract info) {
		Object infoRequested = null;
		
		if(info.getType().equals(InfoAbstract.Type.REQUEST)) {
			
			InfoRequest request = (InfoRequest) info;
			switch(request.getInfoRequest()) {
				case Constants.REQUEST_ID:
					infoRequested = this.id;
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
		}
		
		return infoRequested;
	}
	
	
	@Override
	public void handleObservation(Message msg) {
		// NOTHING
	}
	
	
	/*******************************
	 * 
	 * Handle simulation events
	 * 
	 *******************************/
	
	@Override
	public void handleEvent(Event event) {
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_SPREAD_INFORMATION:
				this.spreadInformation();
				break;
		}
	}
}