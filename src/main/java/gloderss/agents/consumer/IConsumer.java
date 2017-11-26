package gloderss.agents.consumer;

import gloderss.actions.DeliverProductAction;

public interface IConsumer {
  
  public void initializeSim();
  
  
  public void buyProduct();
  
  
  public void receiveProduct( DeliverProductAction action );
  
  
  public void finalizeSim();
}