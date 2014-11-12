package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class MafiaBenefitAction extends ActionAbstract {
	
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
	public MafiaBenefitAction(int extortionId) {
		super(Actions.MAFIA_BENEFIT.ordinal(), Actions.MAFIA_BENEFIT.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.EXTORTION_ID, extortionId);
	}
}