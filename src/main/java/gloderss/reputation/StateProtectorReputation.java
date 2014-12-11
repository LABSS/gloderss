package gloderss.reputation;

import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.StateCompensationAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateProtectorReputation extends ReputationAbstract {
	
	private final static Logger	logger	= LoggerFactory
																					.getLogger(StateProtectorReputation.class);
	
	private int									numDenounces;
	
	private int									numImprisonment;
	
	private int									numDenouncePunishment;
	
	private int									numStateCompensation;
	
	private boolean							unknown;
	
	private double							value;
	
	
	public StateProtectorReputation(double unknownValue) {
		super(unknownValue);
		
		this.numDenounces = 0;
		this.numImprisonment = 0;
		this.numDenouncePunishment = 0;
		this.numStateCompensation = 0;
		
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
		// State compensation
		if(action instanceof StateCompensationAction) {
			updated = true;
			this.numStateCompensation++;
			
			// Denounce extortion
		} else if(action instanceof DenounceExtortionAction) {
			updated = true;
			this.numDenounces++;
			
			// Denounce punishment
		} else if(action instanceof DenouncePunishmentAction) {
			updated = true;
			this.numDenounces++;
			this.numDenouncePunishment++;
			
			// Imprisonment
		} else if(action instanceof ImprisonmentAction) {
			updated = true;
			this.numImprisonment++;
			
		}
		
		if(updated) {
			this.unknown = false;
			
			this.value = 0.0;
			
			int divisor = 0;
			if(this.numDenounces > 0) {
				this.value += (double) this.numImprisonment
						/ (double) this.numDenounces;
				divisor++;
			}
			
			if(this.numDenouncePunishment > 0) {
				this.value += (double) this.numStateCompensation
						/ (double) this.numDenouncePunishment;
				divisor++;
			}
			
			if(divisor > 0) {
				this.value = (double) this.value / (double) divisor;
			}
			
			this.value = Math.max(0.0, Math.min(1.0, this.value));
			
			logger.debug("[PROTECTOR_STATE_REPUTATION] " + this.value);
		}
	}
}