package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class ReleaseCustodyAction extends ActionAbstract {
	
	public enum Param {
	};
	
	
	/**
	 * ReleaseCustodyAction constructor
	 * 
	 * @param none
	 * @return none
	 */
	public ReleaseCustodyAction() {
		super(Actions.RELEASE_CUSTODY.ordinal(), Actions.RELEASE_CUSTODY.name());
		
		this.params = new HashMap<Object, Object>();
	}
}