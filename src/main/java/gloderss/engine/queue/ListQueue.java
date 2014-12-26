package gloderss.engine.queue;

import gloderss.engine.event.Comparable;
import java.util.Vector;

public class ListQueue extends OrderedSet {
	
	private Vector<Comparable>	events	= new Vector<Comparable>();
	
	
	/**
	 * Insert a new event into the queue
	 * 
	 * @param event
	 *          Event
	 * @return none
	 */
	public synchronized void insert(Comparable event) {
		int i = 0;
		while(i < this.events.size()
				&& ((Comparable) this.events.elementAt(i)).lessThan(event)) {
			i++;
		}
		this.events.insertElementAt(event, i);
	}
	
	
	/**
	 * Remove the first event of the queue
	 * 
	 * @param none
	 * @return Event
	 */
	public synchronized Comparable removeFirst() {
		
		if(this.events.size() != 0) {
			Comparable event = (Comparable) this.events.firstElement();
			this.events.removeElementAt(0);
			
			return event;
		}
		
		return null;
	}
	
	
	/**
	 * Remove a specific event from the queue
	 * 
	 * @param event
	 *          Event to remove
	 * @return Event removed
	 */
	public synchronized Comparable remove(Comparable event) {
		
		for(int i = 0; i < this.events.size(); i++) {
			
			if(this.events.elementAt(i).equals(event)) {
				event = this.events.elementAt(i);
				this.events.removeElementAt(i);
				
				return (Comparable) event;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Get the number of events in the queue
	 * 
	 * @param none
	 * @return Number of events in the queue
	 */
	public int size() {
		return this.events.size();
	}
}