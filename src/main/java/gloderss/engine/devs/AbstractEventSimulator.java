package gloderss.engine.devs;

import gloderss.engine.event.AbstractEvent;
import gloderss.engine.queue.OrderedSet;

public class AbstractEventSimulator {
	
	protected OrderedSet	events;
	
	
	public synchronized void insert(AbstractEvent e) {
		this.events.insert(e);
	}
	
	
	public synchronized AbstractEvent cancel(AbstractEvent e) {
		return (AbstractEvent) this.events.remove(e);
	}
}