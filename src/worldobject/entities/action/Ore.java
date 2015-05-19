package worldobject.entities.action;
import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.action.animated.Blob;


public class Ore
extends Actionable
{
	public Ore(String name, Point position, List<PImage> imgs, long rate)
	{
		super(name, position, rate, imgs);
		// TODO Auto-generated constructor stub
	}
	
	public Types getType()
	{
		return Types.ORE;
	}
	
	public Action createOreTransformAction(WorldModel world)
	{
		
		
		Action[] func = {null};
		func[0] = (long current_ticks) ->
		{
			
			this.removePendingAction(func[0]);
			//System.out.println("BlobCreated");
			Blob blob = world.createBlob(getName() + " -- blob", 
					this.getPosition(), 
					this.getRate() / Schedules.BLOB_RATE_SCALE, 
					current_ticks);
			
			Schedules.removeEntity(world, this);
			world.add_entity(blob);
		};
		return func[0];
	}
	
	public void scheduleOre(WorldModel world, long ticks)
	{
		System.out.println(ticks);
		System.out.println(this.getRate());
		Schedules.scheduleAction(world, this, 
				this.createOreTransformAction(world),
				ticks + this.getRate());
	}
	
	
}
