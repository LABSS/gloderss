package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class MafiaPunishmentAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    ENTREPRENEUR_ID,
    MAFIOSO_ID,
    PUNISHMENT;
  }
  
  
  /**
   * Mafia punishment constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param mafiosoId
   *          Mafioso identification
   * @param punishment
   *          Punishment amount
   * @return none
   */
  public MafiaPunishmentAction(int extortionId, int entrepreneurId,
      int mafiosoId, double punishment) {
    super(Actions.MAFIA_PUNISHMENT.ordinal(), Actions.MAFIA_PUNISHMENT.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.EXTORTION_ID, extortionId);
    this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
    this.params.put(Param.MAFIOSO_ID, mafiosoId);
    this.params.put(Param.PUNISHMENT, punishment);
  }
}