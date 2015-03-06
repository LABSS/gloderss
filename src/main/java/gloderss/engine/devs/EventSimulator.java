package gloderss.engine.devs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gloderss.engine.event.Event;

public class EventSimulator extends AbstractEventSimulator {
	
	private final static Logger	logger	= LoggerFactory
																					.getLogger(EventSimulator.class);
	
	private double							time;
	
	
	public void init() {
		this.time = 0.0;
	}
	
	
	public double now() {
		return this.time;
	}
	
	
	public void doAllEvents(double stop) {
		Event event;
		while((this.time < stop)
				&& ((event = (Event) this.events.removeFirst()) != null)) {
			this.time = event.getTime();
			logger.debug(event.getCommand().toString());
			event.execute(this);
		}
	}
}