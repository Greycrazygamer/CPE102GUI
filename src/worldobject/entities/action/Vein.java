package worldobject.entities.action;

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
extends Actionable
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
			Point openPt= Schedules.findOpenAround(world, this.getPosition(), this.getReach());
			if (openPt != null)
			{
				System.out.println("OreCreated");
				Ore ore =world.createOre("ore -" + this.getName() + " - " + String.valueOf(current_ticks),
						openPt, current_ticks);
				world.add_entity(ore);
			}
			this.scheduleAction(world, this.createVeinAction(world), current_ticks + this.getRate());
			//Schedules.scheduleAction(world, this, this.createVeinAction(world), current_ticks + this.getRate());
			
		};
		return func[0];
		
	}
	
	public void scheduleVein(WorldModel world, long ticks)
	{
		
		Schedules.scheduleAction(world, this, this.createVeinAction(world), ticks + this.getRate());
	}
}
