package gloderss.communication;

public abstract class InfoAbstract {
	
	public enum Type {
		REQUEST,
		SET
	};
	
	// Type of information communication
	protected Type	type;
	
	// Sender identification
	protected int		sender;
	
	// Receiver identification
	protected int		receiver;
	
	
	/**
	 * Information communication constructor abstract
	 * 
	 * @param type
	 *          Type of information communication
	 */
	public InfoAbstract(Type type) {
		this.type = type;
	}
	
	
	/**
	 * Information communication constructor abstract
	 * 
	 * @param type
	 *          Type of information communication
	 * @param sender
	 *          Sender identification
	 * @param receiver
	 *          Receiver identification
	 */
	public InfoAbstract(Type type, int sender, int receiver) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	
	/**
	 * Get the type of information communication
	 * 
	 * @param none
	 * @return Type of information communication
	 */
	public Type getType() {
		return this.type;
	}
	
	
	/**
	 * Set the type of information communication
	 * 
	 * @param type
	 *          Type of information communication
	 * @return none
	 */
	public void setType(Type type) {
		this.type = type;
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
	 * Set the sender identification
	 * 
	 * @param sender
	 *          Sender identification
	 * @return none
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	
	/**
	 * Get receiver identification
	 * 
	 * @param none
	 * @return Receiver identification
	 */
	public int getReceiver() {
		return this.receiver;
	}
	
	
	/**
	 * Set the receiver identification
	 * 
	 * @param receiver
	 *          Receiver identification
	 * @return none
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
}