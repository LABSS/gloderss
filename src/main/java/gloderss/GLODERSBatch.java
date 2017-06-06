package gloderss;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gloderss.conf.BatchCodeConf;
import gloderss.conf.BatchConf;
import gloderss.main.RunSimulation;
import gloderss.util.file.FileHandler;

public class GLODERSBatch {
  
  private final static Logger logger = LoggerFactory
      .getLogger(GLODERSBatch.class);
  
  
  public static void main(String[] args) {
    if(args.length < 1) {
      System.out.println(
          "Syntax: GLODERSBatch [XML Batch Filename] [XSD Batch Filename]");
      System.exit(1);
    }
    
    String xmlFilename = args[0];
    String xsdFilename = args[1];
    
    boolean valid = BatchConf.isValid(xmlFilename, xsdFilename);
    logger.debug("[XML VALID] " + valid);
    
    if(!valid) {
      System.out.println("INVALID XML FILE");
      System.exit(1);
    }
    
    BatchConf batchConf = BatchConf.getBatchConf(xmlFilename, xsdFilename);
    
    Map<String, String> citizens = new HashMap<String, String>();
    for(BatchCodeConf citizen : batchConf.getBatchCitizens()) {
      citizens.put(citizen.getCode(), citizen.getContent());
    }
    
    Map<String, String> states = new HashMap<String, String>();
    for(BatchCodeConf state : batchConf.getBatchStates()) {
      states.put(state.getCode(), state.getContent());
    }
    
    Map<String, String> mafias = new HashMap<String, String>();
    for(BatchCodeConf mafia : batchConf.getBatchMafias()) {
      mafias.put(mafia.getCode(), mafia.getContent());
    }
    
    Map<String, String> ios = new HashMap<String, String>();
    for(BatchCodeConf io : batchConf.getBatchIOs()) {
      ios.put(io.getCode(), io.getContent());
    }
    
    String headerContent = FileHandler
        .fileReader(batchConf.getBatchBase() + File.separator + "batch"
            + File.separator + batchConf.getBatchHeader());
    
    String generalContent = FileHandler
        .fileReader(batchConf.getBatchBase() + File.separator + "batch"
            + File.separator + batchConf.getBatchGeneral());
    
    String communicationContent = FileHandler
        .fileReader(batchConf.getBatchBase() + File.separator + "batch"
            + File.separator + batchConf.getBatchCommunication());
    
    String tailContent = FileHandler.fileReader(batchConf.getBatchBase()
        + File.separator + "batch" + File.separator + batchConf.getBatchTail());
    
    for(String citizenCode : citizens.keySet()) {
      
      String citizenContent = FileHandler
          .fileReader(batchConf.getBatchBase() + File.separator + "batch"
              + File.separator + citizens.get(citizenCode));
      
      for(String stateCode : states.keySet()) {
        String stateContent = FileHandler
            .fileReader(batchConf.getBatchBase() + File.separator + "batch"
                + File.separator + states.get(stateCode));
        
        for(String mafiaCode : mafias.keySet()) {
          String mafiaContent = FileHandler
              .fileReader(batchConf.getBatchBase() + File.separator + "batch"
                  + File.separator + mafias.get(mafiaCode));
          
          for(String ioCode : ios.keySet()) {
            String ioContent = FileHandler.fileReader(batchConf.getBatchBase()
                + File.separator + "batch" + File.separator + ios.get(ioCode));
            
            // Generate the XML Scenario
            String xmlScenario = headerContent + generalContent
                + communicationContent + citizenContent + stateContent
                + mafiaContent + ioContent + tailContent;
            
            FileHandler.fileWriter(batchConf.getBatchXMLScenario(),
                xmlScenario);
            
            String outputDir = citizenCode + "-" + stateCode + "-" + mafiaCode
                + "-" + ioCode;
            
            // Run the simulation
            long startTime = System.currentTimeMillis();
            logger.debug("[START_TIME] " + startTime);
            RunSimulation gloderss = new RunSimulation(
                batchConf.getBatchXMLScenario(),
                batchConf.getBatchXSDScenario());
            gloderss.run();
            
            long endTime = System.currentTimeMillis();
            logger.debug("[END_TIME] " + endTime);
            
            logger.debug("[ELAPSED] " + (endTime - startTime));
            
            // Move output files to the right directory
            String newOutput = batchConf.getBatchOutput() + File.separator
                + outputDir;
            (new File(newOutput)).mkdirs();
            
            // Move the configuration file to the output directory
            Path source = Paths.get(batchConf.getBatchXMLScenario());
            Path target = Paths
                .get(newOutput + File.separator + source.getFileName());
            try {
              Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch(IOException e) {
            }
            
            // Move the output file to the output directory
            for(Integer replica = 0; replica < batchConf
                .getBatchReplica(); replica++) {
              
              (new File(newOutput + File.separator + replica.toString()))
                  .mkdirs();
              source = Paths.get(batchConf.getBatchOutput() + File.separator
                  + replica.toString());
              target = Paths.get(batchConf.getBatchOutput() + File.separator
                  + outputDir + File.separator + replica.toString());
              try {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
              } catch(IOException e) {
              }
            }
          }
        }
      }
    }
  }
}