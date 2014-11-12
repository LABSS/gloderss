package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class MafiaPunishmentAction extends ActionAbstract {
	
	public enum Param {
		EXTORTION_ID;
	}
	
	
	/**
	 * Mafia punishment constructor
	 * 
	 * @param extortionId
	 *          Extortion identification
	 * @return none
	 */
	public MafiaPunishmentAction(int extortionId) {
		super(Actions.MAFIA_PUNISHMENT.ordinal(), Actions.MAFIA_PUNISHMENT.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.EXTORTION_ID, extortionId);
	}
}