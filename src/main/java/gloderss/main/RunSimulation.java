package gloderss.main;

import gloderss.Constants;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.agents.intermediaryOrg.IntermediaryOrg;
import gloderss.agents.mafia.MafiaOrg;
import gloderss.agents.mafia.MafiosoAgent;
import gloderss.agents.state.StateOrg;
import gloderss.communication.CommunicationController;
import gloderss.conf.ConsumerConf;
import gloderss.conf.EntrepreneurConf;
import gloderss.conf.ScenarioConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.queue.ListQueue;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.OutputController;
import gloderss.util.network.Network;
import gloderss.util.random.RandomUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunSimulation extends EventSimulator {
	
	private final static Logger							logger	= LoggerFactory
																											.getLogger(RunSimulation.class);
	
	private ScenarioConf										scenarioConf;
	
	private Map<Integer, ConsumerAgent>			consumers;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private StateOrg												state;
	
	private MafiaOrg												mafia;
	
	private IntermediaryOrg									intermediaryOrg;
	
	
	/**
	 * Run a single instance of the simulation
	 * 
	 * @param xmlFilename
	 *          Configuration file
	 * @param xsdFilename
	 *          Schema of the configuration file
	 */
	public RunSimulation(String xmlFilename, String xsdFilename) {
		this.scenarioConf = ScenarioConf.getScenarioConf(xmlFilename, xsdFilename);
		
		boolean valid = ScenarioConf.isValid(xmlFilename, xsdFilename);
		logger.debug("[XML VALID] " + valid);
		
		if(!valid) {
			System.out.println("INVALID XML FILE");
			System.exit(1);
		}
	}
	
	
	/**
	 * Create the citizens distributed in a way they will be randomly distributed
	 * in the network
	 * 
	 * @param id
	 *          Initial agents identification
	 * @param citizens
	 *          Random citizens
	 * @return Next available agent identification
	 */
	private int randomDistribution(int id, List<Integer> citizens) {
		int citizenId = id;
		int index;
		
		logger.debug("CREATE CONSUMERS");
		int totalConsumers = 0;
		List<ConsumerConf> consumersConf = new Vector<ConsumerConf>();
		consumersConf.addAll(this.scenarioConf.getConsumersConf());
		int nConsumers[] = new int[consumersConf.size()];
		index = 0;
		for(ConsumerConf consumerConf : consumersConf) {
			nConsumers[index++] = consumerConf.getNumberConsumers();
			totalConsumers += consumerConf.getNumberConsumers();
		}
		
		int totalEntrepreneurs = 0;
		List<EntrepreneurConf> entrepreneursConf = new Vector<EntrepreneurConf>();
		entrepreneursConf.addAll(this.scenarioConf.getEntrepreneursConf());
		int nEntrepreneurs[] = new int[entrepreneursConf.size()];
		index = 0;
		for(EntrepreneurConf entrepreneurConf : entrepreneursConf) {
			nEntrepreneurs[index++] = entrepreneurConf.getNumberEntrepreneurs();
			totalEntrepreneurs += entrepreneurConf.getNumberEntrepreneurs();
		}
		
		EntrepreneurAgent entrepreneur;
		ConsumerAgent consumer;
		for(int i = 0; i < (totalConsumers + totalEntrepreneurs);) {
			
			index = RandomUtil.nextIntFromTo(0,
					(nConsumers.length + nEntrepreneurs.length) - 1);
			
			if(index <= (nConsumers.length - 1)) {
				if(nConsumers[index] > 0) {
					consumer = new ConsumerAgent(citizenId, this,
							consumersConf.get(index));
					this.consumers.put(citizenId, consumer);
					citizens.add(citizenId);
					nConsumers[index]--;
					i++;
					citizenId++;
				}
			} else {
				index -= nConsumers.length;
				if(nEntrepreneurs[index] > 0) {
					entrepreneur = new EntrepreneurAgent(citizenId, this,
							entrepreneursConf.get(index));
					this.entrepreneurs.put(citizenId, entrepreneur);
					citizens.add(citizenId);
					nEntrepreneurs[index]--;
					i++;
					citizenId++;
				}
			}
		}
		
		return citizenId;
	}
	
	
	/**
	 * Create the citizens clustered in the network
	 * 
	 * @param id
	 *          Initial agents identification
	 * @param citizens
	 *          Clustered citizens
	 * @return Next available agent identification
	 */
	private int clusteredDistribution(int id, List<Integer> citizens) {
		int citizenId = id;
		int index;
		
		List<ConsumerConf> consumersConf = new Vector<ConsumerConf>();
		consumersConf.addAll(this.scenarioConf.getConsumersConf());
		int nConsumers[] = new int[consumersConf.size()];
		
		List<EntrepreneurConf> entrepreneursConf = new Vector<EntrepreneurConf>();
		entrepreneursConf.addAll(this.scenarioConf.getEntrepreneursConf());
		int nEntrepreneurs[] = new int[entrepreneursConf.size()];
		
		logger.debug("CREATE CONSUMERS CLUSTERED");
		index = 0;
		int totalConsumers = 0;
		for(ConsumerConf consumerConf : consumersConf) {
			if(consumerConf.getClustered()) {
				nConsumers[index++] = consumerConf.getNumberConsumers();
				totalConsumers += consumerConf.getNumberConsumers();
			} else {
				nConsumers[index++] = 0;
			}
		}
		
		logger.debug("CREATE ENTREPRENEURS CLUSTERED");
		index = 0;
		int totalEntrepreneurs = 0;
		for(EntrepreneurConf entrepreneurConf : entrepreneursConf) {
			if(entrepreneurConf.getClustered()) {
				nEntrepreneurs[index++] = entrepreneurConf.getNumberEntrepreneurs();
				totalEntrepreneurs += entrepreneurConf.getNumberEntrepreneurs();
			} else {
				nEntrepreneurs[index++] = 0;
			}
		}
		
		EntrepreneurAgent entrepreneur;
		ConsumerAgent consumer;
		for(int i = 0; i < (totalConsumers + totalEntrepreneurs);) {
			
			index = RandomUtil.nextIntFromTo(0,
					(nConsumers.length + nEntrepreneurs.length) - 1);
			
			if(index <= (nConsumers.length - 1)) {
				if(nConsumers[index] > 0) {
					consumer = new ConsumerAgent(citizenId, this,
							consumersConf.get(index));
					this.consumers.put(citizenId, consumer);
					citizens.add(citizenId);
					nConsumers[index]--;
					i++;
					citizenId++;
				}
			} else {
				index -= nConsumers.length;
				if(nEntrepreneurs[index] > 0) {
					entrepreneur = new EntrepreneurAgent(citizenId, this,
							entrepreneursConf.get(index));
					this.entrepreneurs.put(citizenId, entrepreneur);
					citizens.add(citizenId);
					nEntrepreneurs[index]--;
					i++;
					citizenId++;
				}
			}
		}
		
		logger.debug("CREATE CONSUMERS");
		index = 0;
		totalConsumers = 0;
		for(ConsumerConf consumerConf : consumersConf) {
			if(!consumerConf.getClustered()) {
				nConsumers[index++] = consumerConf.getNumberConsumers();
				totalConsumers += consumerConf.getNumberConsumers();
			} else {
				nConsumers[index++] = 0;
			}
		}
		
		logger.debug("CREATE ENTREPRENEURS");
		index = 0;
		totalEntrepreneurs = 0;
		for(EntrepreneurConf entrepreneurConf : entrepreneursConf) {
			if(!entrepreneurConf.getClustered()) {
				nEntrepreneurs[index++] = entrepreneurConf.getNumberEntrepreneurs();
				totalEntrepreneurs += entrepreneurConf.getNumberEntrepreneurs();
			} else {
				nEntrepreneurs[index++] = 0;
			}
		}
		
		for(int i = 0; i < (totalConsumers + totalEntrepreneurs);) {
			
			index = RandomUtil.nextIntFromTo(0,
					(nConsumers.length + nEntrepreneurs.length) - 1);
			
			if(index <= (nConsumers.length - 1)) {
				if(nConsumers[index] > 0) {
					consumer = new ConsumerAgent(citizenId, this,
							consumersConf.get(index));
					this.consumers.put(citizenId, consumer);
					citizens.add(citizenId);
					nConsumers[index]--;
					i++;
					citizenId++;
				}
			} else {
				index -= nConsumers.length;
				if(nEntrepreneurs[index] > 0) {
					entrepreneur = new EntrepreneurAgent(citizenId, this,
							entrepreneursConf.get(index));
					this.entrepreneurs.put(citizenId, entrepreneur);
					citizens.add(citizenId);
					nEntrepreneurs[index]--;
					i++;
					citizenId++;
				}
			}
		}
		
		return citizenId;
	}
	
	
	/**
	 * Run the simulation for a period of time
	 * 
	 * @param none
	 * @return none
	 */
	public void run() {
		
		int numReplications = this.scenarioConf.getGeneralConf()
				.getNumberReplications();
		
		double numCycles = this.scenarioConf.getGeneralConf().getNumberCycles();
		
		Vector<Integer> seeds = new Vector<Integer>();
		seeds.addAll(this.scenarioConf.getGeneralConf().getSeedsConf());
		
		CommunicationController.getInstance(this.scenarioConf
				.getCommunicationConf());
		
		OutputController outputController = new OutputController(this,
				this.scenarioConf.getGeneralConf().getOutputConf());
		
		int nextSeed = 0;
		for(int replica = 0; replica < numReplications; replica++) {
			this.init();
			this.events = new ListQueue();
			
			CommunicationController.getInstance().reset(
					this.scenarioConf.getCommunicationConf());
			
			/**
			 * Output controller
			 */
			outputController.newInstance(replica);
			outputController.init(EntityType.EXTORTION, this.scenarioConf
					.getGeneralConf().getFilenameConf().getExtortion());
			outputController.init(EntityType.COMPENSATION, this.scenarioConf
					.getGeneralConf().getFilenameConf().getCompensation());
			outputController.init(EntityType.PURCHASE, this.scenarioConf
					.getGeneralConf().getFilenameConf().getPurchase());
			outputController.init(EntityType.NORMATIVE, this.scenarioConf
					.getGeneralConf().getFilenameConf().getNormative());
			outputController.init(EntityType.ENTREPRENEUR, this.scenarioConf
					.getGeneralConf().getFilenameConf().getEntrepreneur());
			outputController.init(EntityType.CONSUMER, this.scenarioConf
					.getGeneralConf().getFilenameConf().getConsumer());
			outputController.init(EntityType.MAFIA, this.scenarioConf
					.getGeneralConf().getFilenameConf().getMafia());
			outputController.init(EntityType.MAFIOSI, this.scenarioConf
					.getGeneralConf().getFilenameConf().getMafiosi());
			outputController.init(EntityType.STATE, this.scenarioConf
					.getGeneralConf().getFilenameConf().getState());
			outputController.init(EntityType.INTERMEDIARY_ORGANIZATION,
					this.scenarioConf.getGeneralConf().getFilenameConf()
							.getIntermediaryOrganization());
			
			/**
			 * Set random seed
			 */
			RandomUtil.setSeed(nextSeed);
			if(seeds.size() > (nextSeed + 1)) {
				nextSeed += 1;
			} else {
				nextSeed = 0;
			}
			
			/**
			 * Create agents
			 */
			int id = 0;
			
			// Create Citizens
			this.consumers = new HashMap<Integer, ConsumerAgent>();
			this.entrepreneurs = new HashMap<Integer, EntrepreneurAgent>();
			
			List<Integer> citizens = new ArrayList<Integer>();
			switch(Constants.CitizensDistribution.valueOf(this.scenarioConf
					.getGeneralConf().getCitizensDistribution())) {
				case CLUSTERED:
					id = this.clusteredDistribution(id, citizens);
					break;
				case RANDOM:
					id = this.randomDistribution(id, citizens);
					break;
				default:
					id = this.randomDistribution(id, citizens);
					break;
			}
			
			ConsumerAgent consumer;
			// Provide the set of Entrepreneurs to the Consumers
			for(Integer consumerId : this.consumers.keySet()) {
				consumer = this.consumers.get(consumerId);
				consumer.setEntrepreneurs(this.entrepreneurs);
			}
			
			// Create the State agent
			logger.debug("[CREATE STATE]");
			this.state = new StateOrg(id, this, this.scenarioConf.getStateConf(),
					this.consumers, this.entrepreneurs);
			id += this.scenarioConf.getStateConf().getNumberPoliceOfficers() + 1;
			
			// Create the Mafia agent
			logger.debug("[CREATE MAFIA]");
			this.mafia = new MafiaOrg(id, this, this.scenarioConf.getMafiaConf(),
					this.state.getId(), this.entrepreneurs);
			id += this.scenarioConf.getMafiaConf().getNumberMafiosi() + 1;
			
			// Create the Intermediary Organization agent
			logger.debug("[CREATE INTERMEDIARY ORGANIZATION]");
			this.intermediaryOrg = new IntermediaryOrg(id++, this,
					this.scenarioConf.getIntermediaryOrgConf(), this.consumers,
					this.entrepreneurs);
			
			this.state.setIOId(this.intermediaryOrg.getId());
			for(EntrepreneurAgent e : this.entrepreneurs.values()) {
				e.setIOId(this.intermediaryOrg.getId());
			}
			
			/**
			 * Networks
			 */
			logger.debug("[CREATE NETWORK OF ENTREPRENEURS AND CONSUMERS]");
			Network<Integer> socialNetwork = new Network<Integer>();
			
			// Customers and Entrepreneurs network
			switch(Constants.NetworkTopolgy.valueOf(this.scenarioConf
					.getGeneralConf().getNetworkTopology())) {
				case MESH:
					socialNetwork.generateMeshNetwork(citizens);
					break;
				case SCALEFREE:
					socialNetwork.generateBarabasiAlbertScaleFreeNetwork(citizens);
					break;
				default:
					socialNetwork.generateBarabasiAlbertScaleFreeNetwork(citizens);
					break;
			}
			
			for(EntrepreneurAgent e : this.entrepreneurs.values()) {
				e.setNeighbors(socialNetwork.getNeighbors(e.getId()));
			}
			for(ConsumerAgent c : this.consumers.values()) {
				c.setNeighbors(socialNetwork.getNeighbors(c.getId()));
			}
			
			// Mafia network
			Network<Integer> mafiaNetwork = new Network<Integer>();
			mafiaNetwork.generateBarabasiAlbertScaleFreeNetwork(this.mafia
					.getMafiosi().keySet());
			for(MafiosoAgent m : this.mafia.getMafiosi().values()) {
				m.setNeighbors(mafiaNetwork.getNeighbors(m.getId()));
			}
			
			for(ConsumerAgent c : this.consumers.values()) {
				c.initializeSim();
			}
			
			for(EntrepreneurAgent e : this.entrepreneurs.values()) {
				e.initializeSim();
			}
			
			this.state.initializeSim();
			this.mafia.initializeSim();
			this.intermediaryOrg.initializeSim();
			outputController.initializeSim();
			
			this.doAllEvents(numCycles);
			
			this.state.finalizeSim();
			this.mafia.finalizeSim();
			this.intermediaryOrg.finalizeSim();
			for(ConsumerAgent c : this.consumers.values()) {
				c.finalizeSim();
			}
			
			for(EntrepreneurAgent e : this.entrepreneurs.values()) {
				e.finalizeSim();
			}
			
			try {
				outputController.write(true);
			} catch(IOException e) {
				logger.debug(e.toString());
			}
		}
	}
}