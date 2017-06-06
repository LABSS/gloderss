package gloderss.actions;

import emilia.entity.action.ActionAbstract;
import gloderss.Constants.Actions;
import java.util.HashMap;

public class CriticalConsumerInfoAction extends ActionAbstract {
  
  public enum Param {
    INTERMEDIARY_ORGANIZATION_ID,
    ENTEPRENEUR_ID,
    CRITICAL_CONSUMERS;
  }
  
  
  /**
   * CriticalConsumerInfoAction constructor
   * 
   * @param entrepreneurId
   *          Entrepreneur identification
   * @param ioId
   *          Intermediary organization identification
   * @param criticalConsumer
   *          Critical consumers
   * @return none
   */
  public CriticalConsumerInfoAction(int ioId, int entrepreneurId,
      double criticalConsumer) {
    super(Actions.CRITICAL_CONSUMER_INFO.ordinal(),
        Actions.CRITICAL_CONSUMER_INFO.name());
    
    this.params = new HashMap<Object, Object>();
    this.params.put(Param.INTERMEDIARY_ORGANIZATION_ID, ioId);
    this.params.put(Param.ENTEPRENEUR_ID, entrepreneurId);
    this.params.put(Param.CRITICAL_CONSUMERS, criticalConsumer);
  }
}