package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class PayExtortionAction extends ActionAbstract {
	
	public enum Param {
		EXTORTION_ID,
		MAFIOSO_ID,
		VICTIM_ID,
		EXTORTION;
	}
	
	
	/**
	 * Extortion constructor
	 * 
	 * @param extortionId
	 *          Extortion identification
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param victimId
	 *          Victim identification
	 * @param extortion
	 *          Extortion amount
	 * @return none
	 */
	public PayExtortionAction(int extortionId, int mafiosoId, int victimId,
			double extortion) {
		super(Actions.PAY_EXTORTION.ordinal(), Actions.PAY_EXTORTION.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.EXTORTION_ID, extortionId);
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.VICTIM_ID, victimId);
		this.params.put(Param.EXTORTION, extortion);
	}
}