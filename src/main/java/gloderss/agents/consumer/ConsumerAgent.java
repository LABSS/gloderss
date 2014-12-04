package gloderss.agents.consumer;

import emilia.EmiliaController;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gloderss.Constants;
import gloderss.Constants.Actions;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.AffiliationDeniedAction;
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
import gloderss.normative.entity.norm.NormEntity;
import gloderss.reputation.EntrepreneursReputation;
import gloderss.reputation.ReputationAbstract;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;

public class ConsumerAgent extends CitizenAgent implements IConsumer,
		NormEnforcementListener {
	
	private ConsumerConf										conf;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private EntrepreneursReputation					entrepreneurRep;
	
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
		this.entrepreneurRep = new EntrepreneursReputation(conf.getReputationConf()
				.getEntrepreneurPayer());
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
		
		// BUY_NOT_PAY Norm
		normContent = new NormContent(Actions.BUY_NOT_PAY_EXTORTION,
				Actions.BUY_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal(),
				NormType.SOCIAL, NormSource.DISTRIBUTED, NormStatus.GOAL, normContent,
				conf.getSaliences().get(
						Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		// BUY_PAY Norm
		normContent = new NormContent(Actions.BUY_PAY_EXTORTION,
				Actions.BUY_NOT_PAY_EXTORTION);
		
		norm = new NormEntity(Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal(),
				NormType.SOCIAL, NormSource.DISTRIBUTED, NormStatus.GOAL, normContent,
				this.conf.getSaliences().get(
						Norms.BUY_FROM_PAYING_ENTREPRENEURS.ordinal()));
		
		sanctions = new ArrayList<SanctionEntityAbstract>();
		
		normsSanctions.put(norm, sanctions);
		
		this.normative.addNormsSanctions(normsSanctions);
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
		
		// Score each Entrepreneur
		double maxScore = 0.0;
		int selectedEntrepreneurId = -1;
		for(Integer entrepreneurId : buyList.keySet()) {
			double price = buyList.get(entrepreneurId);
			
			double buyNotPayExtortion = this.normative
					.getNormSalience(Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal());
			
			double score = ((1 - (price / maxPrice)) * this.conf
					.getIndividualWeight())
					+ ((buyNotPayExtortion * this.conf.getNormativeWeight()) * this.entrepreneurRep
							.getReputation(entrepreneurId));
			
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
				this.entrepreneurRep.setReputation(entrepreneurId,
						ReputationAbstract.MAX);
				
				// Affiliation Denied
			} else if(content instanceof AffiliationDeniedAction) {
				
				AffiliationDeniedAction action = (AffiliationDeniedAction) content;
				int entrepreneurId = (int) action
						.getParam(AffiliationDeniedAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurRep.setReputation(entrepreneurId,
						ReputationAbstract.MIN);
				
				// Buy product
			} else if(content instanceof BuyProductAction) {
				// TODO
				
				// Collaborate
			} else if(content instanceof CollaborateAction) {
				
				CollaborateAction action = (CollaborateAction) content;
				int entrepreneurId = (int) action
						.getParam(CollaborateAction.Param.ENTREPRENEUR_ID);
				this.entrepreneurRep.setReputation(entrepreneurId,
						ReputationAbstract.MIN);
				
				// Denounce extortion
			} else if(content instanceof DenounceExtortionAction) {
				DenounceExtortionAction action = (DenounceExtortionAction) content;
				this.normative.input(action);
				
				// Denounce punishment
			} else if(content instanceof DenouncePunishmentAction) {
				DenouncePunishmentAction action = (DenouncePunishmentAction) content;
				this.normative.input(action);
				
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
				// TODO
				// Reduce Entrepreneur reputation
				
				// Do not pay extortion
			} else if(content instanceof NotPayExtortionAction) {
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
				this.entrepreneurRep.setReputation(entrepreneurId,
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