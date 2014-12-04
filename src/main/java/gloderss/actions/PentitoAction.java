package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;
import java.util.List;

public class PentitoAction extends ActionAbstract {
	
	public enum Param {
		MAFIOSO_ID,
		MAFIOSI_LIST,
		ENTREPRENEUR_LIST;
	}
	
	
	/**
	 * Pentiti action constructor
	 * 
	 * @param mafiosoId
	 *          Mafioso identification
	 * @param mafiosiList
	 *          List of all other Mafiosi it knows
	 * @param entrepreneurList
	 *          List of all Entrepreneurs that have paid extortion
	 * @return none
	 */
	public PentitoAction(int mafiosoId, List<Integer> mafiosiList,
			List<Integer> entrepreneursList) {
		super(Actions.PENTITI.ordinal(), Actions.PENTITI.name());
		
		this.params = new HashMap<Object, Object>();
		this.params.put(Param.MAFIOSO_ID, mafiosoId);
		this.params.put(Param.MAFIOSI_LIST, mafiosiList);
		this.params.put(Param.ENTREPRENEUR_LIST, entrepreneursList);
	}
}