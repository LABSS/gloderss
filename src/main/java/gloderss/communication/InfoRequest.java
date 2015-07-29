package gloderss.communication;

public class InfoRequest extends InfoAbstract {
	
	private String	parameter;
	
	
	/**
	 * Information request constructor
	 * 
	 * @param none
	 * @return none
	 */
	public InfoRequest() {
		super(InfoAbstract.Type.REQUEST);
	}
	
	
	/**
	 * Information request constructor
	 * 
	 * @param sender
	 *          Sender identification
	 * @param receiver
	 *          Receiver identification
	 * @param parameter
	 *          Information requested
	 * @return Value of the information requested
	 */
	public InfoRequest(int sender, int receiver, String parameter) {
		super(InfoAbstract.Type.REQUEST, sender, receiver);
		this.parameter = parameter;
	}
	
	
	public String getInfoRequest() {
		return this.parameter;
	}
	
	
	public void setInfoRequest(String parameter) {
		this.parameter = parameter;
	}
}