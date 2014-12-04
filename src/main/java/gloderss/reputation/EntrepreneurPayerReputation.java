package gloderss.reputation;

import java.util.HashMap;
import java.util.Map;

public class EntrepreneurPayerReputation extends ReputationAbstract {
	
	private Map<Integer, Double>	value;
	
	
	public EntrepreneurPayerReputation(double unknownValue) {
		super(unknownValue);
		
		this.value = new HashMap<Integer, Double>();
	}
	
	
	@Override
	public boolean isUnknown(int target) {
		
		if(this.value.containsKey(target)) {
			return false;
		}
		
		return false;
	}
	
	
	@Override
	public double getReputation(int... target) {
		
		if(this.value.containsKey(target)) {
			return this.value.get(target);
		}
		
		return this.unknownValue;
	}
	
	
	@Override
	public void setReputation(int target, double value) {
		this.value.put(target, value);
	}
	
	
	@Override
	// TODO
	public void updateReputation(Object... objects) {
	}
}