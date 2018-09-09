package gloderss;

public final class Constants {

  /**
   * General
   */

  public static final String ENCONDING                                         = "UTF-8";

  /**
   * Parameters
   */
  public static final String PARAM_XML_FILENAME                                = "xmlFilename";

  public static final String PARAM_XSD_FILENAME                                = "xsdFilename";

  /**
   * Simulation tags
   */
  public static final String TAG_NAMESPACE                                     = "palermo.conf";

  public static final String TAG_SCENARIO                                      = "scenario";

  /**
   * General tags
   */
  public static final String TAG_GENERAL                                       = "general";

  public static final String TAG_NUMBER_REPLICATIONS                           = "numberReplications";

  public static final String TAG_NUMBER_CYCLES                                 = "numberCycles";

  public static final String TAG_NETWORK_TOPOLOGY                              = "networkTopology";

  public static final String TAG_CITIZENS_DISTRIBUTION                         = "citizensDistribution";

  /**
   * Seed tags
   */
  public static final String TAG_SEEDS                                         = "seeds";

  public static final String TAG_SEED                                          = "seed";

  /**
   * Output tags
   */
  public static final String TAG_OUTPUT                                        = "output";

  public static final String TAG_OUTPUT_DIRECTORY                              = "directory";

  public static final String TAG_OUTPUT_APPEND                                 = "append";

  public static final String TAG_OUTPUT_SEPARATOR                              = "separator";

  public static final String TAG_OUTPUT_TIME_TO_WRITE                          = "timeToWrite";

  /**
   * Filename tags
   */
  public static final String TAG_FILENAME                                      = "filename";

  public static final String TAG_FILENAME_EXTORTION                            = "extortion";

  public static final String TAG_FILENAME_COMPENSATION                         = "compensation";

  public static final String TAG_FILENAME_PURCHASE                             = "purchase";

  public static final String TAG_FILENAME_NORMATIVE                            = "normative";

  public static final String TAG_FILENAME_ENTREPRENEUR                         = "entrepreneur";

  public static final String TAG_FILENAME_CONSUMER                             = "consumer";

  public static final String TAG_FILENAME_MAFIA                                = "mafia";

  public static final String TAG_FILENAME_MAFIOSI                              = "mafiosi";

  public static final String TAG_FILENAME_STATE                                = "state";

  public static final String TAG_FILENAME_INVESTIGATION                        = "investigation";

  public static final String TAG_FILENAME_INTERMEDIARY_ORGANIZATION            = "intermediaryOrganization";

  /**
   * Visibility tags
   */
  public static final String TAG_COMMUNICATION                                 = "communication";

  public static final String TAG_COMMUNICATION_TYPES                           = "types";

  public static final String TAG_COMMUNICATION_TYPE                            = "type";

  public static final String TAG_COMMUNICATION_TYPE_NAME                       = "name";

  public static final String TAG_COMMUNICATION_ACTIONS                         = "actions";

  public static final String TAG_COMMUNICATION_ACTION                          = "action";

  public static final String TAG_COMMUNICATION_ACTION_NAME                     = "name";

  /**
   * Consumers tags
   */
  public static final String TAG_CONSUMERS                                     = "consumers";

  public static final String TAG_CONSUMER                                      = "consumer";

  public static final String TAG_CONSUMER_NUMBER_CONSUMERS                     = "numberConsumers";

  public static final String TAG_CONSUMER_LOGGING_TIME_UNIT                    = "loggingTimeUnit";

  public static final String TAG_CONSUMER_CLUSTERED                            = "clustered";

  public static final String TAG_CONSUMER_BUY_PDF                              = "buyPDF";

  public static final String TAG_CONSUMER_NUMBER_ENTREPRENEURS_SEARCH          = "numberEntrepreneursSearch";

  public static final String TAG_CONSUMER_REPUTATION                           = "reputation";

  public static final String TAG_CONSUMER_REPUTATION_ENTREPRENEUR              = "entrepreneur";

  public static final String TAG_CONSUMER_REPUTATION_ENTREPRENEUR_THRESHOLD    = "entrepreneurThreshold";

  public static final String TAG_CONSUMER_SANCTION                             = "sanction";

  public static final String TAG_CONSUMER_SANCTION_THRESHOLD                   = "threshold";

  public static final String TAG_CONSUMER_SANCTION_DISCERNABILITY              = "discernability";

  /**
   * Entrepreneurs tags
   */
  public static final String TAG_ENTREPRENEURS                                 = "entrepreneurs";

  public static final String TAG_ENTREPRENEUR                                  = "entrepreneur";

  public static final String TAG_ENTREPRENEUR_NUMBER_ENTREPRENEURS             = "numberEntrepreneurs";

  public static final String TAG_ENTREPRENEUR_LOGGING_TIME_UNIT                = "loggingTimeUnit";

  public static final String TAG_ENTREPRENEUR_CLUSTERED                        = "clustered";

  public static final String TAG_ENTREPRENEUR_WEALTH                           = "wealth";

  public static final String TAG_ENTREPRENEUR_PERIODICITY_WAGE_PDF             = "periodicityWagePDF";

  public static final String TAG_ENTREPRENEUR_MINIMUM_WAGE                     = "minimumWage";

  public static final String TAG_ENTREPRENEUR_MAXIMUM_WAGE                     = "maximumWage";

  public static final String TAG_ENTREPRENEUR_VARIATION_WAGE                   = "variationWage";

  public static final String TAG_ENTREPRENEUR_MINIMUM_PRICE                    = "minimumPrice";

  public static final String TAG_ENTREPRENEUR_MAXIMUM_PRICE                    = "maximumPrice";

  public static final String TAG_ENTREPRENEUR_VARIATION_PRICE                  = "variationPrice";

  public static final String TAG_ENTREPRENEUR_DENOUNCE_ALPHA                   = "denounceAlpha";

  public static final String TAG_ENTREPRENEUR_COLLABORATION_PROBABILITY        = "collaborationProbability";

  public static final String TAG_ENTREPRENEUR_AFFILIATE_THRESHOLD              = "affiliateThreshold";

  public static final String TAG_ENTREPRENEUR_AFFILIATED                       = "affiliated";

  public static final String TAG_ENTREPRENEUR_REPUTATION                       = "reputation";

  public static final String TAG_ENTREPRENEUR_REPUTATION_STATE_PROTECTOR       = "stateProtector";

  public static final String TAG_ENTREPRENEUR_REPUTATION_STATE_PUNISHER        = "statePunisher";

  public static final String TAG_ENTREPRENEUR_REPUTATION_MAFIA_PUNISHER        = "mafiaPunisher";

  /**
   * Normative tags
   */
  public static final String TAG_NORMATIVE_XML                                 = "normativeXML";

  public static final String TAG_NORMATIVE_XSD                                 = "normativeXSD";

  public static final String TAG_NORMATIVE_INDIVIDUAL_WEIGHT                   = "individualWeight";

  public static final String TAG_NORMATIVE_NORMATIVE_WEIGHT                    = "normativeWeight";

  public static final String TAG_NORMATIVE_NORMS_SALIENCE                      = "normsSalience";

  public static final String TAG_NORMATIVE_NORM_SALIENCE                       = "normSalience";

  public static final String TAG_NORMATIVE_NORM_ID                             = "id";

  public static final String TAG_NORMATIVE_NORM_ACTIVE                         = "active";

  public static final String TAG_NORMATIVE_NORM_INITIAL_COMPLIANCE             = "compliance";

  public static final String TAG_NORMATIVE_NORM_INITIAL_VIOLATION              = "violation";

  public static final String TAG_NORMATIVE_NORM_INITIAL_OBS_COMPLIANCE         = "obsCompliance";

  public static final String TAG_NORMATIVE_NORM_INITIAL_OBS_VIOLATION          = "obsViolation";

  public static final String TAG_NORMATIVE_NORM_INITIAL_PUNISHMENT             = "punishment";

  public static final String TAG_NORMATIVE_NORM_INITIAL_SANCTION               = "sanction";

  public static final String TAG_NORMATIVE_NORM_INITIAL_INVOCATION_COMPLIANCE  = "invocationCompliance";

  public static final String TAG_NORMATIVE_NORM_INITIAL_INVOCATION_VIOLATION   = "invocationViolation";

  /**
   * State tags
   */
  public static final String TAG_STATE                                         = "state";

  public static final String TAG_STATE_NUMBER_POLICE_OFFICERS                  = "numberPoliceOfficers";

  public static final String TAG_STATE_LOGGING_TIME_UNIT                       = "loggingTimeUnit";

  public static final String TAG_STATE_GENERAL_INVESTIGATION_DURATION_PDF      = "generalInvestigationDurationPDF";

  public static final String TAG_STATE_BUREAUCRATIC_ACTIVITY_DURATION_PDF      = "bureaucraticActivityDurationPDF";

  public static final String TAG_STATE_SPECIFIC_INVESTIGATION_DURATION_PDF     = "specificInvestigationDurationPDF";

  public static final String TAG_STATE_SPECIFIC_INVESTIGATION_PROBABILITY      = "specificInvestigationProbability";

  public static final String TAG_STATE_CAPTURE_PROBABILITY                     = "captureProbability";

  public static final String TAG_STATE_EVIDENCE_PROBABILITY                    = "evidenceProbability";

  public static final String TAG_STATE_CUSTODY_DURATION_PDF                    = "custodyDurationPDF";

  public static final String TAG_STATE_CONVICTION_PROBABILITY                  = "convictionProbability";

  public static final String TAG_STATE_COLLABORATION_CONVICTION_FUNCTION       = "collaborationConvictionFunction";

  public static final String TAG_STATE_IMPRISONMENT_DURATION_PDF               = "imprisonmentDurationPDF";

  public static final String TAG_STATE_TIME_TO_COMPENSATION_PDF                = "timeToCompensationPDF";

  public static final String TAG_STATE_NO_COLLABORATION_PUNISHMENT_PROBABILITY = "noCollaborationPunishmentProbability";

  public static final String TAG_STATE_NO_COLLABORATION_PUNISHMENT             = "noCollaborationPunishment";

  public static final String TAG_STATE_RESOURCE_FONDO                          = "resourceFondo";

  public static final String TAG_STATE_PERIODICITY_FONDO_PDF                   = "periodicityFondoPDF";

  public static final String TAG_STATE_PROPORTION_TRANSFER_FONDO               = "proportionTransferFondo";

  public static final String TAG_STATE_SPREAD_INFO_FUNCTION                    = "spreadInfoFunction";

  public static final String TAG_STATE_PROPORTION_CONSUMERS                    = "proportionConsumers";

  public static final String TAG_STATE_PROPORTION_ENTREPRENEURS                = "proportionEntrepreneurs";

  /**
   * Mafia tags
   */
  public static final String TAG_MAFIA                                         = "mafia";

  public static final String TAG_MAFIA_NUMBER_MAFIOSI                          = "numberMafiosi";

  public static final String TAG_MAFIA_LOGGING_TIME_UNIT                       = "loggingTimeUnit";

  public static final String TAG_MAFIA_WEALTH                                  = "wealth";

  public static final String TAG_MAFIA_DEMAND_PDF                              = "demandPDF";

  public static final String TAG_MAFIA_DEMAND_AFFILIATED_PROBABILITY           = "demandAffiliatedProbability";

  public static final String TAG_MAFIA_EXTORTION_LEVEL                         = "extortionLevel";

  public static final String TAG_MAFIA_PUNISHMENT_SEVERITY                     = "punishmentSeverity";

  public static final String TAG_MAFIA_COLLECTION_PDF                          = "collectionPDF";

  public static final String TAG_MAFIA_PUNISHMENT_PROBABILITY                  = "punishmentProbability";

  public static final String TAG_MAFIA_MINIMUM_BENEFIT                         = "minimumBenefit";

  public static final String TAG_MAFIA_MAXIMUM_BENEFIT                         = "maximumBenefit";

  public static final String TAG_MAFIA_PENTITI_PROBABILITY                     = "pentitiProbability";

  public static final String TAG_MAFIA_RECRUITING_THRESHOLD                    = "recruitingThreshold";

  public static final String TAG_MAFIA_RECRUITING_PROBABILITY                  = "recruitingProbability";

  /**
   * Intermediary organization tags
   */
  public static final String TAG_INTERMEDIARY_ORG                              = "intermediaryOrg";

  public static final String TAG_INTERMEDIARY_ORG_TIME_TO_AFFILIATE_PDF        = "timeToAffiliatePDF";

  public static final String TAG_INTERMEDIARY_ORG_SPREAD_INFO_FUNCTION         = "spreadInfoFunction";

  public static final String TAG_INTERMEDIARY_ORG_PROPORTION_CONSUMERS         = "proportionConsumers";

  public static final String TAG_INTERMEDIARY_ORG_PROPORTION_ENTREPRENEURS     = "proportionEntrepreneurs";

  /**
   * Change tags
   */
  public static final String TAG_CHANGES                                       = "changes";

  public static final String TAG_CHANGE                                        = "change";

  public static final String TAG_CHANGE_TIME                                   = "time";

  public static final String TAG_CHANGE_PARAMETER                              = "parameter";

  public static final String TAG_CHANGE_VALUE                                  = "value";

  /**
   * Batch tags
   */
  public static final String TAG_BATCH                                         = "batch";

  public static final String TAG_BATCH_BASE                                    = "base";

  public static final String TAG_BATCH_OUTPUT                                  = "output";

  public static final String TAG_BATCH_REPLICA                                 = "replica";

  public static final String TAG_BATCH_XML_SCENARIO                            = "xmlScenario";

  public static final String TAG_BATCH_XSD_SCENARIO                            = "xsdScenario";

  public static final String TAG_BATCH_HEADER                                  = "header";

  public static final String TAG_BATCH_GENERAL                                 = "general";

  public static final String TAG_BATCH_COMMUNICATION                           = "communication";

  public static final String TAG_BATCH_CITIZENS                                = "citizens";

  public static final String TAG_BATCH_STATES                                  = "states";

  public static final String TAG_BATCH_MAFIAS                                  = "mafias";

  public static final String TAG_BATCH_IOS                                     = "ios";

  public static final String TAG_BATCH_TAIL                                    = "tail";

  public static final String TAG_BATCH_CODE                                    = "code";

  public static final String TAG_BATCH_CONTENT                                 = "content";

  /**
   * Batch tags
   */
  public static final String TAG_RANDOM_BATCH                                  = "randomBatch";

  public static final String TAG_RANDOM_BATCH_OUTPUT_DIR                       = "outputDir";

  public static final String TAG_RANDOM_BATCH_OUTPUT_FILE                      = "outputFile";

  public static final String TAG_RANDOM_BATCH_REPLICA                          = "replica";

  public static final String TAG_RANDOM_BATCH_XML_SCENARIO                     = "xmlScenario";

  public static final String TAG_RANDOM_BATCH_XSD_SCENARIO                     = "xsdScenario";

  public static final String TAG_RANDOM_BATCH_CHANGES                          = "changes";

  public static final String TAG_RANDOM_BATCH_CHANGE                           = "change";

  public static final String TAG_RANDOM_BATCH_NAME                             = "name";

  public static final String TAG_RANDOM_BATCH_PARAMETER                        = "parameter";

  public static final String TAG_RANDOM_BATCH_TYPE                             = "type";

  public static final String TAG_RANDOM_BATCH_VALUE                            = "value";

  /**
   * Actions
   */
  public static enum Actions {
    EXTORTION,
    PAY_EXTORTION,
    NOT_PAY_EXTORTION,
    DENOUNCE_EXTORTION,
    DENOUNCE_EXTORTION_AFFILIATED,
    NOT_DENOUNCE_EXTORTION,
    NOT_DENOUNCE_EXTORTION_AFFILIATED,
    SPECIFIC_INVESTIGATION,
    MAFIA_PUNISHMENT,
    MAFIA_BENEFIT,
    DENOUNCE_PUNISHMENT,
    DENOUNCE_PUNISHMENT_AFFILIATED,
    NOT_DENOUNCE_PUNISHMENT,
    NOT_DENOUNCE_PUNISHMENT_AFFILIATED,
    CUSTODY,
    RELEASE_CUSTODY,
    IMPRISONMENT,
    RELEASE_IMPRISONMENT,
    PENTITI,
    CAPTURE_MAFIOSO,
    COLLABORATION_REQUEST,
    COLLABORATE,
    NOT_COLLABORATE,
    STATE_PUNISHMENT,
    STATE_COMPENSATION,
    BUY_PRODUCT,
    DELIVER_PRODUCT,
    BUY_PAY_EXTORTION,
    BUY_NOT_PAY_EXTORTION,
    AFFILIATE,
    AFFILIATION_ACCEPTED,
    AFFILIATION_DENIED,
    NORM_INVOCATION_INFO,
    NORM_SANCTION_INFO,
    REPUTATION_INFO,
    CRITICAL_CONSUMER_INFO;
  }

  /**
   * Norms
   */
  public static enum Norms {
    PAY_EXTORTION,
    NOT_PAY_EXTORTION,
    DENOUNCE,
    NOT_DENOUNCE,
    BUY_FROM_NOT_PAYING_ENTREPRENEURS,
    BUY_FROM_PAYING_ENTREPRENEURS;
  }

  /**
   * Sanctions
   */
  public static enum Sanctions {
    REPUTATION_ENTREPRENEUR,
    NORM_SANCTION;
  }

  /**
   * Citizens distribution
   */
  public static enum CitizensDistribution {
    CLUSTERED,
    RANDOM;
  }

  /**
   * Network topology
   */
  public static enum NetworkTopolgy {
    MESH,
    SCALEFREE;
  }

  /**
   * Information request
   */
  public static final String REQUEST_AFFILIATION         = "affiliated";

  public static final String REQUEST_COLLECT_PAYERS      = "requestCollectPayers";

  public static final String REQUEST_CRITICAL_CONSUMERS  = "criticalConsumers";

  public static final String REQUEST_ID                  = "id";

  public static final String REQUEST_DEFAULT_WAGE        = "defaultWage";

  public static final String REQUEST_DURATION            = "duration";

  public static final String REQUEST_ENTREPRENEUR_ID     = "entrepreneurId";

  public static final String REQUEST_PRODUCT_PRICE       = "productPrice";

  public static final String REQUEST_TARGET_ID           = "targetId";

  public static final String REQUEST_WEALTH              = "wealth";

  /**
   * Information set
   */
  public static final String PARAMETER_STATE_ID          = "stateId";

  public static final String PARAMETER_STATE_PUNISHMENT  = "statePunishment";

  public static final String PARAMETER_ADD_MAFIOSO       = "addMafioso";

  public static final String PARAMETER_REMOVE_MAFIOSO    = "removeMafioso";

  public static final String PARAMETER_WEALTH            = "wealth";

  /**
   * Events
   */
  public static final String EVENT_AFFILIATE_PROCESSING  = "eventAffiliateProcessing";

  public static final String EVENT_ASSIST_ENTREPRENEUR   = "eventAssistEntrepreneur";

  public static final String EVENT_BUROCRATIC_ACTIVITY   = "eventBurocraticActivity";

  public static final String EVENT_BUY_PRODUCT           = "eventBuyProduct";

  public static final String EVENT_COLLECT_EXTORTION     = "eventCollectExtortion";

  public static final String EVENT_DEMAND_EXTORTION      = "eventDemandExtortion";

  public static final String EVENT_GENERAL_INVESTIGATION = "eventGeneralInvestigation";

  public static final String EVENT_LOGGING_CONSUMERS     = "eventLoggingConsumers";

  public static final String EVENT_LOGGING_ENTREPRENEURS = "eventLoggingEntrepreneurs";

  public static final String EVENT_LOGGING_MAFIOSI       = "eventLoggingMafiosi";

  public static final String EVENT_LOGGING_INVESTIGATION = "eventLoggingInvestigations";

  public static final String EVENT_RECEIVE_WAGE          = "eventReceiveWage";

  public static final String EVENT_RELEASE_PRISON        = "eventReleasePrison";

  public static final String EVENT_RELEASE_CUSTODY       = "eventReleaseCustody";

  public static final String EVENT_RESOURCE_FONDO        = "eventResourceFondo";

  public static final String EVENT_SPREAD_INFORMATION    = "eventSpreadInformation";

  public static final String EVENT_WRITE_DATA            = "eventWriteData";
}