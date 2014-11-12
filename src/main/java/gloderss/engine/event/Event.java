package gloderss.engine.event;

import gloderss.engine.devs.AbstractEventSimulator;

public class Event extends AbstractEvent {
	
	private double									time;
	
	private EventHandler						eventHandler;
	
	private Object									command;
	
	private AbstractEventSimulator	simulator;
	
	
	public Event() {
	}
	
	
	public Event(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
		this.command = null;
	}
	
	
	public Event(EventHandler eventHandler, Object command) {
		this.eventHandler = eventHandler;
		this.command = command;
	}
	
	
	public Event(double time, EventHandler eventHandler, Object command) {
		this.time = time;
		this.eventHandler = eventHandler;
		this.command = command;
	}
	
	
	public double getTime() {
		return this.time;
	}
	
	
	public void setTime(double time) {
		this.time = time;
	}
	
	
	public void set(double time, Object command) {
		this.command = command;
		this.setTime(time);
	}
	
	
	public EventHandler getMessageHandler() {
		return this.eventHandler;
	}
	
	
	public void setMessageHandler(EventHandler messageHandler) {
		this.eventHandler = messageHandler;
	}
	
	
	public Object getCommand() {
		return this.command;
	}
	
	
	public void setCommand(Object command) {
		this.command = command;
	}
	
	
	public AbstractEventSimulator getSimulator() {
		return this.simulator;
	}
	
	
	public void setSimulator(AbstractEventSimulator simulator) {
		this.simulator = simulator;
	}
	
	
	@Override
	public void execute(AbstractEventSimulator simulator) {
		if((this.command != null) && (this.eventHandler != null)) {
			this.eventHandler.handleEvent(this);
		}
	}
	
	
	@Override
	public boolean lessThan(Comparable e) {
		if(e != null) {
			Event event = (Event) e;
			
			return this.time < event.getTime();
		}
		
		return false;
	}
}