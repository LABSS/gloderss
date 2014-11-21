package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class StateCompensationAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID,
		COMPENSATION;
	}
	
	
	/**
	 * AssistanceEntrepreneurAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param compensation
	 *          Compensation amount
	 * @return none
	 */
	public StateCompensationAction(int entrepreneurId, double assistance) {
		super(Actions.STATE_COMPENSATION.ordinal(), Actions.STATE_COMPENSATION
				.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.COMPENSATION, assistance);
	}
}