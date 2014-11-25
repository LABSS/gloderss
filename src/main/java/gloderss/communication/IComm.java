package gloderss.communication;

public interface IComm {
	
	/**
	 * Handle a message received from the Communication Controller
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	public void handleMessage(Message msg);
	
	
	/**
	 * Handle a request received from the Communication Controller
	 * 
	 * @param info
	 *          Info Request or Info Set
	 * @return Information requested
	 */
	public Object handleInfo(InfoAbstract info);
	
	
	/**
	 * Handle an observed message received from the Communication Controller
	 * 
	 * @param msg
	 *          Message
	 * @return none
	 */
	public void handleObservation(Message msg);
}