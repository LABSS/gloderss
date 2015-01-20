package gloderss.agents.entrepreneur.normative.modules.enforcement;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.NormEnforcementAbstract;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormContentSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormEnforcementController.class);
	
	
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
		
		if(event instanceof ActionEvent) {
			ActionEvent actionEvent = (ActionEvent) event;
			
			for(NormEntityAbstract norm : normSanctions.keySet()) {
				
				if(norm.getContent() instanceof NormContent) {
					NormContent normContent = (NormContent) norm.getContent();
					
					if(normContent.comply(actionEvent.getAction().getDescription())) {
						deviations.put(norm, new ComplianceDeviation());
					} else {
						deviations.put(norm, new ViolationDeviation());
					}
					
				} else if(norm.getContent() instanceof NormContentSet) {
					NormContentSet normContent = (NormContentSet) norm.getContent();
					
					if(normContent.comply(actionEvent.getAction().getDescription())) {
						deviations.put(norm, new ComplianceDeviation());
					} else {
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