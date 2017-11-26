package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class NotDenounceExtortionAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    ENTREPRENEUR_ID,
    STATE_ID,
    MAFIOSO_ID;
  }
  
  
  /**
   * DenounceExtortionAction constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param stateId
   *          State identification
   * @param mafiosoId
   *          Mafioso identification
   * @return none
   */
  public NotDenounceExtortionAction( int extortionId, int entrepreneurId, int stateId, int mafiosoId ) {
    super( Actions.NOT_DENOUNCE_EXTORTION.ordinal(),
        Actions.NOT_DENOUNCE_EXTORTION.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.STATE_ID, stateId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
  }
}