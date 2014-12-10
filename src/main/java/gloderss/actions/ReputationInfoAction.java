package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class ReputationInfoAction extends ActionAbstract {
	
	public enum Param {
		CONSUMER_ID,
		ENTREPRENEUR_ID,
		REPUTATION
	}
	
	
	/**
	 * ReputationInfoAction constructor
	 * 
	 * @param consumerId
	 *          Consumer identification
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param reputation
	 *          Reputation value
	 * @return none
	 */
	public ReputationInfoAction(int consumerId, int entrepreneurId,
			double reputation) {
		super(Actions.REPUTATION_INFO.ordinal(), Actions.REPUTATION_INFO.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.CONSUMER_ID, consumerId);
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.REPUTATION, reputation);
	}
}