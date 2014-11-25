package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class AffiliateRequestAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID
	}
	
	
	/**
	 * AffiliateRequestAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @return none
	 */
	public AffiliateRequestAction(int entrepreneurId) {
		super(Actions.AFFILIATE.ordinal(), Actions.AFFILIATE.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
	}
}