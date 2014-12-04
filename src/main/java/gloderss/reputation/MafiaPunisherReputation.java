package gloderss.reputation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MafiaPunisherReputation extends ReputationAbstract {
	
	private final static Logger	logger					= LoggerFactory
																									.getLogger(MafiaPunisherReputation.class);
	
	private final static int		NUM_NO_PAYERS		= 0;
	
	private final static int		NUM_PUNISHMENTS	= 1;
	
	private int									numNoPayers;
	
	private int									numPunishments;
	
	private boolean							unknown;
	
	private double							value;
	
	
	public MafiaPunisherReputation(double unknownValue) {
		super(unknownValue);
		
		this.numNoPayers = 0;
		this.numPunishments = 0;
		
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
	 * Update the Mafia as Punisher Reputation as a ratio between the number of
	 * non-payers and the number of punishments inflicted by Mafia
	 * 
	 * @param numNoPayers
	 *          Incremental number of non-payers
	 * @param numPunishment
	 *          Incremental number of punishments inflicted by Mafia
	 * @return none
	 */
	@Override
	public void updateReputation(Object... objects) {
		if(objects.length >= 2) {
			
			boolean updated = false;
			if(objects[NUM_NO_PAYERS] instanceof Integer) {
				this.numNoPayers += (int) objects[NUM_NO_PAYERS];
				updated = true;
			}
			
			if(objects[NUM_PUNISHMENTS] instanceof Integer) {
				this.numPunishments += (int) objects[NUM_PUNISHMENTS];
				updated = true;
			}
			
			if(updated) {
				this.unknown = false;
				
				this.value = Math.min(1.0,
						((double) this.numPunishments / (double) this.numNoPayers));
				
				logger.debug("[PUNISHER_MAFIA_REPUTATION] " + this.numNoPayers + " "
						+ this.numPunishments + " " + this.value);
			}
		}
	}
}