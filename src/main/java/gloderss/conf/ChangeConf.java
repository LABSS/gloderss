package gloderss.conf;

import javax.xml.bind.annotation.XmlElement;
import gloderss.Constants;

public class ChangeConf {
	
	private double	time;
	
	private String	parameter;
	
	private String	value;
	
	
	public double getTime() {
		return this.time;
	}
	
	
	@XmlElement(name = Constants.TAG_CHANGE_TIME)
	public void setTime(double time) {
		this.time = time;
	}
	
	
	public String getParameter() {
		return this.parameter;
	}
	
	
	@XmlElement(name = Constants.TAG_CHANGE_PARAMETER)
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
	public String getValue() {
		return this.value;
	}
	
	
	@XmlElement(name = Constants.TAG_CHANGE_VALUE)
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String toString() {
		String str;
		
		str = "[Time = " + this.time + "] [Parameter = " + this.parameter
				+ "] [Value = " + this.value + "]";
		
		return str;
	}
}