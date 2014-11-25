package gloderss.agents.entrepreneur;

import emilia.EmiliaController;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import gloderss.Constants;
import gloderss.Constants.Actions;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.BuyProductAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.CollectAction;
import gloderss.actions.DeliverProductAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotCollaborateAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.CitizenAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.EntrepreneurConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.ExtortionOutputEntity.Field;
import gloderss.reputation.MafiaPunisherReputation;
import gloderss.reputation.StateProtectorReputation;
import gloderss.reputation.StateFinderReputation;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PROBLEM
 * 1. Erroneous individual drive proportionality calculation on paying and not
 * paying extortion
 * 
 */
public class EntrepreneurAgent extends CitizenAgent implements IEntrepreneur,
		NormEnforcementListener {
	
	private EntrepreneurConf					conf;
	
	private PDFAbstract								periodicityWagePDF;
	
	private int												stateId;
	
	private double										wealth;
	
	private double										defaultWage;
	
	private double										currentWage;
	
	private double										productPrice;
	
	// TODO Review this parameter
	private double										punishmentState;
	
	private boolean										affiliated;
	
	private StateFinderReputation			stateFinderRep;
	
	private StateProtectorReputation	stateProtectorRep;
	
	private MafiaPunisherReputation		mafiaPunisherRep;
	
	private EmiliaController					normative;
	
	private boolean										pay;
	
	
	/**
	 * Entrepreneur constructor
	 * 
	 * @param id
	 *          Entrepreneur agent identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Entrepreneur configuration
	 * @return none
	 */
	public EntrepreneurAgent(int id, EventSimulator simulator,
			EntrepreneurConf conf) {
		super(id, simulator);
		this.conf = conf;
		
		this.periodicityWagePDF = PDFAbstract.getInstance(this.conf
				.getPeriodicityWagePDF());
		
		/**
		 * Economic
		 */
		this.wealth = conf.getWealth();
		
		this.defaultWage = conf.getMinimumWage()
				+ (RandomUtil.nextDouble() * (conf.getMaximumWage() - conf
						.getMinimumWage()));
		
		this.productPrice = conf.getMinimumPrice()
				+ (RandomUtil.nextDouble() * (conf.getMaximumPrice() - conf
						.getMinimumPrice()));
		
		/**
		 * State
		 */
		// TODO Understand how and where to define this value
		this.punishmentState = 0.0;
		
		/**
		 * Intermediary Organization
		 */
		this.affiliated = conf.getAffiliated();
		
		/**
		 * Reputation
		 */
		this.stateFinderRep = new StateFinderReputation(conf.getReputationConf()
				.getStatePunisher());
		
		this.stateProtectorRep = new StateProtectorReputation(conf
				.getReputationConf().getStateProtector());
		
		this.mafiaPunisherRep = new MafiaPunisherReputation(conf
				.getReputationConf().getMafiaPunisher());
		
		/**
		 * Normative
		 */
		this.normative = new EmiliaController(id, conf.getNormativeXML(),
				this.conf.getNormativeXSD());
		this.normative.init();
		this.normative.registerNormEnforcement(this);
		
		// Create Norms
		List<SanctionEntityAbstract> sanctions;
		NormContent normContent;
		NormEntityAbstract norm;
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
		
		// PAY Norm
		normContent = new NormContent(Actions.PAY_EXTORTION,
				Actions.NOT_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.PAY_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, this.conf
						.getSaliences().get(Norms.PAY_EXTORTION.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_PAY Norm
		normContent = new NormContent(Actions.NOT_PAY_EXTORTION,
				Actions.PAY_EXTORTION);
		
		norm = new NormEntity(Norms.NOT_PAY_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, conf
						.getSaliences().get(Norms.NOT_PAY_EXTORTION.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// DENOUNCE Norm
		normContent = new NormContent(Actions.DENOUNCE_EXTORTION,
				Actions.NOT_DENOUNCE_EXTORTION);
		
		norm = new NormEntity(Norms.DENOUNCE_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, conf
						.getSaliences().get(Norms.DENOUNCE_EXTORTION.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_DENOUNCE Norm
		normContent = new NormContent(Actions.NOT_DENOUNCE_EXTORTION,
				Actions.DENOUNCE_EXTORTION);
		
		norm = new NormEntity(Norms.NOT_DENOUNCE_EXTORTION.ordinal(),
				NormType.SOCIAL, NormSource.DISTRIBUTED, NormStatus.GOAL, normContent,
				conf.getSaliences().get(Norms.NOT_DENOUNCE_EXTORTION.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		this.normative.addNormsSanctions(normsSanctions);
		
		this.pay = false;
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public EntrepreneurConf getConf() {
		return this.conf;
	}
	
	
	public int getStateId() {
		return this.stateId;
	}
	
	
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	
	public boolean getAffiliated() {
		return this.affiliated;
	}
	
	
	public void setAffiliated(boolean affiliated) {
		this.affiliated = affiliated;
	}
	
	
	public double getWealth() {
		return this.wealth;
	}
	
	
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
	
	
	public double getDefaultWage() {
		return this.defaultWage;
	}
	
	
	public void setDefaultWage(double defaultWage) {
		this.defaultWage = defaultWage;
	}
	
	
	public double getCurrentWage() {
		return this.currentWage;
	}
	
	
	public void setCurrentWage(double currentWage) {
		this.currentWage = currentWage;
	}
	
	
	public double getProductPrice() {
		return this.productPrice;
	}
	
	
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		this.currentWage = 0.0;
		
		Event event = new Event(this.simulator.now() + 1, this,
				Constants.EVENT_RECEIVE_WAGE);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void receiveWage() {
		this.wealth += this.currentWage;
		
		// Define the current Wage
		if(RandomUtil.nextDouble() < 0.5) {
			this.currentWage = this.defaultWage
					* (1 + (this.conf.getVariationWage() * RandomUtil.nextDouble()));
		} else {
			this.currentWage = this.defaultWage
					* (1 - (this.conf.getVariationWage() * RandomUtil.nextDouble()));
		}
		
		Event event = new Event(this.simulator.now()
				+ this.periodicityWagePDF.nextValue(), this,
				Constants.EVENT_RECEIVE_WAGE);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void decidePayment(ExtortionAction action) {
		
		double extortion = (double) action
				.getParam(ExtortionAction.Param.EXTORTION);
		
		if((this.currentWage > extortion) && (!this.affiliated)) {
			
			double punishment = (double) action
					.getParam(ExtortionAction.Param.PUNISHMENT);
			
			double benefit = (double) action.getParam(ExtortionAction.Param.BENEFIT);
			
			double TpayNG = this.normative.getNormSalience(Norms.PAY_EXTORTION
					.ordinal());
			
			double TpayIG = (benefit - extortion)
					- (this.punishmentState * this.stateFinderRep.getReputation() * (1 - this.conf
							.getCollaborationProbability()));
			
			double TnotPayNG = this.normative.getNormSalience(Norms.NOT_PAY_EXTORTION
					.ordinal());
			
			double TnotPayIG = (extortion - benefit)
					- (punishment * this.mafiaPunisherRep.getReputation());
			
			// System.out.println(this.conf.getIndividualWeight() + " " + TpayIG + " "
			// + TpayNG + " " + this.conf.getNormativeWeight() + " " + TnotPayIG
			// + " " + TnotPayNG);
			
			// TODO
			// Need to be adjusted with ARC TAN
			double probPay = 0.0;
			if(TpayNG > TnotPayNG) {
				
				double IG = (Math.abs(TpayIG) / (Math.abs(TpayIG) + Math.abs(TnotPayIG)));
				if((TpayIG < 0) || (TnotPayIG < 0)) {
					IG = 1 - IG;
				}
				
				probPay = (this.conf.getIndividualWeight() * IG)
						+ (this.conf.getNormativeWeight() * TpayNG);
				
				// System.out.println(this.conf.getIndividualWeight() + " " + IG + " "
				// + this.conf.getNormativeWeight() + " " + TnotPayNG);
				
			} else {
				
				double id = (Math.abs(TnotPayIG) / (Math.abs(TpayIG) + Math
						.abs(TnotPayIG)));
				if((TpayIG < 0) || (TnotPayIG < 0)) {
					id = 1 - id;
				}
				
				probPay = (this.conf.getIndividualWeight() * id)
						+ (this.conf.getNormativeWeight() * TnotPayNG);
				
				// System.out.println(this.conf.getIndividualWeight() + " " + id + " "
				// + this.conf.getNormativeWeight() + " " + TnotPayNG);
			}
			
			// Decide paying extortion
			if(RandomUtil.nextDouble() < probPay) {
				this.pay = true;
				
				// Decide not paying extortion
			} else {
				this.pay = false;
			}
		}
		
		// If the Entrepreneur decided not to pay
		if(!this.pay) {
			this.decideDenounceExtortion(action);
		}
	}
	
	
	@Override
	public void decideDenounceExtortion(ExtortionAction action) {
		
		int extortionId = (int) action.getParam(ExtortionAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		// TODO
		// Should we include the Critical Consumers here????
		// How to consider Proportion of critical consumers (slide 9 - 2014-10-21)
		// this.conf.getDenounceAlpha() - balance between risk and opportunity
		double idDenounce = this.mafiaPunisherRep.getReputation()
				* (1 - this.stateProtectorRep.getReputation());
		
		double denounceNG = this.normative.getNormSalience(Norms.DENOUNCE_EXTORTION
				.ordinal());
		
		double notDenounceNG = this.normative
				.getNormSalience(Norms.NOT_DENOUNCE_EXTORTION.ordinal());
		
		double probDenounce;
		if(denounceNG > notDenounceNG) {
			
			probDenounce = (this.conf.getIndividualWeight() * idDenounce)
					+ (this.conf.getNormativeWeight() * denounceNG);
			
		} else {
			
			probDenounce = 1 - ((this.conf.getIndividualWeight() * idDenounce) + (this.conf
					.getNormativeWeight() * notDenounceNG));
			
		}
		
		if(RandomUtil.nextDouble() < probDenounce) {
			DenounceExtortionAction denounceAction = new DenounceExtortionAction(
					this.id, (int) action.getParam(ExtortionAction.Param.MAFIOSO_ID));
			
			Message replyMsg = new Message(this.simulator.now(), this.id,
					this.stateId, denounceAction);
			this.sendMsg(replyMsg);
			
			outputEntity.setValue(Field.DENOUNCED_EXTORTION.name(), true);
		} else {
			outputEntity.setValue(Field.DENOUNCED_EXTORTION.name(), false);
		}
	}
	
	
	@Override
	public void collectExtortion(CollectAction action) {
		
		int extortionId = (int) action.getParam(CollectAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		int mafiosoId = (int) action.getParam(CollectAction.Param.MAFIOSO_ID);
		
		int victimId = (int) action.getParam(CollectAction.Param.VICTIM_ID);
		
		double extortion = (double) action.getParam(CollectAction.Param.EXTORTION);
		
		if(this.pay) {
			
			PayExtortionAction payAction = new PayExtortionAction(extortionId,
					mafiosoId, victimId, extortion);
			
			Message msg = new Message(this.simulator.now(), victimId, mafiosoId,
					payAction);
			this.sendMsg(msg);
			
			this.currentWage -= extortion;
			
			outputEntity.setValue(Field.PAID.name(), true);
			
		} else {
			
			NotPayExtortionAction notPayAction = new NotPayExtortionAction(
					extortionId, mafiosoId, victimId, extortion);
			
			Message msg = new Message(this.simulator.now(), victimId, mafiosoId,
					notPayAction);
			this.sendMsg(msg);
			
			outputEntity.setValue(Field.PAID.name(), false);
			
		}
		
		this.pay = false;
	}
	
	
	@Override
	public void receiveMafiaBenefit(MafiaBenefitAction action) {
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION,
				(int) action.getParam(MafiaBenefitAction.Param.EXTORTION_ID));
		
		outputEntity.setValue(Field.MAFIA_PUNISHED.name(), false);
		outputEntity.setValue(Field.MAFIA_BENEFITED.name(), true);
		
		this.wealth += (double) outputEntity.getValue(Field.MAFIA_BENEFIT.name());
	}
	
	
	@Override
	public void receiveMafiaPunishment(MafiaPunishmentAction action) {
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION,
				(int) action.getParam(MafiaPunishmentAction.Param.EXTORTION_ID));
		
		outputEntity.setValue(Field.MAFIA_PUNISHED.name(), true);
		outputEntity.setValue(Field.MAFIA_BENEFITED.name(), false);
		
		this.wealth -= (double) outputEntity
				.getValue(Field.MAFIA_PUNISHMENT.name());
		
		this.decideDenouncePunishment(action);
	}
	
	
	@Override
	public void decideDenouncePunishment(MafiaPunishmentAction action) {
		
		int extortionId = (int) action
				.getParam(MafiaPunishmentAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		// TODO
		// Should we include the Critical Consumers here????
		// How to consider Proportion of critical consumers (slide 9 - 2014-10-21)
		// this.conf.getDenounceAlpha() - balance between risk and opportunity
		double idDenounce = this.mafiaPunisherRep.getReputation()
				* (1 - this.stateProtectorRep.getReputation());
		
		double denounceNG = this.normative.getNormSalience(Norms.DENOUNCE_EXTORTION
				.ordinal());
		
		double notDenounceNG = this.normative
				.getNormSalience(Norms.NOT_DENOUNCE_EXTORTION.ordinal());
		
		double probDenounce;
		if((this.affiliated) || (denounceNG > notDenounceNG)) {
			
			probDenounce = (this.conf.getIndividualWeight() * idDenounce)
					+ (this.conf.getNormativeWeight() * denounceNG);
			
		} else {
			
			probDenounce = 1 - ((this.conf.getIndividualWeight() * idDenounce) + (this.conf
					.getNormativeWeight() * notDenounceNG));
			
		}
		
		if(RandomUtil.nextDouble() < probDenounce) {
			
			int mafioso_id = (int) outputEntity.getValue(Field.MAFIOSO_ID.name());
			
			double punishment = (double) outputEntity.getValue(Field.MAFIA_PUNISHMENT
					.name());
			
			DenouncePunishmentAction denounceAction = new DenouncePunishmentAction(
					this.id, mafioso_id, punishment);
			
			Message replyMsg = new Message(this.simulator.now(), this.id,
					this.stateId, denounceAction);
			this.sendMsg(replyMsg);
			
			outputEntity.setValue(Field.DENOUNCED_PUNISHMENT.name(), true);
		} else {
			outputEntity.setValue(Field.DENOUNCED_PUNISHMENT.name(), false);
		}
	}
	
	
	@Override
	public void decideCollaboration(CollaborationRequestAction action) {
		
		int entrepreneurId = (int) action
				.getParam(CollaborationRequestAction.Param.ENTREPRENEUR_ID);
		
		int mafiosoId = (int) action
				.getParam(CollaborationRequestAction.Param.MAFIOSO_ID);
		
		if(RandomUtil.nextDouble() < this.conf.getCollaborationProbability()) {
			
			CollaborateAction collaborate = new CollaborateAction(mafiosoId,
					entrepreneurId);
			
			Message msg = new Message(this.simulator.now(), this.id, entrepreneurId,
					collaborate);
			this.sendMsg(msg);
			
		} else {
			
			NotCollaborateAction notCollaborate = new NotCollaborateAction(mafiosoId,
					entrepreneurId);
			
			Message msg = new Message(this.simulator.now(), this.id, entrepreneurId,
					notCollaborate);
			this.sendMsg(msg);
		}
	}
	
	
	@Override
	public void receiveStatePunishment(StatePunishmentAction action) {
		
		double punishment = (double) action
				.getParam(StatePunishmentAction.Param.PUNISHMENT);
		
		this.wealth -= punishment;
	}
	
	
	@Override
	public void receiveStateCompensation(StateCompensationAction action) {
		
		double compensation = (double) action
				.getParam(StateCompensationAction.Param.COMPENSATION);
		
		this.wealth += compensation;
	}
	
	
	@Override
	public void receiveBuy(BuyProductAction action) {
		
		int consumerId = (int) action.getParam(BuyProductAction.Param.CONSUMER_ID);
		
		DeliverProductAction deliver = new DeliverProductAction(consumerId,
				this.id, this.productPrice);
		
		Message msg = new Message(this.simulator.now(), this.id, consumerId,
				deliver);
		this.sendMsg(msg);
	}
	
	
	// TODO
	@Override
	public void decideAffiliation() {
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
			
			// Extortion demand
			if(content instanceof ExtortionAction) {
				this.decidePayment((ExtortionAction) content);
				
				// Collect extortion
			} else if(content instanceof CollectAction) {
				this.collectExtortion((CollectAction) content);
				
				// Mafia benefit
			} else if(content instanceof MafiaBenefitAction) {
				this.receiveMafiaBenefit((MafiaBenefitAction) content);
				
				// Mafia punishment
			} else if(content instanceof MafiaPunishmentAction) {
				this.receiveMafiaPunishment((MafiaPunishmentAction) content);
				
				// Collaboration request
			} else if(content instanceof CollaborationRequestAction) {
				this.decideCollaboration((CollaborationRequestAction) content);
				
				// State punishment
			} else if(content instanceof StatePunishmentAction) {
				this.receiveStatePunishment((StatePunishmentAction) content);
				
				// State compensation
			} else if(content instanceof StateCompensationAction) {
				this.receiveStateCompensation((StateCompensationAction) content);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				this.receiveBuy((BuyProductAction) content);
				
				// Affiliation accepted
			} else if(content instanceof AffiliationAcceptedAction) {
				AffiliationAcceptedAction action = (AffiliationAcceptedAction) content;
				
				int entrepreneurId = (int) action
						.getParam(AffiliationAcceptedAction.Param.ENTREPRENEUR_ID);
				
				if(entrepreneurId == this.id) {
					this.affiliated = true;
				}
				
			} else if(content instanceof AffiliationDeniedAction) {
				AffiliationDeniedAction action = (AffiliationDeniedAction) content;
				
				int entrepreneurId = (int) action
						.getParam(AffiliationDeniedAction.Param.ENTREPRENEUR_ID);
				
				if(entrepreneurId == this.id) {
					this.affiliated = false;
				}
				
			}
		}
	}
	
	
	@Override
	public Object handleInfo(InfoAbstract info) {
		if(info.getType().equals(InfoAbstract.Type.REQUEST)) {
			Object infoRequested = null;
			
			InfoRequest request = (InfoRequest) info;
			switch(request.getInfoRequest()) {
				case Constants.REQUEST_AFFILIATION:
					infoRequested = this.getAffiliated();
					break;
				case Constants.REQUEST_ID:
					infoRequested = this.getId();
					break;
				case Constants.REQUEST_DEFAULT_WAGE:
					infoRequested = this.getDefaultWage();
					break;
				case Constants.REQUEST_PRODUCT_PRICE:
					infoRequested = this.getProductPrice();
					break;
			}
			
			return infoRequested;
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			Object infoResult = new Boolean(false);
			
			InfoSet set = (InfoSet) info;
			switch(set.getParameter()) {
				case Constants.PARAMETER_STATE_ID:
					if(set.getValue() instanceof Integer) {
						this.stateId = (Integer) set.getValue();
						infoResult = new Boolean(true);
					}
					break;
			}
			
			return infoResult;
		}
		
		return null;
	}
	
	
	@Override
	public void handleObservation(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (!msg.getReceiver().contains(this.id))) {
			
			if(content instanceof AffiliationAcceptedAction) {
				// TODO
				
			} else if(content instanceof AffiliationDeniedAction) {
				// TODO
				
			} else if(content instanceof BuyProductAction) {
				// TODO
				
			} else if(content instanceof CollaborateAction) {
				// TODO
				
			} else if(content instanceof DenounceExtortionAction) {
				// TODO
				
			} else if(content instanceof DenouncePunishmentAction) {
				// TODO
				
			} else if(content instanceof MafiaBenefitAction) {
				// TODO
				
			} else if(content instanceof MafiaPunishmentAction) {
				// TODO
				
			} else if(content instanceof PayExtortionAction) {
				// TODO
				
			} else if(content instanceof NotPayExtortionAction) {
				// TODO
				
			} else if(content instanceof StateCompensationAction) {
				// TODO
				
			} else if(content instanceof StatePunishmentAction) {
				// TODO
				
			}
		}
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
	
	@Override
	public void handleEvent(Event event) {
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_RECEIVE_WAGE:
				this.receiveWage();
				break;
		}
	}
}