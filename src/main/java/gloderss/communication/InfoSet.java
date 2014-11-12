package gloderss.communication;

public class InfoSet extends InfoAbstract {
	
	private String	parameter;
	
	private Object	value;
	
	
	/**
	 * Information set constructor
	 * 
	 * @param none
	 * @return none
	 */
	public InfoSet() {
		super(InfoAbstract.Type.SET);
	}
	
	
	/**
	 * Information request constructor
	 * 
	 * @param sender
	 *          Sender identification
	 * @param receiver
	 *          Receiver identification
	 * @param parameter
	 *          Parameter to set
	 * @param value
	 *          Parameter value
	 * @return TRUE parameter value set, FALSE otherwise
	 */
	public InfoSet(int sender, int receiver, String parameter, Object value) {
		super(InfoAbstract.Type.SET, sender, receiver);
		this.parameter = parameter;
		this.value = value;
	}
	
	
	public String getParameter() {
		return this.parameter;
	}
	
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
	public Object getValue() {
		return this.value;
	}
	
	
	public void setValue(Object value) {
		this.value = value;
	}
}