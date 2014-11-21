package gloderss.communication;

public abstract class InfoAbstract {
	
	public enum Type {
		REQUEST,
		SET
	};
	
	protected Type	type;
	
	// Sender identification
	protected int		sender;
	
	// Receiver identification
	protected int		receiver;
	
	
	public InfoAbstract(Type type) {
		this.type = type;
	}
	
	
	public InfoAbstract(Type type, int sender, int receiver) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	
	public Type getType() {
		return this.type;
	}
	
	
	public void setType(Type type) {
		this.type = type;
	}
	
	
	public int getSender() {
		return this.sender;
	}
	
	
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	
	public int getReceiver() {
		return this.receiver;
	}
	
	
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
}