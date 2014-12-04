package gloderss.conf;

import gloderss.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

@XmlRootElement(name = Constants.TAG_SCENARIO)
public class ScenarioConf {
	
	private GeneralConf							generalConf					= new GeneralConf();
	
	private CommunicationConf				communicationConf		= new CommunicationConf();
	
	private List<ConsumerConf>			consumersConf				= new ArrayList<ConsumerConf>();
	
	private List<EntrepreneurConf>	entrepreneursConf		= new ArrayList<EntrepreneurConf>();
	
	private StateConf								stateConf						= new StateConf();
	
	private MafiaConf								mafiaConf						= new MafiaConf();
	
	private IntermediaryOrgConf			intermediaryOrgConf	= new IntermediaryOrgConf();
	
	
	/**
	 * Process XML scenario configuration file
	 * 
	 * @param xmlFilename
	 *          XML configuration file
	 * @param xsdFilename
	 *          XSD configuration filename
	 * @return Scenario configuration
	 */
	public static ScenarioConf getScenarioConf(String xmlFilename,
			String xsdFilename) {
		
		ScenarioConf scenarioConf = new ScenarioConf();
		
		File xmlFile = new File(xmlFilename);
		File xsdFile = new File(xsdFilename);
		
		if((xmlFile.exists()) && (xsdFile.exists())) {
			
			if(isValid(xmlFilename, xsdFilename)) {
				
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(ScenarioConf.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					
					scenarioConf = (ScenarioConf) jaxbUnmarshaller.unmarshal(xmlFile);
					
				} catch(JAXBException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return scenarioConf;
	}
	
	
	/**
	 * Check the validity of the XML file against an XSD file
	 * 
	 * @param xmlFilename
	 *          XML filename
	 * @param xsdFilename
	 *          XSD filename
	 * @return True if valid, False otherwise
	 */
	public static boolean isValid(String xmlFilename, String xsdFilename) {
		boolean valid = false;
		
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(xsdFilename));
			
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlFilename));
			
			valid = true;
			
		} catch(SAXException e) {
		} catch(Exception e) {
		}
		
		return valid;
	}
	
	
	public GeneralConf getGeneralConf() {
		return this.generalConf;
	}
	
	
	@XmlElement(name = Constants.TAG_GENERAL)
	public void setGeneralConf(GeneralConf generalConf) {
		this.generalConf = generalConf;
	}
	
	
	public CommunicationConf getCommunicationConf() {
		return this.communicationConf;
	}
	
	
	@XmlElement(name = Constants.TAG_COMMUNICATION)
	public void setCommunicationConf(CommunicationConf communicationConf) {
		this.communicationConf = communicationConf;
	}
	
	
	public List<ConsumerConf> getConsumersConf() {
		return this.consumersConf;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_CONSUMERS)
	@XmlElement(name = Constants.TAG_CONSUMER)
	public void setConsumersConf(List<ConsumerConf> consumersConf) {
		this.consumersConf = consumersConf;
	}
	
	
	public List<EntrepreneurConf> getEntrepreneursConf() {
		return this.entrepreneursConf;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_ENTREPRENEURS)
	@XmlElement(name = Constants.TAG_ENTREPRENEUR)
	public void setEntrepreneursConf(List<EntrepreneurConf> entrepreneursConf) {
		this.entrepreneursConf = entrepreneursConf;
	}
	
	
	public StateConf getStateConf() {
		return this.stateConf;
	}
	
	
	@XmlElement(name = Constants.TAG_STATE)
	public void setStateConf(StateConf stateConf) {
		this.stateConf = stateConf;
	}
	
	
	public MafiaConf getMafiaConf() {
		return this.mafiaConf;
	}
	
	
	@XmlElement(name = Constants.TAG_MAFIA)
	public void setMafiaConf(MafiaConf mafiaConf) {
		this.mafiaConf = mafiaConf;
	}
	
	
	public IntermediaryOrgConf getIntermediaryOrgConf() {
		return this.intermediaryOrgConf;
	}
	
	
	@XmlElement(name = Constants.TAG_INTERMEDIARY_ORG)
	public void setIntermediaryOrgConf(IntermediaryOrgConf intermediaryOrgConf) {
		this.intermediaryOrgConf = intermediaryOrgConf;
	}
}