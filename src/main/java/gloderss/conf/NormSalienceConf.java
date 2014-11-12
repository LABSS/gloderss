package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Constants.TAG_NORMATIVE_NORM_SALIENCE)
public class NormSalienceConf {
	
	// Norm identification
	private int			id;
	
	// Initial salience
	private double	salience;
	
	
	public int getId() {
		return this.id;
	}
	
	
	@XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_ID)
	public void setId(int id) {
		this.id = id;
	}
	
	
	public double getSalience() {
		return this.salience;
	}
	
	
	@XmlAttribute(name = Constants.TAG_NORMATIVE_NORM_INITIAL_SALIENCE)
	public void setSalience(double salience) {
		this.salience = salience;
	}
}