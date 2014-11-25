package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class NormativeInfoSpreadAction extends ActionAbstract {
	
	public enum Param {
		AGENT_ID,
		NORMATIVE_INFO
	};
	
	
	/**
	 * AffiliationAction constructor
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param normativeInfo
	 *          Normative information
	 * @return none
	 */
	public NormativeInfoSpreadAction(int agentId, String normativeInfo) {
		super(Actions.NORMATIVE_INFO_SPREAD.ordinal(),
				Actions.NORMATIVE_INFO_SPREAD.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.AGENT_ID, agentId);
		this.params.put(Param.NORMATIVE_INFO, normativeInfo);
	}
}