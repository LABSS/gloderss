package gloderss.agents.entrepreneur.normative.modules.enforcement;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.DeviationAbstract.Type;
import emilia.modules.enforcement.NormEnforcementAbstract;
import gloderss.normative.entity.norm.NormContent;
import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	/**
	 * Create Norm Enforcement controller
	 * 
	 * @param agentId
	 *          Agent identification
	 * @return none
	 */
	public NormEnforcementController(Integer agentId) {
		super(agentId);
	}
	
	
	@Override
	public Map<NormEntityAbstract, DeviationAbstract> detect(
			NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		
		Map<NormEntityAbstract, DeviationAbstract> deviations = new HashMap<NormEntityAbstract, DeviationAbstract>();
		
		NormContent normContent;
		ActionEvent actionEvent;
		for(NormEntityAbstract norm : normSanctions.keySet()) {
			if(norm.getContent() instanceof NormContent) {
				normContent = (NormContent) norm.getContent();
				
				if(event instanceof ActionEvent) {
					actionEvent = (ActionEvent) event;
					
					if(actionEvent.getAction().getDescription()
							.equalsIgnoreCase(normContent.getAction().name())) {
						deviations.put(norm, new ComplianceDeviation());
					} else if(actionEvent.getAction().getDescription()
							.equalsIgnoreCase(normContent.getNotAction().name())) {
						deviations.put(norm, new ViolationDeviation());
					}
				}
			}
		}
		
		return deviations;
	}
	
	
	@Override
	public List<SanctionEntityAbstract> evaluate(
			NormativeEventEntityAbstract event, NormEntityAbstract norm,
			List<SanctionEntityAbstract> sanctions, DeviationAbstract evaluation) {
		
		List<SanctionEntityAbstract> enforceSanctions = new ArrayList<SanctionEntityAbstract>();
		
		Polarity polarity;
		if(evaluation.getType().equals(Type.VIOLATION)) {
			polarity = Polarity.NEGATIVE;
		} else {
			polarity = Polarity.POSITIVE;
		}
		
		List<SanctionEntityAbstract> newSanctions = new ArrayList<SanctionEntityAbstract>();
		for(SanctionEntityAbstract sanction : sanctions) {
			if(sanction.getCategory().getPolarity().equals(polarity)) {
				newSanctions.add(sanction);
			}
		}
		
		SanctionEntityAbstract sanction;
		if(newSanctions.size() > 0) {
			sanction = newSanctions.get((int) ((newSanctions.size() - 1) * RandomUtil
					.nextDouble()));
			enforceSanctions.add(sanction);
		}
		
		return enforceSanctions;
	}
	
	
	@Override
	public void enforce(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, List<SanctionEntityAbstract> sanctions) {
	}
	
	
	@Override
	public void adapt(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, DeviationAbstract evaluation) {
	}
	
	
	@Override
	public void update() {
	}
}