package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class NormInvocationAction extends ActionAbstract {
  
  public enum Param {
    AGENT_ID,
    NORM
  };
  
  
  /**
   * NormInvocationAction constructor
   * 
   * @param agentId
   *          Agent identification
   * @param norm
   *          Norm name
   * @return none
   */
  public NormInvocationAction( int agentId, String norm ) {
    super( Actions.NORM_INVOCATION_INFO.ordinal(),
        Actions.NORM_INVOCATION_INFO.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.AGENT_ID, agentId );
    this.params.put( Param.NORM, norm );
  }
}