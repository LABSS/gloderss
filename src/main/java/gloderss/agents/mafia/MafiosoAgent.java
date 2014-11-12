package gloderss.agents.mafia;

import gloderss.Constants;
import gloderss.actions.ExtortionAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitiAction;
import gloderss.agents.AbstractAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.MafiaConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.ExtortionOutputEntity.Field;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MafiosoAgent extends AbstractAgent implements IMafioso {
	
	private MafiaConf							conf;
	
	private int										mafiaId;
	
	private int										stateId;
	
	private List<Integer>					neighbors;
	
	private Map<Integer, Double>	benefits;
	
	private double								wealth;
	
	private boolean								prisonStatus;
	
	
	/**
	 * Mafioso constructor
	 * 
	 * @param id
	 *          Mafioso agent identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Mafioso configuration
	 * @param mafiaId
	 *          Mafia organization identification
	 * @param stateId
	 *          State organization identification
	 * @return none
	 */
	public MafiosoAgent(int id, EventSimulator simulator, MafiaConf conf,
			int mafiaId, int stateId) {
		super(id, simulator);
		this.conf = conf;
		this.mafiaId = mafiaId;
		this.stateId = stateId;
		
		this.neighbors = new ArrayList<Integer>();
		
		this.wealth = this.conf.getWealth();
		
		this.prisonStatus = false;
		this.benefits = new HashMap<Integer, Double>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public List<Integer> getNeighbors() {
		return this.neighbors;
	}
	
	
	public void setNeighbors(List<Integer> neighbors) {
		this.neighbors = neighbors;
	}
	
	
	public Map<Integer, Double> getBenefits() {
		return this.benefits;
	}
	
	
	public void setBenefits(Map<Integer, Double> benefits) {
		this.benefits = benefits;
	}
	
	
	public Double getWeath() {
		return this.wealth;
	}
	
	
	public void setWealth(Double wealth) {
		this.wealth = wealth;
	}
	
	
	public Boolean getPrisonStatus() {
		return this.prisonStatus;
	}
	
	
	public void setPrisonStatus(Boolean prisonStatus) {
		this.prisonStatus = prisonStatus;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		Event event = new Event(this.simulator.now()
				+ RandomUtil.nextNormalInt(this.conf.getDemandMean(),
						this.conf.getDemandStDev()), this, Constants.EVENT_EXTORTION_DEMAND);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void decideExtortion() {
		
		// Get one target with the Mafia Organization
		InfoRequest request = new InfoRequest(this.id, this.mafiaId,
				Constants.REQUEST_TARGET_ID);
		
		int targetId = (Integer) this.sendInfo(request);
		
		double wage = 0.0;
		InfoRequest wageRequest = new InfoRequest(this.id, targetId,
				Constants.REQUEST_DEFAULT_WAGE);
		Object infoRequested = this.sendInfo(wageRequest);
		if(infoRequested != null) {
			wage = (Double) infoRequested;
		}
		
		double extortion = wage * this.conf.getExtortionLevel();
		double punishment = wage * this.conf.getPunishmentSeverity();
		
		double benefit = 0.0;
		if(this.benefits.containsKey(targetId)) {
			benefit = wage * this.benefits.get(targetId);
		}
		
		// Obtains an Extortion Identification
		AbstractEntity outputEntity = OutputController
				.getEntity(EntityType.EXTORTION);
		outputEntity.setValue(Field.CYCLE.name(), 0);
		outputEntity.setValue(Field.MAFIOSO_ID.name(), this.id);
		outputEntity.setValue(Field.ENTREPRENEUR_ID.name(), targetId);
		outputEntity.setValue(Field.MAFIA_EXTORTION.name(), extortion);
		outputEntity.setValue(Field.MAFIA_PUNISHMENT.name(), punishment);
		outputEntity.setValue(Field.MAFIA_BENEFIT.name(), benefit);
		
		int extortionId = (int) outputEntity.getValue(Field.EXTORTION_ID.name());
		
		// Send an extortion demand to a victim
		ExtortionAction demand = new ExtortionAction(extortionId, this.getId(),
				targetId, extortion, punishment, benefit);
		
		Message msg = new Message(System.currentTimeMillis(), this.getId(),
				targetId, demand);
		this.sendMsg(msg);
		
		// Schedule the next extortion demand
		Event event = new Event(this.simulator.now()
				+ RandomUtil.nextNormalInt(this.conf.getDemandMean(),
						this.conf.getDemandStDev()), this, Constants.EVENT_EXTORTION_DEMAND);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void receivePayment(PayExtortionAction action) {
		
		int extortionId = (Integer) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getEntity(
				EntityType.EXTORTION, extortionId);
		
		int mafiosoId = (Integer) action
				.getParam(PayExtortionAction.Param.MAFIOSO_ID);
		
		if(mafiosoId == this.id) {
			this.wealth += (Double) action
					.getParam(PayExtortionAction.Param.EXTORTION);
			
			outputEntity.setValue(Field.MAFIA_PUNISHED.name(), false);
			
			this.decideBenefit(action);
		}
	}
	
	
	@Override
	public void decidePunishment(NotPayExtortionAction action) {
		
		int extortionId = (Integer) action
				.getParam(NotPayExtortionAction.Param.EXTORTION_ID);
		
		int victimId = (Integer) action
				.getParam(NotPayExtortionAction.Param.VICTIM_ID);
		
		AbstractEntity outputEntity = OutputController.getEntity(
				EntityType.EXTORTION, extortionId);
		
		// Probability to punish
		if(RandomUtil.nextDouble() < this.conf.getPunishmentProbability()) {
			
			MafiaPunishmentAction punish = new MafiaPunishmentAction(extortionId);
			
			Message msg = new Message(System.currentTimeMillis(), this.getId(),
					victimId, punish);
			this.sendMsg(msg);
			
			outputEntity.setValue(Field.MAFIA_PUNISHED.name(), true);
		} else {
			outputEntity.setValue(Field.MAFIA_PUNISHED.name(), false);
		}
	}
	
	
	@Override
	public void decideBenefit(PayExtortionAction action) {
		
		int extortionId = (Integer) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		int victimId = (Integer) action
				.getParam(PayExtortionAction.Param.VICTIM_ID);
		
		AbstractEntity outputEntity = OutputController.getEntity(
				EntityType.EXTORTION, extortionId);
		
		MafiaBenefitAction benefit = new MafiaBenefitAction(extortionId);
		
		Message msg = new Message(this.simulator.now(), this.getId(), victimId,
				benefit);
		this.sendMsg(msg);
		
		outputEntity.setValue(Field.MAFIA_BENEFITED.name(), true);
	}
	
	
	@Override
	public void decidePentiti() {
		if(RandomUtil.nextDouble() < this.conf.getPentitiProbability()) {
			
			PentitiAction pentiti = new PentitiAction(this.getId(), this.neighbors);
			
			Message msg = new Message(System.currentTimeMillis(), this.getId(),
					stateId, pentiti);
			this.sendMsg(msg);
		}
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
	public void receiveConsumerSpreadInformation() {
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
			
			if(content instanceof PayExtortionAction) {
				this.receivePayment((PayExtortionAction) content);
				
			} else if(content instanceof NotPayExtortionAction) {
				this.decidePunishment((NotPayExtortionAction) content);
				
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
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_EXTORTION_DEMAND:
				this.decideExtortion();
				break;
		}
	}
}