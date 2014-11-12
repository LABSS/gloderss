package gloderss.output;

import gloderss.conf.OutputConf;
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

public class OutputController {
	
	private static OutputController																instance;
	
	private static int																						replication;
	
	private static String																					directory;
	
	private static boolean																				append;
	
	private static String																					separator;
	
	private static Map<EntityType, BufferedWriter>								file;
	
	private static Map<EntityType, Integer>												entityId;
	
	private static Map<EntityType, Map<Integer, AbstractEntity>>	entities;
	
	private static Map<EntityType, Boolean>												firstWrite;
	
	
	public static OutputController getInstance(OutputConf conf, int replica) {
		if(instance == null) {
			instance = new OutputController();
			replication = replica;
			
			directory = conf.getDirectory();
			append = conf.getAppend();
			separator = conf.getSeparator();
			
			file = new HashMap<EntityType, BufferedWriter>();
			entities = new LinkedHashMap<EntityType, Map<Integer, AbstractEntity>>();
			entityId = new HashMap<EntityType, Integer>();
			firstWrite = new HashMap<EntityType, Boolean>();
		} else {
			if(replication != replica) {
				instance = new OutputController();
				replication = replica;
				
				directory = conf.getDirectory();
				append = conf.getAppend();
				separator = conf.getSeparator();
				
				file = new HashMap<EntityType, BufferedWriter>();
				entities = new LinkedHashMap<EntityType, Map<Integer, AbstractEntity>>();
				entityId = new HashMap<EntityType, Integer>();
				firstWrite = new HashMap<EntityType, Boolean>();
			}
		}
		
		return instance;
	}
	
	
	public static void init(EntityType type, String filename) {
		
		if(!file.containsKey(type)) {
			File dir = new File(directory + File.separator + replication);
			dir.mkdirs();
			try {
				BufferedWriter pFile = new BufferedWriter(new FileWriter(new File(
						directory + File.separator + replication + File.separator
								+ filename), append));
				file.put(type, pFile);
				
				Map<Integer, AbstractEntity> typeEntities = new HashMap<Integer, AbstractEntity>();
				entities.put(type, typeEntities);
				
				entityId.put(type, 0);
				firstWrite.put(type, true);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static Collection<AbstractEntity> getEntities(EntityType type) {
		if(entities.containsKey(type)) {
			return entities.get(type).values();
		}
		
		return new LinkedList<AbstractEntity>();
	}
	
	
	public synchronized static AbstractEntity getEntity(EntityType type) {
		AbstractEntity entity = null;
		
		if(entityId.containsKey(type)) {
			
			int id = entityId.get(type);
			switch(type) {
				case EXTORTION:
					entity = new ExtortionOutputEntity(id, separator);
					break;
			}
			
			if(entity != null) {
				
				Map<Integer, AbstractEntity> typeEntities;
				if(entities.containsKey(type)) {
					typeEntities = entities.get(type);
				} else {
					typeEntities = new HashMap<Integer, AbstractEntity>();
				}
				
				typeEntities.put(id, entity);
				entities.put(type, typeEntities);
				entityId.put(type, id + 1);
			}
		}
		
		return entity;
	}
	
	
	public static AbstractEntity getEntity(EntityType type, int id) {
		AbstractEntity record = null;
		
		if(entities.containsKey(type)) {
			Map<Integer, AbstractEntity> typeEntities = entities.get(type);
			if(typeEntities.containsKey(id)) {
				record = typeEntities.get(id);
			}
		}
		
		return record;
	}
	
	
	public static void setEntity(EntityType type, AbstractEntity entity) {
		int id = entity.getEntityId();
		
		Map<Integer, AbstractEntity> typeEntities;
		if(entities.containsKey(type)) {
			typeEntities = entities.get(type);
		} else {
			typeEntities = new HashMap<Integer, AbstractEntity>();
		}
		
		typeEntities.put(id, entity);
		entities.put(type, typeEntities);
	}
	
	
	public static void write() throws IOException {
		
		for(EntityType type : EntityType.values()) {
			
			Map<Integer, AbstractEntity> typeEntities = entities.remove(type);
			
			if((typeEntities != null) && (!typeEntities.isEmpty())) {
				
				for(Integer id : typeEntities.keySet()) {
					if(firstWrite.get(type)) {
						file.get(type).write(typeEntities.get(id).getHeader());
						file.get(type).newLine();
						firstWrite.put(type, false);
					}
					
					if(typeEntities.get(id).isActive()) {
						AbstractEntity entity = typeEntities.remove(id);
						file.get(type).write(entity.getLine());
						file.get(type).newLine();
					}
				}
				
				entities.put(type, typeEntities);
				file.get(type).flush();
			}
		}
	}
	
	
	public static void close() throws IOException {
		write();
		
		for(EntityType type : EntityType.values()) {
			file.get(type).close();
		}
	}
}