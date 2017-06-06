package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class CollaborateAction extends ActionAbstract {
  
  public enum Param {
    MAFIOSO_ID,
    ENTREPRENEUR_ID;
  }
  
  
  /**
   * CollaborateAction constructor
   * 
   * @param mafiosoId
   *          Mafioso identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @return none
   */
  public CollaborateAction(int mafiosoId, int entrepreneurId) {
    super(Actions.COLLABORATE.ordinal(), Actions.COLLABORATE.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.MAFIOSO_ID, mafiosoId);
    this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
  }
}