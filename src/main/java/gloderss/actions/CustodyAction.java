package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class CustodyAction extends ActionAbstract {
	
	public enum Param {
		MAFIOSO_ID, DURATION;
	}
	
	
	/**
	 * EmprisonmentAction constructor
	 * 
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param duration
	 *          Custody duration
	 * @return none
	 */
	public CustodyAction(int mafiosoId, double duration) {
		super(Actions.CUSTODY.ordinal(), Actions.CUSTODY.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.DURATION, duration);
	}
}