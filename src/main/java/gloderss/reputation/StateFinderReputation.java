package gloderss.reputation;

public class StateFinderReputation implements IReputation {
	
	private Double	value;
	
	
	public StateFinderReputation(double initValue) {
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