package gloderss.output;

public abstract class AbstractEntity {
	
	public enum EntityType {
		EXTORTION;
	}
	
	protected enum DataType {
		BOOLEAN,
		DOUBLE,
		INTEGER;
	}
	
	private int			entityId;
	
	private boolean	active;
	
	
	public AbstractEntity(int entityId) {
		this.entityId = entityId;
		this.active = false;
	}
	
	
	public int getEntityId() {
		return this.entityId;
	}
	
	
	public boolean isActive() {
		return this.active;
	}
	
	
	public void activate() {
		this.active = true;
	}
	
	
	public abstract void setValue(String fieldStr, Object value);
	
	
	public abstract Object getValue(String fieldStr);
	
	
	public abstract String getLine();
	
	
	public abstract String getHeader();
}