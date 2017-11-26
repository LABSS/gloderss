package gloderss.agents.consumer.normative;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.EmiliaController;
import emilia.board.NormativeBoardInterface;
import emilia.modules.enforcement.NormEnforcementAbstract;

public class EmiliaControllerConsumer extends EmiliaController {
  
  private static final Logger logger = LoggerFactory
      .getLogger( EmiliaControllerConsumer.class );
  
  private double              sanctionThreshold;
  
  
  public EmiliaControllerConsumer( int agentId, String xmlFilename, String xsdFilename, double sanctionThreshold ) {
    super( agentId, xmlFilename, xsdFilename );
    
    this.sanctionThreshold = sanctionThreshold;
  }
  
  
  @Override
  protected void setNormEnforcement( String normEnforcementClass ) {
    try {
      @SuppressWarnings ( "unchecked" )
      Class<NormEnforcementAbstract> neClass = (Class<NormEnforcementAbstract>) Class
          .forName( normEnforcementClass );
      
      Constructor<NormEnforcementAbstract> neConstructor = neClass
          .getDeclaredConstructor( Integer.class, NormativeBoardInterface.class,
              Double.class );
      
      this.normEnforcement = neConstructor.newInstance( this.agentId,
          this.normativeBoard, this.sanctionThreshold );
      
    } catch ( ClassNotFoundException e ) {
      logger.debug( e.toString() );
    } catch ( NoSuchMethodException e ) {
      logger.debug( e.toString() );
    } catch ( InvocationTargetException e ) {
      logger.debug( e.toString() );
    } catch ( IllegalAccessException e ) {
      logger.debug( e.toString() );
    } catch ( InstantiationException e ) {
      logger.debug( e.toString() );
    }
  }
}