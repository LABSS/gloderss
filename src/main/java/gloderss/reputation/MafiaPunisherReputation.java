package gloderss.reputation;

public class MafiaPunisherReputation implements IReputation {
	
	private double	value;
	
	
	public MafiaPunisherReputation(double initValue) {
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