package gloderss;

import gloderss.main.RunSimulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GLODERSBatch {
	
	private final static Logger	logger	= LoggerFactory
																					.getLogger(GLODERSBatch.class);
	
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Syntax: GLODERSBatch [XML Config Filename] [XSD Config Filename] [Param values]");
			System.exit(1);
		}
		
		String original = args[0];
		
		long startTime = System.currentTimeMillis();
		logger.debug("[START_TIME] " + startTime);
		RunSimulation gloderss = new RunSimulation(args[0], args[1]);
		gloderss.run();
		
		long endTime = System.currentTimeMillis();
		logger.debug("[END_TIME] " + endTime);
		
		logger.debug("[ELAPSED] " + (endTime - startTime));
	}
	
}