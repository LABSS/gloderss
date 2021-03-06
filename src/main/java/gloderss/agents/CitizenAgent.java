package gloderss.agents;

import java.util.ArrayList;
import java.util.List;
import gloderss.engine.devs.EventSimulator;

public abstract class CitizenAgent extends AbstractAgent {
  
  protected List<Integer> neighbors;
  
  
  /**
   * Citizen constructor
   * 
   * @param id
   *          Citizen agent identification
   * @return none
   */
  public CitizenAgent( Integer id, EventSimulator simulator ) {
    super( id, simulator );
    
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
  
  
  public void setNeighbors( List<Integer> neighbors ) {
    
    // Remove the current neighbors as observed
    this.removeObservation( this.id, this.neighbors );
    
    // Add the new neighbors as observed
    this.neighbors.clear();
    for ( Integer neighborId : neighbors ) {
      this.neighbors.add( neighborId );
    }
    this.addObservation( this.id, this.neighbors );
  }
}