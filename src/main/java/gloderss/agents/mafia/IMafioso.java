package gloderss.agents.mafia;

import gloderss.actions.CustodyAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.actions.ReleaseImprisonmentAction;

public interface IMafioso {
  
  public void initializeSim();
  
  
  public void decideExtortion();
  
  
  public void collectExtortion();
  
  
  public void receivePayment(PayExtortionAction action);
  
  
  public void decidePunishment(NotPayExtortionAction action);
  
  
  public void decideBenefit(PayExtortionAction action);
  
  
  public void custody(CustodyAction action);
  
  
  public void releaseCustody();
  
  
  public void imprisonment(ImprisonmentAction action);
  
  
  public void releaseImprisonment(ReleaseImprisonmentAction action);
  
  
  public void decidePentito();
  
  
  public void finalizeSim();
}