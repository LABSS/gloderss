package gloderss.output;

import gloderss.Constants;
import gloderss.conf.OutputConf;
import gloderss.engine.devs.EventSimulator;
import gloderss.engine.event.Event;
import gloderss.engine.event.EventHandler;
import gloderss.output.AbstractEntity.EntityType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class OutputController implements EventHandler {
	
	private static OutputController												instance;
	
	private EventSimulator																simulator;
	
	private int																						replication;
	
	private String																				directory;
	
	private boolean																				append;
	
	private String																				separator;
	
	private int																						writeFrequency;
	
	private Map<EntityType, BufferedWriter>								file;
	
	private Map<EntityType, Integer>											entityId;
	
	private Map<EntityType, Map<Integer, AbstractEntity>>	entities;
	
	private Map<EntityType, Boolean>											firstWrite;
	
	
	public OutputController(EventSimulator simulator, OutputConf conf) {
		this.simulator = simulator;
		
		this.directory = conf.getDirectory();
		this.append = conf.getAppend();
		this.separator = conf.getSeparator();
		this.writeFrequency = conf.getWriteFrequency();
		
		this.replication = -1;
		
		instance = this;
	}
	
	
	public void initializeSim() {
		Event event = new Event(this.simulator.now() + this.writeFrequency, this,
				Constants.EVENT_WRITE_DATA);
		this.simulator.insert(event);
	}
	
	
	public static OutputController getInstance() {
		return instance;
	}
	
	
	public void newInstance(int replica) {
		if(this.replication != replica) {
			this.file = new HashMap<EntityType, BufferedWriter>();
			this.entities = new LinkedHashMap<EntityType, Map<Integer, AbstractEntity>>();
			this.entityId = new HashMap<EntityType, Integer>();
			this.firstWrite = new HashMap<EntityType, Boolean>();
			
			this.replication = replica;
		}
	}
	
	
	public void init(EntityType type, String filename) {
		
		if(!this.file.containsKey(type)) {
			File dir = new File(this.directory + File.separator + this.replication);
			dir.mkdirs();
			try {
				BufferedWriter pFile = new BufferedWriter(new FileWriter(new File(
						this.directory + File.separator + this.replication + File.separator
								+ filename), this.append));
				this.file.put(type, pFile);
				
				Map<Integer, AbstractEntity> typeEntities = new HashMap<Integer, AbstractEntity>();
				this.entities.put(type, typeEntities);
				
				this.entityId.put(type, 0);
				this.firstWrite.put(type, true);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Collection<AbstractEntity> getEntities(EntityType type) {
		if(this.entities.containsKey(type)) {
			return this.entities.get(type).values();
		}
		
		return new LinkedList<AbstractEntity>();
	}
	
	
	public synchronized AbstractEntity getEntity(EntityType type) {
		AbstractEntity entity = null;
		
		if(this.entityId.containsKey(type)) {
			
			int id = this.entityId.get(type);
			switch(type) {
				case EXTORTION:
					entity = new ExtortionOutputEntity(id, this.separator);
					break;
			}
			
			if(entity != null) {
				
				Map<Integer, AbstractEntity> typeEntities;
				if(this.entities.containsKey(type)) {
					typeEntities = this.entities.get(type);
				} else {
					typeEntities = new HashMap<Integer, AbstractEntity>();
				}
				
				typeEntities.put(id, entity);
				this.entities.put(type, typeEntities);
				this.entityId.put(type, id + 1);
			}
		}
		
		return entity;
	}
	
	
	public AbstractEntity getEntity(EntityType type, int id) {
		AbstractEntity record = null;
		
		if(this.entities.containsKey(type)) {
			Map<Integer, AbstractEntity> typeEntities = this.entities.get(type);
			if(typeEntities.containsKey(id)) {
				record = typeEntities.get(id);
			}
		}
		
		return record;
	}
	
	
	public void setEntity(EntityType type, AbstractEntity entity) {
		int id = entity.getEntityId();
		
		Map<Integer, AbstractEntity> typeEntities;
		if(this.entities.containsKey(type)) {
			typeEntities = this.entities.get(type);
		} else {
			typeEntities = new HashMap<Integer, AbstractEntity>();
		}
		
		typeEntities.put(id, entity);
		this.entities.put(type, typeEntities);
	}
	
	
	public void write() throws IOException {
		
		for(EntityType type : EntityType.values()) {
			
			Map<Integer, AbstractEntity> typeEntities = this.entities.remove(type);
			
			if((typeEntities != null) && (!typeEntities.isEmpty())) {
				
				for(Integer id : typeEntities.keySet()) {
					if(this.firstWrite.get(type)) {
						this.file.get(type).write(typeEntities.get(id).getHeader());
						this.file.get(type).newLine();
						this.firstWrite.put(type, false);
					}
					
					if(typeEntities.get(id).isActive()) {
						AbstractEntity entity = typeEntities.remove(id);
						this.file.get(type).write(entity.getLine());
						this.file.get(type).newLine();
					}
				}
				
				this.entities.put(type, typeEntities);
				this.file.get(type).flush();
			}
		}
	}
	
	
	public void close() throws IOException {
		this.write();
		
		for(EntityType type : EntityType.values()) {
			this.file.get(type).close();
		}
	}
	
	
	@Override
	public void handleEvent(Event event) {
		
		switch((String) event.getCommand()) {
			case Constants.EVENT_WRITE_DATA:
				try {
					this.write();
				} catch(IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}
}