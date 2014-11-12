package gloderss.normative.modules.adoption;

import emilia.board.NormativeBoardEventType;
import emilia.board.NormativeBoardInterface;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.modules.adoption.NormAdoptionAbstract;

public class NormAdoptionController extends NormAdoptionAbstract {
	
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
		
		if((type.equals(NormativeBoardEventType.INSERT_NORM))
				|| (type.equals(NormativeBoardEventType.UPDATE_NORM))) {
			
			if((newNorm != null) && (newNorm.getStatus() != NormStatus.GOAL)) {
				newNorm.setStatus(NormStatus.GOAL);
				this.normativeBoard.setNorm(newNorm);
			}
		}
	}
}