package gloderss.reputation;

public class StateProtectorReputation extends ReputationAbstract {
	
	private boolean	unknown;
	
	private double	value;
	
	
	public StateProtectorReputation(double unknownValue) {
		super(unknownValue);
		
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
	
	
	@Override
	// TODO
	public void updateReputation(Object... objects) {
		this.unknown = false;
	}
}