package gloderss.rating;

import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.PayExtortionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateFinderRating extends ReputationAbstract {
  
  private final static Logger logger = LoggerFactory
      .getLogger(StateFinderRating.class);
  
  private int                 numPayers;
  
  private int                 numCollaborationReqs;
  
  private boolean             unknown;
  
  private double              value;
  
  
  public StateFinderRating(double unknownValue) {
    super(unknownValue);
    
    this.numPayers = 0;
    this.numCollaborationReqs = 0;
    
    this.unknown = true;
    this.value = unknownValue;
  }
  
  
  @Override
  public boolean isUnknown(int... target) {
    return this.unknown;
  }
  
  
  @Override
  public double getReputation(int... target) {
    return this.value;
  }
  
  
  @Override
  public void setReputation(int target, double value) {
    this.unknown = false;
    this.value = value;
  }
  
  
  @Override
  public void updateReputation(Object action) {
    
    boolean updated = false;
    if(action instanceof PayExtortionAction) {
      updated = true;
      this.numPayers++;
    } else if(action instanceof CollaborationRequestAction) {
      updated = true;
      this.numCollaborationReqs++;
    }
    
    if(updated) {
      this.unknown = false;
      
      this.value = Math.min(1.0,
          ((double) this.numCollaborationReqs / (double) this.numPayers));
      
      logger.debug("[FINDER_STATE_REPUTATION] " + this.value);
    }
  }
}