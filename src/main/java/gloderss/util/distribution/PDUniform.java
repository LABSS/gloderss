package gloderss.util.distribution;

import gloderss.util.random.RandomUtil;

public class PDUniform extends PDFAbstract {
	
	private double	minValue;
	
	private double	maxValue;
	
	
	public PDUniform(double minValue, double maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	
	@Override
	public double nextValue() {
		return RandomUtil.nextDoubleFromTo(this.minValue, this.maxValue);
	}
}