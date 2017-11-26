package gloderss.util.function;

import java.util.ArrayList;
import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

public class Const implements Function {
  
  @Override
  public String getName() {
    return "const";
  }
  
  
  @Override
  public FunctionResult execute( final Evaluator evaluator,
      final String arguments ) throws FunctionException {
    Double result = null;
    
    @SuppressWarnings ( "rawtypes" )
    ArrayList numbers = FunctionHelper.getDoubles( arguments,
        EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR );
    
    if ( numbers.size() != 1 ) {
      throw new FunctionException( "One numeric arguments is required." );
    }
    
    try {
      result = ((Double) numbers.get( 0 )).doubleValue();
      
    } catch ( Exception e ) {
      throw new FunctionException( "One numeric arguments is required.", e );
    }
    
    return new FunctionResult( result.toString(),
        FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC );
  }
}