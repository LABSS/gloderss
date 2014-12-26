package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class NormativeInfoAction extends ActionAbstract {
	
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
	public NormativeInfoAction(int agentId, String normativeInfo) {
		super(Actions.NORMATIVE_INFO.ordinal(), Actions.NORMATIVE_INFO.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.AGENT_ID, agentId);
		this.params.put(Param.NORMATIVE_INFO, normativeInfo);
	}
}