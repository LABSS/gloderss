package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class CollaborationRequestAction extends ActionAbstract {
	
	public enum Param {
		MAFIOSO_ID,
		ENTREPRENEUR_ID;
	}
	
	
	/**
	 * CollaborationRequestAction constructor
	 * 
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @return none
	 */
	public CollaborationRequestAction(int mafiosoId, int entrepreneurId) {
		super(Actions.COLLABORATION_REQUEST.ordinal(),
				Actions.COLLABORATION_REQUEST.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
	}
}