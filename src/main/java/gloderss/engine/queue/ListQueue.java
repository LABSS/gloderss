package gloderss.engine.queue;

import gloderss.engine.event.Comparable;
import java.util.Vector;

public class ListQueue extends OrderedSet {
	
	private Vector<Comparable>	events	= new Vector<Comparable>();
	
	
	public void insert(Comparable event) {
		int i = 0;
		while(i < this.events.size()
				&& ((Comparable) this.events.elementAt(i)).lessThan(event)) {
			i++;
		}
		this.events.insertElementAt(event, i);
	}
	
	
	public Comparable removeFirst() {
		
		if(this.events.size() != 0) {
			Comparable event = (Comparable) this.events.firstElement();
			this.events.removeElementAt(0);
			
			return event;
		}
		
		return null;
	}
	
	
	public Comparable remove(Comparable event) {
		
		for(int i = 0; i < this.events.size(); i++) {
			
			if(this.events.elementAt(i).equals(event)) {
				event = this.events.elementAt(i);
				this.events.removeElementAt(i);
				
				return (Comparable) event;
			}
		}
		
		return null;
	}
	
	
	public int size() {
		return this.events.size();
	}
}