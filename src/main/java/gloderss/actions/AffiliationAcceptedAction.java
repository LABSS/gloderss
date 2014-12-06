package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class AffiliationAcceptedAction extends ActionAbstract {
	
	public enum Param {
		ENTREPRENEUR_ID,
		INTERMEDIARY_ORGANIZATION_ID
	}
	
	
	/**
	 * AffiliationAcceptedAction constructor
	 * 
	 * @param entrepreneurId
	 *          Entrepreneur identification
	 * @param ioId
	 *          Intermediary organization identification
	 * @return none
	 */
	public AffiliationAcceptedAction(int entrepreneurId, int ioId) {
		super(Actions.AFFILIATION_ACCEPTED.ordinal(), Actions.AFFILIATION_ACCEPTED
				.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.ENTREPRENEUR_ID, entrepreneurId);
		this.params.put(Param.INTERMEDIARY_ORGANIZATION_ID, ioId);
	}
}