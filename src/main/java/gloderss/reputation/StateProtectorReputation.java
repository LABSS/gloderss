package gloderss.reputation;

public class StateProtectorReputation extends ReputationAbstract {
	
	private double	value;
	
	
	public StateProtectorReputation(double initValue) {
		this.value = initValue;
	}
	
	
	@Override
	public double getReputation(int... target) {
		return this.value;
	}
	
	
	@Override
	public void setReputation(int target, double value) {
		this.value = value;
	}
	
	
	@Override
	// TODO
	public void updateReputation(Object... objects) {
	}
}