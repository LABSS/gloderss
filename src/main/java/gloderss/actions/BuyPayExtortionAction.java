package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class BuyPayExtortionAction extends ActionAbstract {
  
  public enum Param {
    CONSUMER_ID,
    ENTREPRENEUR_ID;
  }
  
  
  /**
   * BuyAction constructor
   * 
   * @param consumerId
   *          Consumer identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @return none
   */
  public BuyPayExtortionAction( int consumerId, int entrepreneurId ) {
    super( Actions.BUY_NOT_PAY_EXTORTION.ordinal(),
        Actions.BUY_NOT_PAY_EXTORTION.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.CONSUMER_ID, consumerId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
  }
}