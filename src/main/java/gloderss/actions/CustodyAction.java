package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class CustodyAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    STATE_ID,
    MAFIOSO_ID,
    DURATION;
  }
  
  
  /**
   * EmprisonmentAction constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param stateId
   *          State identification
   * @param mafiosoId
   *          Mafioso identification
   * @param duration
   *          Custody duration
   * @return none
   */
  public CustodyAction( int extortionId, int stateId, int mafiosoId, double duration ) {
    super( Actions.CUSTODY.ordinal(), Actions.CUSTODY.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.STATE_ID, stateId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
    this.params.put( Param.DURATION, duration );
  }
}