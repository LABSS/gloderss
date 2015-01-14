package gloderss.agents.entrepreneur;

import emilia.EmiliaController;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import emilia.modules.salience.DataType;
import gloderss.Constants;
import gloderss.Constants.Actions;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliateRequestAction;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.BuyProductAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.CollectAction;
import gloderss.actions.CriticalConsumerInfoAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.DeliverProductAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NormInvocationAction;
import gloderss.actions.NormSanctionAction;
import gloderss.actions.NotCollaborateAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenounceExtortionAffiliatedAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotDenouncePunishmentAffiliatedAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitoAction;
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
import gloderss.output.EntrepreneurOutputEntity;
import gloderss.output.ExtortionOutputEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.CompensationOutputEntity;
import gloderss.reputation.MafiaPunisherReputation;
import gloderss.reputation.StateProtectorReputation;
import gloderss.reputation.StateFinderReputation;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormContentSet;
import gloderss.normative.entity.norm.NormEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntrepreneurAgent extends CitizenAgent implements IEntrepreneur,
		NormEnforcementListener {
	
	private final static Logger				logger			= LoggerFactory
																										.getLogger(EntrepreneurAgent.class);
	
	private final static double				ATAN_FACTOR	= 0.01;
	
	private EntrepreneurConf					conf;
	
	private PDFAbstract								periodicityWagePDF;
	
	private int												stateId;
	
	private int												ioId;
	
	private double										wealth;
	
	private double										defaultWage;
	
	private double										currentWage;
	
	private double										productPrice;
	
	private double										statePunishment;
	
	private boolean										affiliated;
	
	private double										criticalConsumers;
	
	private StateFinderReputation			stateFinderRep;
	
	private StateProtectorReputation	stateProtectorRep;
	
	private MafiaPunisherReputation		mafiaPunisherRep;
	
	private EmiliaController					normative;
	
	private Map<Integer, Boolean>			pay;
	
	
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
		
		this.currentWage = 0.0;
		
		this.productPrice = conf.getMinimumPrice()
				+ (RandomUtil.nextDouble() * (conf.getMaximumPrice() - conf
						.getMinimumPrice()));
		
		/**
		 * State
		 */
		this.statePunishment = 0.0;
		
		/**
		 * Intermediary Organization
		 */
		this.affiliated = conf.getAffiliated();
		
		this.criticalConsumers = 0.0;
		
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
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_PAY Norm
		normContent = new NormContent(Actions.NOT_PAY_EXTORTION,
				Actions.PAY_EXTORTION);
		
		norm = new NormEntity(Norms.NOT_PAY_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// DENOUNCE Norm
		List<Actions> actions = new ArrayList<Actions>();
		actions.add(Actions.DENOUNCE_EXTORTION);
		actions.add(Actions.DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.DENOUNCE_PUNISHMENT);
		actions.add(Actions.DENOUNCE_PUNISHMENT_AFFILIATED);
		
		List<Actions> noActions = new ArrayList<Actions>();
		actions.add(Actions.NOT_DENOUNCE_EXTORTION);
		actions.add(Actions.NOT_DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT_AFFILIATED);
		
		NormContentSet normContentSet = new NormContentSet(actions, noActions);
		
		norm = new NormEntity(Norms.DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_DENOUNCE Norm
		actions = new ArrayList<Actions>();
		actions.add(Actions.NOT_DENOUNCE_EXTORTION);
		actions.add(Actions.NOT_DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT_AFFILIATED);
		
		noActions = new ArrayList<Actions>();
		actions.add(Actions.DENOUNCE_EXTORTION);
		actions.add(Actions.DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.DENOUNCE_PUNISHMENT);
		actions.add(Actions.DENOUNCE_PUNISHMENT_AFFILIATED);
		
		normContentSet = new NormContentSet(noActions, actions);
		
		norm = new NormEntity(Norms.NOT_DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		this.normative.addNormsSanctions(normsSanctions);
		
		// Initialize norm salience
		Map<DataType, Integer> values;
		for(Integer normId : conf.getSalienceConf().keySet()) {
			
			values = new HashMap<DataType, Integer>();
			values.put(DataType.COMPLIANCE, conf.getSalienceConf().get(normId)
					.getCompliance());
			values.put(DataType.VIOLATION, conf.getSalienceConf().get(normId)
					.getViolation());
			values.put(DataType.COMPLIANCE_OBSERVED,
					conf.getSalienceConf().get(normId).getObsCompliance());
			values.put(DataType.VIOLATION_OBSERVED, conf.getSalienceConf()
					.get(normId).getObsViolation());
			values.put(DataType.PUNISHMENT, conf.getSalienceConf().get(normId)
					.getPunishment());
			values.put(DataType.SANCTION, conf.getSalienceConf().get(normId)
					.getSanction());
			values.put(DataType.COMPLIANCE_INVOKED, conf.getSalienceConf()
					.get(normId).getInvocationCompliance());
			values.put(DataType.VIOLATION_INVOKED, conf.getSalienceConf().get(normId)
					.getInvocationViolation());
			
			this.normative.setInitialValues(normId, values);
			
			if(conf.getSalienceConf().get(normId).getActive()) {
				this.normative.getNorm(normId).setStatus(NormStatus.GOAL);
			} else {
				this.normative.getNorm(normId).setStatus(NormStatus.INACTIVE);
			}
		}
		
		this.pay = new HashMap<Integer, Boolean>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public int getStateId() {
		return this.stateId;
	}
	
	
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	
	public double getStatePunishment() {
		return this.statePunishment;
	}
	
	
	public void setStatePunishment(double statePunishment) {
		this.statePunishment = statePunishment;
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
	
	
	public int getIOId() {
		return this.ioId;
	}
	
	
	public void setIOId(int ioId) {
		this.ioId = ioId;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		Event event = new Event(this.simulator.now(), this,
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
		
		this.normative.update();
		
		int extortionId = (int) action.getParam(ExtortionAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		// Decide to affiliate to Intermediary Organization
		this.decideAffiliation();
		
		int mafiosoId = (int) action.getParam(ExtortionAction.Param.MAFIOSO_ID);
		
		double extortion = (double) action
				.getParam(ExtortionAction.Param.EXTORTION);
		
		if((this.currentWage > extortion) && (!this.affiliated)) {
			
			double punishment = (double) action
					.getParam(ExtortionAction.Param.PUNISHMENT);
			
			double benefit = (double) action.getParam(ExtortionAction.Param.BENEFIT);
			
			double TpayIG = (benefit - extortion)
					- (this.statePunishment * this.stateFinderRep.getReputation() * (1 - this.conf
							.getCollaborationProbability()));
			
			double TpayNG = this.normative.getNormSalience(Norms.PAY_EXTORTION
					.ordinal());
			
			double TnotPayIG = (extortion - benefit)
					- (punishment * this.mafiaPunisherRep.getReputation());
			
			double TnotPayNG = this.normative.getNormSalience(Norms.NOT_PAY_EXTORTION
					.ordinal());
			
			logger.debug("[DECIDE-TO-PAY] " + this.id + " " + mafiosoId + " "
					+ TpayIG + " " + TpayNG + " " + TnotPayIG + " " + TnotPayNG);
			
			double atanTpayIG = (0.5 * Math.atan2(ATAN_FACTOR * TpayIG, 1) / ((double) Math.PI / (double) 2.0)) + 0.5;
			double atanTnotPayIG = (0.5 * (Math.atan2(ATAN_FACTOR * TnotPayIG, 1) / ((double) Math.PI / (double) 2.0))) + 0.5;
			
			double IG = (double) atanTpayIG / (double) (atanTpayIG + atanTnotPayIG);
			
			logger.debug("[ADJUSTED-IG] " + TpayIG + " " + TnotPayIG + " " + IG);
			
			NormEntityAbstract normPay = this.normative.getNorm(Norms.PAY_EXTORTION
					.ordinal());
			
			NormEntityAbstract normNotPay = this.normative
					.getNorm(Norms.NOT_PAY_EXTORTION.ordinal());
			
			double probPay = 0.0;
			// NOT PAY EXTORTION and PAY EXTORTION norms active
			if((normPay.getStatus().equals(NormStatus.GOAL))
					&& (normNotPay.getStatus().equals(NormStatus.GOAL))) {
				
				if(TpayNG > TnotPayNG) {
					
					probPay = (this.conf.getIndividualWeight() * IG)
							+ (this.conf.getNormativeWeight() * TpayNG);
					
				} else {
					
					probPay = (this.conf.getIndividualWeight() * IG)
							+ (this.conf.getNormativeWeight() * (1 - TnotPayNG));
					
				}
				
				// PAY EXTORTION norm active
			} else if(normPay.getStatus().equals(NormStatus.GOAL)) {
				
				probPay = (this.conf.getIndividualWeight() * IG)
						+ (this.conf.getNormativeWeight() * TpayNG);
				
				// NOT PAY EXTORTION norm active
			} else if(normNotPay.getStatus().equals(NormStatus.GOAL)) {
				
				probPay = (this.conf.getIndividualWeight() * IG)
						+ (this.conf.getNormativeWeight() * (1 - TnotPayNG));
				
				// NONE norm active
			} else {
				
				probPay = IG;
				
			}
			
			// Decide paying extortion
			if(RandomUtil.nextDouble() < probPay) {
				this.pay.put(extortionId, true);
				
				// Decide not paying extortion
			} else {
				this.pay.put(extortionId, false);
				this.decideDenounceExtortion(action);
			}
			
			// Output
			outputEntity.setValue(
					ExtortionOutputEntity.Field.STATE_PUNISHMENT.name(),
					this.statePunishment);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.PAY_EXTORTION_INDIVIDUAL.name(), TpayIG);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.PAY_EXTORTION_SALIENCE.name(), TpayNG);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.NOT_PAY_EXTORTION_INDIVIDUAL.name(),
					TnotPayIG);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.NOT_PAY_EXTORTION_SALIENCE.name(),
					TnotPayNG);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.STATE_FINDER_REPUTATION.name(),
					this.stateFinderRep.getReputation());
			outputEntity.setValue(
					ExtortionOutputEntity.Field.STATE_PROTECTOR_REPUTATION.name(),
					this.stateProtectorRep.getReputation());
			outputEntity.setValue(
					ExtortionOutputEntity.Field.MAFIA_PUNISHER_REPUTATION.name(),
					this.mafiaPunisherRep.getReputation());
			outputEntity.setValue(ExtortionOutputEntity.Field.PAID.name(),
					this.pay.get(extortionId));
		}
	}
	
	
	@Override
	public void decideDenounceExtortion(ExtortionAction action) {
		
		this.normative.update();
		
		int extortionId = (int) action.getParam(ExtortionAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		double denounceIG = (this.conf.getDenounceAlpha()
				* (1 - this.mafiaPunisherRep.getReputation()) * this.stateProtectorRep
					.getReputation())
				+ ((1 - this.conf.getDenounceAlpha()) * this.criticalConsumers);
		
		NormEntityAbstract normDenounce = this.normative.getNorm(Norms.DENOUNCE
				.ordinal());
		
		NormEntityAbstract normNotDenounce = this.normative
				.getNorm(Norms.NOT_DENOUNCE.ordinal());
		
		double denounceNG = normDenounce.getSalience();
		
		double notDenounceNG = normNotDenounce.getSalience();
		
		double probDenounce;
		// NOT DENOUNCE and DENOUNCE EXTORTION norms active
		if((normDenounce.getStatus().equals(NormStatus.GOAL))
				&& (normNotDenounce.getStatus().equals(NormStatus.GOAL))) {
			
			if(denounceNG > notDenounceNG) {
				
				probDenounce = (this.conf.getIndividualWeight() * denounceIG)
						+ (this.conf.getNormativeWeight() * denounceNG);
				
			} else {
				
				probDenounce = (this.conf.getIndividualWeight() * denounceIG)
						+ (this.conf.getNormativeWeight() * (1 - notDenounceNG));
				
			}
			
			// DENOUNCE EXTORTION norm active
		} else if(normDenounce.getStatus().equals(NormStatus.GOAL)) {
			
			probDenounce = (this.conf.getIndividualWeight() * denounceIG)
					+ (this.conf.getNormativeWeight() * denounceNG);
			
			// NOT DENOUNCE EXTORTION norm active
		} else if(normNotDenounce.getStatus().equals(NormStatus.GOAL)) {
			
			probDenounce = (this.conf.getIndividualWeight() * denounceIG)
					+ (this.conf.getNormativeWeight() * (1 - notDenounceNG));
			
			// NONE norm active
		} else {
			
			probDenounce = denounceIG;
			
		}
		
		int mafiosoId = (int) action.getParam(ExtortionAction.Param.MAFIOSO_ID);
		
		if(RandomUtil.nextDouble() < probDenounce) {
			
			if(this.affiliated) {
				DenounceExtortionAffiliatedAction denounceAction = new DenounceExtortionAffiliatedAction(
						extortionId, this.id, this.stateId, mafiosoId);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						denounceAction);
				this.sendMsg(msg);
				
				// Reputation
				this.stateProtectorRep.updateReputation(denounceAction);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
				
			} else {
				DenounceExtortionAction denounceAction = new DenounceExtortionAction(
						extortionId, this.id, this.stateId, mafiosoId);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						denounceAction);
				this.sendMsg(msg);
				
				// Reputation
				this.stateProtectorRep.updateReputation(denounceAction);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
			}
			
			// Output
			outputEntity.setValue(
					ExtortionOutputEntity.Field.DENOUNCED_EXTORTION.name(), true);
			
		} else {
			
			if(this.affiliated) {
				NotDenounceExtortionAffiliatedAction notDenounceExtortionAction = new NotDenounceExtortionAffiliatedAction(
						extortionId, this.id, this.stateId, mafiosoId);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						notDenounceExtortionAction);
				this.sendMsg(msg);
				
			} else {
				NotDenounceExtortionAction notDenounceExtortionAction = new NotDenounceExtortionAction(
						extortionId, this.id, this.stateId, mafiosoId);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						notDenounceExtortionAction);
				this.sendMsg(msg);
			}
			
			// Output
			outputEntity.setValue(
					ExtortionOutputEntity.Field.DENOUNCED_EXTORTION.name(), false);
		}
		
		outputEntity.setValue(
				ExtortionOutputEntity.Field.DENOUNCE_EXTORTION_INDIVIDUAL.name(),
				denounceIG);
		outputEntity.setValue(
				ExtortionOutputEntity.Field.DENOUNCE_EXTORTION_SALIENCE.name(),
				denounceNG);
		outputEntity.setValue(
				ExtortionOutputEntity.Field.NOT_DENOUNCE_EXTORTION_SALIENCE.name(),
				notDenounceNG);
		outputEntity.setValue(
				ExtortionOutputEntity.Field.CRITICAL_CONSUMERS.name(),
				this.criticalConsumers);
	}
	
	
	@Override
	public void collectExtortion(CollectAction action) {
		
		int extortionId = (int) action.getParam(CollectAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		int mafiosoId = (int) action.getParam(CollectAction.Param.MAFIOSO_ID);
		
		int victimId = (int) action.getParam(CollectAction.Param.VICTIM_ID);
		
		double extortion = (double) action.getParam(CollectAction.Param.EXTORTION);
		
		if((this.pay.containsKey(extortionId)) && (this.pay.get(extortionId))) {
			
			PayExtortionAction payAction = new PayExtortionAction(extortionId,
					mafiosoId, victimId, extortion);
			
			Message msg = new Message(this.simulator.now(), victimId, mafiosoId,
					payAction);
			this.sendMsg(msg);
			
			this.currentWage -= extortion;
			
			// Output
			outputEntity.setValue(ExtortionOutputEntity.Field.PAID.name(), true);
			
			// Reputation
			this.stateFinderRep.updateReputation(payAction);
			
		} else {
			
			NotPayExtortionAction notPayAction = new NotPayExtortionAction(
					extortionId, mafiosoId, victimId, extortion);
			
			Message msg = new Message(this.simulator.now(), victimId, mafiosoId,
					notPayAction);
			this.sendMsg(msg);
			
			// Spread action to IO
			this.spreadActionInformation(msg);
			
			// Output
			outputEntity.setValue(ExtortionOutputEntity.Field.PAID.name(), false);
			
			// Update Mafia reputation as Punisher
			this.mafiaPunisherRep.updateReputation(notPayAction);
			
		}
		
		this.pay.remove(extortionId);
	}
	
	
	@Override
	public void receiveMafiaBenefit(MafiaBenefitAction action) {
		
		double benefit = (double) action.getParam(MafiaBenefitAction.Param.BENEFIT);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION,
				(int) action.getParam(MafiaBenefitAction.Param.EXTORTION_ID));
		
		outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_PUNISHED.name(),
				false);
		
		if(benefit > 0) {
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_BENEFITED.name(),
					true);
		} else {
			outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_BENEFITED.name(),
					false);
		}
		
		outputEntity.setValue(
				ExtortionOutputEntity.Field.MAFIA_BENEFITED_AMOUNT.name(), benefit);
		
		outputEntity.setActive();
		
		this.wealth += benefit;
	}
	
	
	@Override
	public void receiveMafiaPunishment(MafiaPunishmentAction action) {
		
		int extortionId = (int) action
				.getParam(MafiaPunishmentAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_PUNISHED.name(),
				true);
		outputEntity.setValue(ExtortionOutputEntity.Field.MAFIA_BENEFITED.name(),
				false);
		outputEntity.setValue(
				ExtortionOutputEntity.Field.MAFIA_BENEFITED_AMOUNT.name(), 0.0);
		outputEntity.setActive();
		
		this.wealth -= (double) outputEntity
				.getValue(ExtortionOutputEntity.Field.MAFIA_PUNISHMENT.name());
		
		// Output
		outputEntity = OutputController.getInstance().getEntity(
				EntityType.COMPENSATION, extortionId);
		outputEntity.setValue(CompensationOutputEntity.Field.TIME.name(),
				this.simulator.now());
		outputEntity.setValue(CompensationOutputEntity.Field.EXTORTION_ID.name(),
				extortionId);
		outputEntity.setValue(
				CompensationOutputEntity.Field.ENTREPRENEUR_ID.name(),
				(int) action.getParam(MafiaPunishmentAction.Param.ENTREPRENEUR_ID));
		outputEntity.setValue(CompensationOutputEntity.Field.MAFIOSO_ID.name(),
				(int) action.getParam(MafiaPunishmentAction.Param.MAFIOSO_ID));
		
		// Decide to denounce punishment
		this.decideDenouncePunishment(action);
	}
	
	
	@Override
	public void decideDenouncePunishment(MafiaPunishmentAction action) {
		
		this.normative.update();
		
		int extortionId = (int) action
				.getParam(MafiaPunishmentAction.Param.EXTORTION_ID);
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.EXTORTION, extortionId);
		
		double denounceIG = (this.conf.getDenounceAlpha()
				* (1 - this.mafiaPunisherRep.getReputation()) * this.stateProtectorRep
					.getReputation())
				+ ((1 - this.conf.getDenounceAlpha()) * this.criticalConsumers);
		
		NormEntityAbstract normDenounce = this.normative.getNorm(Norms.DENOUNCE
				.ordinal());
		
		NormEntityAbstract normNotDenounce = this.normative
				.getNorm(Norms.NOT_DENOUNCE.ordinal());
		
		double denounceNG = normDenounce.getSalience();
		
		double notDenounceNG = normNotDenounce.getSalience();
		
		double probDenounce;
		// NOT DENOUNCE and DENOUNCE EXTORTION norms active
		if((normDenounce.getStatus().equals(NormStatus.GOAL))
				&& (normNotDenounce.getStatus().equals(NormStatus.GOAL))) {
			
			if(denounceNG > notDenounceNG) {
				
				probDenounce = (this.conf.getIndividualWeight() * denounceIG)
						+ (this.conf.getNormativeWeight() * denounceNG);
				
			} else {
				
				probDenounce = (this.conf.getIndividualWeight() * denounceIG)
						+ (this.conf.getNormativeWeight() * (1 - notDenounceNG));
				
			}
			
			// DENOUNCE EXTORTION norm active
		} else if(normDenounce.getStatus().equals(NormStatus.GOAL)) {
			
			probDenounce = (this.conf.getIndividualWeight() * denounceIG)
					+ (this.conf.getNormativeWeight() * denounceNG);
			
			// NOT DENOUNCE EXTORTION norm active
		} else if(normNotDenounce.getStatus().equals(NormStatus.GOAL)) {
			
			probDenounce = (this.conf.getIndividualWeight() * denounceIG)
					+ (this.conf.getNormativeWeight() * (1 - notDenounceNG));
			
			// NONE norm active
		} else {
			
			probDenounce = denounceIG;
			
		}
		
		int mafiosoId = (int) action
				.getParam(MafiaPunishmentAction.Param.MAFIOSO_ID);
		
		double punishment = (double) action
				.getParam(MafiaPunishmentAction.Param.PUNISHMENT);
		
		// An affiliated Entrepreneur always denounce punishment
		if(RandomUtil.nextDouble() < probDenounce) {
			
			if(this.affiliated) {
				DenouncePunishmentAffiliatedAction denounceAction = new DenouncePunishmentAffiliatedAction(
						extortionId, this.id, this.stateId, mafiosoId, punishment);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						denounceAction);
				this.sendMsg(msg);
				
				// Reputation
				this.stateProtectorRep.updateReputation(denounceAction);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
				
			} else {
				DenouncePunishmentAction denounceAction = new DenouncePunishmentAction(
						extortionId, this.id, this.stateId, mafiosoId, punishment);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						denounceAction);
				this.sendMsg(msg);
				
				// Reputation
				this.stateProtectorRep.updateReputation(denounceAction);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
			}
			
			// Output
			outputEntity.setValue(
					CompensationOutputEntity.Field.DENOUNCED_PUNISHMENT.name(), true);
			
			outputEntity = OutputController.getInstance().getEntity(
					EntityType.COMPENSATION, extortionId);
			outputEntity.setValue(CompensationOutputEntity.Field.TIME.name(),
					this.simulator.now());
			outputEntity.setValue(CompensationOutputEntity.Field.EXTORTION_ID.name(),
					extortionId);
			outputEntity.setValue(CompensationOutputEntity.Field.MAFIOSO_ID.name(),
					mafiosoId);
			outputEntity.setValue(
					ExtortionOutputEntity.Field.DENOUNCED_PUNISHMENT.name(), true);
			
		} else {
			
			if(this.affiliated) {
				NotDenouncePunishmentAffiliatedAction notDenouncePunishmentAction = new NotDenouncePunishmentAffiliatedAction(
						extortionId, this.id, this.stateId, mafiosoId, punishment);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						notDenouncePunishmentAction);
				this.sendMsg(msg);
				
			} else {
				NotDenouncePunishmentAction notDenouncePunishmentAction = new NotDenouncePunishmentAction(
						extortionId, this.id, this.stateId, mafiosoId, punishment);
				
				Message msg = new Message(this.simulator.now(), this.id, this.stateId,
						notDenouncePunishmentAction);
				this.sendMsg(msg);
			}
			
			// Output
			outputEntity.setValue(
					ExtortionOutputEntity.Field.DENOUNCED_PUNISHMENT.name(), false);
			
			outputEntity = OutputController.getInstance().getEntity(
					EntityType.COMPENSATION, extortionId);
			outputEntity.setValue(CompensationOutputEntity.Field.TIME.name(),
					this.simulator.now());
			outputEntity.setValue(CompensationOutputEntity.Field.EXTORTION_ID.name(),
					extortionId);
			outputEntity.setValue(CompensationOutputEntity.Field.MAFIOSO_ID.name(),
					mafiosoId);
			outputEntity.setValue(
					CompensationOutputEntity.Field.DENOUNCED_PUNISHMENT.name(), false);
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
			Message msg = new Message(this.simulator.now(), this.id, stateId,
					collaborate);
			this.sendMsg(msg);
			
			// Update State reputation as Finder
			this.stateFinderRep.updateReputation(action);
			
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
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.COMPENSATION,
				(int) action.getParam(StateCompensationAction.Param.EXTORTION_ID));
		
		double compensation = (double) action
				.getParam(StateCompensationAction.Param.COMPENSATION);
		
		this.wealth += compensation;
		
		outputEntity.setValue(
				CompensationOutputEntity.Field.STATE_COMPENSATED.name(), true);
		outputEntity.setValue(
				CompensationOutputEntity.Field.STATE_COMPENSATION.name(), compensation);
		outputEntity.setActive();
		
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
	
	
	@Override
	public void decideAffiliation() {
		
		this.normative.update();
		
		double probAffiliate;
		if(this.normative.getNorm(Norms.DENOUNCE.ordinal()).getStatus()
				.equals(NormStatus.GOAL)) {
			
			probAffiliate = this.normative.getNormSalience(Norms.DENOUNCE.ordinal());
			
		} else {
			
			probAffiliate = RandomUtil.nextDouble();
			
		}
		
		if(probAffiliate >= this.conf.getAffiliateThreshold()) {
			
			AffiliateRequestAction affiliation = new AffiliateRequestAction(this.id,
					this.ioId);
			Message msg = new Message(this.simulator.now(), this.id, this.ioId,
					affiliation);
			this.sendMsg(msg);
		}
	}
	
	
	@Override
	public void finalizeSim() {
		
		this.normative.update();
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.ENTREPRENEUR);
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.ENTREPRENEUR_ID.name(), this.id);
		
		outputEntity.setValue(EntrepreneurOutputEntity.Field.DEFAULT_WAGE.name(),
				this.defaultWage);
		
		outputEntity.setValue(EntrepreneurOutputEntity.Field.PRODUCT_PRICE.name(),
				this.productPrice);
		
		outputEntity.setValue(EntrepreneurOutputEntity.Field.WEALTH.name(),
				this.wealth);
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.ENTREPRENEUR_ID.name(), this.id);
		
		outputEntity.setValue(EntrepreneurOutputEntity.Field.AFFILIATED.name(),
				this.affiliated);
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.CRITICAL_COSTUMER.name(),
				this.criticalConsumers);
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.REPUTATION_STATE_FINDER.name(),
				this.stateFinderRep.getReputation());
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.REPUTATION_STATE_PROTECTOR.name(),
				this.stateProtectorRep.getReputation());
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.REPUTATION_MAFIA_PUNISHER.name(),
				this.mafiaPunisherRep.getReputation());
		
		outputEntity
				.setValue(EntrepreneurOutputEntity.Field.SALIENCE_PAY_EXTORTION.name(),
						this.normative.getNormSalience(Constants.Norms.PAY_EXTORTION
								.ordinal()));
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.SALIENCE_NOT_PAY_EXTORTION.name(),
				this.normative.getNormSalience(Constants.Norms.NOT_PAY_EXTORTION
						.ordinal()));
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.SALIENCE_DENOUNCE.name(),
				this.normative.getNormSalience(Constants.Norms.DENOUNCE.ordinal()));
		
		outputEntity.setValue(
				EntrepreneurOutputEntity.Field.SALIENCE_NOT_DENOUNCE.name(),
				this.normative.getNormSalience(Constants.Norms.NOT_DENOUNCE.ordinal()));
		
		outputEntity.setActive();
	}
	
	
	/**
	 * Set up the Entrepreneur as an IO affiliate
	 * 
	 * @param action
	 *          Accepted affiliation
	 * @return none
	 */
	private void affiliateAccepted(AffiliationAcceptedAction action) {
		
		int entrepreneurId = (int) action
				.getParam(AffiliationAcceptedAction.Param.ENTREPRENEUR_ID);
		
		if(entrepreneurId == this.id) {
			this.affiliated = true;
		}
		
	}
	
	
	/**
	 * Set up the Entrepreneur as an non-IO affiliate
	 * 
	 * @param action
	 *          Denied affiliation
	 * @return none
	 */
	private void affiliateDenied(AffiliationDeniedAction action) {
		
		int entrepreneurId = (int) action
				.getParam(AffiliationDeniedAction.Param.ENTREPRENEUR_ID);
		
		if(entrepreneurId == this.id) {
			this.affiliated = false;
		}
	}
	
	
	/**
	 * Spread action information to the Intermediary Organization
	 * 
	 * @param msg
	 *          Spread action message to the Intermediary Organization
	 * @return none
	 */
	private void spreadActionInformation(Message msg) {
		
		if(this.affiliated) {
			Message newMsg = new Message(this.simulator.now(), this.id, this.ioId,
					msg);
			this.sendMsg(newMsg);
		}
		
	}
	
	
	/**
	 * Critical consumers information received from the Intermediary Organization
	 * 
	 * @param action
	 *          CriticalConsumerInfo action
	 * @return none
	 */
	private void criticalConsumers(CriticalConsumerInfoAction action) {
		
		int entrepreneurId = (int) action
				.getParam(CriticalConsumerInfoAction.Param.ENTEPRENEUR_ID);
		
		if(this.id == entrepreneurId) {
			this.criticalConsumers = (double) action
					.getParam(CriticalConsumerInfoAction.Param.CRITICAL_CONSUMERS);
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
			
			// Extortion demand
			if(content instanceof ExtortionAction) {
				this.decidePayment((ExtortionAction) content);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
				
				// Collect extortion
			} else if(content instanceof CollectAction) {
				this.collectExtortion((CollectAction) content);
				
				// Mafia benefit
			} else if(content instanceof MafiaBenefitAction) {
				this.receiveMafiaBenefit((MafiaBenefitAction) content);
				
				// Mafia punishment
			} else if(content instanceof MafiaPunishmentAction) {
				this.receiveMafiaPunishment((MafiaPunishmentAction) content);
				
				// Normative process
				this.normative.input(msg);
				
				// Reputation
				this.mafiaPunisherRep.updateReputation((MafiaPunishmentAction) content);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
				
				// Collaboration request
			} else if(content instanceof CollaborationRequestAction) {
				this.decideCollaboration((CollaborationRequestAction) content);
				
				// State punishment
			} else if(content instanceof StatePunishmentAction) {
				this.receiveStatePunishment((StatePunishmentAction) content);
				
				// Normative process
				this.normative.input(msg);
				
				// State compensation
			} else if(content instanceof StateCompensationAction) {
				this.receiveStateCompensation((StateCompensationAction) content);
				
				// Reputation
				this.stateProtectorRep
						.updateReputation((StateCompensationAction) content);
				
				// Spread action to IO
				this.spreadActionInformation(msg);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				this.receiveBuy((BuyProductAction) content);
				
				// Affiliation accepted
			} else if(content instanceof AffiliationAcceptedAction) {
				this.affiliateAccepted((AffiliationAcceptedAction) content);
				
				// Affiliation denied
			} else if(content instanceof AffiliationDeniedAction) {
				this.affiliateDenied((AffiliationDeniedAction) content);
				
				// Normative Information
			} else if(content instanceof NormInvocationAction) {
				this.normative.input(msg);
				
				// Normative Sanction
			} else if(content instanceof NormSanctionAction) {
				this.normative.input(msg);
				
				// Critical Consumer information
			} else if(content instanceof CriticalConsumerInfoAction) {
				this.criticalConsumers((CriticalConsumerInfoAction) content);
				
				// Message
			} else if(content instanceof Message) {
				
				Message otherMsg = (Message) content;
				Object contentMsg = otherMsg.getContent();
				
				// Custody
				if(contentMsg instanceof CustodyAction) {
					// DO NOTHING
					
					// Imprisonment
				} else if(contentMsg instanceof ImprisonmentAction) {
					// Reputation
					this.stateProtectorRep
							.updateReputation((ImprisonmentAction) contentMsg);
					
					// State Compensation
				} else if(contentMsg instanceof StateCompensationAction) {
					// Reputation
					this.stateProtectorRep
							.updateReputation((StateCompensationAction) contentMsg);
					
					// State Punishment
				} else if(contentMsg instanceof StatePunishmentAction) {
					// Normative
					this.normative.input(msg);
					
					// Denounce Punishment
				} else if(contentMsg instanceof DenouncePunishmentAction) {
					// Normative
					this.normative.input(msg);
					
					// Reputation
					this.stateProtectorRep
							.updateReputation((DenouncePunishmentAction) contentMsg);
					
					// Pentito
				} else if(contentMsg instanceof PentitoAction) {
					
					PentitoAction action = (PentitoAction) contentMsg;
					
					@SuppressWarnings("unchecked")
					List<Integer> entrepreneursId = (List<Integer>) action
							.getParam(PentitoAction.Param.ENTREPRENEUR_LIST);
					for(Integer entrepreneurId : entrepreneursId) {
						
						// Create an Pay Extortion action to each Entrepreneur denounced via
						// Pentito to update the Norm Salience
						PayExtortionAction pay = new PayExtortionAction(0, 0,
								entrepreneurId, 0.0);
						Message newMsg = new Message(msg.getTime(), msg.getSender(),
								msg.getReceiver(), pay);
						this.normative.input(newMsg);
					}
					
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
				case Constants.PARAMETER_STATE_PUNISHMENT:
					if(set.getValue() instanceof Double) {
						this.statePunishment = ((Double) set.getValue() * this.defaultWage);
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
			
			// Affiliation accepted
			if(content instanceof AffiliationAcceptedAction) {
				this.affiliateAccepted((AffiliationAcceptedAction) content);
				
				// Affiliation denied
			} else if(content instanceof AffiliationDeniedAction) {
				this.affiliateDenied((AffiliationDeniedAction) content);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				// DO NOTHING
				
				// Collaboration Request
			} else if(content instanceof CollaborationRequestAction) {
				// Reputation
				this.stateFinderRep
						.updateReputation((CollaborationRequestAction) content);
				
				// Collaborate
			} else if(content instanceof CollaborateAction) {
				// DO NOTHING
				
				// Denounce extortion
			} else if(content instanceof DenounceExtortionAction) {
				// Normative
				this.normative.input(msg);
				
				// Reputation
				this.stateProtectorRep
						.updateReputation((DenounceExtortionAction) content);
				
				// Not Denounce extortion
			} else if(content instanceof NotDenounceExtortionAction) {
				// Normative
				this.normative.input(msg);
				
				// Reputation
				this.stateProtectorRep
						.updateReputation((NotDenounceExtortionAction) content);
				
				// Denounce punishment
			} else if(content instanceof DenouncePunishmentAction) {
				// Normative
				this.normative.input(msg);
				
				// Not Denounce punishment
			} else if(content instanceof NotDenouncePunishmentAction) {
				// Normative
				this.normative.input(msg);
				
				// Mafia benefit
			} else if(content instanceof MafiaBenefitAction) {
				// DO NOTHING
				
				// Mafia punishment
			} else if(content instanceof MafiaPunishmentAction) {
				// Normative
				this.normative.input(msg);
				
				// Reputation
				this.mafiaPunisherRep.updateReputation((MafiaPunishmentAction) content);
				
				// Extortion
			} else if(content instanceof ExtortionAction) {
				// Normative
				this.normative.input(msg);
				
				// Pay extortion
			} else if(content instanceof PayExtortionAction) {
				// Normative
				this.normative.input(msg);
				
				// Reputation
				this.stateFinderRep.updateReputation((PayExtortionAction) content);
				
				// Not pay extortion
			} else if(content instanceof NotPayExtortionAction) {
				// Normative
				this.normative.input(msg);
				
				// Reputation
				this.mafiaPunisherRep.updateReputation((NotPayExtortionAction) content);
				
				// State compensation
			} else if(content instanceof StateCompensationAction) {
				
				// Reputation
				this.stateProtectorRep
						.updateReputation((StateCompensationAction) content);
				
				// State punishment
			} else if(content instanceof StatePunishmentAction) {
				// DO NOTHING
				
			}
		}
	}
	
	
	/*******************************
	 * 
	 * Handle normative information
	 * 
	 *******************************/
	
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