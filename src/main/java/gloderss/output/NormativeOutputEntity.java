package gloderss.output;

public class NormativeOutputEntity extends AbstractEntity {
  
  public enum Field {
    TIME("time", DataType.DOUBLE),
    AGENT_ID("agentId", DataType.INTEGER),
    NUMBER_CONSUMERS("numberConsumers", DataType.INTEGER),
    NUMBER_ENTREPRENEURS("numberEntrepreneurs", DataType.INTEGER);
    
    private String   name;
    
    private DataType type;
    
    
    Field(String name, DataType type) {
      this.name = name;
      this.type = type;
    }
    
    
    public String getName() {
      return this.name;
    }
    
    
    public DataType getType() {
      return this.type;
    }
  }
  
  private String   separator;
  
  private Object[] entity;
  
  
  public NormativeOutputEntity(int id, String separator) {
    super(id);
    this.separator = separator;
    this.entity = new Object[Field.values().length];
    
    this.entity[Field.TIME.ordinal()] = 0.0;
    this.entity[Field.AGENT_ID.ordinal()] = -1;
    this.entity[Field.NUMBER_CONSUMERS.ordinal()] = 0.0;
    this.entity[Field.NUMBER_ENTREPRENEURS.ordinal()] = 0.0;
  }
  
  
  @Override
  public void setValue(String fieldStr, Object value) {
    
    Field field = Field.valueOf(fieldStr);
    
    if(field.getType().equals(DataType.BOOLEAN)) {
      this.entity[field.ordinal()] = (Boolean) value;
      
    } else if(field.getType().equals(DataType.DOUBLE)) {
      this.entity[field.ordinal()] = (Double) value;
      
    } else if(field.getType().equals(DataType.INTEGER)) {
      this.entity[field.ordinal()] = (Integer) value;
      
    } else if(field.getType().equals(DataType.STRING)) {
      this.entity[field.ordinal()] = (String) value;
      
    }
  }
  
  
  @Override
  public Object getValue(String fieldStr) {
    return this.entity[Field.valueOf(fieldStr).ordinal()];
  }
  
  
  @Override
  public String getLine() {
    String line = new String();
    
    Object value;
    for(Field field : Field.values()) {
      value = this.entity[field.ordinal()];
      if(value == null) {
        value = (String) "";
      } else if(field.getType().equals(DataType.DOUBLE)) {
        value = String.format("%.2f", value);
      }
      line += value + this.separator;
    }
    line = line.substring(0, line.length() - 1);
    
    return line;
  }
  
  
  @Override
  public String getHeader() {
    String header = new String();
    
    for(Field field : Field.values()) {
      header += field.getName() + this.separator;
    }
    header = header.substring(0, header.length() - 1);
    
    return header;
  }
}