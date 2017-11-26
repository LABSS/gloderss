package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class DenouncePunishmentAffiliatedAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    ENTREPRENEUR_ID,
    STATE_ID,
    MAFIOSO_ID,
    PUNISHMENT;
  }
  
  
  /**
   * DenouncePunishmentAction constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param stateId
   *          State identification
   * @param mafiosoId
   *          Mafioso identification
   * @param punishment
   *          Punishment amount
   * @return none
   */
  public DenouncePunishmentAffiliatedAction( int extortionId, int entrepreneurId, int stateId, int mafiosoId, double punishment ) {
    super( Actions.DENOUNCE_PUNISHMENT_AFFILIATED.ordinal(),
        Actions.DENOUNCE_PUNISHMENT_AFFILIATED.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.STATE_ID, stateId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
    this.params.put( Param.PUNISHMENT, punishment );
  }
}