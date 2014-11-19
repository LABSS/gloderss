package gloderss.util.distribution;

import gloderss.util.random.RandomUtil;

public class PDNormal extends PDFAbstract {
	
	private double	mean;
	
	private double	stDev;
	
	
	public PDNormal(double mean, double stDev) {
		this.mean = mean;
		this.stDev = stDev;
	}
	
	
	public double nextValue() {
		return RandomUtil.nextNormal(mean, stDev);
	}
}