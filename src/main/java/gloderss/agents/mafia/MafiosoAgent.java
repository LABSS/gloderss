package gloderss.agents.mafia;

import gloderss.Constants;
import gloderss.actions.CollectAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.ImprisonmentAction.Param;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitiAction;
import gloderss.actions.ReleaseCustodyAction;
import gloderss.agents.AbstractAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.MafiaConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.ExtortionOutputEntity.Field;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MafiosoAgent extends AbstractAgent implements IMafioso {
	
	private MafiaConf							conf;
	
	private PDFAbstract						demandPDF;
	
	private PDFAbstract						collectionPDF;
	
	private int										mafiaId;
	
	private int										stateId;
	
	private List<Integer>					neighbors;
	
	private Map<Integer, Double>	benefits;
	
	private double								wealth;
	
	private boolean								pentito;
	
	private boolean								custodyStatus;
	
	private boolean								prisonStatus;
	
	private Event									event;
	
	private ExtortionAction				demand;
	
	private List<Integer>					payingEntrepreneurs;
	
	
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
		
		this.demandPDF = PDFAbstract.getInstance(conf.getDemandPDF());
		this.collectionPDF = PDFAbstract.getInstance(conf.getCollectionPDF());
		
		this.mafiaId = mafiaId;
		this.stateId = stateId;
		
		this.neighbors = new ArrayList<Integer>();
		
		this.wealth = this.conf.getWealth();
		
		this.pentito = false;
		this.custodyStatus = false;
		this.prisonStatus = false;
		this.benefits = new HashMap<Integer, Double>();
		this.payingEntrepreneurs = new ArrayList<Integer>();
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
	
	
	public double getWeath() {
		return this.wealth;
	}
	
	
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public boolean getCustodyStatus() {
		return this.custodyStatus;
	}
	
	
	public void setCustodyStatus(boolean custodyStatus) {
		this.custodyStatus = custodyStatus;
	}
	
	
	public boolean getPrisonStatus() {
		return this.prisonStatus;
	}
	
	
	public void setPrisonStatus(boolean prisonStatus) {
		this.prisonStatus = prisonStatus;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		this.event = new Event(this.simulator.now() + this.demandPDF.nextValue(),
				this, Constants.EVENT_EXTORTION_DEMAND);
		this.simulator.insert(this.event);
	}
	
	
	@Override
	public void decideExtortion() {
		
		if((!this.prisonStatus) && (!this.custodyStatus) && (!this.pentito)) {
			
			// Select a none IO affiliated Entrepreneur to extort
			int targetId = -1;
			boolean affiliated = false;
			do {
				// Get one target with the Mafia Organization
				InfoRequest targetRequest = new InfoRequest(this.id, this.mafiaId,
						Constants.REQUEST_TARGET_ID);
				targetId = (int) this.sendInfo(targetRequest);
				
				InfoRequest affiliationRequest = new InfoRequest(this.id, targetId,
						Constants.REQUEST_AFFILIATION);
				affiliated = (boolean) this.sendInfo(affiliationRequest);
				
			} while((affiliated)
					&& (RandomUtil.nextDouble() < this.conf
							.getDemandAffiliatedProbability()));
			
			double wage = 0.0;
			InfoRequest wageRequest = new InfoRequest(this.id, targetId,
					Constants.REQUEST_DEFAULT_WAGE);
			wage = (double) this.sendInfo(wageRequest);
			
			double extortion = wage * this.conf.getExtortionLevel();
			double punishment = wage * this.conf.getPunishmentSeverity();
			
			double benefit = 0.0;
			if(this.benefits.containsKey(targetId)) {
				benefit = wage * this.benefits.get(targetId);
			}
			
			// Obtains an Extortion Identification
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.EXTORTION);
			outputEntity.setValue(Field.CYCLE.name(), 0);
			outputEntity.setValue(Field.MAFIOSO_ID.name(), this.id);
			outputEntity.setValue(Field.ENTREPRENEUR_ID.name(), targetId);
			outputEntity.setValue(Field.MAFIA_EXTORTION.name(), extortion);
			outputEntity.setValue(Field.MAFIA_PUNISHMENT.name(), punishment);
			outputEntity.setValue(Field.MAFIA_BENEFIT.name(), benefit);
			
			int extortionId = (int) outputEntity.getValue(Field.EXTORTION_ID.name());
			
			// Send an extortion demand to a victim
			this.demand = new ExtortionAction(extortionId, this.id, targetId,
					extortion, punishment, benefit);
			
			Message msg = new Message(this.simulator.now(), this.id, targetId,
					this.demand);
			this.sendMsg(msg);
			
			// Schedule the collection of extortion
			this.event = new Event(this.simulator.now()
					+ this.collectionPDF.nextValue(), this,
					Constants.EVENT_COLLECT_EXTORTION);
			this.simulator.insert(this.event);
		}
	}
	
	
	@Override
	public void collectExtortion() {
		
		if(this.demand != null) {
			
			int extortionId = (int) this.demand
					.getParam(ExtortionAction.Param.EXTORTION_ID);
			
			int targetId = (int) this.demand
					.getParam(ExtortionAction.Param.VICTIM_ID);
			
			double extortion = (double) this.demand
					.getParam(ExtortionAction.Param.EXTORTION);
			
			CollectAction collect = new CollectAction(extortionId, this.id, targetId,
					extortion);
			
			Message msg = new Message(this.simulator.now(), this.id, targetId,
					collect);
			this.sendMsg(msg);
			
			// Schedule the next demand extortion
			this.event = new Event(this.simulator.now() + this.demandPDF.nextValue(),
					this, Constants.EVENT_EXTORTION_DEMAND);
			this.simulator.insert(this.event);
		}
	}
	
	
	@Override
	public void receivePayment(PayExtortionAction action) {
		
		int extortionId = (int) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		int targetId = (int) action.getParam(PayExtortionAction.Param.VICTIM_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		int mafiosoId = (int) action.getParam(PayExtortionAction.Param.MAFIOSO_ID);
		
		if(mafiosoId == this.id) {
			this.wealth += (Double) action
					.getParam(PayExtortionAction.Param.EXTORTION);
			
			outputEntity.setValue(Field.MAFIA_PUNISHED.name(), false);
			
			this.payingEntrepreneurs.add(targetId);
			
			this.decideBenefit(action);
		}
	}
	
	
	@Override
	public void decidePunishment(NotPayExtortionAction action) {
		
		if(!this.prisonStatus) {
			int extortionId = (int) action
					.getParam(NotPayExtortionAction.Param.EXTORTION_ID);
			
			int victimId = (int) action
					.getParam(NotPayExtortionAction.Param.VICTIM_ID);
			
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.EXTORTION, extortionId);
			
			// Probability to punish
			if(RandomUtil.nextDouble() < this.conf.getPunishmentProbability()) {
				
				MafiaPunishmentAction punish = new MafiaPunishmentAction(extortionId,
						this.id,
						(double) this.demand.getParam(ExtortionAction.Param.PUNISHMENT));
				
				Message msg = new Message(this.simulator.now(), this.id, victimId,
						punish);
				this.sendMsg(msg);
				
				outputEntity.setValue(Field.MAFIA_PUNISHED.name(), true);
			} else {
				outputEntity.setValue(Field.MAFIA_PUNISHED.name(), false);
			}
		}
	}
	
	
	@Override
	public void decideBenefit(PayExtortionAction action) {
		
		int extortionId = (int) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		int victimId = (int) action.getParam(PayExtortionAction.Param.VICTIM_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		double benefitAmount = (double) this.demand
				.getParam(ExtortionAction.Param.BENEFIT);
		
		this.wealth -= benefitAmount;
		
		MafiaBenefitAction benefit = new MafiaBenefitAction(extortionId, this.id,
				benefitAmount);
		
		Message msg = new Message(this.simulator.now(), this.id, victimId, benefit);
		this.sendMsg(msg);
		
		outputEntity.setValue(Field.MAFIA_BENEFITED.name(), true);
	}
	
	
	@Override
	public void custody(CustodyAction action) {
		this.custodyStatus = true;
		
		this.simulator.cancel(this.event);
	}
	
	
	@Override
	public void releaseCustody() {
		this.custodyStatus = false;
		
		if((!this.pentito) && (!this.prisonStatus)) {
			// Schedule the next extortion demand
			this.event = new Event(this.simulator.now() + this.demandPDF.nextValue(),
					this, Constants.EVENT_EXTORTION_DEMAND);
			this.simulator.insert(this.event);
		}
	}
	
	
	@Override
	public void imprisonment(ImprisonmentAction action) {
		this.prisonStatus = true;
		
		if(this.custodyStatus) {
			this.custodyStatus = false;
		}
		
		double duration = (double) action.getParam(Param.DURATION);
		
		// Schedule the next extortion demand
		Event event = new Event(this.simulator.now() + duration, this,
				Constants.EVENT_RELEASE_PRISON);
		this.simulator.insert(event);
		
		this.decidePentito();
	}
	
	
	@Override
	public void releasePrison() {
		this.prisonStatus = false;
		
		if(!this.pentito) {
			// Schedule the next extortion demand
			Event event = new Event(
					this.simulator.now() + this.demandPDF.nextValue(), this,
					Constants.EVENT_EXTORTION_DEMAND);
			this.simulator.insert(event);
		}
	}
	
	
	@Override
	public void decidePentito() {
		if(RandomUtil.nextDouble() < this.conf.getPentitiProbability()) {
			
			this.pentito = true;
			PentitiAction pentiti = new PentitiAction(this.id, this.neighbors,
					this.payingEntrepreneurs);
			
			this.payingEntrepreneurs.clear();
			
			Message msg = new Message(this.simulator.now(), this.id, stateId, pentiti);
			this.sendMsg(msg);
		}
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
				
			} else if(content instanceof CustodyAction) {
				this.custody((CustodyAction) content);
				
			} else if(content instanceof ReleaseCustodyAction) {
				this.releaseCustody();
				
			} else if(content instanceof ImprisonmentAction) {
				this.imprisonment((ImprisonmentAction) content);
				
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
				case Constants.REQUEST_WEALTH:
					infoRequested = this.wealth;
					break;
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
			InfoSet set = (InfoSet) info;
			switch(set.getParameter()) {
				case Constants.PARAMETER_WEALTH:
					this.wealth = (double) set.getValue();
					break;
			}
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
			case Constants.EVENT_EXTORTION_DEMAND:
				this.decideExtortion();
				break;
			case Constants.EVENT_COLLECT_EXTORTION:
				this.collectExtortion();
				break;
			case Constants.EVENT_RELEASE_CUSTODY:
				this.releaseCustody();
				break;
			case Constants.EVENT_RELEASE_PRISON:
				this.releasePrison();
				break;
		}
	}
}