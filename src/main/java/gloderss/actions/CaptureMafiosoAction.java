package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class CaptureMafiosoAction extends ActionAbstract {
	
	public enum Param {
		POLICE_OFFICER_ID, MAFIOSO_ID;
	}
	
	
	/**
	 * CaptureMafiosoAction constructor
	 * 
	 * @param policeOfficerId
	 *          Police officer identification
	 * @param mafiosoId
	 *          Mafioso identification
	 * @return none
	 */
	public CaptureMafiosoAction(int policeOfficerId, int mafiosoId) {
		super(Actions.CAPTURE_MAFIOSO.ordinal(), Actions.CAPTURE_MAFIOSO.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.POLICE_OFFICER_ID, policeOfficerId);
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
	}
}