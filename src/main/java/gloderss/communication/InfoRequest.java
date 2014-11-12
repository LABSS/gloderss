package gloderss.communication;

public class InfoRequest extends InfoAbstract {
	
	private String	infoRequest;
	
	
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
	 * @param infoRequest
	 *          Information requested
	 * @return Value of the information requested
	 */
	public InfoRequest(int sender, int receiver, String infoRequest) {
		super(InfoAbstract.Type.REQUEST, sender, receiver);
		this.infoRequest = infoRequest;
	}
	
	
	public String getInfoRequest() {
		return this.infoRequest;
	}
	
	
	public void setInfoRequest(String infoRequest) {
		this.infoRequest = infoRequest;
	}
}