package gloderss.agents.consumer.normative.modules.enforcement;

import emilia.entity.sanction.SanctionContentInterface;
import gloderss.actions.NormSanctionAction;

public class SanctionSanction implements SanctionContentInterface {
	
	private int									consumerId;
	
	private String							norm;
	
	private NormSanctionAction	normSanctionAction;
	
	
	/**
	 * Create a reputation sanction content
	 * 
	 * @param consumerId
	 *          Consumer identification
	 * @param norm
	 *          Norm sanctioned
	 * @return none
	 */
	public SanctionSanction(int consumerId, String norm) {
		this.consumerId = consumerId;
		this.norm = norm;
	}
	
	
	@Override
	public void execute(Object input) {
		int entrepreneurId = (int) input;
		this.normSanctionAction = new NormSanctionAction(this.consumerId,
				entrepreneurId, this.norm);
	}
	
	
	@Override
	public Object getSanction() {
		return this.normSanctionAction;
	}
	
}