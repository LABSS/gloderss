package gloderss.engine.queue;

import gloderss.engine.event.Comparable;

public abstract class OrderedSet {
  
  public abstract void insert( Comparable event );
  
  
  public abstract Comparable removeFirst();
  
  
  public abstract int size();
  
  
  public abstract Comparable remove( Comparable event );
}