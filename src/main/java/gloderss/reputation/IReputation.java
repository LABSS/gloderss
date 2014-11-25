package gloderss.reputation;

public interface IReputation {
	
	public double getReputation(int... target);
	
	
	public void setReputation(int target, double value);
	
	
	public void updateReputation(Object... objects);
}