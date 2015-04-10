package gloderss.agents.mafia;

import gloderss.Constants;
import gloderss.actions.CollectAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReleaseCustodyAction;
import gloderss.actions.ReleaseImprisonmentAction;
import gloderss.agents.AbstractAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.MafiaConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.ExtortionOutputEntity;
import gloderss.output.MafiosiOutputEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MafiosoAgent extends AbstractAgent implements IMafioso {
	
	private PDFAbstract						demandPDF;
	
	private PDFAbstract						collectionPDF;
	
	private double								demandAffiliatedProbability;
	
	private double								extortionLevel;
	
	private double								punishmentSeverity;
	
	private double								punishmentProbability;
	
	private double								pentitiProbability;
	
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
	
	private double								nextCollection;
	
	private int										outputId;
	
	
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
		
		this.demandPDF = PDFAbstract.getInstance(conf.getDemandPDF());
		this.collectionPDF = PDFAbstract.getInstance(conf.getCollectionPDF());
		
		this.demandAffiliatedProbability = conf.getDemandAffiliatedProbability();
		
		this.extortionLevel = conf.getExtortionLevel();
		
		this.punishmentSeverity = conf.getPunishmentSeverity();
		
		this.punishmentProbability = conf.getPunishmentProbability();
		
		this.pentitiProbability = conf.getPentitiProbability();
		
		this.mafiaId = mafiaId;
		this.stateId = stateId;
		
		this.neighbors = new ArrayList<Integer>();
		
		this.wealth = conf.getWealth();
		
		this.pentito = false;
		this.custodyStatus = false;
		this.prisonStatus = false;
		this.benefits = new HashMap<Integer, Double>();
		this.payingEntrepreneurs = new ArrayList<Integer>();
		this.nextCollection = 0.0;
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public PDFAbstract getDemandPDF() {
		return this.demandPDF;
	}
	
	
	public void setDemandPDF(String demandPDF) {
		this.demandPDF = PDFAbstract.getInstance(demandPDF);
	}
	
	
	public PDFAbstract getCollectionPDF() {
		return this.collectionPDF;
	}
	
	
	public void setCollectionPDF(String collectionPDF) {
		this.collectionPDF = PDFAbstract.getInstance(collectionPDF);
	}
	
	
	public double getDemandAffiliatedProbability() {
		return this.demandAffiliatedProbability;
	}
	
	
	public void setDemandAffiliatedProbability(double demandAffiliatedProbability) {
		this.demandAffiliatedProbability = demandAffiliatedProbability;
	}
	
	
	public double getExtortionLevel() {
		return this.extortionLevel;
	}
	
	
	public void setExtortionLevel(double extortionLevel) {
		this.extortionLevel = extortionLevel;
	}
	
	
	public double getPunishmentSeverity() {
		return this.punishmentSeverity;
	}
	
	
	public void setPunishmentSeverity(double punishmentSeverity) {
		this.punishmentSeverity = punishmentSeverity;
	}
	
	
	public double getPunishmentProbability() {
		return this.punishmentProbability;
	}
	
	
	public void setPunishmentProbability(double punishmentProbability) {
		this.punishmentProbability = punishmentProbability;
	}
	
	
	public double getPentitiProbability() {
		return this.pentitiProbability;
	}
	
	
	public void setPentitiProbability(double pentitiProbability) {
		this.pentitiProbability = pentitiProbability;
	}
	
	
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
	
	
	public boolean getPentitoStatus() {
		return this.pentito;
	}
	
	
	public void setPentitoStatus(boolean pentito) {
		this.pentito = pentito;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		Event event = new Event(this.demandPDF.nextValue(), this,
				Constants.EVENT_DEMAND_EXTORTION);
		this.simulator.insert(event);
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
					&& (RandomUtil.nextDouble() < this.demandAffiliatedProbability));
			
			double wage = 0.0;
			InfoRequest wageRequest = new InfoRequest(this.id, targetId,
					Constants.REQUEST_DEFAULT_WAGE);
			wage = (double) this.sendInfo(wageRequest);
			
			double extortion = wage * this.extortionLevel;
			double punishment = wage * this.punishmentSeverity;
			
			double benefit = 0.0;
			if(this.benefits.containsKey(targetId)) {
				benefit = wage * this.benefits.get(targetId);
			}
			
			// Obtains an Extortion Identification
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.EXTORTION);
			outputEntity.setValue(ExtortionOutputEntity.Field.TIME.name(),
					this.simulator.now());
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIOSO_ID.name(),
					this.id);
			outputEntity.setValue(ExtortionOutputEntity.Field.ENTREPRENEUR_ID.name(),
					targetId);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.ENTREPRENEUR_WAGE.name(), wage);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.ENTREPRENEUR_AFFILIATED.name(),
					affiliated);
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_EXTORTION.name(),
					extortion);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.MAFIA_PUNISHMENT.name(), punishment);
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_BENEFIT.name(),
					benefit);
			
			int extortionId = (int) outputEntity
					.getValue(ExtortionOutputEntity.Field.EXTORTION_ID.name());
			
			// Send an extortion demand to a victim
			this.demand = new ExtortionAction(extortionId, this.id, targetId,
					extortion, punishment, benefit);
			
			Message msg = new Message(this.simulator.now(), this.id, targetId,
					this.demand);
			this.sendMsg(msg);
			
			// Schedule the collection of extortion
			this.nextCollection = this.simulator.now()
					+ this.collectionPDF.nextValue();
			
			this.event = new Event(this.nextCollection, this,
					Constants.EVENT_COLLECT_EXTORTION);
			this.simulator.insert(this.event);
		}
	}
	
	
	@Override
	public void collectExtortion() {
		
		if((this.demand != null) && (!this.prisonStatus) && (!this.custodyStatus)
				&& (!this.pentito)) {
			
			int extortionId = (int) this.demand
					.getParam(ExtortionAction.Param.EXTORTION_ID);
			
			int targetId = (int) this.demand
					.getParam(ExtortionAction.Param.ENTREPRENEUR_ID);
			
			double extortion = (double) this.demand
					.getParam(ExtortionAction.Param.EXTORTION);
			
			CollectAction collect = new CollectAction(extortionId, this.id, targetId,
					extortion);
			
			Message msg = new Message(this.simulator.now(), this.id, targetId,
					collect);
			this.sendMsg(msg);
			
			// Schedule the next demand extortion
			this.event = new Event(this.nextCollection + this.demandPDF.nextValue(),
					this, Constants.EVENT_DEMAND_EXTORTION);
			this.simulator.insert(this.event);
		}
	}
	
	
	@Override
	public void receivePayment(PayExtortionAction action) {
		
		int extortionId = (int) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		int targetId = (int) action
				.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		int mafiosoId = (int) action.getParam(PayExtortionAction.Param.MAFIOSO_ID);
		
		if(mafiosoId == this.id) {
			this.wealth += (Double) action
					.getParam(PayExtortionAction.Param.EXTORTION);
			
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_PUNISHED.name(),
					false);
			
			this.payingEntrepreneurs.add(targetId);
			
			this.decideBenefit(action);
		}
	}
	
	
	@Override
	public void decidePunishment(NotPayExtortionAction action) {
		
		if(!this.prisonStatus) {
			int extortionId = (int) action
					.getParam(NotPayExtortionAction.Param.EXTORTION_ID);
			
			int entrepreneurId = (int) action
					.getParam(NotPayExtortionAction.Param.ENTREPRENEUR_ID);
			
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.EXTORTION, extortionId);
			
			// Probability to punish
			if(RandomUtil.nextDouble() < this.punishmentProbability) {
				
				MafiaPunishmentAction punish = new MafiaPunishmentAction(extortionId,
						entrepreneurId, this.id,
						(double) this.demand.getParam(ExtortionAction.Param.PUNISHMENT));
				
				Message msg = new Message(this.simulator.now(), this.id,
						entrepreneurId, punish);
				this.sendMsg(msg);
				
				outputEntity.setValue(
						ExtortionOutputEntity.Field.MAFIA_PUNISHED.name(), true);
			} else {
				outputEntity.setValue(
						ExtortionOutputEntity.Field.MAFIA_PUNISHED.name(), false);
			}
		}
	}
	
	
	@Override
	public void decideBenefit(PayExtortionAction action) {
		
		int extortionId = (int) action
				.getParam(PayExtortionAction.Param.EXTORTION_ID);
		
		int entrepreneurId = (int) action
				.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		double benefitAmount = (double) this.demand
				.getParam(ExtortionAction.Param.BENEFIT);
		if(this.wealth < benefitAmount) {
			benefitAmount = this.wealth;
		}
		
		this.wealth -= benefitAmount;
		
		MafiaBenefitAction benefit = new MafiaBenefitAction(extortionId,
				entrepreneurId, this.id, benefitAmount);
		
		Message msg = new Message(this.simulator.now(), this.id, entrepreneurId,
				benefit);
		this.sendMsg(msg);
		
		outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_BENEFITED.name(),
				true);
		outputEntity.setActive();
	}
	
	
	@Override
	public void custody(CustodyAction action) {
		int mafiosoId = (int) action.getParam(CustodyAction.Param.MAFIOSO_ID);
		
		if(this.id == mafiosoId) {
			this.custodyStatus = true;
			
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.MAFIOSI);
			this.outputId = outputEntity.getEntityId();
			
			outputEntity.setValue(MafiosiOutputEntity.Field.MAFIOSO_ID.name(),
					this.id);
			outputEntity.setValue(MafiosiOutputEntity.Field.CUSTODY_TIME.name(),
					this.simulator.now());
			
			this.simulator.cancel(this.event);
		}
	}
	
	
	@Override
	public void releaseCustody() {
		this.custodyStatus = false;
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.MAFIOSI, this.outputId);
		
		outputEntity.setValue(MafiosiOutputEntity.Field.TIME.name(),
				(int) this.simulator.now());
		outputEntity.setValue(
				MafiosiOutputEntity.Field.RELEASE_CUSTODY_TIME.name(),
				this.simulator.now());
		outputEntity.setActive();
		
		if((!this.pentito) && (!this.prisonStatus)) {
			// Schedule the next extortion demand
			Event event = new Event(
					this.simulator.now() + this.demandPDF.nextValue(), this,
					Constants.EVENT_DEMAND_EXTORTION);
			this.simulator.insert(event);
		}
	}
	
	
	@Override
	public void imprisonment(ImprisonmentAction action) {
		
		int mafiosoId = (int) action.getParam(ImprisonmentAction.Param.MAFIOSO_ID);
		
		if(this.id == mafiosoId) {
			this.custodyStatus = false;
			this.prisonStatus = true;
			
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.MAFIOSI, this.outputId);
			outputEntity.setValue(
					MafiosiOutputEntity.Field.RELEASE_CUSTODY_TIME.name(),
					this.simulator.now());
			outputEntity.setValue(MafiosiOutputEntity.Field.IMPRISONED_TIME.name(),
					this.simulator.now());
		}
	}
	
	
	@Override
	public void releaseImprisonment(ReleaseImprisonmentAction action) {
		
		int mafiosoId = (int) action
				.getParam(ReleaseImprisonmentAction.Param.MAFIOSO_ID);
		
		if(this.id == mafiosoId) {
			this.prisonStatus = false;
			this.custodyStatus = false;
			
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.MAFIOSI, this.outputId);
			outputEntity.setValue(
					MafiosiOutputEntity.Field.RELEASE_IMPRISONMENT_TIME.name(),
					this.simulator.now());
			outputEntity.setActive();
			
			if(!this.pentito) {
				// Schedule the next extortion demand
				Event event = new Event(this.simulator.now()
						+ this.demandPDF.nextValue(), this,
						Constants.EVENT_DEMAND_EXTORTION);
				this.simulator.insert(event);
			}
		}
	}
	
	
	@Override
	public void decidePentito() {
		if(RandomUtil.nextDouble() < this.pentitiProbability) {
			
			List<Integer> payerEntrepreneurs = new ArrayList<Integer>();
			payerEntrepreneurs.addAll(this.payingEntrepreneurs);
			this.payingEntrepreneurs.clear();
			
			this.pentito = true;
			PentitoAction pentiti = new PentitoAction(this.id, this.neighbors,
					payerEntrepreneurs);
			Message msg = new Message(this.simulator.now(), this.id, this.stateId,
					pentiti);
			this.sendMsg(msg);
		}
	}
	
	
	@Override
	public void finalizeSim() {
	}
	
	
	/**
	 * Get a list of all the paying Entrepreneurs
	 * 
	 * @param none
	 * @return List of paying Entrepreneurs
	 */
	private List<Integer> getPayingEntrepreneurs() {
		List<Integer> payers = new ArrayList<Integer>();
		
		payers.addAll(this.payingEntrepreneurs);
		this.payingEntrepreneurs.clear();
		
		return payers;
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
			
			// Pay extortion
			if(content instanceof PayExtortionAction) {
				this.receivePayment((PayExtortionAction) content);
				
				// Not pay extortion
			} else if(content instanceof NotPayExtortionAction) {
				this.decidePunishment((NotPayExtortionAction) content);
				
				// Custody
			} else if(content instanceof CustodyAction) {
				this.custody((CustodyAction) content);
				
				// Release custody
			} else if(content instanceof ReleaseCustodyAction) {
				this.releaseCustody();
				
				// Imprisonment
			} else if(content instanceof ImprisonmentAction) {
				this.imprisonment((ImprisonmentAction) content);
				
				// Release Imprisonment
			} else if(content instanceof ReleaseImprisonmentAction) {
				this.releaseImprisonment((ReleaseImprisonmentAction) content);
				
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
				case Constants.REQUEST_COLLECT_PAYERS:
					infoRequested = this.getPayingEntrepreneurs();
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
			case Constants.EVENT_DEMAND_EXTORTION:
				this.decideExtortion();
				break;
			case Constants.EVENT_COLLECT_EXTORTION:
				this.collectExtortion();
				break;
		}
	}
}