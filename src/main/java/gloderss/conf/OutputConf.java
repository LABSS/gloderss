package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class OutputConf {
	
	private String	directory;
	
	private String	filename;
	
	private boolean	append;
	
	private String	separator;
	
	private int			writeFrequency;
	
	
	public String getDirectory() {
		return this.directory;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT_DIRECTORY)
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	
	public String getFilename() {
		return this.filename;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT_FILENAME)
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public boolean getAppend() {
		return this.append;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT_APPEND)
	public void setAppend(boolean append) {
		this.append = append;
	}
	
	
	public String getSeparator() {
		return this.separator;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT_SEPARATOR)
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	
	public int getWriteFrequency() {
		return this.writeFrequency;
	}
	
	
	@XmlElement(name = Constants.TAG_OUTPUT_WRITE_FREQUENCY)
	public void setWriteFrequency(int writeFrequency) {
		this.writeFrequency = writeFrequency;
	}
}