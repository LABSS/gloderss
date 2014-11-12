package gloderss.reputation;

public class StateProtectorReputation implements IReputation {
	
	private double	value;
	
	
	public StateProtectorReputation(double initValue) {
		this.value = initValue;
	}
	
	
	@Override
	public double getReputation(int... target) {
		return this.value;
	}
	
	
	@Override
	// TODO
	public void updateReputation(Object... objects) {
	}
}