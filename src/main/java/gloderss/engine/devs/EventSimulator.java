package gloderss.engine.devs;

import gloderss.engine.event.Event;

public class EventSimulator extends AbstractEventSimulator {
	
	private double	time;
	
	
	public double now() {
		return this.time;
	}
	
	
	public void doAllEvents(double stop) {
		Event event;
		while((this.time < stop)
				&& ((event = (Event) this.events.removeFirst()) != null)) {
			this.time = event.getTime();
			event.execute(this);
		}
	}
}