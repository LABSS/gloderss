package gloderss.agents;

import gloderss.communication.CommunicationController;
import gloderss.communication.IComm;
import gloderss.communication.InfoAbstract;
import gloderss.communication.Message;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.EventHandler;
import java.util.List;
import java.util.Map;

public abstract class AbstractAgent implements IComm, EventHandler {
  
  // Agent identification
  protected int                     id;
  
  // Simulator
  protected EventSimulator          simulator;
  
  // Communication controller
  protected CommunicationController comm;
  
  
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