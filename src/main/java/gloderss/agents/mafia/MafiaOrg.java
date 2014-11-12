package gloderss.agents.mafia;

import gloderss.Constants;
import gloderss.agents.AbstractAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.MafiaConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.util.random.RandomUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MafiaOrg extends AbstractAgent implements IMafiaOrg {
	
	private MafiaConf												conf;
	
	private Map<Integer, MafiosoAgent>			mafiosi;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private List<Integer>										domain;
	
	private Map<Integer, Double>						benefits;
	
	
	/**
	 * Constructor
	 * 
	 * @param id
	 *          Mafia identification
	 * @param simulator
	 *          Event simulator
	 * @param conf
	 *          Mafia configuration
	 * @param stateId
	 *          State organization identification
	 * @param entrepreneurs
	 *          List of all entrepreneurs
	 * @return none
	 */
	public MafiaOrg(Integer id, EventSimulator simulator, MafiaConf conf,
			Integer stateId, Map<Integer, EntrepreneurAgent> entrepreneurs) {
		super(id, simulator);
		this.conf = conf;
		
		this.entrepreneurs = entrepreneurs;
		this.domain = new Vector<Integer>();
		
		// Create Mafiosi
		MafiosoAgent mafioso;
		int mafiosoId = id + 1;
		this.mafiosi = new HashMap<Integer, MafiosoAgent>();
		for(int i = 0; i < this.conf.getNumberMafiosi(); i++, mafiosoId++) {
			mafioso = new MafiosoAgent(mafiosoId, simulator, this.conf, this.getId(),
					stateId);
			this.mafiosi.put(mafiosoId, mafioso);
		}
		
		// Setup the benefits provided to each Entrepreneur
		this.benefits = new HashMap<Integer, Double>();
		this.setupBenefits();
	}
	
	
	/**
	 * Setup the benefits provided to the entrepreneurs
	 * 
	 * @param none
	 * @return none
	 */
	private void setupBenefits() {
		double minBenefit = this.conf.getMinimumBenefit();
		double maxBenefit = this.conf.getMaximumBenefit();
		
		for(Integer eId : this.entrepreneurs.keySet()) {
			double benefit = minBenefit
					+ ((maxBenefit - minBenefit) * RandomUtil.nextDouble());
			
			this.benefits.put(eId, benefit);
		}
		
		for(MafiosoAgent mafioso : this.mafiosi.values()) {
			mafioso.setBenefits(this.benefits);
		}
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public MafiaConf getConf() {
		return this.conf;
	}
	
	
	public Map<Integer, MafiosoAgent> getMafiosi() {
		return this.mafiosi;
	}
	
	
	/*******************************
	 * 
	 * Decision Processes
	 * 
	 *******************************/
	
	public synchronized int decideTarget() {
		
		if(this.domain.isEmpty()) {
			this.domain.addAll(this.entrepreneurs.keySet());
			Collections.shuffle(this.domain);
		}
		
		int targetId = this.domain.remove(RandomUtil.nextIntFromTo(0,
				(this.domain.size() - 1)));
		
		return targetId;
	}
	
	
	@Override
	public void initializeSim() {
		for(MafiosoAgent mafioso : this.mafiosi.values()) {
			mafioso.initializeSim();
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
	public Object handleInfo(InfoAbstract info) {
		Object infoRequested = null;
		
		if(info.getType().equals(InfoAbstract.Type.REQUEST)) {
			
			InfoRequest request = (InfoRequest) info;
			switch(request.getInfoRequest()) {
				case Constants.REQUEST_ID:
					infoRequested = this.getId();
					break;
				case Constants.REQUEST_TARGET_ID:
					infoRequested = this.decideTarget();
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
	}
}