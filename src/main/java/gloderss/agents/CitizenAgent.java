package gloderss.agents;

import gloderss.engine.devs.EventSimulator;
import java.util.ArrayList;
import java.util.List;

public abstract class CitizenAgent extends AbstractAgent {
	
	private List<Integer>	neighbors;
	
	
	/**
	 * Citizen constructor
	 * 
	 * @param id
	 *          Citizen agent identification
	 * @return none
	 */
	public CitizenAgent(Integer id, EventSimulator simulator) {
		super(id, simulator);
		
		this.neighbors = new ArrayList<Integer>();
	}
	
	
	/*******************************
	 * 
	 * Getters and Setters
	 * 
	 *******************************/
	
	public List<Integer> getNeighbors() {
		return this.neighbors;
	}
	
	
	public void setNeighbors(List<Integer> neighbors) {
		
		// Remove the current neighbors as observed
		this.removeObservation(this.id, this.neighbors);
		
		// Add the new neighbors as observed
		this.neighbors = neighbors;
		this.addObservation(this.id, this.neighbors);
	}
}