package gloderss.agents.consumer;

import emilia.EmiliaController;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import emilia.modules.salience.DataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gloderss.Constants;
import gloderss.Constants.Actions;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
import gloderss.actions.BuyNotPayExtortionAction;
import gloderss.actions.BuyPayExtortionAction;
import gloderss.actions.BuyProductAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.DeliverProductAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.CitizenAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.ConsumerConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormContentSet;
import gloderss.normative.entity.norm.NormEntity;
import gloderss.reputation.EntrepreneurPayerReputation;
import gloderss.reputation.ReputationAbstract;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;

public class ConsumerAgent extends CitizenAgent implements IConsumer,
		NormEnforcementListener {
	
	private ConsumerConf										conf;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private EntrepreneurPayerReputation			entrepreneurPayerRep;
	
	private EmiliaController								normative;
	
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
		this.conf = conf;
		this.entrepreneurs = new HashMap<Integer, EntrepreneurAgent>();
		this.entrepreneurPayerRep = new EntrepreneurPayerReputation(conf
				.getReputationConf().getEntrepreneurPayer());
		this.buyPDF = PDFAbstract.getInstance(conf.getBuyPDF());
		this.numberProducts = new HashMap<Integer, Integer>();
		this.paidPrice = new HashMap<Integer, Double>();
		
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
		actions.add(Actions.DENOUNCE_PUNISHMENT);
		
		List<Actions> noActions = new ArrayList<Actions>();
		actions.add(Actions.NOT_DENOUNCE_EXTORTION);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		
		NormContentSet normContentSet = new NormContentSet(actions, noActions);
		
		norm = new NormEntity(Norms.DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// NOT_DENOUNCE Norm
		actions = new ArrayList<Actions>();
		actions.add(Actions.DENOUNCE_EXTORTION);
		actions.add(Actions.DENOUNCE_PUNISHMENT);
		
		noActions = new ArrayList<Actions>();
		actions.add(Actions.NOT_DENOUNCE_EXTORTION);
		actions.add(Actions.NOT_DENOUNCE_PUNISHMENT);
		
		normContentSet = new NormContentSet(noActions, actions);
		
		norm = new NormEntity(Norms.NOT_DENOUNCE.ordinal(), NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContentSet);
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
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
	}
	
	
	@Override
	public void buyProduct() {
		
		// Randomly select a set of Entrepreneurs to search price
		double maxPrice = 0.0;
		Map<Integer, Double> buyList = new HashMap<Integer, Double>();
		while((buyList.size() < this.entrepreneurs.size())
				&& (buyList.size() < this.conf.getNumberEntrepreneursSearch())) {
			
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
		
		this.normative.update();
		
		double buyPayExtortionSalience = this.normative
				.getNormSalience(Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal());
		
		double buyNotPayExtortionSalience = this.normative
				.getNormSalience(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal());
		
		// Calculate neighbors' average reputation
		int numRep = 0;
		double avgRep = 0.0;
		for(Integer neighborId : this.neighbors) {
			if(this.entrepreneurs.containsKey(neighborId)) {
				if(!this.entrepreneurPayerRep.isUnknown(neighborId)) {
					avgRep += this.entrepreneurPayerRep.getReputation(neighborId);
					numRep++;
				}
			}
		}
		
		if(numRep > 0) {
			avgRep /= (double) numRep;
		} else {
			avgRep = this.entrepreneurPayerRep.getUnknownValue();
		}
		
		// Score each Entrepreneur
		double maxScore = 0.0;
		int selectedEntrepreneurId = -1;
		for(Integer entrepreneurId : buyList.keySet()) {
			double price = buyList.get(entrepreneurId);
			
			double reputation;
			if(!this.entrepreneurPayerRep.isUnknown(entrepreneurId)) {
				reputation = this.entrepreneurPayerRep.getReputation(entrepreneurId);
			} else {
				reputation = avgRep;
			}
			
			double score = (1 - (price / maxPrice)) * this.conf.getIndividualWeight();
			if(buyNotPayExtortionSalience > buyPayExtortionSalience) {
				
				score += (buyNotPayExtortionSalience * this.conf.getNormativeWeight())
						* reputation;
				
			} else {
				
				score += (buyPayExtortionSalience * this.conf.getNormativeWeight())
						* (1 - reputation);
				
			}
			
			if(score > maxScore) {
				maxScore = score;
				selectedEntrepreneurId = entrepreneurId;
			}
		}
		
		if(selectedEntrepreneurId > -1) {
			BuyProductAction buy = new BuyProductAction(this.id,
					selectedEntrepreneurId);
			
			Message msg = new Message(this.simulator.now(), this.id,
					selectedEntrepreneurId, buy);
			this.sendMsg(msg);
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
	
	
	/*******************************
	 * 
	 * Handle communication requests
	 * 
	 *******************************/
	
	@Override
	public synchronized void handleMessage(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (msg.getReceiver().contains(this.id))) {
			
			if(content instanceof DeliverProductAction) {
				this.receiveProduct((DeliverProductAction) content);
				
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
				
				AffiliationAcceptedAction action = (AffiliationAcceptedAction) content;
				int entrepreneurId = (int) action
						.getParam(AffiliationAcceptedAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurPayerRep.setReputation(entrepreneurId,
						ReputationAbstract.MAX);
				
				// Affiliation Denied
			} else if(content instanceof AffiliationDeniedAction) {
				
				AffiliationDeniedAction action = (AffiliationDeniedAction) content;
				int entrepreneurId = (int) action
						.getParam(AffiliationDeniedAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurPayerRep.setReputation(entrepreneurId,
						ReputationAbstract.MIN);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				
				BuyProductAction action = (BuyProductAction) content;
				
				int consumerId = (int) action
						.getParam(BuyProductAction.Param.CONSUMER_ID);
				
				int entrepreneurId = (int) action
						.getParam(BuyProductAction.Param.ENTREPRENEUR_ID);
				
				Message newMsg;
				if(this.entrepreneurPayerRep.getReputation(entrepreneurId) > this.conf
						.getReputationConf().getEntrepreneurPayerThreshold()) {
					
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
				
				this.normative.input(newMsg);
				
				// Collaborate
			} else if(content instanceof CollaborateAction) {
				
				CollaborateAction action = (CollaborateAction) content;
				int entrepreneurId = (int) action
						.getParam(CollaborateAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurPayerRep.setReputation(entrepreneurId,
						ReputationAbstract.MIN);
				
				// Denounce extortion
			} else if(content instanceof DenounceExtortionAction) {
				this.normative.input(msg);
				this.normative.update();
				
				// TODO
				// Increase Entrepreneur reputation
				
				// Denounce punishment
			} else if(content instanceof DenouncePunishmentAction) {
				this.normative.input(msg);
				// TODO
				// Increase Entrepreneur reputation
				
				// Mafia pay benefit
			} else if(content instanceof MafiaBenefitAction) {
				// TODO
				// Decrease Entrepreneur reputation
				
				// Mafia punish
			} else if(content instanceof MafiaPunishmentAction) {
				// TODO
				// Increase Entrepreneur reputation
				
				// Pay extortion
			} else if(content instanceof PayExtortionAction) {
				this.normative.input(msg);
				// TODO
				// Reduce Entrepreneur reputation
				
				// Do not pay extortion
			} else if(content instanceof NotPayExtortionAction) {
				this.normative.input(msg);
				// TODO
				// Increase Entrepreneur reputation
				
				// State compensate punishment
			} else if(content instanceof StateCompensationAction) {
				// TODO
				// Increase Entrepreneur reputation
				
				// State punish
			} else if(content instanceof StatePunishmentAction) {
				
				StatePunishmentAction action = (StatePunishmentAction) content;
				int entrepreneurId = (int) action
						.getParam(StatePunishmentAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurPayerRep.setReputation(entrepreneurId,
						ReputationAbstract.MIN);
				
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
		
		// Spread bad reputation about the Entrepreneur
		
	}
	
	
	/*******************************
	 * 
	 * Handle simulation events
	 * 
	 *******************************/
	
	@Override
	public void handleEvent(Event event) {
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_BUY_PRODUCT:
				this.buyProduct();
				break;
		}
	}
}