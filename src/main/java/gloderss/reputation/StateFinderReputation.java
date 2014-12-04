package gloderss.reputation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateFinderReputation extends ReputationAbstract {
	
	private final static Logger	logger											= LoggerFactory
																															.getLogger(StateFinderReputation.class);
	
	private final static int		NUM_PAYERS									= 0;
	
	private final static int		NUM_COLLABORATION_REQUESTS	= 1;
	
	private int									numPayers;
	
	private int									numCollaborationReqs;
	
	private boolean							unknown;
	
	private double							value;
	
	
	public StateFinderReputation(double unknownValue) {
		super(unknownValue);
		
		this.numPayers = 0;
		this.numCollaborationReqs = 0;
		
		this.unknown = true;
		this.value = unknownValue;
	}
	
	
	@Override
	public boolean isUnknown(int target) {
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
	
	
	/**
	 * Update the State Finder Reputation as a ratio between the number of payers
	 * and the number of collaboration requests the State issued
	 * 
	 * @param numPayers
	 *          Incremental number of payers
	 * @param numCollaborationReqs
	 *          Incremental number of collaboration requests the State issued
	 * @return none
	 */
	@Override
	public void updateReputation(Object... objects) {
		if(objects.length >= 2) {
			
			boolean updated = false;
			if(objects[NUM_PAYERS] instanceof Integer) {
				this.numPayers += (int) objects[NUM_PAYERS];
				updated = true;
			}
			
			if(objects[NUM_COLLABORATION_REQUESTS] instanceof Integer) {
				this.numCollaborationReqs += (int) objects[NUM_COLLABORATION_REQUESTS];
				updated = true;
			}
			
			if(updated) {
				this.unknown = false;
				
				this.value = Math.min(1.0,
						((double) this.numCollaborationReqs / (double) this.numPayers));
				
				logger.debug("[FINDER_STATE_REPUTATION] " + this.value);
			}
		}
	}
}