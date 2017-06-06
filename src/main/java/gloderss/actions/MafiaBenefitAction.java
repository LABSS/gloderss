package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class MafiaBenefitAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    ENTREPRENEUR_ID,
    MAFIOSO_ID,
    BENEFIT;
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
   * @param benefit
   *          Benefit amount
   * @return none
   */
  public MafiaBenefitAction(int extortionId, int entrepreneurId, int mafiosoId,
      double benefit) {
    super(Actions.MAFIA_BENEFIT.ordinal(), Actions.MAFIA_BENEFIT.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.EXTORTION_ID, extortionId);
    this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
    this.params.put(Param.MAFIOSO_ID, mafiosoId);
    this.params.put(Param.BENEFIT, benefit);
  }
}