package gloderss.agents.consumer;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import gloderss.Constants;
import gloderss.agents.CitizenAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.ConsumerConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;

public class ConsumerAgent extends CitizenAgent implements IConsumer,
		NormEnforcementListener {
	
	/**
	 * Consumer constructor
	 * 
	 * @param id
	 *          Consumer agent identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Consumer configuration
	 * @return none
	 */
	public ConsumerAgent(Integer id, EventSimulator simulator, ConsumerConf conf) {
		super(id, simulator);
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
	}
	
	
	@Override
	public void receiveStateSpreadInformation() {
	}
	
	
	@Override
	public void receiveEntrepreurSpreadInformation() {
	}
	
	
	@Override
	public void receiveIOSpreadInformation() {
	}
	
	
	@Override
	public void consumerSpreadInformation() {
	}
	
	
	@Override
	public void receiveConsumerSpreadInformation() {
	}
	
	
	@Override
	public void decideBuy() {
	}
	
	
	/*******************************
	 * 
	 * Handle communication requests
	 * 
	 *******************************/
	
	@Override
	public Object handleInfo(InfoAbstract info) {
		Object infoRequested = null;
		
		if(info.getType().equals(InfoAbstract.Type.REQUEST)) {
			
			InfoRequest request = (InfoRequest) info;
			switch(request.getInfoRequest()) {
				case Constants.REQUEST_ID:
					infoRequested = this.getId();
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
		}
		
		return infoRequested;
	}
	
	
	// TODO
	@Override
	public void handleObservation(Message msg) {
	}
	
	
	/*******************************
	 * 
	 * Handle normative information
	 * 
	 *******************************/
	
	// TODO
	@Override
	public void receive(NormativeEventEntityAbstract entity,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
	}
	
	
	/*******************************
	 * 
	 * Handle simulation events
	 * 
	 *******************************/
	
	// TODO
	@Override
	public void handleEvent(Event event) {
	}
}