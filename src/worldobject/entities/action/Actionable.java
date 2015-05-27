package worldobject.entities.action;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldmodel.WorldModel;

public class Actionable
extends worldobject.entities.Entity
{
	private List<Action> pendingActions;
	private long rate;
	public Actionable(String name, Point position, long rate, List<PImage> imgs)
	{
		super(name, position, imgs);
		pendingActions = new LinkedList<>();
		this.rate = rate;
		
	}
	
	public long getRate()
	{
		return this.rate;
	}
	public void clearPendingActions()
	{
		 this.pendingActions.clear();
	}
	
	public void removePendingAction(Action thingtodo)
	{
		this.pendingActions.remove(thingtodo);
	}
	
	public void addPendingAction(Action thingtodo)
	{
		this.pendingActions.add(thingtodo);
	}
	
	public List<Action> getPendingActions()
	{
		return this.pendingActions;
	}
	
	 public void scheduleAction(WorldModel world, Action action, long ticks) 
	 {
	        this.addPendingAction(action);
	        world.scheduleAction(action, ticks);
	  }
	 
	 public void removeEntity(WorldModel world) 
	 {
		 for( Action a: this.getPendingActions())
			{
				world.unscheduleAction(a);
			}
	        this.clearPendingActions();
	        world.remove_entity(this);
	 }
	public Types getType()
	{
		return Types.ACTIONABLE;
	}
}
