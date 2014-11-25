package gloderss.reputation;

public abstract class ReputationAbstract {
	
	public final static double	MIN	= 0.0;
	
	public final static double	MAX	= 1.0;
	
	
	public abstract double getReputation(int... target);
	
	
	public abstract void setReputation(int target, double value);
	
	
	public abstract void updateReputation(Object... objects);
}