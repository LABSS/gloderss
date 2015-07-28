package gloderss.rating;

import gloderss.actions.MafiaPunishmentAction;
import gloderss.actions.NotPayExtortionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MafiaPunisherRating extends ReputationAbstract {
	
	private final static Logger	logger	= LoggerFactory
																					.getLogger(MafiaPunisherRating.class);
	
	private int									numNoPayers;
	
	private int									numPunishments;
	
	private boolean							unknown;
	
	private double							value;
	
	
	public MafiaPunisherRating(double unknownValue) {
		super(unknownValue);
		
		this.numNoPayers = 0;
		this.numPunishments = 0;
		
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
		if(action instanceof NotPayExtortionAction) {
			updated = true;
			this.numNoPayers++;
		} else if(action instanceof MafiaPunishmentAction) {
			updated = true;
			this.numPunishments++;
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