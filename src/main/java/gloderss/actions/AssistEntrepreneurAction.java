package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class AssistEntrepreneurAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID, ASSISTANCE;
	}
	
	
	/**
	 * AssistanceEntrepreneurAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param assistance
	 *          Assistance amount
	 * @return none
	 */
	public AssistEntrepreneurAction(int entrepreneurId, double assistance) {
		super(Actions.ASSISTANCE.ordinal(), Actions.ASSISTANCE.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.ASSISTANCE, assistance);
	}
}