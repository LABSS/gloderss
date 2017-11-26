package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class ReleaseCustodyAction extends ActionAbstract {
  
  public enum Param {
    MAFIOSO_ID
  };
  
  
  /**
   * ReleaseCustodyAction constructor
   * 
   * @param mafiosoId
   *          Mafioso identification
   * @return none
   */
  public ReleaseCustodyAction( int mafiosoId ) {
    super( Actions.RELEASE_CUSTODY.ordinal(), Actions.RELEASE_CUSTODY.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
  }
}