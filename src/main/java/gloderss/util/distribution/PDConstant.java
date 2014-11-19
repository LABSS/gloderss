package gloderss.util.distribution;

public class PDConstant extends PDFAbstract {
	
	private double	value;
	
	
	public PDConstant(double value) {
		this.value = value;
	}
	
	
	@Override
	public double nextValue() {
		return this.value;
	}
}