package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class AffiliationDeniedAction extends ActionAbstract {
  
  public enum Param {
    ENTREPRENEUR_ID,
    INTERMEDIARY_ORGANIZATION_ID
  }
  
  
  /**
   * AffiliationDeniedAction constructor
   * 
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param ioId
   *          Intermediary organization identification
   * @return none
   */
  public AffiliationDeniedAction(int entrepreneurId, int ioId) {
    super(Actions.AFFILIATION_DENIED.ordinal(),
        Actions.AFFILIATION_DENIED.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
    this.params.put(Param.INTERMEDIARY_ORGANIZATION_ID, ioId);
  }
}