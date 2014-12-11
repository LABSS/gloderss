package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class StateCompensationAction extends ActionAbstract {
	
	public enum Param {
		EXTORTION_ID,
		STATE_ID,
		ENTREPRENEUR_ID,
		COMPENSATION;
	}
	
	
	/**
	 * StateCompensationAction constructor
	 * 
	 * @param extortionId
	 *          Extortion identification
	 * @param stateId
	 *          State identification
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param compensation
	 *          Compensation amount
	 * @return none
	 */
	public StateCompensationAction(int extortionId, int stateId,
			int entrepreneurId, double assistance) {
		super(Actions.STATE_COMPENSATION.ordinal(), Actions.STATE_COMPENSATION
				.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.EXTORTION_ID, extortionId);
		this.params.put(Param.STATE_ID, stateId);
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.COMPENSATION, assistance);
	}
}