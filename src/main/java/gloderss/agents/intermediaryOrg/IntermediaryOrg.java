package gloderss.agents.intermediaryOrg;

import gloderss.Constants;
import gloderss.Constants.Norms;
import gloderss.actions.AffiliateRequestAction;
import gloderss.actions.AffiliationAcceptedAction;
import gloderss.actions.BuyProductAction;
import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CriticalConsumerInfoAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.ExtortionAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.MafiaBenefitAction;
import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NormativeInfoAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReleaseCustodyAction;
import gloderss.actions.ReleaseImprisonmentAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.AbstractAgent;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.IntermediaryOrgConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.IntermediaryOrganizationOutputEntity;
import gloderss.output.NormativeOutputEntity;
import gloderss.output.OutputController;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.function.Tanh;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class IntermediaryOrg extends AbstractAgent implements IIntermediaryOrg {
	
	private final static Logger							logger					= LoggerFactory
																															.getLogger(IntermediaryOrg.class);
	
	private final static String							NUMBER_ACTIONS	= "NUMBER_ACTIONS";
	
	private IntermediaryOrgConf							conf;
	
	private Map<Integer, ConsumerAgent>			consumers;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private PDFAbstract											timeToAffiliatePDF;
	
	private Evaluator												spreadInfoFunction;
	
	private Evaluator												proportionConsumers;
	
	private Evaluator												proportionEntrepreneurs;
	
	private List<Integer>										affiliated;
	
	private int															criticalConsumerPurchases;
	
	private int															totalPurchases;
	
	private Queue<AffiliateRequestAction>		affiliateQueue;
	
	private int															numActions;
	
	
	/**
	 * Intermediary Organization constructor
	 * 
	 * @param id
	 *          Intermediary Organization identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Intermediary organization configuration
	 * @param consumers
	 *          List of consumers
	 * @param entrepreneurs
	 *          List of entrepreneurs
	 * @return none
	 */
	public IntermediaryOrg(Integer id, EventSimulator simulator,
			IntermediaryOrgConf conf, Map<Integer, ConsumerAgent> consumers,
			Map<Integer, EntrepreneurAgent> entrepreneurs) {
		super(id, simulator);
		
		this.conf = conf;
		
		this.consumers = consumers;
		this.entrepreneurs = entrepreneurs;
		
		this.timeToAffiliatePDF = PDFAbstract.getInstance(conf
				.getTimeToAffiliatePDF());
		
		this.spreadInfoFunction = new Evaluator();
		this.spreadInfoFunction.putFunction(new Tanh());
		
		this.proportionConsumers = new Evaluator();
		
		this.proportionEntrepreneurs = new Evaluator();
		
		this.affiliated = new ArrayList<Integer>();
		
		this.criticalConsumerPurchases = 0;
		
		this.totalPurchases = 0;
		
		this.affiliateQueue = new LinkedList<AffiliateRequestAction>();
		
		this.numActions = 0;
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	@Override
	public void initializeSim() {
		
		for(Integer consumerId : this.consumers.keySet()) {
			this.comm.addObservation(this.id, consumerId);
		}
	}
	
	
	@Override
	public void receiveAffiliationRequest(AffiliateRequestAction action) {
		
		this.affiliateQueue.offer(action);
		
		Event event = new Event(this.simulator.now()
				+ this.timeToAffiliatePDF.nextValue(), this,
				Constants.EVENT_AFFILIATE_PROCESSING);
		this.simulator.insert(event);
	}
	
	
	@Override
	public void spreadNormativeInformation() {
		
		this.spreadInfoFunction.clearVariables();
		this.spreadInfoFunction.putVariable(NUMBER_ACTIONS, (new Integer(
				this.numActions)).toString());
		
		double probSpreadInfo = 0.0;
		try {
			probSpreadInfo = this.spreadInfoFunction.getNumberResult(this.conf
					.getSpreadInfoFunction());
		} catch(EvaluationException e) {
			logger.debug(e.getMessage());
		}
		
		if(RandomUtil.nextDouble() < probSpreadInfo) {
			
			// Spread information to Consumers
			NormativeInfoAction notBuyPayExtortion = new NormativeInfoAction(this.id,
					Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.name());
			
			this.proportionConsumers.clearVariables();
			
			double propConsumers = 0.0;
			try {
				propConsumers = this.proportionConsumers.getNumberResult(this.conf
						.getProportionConsumers());
			} catch(EvaluationException e) {
				logger.debug(e.getMessage());
			}
			
			propConsumers = Math.max(0, Math.min(1.0, propConsumers));
			int numConsumers = (int) (this.consumers.size() * propConsumers);
			List<Integer> consumerIds = new ArrayList<Integer>();
			while(consumerIds.size() < numConsumers) {
				
				int consumerId = (int) this.consumers.keySet().toArray()[RandomUtil
						.nextIntFromTo(0, (this.consumers.size() - 1))];
				
				if(!consumerIds.contains(consumerId)) {
					consumerIds.add(consumerId);
					
					Message msg = new Message(this.simulator.now(), this.id, consumerId,
							notBuyPayExtortion);
					this.sendMsg(msg);
				}
			}
			
			double criticalConsumers = this.calcCriticalConsumers();
			
			NormativeInfoAction notPayExtortion = new NormativeInfoAction(this.id,
					Norms.NOT_PAY_EXTORTION.name());
			
			NormativeInfoAction denounceExtortion = new NormativeInfoAction(this.id,
					Norms.DENOUNCE.name());
			
			this.proportionEntrepreneurs.clearVariables();
			
			double propEntrepreneurs = 0.0;
			try {
				propEntrepreneurs = this.proportionEntrepreneurs
						.getNumberResult(this.conf.getProportionEntrepreneurs());
			} catch(EvaluationException e) {
				logger.debug(e.getMessage());
			}
			
			propEntrepreneurs = Math.max(0, Math.min(1.0, propEntrepreneurs));
			
			int numEntrepreneurs = (int) (this.entrepreneurs.size() * propEntrepreneurs);
			List<Integer> entrepreneurIds = new ArrayList<Integer>();
			int qtyEntrepreneurs = this.entrepreneurs.size();
			while(entrepreneurIds.size() < numEntrepreneurs) {
				
				int entrepreneurId = (int) this.entrepreneurs.keySet().toArray()[RandomUtil
						.nextIntFromTo(0, (qtyEntrepreneurs - 1))];
				
				if(!entrepreneurIds.contains(entrepreneurId)) {
					entrepreneurIds.add(entrepreneurId);
					
					Message msg = new Message(this.simulator.now(), this.id,
							entrepreneurId, notPayExtortion);
					this.sendMsg(msg);
					
					msg = new Message(this.simulator.now(), this.id, entrepreneurId,
							denounceExtortion);
					this.sendMsg(msg);
					
					CriticalConsumerInfoAction ccInfo = new CriticalConsumerInfoAction(
							this.id, entrepreneurId, criticalConsumers);
					msg = new Message(this.simulator.now(), this.id, entrepreneurId,
							ccInfo);
					this.sendMsg(msg);
				}
			}
			
			// Output
			AbstractEntity outputEntity = OutputController.getInstance().getEntity(
					EntityType.NORMATIVE);
			outputEntity.setValue(NormativeOutputEntity.Field.TIME.name(),
					this.simulator.now());
			outputEntity.setValue(NormativeOutputEntity.Field.AGENT_ID.name(),
					this.id);
			outputEntity.setValue(
					NormativeOutputEntity.Field.NUMBER_CONSUMERS.name(), numConsumers);
			outputEntity.setValue(
					NormativeOutputEntity.Field.NUMBER_ENTREPRENEURS.name(),
					numEntrepreneurs);
			outputEntity.setActive();
			
			this.numActions = 0;
		}
	}
	
	
	@Override
	public void finalizeSim() {
	}
	
	
	/**
	 * Estimate the proportion of Critical Consumers
	 * 
	 * @param none
	 * @return Proportion of critical consumers
	 */
	private double calcCriticalConsumers() {
		double propCC = 0.0;
		
		if(this.totalPurchases != 0) {
			propCC = (double) this.criticalConsumerPurchases
					/ (double) this.totalPurchases;
		}
		
		return propCC;
	}
	
	
	/**
	 * Decide to accept or deny an affiliation request
	 * 
	 * @param none
	 * @return none
	 */
	private void affiliate() {
		
		if(!this.affiliateQueue.isEmpty()) {
			AffiliateRequestAction action = this.affiliateQueue.poll();
			
			int entrepreneurId = (int) action
					.getParam(AffiliateRequestAction.Param.ENTREPRENEUR_ID);
			
			if(!this.affiliated.contains(entrepreneurId)) {
				this.affiliated.add(entrepreneurId);
				
				AffiliationAcceptedAction affiliation = new AffiliationAcceptedAction(
						this.id, entrepreneurId);
				
				Message msg = new Message(this.simulator.now(), this.id,
						entrepreneurId, affiliation);
				this.sendMsg(msg);
				
				// Output
				AbstractEntity outputEntity = OutputController.getInstance().getEntity(
						EntityType.INTERMEDIARY_ORGANIZATION);
				outputEntity.setValue(
						IntermediaryOrganizationOutputEntity.Field.TIME.name(),
						this.simulator.now());
				outputEntity.setValue(
						IntermediaryOrganizationOutputEntity.Field.ENTREPRENEUR_ID.name(),
						entrepreneurId);
				outputEntity.setValue(
						IntermediaryOrganizationOutputEntity.Field.REQUEST_ACCEPTED.name(),
						true);
				outputEntity.setActive();
			}
		}
	}
	
	
	/**
	 * Calculate the hyperbolic tangent
	 * 
	 * @param x
	 *          Input parameter
	 * @return Hyperbolic tangent of x
	 *
	 *         private double tanh(int x) {
	 * 
	 *         final double y = (Math.exp(this.conf.getSlope() * x) - 1.0)
	 *         / (Math.exp(this.conf.getSlope() * x) + 1.0);
	 * 
	 *         return y;
	 *         }
	 * 
	 * 
	 *         /*******************************
	 * 
	 *         Handle communication requests
	 * 
	 *******************************/
	
	@Override
	public synchronized void handleMessage(Message msg) {
		
		Object content = msg.getContent();
		
		if((msg.getSender() != this.id) && (msg.getReceiver().contains(this.id))) {
			
			if(content instanceof AffiliateRequestAction) {
				this.receiveAffiliationRequest((AffiliateRequestAction) content);
				
			} else if(content instanceof Message) {
				
				Message actionMsg = (Message) content;
				content = actionMsg.getContent();
				
				if(content instanceof ExtortionAction) {
					this.numActions++;
					
				} else if(content instanceof PayExtortionAction) {
					this.numActions++;
					
				} else if(content instanceof NotPayExtortionAction) {
					this.numActions++;
					
				} else if(content instanceof DenounceExtortionAction) {
					this.numActions++;
					
				} else if(content instanceof DenounceExtortionAffiliatedAction) {
					this.numActions++;
					
				} else if(content instanceof DenouncePunishmentAction) {
					this.numActions++;
					
				} else if(content instanceof DenouncePunishmentAffiliatedAction) {
					this.numActions++;
					
				} else if(content instanceof CaptureMafiosoAction) {
					this.numActions++;
					
				} else if(content instanceof CollaborateAction) {
					this.numActions++;
					
				} else if(content instanceof ImprisonmentAction) {
					this.numActions++;
					
				} else if(content instanceof PentitoAction) {
					this.numActions++;
					
				} else if(content instanceof StateCompensationAction) {
					this.numActions++;
					
				} else if(content instanceof StatePunishmentAction) {
					this.numActions++;
					
				} else if(content instanceof CustodyAction) {
					this.numActions++;
					
				} else if(content instanceof ReleaseCustodyAction) {
					this.numActions++;
					
				} else if(content instanceof ReleaseImprisonmentAction) {
					this.numActions++;
					
				} else if(content instanceof MafiaPunishmentAction) {
					this.numActions++;
					
				} else if(content instanceof MafiaBenefitAction) {
					this.numActions++;
					
				}
				
				this.spreadNormativeInformation();
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
		
		// Buy product
		if(content instanceof BuyProductAction) {
			
			BuyProductAction action = (BuyProductAction) content;
			
			int entrepreneurId = (int) action
					.getParam(BuyProductAction.Param.ENTREPRENEUR_ID);
			if(this.affiliated.contains(entrepreneurId)) {
				this.criticalConsumerPurchases++;
			}
			
			this.totalPurchases++;
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
			case Constants.EVENT_AFFILIATE_PROCESSING:
				this.affiliate();
				break;
		}
	}
}