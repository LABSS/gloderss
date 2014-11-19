package gloderss.agents;

import java.util.List;
import java.util.Map;
import gloderss.communication.CommunicationController;
import gloderss.communication.IComm;
import gloderss.communication.InfoAbstract;
import gloderss.communication.Message;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.EventHandler;

public abstract class AbstractAgent implements IComm, EventHandler {
	
	// Agent identification
	protected int											id;
	
	// Simulator
	protected EventSimulator					simulator;
	
	// Communication controller
	protected CommunicationController	comm;
	
	
	// Inbox messages
	// protected Queue<Message> inbox;
	
	/**
	 * Constructor
	 * 
	 * @param id
	 *          Agent identification
	 * @param simulator
	 *          Event simulator
	 * @return none
	 */
	public AbstractAgent(int id, EventSimulator simulator) {
		this.id = id;
		this.simulator = simulator;
		
		this.comm = CommunicationController.getInstance();
		this.comm.subscribe(id, this);
		
		// this.inbox = new LinkedList<Message>();
	}
	
	
	/**
	 * Get agent identification
	 * 
	 * @param none
	 * @return Agent identification
	 */
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Send a message to the Communication Controller
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	public void sendMsg(Message msg) {
		if(msg != null) {
			this.comm.sendMessage(msg);
		}
	}
	
	
	/**
	 * Send a request to the Communication Controller
	 * 
	 * @param info
	 *          Information request or set
	 * @return Requested data
	 */
	public Object sendInfo(Object info) {
		Object result = null;
		
		if(info instanceof InfoAbstract) {
			result = this.comm.sendInfo((InfoAbstract) info);
		}
		
		return result;
	}
	
	
	/**
	 * Handle received messages
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	@Override
	public synchronized void handleMessage(Message msg) {
		// this.inbox.add(msg);
	}
	
	
	/**
	 * Check whether there is a message in the Inbox
	 * 
	 * @param none
	 * @return True There is message, False otherwise
	 * 
	 *         public boolean hasMsg() {
	 *         if(!this.inbox.isEmpty()) {
	 *         return true;
	 *         }
	 * 
	 *         return false;
	 *         }
	 */
	
	/**
	 * Check whether there is a message of a specific type in the Inbox
	 * 
	 * @param clazz
	 *          Class type to search in the Inbox
	 * @return True if found the content of this class type, False otherwise
	 * 
	 *         public boolean hasMsg(Class<?> clazz) {
	 *         boolean found = false;
	 *         Iterator<Message> it = this.inbox.iterator();
	 *         Message msg;
	 *         while((it.hasNext()) && (!found)) {
	 *         msg = it.next();
	 *         if((msg.getContent() != null) &&
	 *         (clazz.isInstance(msg.getContent()))) {
	 *         found = true;
	 *         }
	 *         }
	 * 
	 *         return found;
	 *         }
	 */
	
	/**
	 * Get the next oldest message
	 * 
	 * @param none
	 * @return Oldest message in the Inbox
	 * 
	 *         public Message nextMsg() {
	 *         Message msg = null;
	 * 
	 *         if(!this.inbox.isEmpty()) {
	 *         msg = this.inbox.poll();
	 *         }
	 * 
	 *         return msg;
	 *         }
	 */
	
	/**
	 * Get all the messages of a specific class type
	 * 
	 * @param clazz
	 *          Class type to search in the Inbox
	 * @return List of messages of the specified type
	 * 
	 *         public List<Message> retriveMsgs(Class<?> clazz) {
	 *         List<Message> msgs = new ArrayList<Message>();
	 * 
	 *         Iterator<Message> it = this.inbox.iterator();
	 *         Message msg;
	 *         while(it.hasNext()) {
	 *         msg = it.next();
	 *         if(clazz.isInstance(msg.getContent())) {
	 *         msgs.add(msg);
	 *         }
	 *         }
	 *         this.inbox.removeAll(msgs);
	 * 
	 *         return msgs;
	 *         }
	 */
	
	/**
	 * Get the list of observations
	 * 
	 * @param none
	 * @return Observations registered
	 */
	public Map<Integer, List<Integer>> getObservations() {
		return this.comm.getObservations();
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
		if((observedList != null) && (!observedList.isEmpty())) {
			this.comm.addObservation(observer, observedList);
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
		this.comm.addObservation(observer, observed);
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
		if((observedList != null) && (!observedList.isEmpty())) {
			this.comm.removeObservation(observer, observedList);
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
		this.comm.removeObservation(observer, observed);
	}
}