package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class AffiliationDeniedAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID
	}
	
	
	/**
	 * AffiliationDeniedAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @return none
	 */
	public AffiliationDeniedAction(int entrepreneurId) {
		super(Actions.AFFILIATION_DENIED.ordinal(), Actions.AFFILIATION_DENIED
				.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
	}
}