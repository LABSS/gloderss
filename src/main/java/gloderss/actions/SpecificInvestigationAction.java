package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class SpecificInvestigationAction extends ActionAbstract {
	
	public enum Param {
		POLICE_OFFICER_ID,
		ENTREPRENEUR_ID;
	}
	
	
	/**
	 * SpecificInvestigationAction constructor
	 * 
	 * @param policeOfficerId
	 *          Police officer identification
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @return none
	 */
	public SpecificInvestigationAction(int policeOfficerId, int entrepreneurId) {
		super(Actions.SPECIFIC_INVESTIGATION.ordinal(),
				Actions.SPECIFIC_INVESTIGATION.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.POLICE_OFFICER_ID, policeOfficerId);
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
	}
}