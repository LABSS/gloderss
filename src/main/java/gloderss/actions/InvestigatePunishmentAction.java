package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class InvestigatePunishmentAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID, MAFIOSO_ID, INVESTIGATIVE_DURATION;
	}
	
	
	/**
	 * InvestigativePunishmentAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param investigativeDuration
	 *          Number of rounds to investigate the entrepreneur
	 * @return none
	 */
	public InvestigatePunishmentAction(int entrepreneurId, int mafiosoId,
			int investigativeDuration) {
		super(Actions.INVESTIGATE_PUNISHMENT.ordinal(),
				Actions.INVESTIGATE_PUNISHMENT.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.INVESTIGATIVE_DURATION, investigativeDuration);
	}
}