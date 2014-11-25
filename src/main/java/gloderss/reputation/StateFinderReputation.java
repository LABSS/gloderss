package gloderss.reputation;

public class StateFinderReputation extends ReputationAbstract {
	
	private double	value;
	
	
	public StateFinderReputation(double initValue) {
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