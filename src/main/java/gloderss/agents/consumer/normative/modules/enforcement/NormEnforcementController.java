package gloderss.agents.consumer.normative.modules.enforcement;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.DeviationAbstract.Type;
import emilia.modules.enforcement.NormEnforcementAbstract;
import gloderss.Constants.Norms;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.NotDenounceExtortionAction;
import gloderss.actions.NotPayExtortionAction;
import gloderss.actions.PayExtortionAction;
import gloderss.agents.entrepreneur.normative.modules.enforcement.ComplianceDeviation;
import gloderss.agents.entrepreneur.normative.modules.enforcement.ViolationDeviation;
import gloderss.normative.entity.norm.NormContent;
import gloderss.normative.entity.norm.NormContentSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	private NormativeBoardInterface	normativeBoard;
	
	private double									sanctionThreshold;
	
	
	/**
	 * Create Norm Enforcement controller
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param normativeBoard
	 *          Normative board
	 * @param sanctionThreshold
	 *          Threshold to sanction the Entrepreneur
	 * @return none
	 */
	public NormEnforcementController(Integer agentId,
			NormativeBoardInterface normativeBoard, Double sanctionThreshold) {
		super(agentId);
		
		this.normativeBoard = normativeBoard;
		this.sanctionThreshold = sanctionThreshold;
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
		
		Polarity polarity;
		if(evaluation.getType().equals(Type.VIOLATION)) {
			polarity = Polarity.NEGATIVE;
		} else {
			polarity = Polarity.POSITIVE;
		}
		
		for(SanctionEntityAbstract sanction : sanctions) {
			
			if(sanction.getCategory().getPolarity().equals(polarity)) {
				
				if(event instanceof ActionEvent) {
					ActionEvent actionEvent = (ActionEvent) event;
					
					if(actionEvent.getAction() instanceof PayExtortionAction) {
						
						if(sanction.getContent() instanceof ReputationSanction) {
							
							if(this.normativeBoard.getSalience(Norms.NOT_PAY_EXTORTION
									.ordinal()) > this.normativeBoard
									.getSalience(Norms.PAY_EXTORTION.ordinal())) {
								
								PayExtortionAction action = (PayExtortionAction) actionEvent
										.getAction();
								
								sanction.getContent().execute(
										(int) action
												.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
							
						} else if(sanction.getContent() instanceof SanctionSanction) {
							
							if(this.normativeBoard.getSalience(Norms.NOT_PAY_EXTORTION
									.ordinal()) >= this.sanctionThreshold) {
								
								PayExtortionAction action = (PayExtortionAction) actionEvent
										.getAction();
								
								sanction.getContent().execute(
										(int) action
												.getParam(PayExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
						}
						
					} else if(actionEvent.getAction() instanceof NotPayExtortionAction) {
						
						if(sanction.getContent() instanceof ReputationSanction) {
							
							if(this.normativeBoard.getSalience(Norms.PAY_EXTORTION.ordinal()) > this.normativeBoard
									.getSalience(Norms.NOT_PAY_EXTORTION.ordinal())) {
								
								NotPayExtortionAction action = (NotPayExtortionAction) actionEvent
										.getAction();
								
								sanction.getContent().execute(
										(int) action
												.getParam(NotPayExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
						} else if(sanction.getContent() instanceof SanctionSanction) {
							
							if(this.normativeBoard.getSalience(Norms.PAY_EXTORTION.ordinal()) >= this.sanctionThreshold) {
								
								NotPayExtortionAction action = (NotPayExtortionAction) actionEvent
										.getAction();
								
								sanction.getContent().execute(
										(int) action
												.getParam(NotPayExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
						}
						
					} else if(actionEvent.getAction() instanceof DenounceExtortionAction) {
						
						if(sanction.getContent() instanceof ReputationSanction) {
							
							if(this.normativeBoard.getSalience(Norms.NOT_DENOUNCE.ordinal()) > this.normativeBoard
									.getSalience(Norms.DENOUNCE.ordinal())) {
								
								DenounceExtortionAction action = (DenounceExtortionAction) actionEvent
										.getAction();
								
								sanction
										.getContent()
										.execute(
												(int) action
														.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
							
						} else if(sanction.getContent() instanceof SanctionSanction) {
							
							if(this.normativeBoard.getSalience(Norms.NOT_DENOUNCE.ordinal()) >= this.sanctionThreshold) {
								
								DenounceExtortionAction action = (DenounceExtortionAction) actionEvent
										.getAction();
								
								sanction
										.getContent()
										.execute(
												(int) action
														.getParam(DenounceExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
						}
						
					} else if(actionEvent.getAction() instanceof NotDenounceExtortionAction) {
						
						if(sanction.getContent() instanceof ReputationSanction) {
							
							if(this.normativeBoard.getSalience(Norms.DENOUNCE.ordinal()) > this.normativeBoard
									.getSalience(Norms.NOT_DENOUNCE.ordinal())) {
								
								NotDenounceExtortionAction action = (NotDenounceExtortionAction) actionEvent
										.getAction();
								
								sanction
										.getContent()
										.execute(
												(int) action
														.getParam(NotDenounceExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
							}
							
						} else if(sanction.getContent() instanceof SanctionSanction) {
							
							if(this.normativeBoard.getSalience(Norms.DENOUNCE.ordinal()) >= this.sanctionThreshold) {
								
								NotDenounceExtortionAction action = (NotDenounceExtortionAction) actionEvent
										.getAction();
								
								sanction
										.getContent()
										.execute(
												(int) action
														.getParam(NotDenounceExtortionAction.Param.ENTREPRENEUR_ID));
								
								enforceSanctions.add(sanction);
								
							}
						}
					}
				}
			}
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