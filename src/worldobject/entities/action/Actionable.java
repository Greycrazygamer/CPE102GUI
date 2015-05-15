package worldobject.entities.action;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Action;
import projdata.Point;
import projdata.Types;

public class Actionable
extends worldobject.entities.Entity
{
	private List<Action> pendingActions;
	public Actionable(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
		
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
	public Types getType()
	{
		return Types.ACTIONABLE;
	}
}
