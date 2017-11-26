package gloderss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gloderss.main.RunSimulation;

public class GLODERSSimulator {
  
  private final static Logger logger = LoggerFactory
      .getLogger( GLODERSSimulator.class );
  
  
  public static void main( String[] args ) {
    if ( args.length < 2 ) {
      System.out
          .println( "Syntax: GLODERSSimulator [XML Filename] [XSD Filename]" );
      System.exit( 1 );
    }
    
    long startTime = System.currentTimeMillis();
    logger.debug( "[START_TIME] " + startTime );
    RunSimulation gloderss = new RunSimulation( args[0], args[1] );
    gloderss.run();
    
    long endTime = System.currentTimeMillis();
    logger.debug( "[END_TIME] " + endTime );
    
    logger.debug( "[ELAPSED] " + (endTime - startTime) );
  }
}