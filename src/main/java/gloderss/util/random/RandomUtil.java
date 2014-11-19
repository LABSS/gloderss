package gloderss.util.random;

import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Normal;
import cern.jet.random.Poisson;
import cern.jet.random.Uniform;

public class RandomUtil {
	
	private static RandomEngine	generator;
	
	static {
		init();
	}
	
	
	private static void init() {
		generator = new MersenneTwister64((int) System.currentTimeMillis());
	}
	
	
	public static void setSeed(int seed) {
		generator = new MersenneTwister64(seed);
	}
	
	
	public static int nextInt() {
		return generator.nextInt();
	}
	
	
	public static int nextIntFromTo(int min, int max) {
		Uniform uni = new Uniform(min, max, generator);
		return uni.nextIntFromTo(min, max);
	}
	
	
	public static double nextDouble() {
		return generator.nextDouble();
	}
	
	
	public static double nextDoubleFromTo(double min, double max) {
		Uniform uni = new Uniform(min, max, generator);
		return uni.nextDoubleFromTo(min, max);
	}
	
	
	public static long nextLong() {
		return generator.nextLong();
	}
	
	
	public static float nextFloat() {
		return generator.nextFloat();
	}
	
	
	public static double nextExponential(double mean) {
		return -mean * Math.log(RandomUtil.nextDouble());
	}
	
	
	public static double nextNormal(double mean, double stDev) {
		Normal normal = new Normal(mean, stDev, generator);
		return normal.nextDouble();
	}
	
	
	public static double nextPoisson(double lambda) {
		Poisson poisson = new Poisson(lambda, generator);
		return poisson.nextDouble();
	}
}