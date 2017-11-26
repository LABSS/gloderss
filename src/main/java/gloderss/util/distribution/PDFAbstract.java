package gloderss.util.distribution;

public abstract class PDFAbstract {
  
  private enum PDF {
    CONSTANT("^CONSTANT\\([0-9]+\\)"),
    UNIFORM("^UNIFORM\\([0-9]+,[0-9]+\\)"),
    NORMAL("^NORMAL\\([0-9]+,[0-9]+\\)"),
    POISSON("^POISSON\\([0-9]+\\)");
    
    private String regex;
    
    
    private PDF( String regex ) {
      this.regex = regex;
    }
    
    
    public String getRegex() {
      return this.regex;
    }
  };
  
  private static PDF pdf;
  
  
  public static PDFAbstract getInstance( String function ) {
    PDFAbstract pd = null;
    
    String[] tokens = function.split( "\\(|\\,|\\)" );
    
    // Constant PDF
    if ( function.matches( PDF.CONSTANT.getRegex() ) ) {
      pdf = PDF.CONSTANT;
      
      try {
        double value = Double.parseDouble( tokens[1] );
        pd = new PDConstant( value );
      } catch ( NumberFormatException e ) {
        e.printStackTrace();
      }
      
      // Uniform PDF
    } else if ( function.matches( PDF.UNIFORM.getRegex() ) ) {
      pdf = PDF.UNIFORM;
      
      try {
        double minValue = Double.parseDouble( tokens[1] );
        double maxValue = Double.parseDouble( tokens[2] );
        pd = new PDUniform( minValue, maxValue );
      } catch ( NumberFormatException e ) {
        e.printStackTrace();
      }
      
      // Normal PDF
    } else if ( function.matches( PDF.NORMAL.getRegex() ) ) {
      pdf = PDF.NORMAL;
      
      try {
        double meanValue = Double.parseDouble( tokens[1] );
        double stDevValue = Double.parseDouble( tokens[2] );
        pd = new PDNormal( meanValue, stDevValue );
      } catch ( NumberFormatException e ) {
        e.printStackTrace();
      }
      
      // Poisson PDF
    } else if ( function.matches( PDF.POISSON.getRegex() ) ) {
      pdf = PDF.POISSON;
      
      try {
        double value = Double.parseDouble( tokens[1] );
        pd = new PDPoisson( value );
      } catch ( NumberFormatException e ) {
        e.printStackTrace();
      }
      
    } else {
      pd = new PDConstant( 0 );
    }
    
    return pd;
  }
  
  
  public PDF getPDF() {
    return pdf;
  }
  
  
  public abstract double nextValue();
}