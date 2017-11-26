package gloderss.agents.mafia;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import gloderss.Constants;
import gloderss.agents.AbstractAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.Message;
import gloderss.conf.ChangeConf;
import gloderss.conf.MafiaConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.MafiaOutputEntity;
import gloderss.output.OutputController;
import gloderss.util.random.RandomUtil;

public class MafiaOrg extends AbstractAgent implements IMafiaOrg {
  
  private double                          loggingTimeUnit;
  
  private double                          minBenefit;
  
  private double                          maxBenefit;
  
  private double                          extortionLevel;
  
  private double                          punishmentSeverity;
  
  @SuppressWarnings ( "unused" )
  private double                          recruitingThreshold;
  
  @SuppressWarnings ( "unused" )
  private double                          recruitingProbability;
  
  private List<ChangeConf>                changesConf;
  
  private Map<Integer, MafiosoAgent>      mafiosi;
  
  private Map<Integer, EntrepreneurAgent> entrepreneurs;
  
  private List<Integer>                   domain;
  
  private Map<Integer, Double>            benefits;
  
  
  /**
   * Constructor
   * 
   * @param id
   *          Mafia identification
   * @param simulator
   *          Event simulator
   * @param conf
   *          Mafia configuration
   * @param stateId
   *          State organization identification
   * @param entrepreneurs
   *          List of all entrepreneurs
   * @return none
   */
  public MafiaOrg( Integer id, EventSimulator simulator, MafiaConf conf, Integer stateId, Map<Integer, EntrepreneurAgent> entrepreneurs ) {
    super( id, simulator );
    
    this.changesConf = conf.getChangesConf();
    
    this.loggingTimeUnit = conf.getLoggingTimeUnit();
    
    this.minBenefit = conf.getMinimumBenefit();
    
    this.maxBenefit = conf.getMaximumBenefit();
    
    this.extortionLevel = conf.getExtortionLevel();
    
    this.punishmentSeverity = conf.getPunishmentSeverity();
    
    this.recruitingThreshold = conf.getRecruitingThreshold();
    
    this.recruitingProbability = conf.getRecruitingProbability();
    
    this.entrepreneurs = entrepreneurs;
    this.domain = new Vector<Integer>();
    
    // Create Mafiosi
    MafiosoAgent mafioso;
    int mafiosoId = id + 1;
    this.mafiosi = new HashMap<Integer, MafiosoAgent>();
    for ( int i = 0; i < conf.getNumberMafiosi(); i++, mafiosoId++ ) {
      mafioso = new MafiosoAgent( mafiosoId, simulator, conf, this.getId(),
          stateId );
      this.mafiosi.put( mafiosoId, mafioso );
    }
    
    // Setup the benefits provided to each Entrepreneur
    this.benefits = new HashMap<Integer, Double>();
    this.setupBenefits();
  }
  
  
  /**
   * Setup the benefits provided to the entrepreneurs
   * 
   * @param none
   * @return none
   */
  private void setupBenefits() {
    for ( Integer eId : this.entrepreneurs.keySet() ) {
      double benefit = this.minBenefit
          + ((this.maxBenefit - this.minBenefit) * RandomUtil.nextDouble());
      
      this.benefits.put( eId, benefit );
    }
    
    for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
      mafioso.setBenefits( this.benefits );
    }
  }
  
  
  /*******************************
   * 
   * Getters and Setters
   * 
   *******************************/
  
  public Map<Integer, MafiosoAgent> getMafiosi() {
    return this.mafiosi;
  }
  
  
  /*******************************
   * 
   * Decision Processes
   * 
   *******************************/
  
  public int decideTarget() {
    
    if ( this.domain.isEmpty() ) {
      this.domain.addAll( this.entrepreneurs.keySet() );
      Collections.shuffle( this.domain );
    }
    
    int targetId = this.domain
        .remove( RandomUtil.nextIntFromTo( 0, (this.domain.size() - 1) ) );
    
    return targetId;
  }
  
  
  @Override
  public void initializeSim() {
    for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
      mafioso.initializeSim();
    }
    
    Event event = new Event( this.simulator.now(), this,
        Constants.EVENT_LOGGING_MAFIOSI );
    this.simulator.insert( event );
    
    // Schedule changes
    for ( ChangeConf change : this.changesConf ) {
      event = new Event( change.getTime(), this, change.getParameter(),
          change );
      this.simulator.insert( event );
    }
  }
  
  
  @Override
  public void spreadInformation() {
    // NOTHING
  }
  
  
  /**
   * Log Mafiosi data
   * 
   * @param none
   * @return none
   */
  private void loggingMafiosi() {
    for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
      AbstractEntity outputEntity = OutputController.getInstance()
          .getEntity( EntityType.MAFIA );
      outputEntity.setValue( MafiaOutputEntity.Field.TIME.name(),
          (int) this.simulator.now() );
      outputEntity.setValue( MafiaOutputEntity.Field.MAFIOSO_ID.name(),
          mafioso.getId() );
      outputEntity.setValue( MafiaOutputEntity.Field.EXTORTION_LEVEL.name(),
          this.extortionLevel );
      outputEntity.setValue( MafiaOutputEntity.Field.PUNISHMENT_SEVERITY.name(),
          this.punishmentSeverity );
      outputEntity.setValue( MafiaOutputEntity.Field.NEIGHBORS.name(),
          mafioso.getNeighbors().size() );
      outputEntity.setValue( MafiaOutputEntity.Field.WEALTH.name(),
          mafioso.getWeath() );
      outputEntity.setValue( MafiaOutputEntity.Field.CUSTODY.name(),
          mafioso.getCustodyStatus() );
      outputEntity.setValue( MafiaOutputEntity.Field.IMPRISONED.name(),
          mafioso.getPrisonStatus() );
      outputEntity.setValue( MafiaOutputEntity.Field.PENTITO.name(),
          mafioso.getPentitoStatus() );
      outputEntity.setActive();
    }
    
    Event event = new Event( this.simulator.now() + this.loggingTimeUnit, this,
        Constants.EVENT_LOGGING_MAFIOSI );
    this.simulator.insert( event );
  }
  
  
  @Override
  public void finalizeSim() {
    // this.loggingMafiosi();
  }
  
  
  /*******************************
   * 
   * Handle communication requests
   * 
   *******************************/
  
  @Override
  public synchronized void handleMessage( Message msg ) {
    // NOTHING
  }
  
  
  @Override
  public Object handleInfo( InfoAbstract info ) {
    Object infoRequested = null;
    
    if ( info.getType().equals( InfoAbstract.Type.REQUEST ) ) {
      
      InfoRequest request = (InfoRequest) info;
      switch ( request.getInfoRequest() ) {
        case Constants.REQUEST_ID:
          infoRequested = this.getId();
          break;
        case Constants.REQUEST_TARGET_ID:
          infoRequested = this.decideTarget();
          break;
      }
      
    } else if ( info.getType().equals( InfoAbstract.Type.SET ) ) {
      
    }
    
    return infoRequested;
  }
  
  
  @Override
  public void handleObservation( Message msg ) {
    // NOTHING
  }
  
  
  /*******************************
   * 
   * Handle simulation events
   * 
   *******************************/
  
  @Override
  public void handleEvent( Event event ) {
    
    ChangeConf change = null;
    if ( (event.getParameter() != null)
        && (event.getParameter() instanceof ChangeConf) ) {
      change = (ChangeConf) event.getParameter();
    }
    
    switch ( (String) event.getCommand() ) {
      case Constants.EVENT_LOGGING_MAFIOSI:
        this.loggingMafiosi();
        break;
      case Constants.TAG_MAFIA_LOGGING_TIME_UNIT:
        if ( change != null ) {
          this.loggingTimeUnit = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_MAFIA_DEMAND_PDF:
        if ( change != null ) {
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setDemandPDF( change.getValue() );
          }
        }
        break;
      case Constants.TAG_MAFIA_DEMAND_AFFILIATED_PROBABILITY:
        if ( change != null ) {
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setDemandAffiliatedProbability(
                Double.valueOf( change.getValue() ) );
          }
        }
        break;
      case Constants.TAG_MAFIA_EXTORTION_LEVEL:
        if ( change != null ) {
          this.extortionLevel = Double.valueOf( change.getValue() );
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setExtortionLevel( this.extortionLevel );
          }
        }
        break;
      case Constants.TAG_MAFIA_PUNISHMENT_SEVERITY:
        if ( change != null ) {
          this.punishmentSeverity = Double.valueOf( change.getValue() );
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setPunishmentSeverity( this.punishmentSeverity );
          }
        }
        break;
      case Constants.TAG_MAFIA_COLLECTION_PDF:
        if ( change != null ) {
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setCollectionPDF( change.getValue() );
          }
        }
        break;
      case Constants.TAG_MAFIA_PUNISHMENT_PROBABILITY:
        if ( change != null ) {
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso.setPunishmentProbability(
                Double.valueOf( change.getValue() ) );
          }
        }
        break;
      case Constants.TAG_MAFIA_MINIMUM_BENEFIT:
        if ( change != null ) {
          this.minBenefit = Double.valueOf( change.getValue() );
          this.setupBenefits();
        }
        break;
      case Constants.TAG_MAFIA_MAXIMUM_BENEFIT:
        if ( change != null ) {
          this.maxBenefit = Double.valueOf( change.getValue() );
          this.setupBenefits();
        }
        break;
      case Constants.TAG_MAFIA_PENTITI_PROBABILITY:
        if ( change != null ) {
          for ( MafiosoAgent mafioso : this.mafiosi.values() ) {
            mafioso
                .setPentitiProbability( Double.valueOf( change.getValue() ) );
          }
        }
        break;
      case Constants.TAG_MAFIA_RECRUITING_THRESHOLD:
        if ( change != null ) {
          this.recruitingThreshold = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_MAFIA_RECRUITING_PROBABILITY:
        if ( change != null ) {
          this.recruitingProbability = Double.valueOf( change.getValue() );
        }
        break;
    }
  }
}