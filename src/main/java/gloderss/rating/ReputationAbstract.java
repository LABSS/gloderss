package gloderss.rating;

public abstract class ReputationAbstract {
  
  public final static double MIN = 0.0;
  
  public final static double MAX = 1.0;
  
  protected double           unknownValue;
  
  
  public ReputationAbstract(double unknownValue) {
    this.unknownValue = unknownValue;
  }
  
  
  public double getUnknownValue() {
    return this.unknownValue;
  }
  
  
  public abstract boolean isUnknown(int... target);
  
  
  public abstract double getReputation(int... target);
  
  
  public abstract void setReputation(int target, double value);
  
  
  public abstract void updateReputation(Object action);
}