package gloderss.normative.modules.adoption;

import emilia.board.NormativeBoardEventType;
import emilia.board.NormativeBoardInterface;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.modules.adoption.NormAdoptionAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormAdoptionController extends NormAdoptionAbstract {
	
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormAdoptionController.class);
	
	
	/**
	 * Create norm adoption
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormAdoptionController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
	}
	
	
	@Override
	public void receive(NormativeBoardEventType type, NormEntityAbstract oldNorm,
			NormEntityAbstract newNorm) {
		
		if((newNorm != null) && (newNorm.getStatus() != NormStatus.GOAL)) {
			
			logger.debug(type.name() + " " + newNorm.getContent().toString() + " "
					+ newNorm.getStatus().name());
		}
	}
}