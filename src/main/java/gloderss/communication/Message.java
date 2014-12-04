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
	 * 
	 * @return
	 */
	public double getTime() {
		return this.time;
	}
	
	
	/**
	 * 
	 * @param time
	 */
	public void setTime(double time) {
		this.time = time;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getSender() {
		return this.sender;
	}
	
	
	/**
	 * 
	 * @param sender
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getReceiver() {
		return this.receiver;
	}
	
	
	/**
	 * 
	 * @param receiver
	 */
	public void setReceiver(List<Integer> receiver) {
		this.receiver = receiver;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Object getContent() {
		return this.content;
	}
	
	
	/**
	 * 
	 * @param content
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		String str = new String();
		
		str += this.time + " " + this.sender + " " + this.receiver + " "
				+ this.content.toString();
		
		return str;
	}
}