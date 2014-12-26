package gloderss.output;

public class ExtortionOutputEntity extends AbstractEntity {
	
	public enum Field {
		TIME("time", DataType.DOUBLE),
		EXTORTION_ID("extortionId", DataType.INTEGER),
		ENTREPRENEUR_ID("entrepreneurId", DataType.INTEGER),
		MAFIOSO_ID("mafiosoId", DataType.INTEGER),
		MAFIA_EXTORTION("mafiaExtortion", DataType.DOUBLE),
		MAFIA_PUNISHMENT("mafiaPunishment", DataType.DOUBLE),
		MAFIA_BENEFIT("mafiaBenefit", DataType.DOUBLE),
		PAID("paid", DataType.BOOLEAN),
		MAFIA_PUNISHED("mafiaPunished", DataType.BOOLEAN),
		MAFIA_BENEFITED("mafiaBenefited", DataType.BOOLEAN),
		MAFIA_BENEFITED_AMOUNT("mafiaBenefitedAmount", DataType.DOUBLE),
		DENOUNCED_EXTORTION("denouncedExtortion", DataType.BOOLEAN),
		DENOUNCED_PUNISHMENT("denouncedPunishment", DataType.BOOLEAN),
		INVESTIGATED_EXTORTION("investigatedExtortion", DataType.BOOLEAN),
		INVESTIGATED_PUNISHMENT("investigatedPunishment", DataType.BOOLEAN),
		MAFIOSO_CUSTODY("mafiosoCustody", DataType.BOOLEAN),
		MAFIOSO_CONVICTED("mafiosoConvicted", DataType.BOOLEAN);
		
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
	
	
	public ExtortionOutputEntity(int id, String separator) {
		super(id);
		this.separator = separator;
		this.entity = new Object[Field.values().length];
		
		this.entity[Field.TIME.ordinal()] = 0.0;
		this.entity[Field.EXTORTION_ID.ordinal()] = id;
		this.entity[Field.ENTREPRENEUR_ID.ordinal()] = -1;
		this.entity[Field.MAFIOSO_ID.ordinal()] = -1;
		this.entity[Field.MAFIA_EXTORTION.ordinal()] = 0.0;
		this.entity[Field.MAFIA_PUNISHMENT.ordinal()] = 0.0;
		this.entity[Field.MAFIA_BENEFIT.ordinal()] = 0.0;
		this.entity[Field.PAID.ordinal()] = false;
		this.entity[Field.MAFIA_PUNISHED.ordinal()] = false;
		this.entity[Field.MAFIA_BENEFITED.ordinal()] = false;
		this.entity[Field.MAFIA_BENEFITED_AMOUNT.ordinal()] = 0.0;
		this.entity[Field.DENOUNCED_EXTORTION.ordinal()] = false;
		this.entity[Field.DENOUNCED_PUNISHMENT.ordinal()] = false;
		this.entity[Field.INVESTIGATED_EXTORTION.ordinal()] = false;
		this.entity[Field.INVESTIGATED_PUNISHMENT.ordinal()] = false;
		this.entity[Field.MAFIOSO_CUSTODY.ordinal()] = false;
		this.entity[Field.MAFIOSO_CONVICTED.ordinal()] = false;
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