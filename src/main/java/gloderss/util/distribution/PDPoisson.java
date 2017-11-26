package gloderss.util.distribution;

import gloderss.util.random.RandomUtil;

public class PDPoisson extends PDFAbstract {
  
  private double lambda;
  
  
  public PDPoisson( double lambda ) {
    this.lambda = lambda;
  }
  
  
  @Override
  public double nextValue() {
    return RandomUtil.nextPoisson( this.lambda );
  }
}