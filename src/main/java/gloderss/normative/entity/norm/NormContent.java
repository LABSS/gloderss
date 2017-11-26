package gloderss.normative.entity.norm;

import emilia.entity.norm.NormContentInterface;
import gloderss.Constants.Actions;

public class NormContent implements NormContentInterface {
  
  // Action
  private Actions action;
  
  // Negated action
  private Actions notAction;
  
  
  /**
   * Create a norm content
   * 
   * @param action
   *          Action
   * @param notAction
   *          Negated action
   * @return none
   */
  public NormContent( Actions action, Actions notAction ) {
    this.action = action;
    this.notAction = notAction;
  }
  
  
  /**
   * Get action
   * 
   * @param none
   * @return Action
   */
  public Actions getAction() {
    return this.action;
  }
  
  
  /**
   * Get negated action
   * 
   * @param none
   * @return Negated action
   */
  public Actions getNotAction() {
    return this.notAction;
  }
  
  
  @Override
  public boolean match( Object value ) {
    
    if ( value instanceof String ) {
      if ( (this.action.name().equalsIgnoreCase( (String) value ))
          || (this.notAction.name().equalsIgnoreCase( (String) value )) ) {
        return true;
      }
    }
    
    return false;
  }
  
  
  @Override
  public boolean comply( Object value ) {
    
    if ( value instanceof String ) {
      if ( this.action.name().equalsIgnoreCase( (String) value ) ) {
        return true;
      }
    }
    
    return false;
  }
}