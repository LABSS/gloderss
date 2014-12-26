package gloderss.agents.consumer.normative.modules.enforcement;

import emilia.entity.sanction.SanctionContentInterface;
import gloderss.actions.ReputationInfoAction;
import gloderss.reputation.EntrepreneurPayerReputation;

public class ReputationSanction implements SanctionContentInterface {
	
	private int													consumerId;
	
	private EntrepreneurPayerReputation	entrepreneurPayerRep;
	
	private ReputationInfoAction				repInfoAction;
	
	
	/**
	 * Create a reputation sanction content
	 * 
	 * @param none
	 * @return none
	 */
	public ReputationSanction(int consumerId,
			EntrepreneurPayerReputation entrepreneurPayerRep) {
		this.entrepreneurPayerRep = entrepreneurPayerRep;
	}
	
	
	@Override
	public void execute(Object input) {
		
		int entrepreneurId = (int) input;
		double repValue;
		if(this.entrepreneurPayerRep.isUnknown(entrepreneurId)) {
			repValue = this.entrepreneurPayerRep.getUnknownValue();
		} else {
			repValue = this.entrepreneurPayerRep.getReputation(entrepreneurId);
		}
		
		this.repInfoAction = new ReputationInfoAction(this.consumerId,
				entrepreneurId, repValue);
	}
	
	
	@Override
	public Object getSanction() {
		return this.repInfoAction;
	}
	
}