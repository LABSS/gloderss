package gloderss.agents.consumer.normative.modules.enforcement;

import emilia.entity.sanction.SanctionContentInterface;
import gloderss.actions.ReputationInfoAction;
import gloderss.rating.EntrepreneurRating;

public class ReputationSanction implements SanctionContentInterface {
  
  private int                  consumerId;
  
  private EntrepreneurRating   entrepreneurRep;
  
  private ReputationInfoAction repInfoAction;
  
  
  /**
   * Create a reputation sanction content
   * 
   * @param consumerId
   *          Consumer identification
   * @param entrepreneurRep
   *          Entrepreneurs reputation
   * @return none
   */
  public ReputationSanction(int consumerId,
      EntrepreneurRating entrepreneurRep) {
    this.consumerId = consumerId;
    this.entrepreneurRep = entrepreneurRep;
  }
  
  
  @Override
  public void execute(Object input) {
    
    int entrepreneurId = (int) input;
    double repValue;
    if(this.entrepreneurRep.isUnknown(entrepreneurId)) {
      repValue = this.entrepreneurRep.getUnknownValue();
    } else {
      repValue = this.entrepreneurRep.getReputation(entrepreneurId);
    }
    
    this.repInfoAction = new ReputationInfoAction(this.consumerId,
        entrepreneurId, repValue);
  }
  
  
  @Override
  public Object getSanction() {
    return this.repInfoAction;
  }
  
}