package gloderss.agents.consumer;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionCategory.Discernability;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Issuer;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import emilia.modules.enforcement.NormEnforcementListener;
import emilia.modules.salience.DataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gloderss.Constants;
import gloderss.Constants.Actions;
import gloderss.Constants.Norms;
import gloderss.Constants.Sanctions;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.BuyNotPayExtortionAction;
import gloderss.actions.BuyPayExtortionAction;
import gloderss.actions.BuyProductAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.DeliverProductAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NormInvocationAction;
import gloderss.actions.NormSanctionAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotDenounceExtortionAffiliatedAction;
import gloderss.actions.NotDenouncePunishmentAction;
import gloderss.actions.NotDenouncePunishmentAffiliatedAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReputationInfoAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.CitizenAgent;
import gloderss.agents.consumer.normative.EmiliaControllerConsumer;
import gloderss.agents.consumer.normative.modules.enforcement.ReputationSanction;
import gloderss.agents.consumer.normative.modules.enforcement.SanctionSanction;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.ChangeConf;
import gloderss.conf.ConsumerConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormContentSet;
import gloderss.normative.entity.norm.NormEntity;
import gloderss.normative.entity.sanction.SanctionEntity;
import gloderss.output.AbstractEntity;
import gloderss.output.ConsumerOutputEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.PurchaseOutputEntity;
import gloderss.reputation.EntrepreneurReputation;
import gloderss.reputation.ReputationAbstract;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;

public class ConsumerAgent extends CitizenAgent implements IConsumer,
		NormEnforcementListener {
	
	private int															loggingTimeUnit;
	
	private int															numberEntrepreneursSearch;
	
	private double													reputationEntrepreneurThreshold;
	
	@SuppressWarnings("unused")
	private double													sanctionThreshold;
	
	private double													sanctionDiscernability;
	
	private double													individualWeight;
	
	private double													normativeWeight;
	
	private List<ChangeConf>								changesConf;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private EntrepreneurReputation					entrepreneurRep;
	
	private EmiliaControllerConsumer				normative;
	
	private PDFAbstract											buyPDF;
	
	private Map<Integer, Integer>						numberProducts;
	
	private Map<Integer, Double>						paidPrice;
	
	
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
		
		this.loggingTimeUnit = conf.getLoggingTimeUnit();
		
		this.numberEntrepreneursSearch = conf.getNumberEntrepreneursSearch();
		
		this.reputationEntrepreneurThreshold = conf.getReputationConf()
				.getEntrepreneurRepThreshold();
		
		this.sanctionThreshold = conf.getSanctionConf().getThreshold();
		
		this.sanctionDiscernability = conf.getSanctionConf().getDiscernability();
		
		this.individualWeight = conf.getIndividualWeight();
		
		this.normativeWeight = conf.getNormativeWeight();
		
		this.changesConf = conf.getChangesConf();
		
		this.entrepreneurs = new HashMap<Integer, EntrepreneurAgent>();
		this.entrepreneurRep = new EntrepreneurReputation(conf.getReputationConf()
				.getEntrepreneurRep());
		this.buyPDF = PDFAbstract.getInstance(conf.getBuyPDF());
		this.numberProducts = new HashMap<Integer, Integer>();
		this.paidPrice = new HashMap<Integer, Double>();
		
		/**
		 * Normative
		 */
		this.normative = new EmiliaControllerConsumer(id, conf.getNormativeXML(),
				conf.getNormativeXSD(), conf.getSanctionConf().getThreshold());
		this.normative.init();
		this.normative.registerNormEnforcement(this);
		
		// Create Norms
		List<SanctionEntityAbstract> sanctions;
		NormContent normContent;
		NormEntityAbstract norm;
		ReputationSanction repSanction;
		SanctionSanction sancSanction;
		SanctionCategory category;
		SanctionEntityAbstract sanction;
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
		
		// PAY Norm
		normContent = new NormContent(Actions.PAY_EXTORTION,
				Actions.NOT_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.PAY_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		// REPUTATION SANCTION
		repSanction = new ReputationSanction(this.id, this.entrepreneurRep);
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.INDIRECT, Polarity.NEGATIVE, Discernability.UNOBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.REPUTATION_ENTREPRENEUR.ordinal(),
				category, SanctionStatus.ACTIVE, repSanction);
		
		sanctions.add(sanction);
		
		// SANCTION SANCTION
		sancSanction = new SanctionSanction(this.id, Norms.PAY_EXTORTION.name());
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.DIRECT, Polarity.NEGATIVE, Discernability.OBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.NORM_SANCTION.ordinal(), category,
				SanctionStatus.ACTIVE, sancSanction);
		
		sanctions.add(sanction);
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_PAY Norm
		normContent = new NormContent(Actions.NOT_PAY_EXTORTION,
				Actions.PAY_EXTORTION);
		
		norm = new NormEntity(Norms.NOT_PAY_EXTORTION.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		// REPUTATION SANCTION
		repSanction = new ReputationSanction(this.id, this.entrepreneurRep);
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.INDIRECT, Polarity.NEGATIVE, Discernability.UNOBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.REPUTATION_ENTREPRENEUR.ordinal(),
				category, SanctionStatus.ACTIVE, repSanction);
		
		sanctions.add(sanction);
		
		// SANCTION SANCTION
		sancSanction = new SanctionSanction(this.id, Norms.NOT_PAY_EXTORTION.name());
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.DIRECT, Polarity.NEGATIVE, Discernability.OBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.NORM_SANCTION.ordinal(), category,
				SanctionStatus.ACTIVE, sancSanction);
		
		sanctions.add(sanction);
		
		normsSanctions.put(norm, sanctions);
		
		// DENOUNCE Norm
		List<Actions> actions = new ArrayList<Actions>();
		actions.add(Actions.DENOUNCE_EXTORTION);
		actions.add(Actions.DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.DENOUNCE_PUNISHMENT);
		actions.add(Actions.DENOUNCE_PUNISHMENT_AFFILIATED);
		
		List<Actions> noActions = new ArrayList<Actions>();
		noActions.add(Actions.NOT_DENOUNCE_EXTORTION);
		noActions.add(Actions.NOT_DENOUNCE_EXTORTION_AFFILIATED);
		noActions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		noActions.add(Actions.NOT_DENOUNCE_PUNISHMENT_AFFILIATED);
		
		NormContentSet normContentSet = new NormContentSet(actions, noActions);
		
		norm = new NormEntity(Norms.DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		// REPUTATION SANCTION
		repSanction = new ReputationSanction(this.id, this.entrepreneurRep);
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.INDIRECT, Polarity.NEGATIVE, Discernability.UNOBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.REPUTATION_ENTREPRENEUR.ordinal(),
				category, SanctionStatus.ACTIVE, repSanction);
		
		sanctions.add(sanction);
		
		// SANCTION SANCTION
		sancSanction = new SanctionSanction(this.id, Norms.DENOUNCE.name());
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.DIRECT, Polarity.NEGATIVE, Discernability.OBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.NORM_SANCTION.ordinal(), category,
				SanctionStatus.ACTIVE, sancSanction);
		
		sanctions.add(sanction);
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_DENOUNCE Norm
		actions = new ArrayList<Actions>();
		actions.add(Actions.NOT_DENOUNCE_EXTORTION);
		actions.add(Actions.NOT_DENOUNCE_EXTORTION_AFFILIATED);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT_AFFILIATED);
		
		noActions = new ArrayList<Actions>();
		noActions.add(Actions.DENOUNCE_EXTORTION);
		noActions.add(Actions.DENOUNCE_EXTORTION_AFFILIATED);
		noActions.add(Actions.DENOUNCE_PUNISHMENT);
		noActions.add(Actions.DENOUNCE_PUNISHMENT_AFFILIATED);
		
		normContentSet = new NormContentSet(actions, noActions);
		
		norm = new NormEntity(Norms.NOT_DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		// REPUTATION SANCTION
		repSanction = new ReputationSanction(this.id, this.entrepreneurRep);
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.INDIRECT, Polarity.NEGATIVE, Discernability.UNOBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.REPUTATION_ENTREPRENEUR.ordinal(),
				category, SanctionStatus.ACTIVE, repSanction);
		
		sanctions.add(sanction);
		
		// SANCTION SANCTION
		sancSanction = new SanctionSanction(this.id, Norms.NOT_DENOUNCE.name());
		
		category = new SanctionCategory(Issuer.INFORMAL, Locus.OTHER_DIRECTED,
				Mode.DIRECT, Polarity.NEGATIVE, Discernability.OBSTRUSIVE);
		
		sanction = new SanctionEntity(Sanctions.NORM_SANCTION.ordinal(), category,
				SanctionStatus.ACTIVE, sancSanction);
		
		sanctions.add(sanction);
		
		normsSanctions.put(norm, sanctions);
		
		// BUY_NOT_PAY Norm
		normContent = new NormContent(Actions.BUY_NOT_PAY_EXTORTION,
				Actions.BUY_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal(),
				NormType.SOCIAL, NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// BUY_PAY Norm
		normContent = new NormContent(Actions.BUY_PAY_EXTORTION,
				Actions.BUY_NOT_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal(),
				NormType.SOCIAL, NormSource.DISTRIBUTED, NormStatus.GOAL, normContent);
		
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
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public Map<Integer, EntrepreneurAgent> getEntrepreneurs() {
		return this.entrepreneurs;
	}
	
	
	public void setEntrepreneurs(Map<Integer, EntrepreneurAgent> entrepreneurs) {
		this.entrepreneurs = entrepreneurs;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		
		Event event = new Event(this.simulator.now() + this.buyPDF.nextValue(),
				this, Constants.EVENT_BUY_PRODUCT);
		this.simulator.insert(event);
		
		event = new Event(this.simulator.now(), this,
				Constants.EVENT_LOGGING_CONSUMERS);
		this.simulator.insert(event);
		
		// Schedule changes
		for(ChangeConf change : this.changesConf) {
			event = new Event(change.getTime(), this, change.getParameter(), change);
			this.simulator.insert(event);
		}
	}
	
	
	@Override
	public void buyProduct() {
		
		this.normative.update();
		
		// Obtains a Purchase output
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.PURCHASE);
		outputEntity.setValue(PurchaseOutputEntity.Field.TIME.name(),
				this.simulator.now());
		outputEntity.setValue(PurchaseOutputEntity.Field.CONSUMER_ID.name(),
				this.id);
		
		// Randomly select a set of Entrepreneurs to search price
		double maxPrice = 0.0;
		Map<Integer, Double> buyList = new HashMap<Integer, Double>();
		while((buyList.size() < this.entrepreneurs.size())
				&& (buyList.size() < this.numberEntrepreneursSearch)) {
			
			int entrepreneurId = (int) this.entrepreneurs.keySet().toArray()[RandomUtil
					.nextIntFromTo(0, (this.entrepreneurs.size() - 1))];
			
			if(!buyList.containsKey(entrepreneurId)) {
				
				InfoRequest info = new InfoRequest(this.id, entrepreneurId,
						Constants.REQUEST_PRODUCT_PRICE);
				double price = (double) this.sendInfo(info);
				
				buyList.put(entrepreneurId, price);
				
				if(price > maxPrice) {
					maxPrice = price;
				}
			}
		}
		
		// BUY PAYING ENTREPRENEURS norm active
		boolean buyPayExtortionStatus = false;
		if(this.normative.getNorm(Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal())
				.getStatus().equals(NormStatus.GOAL)) {
			buyPayExtortionStatus = true;
		}
		
		// NOT BUY PAYING ENTREPRENEURS norm active
		boolean notBuyPayExtortionStatus = false;
		if(this.normative
				.getNorm(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal()).getStatus()
				.equals(NormStatus.GOAL)) {
			notBuyPayExtortionStatus = true;
		}
		
		double buyPayExtortionSalience = this.normative
				.getNormSalience(Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal());
		
		double buyNotPayExtortionSalience = this.normative
				.getNormSalience(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal());
		
		// Calculate neighbors' average reputation
		int numRep = 0;
		double avgRep = 0.0;
		for(Integer neighborId : this.neighbors) {
			if(this.entrepreneurs.containsKey(neighborId)) {
				if(!this.entrepreneurRep.isUnknown(neighborId)) {
					avgRep += this.entrepreneurRep.getReputation(neighborId);
					numRep++;
				}
			}
		}
		
		if(numRep > 0) {
			avgRep /= (double) numRep;
		} else {
			avgRep = this.entrepreneurRep.getUnknownValue();
		}
		
		@SuppressWarnings("unused")
		String outputResearched = new String();
		
		// Score each Entrepreneur
		double maxScore = 0.0;
		double paidPrice = 0.0;
		int selectedEntrepreneurId = -1;
		for(Integer entrepreneurId : buyList.keySet()) {
			double price = buyList.get(entrepreneurId);
			
			double reputation;
			if(!this.entrepreneurRep.isUnknown(entrepreneurId)) {
				reputation = this.entrepreneurRep.getReputation(entrepreneurId);
			} else {
				reputation = avgRep;
			}
			
			double score = (1 - (price / maxPrice)) * this.individualWeight;
			// BUY PAYING ENTREPRENEURS and NOT BUY PAYING ENTREPRENEURS norms active
			if((buyPayExtortionStatus) && (notBuyPayExtortionStatus)) {
				
				if(buyNotPayExtortionSalience > buyPayExtortionSalience) {
					
					score += (buyNotPayExtortionSalience * this.normativeWeight)
							* reputation;
					
				} else {
					
					score += (buyPayExtortionSalience * this.normativeWeight)
							* (1 - reputation);
					
				}
				
				// BUY PAYING ENTREPRENEURS norm active
			} else if(buyPayExtortionStatus) {
				
				score += (buyPayExtortionSalience * this.normativeWeight)
						* (1 - reputation);
				
				// NOT BUY PAYING ENTREPRENEURS norm active
			} else if(notBuyPayExtortionStatus) {
				
				score += (buyNotPayExtortionSalience * this.normativeWeight)
						* reputation;
				
				// NONE norm active
			} else {
				
				score = (1 - (price / maxPrice));
				
			}
			
			if(score > maxScore) {
				maxScore = score;
				paidPrice = price;
				selectedEntrepreneurId = entrepreneurId;
			}
			
			outputResearched += "[" + entrepreneurId + "|"
					+ String.format("%.2f", price) + "|"
					+ String.format("%.2f", reputation) + "|"
					+ String.format("%.2f", score) + "]";
		}
		
		if(selectedEntrepreneurId > -1) {
			BuyProductAction buy = new BuyProductAction(this.id,
					selectedEntrepreneurId);
			
			Message msg = new Message(this.simulator.now(), this.id,
					selectedEntrepreneurId, buy);
			this.sendMsg(msg);
			
			// Normative
			Message newMsg;
			if(this.entrepreneurRep.getReputation(selectedEntrepreneurId) < this.reputationEntrepreneurThreshold) {
				
				BuyPayExtortionAction newAction = new BuyPayExtortionAction(this.id,
						selectedEntrepreneurId);
				
				newMsg = new Message(msg.getTime(), msg.getSender(), msg.getReceiver(),
						newAction);
				
			} else {
				
				BuyNotPayExtortionAction newAction = new BuyNotPayExtortionAction(
						this.id, selectedEntrepreneurId);
				
				newMsg = new Message(msg.getTime(), msg.getSender(), msg.getReceiver(),
						newAction);
				
			}
			this.normative.input(newMsg);
			
			// TODO
			// outputEntity.setValue(PurchaseOutputEntity.Field.RESEARCHED.name(),
			// outputResearched);
			outputEntity.setValue(PurchaseOutputEntity.Field.ENTREPRENEUR_ID.name(),
					selectedEntrepreneurId);
			outputEntity.setValue(
					PurchaseOutputEntity.Field.BUY_PAY_EXTORTION_SALIENCE.name(),
					buyPayExtortionSalience);
			outputEntity.setValue(
					PurchaseOutputEntity.Field.BUY_NOT_PAY_EXTORTION_SALIENCE.name(),
					buyNotPayExtortionSalience);
			outputEntity.setValue(PurchaseOutputEntity.Field.MAX_PRICE.name(),
					maxPrice);
			outputEntity.setValue(PurchaseOutputEntity.Field.PAID_PRICE.name(),
					paidPrice);
			outputEntity.setActive();
		}
	}
	
	
	@Override
	public void receiveProduct(DeliverProductAction action) {
		
		int entrepreneurId = (int) action
				.getParam(DeliverProductAction.Param.ENTREPRENEUR_ID);
		
		double cost = (double) action.getParam(DeliverProductAction.Param.COST);
		
		int numProd = 1;
		if(this.numberProducts.containsKey(entrepreneurId)) {
			numProd += this.numberProducts.get(entrepreneurId);
		}
		this.numberProducts.put(entrepreneurId, numProd);
		
		double paid = cost;
		if(this.paidPrice.containsKey(entrepreneurId)) {
			paid += this.paidPrice.get(entrepreneurId);
		}
		this.paidPrice.put(entrepreneurId, paid);
		
		Event event = new Event(this.simulator.now() + this.buyPDF.nextValue(),
				this, Constants.EVENT_BUY_PRODUCT);
		this.simulator.insert(event);
	}
	
	
	/**
	 * Log consumers data
	 * 
	 * @param none
	 * @return none
	 */
	private void loggingConsumers() {
		this.normative.update();
		
		AbstractEntity outputEntity = OutputController.getInstance().getEntity(
				EntityType.CONSUMER);
		outputEntity.setValue(ConsumerOutputEntity.Field.TIME.name(),
				(int) this.simulator.now());
		outputEntity.setValue(ConsumerOutputEntity.Field.CONSUMER_ID.name(),
				this.id);
		
		String rep = new String();
		for(Integer entrepreneurId : this.entrepreneurs.keySet()) {
			
			if(this.entrepreneurRep.isUnknown(entrepreneurId)) {
				rep += "[" + entrepreneurId + "|"
						+ this.entrepreneurRep.getReputation(entrepreneurId) + "] ";
			} else {
				rep += "[" + entrepreneurId + "|"
						+ this.entrepreneurRep.getUnknownValue() + "] ";
			}
		}
		if(!rep.isEmpty()) {
			rep = rep.substring(0, (rep.length() - 1));
		}
		// TODO
		// outputEntity.setValue(ConsumerOutputEntity.Field.ENTREPRENEUR_REP.name(),
		// rep);
		
		int numProducts = 0;
		for(Integer numProd : this.numberProducts.values()) {
			numProducts += numProd;
		}
		outputEntity.setValue(ConsumerOutputEntity.Field.NUMBER_PRODUCTS.name(),
				numProducts);
		
		double totalPrice = 0.0;
		for(Double paidPrice : this.paidPrice.values()) {
			totalPrice += paidPrice;
		}
		outputEntity.setValue(ConsumerOutputEntity.Field.TOTAL_PRICE.name(),
				totalPrice);
		
		outputEntity.setValue(
				ConsumerOutputEntity.Field.SALIENCE_BUY_FROM_NOT_PAYING_ENTREPRENEURS
						.name(), this.normative
						.getNormSalience(Constants.Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS
								.ordinal()));
		
		outputEntity.setValue(
				ConsumerOutputEntity.Field.SALIENCE_BUY_FROM_PAYING_ENTREPRENEURS
						.name(), this.normative
						.getNormSalience(Constants.Norms.BUY_FROM_PAYING_ENTREPRENEURS
								.ordinal()));
		
		outputEntity.setValue(ConsumerOutputEntity.Field.SALIENCE_NOT_PAY_EXTORTION
				.name(), this.normative
				.getNormSalience(Constants.Norms.NOT_PAY_EXTORTION.ordinal()));
		
		outputEntity
				.setValue(ConsumerOutputEntity.Field.SALIENCE_PAY_EXTORTION.name(),
						this.normative.getNormSalience(Constants.Norms.PAY_EXTORTION
								.ordinal()));
		
		outputEntity.setValue(
				ConsumerOutputEntity.Field.SALIENCE_NOT_DENOUNCE.name(),
				this.normative.getNormSalience(Constants.Norms.NOT_DENOUNCE.ordinal()));
		
		outputEntity.setValue(ConsumerOutputEntity.Field.SALIENCE_DENOUNCE.name(),
				this.normative.getNormSalience(Constants.Norms.DENOUNCE.ordinal()));
		
		outputEntity.setActive();
		
		Event event = new Event(this.simulator.now() + this.loggingTimeUnit, this,
				Constants.EVENT_LOGGING_CONSUMERS);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void finalizeSim() {
		this.loggingConsumers();
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
			
			// Deliver product
			if(content instanceof DeliverProductAction) {
				this.receiveProduct((DeliverProductAction) content);
				
				// Normative Information
			} else if(content instanceof NormInvocationAction) {
				this.normative.input(msg);
				
				// Normative Sanction
			} else if(content instanceof NormSanctionAction) {
				this.normative.input(msg);
				
				this.entrepreneurRep.updateReputation((NormSanctionAction) content);
				
				// Reputation Information
			} else if(content instanceof ReputationInfoAction) {
				this.entrepreneurRep.updateReputation((ReputationInfoAction) content);
				
				// Message
			} else if(content instanceof Message) {
				
				Message otherMsg = (Message) content;
				Object contentMsg = otherMsg.getContent();
				
				// Denounce Punishment
				if(contentMsg instanceof DenouncePunishmentAction) {
					// Reputation
					this.entrepreneurRep
							.updateReputation((DenouncePunishmentAction) contentMsg);
					
					// Normative
					this.normative.input(msg);
					
					// Denounce Punishment Affiliated
				} else if(contentMsg instanceof DenouncePunishmentAffiliatedAction) {
					// Reputation
					this.entrepreneurRep
							.updateReputation((DenouncePunishmentAffiliatedAction) contentMsg);
					
					// Normative
					this.normative.input(msg);
					
					// Pentito
				} else if(contentMsg instanceof PentitoAction) {
					
					PentitoAction action = (PentitoAction) contentMsg;
					
					@SuppressWarnings("unchecked")
					List<Integer> entrepreneursId = (List<Integer>) action
							.getParam(PentitoAction.Param.ENTREPRENEUR_LIST);
					for(Integer entrepreneurId : entrepreneursId) {
						this.entrepreneurRep.setReputation(entrepreneurId,
								ReputationAbstract.MIN);
					}
					
					// Custody
				} else if(contentMsg instanceof CustodyAction) {
					// NOTHING
					
					// Imprisonment
				} else if(contentMsg instanceof ImprisonmentAction) {
					// NOTHING
					
					// State Compensation
				} else if(contentMsg instanceof StateCompensationAction) {
					// Reputation
					this.entrepreneurRep
							.updateReputation((StateCompensationAction) contentMsg);
					
					// State Punishment
				} else if(contentMsg instanceof StatePunishmentAction) {
					// Normative
					this.normative.input(msg);
					
					// Reputation
					this.entrepreneurRep
							.updateReputation((StatePunishmentAction) contentMsg);
					
				}
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
			}
			
		} else if(info.getType().equals(InfoAbstract.Type.SET)) {
			
		}
		
		return infoRequested;
	}
	
	
	@Override
	public void handleObservation(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (!msg.getReceiver().contains(this.id))) {
			
			// Affiliation Accepted
			if(content instanceof AffiliationAcceptedAction) {
				// Reputation
				this.entrepreneurRep
						.updateReputation((AffiliationAcceptedAction) content);
				
				// Affiliation Denied
			} else if(content instanceof AffiliationDeniedAction) {
				// Reputation
				this.entrepreneurRep
						.updateReputation((AffiliationDeniedAction) content);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				
				BuyProductAction action = (BuyProductAction) content;
				
				int consumerId = (int) action
						.getParam(BuyProductAction.Param.CONSUMER_ID);
				
				int entrepreneurId = (int) action
						.getParam(BuyProductAction.Param.ENTREPRENEUR_ID);
				
				Message newMsg;
				if(this.entrepreneurRep.getReputation(entrepreneurId) < this.reputationEntrepreneurThreshold) {
					
					BuyPayExtortionAction newAction = new BuyPayExtortionAction(
							consumerId, entrepreneurId);
					
					newMsg = new Message(msg.getTime(), msg.getSender(),
							msg.getReceiver(), newAction);
					
				} else {
					
					BuyNotPayExtortionAction newAction = new BuyNotPayExtortionAction(
							consumerId, entrepreneurId);
					
					newMsg = new Message(msg.getTime(), msg.getSender(),
							msg.getReceiver(), newAction);
					
				}
				
				// Normative
				this.normative.input(newMsg);
				
				// Collaboration Request
			} else if(content instanceof CollaborationRequestAction) {
				this.entrepreneurRep
						.updateReputation((CollaborationRequestAction) content);
				
				// Collaborate
			} else if(content instanceof CollaborateAction) {
				this.entrepreneurRep.updateReputation((CollaborateAction) content);
				
				// Denounce extortion
			} else if(content instanceof DenounceExtortionAction) {
				this.entrepreneurRep
						.updateReputation((DenounceExtortionAction) content);
				
				this.normative.input(msg);
				
				// Denounce extortion Affiliated
			} else if(content instanceof DenounceExtortionAffiliatedAction) {
				this.entrepreneurRep
						.updateReputation((DenounceExtortionAffiliatedAction) content);
				
				this.normative.input(msg);
				
				// Not Denounce extortion
			} else if(content instanceof NotDenounceExtortionAction) {
				this.entrepreneurRep
						.updateReputation((NotDenounceExtortionAction) content);
				
				this.normative.input(msg);
				
				// Not Denounce extortion Affiliated
			} else if(content instanceof NotDenounceExtortionAffiliatedAction) {
				this.entrepreneurRep
						.updateReputation((NotDenounceExtortionAffiliatedAction) content);
				
				this.normative.input(msg);
				
				// Denounce punishment
			} else if(content instanceof DenouncePunishmentAction) {
				this.entrepreneurRep
						.updateReputation((DenouncePunishmentAction) content);
				
				this.normative.input(msg);
				
				// Denounce punishment Affiliated
			} else if(content instanceof DenouncePunishmentAffiliatedAction) {
				this.entrepreneurRep
						.updateReputation((DenouncePunishmentAffiliatedAction) content);
				
				this.normative.input(msg);
				
				// Not Denounce punishment
			} else if(content instanceof NotDenouncePunishmentAction) {
				this.entrepreneurRep
						.updateReputation((NotDenouncePunishmentAction) content);
				
				this.normative.input(msg);
				
				// Not Denounce punishment Affiliated
			} else if(content instanceof NotDenouncePunishmentAffiliatedAction) {
				this.entrepreneurRep
						.updateReputation((NotDenouncePunishmentAffiliatedAction) content);
				
				this.normative.input(msg);
				
				// Mafia pay benefit
			} else if(content instanceof MafiaBenefitAction) {
				this.entrepreneurRep.updateReputation((MafiaBenefitAction) content);
				
			} else if(content instanceof MafiaPunishmentAction) {
				this.entrepreneurRep.updateReputation((MafiaPunishmentAction) content);
				
			} else if(content instanceof PayExtortionAction) {
				this.entrepreneurRep.updateReputation((PayExtortionAction) content);
				
				this.normative.input(msg);
				
				// Do not pay extortion
			} else if(content instanceof NotPayExtortionAction) {
				this.entrepreneurRep.updateReputation((NotPayExtortionAction) content);
				
				this.normative.input(msg);
				
				// State compensate punishment
			} else if(content instanceof StateCompensationAction) {
				this.entrepreneurRep
						.updateReputation((StateCompensationAction) content);
				
				// State punish
			} else if(content instanceof StatePunishmentAction) {
				this.entrepreneurRep.updateReputation((StatePunishmentAction) content);
				
				this.normative.input(msg);
				
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
		
		if(sanction.getContent() instanceof ReputationSanction) {
			ReputationSanction rep = (ReputationSanction) sanction.getContent();
			ReputationInfoAction action = (ReputationInfoAction) rep.getSanction();
			for(Integer neighborId : this.neighbors) {
				if(!this.entrepreneurs.containsKey(neighborId)) {
					Message msg = new Message(this.simulator.now(), this.id, neighborId,
							action);
					this.sendMsg(msg);
				}
			}
		} else if(sanction.getContent() instanceof SanctionSanction) {
			SanctionSanction sanc = (SanctionSanction) sanction.getContent();
			NormSanctionAction action = (NormSanctionAction) sanc.getSanction();
			int entrepreneurId = (int) action
					.getParam(NormSanctionAction.Param.ENTREPRENEUR_ID);
			for(Integer neighborId : this.neighbors) {
				if(entrepreneurId != neighborId) {
					Message msg = new Message(this.simulator.now(), this.id, neighborId,
							action);
					this.sendMsg(msg);
				}
			}
			
			// The sanction may be discernible to the target
			if(RandomUtil.nextDouble() < this.sanctionDiscernability) {
				Message msg = new Message(this.simulator.now(), this.id,
						entrepreneurId, action);
				this.sendMsg(msg);
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
		
		ChangeConf change = null;
		if((event.getParameter() != null)
				&& (event.getParameter() instanceof ChangeConf)) {
			change = (ChangeConf) event.getParameter();
		}
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_BUY_PRODUCT:
				this.buyProduct();
				break;
			case Constants.EVENT_LOGGING_CONSUMERS:
				this.loggingConsumers();
				break;
			case Constants.TAG_CONSUMER_LOGGING_TIME_UNIT:
				if(change != null) {
					this.loggingTimeUnit = Integer.valueOf(change.getValue());
				}
				break;
			case Constants.TAG_CONSUMER_BUY_PDF:
				if(change != null) {
					this.buyPDF = PDFAbstract.getInstance(change.getValue());
				}
				break;
			case Constants.TAG_CONSUMER_NUMBER_ENTREPRENEURS_SEARCH:
				if(change != null) {
					this.numberEntrepreneursSearch = Integer.valueOf(change.getValue());
				}
				break;
			case Constants.TAG_CONSUMER_REPUTATION_ENTREPRENEUR_THRESHOLD:
				if(change != null) {
					this.reputationEntrepreneurThreshold = Double.valueOf(change
							.getValue());
				}
				break;
			case Constants.TAG_CONSUMER_SANCTION_THRESHOLD:
				if(change != null) {
					this.sanctionThreshold = Double.valueOf(change.getValue());
				}
				break;
			case Constants.TAG_CONSUMER_SANCTION_DISCERNABILITY:
				if(change != null) {
					this.sanctionDiscernability = Double.valueOf(change.getValue());
				}
				break;
			case Constants.TAG_NORMATIVE_INDIVIDUAL_WEIGHT:
				if(change != null) {
					this.individualWeight = Double.valueOf(change.getValue());
				}
				break;
			case Constants.TAG_NORMATIVE_NORMATIVE_WEIGHT:
				if(change != null) {
					this.normativeWeight = Double.valueOf(change.getValue());
				}
				break;
		}
	}
}