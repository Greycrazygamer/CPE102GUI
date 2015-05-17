package worldobject.entities.action.mover;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.runtime.FindProperty;
import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;


public class Vein
extends Mover
{

	private int reach;
	
	public Vein(String name, int rate, Point position, List<PImage> imgs, int reach)
	{
		super(name, position, rate, imgs);
		this.reach = reach;
		// TODO Auto-generated constructor stub
	}
	
	public Types getType()
	{
		return Types.VEIN;
	}
	
	public int getReach()
	{
		return this.reach;
	}
	
	public void createVeinAction(WorldModel world)
	{
		Action<Long> creator= (Long current_ticks) ->
		{
			//this.removePendingAction(creator);
			Point openPt= Schedules.findOpenAround(world, this.getPosition(), this.getReach());
			if (openPt != null)
			{
				
			}
		};
	}
}
