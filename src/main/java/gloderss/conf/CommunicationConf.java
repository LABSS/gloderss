package gloderss.conf;

import gloderss.Constants;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_COMMUNICATION, namespace = Constants.TAG_NAMESPACE)
public class CommunicationConf {
	
	private List<TypeConf>		types;
	
	private List<ActionConf>	actions;
	
	
	public List<TypeConf> getTypesConf() {
		return this.types;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_COMMUNICATION_TYPES)
	@XmlElement(name = Constants.TAG_COMMUNICATION_TYPE)
	public void setTypesConf(List<TypeConf> types) {
		this.types = types;
	}
	
	
	public List<ActionConf> getActionsConf() {
		return this.actions;
	}
	
	
	@XmlElementWrapper(name = Constants.TAG_COMMUNICATION_ACTIONS)
	@XmlElement(name = Constants.TAG_COMMUNICATION_ACTION)
	public void setActionsConf(List<ActionConf> actions) {
		this.actions = actions;
	}
}