package gloderss.engine.event;

import gloderss.engine.devs.AbstractEventSimulator;

public abstract class AbstractEvent implements Comparable {
	
	public abstract void execute(AbstractEventSimulator simulator);
}