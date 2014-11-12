package gloderss.agents.intermediaryOrg;

import gloderss.Constants;
import gloderss.agents.AbstractAgent;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import java.util.Map;

public class IntermediaryOrg extends AbstractAgent implements IIntermediaryOrg {
	
	/**
	 * Intermediary Organization constructor
	 * 
	 * @param id
	 *          Intermediary Organization identification
	 * @param simulator
	 *          Event simulator
	 * @param consumers
	 *          List of consumers
	 * @param entrepreneurs
	 *          List of entrepreneurs
	 * @return none
	 */
	public IntermediaryOrg(Integer id, EventSimulator simulator,
			Map<Integer, ConsumerAgent> consumers,
			Map<Integer, EntrepreneurAgent> entrepreneurs) {
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
	public void ioSpreadInformation() {
	}
	
	
	@Override
	public void receiveConsumerSpreadInformation() {
	}
	
	
	@Override
	public void receiveAffiliation() {
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
	 * Handle simulation events
	 * 
	 *******************************/
	
	// TODO
	@Override
	public void handleEvent(Event event) {
	}
}