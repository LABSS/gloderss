package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class DeliverProductAction extends ActionAbstract {
  
  public enum Param {
    CONSUMER_ID,
    ENTREPRENEUR_ID,
    COST;
  }
  
  
  /**
   * DeiverProductAction constructor
   * 
   * @param consumerId
   *          Consumer identification
   * @param entrepreneurId
   *          Consumer identification
   * @param cost
   *          Cost of the product
   * @return none
   */
  public DeliverProductAction( int consumerId, int entrepreneurId, double cost ) {
    super( Actions.DELIVER_PRODUCT.ordinal(), Actions.DELIVER_PRODUCT.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.CONSUMER_ID, consumerId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.COST, cost );
  }
}