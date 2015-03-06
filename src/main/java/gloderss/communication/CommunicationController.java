package gloderss.communication;

import gloderss.conf.ActionConf;
import gloderss.conf.CommunicationConf;
import gloderss.conf.TypeConf;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommunicationController {
	
	private static Logger	logger	= LoggerFactory
																		.getLogger(CommunicationController.class);
	
	public enum Visibility {
		NONE,
		COMPLETE,
		PARTIAL
	};
	
	private static CommunicationController	instance;
	
	// Visibility of each action in the environment
	private Map<String, Double>							actionVisibility;
	
	// Registered agents
	private Map<Integer, IComm>							agents;
	
	// Registered observations
	private Map<Integer, List<Integer>>			observe;
	
	
	/**
	 * Communication controller constructor
	 * 
	 * @param none
	 * @return none
	 */
	private CommunicationController(CommunicationConf conf) {
		this.actionVisibility = new HashMap<String, Double>();
		
		Map<String, Double> types = new HashMap<String, Double>();
		for(TypeConf type : conf.getTypesConf()) {
			types.put(type.getName(), type.getProbability());
		}
		
		double probability;
		for(ActionConf action : conf.getActionsConf()) {
			
			if(types.containsKey(action.getType())) {
				probability = types.get(action.getType());
			} else {
				probability = 0.0;
			}
			
			this.actionVisibility.put(action.getName(), probability);
		}
		
		this.agents = new HashMap<Integer, IComm>();
		this.observe = new Hashtable<Integer, List<Integer>>();
		
		instance = this;
	}
	
	
	/**
	 * Get the Communication Controller active instance
	 * 
	 * @param conf
	 *          Communication configuration
	 * @return Communication Controller instance
	 */
	public static CommunicationController getInstance(CommunicationConf conf) {
		if(instance == null) {
			instance = new CommunicationController(conf);
		}
		
		return instance;
	}
	
	
	/**
	 * Get the Communication Controller active instance
	 * 
	 * @param none
	 * @return Communication Controller instance
	 */
	public static CommunicationController getInstance() {
		return instance;
	}
	
	
	/**
	 * Register agent as sender/receiver of messages
	 * 
	 * @param id
	 *          Agent identification
	 * @param callback
	 *          Agent callback
	 */
	public boolean subscribe(int id, IComm callback) {
		boolean registered = false;
		
		if(!this.agents.containsKey(id)) {
			this.agents.put(id, callback);
			registered = true;
		}
		
		return registered;
	}
	
	
	/**
	 * Unregister agent as sender/receiver of messages
	 * 
	 * @param id
	 *          Agent identification
	 * @return none
	 */
	public void unregister(int id) {
		if(this.agents.containsKey(id)) {
			this.agents.remove(id);
		}
	}
	
	
	/**
	 * Reset the CommunicationController
	 * 
	 * @param conf
	 *          Communication configuration
	 * @return none
	 */
	public void reset(CommunicationConf conf) {
		this.actionVisibility = new HashMap<String, Double>();
		
		Map<String, Double> types = new HashMap<String, Double>();
		for(TypeConf type : conf.getTypesConf()) {
			types.put(type.getName(), type.getProbability());
		}
		
		double probability;
		for(ActionConf action : conf.getActionsConf()) {
			
			if(types.containsKey(action.getType())) {
				probability = types.get(action.getType());
			} else {
				probability = 0.0;
			}
			
			this.actionVisibility.put(action.getName(), probability);
		}
		
		this.agents = new HashMap<Integer, IComm>();
		this.observe = new Hashtable<Integer, List<Integer>>();
	}
	
	
	/**
	 * Send the message to the recipients
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	public void sendMessage(Message msg) {
		
		int sender = msg.getSender();
		if(this.agents.containsKey(sender)) {
			
			List<Integer> observers = new ArrayList<Integer>();
			
			// Receivers specified
			if(msg.getReceiver() != null) {
				for(Integer receiver : msg.getReceiver()) {
					if(this.agents.containsKey(receiver)) {
						IComm callback = this.agents.get(receiver);
						callback.handleMessage(msg);
						
						logger.debug("[SENDMSG] [" + msg.toString() + "]");
						
						// Add agents as observers
						if(this.observe.containsKey(receiver)) {
							for(Integer observer : this.observe.get(receiver)) {
								if((observer != sender) && (!observers.contains(observer))) {
									observers.add(observer);
								}
							}
						}
					}
				}
				
				// Send sent message to registered observer
				if(this.observe.containsKey(sender)) {
					
					List<Integer> receivers = msg.getReceiver();
					if(receivers == null) {
						receivers = new ArrayList<Integer>();
					}
					
					// Add agents as observers
					for(Integer observer : this.observe.get(sender)) {
						if((!receivers.contains(observer))
								&& (!observers.contains(observer))) {
							observers.add(observer);
						}
					}
				}
				
				String action = msg.getContent().getClass().getName();
				if(this.actionVisibility.containsKey(action)) {
					
					double probability = this.actionVisibility.get(action);
					
					for(Integer observer : observers) {
						if(RandomUtil.nextDouble() < probability) {
							IComm callback = this.agents.get(observer);
							callback.handleObservation(msg);
							
							logger.debug("[OBSERVED] [" + observer + "] [" + msg.toString()
									+ "]");
						} else {
							logger.debug("[NOT_OBSERVATION] [" + observer + "] ["
									+ msg.toString() + "]");
						}
					}
				}
				
				// Broadcast message
			} else {
				String action = msg.getContent().getClass().getName();
				if(this.actionVisibility.containsKey(action)) {
					
					double probability = this.actionVisibility.get(action);
					
					for(Integer receiver : this.agents.keySet()) {
						if(RandomUtil.nextDouble() < probability) {
							IComm callback = this.agents.get(receiver);
							callback.handleMessage(msg);
							
							logger.debug("[BROADCAST] [" + msg.toString() + "]");
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Send an information request or set
	 * 
	 * @param info
	 *          Information request or set
	 * @return Information requested or set result
	 */
	public Object sendInfo(InfoAbstract info) {
		Object result = null;
		
		if(this.agents.containsKey(info.getSender())
				&& (this.agents.containsKey(info.getReceiver()))) {
			IComm agent = this.agents.get(info.getReceiver());
			result = agent.handleInfo(info);
		}
		
		return result;
	}
	
	
	/**
	 * Get the list of observations
	 * 
	 * @param none
	 * @return Observations registered
	 */
	public Map<Integer, List<Integer>> getObservations() {
		return this.observe;
	}
	
	
	/**
	 * Add the observer to the list of observed agents
	 * 
	 * @param observer
	 *          Agent requesting observation
	 * @param observedList
	 *          List of agents to be observed
	 * @return none
	 */
	public void addObservation(int observer, List<Integer> observedList) {
		List<Integer> aux;
		for(Integer observed : observedList) {
			if(this.observe.containsKey(observed)) {
				aux = this.observe.get(observed);
			} else {
				aux = new ArrayList<Integer>();
			}
			
			if(!aux.contains(observer)) {
				aux.add(observer);
				this.observe.put(observed, aux);
			}
		}
	}
	
	
	/**
	 * Add the observer to the observed agent
	 * 
	 * @param observer
	 *          Agent requesting the observation
	 * @param observed
	 *          Observed agent
	 * @return none
	 */
	public void addObservation(int observer, int observed) {
		List<Integer> aux;
		if(this.observe.containsKey(observed)) {
			aux = this.observe.get(observed);
		} else {
			aux = new ArrayList<Integer>();
		}
		
		if(!aux.contains(observer)) {
			aux.add(observer);
			this.observe.put(observed, aux);
		}
	}
	
	
	/**
	 * Remove the observer to the list of observed agents
	 * 
	 * @param observer
	 *          Agent requesting observation removal
	 * @param observedList
	 *          List of agents to remove observation
	 * @return none
	 */
	public void removeObservation(int observer, List<Integer> observedList) {
		for(Integer observed : observedList) {
			if(this.observe.containsKey(observed)) {
				List<Integer> aux = this.observe.get(observed);
				if(aux.contains(observer)) {
					aux.remove(new Integer(observer));
					this.observe.put(observed, aux);
				}
			}
		}
	}
	
	
	/**
	 * Remove the observer to the observed agent
	 * 
	 * @param observer
	 *          Agent requesting observation removal
	 * @param observed
	 *          Observed agent
	 * @return none
	 */
	public void removeObservation(int observer, int observed) {
		if(this.observe.containsKey(observed)) {
			List<Integer> aux = this.observe.get(observed);
			if(aux.contains(observer)) {
				aux.remove(new Integer(observer));
				this.observe.put(observed, aux);
			}
		}
	}
}