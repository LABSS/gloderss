package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class BuyNotPayExtortionAction extends ActionAbstract {
	
	public enum Param {
		CONSUMER_ID,
		ENTREPRENEUR_ID;
	}
	
	
	/**
	 * BuyAction constructor
	 * 
	 * @param consumerId
	 *          Consumer identification
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @return none
	 */
	public BuyNotPayExtortionAction(int consumerId, int entrepreneurId) {
		super(Actions.BUY_PAY_EXTORTION.ordinal(), Actions.BUY_PAY_EXTORTION.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.CONSUMER_ID, consumerId);
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
	}
}