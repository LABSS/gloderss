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
	// Issue
	// -----
	// Needs to remove conflicting messages from the database because the same
	// agent may see the message as sent and receive if it is connected to the
	// sender and the receiver
	//
	public void handleObservation(Message msg);
}