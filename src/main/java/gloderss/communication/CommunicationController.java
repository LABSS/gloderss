package gloderss.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CommunicationController {
	
	private static CommunicationController	instance;
	
	private Map<Integer, IComm>							agents;
	
	private Map<Integer, List<Integer>>			observe;
	
	
	/**
	 * Communication controller constructor
	 * 
	 * @param none
	 * @return none
	 */
	private CommunicationController() {
		this.agents = new HashMap<Integer, IComm>();
		this.observe = new Hashtable<Integer, List<Integer>>();
	}
	
	
	/**
	 * Get the Communication Controller active instance
	 * 
	 * @param none
	 * @return Communication Controller instance
	 */
	public static CommunicationController getInstance() {
		if(instance == null) {
			instance = new CommunicationController();
		}
		
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
	 * Send the message to the recipients
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	public void sendMessage(Message msg) {
		
		int sender = msg.getSender();
		if(this.agents.containsKey(sender)) {
			// Receivers specified
			if(msg.getReceiver() != null) {
				for(int receiver : msg.getReceiver()) {
					if(this.agents.containsKey(receiver)) {
						IComm callback = this.agents.get(receiver);
						callback.handleMessage(msg);
						
						// Send received message to registered observer
						if(this.observe.containsKey(receiver)) {
							for(int observer : this.observe.get(receiver)) {
								if(observer != sender) {
									callback = this.agents.get(observer);
									callback.handleObservation(msg);
								}
							}
						}
					}
				}
				
				// Send sent message to registered observer
				if(this.observe.containsKey(sender)) {
					List<Integer> receivers = msg.getReceiver();
					for(Integer observer : this.observe.get(sender)) {
						if(!receivers.contains(observer)) {
							IComm callback = this.agents.get(observer);
							callback.handleObservation(msg);
						}
					}
				}
				
				// Broadcast message
			} else {
				for(Integer receiver : this.agents.keySet()) {
					IComm callback = this.agents.get(receiver);
					callback.handleMessage(msg);
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
				if(!aux.contains(observer)) {
					aux.remove(observer);
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
			if(!aux.contains(observer)) {
				aux.remove(observer);
				this.observe.put(observed, aux);
			}
		}
	}
}