package gloderss;

public final class Constants {
	
	/**
	 * Parameters
	 */
	public static final String	PARAM_XML_FILENAME													= "xmlFilename";
	
	public static final String	PARAM_XSD_FILENAME													= "xsdFilename";
	
	/**
	 * Simulation tags
	 */
	public static final String	TAG_NAMESPACE																= "palermo.conf";
	
	public static final String	TAG_SCENARIO																= "scenario";
	
	/**
	 * General tags
	 */
	public static final String	TAG_GENERAL																	= "general";
	
	public static final String	TAG_NUMBER_REPLICATIONS											= "numberReplications";
	
	public static final String	TAG_SEEDS																		= "seeds";
	
	public static final String	TAG_SEED																		= "seed";
	
	public static final String	TAG_NUMBER_CYCLES														= "numberCycles";
	
	public static final String	TAG_OUTPUT																	= "output";
	
	public static final String	TAG_OUTPUT_DIRECTORY												= "directory";
	
	public static final String	TAG_OUTPUT_FILENAME													= "filename";
	
	public static final String	TAG_OUTPUT_APPEND														= "append";
	
	public static final String	TAG_OUTPUT_SEPARATOR												= "separator";
	
	public static final String	TAG_OUTPUT_WRITE_FREQUENCY									= "writeFrequency";
	
	/**
	 * Consumers tags
	 */
	public static final String	TAG_CONSUMERS																= "consumers";
	
	public static final String	TAG_CONSUMER																= "consumer";
	
	public static final String	TAG_CONSUMER_NUMBER_CONSUMERS								= "numberConsumers";
	
	public static final String	TAG_CONSUMER_REPUTATION											= "reputation";
	
	public static final String	TAG_CONSUMER_REPUTATION_ENTREPRENEUR_PAYER	= "entrepreneurPayer";
	
	/**
	 * Entrepreneurs tags
	 */
	public static final String	TAG_ENTREPRENEURS														= "entrepreneurs";
	
	public static final String	TAG_ENTREPRENEUR														= "entrepreneur";
	
	public static final String	TAG_ENTREPRENEUR_NUMBER_ENTREPRENEURS				= "numberEntrepreneurs";
	
	public static final String	TAG_ENTREPRENEUR_WEALTH											= "wealth";
	
	public static final String	TAG_ENTREPRENEUR_PERIODICITY_WAGE						= "periodicityWage";
	
	public static final String	TAG_ENTREPRENEUR_MINIMUM_WAGE								= "minimumWage";
	
	public static final String	TAG_ENTREPRENEUR_MAXIMUM_WAGE								= "maximumWage";
	
	public static final String	TAG_ENTREPRENEUR_VARIATION_WAGE							= "variationWage";
	
	public static final String	TAG_ENTREPRENEUR_COLLABORATION_PROBABILITY	= "collaborationProbability";
	
	public static final String	TAG_ENTREPRENEUR_AFFILIATED									= "affiliated";
	
	public static final String	TAG_ENTREPRENEUR_REPUTATION									= "reputation";
	
	public static final String	TAG_ENTREPRENEUR_REPUTATION_STATE_PROTECTOR	= "stateProtector";
	
	public static final String	TAG_ENTREPRENEUR_REPUTATION_STATE_PUNISHER	= "statePunisher";
	
	public static final String	TAG_ENTREPRENEUR_REPUTATION_MAFIA_PUNISHER	= "mafiaPunisher";
	
	/**
	 * Normative tags
	 */
	public static final String	TAG_NORMATIVE_XML														= "normativeXML";
	
	public static final String	TAG_NORMATIVE_XSD														= "normativeXSD";
	
	public static final String	TAG_NORMATIVE_INDIVIDUAL_WEIGHT							= "individualWeight";
	
	public static final String	TAG_NORMATIVE_NORMATIVE_WEIGHT							= "normativeWeight";
	
	public static final String	TAG_NORMATIVE_NORMS_SALIENCE								= "normsSalience";
	
	public static final String	TAG_NORMATIVE_NORM_SALIENCE									= "normSalience";
	
	public static final String	TAG_NORMATIVE_NORM_ID												= "id";
	
	public static final String	TAG_NORMATIVE_NORM_INITIAL_SALIENCE					= "salience";
	
	/**
	 * State tags
	 */
	public static final String	TAG_STATE																		= "state";
	
	public static final String	TAG_STATE_NUMBER_POLICE_OFFICERS						= "numberPoliceOfficers";
	
	public static final String	TAG_STATE_INVESTIGATIVE_DURATION						= "investigativeDuration";
	
	public static final String	TAG_STATE_CAPTURE_PROBABILITY								= "captureProbability";
	
	public static final String	TAG_STATE_INCARCERATION_PROBABILITY					= "incarcerationProbability";
	
	public static final String	TAG_STATE_INCARCERATION_DURATION						= "incarcerationDuration";
	
	public static final String	TAG_STATE_PROPORTION_TRANSFER_FONDO					= "proportionTransferFondo";
	
	/**
	 * Mafia tags
	 */
	public static final String	TAG_MAFIA																		= "mafia";
	
	public static final String	TAG_MAFIA_NUMBER_MAFIOSI										= "numberMafiosi";
	
	public static final String	TAG_MAFIA_WEALTH														= "wealth";
	
	public static final String	TAG_MAFIA_DEMAND_MEAN												= "demandMean";
	
	public static final String	TAG_MAFIA_DEMAND_STANDARD_DEVIATION					= "demandStDev";
	
	public static final String	TAG_MAFIA_EXTORTION_LEVEL										= "extortionLevel";
	
	public static final String	TAG_MAFIA_PUNISHMENT_SEVERITY								= "punishmentSeverity";
	
	public static final String	TAG_MAFIA_PUNISHMENT_PROBABILITY						= "punishmentProbability";
	
	public static final String	TAG_MAFIA_MINIMUM_BENEFIT										= "minimumBenefit";
	
	public static final String	TAG_MAFIA_MAXIMUM_BENEFIT										= "maximumBenefit";
	
	public static final String	TAG_MAFIA_PENTITI_PROBABILITY								= "pentitiProbability";
	
	/**
	 * Intermediary organization tags
	 */
	public static final String	TAG_INTERMEDIARY_ORG												= "intermediaryOrg";
	
	/**
	 * Actions
	 */
	public static enum Actions {
		EXTORTION, PAY_EXTORTION, NOT_PAY_EXTORTION, DENOUNCE_EXTORTION, NOT_DENOUNCE_EXTORTION, INVESTIGATE_EXTORTION, MAFIA_PUNISHMENT, MAFIA_BENEFIT, INVESTIGATE_PUNISHMENT, PENTITI;
	}
	
	/**
	 * Norms
	 */
	public static enum Norms {
		PAY, NOT_PAY, DENOUNCE, NOT_DENOUNCE, BUY_PAY;
	}
	
	/**
	 * Information request
	 */
	public static final String	REQUEST_ID							= "id";
	
	public static final String	REQUEST_DEFAULT_WAGE		= "defaultWage";
	
	public static final String	REQUEST_DURATION				= "duration";
	
	public static final String	REQUEST_TARGET_ID				= "targetId";
	
	/**
	 * Information set
	 */
	public static final String	PARAMETER_STATE_ID			= "stateId";
	
	public static final String	PARAMETER_LIST_MAFIOSI	= "mafiosiList";
	
	/**
	 * Events
	 */
	public static final String	EVENT_EXTORTION_DEMAND	= "decideExtortion";
	
	public static final String	EVENT_RECEIVE_WAGE			= "receiveWage";
}