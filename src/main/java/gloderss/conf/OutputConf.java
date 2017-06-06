package gloderss.conf;

import gloderss.Constants;
import javax.xml.bind.annotation.XmlElement;

public class OutputConf {
  
  private String  directory;
  
  private boolean append;
  
  private String  separator;
  
  private int     timeToWrite;
  
  
  public String getDirectory() {
    return this.directory;
  }
  
  
  @XmlElement(name = Constants.TAG_OUTPUT_DIRECTORY)
  public void setDirectory(String directory) {
    this.directory = directory;
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
  
  
  public int getTimeToWrite() {
    return this.timeToWrite;
  }
  
  
  @XmlElement(name = Constants.TAG_OUTPUT_TIME_TO_WRITE)
  public void setTimeToWrite(int timeToWrite) {
    this.timeToWrite = timeToWrite;
  }
}