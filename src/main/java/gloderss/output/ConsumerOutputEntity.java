package gloderss.output;

public class ConsumerOutputEntity extends AbstractEntity {
	
	public enum Field {
		CONSUMER_ID("consumerId", DataType.INTEGER),
		ENTREPRENEUR_REP("entrepreneurRep", DataType.STRING),
		NUMBER_PRODUCTS("numberProducts", DataType.INTEGER),
		TOTAL_PRICE("totalPrice", DataType.DOUBLE),
		SALIENCE_BUY_FROM_PAYING_ENTREPRENEURS(
				"salienceBuyPayingEntrepreneurs",
				DataType.DOUBLE),
		SALIENCE_BUY_FROM_NOT_PAYING_ENTREPRENEURS(
				"salienceBuyNotPayingEntrepreneurs",
				DataType.DOUBLE),
		SALIENCE_PAY_EXTORTION("saliencePayExtortion", DataType.DOUBLE),
		SALIENCE_NOT_PAY_EXTORTION("salienceNotPayExtortion", DataType.DOUBLE),
		SALIENCE_DENOUNCE("salienceDenounce", DataType.DOUBLE),
		SALIENCE_NOT_DENOUNCE("salienceNotDenounce", DataType.DOUBLE);
		
		private String		name;
		
		private DataType	type;
		
		
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
	
	private String		separator;
	
	private Object[]	entity;
	
	
	public ConsumerOutputEntity(int id, String separator) {
		super(id);
		this.separator = separator;
		this.entity = new Object[Field.values().length];
		
		this.entity[Field.CONSUMER_ID.ordinal()] = -1;
		this.entity[Field.ENTREPRENEUR_REP.ordinal()] = "";
		this.entity[Field.NUMBER_PRODUCTS.ordinal()] = 0;
		this.entity[Field.TOTAL_PRICE.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_BUY_FROM_NOT_PAYING_ENTREPRENEURS.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_BUY_FROM_PAYING_ENTREPRENEURS.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_NOT_PAY_EXTORTION.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_PAY_EXTORTION.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_NOT_DENOUNCE.ordinal()] = 0.0;
		this.entity[Field.SALIENCE_DENOUNCE.ordinal()] = 0.0;
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