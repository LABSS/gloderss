package gloderss.reputation;

public interface IReputation {
	
	public double getReputation(int... target);
	
	
	public void updateReputation(Object... objects);
}