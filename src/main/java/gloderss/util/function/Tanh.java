package gloderss.util.function;

import java.util.ArrayList;
import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

public class Tanh implements Function {
  
  @Override
  public String getName() {
    return "tanh";
  }
  
  
  @Override
  public FunctionResult execute(final Evaluator evaluator,
      final String arguments) throws FunctionException {
    Double result = null;
    
    @SuppressWarnings("rawtypes")
    ArrayList numbers = FunctionHelper.getDoubles(arguments,
        EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);
    
    if(numbers.size() != 2) {
      throw new FunctionException("Two numeric arguments are required.");
    }
    
    try {
      double x = ((Double) numbers.get(0)).doubleValue();
      double slope = ((Double) numbers.get(1)).doubleValue();
      
      result = (Math.exp(slope * x) - 1.0) / (Math.exp(slope * x) + 1.0);
      
    } catch(Exception e) {
      throw new FunctionException("Two numeric arguments are required.", e);
    }
    
    return new FunctionResult(result.toString(),
        FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
  }
}