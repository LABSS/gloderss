package gloderss.engine.devs;

import gloderss.engine.event.AbstractEvent;
import gloderss.engine.queue.OrderedSet;

public class AbstractEventSimulator {
	
	protected OrderedSet	events;
	
	
	public void insert(AbstractEvent e) {
		this.events.insert(e);
	}
	
	
	public AbstractEvent cancel(AbstractEvent e) {
		return (AbstractEvent) this.events.remove(e);
	}
}