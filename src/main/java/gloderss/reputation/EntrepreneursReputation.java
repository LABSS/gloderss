package gloderss.reputation;

import java.util.HashMap;
import java.util.Map;

public class EntrepreneursReputation implements IReputation {
	
	private Map<Integer, Double>	value;
	
	private double								unknownValue;
	
	
	public EntrepreneursReputation(double unknownValue) {
		this.value = new HashMap<Integer, Double>();
		this.unknownValue = unknownValue;
	}
	
	
	@Override
	public double getReputation(int... target) {
		double rep = this.unknownValue;
		
		if((target.length > 0) && (this.value.containsKey(target[0]))) {
			rep = this.value.get(target[0]);
		}
		
		return rep;
	}
	
	
	@Override
	// TODO
	public void updateReputation(Object... objects) {
	}
}