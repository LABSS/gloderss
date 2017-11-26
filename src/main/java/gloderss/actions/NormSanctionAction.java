package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class NormSanctionAction extends ActionAbstract {
  
  public enum Param {
    AGENT_ID,
    ENTREPRENEUR_ID,
    NORM
  };
  
  
  /**
   * NormSanctionAction constructor
   * 
   * @param agentId
   *          Agent identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param norm
   *          Norm name
   * @return none
   */
  public NormSanctionAction( int agentId, int entrepreneurId, String norm ) {
    super( Actions.NORM_SANCTION_INFO.ordinal(),
        Actions.NORM_SANCTION_INFO.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.AGENT_ID, agentId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.NORM, norm );
  }
}