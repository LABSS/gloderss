package gloderss.conf;

import gloderss.Constants;
import java.io.File;
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

@XmlRootElement(name = Constants.TAG_BATCH)
public class BatchConf {
	
	private String							batchBase;
	
	private String							batchOutput;
	
	private Integer							batchReplica;
	
	private String							batchXMLScenario;
	
	private String							batchXSDScenario;
	
	private String							batchHeader;
	
	private String							batchGeneral;
	
	private String							batchCommunication;
	
	private List<BatchCodeConf>	batchCitizens;
	
	private List<BatchCodeConf>	batchStates;
	
	private List<BatchCodeConf>	batchMafias;
	
	private List<BatchCodeConf>	batchIOs;
	
	private String							batchTail;
	
	
	/**
	 * Process XML batch configuration file
	 * 
	 * @param xmlFilename
	 *          XML configuration file
	 * @param xsdFilename
	 *          XSD configuration filename
	 * @return Batch configuration
	 */
	public static BatchConf getBatchConf(String xmlFilename, String xsdFilename) {
		
		BatchConf batchConf = new BatchConf();
		
		File xmlFile = new File(xmlFilename);
		File xsdFile = new File(xsdFilename);
		
		if((xmlFile.exists()) && (xsdFile.exists())) {
			
			if(isValid(xmlFilename, xsdFilename)) {
				
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(BatchConf.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					
					batchConf = (BatchConf) jaxbUnmarshaller.unmarshal(xmlFile);
					
				} catch(JAXBException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return batchConf;
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
	
	
	public String getBatchBase() {
		return this.batchBase;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_BASE)
	public void setBatchBase(String batchBase) {
		this.batchBase = batchBase;
	}
	
	
	public String getBatchOutput() {
		return this.batchOutput;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_OUTPUT)
	public void setBatchOutput(String batchOutput) {
		this.batchOutput = batchOutput;
	}
	
	
	public Integer getBatchReplica() {
		return this.batchReplica;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_REPLICA)
	public void setBatchReplica(Integer batchReplica) {
		this.batchReplica = batchReplica;
	}
	
	
	public String getBatchXMLScenario() {
		return this.batchXMLScenario;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_XML_SCENARIO)
	public void setBatchXMLScenario(String batchXMLScenario) {
		this.batchXMLScenario = batchXMLScenario;
	}
	
	
	public String getBatchXSDScenario() {
		return this.batchXSDScenario;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_XSD_SCENARIO)
	public void setBatchXSDScenario(String batchXSDScenario) {
		this.batchXSDScenario = batchXSDScenario;
	}
	
	
	public String getBatchHeader() {
		return this.batchHeader;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_HEADER)
	public void setBatchHeader(String batchHeader) {
		this.batchHeader = batchHeader;
	}
	
	
	public String getBatchGeneral() {
		return this.batchGeneral;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_GENERAL)
	public void setBatchGeneral(String batchGeneral) {
		this.batchGeneral = batchGeneral;
	}
	
	
	public String getBatchCommunication() {
		return this.batchCommunication;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_COMMUNICATION)
	public void setBatchCommunication(String batchCommunication) {
		this.batchCommunication = batchCommunication;
	}
	
	
	public List<BatchCodeConf> getBatchCitizens() {
		return this.batchCitizens;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_BATCH_CITIZENS)
	@XmlElement(name = Constants.TAG_BATCH_CONTENT)
	public void setBatchCitizens(List<BatchCodeConf> batchCitizens) {
		this.batchCitizens = batchCitizens;
	}
	
	
	public List<BatchCodeConf> getBatchStates() {
		return this.batchStates;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_BATCH_STATES)
	@XmlElement(name = Constants.TAG_BATCH_CONTENT)
	public void setBatchStates(List<BatchCodeConf> batchStates) {
		this.batchStates = batchStates;
	}
	
	
	public List<BatchCodeConf> getBatchMafias() {
		return this.batchMafias;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_BATCH_MAFIAS)
	@XmlElement(name = Constants.TAG_BATCH_CONTENT)
	public void setBatchMafias(List<BatchCodeConf> batchMafias) {
		this.batchMafias = batchMafias;
	}
	
	
	public List<BatchCodeConf> getBatchIOs() {
		return this.batchIOs;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_BATCH_IOS)
	@XmlElement(name = Constants.TAG_BATCH_CONTENT)
	public void setBatchIOs(List<BatchCodeConf> batchIOs) {
		this.batchIOs = batchIOs;
	}
	
	
	public String getBatchTail() {
		return this.batchTail;
	}
	
	
	@XmlElement(name = Constants.TAG_BATCH_TAIL)
	public void setBatchTail(String batchTail) {
		this.batchTail = batchTail;
	}
}