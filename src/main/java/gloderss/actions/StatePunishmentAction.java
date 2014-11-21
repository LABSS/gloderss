package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class StatePunishmentAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID,
		PUNISHMENT;
	}
	
	
	/**
	 * AssistanceEntrepreneurAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param punishment
	 *          Punishment amount
	 * @return none
	 */
	public StatePunishmentAction(int entrepreneurId, double punishment) {
		super(Actions.STATE_PUNISHMENT.ordinal(), Actions.STATE_PUNISHMENT.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.PUNISHMENT, punishment);
	}
}