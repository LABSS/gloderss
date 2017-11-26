package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class ExtortionAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    MAFIOSO_ID,
    ENTREPRENEUR_ID,
    EXTORTION,
    PUNISHMENT,
    BENEFIT;
  }
  
  
  /**
   * ExtortionAction constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param mafiosoId
   *          Mafioso identification
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param extortion
   *          Extortion amount
   * @param punishment
   *          Punishment amount
   * @param benefit
   *          Benefit amount
   * @return none
   */
  public ExtortionAction( int extortionId, int mafiosoId, int entrepreneurId, double extortion, double punishment, double benefit ) {
    super( Actions.EXTORTION.ordinal(), Actions.EXTORTION.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
    this.params.put( Param.ENTREPRENEUR_ID, entrepreneurId );
    this.params.put( Param.EXTORTION, extortion );
    this.params.put( Param.PUNISHMENT, punishment );
    this.params.put( Param.BENEFIT, benefit );
  }
}