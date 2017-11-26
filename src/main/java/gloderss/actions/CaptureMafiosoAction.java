package gloderss.actions;

import java.util.HashMap;
import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;

public class CaptureMafiosoAction extends ActionAbstract {
  
  public enum Param {
    EXTORTION_ID,
    POLICE_OFFICER_ID,
    MAFIOSO_ID;
  }
  
  
  /**
   * CaptureMafiosoAction constructor
   * 
   * @param extortionId
   *          Extortion identification
   * @param policeOfficerId
   *          Police officer identification
   * @param mafiosoId
   *          Mafioso identification
   * @return none
   */
  public CaptureMafiosoAction( int extortionId, int policeOfficerId, int mafiosoId, boolean extortion ) {
    super( Actions.CAPTURE_MAFIOSO.ordinal(), Actions.CAPTURE_MAFIOSO.name() );
    
    this.params = new HashMap<Object, Object>();
    this.params.put( Param.EXTORTION_ID, extortionId );
    this.params.put( Param.POLICE_OFFICER_ID, policeOfficerId );
    this.params.put( Param.MAFIOSO_ID, mafiosoId );
  }
}