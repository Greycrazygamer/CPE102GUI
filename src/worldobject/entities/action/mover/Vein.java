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
	
	public Vein(String name, int rate, Point position, List<PImage> imgs)
	{
		super(name, position, rate, imgs);
		this.reach = 1;
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
	
	public Action createVeinAction(WorldModel world)
	{
		Action[] func = {null};
		func[0]= (long current_ticks) ->
		{
			this.removePendingAction(func[0]);
			List<Point> tiles= new ArrayList<>();
			Point openPt= Schedules.findOpenAround(world, this.getPosition(), this.getReach());
			if (openPt != null)
			{
				Ore ore =world.createOre("ore -" + this.getName() + " - " + current_ticks,
						openPt, current_ticks);
				world.add_entity(ore);
				tiles.add(openPt);
			}
			else
			{
				tiles.clear();
			}
			Schedules.scheduleAction(world, this, func[0], current_ticks + this.getRate());
			
		};
		return func[0];
		
	}
	
	public void scheduleVein(WorldModel world, long ticks)
	{
		Schedules.scheduleAction(world, this, this.createVeinAction(world), ticks + this.getRate());
	}
}
