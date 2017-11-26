package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class ImprisonmentAction extends ActionAbstract {
  
  public enum Param {
    MAFIOSO_ID,
    DURATION;
  }
  
  
  /**
   * EmprisonmentAction constructor
   * 
   * @param mafiosoId
   *          Mafioso identification
   * @param duration
   *          Emprisonment duration
   * @return none
   */
  public ImprisonmentAction( int mafiosoId, double duration ) {
    super( Actions.IMPRISONMENT.ordinal(), Actions.IMPRISONMENT.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
    this.params.put( Param.DURATION, duration );
  }
}