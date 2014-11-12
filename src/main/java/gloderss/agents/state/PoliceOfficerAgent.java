package gloderss.agents.state;

import gloderss.Constants;
import gloderss.actions.InvestigateExtortionAction;
import gloderss.agents.AbstractAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.StateConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import java.util.ArrayList;
import java.util.List;

public class PoliceOfficerAgent extends AbstractAgent implements IPoliceOfficer {
	
	private Integer				observed;
	
	private Integer				duration;
	
	private List<Integer>	mafiosi;
	
	
	/**
	 * Police constructor
	 * 
	 * @param id
	 *          Police agent identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Police officer configuration
	 * @return none
	 */
	public PoliceOfficerAgent(Integer id, EventSimulator simulator, StateConf conf) {
		super(id, simulator);
		
		this.observed = null;
		this.duration = 0;
		this.mafiosi = new ArrayList<Integer>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public Integer getObserved() {
		return this.observed;
	}
	
	
	public void setObserved(Integer observed) {
		this.observed = observed;
	}
	
	
	public Integer getDuration() {
		return this.duration;
	}
	
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	
	public List<Integer> getMafiosi() {
		return this.mafiosi;
	}
	
	
	public void setMafiosi(List<Integer> mafiosi) {
		this.mafiosi = mafiosi;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
	}
	
	
	@Override
	public void receiveInvestigateExtortion() {
		if(this.hasMsg(InvestigateExtortionAction.class)) {
			for(Message msg : this.retriveMsgs(InvestigateExtortionAction.class)) {
				
				if(msg.getReceiver().contains(this.getId())) {
					
					InvestigateExtortionAction action = (InvestigateExtortionAction) msg
							.getContent();
					
					if((this.observed == null) && (this.duration > 0)) {
						this.observed = (Integer) action
								.getParam(InvestigateExtortionAction.Param.ENTREPRENEUR_ID);
						
						this.duration = (Integer) action
								.getParam(InvestigateExtortionAction.Param.INVESTIGATIVE_DURATION);
						
						this.addObservation(this.getId(), observed);
						
						this.mafiosi.add((Integer) action
								.getParam(InvestigateExtortionAction.Param.MAFIOSO_ID));
					}
				}
			}
		}
	}
	
	
	@Override
	public void receiveInvestigatePunishment() {
	}
	
	
	public void endStep() {
		this.duration--;
		if(this.duration <= 0) {
			this.removeObservation(this.getId(), this.observed);
			this.observed = null;
			this.duration = 0;
		}
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
				case Constants.REQUEST_DURATION:
					infoRequested = this.getDuration();
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
			InfoSet set = (InfoSet) info;
			switch(set.getParameter()) {
				case Constants.PARAMETER_LIST_MAFIOSI:
					List<Integer> mList = (List<Integer>) set.getValue();
					this.mafiosi.addAll(mList);
					break;
			}
		}
		
		return infoRequested;
	}
	
	
	// TODO
	@Override
	public void handleObservation(Message msg) {
		System.out.println(msg);
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