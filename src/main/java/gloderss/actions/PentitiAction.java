package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;
import java.util.List;

public class PentitiAction extends ActionAbstract {
	
	public enum Param {
		MAFIOSO_ID, MAFIOSI_LIST;
	}
	
	
	/**
	 * Pentiti action constructor
	 * 
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param mafiosiList
	 *          List of all other Mafiosi it knows
	 * @return none
	 */
	public PentitiAction(int mafiosoId, List<Integer> mafiosiList) {
		super(Actions.PENTITI.ordinal(), Actions.PENTITI.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.MAFIOSI_LIST, mafiosiList);
	}
}