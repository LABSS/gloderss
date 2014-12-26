package gloderss.communication;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	// Time submission
	private double				time;
	
	// Sender identification
	private int						sender;
	
	// Receiver identification
	private List<Integer>	receiver;
	
	// Message content
	private Object				content;
	
	
	/**
	 * Message constructor
	 * 
	 * @param none
	 * @return none
	 */
	public Message() {
	}
	
	
	/**
	 * Message constructor
	 * 
	 * @param time
	 *          Time of submission
	 * @param sender
	 *          Sender identification
	 * @param receiver
	 *          Receiver identification
	 * @param content
	 *          Message content
	 */
	public Message(double time, int sender, int receiver, Object content) {
		this.time = time;
		this.sender = sender;
		this.receiver = new ArrayList<Integer>();
		this.receiver.add(receiver);
		this.content = content;
	}
	
	
	/**
	 * Message constructor
	 * 
	 * @param time
	 *          Time of submission
	 * @param sender
	 *          Sender identification
	 * @param receiver
	 *          List of Receivers identification
	 * @param content
	 *          Message content
	 */
	public Message(double time, int sender, List<Integer> receiver, Object content) {
		this.time = time;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}
	
	
	/**
	 * Get time of submission
	 * 
	 * @param none
	 * @return Time of submission
	 */
	public double getTime() {
		return this.time;
	}
	
	
	/**
	 * Set time of submission
	 * 
	 * @param time
	 *          Time of submission
	 * @return none
	 */
	public void setTime(double time) {
		this.time = time;
	}
	
	
	/**
	 * Get sender identification
	 * 
	 * @param none
	 * @return Sender identification
	 */
	public int getSender() {
		return this.sender;
	}
	
	
	/**
	 * Set sender identification
	 * 
	 * @param sender
	 *          Sender identification
	 * @return none
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	
	/**
	 * Set the list of receivers' identification
	 * 
	 * @param none
	 * @return List of receivers' identification
	 */
	public List<Integer> getReceiver() {
		return this.receiver;
	}
	
	
	/**
	 * Set the list of receivers' identification
	 * 
	 * @param receiver
	 *          List of receivers' identification
	 * @return none
	 */
	public void setReceiver(List<Integer> receiver) {
		this.receiver = receiver;
	}
	
	
	/**
	 * Get content of the message
	 * 
	 * @param none
	 * @return Message content
	 */
	public Object getContent() {
		return this.content;
	}
	
	
	/**
	 * Set the message content
	 * 
	 * @param content
	 *          Message content
	 * @return none
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	
	
	/**
	 * Convert message to String
	 * 
	 * @param none
	 * @return Message converted to String
	 */
	@Override
	public String toString() {
		String str = new String();
		
		str += this.time + " " + this.sender + " " + this.receiver + " "
				+ this.content.toString();
		
		return str;
	}
}