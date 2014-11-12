package gloderss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import gloderss.agents.consumer.ConsumerAgent;
import gloderss.agents.entrepreneur.EntrepreneurAgent;
import gloderss.agents.intermediaryOrg.IntermediaryOrg;
import gloderss.agents.mafia.MafiaOrg;
import gloderss.agents.mafia.MafiosoAgent;
import gloderss.agents.state.StateOrg;
import gloderss.conf.ConsumerConf;
import gloderss.conf.EntrepreneurConf;
import gloderss.conf.ScenarioConf;
import gloderss.output.AbstractEntity.EntityType;
import gloderss.output.OutputController;
import gloderss.util.network.Network;
import gloderss.util.random.RandomUtil;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.queue.ListQueue;

public class GLODERSSimulator extends EventSimulator {
	
	private ScenarioConf										scenarioConf;
	
	private Map<Integer, ConsumerAgent>			consumers;
	
	private Map<Integer, EntrepreneurAgent>	entrepreneurs;
	
	private StateOrg												state;
	
	private MafiaOrg												mafia;
	
	private IntermediaryOrg									intermediaryOrg;
	
	
	public GLODERSSimulator(String xmlFilename, String xsdFilename) {
		this.scenarioConf = ScenarioConf.getScenarioConf(xmlFilename, xsdFilename);
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
		
		int nextSeed = 0;
		for(int replica = 0; replica < numReplications; replica++) {
			this.events = new ListQueue();
			
			/**
			 * Output controller
			 */
			OutputController.getInstance(this.scenarioConf.getGeneralConf()
					.getOutputConf(), replica);
			OutputController.init(EntityType.EXTORTION, this.scenarioConf
					.getGeneralConf().getOutputConf().getFilename());
			
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
			int index = 0;
			
			// Create Citizens
			this.consumers = new HashMap<Integer, ConsumerAgent>();
			this.entrepreneurs = new HashMap<Integer, EntrepreneurAgent>();
			
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
				nEntrepreneurs[index++] = entrepreneurConf.getNumber();
				totalEntrepreneurs += entrepreneurConf.getNumber();
			}
			
			EntrepreneurAgent entrepreneur;
			ConsumerAgent consumer;
			for(int i = 0; i < (totalConsumers + totalEntrepreneurs);) {
				
				index = RandomUtil.nextIntFromTo(0,
						(nConsumers.length + nEntrepreneurs.length) - 1);
				
				if(index <= (nConsumers.length - 1)) {
					if(nConsumers[index] > 0) {
						consumer = new ConsumerAgent(id, this, consumersConf.get(index));
						this.consumers.put(id, consumer);
						nConsumers[index]--;
						i++;
						id++;
					}
				} else {
					index -= nConsumers.length;
					if(nEntrepreneurs[index] > 0) {
						entrepreneur = new EntrepreneurAgent(id, this,
								entrepreneursConf.get(index));
						this.entrepreneurs.put(id, entrepreneur);
						nEntrepreneurs[index]--;
						i++;
						id++;
					}
				}
			}
			
			// Create the State agent
			this.state = new StateOrg(id, this, this.scenarioConf.getStateConf(),
					this.entrepreneurs);
			id += this.scenarioConf.getStateConf().getNumberPoliceOfficers() + 1;
			
			// Create the Mafia agent
			this.mafia = new MafiaOrg(id, this, this.scenarioConf.getMafiaConf(),
					this.state.getId(), this.entrepreneurs);
			id += this.scenarioConf.getMafiaConf().getNumberMafiosi() + 1;
			
			// Create the Intermediary Organization agent
			this.intermediaryOrg = new IntermediaryOrg(id++, this, this.consumers,
					this.entrepreneurs);
			
			/**
			 * Networks
			 */
			Network<Integer> socialNetwork = new Network<Integer>();
			
			// Customers and Entrepreneurs network
			List<Integer> citizens = new ArrayList<Integer>();
			citizens.addAll(this.entrepreneurs.keySet());
			citizens.addAll(this.consumers.keySet());
			
			socialNetwork.generateBarabasiAlbertScaleFreeNetwork(citizens);
			
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
			
			for(EntrepreneurAgent e : entrepreneurs.values()) {
				e.initializeSim();
			}
			
			this.state.initializeSim();
			this.mafia.initializeSim();
			this.intermediaryOrg.initializeSim();
			
			this.doAllEvents(numCycles);
		}
	}
	
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out
					.println("Syntax: GLODERSSimulator [XML Filename] [XSD Filename]");
			System.exit(1);
		}
		
		GLODERSSimulator gloderss = new GLODERSSimulator(args[0], args[1]);
		gloderss.run();
	}
}