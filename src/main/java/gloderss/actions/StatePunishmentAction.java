package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class StatePunishmentAction extends ActionAbstract {
  
  public enum Param {
    STATE_ID,
    ENTREPRENEUR_ID,
    PUNISHMENT;
  }
  
  
  /**
   * StatePunishmentAction constructor
   * 
   * @param stateId
   *          State identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param punishment
   *          Punishment amount
   * @return none
   */
  public StatePunishmentAction(int stateId, int entrepreneurId,
      double punishment) {
    super(Actions.STATE_PUNISHMENT.ordinal(), Actions.STATE_PUNISHMENT.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.STATE_ID, stateId);
    this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
    this.params.put(Param.PUNISHMENT, punishment);
  }
}