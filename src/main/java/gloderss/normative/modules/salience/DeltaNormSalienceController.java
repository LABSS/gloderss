package gloderss.normative.modules.salience;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.board.NormativeBoardInterface;
import emilia.modules.salience.DataType;
import emilia.modules.salience.NormInfoRepositoryMemory;
import emilia.modules.salience.NormSalienceAbstract;

public class DeltaNormSalienceController extends NormSalienceAbstract {
  
  @SuppressWarnings ( "unused" )
  private static final Logger  logger = LoggerFactory
      .getLogger( DeltaNormSalienceController.class );
  
  private static final double  delta  = 0.05;
  
  private Map<Integer, Double> initValues;
  
  
  /**
   * Constructor
   * 
   * @param agentId
   *          Agent identification
   * @param normativeBoard
   *          Normative board
   * @return none
   */
  public DeltaNormSalienceController( Integer agentId, NormativeBoardInterface normativeBoard ) {
    super( agentId, normativeBoard );
    this.repository = new NormInfoRepositoryMemory();
    this.initValues = new HashMap<Integer, Double>();
  }
  
  
  @Override
  public void setInitialValue( int normId, Object initialValues ) {
    
    if ( initialValues instanceof Map ) {
      @SuppressWarnings ( "unchecked" )
      Map<DataType, Integer> values = (Map<DataType, Integer>) initialValues;
      
      for ( DataType dataType : values.keySet() ) {
        int value = values.get( dataType );
        this.repository.setNormInfo( normId, dataType, value );
      }
    }
  }
  
  
  /**
   * Update salience
   * 
   * @param normId
   *          Norm identification
   * @param dataType
   *          Data Type
   * @return none
   */
  @Override
  public void updateSalience( int normId ) {
    
    // Get the Salience
    double salience = 0.0;
    if ( !this.initValues.containsKey( normId ) ) {
      salience = this.normativeBoard.getSalience( normId );
      this.initValues.put( normId, salience );
    } else {
      salience = this.initValues.get( normId );
    }
    
    // Calculate the new value based on the info received
    int compliance = this.repository.getNormInfo( normId, DataType.COMPLIANCE );
    int violation = this.repository.getNormInfo( normId, DataType.VIOLATION );
    int obsCompliance = this.repository.getNormInfo( normId,
        DataType.COMPLIANCE_OBSERVED );
    int obsViolation = this.repository.getNormInfo( normId,
        DataType.VIOLATION_OBSERVED );
    int punishment = this.repository.getNormInfo( normId, DataType.PUNISHMENT );
    int sanction = this.repository.getNormInfo( normId, DataType.SANCTION );
    int normInvocationCompliance = this.repository.getNormInfo( normId,
        DataType.COMPLIANCE_INVOKED );
    int normInvocationViolation = this.repository.getNormInfo( normId,
        DataType.VIOLATION_INVOKED );
    
    double own = compliance - violation;
    double obs = obsCompliance - obsViolation;
    
    double npv = 0;
    if ( (obsViolation + violation) > 0 ) {
      npv = (double) Math.max( 0,
          (obsViolation + violation) - punishment - sanction )
          / (double) (obsViolation + violation);
      
    }
    
    double p = 0;
    double s = 0;
    if ( (Math.max( punishment + sanction, obsViolation + violation )) > 0 ) {
      
      if ( punishment > 0 ) {
        p = (double) punishment
            / (double) (Math.max( punishment, obsViolation + violation ));
      }
      
      if ( sanction > 0 ) {
        s = (double) sanction
            / (double) (Math.max( sanction, obsViolation + violation ));
      }
    }
    
    double e = normInvocationCompliance - normInvocationViolation;
    
    salience += (own + obs - npv + s + p + e) * delta;
    
    salience = Math.max( 0, Math.min( 1, salience ) );
    
    // Set the Salience in the Normative Board
    this.normativeBoard.setSalience( normId, salience );
  }
}