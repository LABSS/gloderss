package gloderss.agents.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import gloderss.Constants;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.InvestigateExtortionAction;
import gloderss.agents.AbstractAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.StateConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.util.random.RandomUtil;

public class StateOrg extends AbstractAgent implements IStateOrg {
	
	private StateConf													conf;
	
	private Map<Integer, PoliceOfficerAgent>	policeOfficers;
	
	private Map<Integer, EntrepreneurAgent>		entrepreneurs;
	
	private List<DenounceExtortionAction>			denounces;
	
	private List<Integer>											mafiosi;
	
	// Police Officer Id, Entrepreneur Id
	private Map<Integer, Integer>							genericInvestigation;
	
	// Police Officer Id, Entrepreneur Id
	private Map<Integer, Integer>							fixedInvestigation;
	
	
	/**
	 * State constructor
	 * 
	 * @param id
	 *          State identification
	 * @param conf
	 *          State configuration
	 * @param entrepreneurs
	 *          List of all entrepreneurs
	 * @return none
	 */
	public StateOrg(Integer id, EventSimulator simulator, StateConf conf,
			Map<Integer, EntrepreneurAgent> entrepreneurs) {
		super(id, simulator);
		this.conf = conf;
		
		// Make Entrepreneur recognize the State identification
		this.entrepreneurs = entrepreneurs;
		for(Integer eId : this.entrepreneurs.keySet()) {
			InfoSet infoSet = new InfoSet(id, eId, Constants.PARAMETER_STATE_ID, id);
			this.sendInfo(infoSet);
		}
		
		PoliceOfficerAgent police;
		int policeId = id + 1;
		this.policeOfficers = new HashMap<Integer, PoliceOfficerAgent>();
		for(int i = 0; i < this.conf.getNumberPoliceOfficers(); i++, policeId++) {
			police = new PoliceOfficerAgent(policeId, simulator, this.conf);
			this.policeOfficers.put(policeId, police);
		}
		
		this.denounces = new ArrayList<DenounceExtortionAction>();
		this.mafiosi = new ArrayList<Integer>();
		this.genericInvestigation = new HashMap<Integer, Integer>();
		this.fixedInvestigation = new HashMap<Integer, Integer>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public StateConf getConf() {
		return this.conf;
	}
	
	
	public Map<Integer, PoliceOfficerAgent> getPoliceOfficers() {
		return this.policeOfficers;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
	}
	
	
	public void initializeStep() {
		this.denounces.clear();
		this.genericInvestigation.clear();
		
		// End investigations
		List<Integer> removeFixed = new ArrayList<Integer>();
		for(int policeId : this.fixedInvestigation.keySet()) {
			InfoRequest info = new InfoRequest(this.getId(), policeId,
					Constants.REQUEST_DURATION);
			int duration = (Integer) this.sendInfo(info);
			if(duration <= 0) {
				removeFixed.add(policeId);
			}
		}
		
		for(Integer policeId : removeFixed) {
			this.fixedInvestigation.remove(policeId);
		}
		
		// Allocate Police Officers
		List<Integer> freePolice = new ArrayList<Integer>();
		freePolice.addAll(this.policeOfficers.keySet());
		freePolice.removeAll(this.fixedInvestigation.keySet());
		
		if(!freePolice.isEmpty()) {
			List<Integer> entrepreneursList = new Vector<Integer>();
			entrepreneursList.addAll(this.entrepreneurs.keySet());
			Collections.shuffle(entrepreneursList);
			
			for(Integer policeId : freePolice) {
				int entrepreneurId = entrepreneursList.get((int) ((entrepreneursList
						.size() - 1) * RandomUtil.nextDouble()));
				
				this.genericInvestigation.put(policeId, entrepreneurId);
				
				InvestigateExtortionAction action = new InvestigateExtortionAction(
						entrepreneurId, -1, 1);
				
				Message msg = new Message(System.currentTimeMillis(), this.id,
						policeId, action);
				this.sendMsg(msg);
			}
		}
		
		// Update all Police Officers list of Mafiosi
		for(Integer policeId : this.policeOfficers.keySet()) {
			InfoSet set = new InfoSet(this.id, policeId,
					Constants.PARAMETER_LIST_MAFIOSI, this.mafiosi);
			this.sendInfo(set);
		}
	}
	
	
	@Override
	public void receiveDenouceExtortion() {
		if(this.hasMsg(DenounceExtortionAction.class)) {
			System.out.println(this.getId() + " DENOUNCE");
			for(Message msg : this.retriveMsgs(DenounceExtortionAction.class)) {
				
				if(msg.getReceiver().contains(this.getId())) {
					this.denounces.add((DenounceExtortionAction) msg.getContent());
				}
			}
		}
	}
	
	
	@Override
	public void decideInvestigateExtortion() {
		List<Integer> polices = new Vector<Integer>();
		polices.addAll(this.genericInvestigation.keySet());
		Collections.shuffle(polices);
		
		for(DenounceExtortionAction action : this.denounces) {
			int mafiosoId = (Integer) action
					.getParam(DenounceExtortionAction.Param.MAFIOSO_ID);
			
			int entrepreneurId = (Integer) action
					.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID);
			
			if(!this.mafiosi.contains(mafiosoId)) {
				this.mafiosi.add(mafiosoId);
			}
			
			if(!polices.isEmpty()) {
				InvestigateExtortionAction investigateAction = new InvestigateExtortionAction(
						entrepreneurId, mafiosoId, this.conf.getIncarcerationDuration());
				
				int policeId = polices.remove((int) ((polices.size() - 1) * RandomUtil
						.nextDouble()));
				
				this.genericInvestigation.remove(policeId);
				this.fixedInvestigation.put(policeId, entrepreneurId);
				
				Message newMsg = new Message(System.currentTimeMillis(), this.getId(),
						policeId, investigateAction);
				this.sendMsg(newMsg);
			}
		}
	}
	
	
	@Override
	public void receiveDenoucePunishment() {
	}
	
	
	@Override
	public void decideInvestigatePunishment() {
	}
	
	
	@Override
	public void receivePentiti() {
	}
	
	
	@Override
	public void decideCollaborationRequest() {
	}
	
	
	@Override
	public void receiveCollaboration() {
	}
	
	
	@Override
	public void decideStatePunishment() {
	}
	
	
	@Override
	public void decideStateCompensation() {
	}
	
	
	@Override
	public void stateSpreadInformation() {
	}
	
	
	@Override
	public void receiveEntrepreurSpreadInformation() {
	}
	
	
	@Override
	public void receiveIOSpreadInformation() {
	}
	
	
	@Override
	public void receiveConsumerSpreadInformation() {
	}
	
	
	/*******************************
	 * 
	 * Handle communication requests
	 * 
	 *******************************/
	
	@Override
	public Object handleInfo(InfoAbstract info) {
		Object infoResult = null;
		
		if(info.getType().equals(InfoAbstract.Type.REQUEST)) {
			
			InfoRequest request = (InfoRequest) info;
			switch(request.getInfoRequest()) {
				case Constants.REQUEST_ID:
					infoResult = this.getId();
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
		}
		
		return infoResult;
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