package gloderss.agents.state;

import gloderss.Constants;
import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollectAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.ReleaseInvestigationAction;
import gloderss.actions.SpecificInvestigationAction;
import gloderss.agents.AbstractAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.StateConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.List;

public class PoliceOfficerAgent extends AbstractAgent implements IPoliceOfficer {
	
	private StateConf			conf;
	
	private int						stateId;
	
	private PDFAbstract		generalInvestigationDurationPDF;
	
	private PDFAbstract		bureaucraticActivityDurationPDF;
	
	private PDFAbstract		specificInvestigationDurationPDF;
	
	private int						observed;
	
	private List<Integer>	mafiosi;
	
	private Event					event;
	
	private int						extortionId;
	
	private boolean				specificInvestigation;
	
	
	/**
	 * Police constructor
	 * 
	 * @param id
	 *          Police officer agent identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Police officer configuration
	 * @return none
	 */
	public PoliceOfficerAgent(Integer id, EventSimulator simulator,
			StateConf conf, int stateId) {
		super(id, simulator);
		
		this.conf = conf;
		
		this.stateId = stateId;
		
		this.generalInvestigationDurationPDF = PDFAbstract.getInstance(conf
				.getGeneralInvestigationDurationPDF());
		
		this.bureaucraticActivityDurationPDF = PDFAbstract.getInstance(conf
				.getBureaucraticActivityDurationPDF());
		
		this.specificInvestigationDurationPDF = PDFAbstract.getInstance(conf
				.getSpecificInvestigationDurationPDF());
		
		this.observed = -1;
		
		this.mafiosi = new ArrayList<Integer>();
		
		this.event = null;
		
		this.specificInvestigation = false;
		this.extortionId = -1;
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public int getObserved() {
		return this.observed;
	}
	
	
	public void setObserved(int observed) {
		this.observed = observed;
	}
	
	
	public List<Integer> getMafiosi() {
		return this.mafiosi;
	}
	
	
	public void setMafiosi(List<Integer> mafiosi) {
		this.mafiosi = mafiosi;
	}
	
	
	public void addMafioso(int mafioso) {
		if(!this.mafiosi.contains(mafioso)) {
			this.mafiosi.add(mafioso);
		}
	}
	
	
	public void removeMafioso(int mafioso) {
		if(this.mafiosi.contains(mafioso)) {
			this.mafiosi.remove(mafioso);
		}
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		this.specificInvestigation = false;
		
		this.event = new Event(this.simulator.now() + 1, this,
				Constants.EVENT_GENERAL_INVESTIGATION);
		this.simulator.insert(this.event);
	}
	
	
	@Override
	public void generalInvestigation() {
		
		// Release specific investigation
		if(this.specificInvestigation) {
			ReleaseInvestigationAction action = new ReleaseInvestigationAction(
					this.id, this.observed);
			
			Message msg = new Message(this.simulator.now(), this.id, this.stateId,
					action);
			this.sendMsg(msg);
			
			this.specificInvestigation = false;
			this.extortionId = -1;
		}
		
		if((this.observed != -1)) {
			this.removeObservation(this.id, this.observed);
			this.observed = -1;
		}
		
		// Get one target with the Mafia Organization
		InfoRequest entrepreneurRequest = new InfoRequest(this.id, this.stateId,
				Constants.REQUEST_ENTREPRENEUR_ID);
		this.observed = (int) this.sendInfo(entrepreneurRequest);
		
		this.addObservation(this.id, this.observed);
		
		this.event = new Event(this.simulator.now()
				+ this.bureaucraticActivityDurationPDF.nextValue(), this,
				Constants.EVENT_BUROCRATIC_ACTIVITY);
		this.simulator.insert(this.event);
	}
	
	
	@Override
	public void specificInvestigation(SpecificInvestigationAction action) {
		
		if(this.event != null) {
			this.simulator.cancel(this.event);
		}
		
		if((this.observed != -1)) {
			this.removeObservation(this.id, this.observed);
		}
		
		this.extortionId = (int) action
				.getParam(SpecificInvestigationAction.Param.EXTORTION_ID);
		
		this.observed = (int) action
				.getParam(SpecificInvestigationAction.Param.ENTREPRENEUR_ID);
		
		this.addObservation(this.id, this.observed);
		
		this.event = new Event(this.simulator.now()
				+ this.specificInvestigationDurationPDF.nextValue(), this,
				Constants.EVENT_GENERAL_INVESTIGATION);
		this.simulator.insert(this.event);
		
		this.specificInvestigation = true;
	}
	
	
	/**
	 * Decides to capture observed Mafioso
	 * 
	 * @param mafiosoId
	 *          Mafioso identification
	 * @return none
	 */
	private void captureMafioso(int mafiosoId) {
		
		if(RandomUtil.nextDouble() < this.conf.getCaptureProbability()) {
			
			CaptureMafiosoAction action;
			if(this.specificInvestigation) {
				action = new CaptureMafiosoAction(this.extortionId, this.id, mafiosoId,
						true);
			} else {
				action = new CaptureMafiosoAction(-1, this.id, mafiosoId, false);
			}
			
			Message msg = new Message(this.simulator.now(), this.id, this.stateId,
					action);
			this.sendMsg(msg);
		}
	}
	
	
	/**
	 * Waits a period of time before initiating a new general investigation
	 * 
	 * @param none
	 * @return none
	 */
	private void burocraticActivity() {
		this.event = new Event(this.simulator.now()
				+ this.generalInvestigationDurationPDF.nextValue(), this,
				Constants.EVENT_GENERAL_INVESTIGATION);
		this.simulator.insert(this.event);
		
	}
	
	
	@Override
	public void finalizeSim() {
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
			
			if(content instanceof SpecificInvestigationAction) {
				this.specificInvestigation((SpecificInvestigationAction) content);
				
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
					infoRequested = this.getId();
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
			InfoSet set = (InfoSet) info;
			int mafioso;
			switch(set.getParameter()) {
				case Constants.PARAMETER_ADD_MAFIOSO:
					mafioso = (int) set.getValue();
					this.addMafioso(mafioso);
					break;
				case Constants.PARAMETER_REMOVE_MAFIOSO:
					mafioso = (int) set.getValue();
					this.removeMafioso(mafioso);
					break;
			}
		}
		
		return infoRequested;
	}
	
	
	@Override
	public void handleObservation(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (!msg.getReceiver().contains(this.id))) {
			
			// Collect
			if(content instanceof CollectAction) {
				CollectAction action = (CollectAction) content;
				int mafiosoId = (int) action.getParam(CollectAction.Param.MAFIOSO_ID);
				this.captureMafioso(mafiosoId);
				
				// Exortion
			} else if(content instanceof ExtortionAction) {
				ExtortionAction action = (ExtortionAction) content;
				int mafiosoId = (int) action.getParam(ExtortionAction.Param.MAFIOSO_ID);
				this.captureMafioso(mafiosoId);
				
				// Mafia Punishment
			} else if(content instanceof MafiaPunishmentAction) {
				MafiaPunishmentAction action = (MafiaPunishmentAction) content;
				int mafiosoId = (int) action
						.getParam(MafiaPunishmentAction.Param.MAFIOSO_ID);
				this.captureMafioso(mafiosoId);
				
			}
		}
	}
	
	
	/*******************************
	 * 
	 * Handle simulation events
	 * 
	 *******************************/
	
	@Override
	public void handleEvent(Event event) {
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_GENERAL_INVESTIGATION:
				this.generalInvestigation();
				break;
			case Constants.EVENT_BUROCRATIC_ACTIVITY:
				this.burocraticActivity();
				break;
		}
	}
}