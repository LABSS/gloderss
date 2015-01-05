package gloderss.agents.consumer.normative;

import emilia.EmiliaController;
import emilia.board.NormativeBoardInterface;
import emilia.modules.enforcement.NormEnforcementAbstract;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmiliaControllerConsumer extends EmiliaController {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EmiliaControllerConsumer.class);
	
	
	public EmiliaControllerConsumer(int agentId, String xmlFilename,
			String xsdFilename) {
		super(agentId, xmlFilename, xsdFilename);
	}
	
	
	@Override
	protected void setNormEnforcement(String normEnforcementClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormEnforcementAbstract> neClass = (Class<NormEnforcementAbstract>) Class
					.forName(normEnforcementClass);
			
			Constructor<NormEnforcementAbstract> neConstructor = neClass
					.getDeclaredConstructor(Integer.class, NormativeBoardInterface.class);
			
			this.normEnforcement = neConstructor.newInstance(this.agentId,
					this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
}