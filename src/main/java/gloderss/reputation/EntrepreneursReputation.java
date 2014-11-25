package gloderss.reputation;

import java.util.HashMap;
import java.util.Map;

public class EntrepreneursReputation extends ReputationAbstract {
	
	private Map<Integer, Double>	value;
	
	private double								unknownValue;
	
	
	public EntrepreneursReputation(double unknownValue) {
		this.value = new HashMap<Integer, Double>();
		this.unknownValue = unknownValue;
	}
	
	
	@Override
	public double getReputation(int... target) {
		double rep = this.unknownValue;
		
		if(this.value.containsKey(target)) {
			rep = this.value.get(target);
		}
		
		return rep;
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