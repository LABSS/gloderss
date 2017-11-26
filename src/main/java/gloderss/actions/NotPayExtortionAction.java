package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class NotPayExtortionAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    MAFIOSO_ID,
    ENTREPRENEUR_ID,
    EXTORTION;
  }
  
  
  /**
   * Not paying constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param mafiosoId
   *          Mafioso identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param extortion
   *          Extortion amount
   * @return none
   */
  public NotPayExtortionAction( int extortionId, int mafiosoId, int entrepreneurId, double extortion ) {
    super( Actions.NOT_PAY_EXTORTION.ordinal(),
        Actions.NOT_PAY_EXTORTION.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.EXTORTION, extortion );
  }
}