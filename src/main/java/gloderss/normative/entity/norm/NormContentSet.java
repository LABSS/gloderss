package gloderss.normative.entity.norm;

import emilia.entity.norm.NormContentInterface;
import gloderss.Constants.Actions;
import java.util.List;

public class NormContentSet implements NormContentInterface {
	
	// Action
	private List<Actions>	actions;
	
	// Negated action
	private List<Actions>	notActions;
	
	
	/**
	 * Create a norm content
	 * 
	 * @param actions
	 *          Actions
	 * @param notActions
	 *          Negated actions
	 * @return none
	 */
	public NormContentSet(List<Actions> actions, List<Actions> notActions) {
		this.actions = actions;
		this.notActions = notActions;
	}
	
	
	/**
	 * Get actions
	 * 
	 * @param none
	 * @return Actions
	 */
	public List<Actions> getActions() {
		return this.actions;
	}
	
	
	/**
	 * Get negated actions
	 * 
	 * @param none
	 * @return Negated actions
	 */
	public List<Actions> getNotActions() {
		return this.notActions;
	}
	
	
	@Override
	public boolean match(Object value) {
		if(value instanceof String) {
			for(Actions action : this.actions) {
				if(action.name().equalsIgnoreCase((String) value)) {
					return true;
				}
			}
			
			for(Actions noAction : this.notActions) {
				if(noAction.name().equalsIgnoreCase((String) value)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	@Override
	public boolean comply(Object value) {
		
		if(value instanceof String) {
			for(Actions action : this.actions) {
				if(action.name().equalsIgnoreCase((String) value)) {
					return true;
				}
			}
		}
		
		return false;
	}
}