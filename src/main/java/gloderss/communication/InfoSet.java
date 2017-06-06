package gloderss.communication;

public class InfoSet extends InfoAbstract {
  
  // Parameter identification
  private String parameter;
  
  // Parameter value
  private Object value;
  
  
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
   *          Parameter identification to set
   * @param value
   *          Parameter value
   * @return TRUE parameter value set, FALSE otherwise
   */
  public InfoSet(int sender, int receiver, String parameter, Object value) {
    super(InfoAbstract.Type.SET, sender, receiver);
    this.parameter = parameter;
    this.value = value;
  }
  
  
  /**
   * Get parameter identification
   * 
   * @param none
   * @return Parameter identification
   */
  public String getParameter() {
    return this.parameter;
  }
  
  
  /**
   * Set the parameter identification
   * 
   * @param parameter
   *          Parameter identification
   * @return none
   */
  public void setParameter(String parameter) {
    this.parameter = parameter;
  }
  
  
  /**
   * Get parameter value
   * 
   * @param none
   * @return Parameter value
   */
  public Object getValue() {
    return this.value;
  }
  
  
  /**
   * Set parameter value
   * 
   * @param value
   *          Parameter value
   * @return none
   */
  public void setValue(Object value) {
    this.value = value;
  }
}