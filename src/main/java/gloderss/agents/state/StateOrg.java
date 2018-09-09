package gloderss.agents.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gloderss.Constants;
import gloderss.Constants.Norms;
import gloderss.actions.CaptureMafiosoAction;
import gloderss.actions.CollaborateAction;
import gloderss.actions.CollaborationRequestAction;
import gloderss.actions.CustodyAction;
import gloderss.actions.DenounceExtortionAction;
import gloderss.actions.DenounceExtortionAffiliatedAction;
import gloderss.actions.DenouncePunishmentAction;
import gloderss.actions.DenouncePunishmentAffiliatedAction;
import gloderss.actions.ImprisonmentAction;
import gloderss.actions.NormInvocationAction;
import gloderss.actions.NotCollaborateAction;
import gloderss.actions.PentitoAction;
import gloderss.actions.ReleaseCustodyAction;
import gloderss.actions.ReleaseImprisonmentAction;
import gloderss.actions.ReleaseInvestigationAction;
import gloderss.actions.SpecificInvestigationAction;
import gloderss.actions.StateCompensationAction;
import gloderss.actions.StatePunishmentAction;
import gloderss.agents.AbstractAgent;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.communication.InfoAbstract;
import gloderss.communication.InfoRequest;
import gloderss.communication.InfoSet;
import gloderss.communication.Message;
import gloderss.conf.ChangeConf;
import gloderss.conf.StateConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.output.AbstractEntity;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.CompensationOutputEntity;
import gloderss.output.ExtortionOutputEntity;
import gloderss.output.InvestigationOutputEntity;
import gloderss.output.NormativeOutputEntity;
import gloderss.output.OutputController;
import gloderss.output.StateOutputEntity;
import gloderss.util.distribution.PDFAbstract;
import gloderss.util.random.RandomUtil;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class StateOrg extends AbstractAgent implements IStateOrg {

  private final static Logger              logger        = LoggerFactory
      .getLogger( StateOrg.class );

  private static final String              COLLABORATION = "COLLABORATION";

  private int                              loggingTimeUnit;

  private int                              ioId;

  private List<ChangeConf>                 changesConf;

  private double                           specificInvestigationProbability;

  private double                           evidenceProbability;

  private double                           convictionProbability;

  private double                           noCollaborationPunishmentProbability;

  private double                           noCollaborationPunishment;

  private double                           resourceFondo;

  private double                           proportionTransferFondo;

  private PDFAbstract                      custodyDurationPDF;

  private PDFAbstract                      imprisonmentDurationPDF;

  private PDFAbstract                      timeToCompensationPDF;

  private PDFAbstract                      periodicityFondoPDF;

  private String                           collaborationConvictionFunction;

  private String                           spreadInfoFunctionStr;

  private Evaluator                        spreadInfoFunction;

  private String                           proportionConsumersStr;

  private Evaluator                        proportionConsumers;

  private String                           proportionEntrepreneursStr;

  private Evaluator                        proportionEntrepreneurs;

  private double                           fondoSolidarieta;

  private Map<Integer, PoliceOfficerAgent> policeOfficers;

  private Map<Integer, ConsumerAgent>      consumers;

  private Map<Integer, EntrepreneurAgent>  entrepreneurs;

  private List<Integer>                    mafiosiBlackList;

  private List<Integer>                    investigateEntrepreneurs;

  private List<Integer>                    allocatedPoliceOfficers;

  private Queue<CaptureMafiosoAction>      custodyQueue;

  private Queue<ImprisonmentAction>        prisonQueue;

  private Queue<Object>                    assistQueue;

  private Map<Integer, Integer>            collaborations;

  private int                              numRequestedSpecInv;

  private int                              numConductedSpecInv;


  /**
   * State constructor
   *
   * @param id
   *                        State identification
   * @param conf
   *                        State configuration
   * @param consumers
   *                        List of all consumers
   * @param entrepreneurs
   *                        List of all entrepreneurs
   * @return none
   */
  public StateOrg( Integer id, EventSimulator simulator, StateConf conf, Map<Integer, ConsumerAgent> consumers, Map<Integer, EntrepreneurAgent> entrepreneurs ) {
    super( id, simulator );

    this.loggingTimeUnit = conf.getLoggingTimeUnit();

    this.changesConf = conf.getChangesConf();

    this.specificInvestigationProbability = conf
        .getSpecificInvestigationProbability();

    this.evidenceProbability = conf.getEvidenceProbability();

    this.convictionProbability = conf.getConvictionProbability();

    this.noCollaborationPunishmentProbability = conf
        .getNoCollaborationPunishmentProbability();

    this.noCollaborationPunishment = conf.getNoCollaborationPunishment();

    this.resourceFondo = conf.getResourceFondo();

    this.proportionTransferFondo = conf.getProportionTransferFondo();

    this.custodyDurationPDF = PDFAbstract
        .getInstance( conf.getCustodyDurationPDF() );

    this.imprisonmentDurationPDF = PDFAbstract
        .getInstance( conf.getImprisonmentDurationPDF() );

    this.timeToCompensationPDF = PDFAbstract
        .getInstance( conf.getTimeToCompensationPDF() );

    this.periodicityFondoPDF = PDFAbstract
        .getInstance( conf.getPeriodicityFondoPDF() );

    this.collaborationConvictionFunction = conf
        .getCollaborationConvictionFunction();

    this.spreadInfoFunctionStr = conf.getSpreadInfoFunction();

    this.spreadInfoFunction = new Evaluator();

    this.proportionConsumersStr = conf.getProportionConsumers();

    this.proportionConsumers = new Evaluator();

    this.proportionEntrepreneursStr = conf.getProportionEntrepreneurs();

    this.proportionEntrepreneurs = new Evaluator();

    this.fondoSolidarieta = 0.0;

    this.consumers = consumers;

    // Make Entrepreneur recognize the State identification
    this.entrepreneurs = entrepreneurs;
    for ( Integer entrepreneurId : this.entrepreneurs.keySet() ) {
      InfoSet infoSet = new InfoSet( id, entrepreneurId,
          Constants.PARAMETER_STATE_ID, id );
      this.sendInfo( infoSet );

      infoSet = new InfoSet( id, entrepreneurId,
          Constants.PARAMETER_STATE_PUNISHMENT,
          conf.getNoCollaborationPunishment() );
      this.sendInfo( infoSet );
    }

    PoliceOfficerAgent police;
    int policeId = id + 1;
    this.policeOfficers = new HashMap<Integer, PoliceOfficerAgent>();
    for ( int i = 0; i < conf.getNumberPoliceOfficers(); i++, policeId++ ) {
      police = new PoliceOfficerAgent( policeId, simulator, conf, this.id );
      this.policeOfficers.put( policeId, police );
    }

    this.mafiosiBlackList = new ArrayList<Integer>();
    this.investigateEntrepreneurs = new ArrayList<Integer>();
    this.allocatedPoliceOfficers = new ArrayList<Integer>();
    this.custodyQueue = new LinkedList<CaptureMafiosoAction>();
    this.prisonQueue = new LinkedList<ImprisonmentAction>();
    this.assistQueue = new LinkedList<Object>();
    this.collaborations = new HashMap<Integer, Integer>();

    this.numRequestedSpecInv = 0;
    this.numConductedSpecInv = 0;
  }


  /*******************************
   *
   * Getters and Setters
   *
   *******************************/

  public int getIOId() {
    return this.ioId;
  }


  public void setIOId( int ioId ) {
    this.ioId = ioId;
  }


  public Map<Integer, PoliceOfficerAgent> getPoliceOfficers() {
    return this.policeOfficers;
  }


  /*******************************
   *
   * Decision Processes
   *
   *******************************/

  @Override
  public void initializeSim() {
    for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
      police.initializeSim();
    }

    Event event = new Event( this.simulator.now(), this,
        Constants.EVENT_RESOURCE_FONDO );
    this.simulator.insert( event );

    event = new Event( this.simulator.now(), this,
        Constants.EVENT_LOGGING_INVESTIGATION );
    this.simulator.insert( event );

    this.spreadInfoFunction.clearVariables();
    double nextSpreadInfo = 0.0;
    try {
      nextSpreadInfo = this.spreadInfoFunction
          .getNumberResult( this.spreadInfoFunctionStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    event = new Event( this.simulator.now() + nextSpreadInfo, this,
        Constants.EVENT_SPREAD_INFORMATION );
    this.simulator.insert( event );

    // Schedule changes
    for ( ChangeConf change : this.changesConf ) {
      event = new Event( change.getTime(), this, change.getParameter(),
          change );
      this.simulator.insert( event );
    }
  }


  @Override
  public void decideInvestigateExtortion( DenounceExtortionAction action ) {

    int extortionId = (int) action
        .getParam( DenounceExtortionAction.Param.EXTORTION_ID );

    int entrepreneurId = (int) action
        .getParam( DenounceExtortionAction.Param.ENTREPRENEUR_ID );

    AbstractEntity extortionOutputEntity = OutputController.getInstance()
        .getEntity( EntityType.EXTORTION, extortionId );

    this.numRequestedSpecInv += 1;

    if ( (!this.investigateEntrepreneurs.contains( entrepreneurId ))
        && (this.policeOfficers.size() > 0)
        && (RandomUtil.nextDouble() < this.specificInvestigationProbability) ) {

      int mafiosoId = (int) action
          .getParam( DenounceExtortionAction.Param.MAFIOSO_ID );

      if ( !this.mafiosiBlackList.contains( mafiosoId ) ) {
        this.mafiosiBlackList.add( mafiosoId );

        for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
          InfoSet set = new InfoSet( this.id, police.getId(),
              Constants.PARAMETER_ADD_MAFIOSO, mafiosoId );
          this.sendInfo( set );
        }
      }

      int policeOfficerId;
      do {

        policeOfficerId = (int) this.policeOfficers.keySet()
            .toArray()[RandomUtil.nextIntFromTo( 0,
                (this.policeOfficers.size() - 1) )];

      } while ( this.allocatedPoliceOfficers.contains( policeOfficerId ) );

      SpecificInvestigationAction investigation = new SpecificInvestigationAction(
          extortionId, policeOfficerId, entrepreneurId );

      Message msg = new Message( this.simulator.now(), this.id, policeOfficerId,
          investigation );
      this.sendMsg( msg );

      this.numConductedSpecInv += 1;

      // Output
      extortionOutputEntity.setValue(
          ExtortionOutputEntity.Field.INVESTIGATED_EXTORTION.name(), true );

    } else {

      // Output
      extortionOutputEntity.setValue(
          ExtortionOutputEntity.Field.INVESTIGATED_EXTORTION.name(), false );
      extortionOutputEntity.setActive();

    }
  }


  @Override
  public void decideInvestigateExtortionAffiliated(
      DenounceExtortionAffiliatedAction action ) {

    int extortionId = (int) action
        .getParam( DenounceExtortionAffiliatedAction.Param.EXTORTION_ID );

    int entrepreneurId = (int) action
        .getParam( DenounceExtortionAffiliatedAction.Param.ENTREPRENEUR_ID );

    AbstractEntity extortionOutputEntity = OutputController.getInstance()
        .getEntity( EntityType.EXTORTION, extortionId );

    this.numRequestedSpecInv += 1;

    if ( (!this.investigateEntrepreneurs.contains( entrepreneurId ))
        && (this.policeOfficers.size() > 0)
        && (RandomUtil.nextDouble() < this.specificInvestigationProbability) ) {

      int mafiosoId = (int) action
          .getParam( DenounceExtortionAffiliatedAction.Param.MAFIOSO_ID );

      if ( !this.mafiosiBlackList.contains( mafiosoId ) ) {
        this.mafiosiBlackList.add( mafiosoId );

        for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
          InfoSet set = new InfoSet( this.id, police.getId(),
              Constants.PARAMETER_ADD_MAFIOSO, mafiosoId );
          this.sendInfo( set );
        }
      }

      int policeOfficerId;
      do {

        policeOfficerId = (int) this.policeOfficers.keySet()
            .toArray()[RandomUtil.nextIntFromTo( 0,
                (this.policeOfficers.size() - 1) )];

      } while ( this.allocatedPoliceOfficers.contains( policeOfficerId ) );

      SpecificInvestigationAction investigation = new SpecificInvestigationAction(
          extortionId, policeOfficerId, entrepreneurId );

      Message msg = new Message( this.simulator.now(), this.id, policeOfficerId,
          investigation );
      this.sendMsg( msg );

      this.numConductedSpecInv += 1;

      // Output
      extortionOutputEntity.setValue(
          ExtortionOutputEntity.Field.INVESTIGATED_EXTORTION.name(), true );

    } else {

      // Output
      extortionOutputEntity.setValue(
          ExtortionOutputEntity.Field.INVESTIGATED_EXTORTION.name(), false );
      extortionOutputEntity.setActive();
    }
  }


  @Override
  public void decideInvestigatePunishment( DenouncePunishmentAction action ) {

    int extortionId = (int) action
        .getParam( DenouncePunishmentAction.Param.EXTORTION_ID );

    int entrepreneurId = (int) action
        .getParam( DenouncePunishmentAction.Param.ENTREPRENEUR_ID );

    int mafiosoId = (int) action
        .getParam( DenouncePunishmentAction.Param.MAFIOSO_ID );

    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( EntityType.EXTORTION, extortionId );

    this.numRequestedSpecInv += 1;

    if ( (!this.investigateEntrepreneurs.contains( entrepreneurId ))
        && (this.policeOfficers.size() > 0) ) {

      if ( !this.mafiosiBlackList.contains( mafiosoId ) ) {
        this.mafiosiBlackList.add( mafiosoId );

        for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
          InfoSet set = new InfoSet( this.id, police.getId(),
              Constants.PARAMETER_ADD_MAFIOSO, mafiosoId );
          this.sendInfo( set );
        }
      }

      int policeOfficerId;
      do {

        policeOfficerId = (int) this.policeOfficers.keySet()
            .toArray()[RandomUtil.nextIntFromTo( 0,
                (this.policeOfficers.size() - 1) )];

      } while ( this.allocatedPoliceOfficers.contains( policeOfficerId ) );

      SpecificInvestigationAction investigation = new SpecificInvestigationAction(
          extortionId, policeOfficerId, entrepreneurId );

      Message msg = new Message( this.simulator.now(), this.id, policeOfficerId,
          investigation );
      this.sendMsg( msg );

      this.numConductedSpecInv += 1;
    }

    // Add Entrepreneur in a queue to receive compensation for the
    // punishment
    this.assistQueue.add( action );

    // Spread action Denounce Punishment
    Message msg = new Message( this.simulator.now(), entrepreneurId, this.id,
        action );
    this.spreadActionInformation( msg );

    // Output
    outputEntity.setValue(
        ExtortionOutputEntity.Field.INVESTIGATED_PUNISHMENT.name(), true );

    Event event = new Event(
        this.simulator.now() + this.timeToCompensationPDF.nextValue(), this,
        Constants.EVENT_ASSIST_ENTREPRENEUR );
    this.simulator.insert( event );
  }


  @Override
  public void decideInvestigatePunishmentAffiliated(
      DenouncePunishmentAffiliatedAction action ) {

    int extortionId = (int) action
        .getParam( DenouncePunishmentAffiliatedAction.Param.EXTORTION_ID );

    int entrepreneurId = (int) action
        .getParam( DenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID );

    int mafiosoId = (int) action
        .getParam( DenouncePunishmentAffiliatedAction.Param.MAFIOSO_ID );

    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( EntityType.EXTORTION, extortionId );

    this.numRequestedSpecInv += 1;

    if ( (!this.investigateEntrepreneurs.contains( entrepreneurId ))
        && (this.policeOfficers.size() > 0) ) {

      if ( !this.mafiosiBlackList.contains( mafiosoId ) ) {
        this.mafiosiBlackList.add( mafiosoId );

        for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
          InfoSet set = new InfoSet( this.id, police.getId(),
              Constants.PARAMETER_ADD_MAFIOSO, mafiosoId );
          this.sendInfo( set );
        }
      }

      int policeOfficerId;
      do {

        policeOfficerId = (int) this.policeOfficers.keySet()
            .toArray()[RandomUtil.nextIntFromTo( 0,
                (this.policeOfficers.size() - 1) )];

      } while ( this.allocatedPoliceOfficers.contains( policeOfficerId ) );

      SpecificInvestigationAction investigation = new SpecificInvestigationAction(
          extortionId, policeOfficerId, entrepreneurId );

      Message msg = new Message( this.simulator.now(), this.id, policeOfficerId,
          investigation );
      this.sendMsg( msg );

      this.numConductedSpecInv += 1;
    }

    // Add Entrepreneur in a queue to receive compensation for the
    // punishment
    this.assistQueue.add( action );

    // Spread action Denounce Punishment
    Message msg = new Message( this.simulator.now(), entrepreneurId, this.id,
        action );
    this.spreadActionInformation( msg );

    // Output
    outputEntity.setValue(
        ExtortionOutputEntity.Field.INVESTIGATED_PUNISHMENT.name(), true );

    Event event = new Event(
        this.simulator.now() + this.timeToCompensationPDF.nextValue(), this,
        Constants.EVENT_ASSIST_ENTREPRENEUR );
    this.simulator.insert( event );
  }


  @Override
  public void releaseInvestigation( ReleaseInvestigationAction action ) {

    int policeOfficerId = (int) action
        .getParam( ReleaseInvestigationAction.Param.POLICE_OFFICER_ID );

    if ( this.allocatedPoliceOfficers.contains( policeOfficerId ) ) {
      this.allocatedPoliceOfficers.remove( policeOfficerId );
    }
  }


  @Override
  public void decideCustody( CaptureMafiosoAction action ) {

    int extortionId = (int) action
        .getParam( CaptureMafiosoAction.Param.EXTORTION_ID );

    int mafiosoId = (int) action
        .getParam( CaptureMafiosoAction.Param.MAFIOSO_ID );

    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( EntityType.EXTORTION, extortionId );

    CustodyAction custody = new CustodyAction( extortionId, this.id, mafiosoId,
        this.custodyDurationPDF.nextValue() );

    Message msg = new Message( this.simulator.now(), this.id, mafiosoId,
        custody );
    this.sendMsg( msg );

    // Spread action
    this.spreadActionInformation( msg );

    // Collaboration evidence collection
    if ( RandomUtil.nextDouble() < this.evidenceProbability ) {

      InfoRequest info = new InfoRequest( this.id, mafiosoId,
          Constants.REQUEST_COLLECT_PAYERS );

      @SuppressWarnings ( "unchecked" )
      List<Integer> evidences = (List<Integer>) this.sendInfo( info );
      if ( !evidences.isEmpty() ) {
        for ( Integer entrepreneurId : evidences ) {
          CollaborationRequestAction collaboration = new CollaborationRequestAction(
              mafiosoId, entrepreneurId );

          msg = new Message( this.simulator.now(), this.id, entrepreneurId,
              collaboration );
          this.sendMsg( msg );
        }
      }

      outputEntity.setValue(
          ExtortionOutputEntity.Field.EVIDENCE_COLLECTED.name(),
          evidences.size() );
    }

    // Output
    if ( extortionId >= 0 ) {
      outputEntity.setValue( ExtortionOutputEntity.Field.MAFIOSO_CUSTODY.name(),
          true );
    }

    Event event = new Event(
        this.simulator.now() + this.custodyDurationPDF.nextValue(), this,
        Constants.EVENT_RELEASE_CUSTODY );
    this.simulator.insert( event );

    this.custodyQueue.offer( action );
  }


  @Override
  public void decideConviction( CaptureMafiosoAction action ) {

    int mafiosoId = (int) action
        .getParam( CaptureMafiosoAction.Param.MAFIOSO_ID );

    int extortionId = (int) action
        .getParam( CaptureMafiosoAction.Param.EXTORTION_ID );

    AbstractEntity outputEntity = null;
    if ( extortionId >= 0 ) {
      outputEntity = OutputController.getInstance()
          .getEntity( EntityType.EXTORTION, extortionId );
    }

    // Calculate conviction probability
    double convictionProb = this.convictionProbability;
    if ( this.collaborations.containsKey( mafiosoId ) ) {
      int numCollaborations = this.collaborations.get( mafiosoId );

      Evaluator eval = new Evaluator();
      eval.putVariable( COLLABORATION,
          Integer.valueOf( numCollaborations ).toString() );

      try {
        convictionProb += eval
            .getNumberResult( this.collaborationConvictionFunction );
      } catch ( EvaluationException e ) {
        logger.debug( e.toString() );
      }
    }

    // Decide whether the Mafioso is going to be imprisoned
    if ( RandomUtil.nextDouble() < Math.max( 0,
        Math.min( 1, convictionProb ) ) ) {

      InfoRequest wealthRequest = new InfoRequest( this.id, mafiosoId,
          Constants.REQUEST_WEALTH );
      double wealth = (double) this.sendInfo( wealthRequest );

      this.fondoSolidarieta += wealth * this.proportionTransferFondo;

      InfoSet wealthSet = new InfoSet( this.id, mafiosoId,
          Constants.PARAMETER_WEALTH, 0.0 );
      this.sendInfo( wealthSet );

      double duration = this.imprisonmentDurationPDF.nextValue();

      ImprisonmentAction imprisonment = new ImprisonmentAction( mafiosoId,
          duration );

      Message msg = new Message( this.simulator.now(), this.id, mafiosoId,
          imprisonment );
      this.sendMsg( msg );

      this.prisonQueue.add( imprisonment );

      Event releasePrison = new Event( this.simulator.now() + duration, this,
          Constants.EVENT_RELEASE_PRISON );
      this.simulator.insert( releasePrison );

      // Spread action
      this.spreadActionInformation( msg );

      // Output
      if ( extortionId >= 0 ) {
        outputEntity.setValue(
            ExtortionOutputEntity.Field.MAFIOSO_CONVICTED.name(), true );
        outputEntity.setActive();
      }

      // Release the Mafioso of custody
    } else {

      ReleaseCustodyAction releaseCustody = new ReleaseCustodyAction(
          mafiosoId );

      Message msg = new Message( this.simulator.now(), this.id, mafiosoId,
          releaseCustody );
      this.sendMsg( msg );

      // Spread action
      this.spreadActionInformation( msg );

      // Output
      if ( extortionId >= 0 ) {
        outputEntity.setValue(
            ExtortionOutputEntity.Field.MAFIOSO_CONVICTED.name(), false );
        outputEntity.setActive();
      }
    }
  }


  @Override
  public void receivePentito( PentitoAction action ) {

    @SuppressWarnings ( "unchecked" )
    List<Integer> mafiosoList = (List<Integer>) action
        .getParam( PentitoAction.Param.MAFIOSI_LIST );

    if ( mafiosoList != null ) {
      for ( Integer mafiosoId : mafiosoList ) {
        if ( !this.mafiosiBlackList.contains( mafiosoId ) ) {
          this.mafiosiBlackList.add( mafiosoId );
        }
      }
    }

    int mafiosoId = (int) action.getParam( PentitoAction.Param.MAFIOSO_ID );

    @SuppressWarnings ( "unchecked" )
    List<Integer> entrepreneurList = (List<Integer>) action
        .getParam( PentitoAction.Param.ENTREPRENEUR_LIST );

    if ( entrepreneurList != null ) {
      for ( Integer entrepreneurId : entrepreneurList ) {
        CollaborationRequestAction collaboration = new CollaborationRequestAction(
            mafiosoId, entrepreneurId );

        Message msg = new Message( this.simulator.now(), this.id,
            entrepreneurId, collaboration );
        this.sendMsg( msg );
      }
    }

    // Spread action
    Message msg = new Message( this.simulator.now(), mafiosoId, this.id,
        action );
    this.spreadActionInformation( msg );
  }


  @Override
  public void receiveCollaboration( CollaborateAction action ) {
    int mafiosoId = (int) action.getParam( CollaborateAction.Param.MAFIOSO_ID );

    int numCollaborations = 0;
    if ( this.collaborations.containsKey( mafiosoId ) ) {
      numCollaborations = this.collaborations.get( mafiosoId );
    }
    numCollaborations++;
    this.collaborations.put( mafiosoId, numCollaborations );
  }


  @Override
  public void decideStatePunishment( NotCollaborateAction action ) {

    if ( RandomUtil.nextDouble() < this.noCollaborationPunishmentProbability ) {
      int entrepreneurId = (int) action
          .getParam( NotCollaborateAction.Param.ENTREPRENEUR_ID );

      if ( this.assistQueue.contains( entrepreneurId ) ) {
        this.assistQueue.remove( entrepreneurId );
      }

      StatePunishmentAction punishment = new StatePunishmentAction( this.id,
          entrepreneurId, this.noCollaborationPunishment );

      Message msg = new Message( this.simulator.now(), this.id, entrepreneurId,
          punishment );
      this.sendMsg( msg );

      // Spread action
      this.spreadActionInformation( msg );
    }
  }


  @Override
  public void decideStateCompensation() {

    if ( !this.assistQueue.isEmpty() ) {
      Object obj = this.assistQueue.poll();

      int extortionId = -1;
      int entrepreneurId = -1;
      double punishment = -1;
      if ( obj instanceof DenouncePunishmentAction ) {
        DenouncePunishmentAction action = (DenouncePunishmentAction) obj;

        extortionId = (int) action
            .getParam( DenouncePunishmentAction.Param.EXTORTION_ID );

        entrepreneurId = (int) action
            .getParam( DenouncePunishmentAction.Param.ENTREPRENEUR_ID );

        punishment = (double) action
            .getParam( DenouncePunishmentAction.Param.PUNISHMENT );

      } else if ( obj instanceof DenouncePunishmentAffiliatedAction ) {
        DenouncePunishmentAffiliatedAction action = (DenouncePunishmentAffiliatedAction) obj;

        extortionId = (int) action
            .getParam( DenouncePunishmentAffiliatedAction.Param.EXTORTION_ID );

        entrepreneurId = (int) action.getParam(
            DenouncePunishmentAffiliatedAction.Param.ENTREPRENEUR_ID );

        punishment = (double) action
            .getParam( DenouncePunishmentAffiliatedAction.Param.PUNISHMENT );
      }

      // State has enough money to compensate the Entrepreneur
      if ( this.fondoSolidarieta >= punishment ) {

        this.fondoSolidarieta -= punishment;

        StateCompensationAction compensation = new StateCompensationAction(
            extortionId, this.id, entrepreneurId, punishment );

        Message msg = new Message( this.simulator.now(), this.id,
            entrepreneurId, compensation );
        this.sendMsg( msg );

        // Spread action
        this.spreadActionInformation( msg );

        // Output
        AbstractEntity outputEntity = OutputController.getInstance()
            .getEntity( EntityType.COMPENSATION, extortionId );
        outputEntity.setValue(
            CompensationOutputEntity.Field.STATE_COMPENSATED.name(), true );
        outputEntity.setValue(
            CompensationOutputEntity.Field.STATE_TIME_COMPENSATION.name(),
            this.simulator.now() );
        outputEntity.setValue(
            CompensationOutputEntity.Field.STATE_COMPENSATION.name(),
            punishment );

        // Entrepreneur goes back to the queue
      } else {

        this.assistQueue.add( obj );

        Event event = new Event(
            this.simulator.now() + this.timeToCompensationPDF.nextValue(), this,
            Constants.EVENT_ASSIST_ENTREPRENEUR );
        this.simulator.insert( event );

      }
    }
  }


  @Override
  public void spreadNormativeInformation() {

    // Spread information to Consumers
    NormInvocationAction notBuyPayExtortion = new NormInvocationAction( this.id,
        Norms.BUY_FROM_NOT_PAYING_ENTREPRENEURS.name() );

    this.proportionConsumers.clearVariables();

    double propConsumers = 0.0;
    try {
      propConsumers = this.proportionConsumers
          .getNumberResult( this.proportionConsumersStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    propConsumers = Math.max( 0, Math.min( 1.0, propConsumers ) );

    int numConsumers = (int) (this.consumers.size() * propConsumers);
    List<Integer> consumerIds = new ArrayList<Integer>();
    while ( consumerIds.size() < numConsumers ) {

      int consumerId = (int) this.consumers.keySet().toArray()[RandomUtil
          .nextIntFromTo( 0, (this.consumers.size() - 1) )];

      if ( !consumerIds.contains( consumerId ) ) {
        consumerIds.add( consumerId );

        Message msg = new Message( this.simulator.now(), this.id, consumerId,
            notBuyPayExtortion );
        this.sendMsg( msg );
      }
    }

    NormInvocationAction notPayExtortion = new NormInvocationAction( this.id,
        Norms.NOT_PAY_EXTORTION.name() );

    NormInvocationAction denounceExtortion = new NormInvocationAction( this.id,
        Norms.DENOUNCE.name() );

    this.proportionEntrepreneurs.clearVariables();

    double propEntrepreneurs = 0.0;
    try {
      propEntrepreneurs = this.proportionEntrepreneurs
          .getNumberResult( this.proportionEntrepreneursStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    propEntrepreneurs = Math.max( 0, Math.min( 1.0, propEntrepreneurs ) );

    int numEntrepreneurs = (int) (this.entrepreneurs.size()
        * propEntrepreneurs);
    List<Integer> entrepreneurIds = new ArrayList<Integer>();
    int qtyEntrepreneurs = this.entrepreneurs.size();
    while ( entrepreneurIds.size() < numEntrepreneurs ) {

      int entrepreneurId = (int) this.entrepreneurs.keySet()
          .toArray()[RandomUtil.nextIntFromTo( 0, (qtyEntrepreneurs - 1) )];

      if ( !entrepreneurIds.contains( entrepreneurId ) ) {
        entrepreneurIds.add( entrepreneurId );

        Message msgNP = new Message( this.simulator.now(), this.id,
            entrepreneurId, notPayExtortion );
        this.sendMsg( msgNP );

        Message msgD = new Message( this.simulator.now(), this.id,
            entrepreneurId, denounceExtortion );
        this.sendMsg( msgD );
      }
    }

    // Output
    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( EntityType.NORMATIVE );
    outputEntity.setValue( NormativeOutputEntity.Field.TIME.name(),
        this.simulator.now() );
    outputEntity.setValue( NormativeOutputEntity.Field.AGENT_ID.name(),
        this.id );
    outputEntity.setValue( NormativeOutputEntity.Field.NUMBER_CONSUMERS.name(),
        numConsumers );
    outputEntity.setValue(
        NormativeOutputEntity.Field.NUMBER_ENTREPRENEURS.name(),
        numEntrepreneurs );
    outputEntity.setActive();

    this.spreadInfoFunction.clearVariables();
    double nextSpreadInfo = 0.0;
    try {
      nextSpreadInfo = this.spreadInfoFunction
          .getNumberResult( this.spreadInfoFunctionStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    Event event = new Event( this.simulator.now() + nextSpreadInfo, this,
        Constants.EVENT_SPREAD_INFORMATION );
    this.simulator.insert( event );
  }


  /**
   * Selects an Entrepreneur for investigation
   *
   * @param none
   * @return Entrepreneur for investigation
   */
  private int decideEntrepreneur() {

    int entrepreneurId;
    do {

      entrepreneurId = (int) this.entrepreneurs.keySet().toArray()[RandomUtil
          .nextIntFromTo( 0, (this.entrepreneurs.size() - 1) )];

    } while ( this.investigateEntrepreneurs.contains( entrepreneurId ) );

    return entrepreneurId;
  }


  /**
   * Increase the Fondo di Solidarieta by a constant amount
   *
   * @param none
   * @return none
   */
  private void increaseFondo() {

    this.fondoSolidarieta += this.resourceFondo;

    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( AbstractEntity.EntityType.STATE );
    outputEntity.setValue( StateOutputEntity.Field.TIME.name(),
        this.simulator.now() );
    outputEntity.setValue( StateOutputEntity.Field.FONDO.name(),
        this.fondoSolidarieta );
    outputEntity.setActive();

    Event event = new Event(
        this.simulator.now() + this.periodicityFondoPDF.nextValue(), this,
        Constants.EVENT_RESOURCE_FONDO );
    this.simulator.insert( event );
  }


  /**
   * Judge whether a captured Mafioso should be imprisoned
   *
   * @param none
   * @return none
   */
  private void judgeMafioso() {

    if ( !this.custodyQueue.isEmpty() ) {
      CaptureMafiosoAction action = this.custodyQueue.poll();
      this.decideConviction( action );
    }
  }


  /**
   * Release a imprisoned Mafioso
   *
   * @param none
   * @return none
   */
  private void releaseMafioso() {

    if ( !this.prisonQueue.isEmpty() ) {
      ImprisonmentAction prison = this.prisonQueue.poll();

      int mafiosoId = (int) prison
          .getParam( ImprisonmentAction.Param.MAFIOSO_ID );

      ReleaseImprisonmentAction action = new ReleaseImprisonmentAction(
          mafiosoId );

      Message msg = new Message( this.simulator.now(), this.id, mafiosoId,
          action );
      this.sendMsg( msg );

      // Spread action
      this.spreadActionInformation( msg );
    }
  }


  /**
   * Spread action information
   *
   * @param msg
   *              Spread action message to a proportion of consumers and
   *              entrepreneurs, and also to the Intermediary Organization
   * @return none
   */
  private void spreadActionInformation( Message msg ) {

    this.proportionConsumers.clearVariables();

    double propConsumers = 0.0;
    try {
      propConsumers = this.proportionConsumers
          .getNumberResult( this.proportionConsumersStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    propConsumers = Math.max( 0, Math.min( 1.0, propConsumers ) );

    // Spread to a proportion of Consumers
    int numConsumers = (int) (this.consumers.size() * propConsumers);
    List<Integer> consumerIds = new ArrayList<Integer>();
    while ( consumerIds.size() < numConsumers ) {

      int consumerId = (int) this.consumers.keySet().toArray()[RandomUtil
          .nextIntFromTo( 0, (this.consumers.size() - 1) )];

      if ( !consumerIds.contains( consumerId ) ) {
        consumerIds.add( consumerId );

        Message newMsg = new Message( this.simulator.now(), this.id, consumerId,
            msg );
        this.sendMsg( newMsg );
      }
    }

    this.proportionEntrepreneurs.clearVariables();

    double propEntrepreneurs = 0.0;
    try {
      propEntrepreneurs = this.proportionEntrepreneurs
          .getNumberResult( this.proportionEntrepreneursStr );
    } catch ( EvaluationException e ) {
      logger.debug( e.toString() );
    }

    propEntrepreneurs = Math.max( 0, Math.min( 1.0, propEntrepreneurs ) );

    // Spread to a proportion of Entrepreneurs
    int numEntrepreneurs = (int) (this.entrepreneurs.size()
        * propEntrepreneurs);
    List<Integer> entrepreneurIds = new ArrayList<Integer>();
    int qtyEntrepreneurs = this.entrepreneurs.size();
    while ( entrepreneurIds.size() < numEntrepreneurs ) {

      int entrepreneurId = (int) this.entrepreneurs.keySet()
          .toArray()[RandomUtil.nextIntFromTo( 0, (qtyEntrepreneurs - 1) )];

      if ( !entrepreneurIds.contains( entrepreneurId ) ) {
        entrepreneurIds.add( entrepreneurId );

        Message newMsg = new Message( this.simulator.now(), this.id,
            entrepreneurId, msg );
        this.sendMsg( newMsg );
      }
    }

    // Send to the Intermediary Organization
    Message newMsg = new Message( this.simulator.now(), this.id, this.ioId,
        msg );
    this.sendMsg( newMsg );
  }


  public void loggingInvestigations() {
    int numConductedGenInv = 0;
    for ( PoliceOfficerAgent policeOfficer : policeOfficers.values() ) {
      numConductedGenInv += policeOfficer.getNumGeneralInv();
      policeOfficer.setNumGeneralInv( 0 );
    }

    AbstractEntity outputEntity = OutputController.getInstance()
        .getEntity( EntityType.INVESTIGATION );

    outputEntity.setValue( InvestigationOutputEntity.Field.TIME.name(),
        (int) this.simulator.now() );
    outputEntity.setValue(
        InvestigationOutputEntity.Field.CONDUCTED_GENERAL_INVESTIGATION.name(),
        numConductedGenInv );
    outputEntity.setValue(
        InvestigationOutputEntity.Field.REQUESTED_SPECIFIC_INVESTIGATION.name(),
        this.numRequestedSpecInv );
    outputEntity.setValue(
        InvestigationOutputEntity.Field.CONDUCTED_SPECIFIC_INVESTIGATION.name(),
        this.numConductedSpecInv );
    outputEntity.setActive();

    this.numRequestedSpecInv = 0;
    this.numConductedSpecInv = 0;

    Event event = new Event( this.simulator.now() + this.loggingTimeUnit, this,
        Constants.EVENT_LOGGING_INVESTIGATION );
    this.simulator.insert( event );
  }


  @Override
  public void finalizeSim() {
    this.loggingInvestigations();
  }


  /*******************************
   *
   * Handle communication requests
   *
   *******************************/

  @Override
  public synchronized void handleMessage( Message msg ) {

    Object content = msg.getContent();

    if ( (msg.getSender() != this.id)
        && (msg.getReceiver().contains( this.id )) ) {

      // Denounce extortion
      if ( content instanceof DenounceExtortionAction ) {
        this.decideInvestigateExtortion( (DenounceExtortionAction) content );

        // Denounce extortion Affiliated
      } else if ( content instanceof DenounceExtortionAffiliatedAction ) {
        this.decideInvestigateExtortionAffiliated(
            (DenounceExtortionAffiliatedAction) content );

        // Denounce punishment
      } else if ( content instanceof DenouncePunishmentAction ) {
        this.decideInvestigatePunishment( (DenouncePunishmentAction) content );

        // Denounce punishment Affiliated
      } else if ( content instanceof DenouncePunishmentAffiliatedAction ) {
        this.decideInvestigatePunishmentAffiliated(
            (DenouncePunishmentAffiliatedAction) content );

        // Pentito
      } else if ( content instanceof PentitoAction ) {
        this.receivePentito( (PentitoAction) content );

        // Release investigation
      } else if ( content instanceof ReleaseInvestigationAction ) {
        this.releaseInvestigation( (ReleaseInvestigationAction) content );

        // Capture Mafioso
      } else if ( content instanceof CaptureMafiosoAction ) {
        this.decideCustody( (CaptureMafiosoAction) content );

        // Collaboration
      } else if ( content instanceof CollaborateAction ) {
        this.receiveCollaboration( (CollaborateAction) content );

      }
    }
  }


  @Override
  public Object handleInfo( InfoAbstract info ) {
    Object infoRequested = null;

    if ( info.getType().equals( InfoAbstract.Type.REQUEST ) ) {

      InfoRequest request = (InfoRequest) info;
      switch ( request.getInfoRequest() ) {
        case Constants.REQUEST_ID:
          infoRequested = this.id;
          break;
        case Constants.REQUEST_ENTREPRENEUR_ID:
          infoRequested = this.decideEntrepreneur();
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
      case Constants.EVENT_RESOURCE_FONDO:
        this.increaseFondo();
        break;
      case Constants.EVENT_RELEASE_CUSTODY:
        this.judgeMafioso();
        break;
      case Constants.EVENT_RELEASE_PRISON:
        this.releaseMafioso();
        break;
      case Constants.EVENT_ASSIST_ENTREPRENEUR:
        this.decideStateCompensation();
        break;
      case Constants.EVENT_SPREAD_INFORMATION:
        this.spreadNormativeInformation();
        break;
      case Constants.EVENT_LOGGING_INVESTIGATION:
        this.loggingInvestigations();
        break;
      case Constants.TAG_STATE_GENERAL_INVESTIGATION_DURATION_PDF:
        if ( change != null ) {
          for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
            police
                .setGeneralInvestigationDuration( (String) change.getValue() );
          }
        }
        break;
      case Constants.TAG_STATE_BUREAUCRATIC_ACTIVITY_DURATION_PDF:
        if ( change != null ) {
          for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
            police
                .setBureaucraticActivityDuration( (String) change.getValue() );
          }
        }
        break;
      case Constants.TAG_STATE_SPECIFIC_INVESTIGATION_DURATION_PDF:
        if ( change != null ) {
          for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
            police
                .setSpecificInvestigationDuration( (String) change.getValue() );
          }
        }
        break;
      case Constants.TAG_STATE_SPECIFIC_INVESTIGATION_PROBABILITY:
        if ( change != null ) {
          this.specificInvestigationProbability = Double
              .valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_CAPTURE_PROBABILITY:
        if ( change != null ) {
          for ( PoliceOfficerAgent police : this.policeOfficers.values() ) {
            police.setCaptureProbability( Double.valueOf( change.getValue() ) );
          }
        }
        break;
      case Constants.TAG_STATE_EVIDENCE_PROBABILITY:
        if ( change != null ) {
          this.evidenceProbability = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_CUSTODY_DURATION_PDF:
        if ( change != null ) {
          this.custodyDurationPDF = PDFAbstract
              .getInstance( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_CONVICTION_PROBABILITY:
        if ( change != null ) {
          this.convictionProbability = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_COLLABORATION_CONVICTION_FUNCTION:
        if ( change != null ) {
          this.collaborationConvictionFunction = change.getValue();
        }
        break;
      case Constants.TAG_STATE_IMPRISONMENT_DURATION_PDF:
        if ( change != null ) {
          this.imprisonmentDurationPDF = PDFAbstract
              .getInstance( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_NO_COLLABORATION_PUNISHMENT_PROBABILITY:
        if ( change != null ) {
          this.noCollaborationPunishmentProbability = Double
              .valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_NO_COLLABORATION_PUNISHMENT:
        if ( change != null ) {
          this.noCollaborationPunishment = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_TIME_TO_COMPENSATION_PDF:
        if ( change != null ) {
          this.timeToCompensationPDF = PDFAbstract
              .getInstance( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_RESOURCE_FONDO:
        if ( change != null ) {
          this.resourceFondo = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_PERIODICITY_FONDO_PDF:
        if ( change != null ) {
          this.periodicityFondoPDF = PDFAbstract
              .getInstance( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_PROPORTION_TRANSFER_FONDO:
        if ( change != null ) {
          this.proportionTransferFondo = Double.valueOf( change.getValue() );
        }
        break;
      case Constants.TAG_STATE_SPREAD_INFO_FUNCTION:
        if ( change != null ) {
          this.spreadInfoFunctionStr = change.getValue();
        }
        break;
      case Constants.TAG_STATE_PROPORTION_CONSUMERS:
        if ( change != null ) {
          this.proportionConsumersStr = change.getValue();
        }
        break;
      case Constants.TAG_STATE_PROPORTION_ENTREPRENEURS:
        if ( change != null ) {
          this.proportionEntrepreneursStr = change.getValue();
        }
        break;
    }
  }
}