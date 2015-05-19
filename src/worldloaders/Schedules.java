package worldloaders;

import projdata.Point;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Quake;
import worldobject.entities.action.mover.Blob;
import worldobject.entities.action.mover.Mover;
import worldobject.entities.action.mover.miner.Miner;

public class Schedules
{
	public static int BLOB_RATE_SCALE = 4;
	public static int BLOB_ANIMATION_RATE_SCALE = 50;
	public static int BLOB_ANIMATION_MIN = 1;
	public static int BLOB_ANIMATION_MAX = 3;
          
	public static int ORE_CORRUPT_MIN = 20000;
	public static int ORE_CORRUPT_MAX = 30000;
           
	public static int QUAKE_STEPS = 10;
	public static int QUAKE_DURATION = 1100;
	public static int QUAKE_ANIMATION_RATE = 100;
           
	public static int VEIN_SPAWN_DELAY = 400;
	public static int VEIN_RATE_MIN = 8000;
	public static int VEIN_RATE_MAX = 17000;

	
	public static Action createEntityDeathAction(WorldModel world, Actionable entity)
	{
		Action[] func= {null};
		func[0]= (long ticks) -> 
		{
			entity.removePendingAction(func[0]);
			Point pt= entity.getPosition();
			world.remove_entity(entity);
			};
		return func[0];
	}
	
	
	
	public static Point findOpenAround(WorldModel world, Point pt, int distance)
	{
		for(int y=-1; y<=(distance); y++)
		{
			for(int x=-1; x<=(distance); x++)
			{
				Point newPt= new Point(pt.getX()+x, pt.getY()+y);
				if (world.within_bounds(newPt) && (!world.is_occupied(newPt)))
				{
					return newPt;
				}
			}
		}
		return null;
	}
	public static void scheduleAction(WorldModel world, Actionable entity, Action thingtodo, long time)
	{
		//System.out.println(time);
		entity.addPendingAction(thingtodo);
		world.scheduleAction(thingtodo, time);
	}
	
	public static void scheduleQuakeAnimation(WorldModel world, Quake entity, int repeat_count)
	{
		Schedules.scheduleAction(world, entity,
				Schedules.createQuakeAnimationAction(world, entity, repeat_count), 
				entity.getAnimationRate());
	}
	
	public static Action createQuakeAnimationAction(WorldModel world, Quake entity, int repeat_count)
	{
		Action[] func = {null};
		func[0]= (long ticks) ->
		{
			entity.removePendingAction(func[0]);
					
			entity.nextImage();
			
			if (repeat_count != 1)
			{
				Schedules.scheduleAction(world, entity, 
						Schedules.createQuakeAnimationAction(world, entity, Math.max(repeat_count-1, 0)), 
						ticks + entity.getAnimationRate());
			}
			
		};
		return func[0];
	}
	public static void scheduleBlobAnimation(WorldModel world, Blob entity)
	{
		Schedules.scheduleAction(world, entity,
				Schedules.createBlobAnimationAction(world, entity, 0), 
				 entity.getAnimationRate());
	}
	public static Action createBlobAnimationAction(WorldModel world, Blob entity, int repeat_count)
	{
		Action[] func = {null};
		func[0]= (long ticks) ->
		{
			entity.removePendingAction(func[0]);
					
			entity.nextImage();
			
			if (repeat_count != 1)
			{
				Schedules.scheduleAction(world, entity, 
						Schedules.createBlobAnimationAction(world, entity, Math.max(repeat_count-1, 0)), 
						ticks + entity.getAnimationRate());
			}
			
		};
		return func[0];
	}
	
	public static void scheduleMinerAnimation(WorldModel world, Miner entity)
	{
		System.out.println("MinerAnimationsScheduled");
		Schedules.scheduleAction(world, entity,
				Schedules.createMinerAnimationAction(world, entity, 0), 
				 entity.getAnimationRate());
	}
	
	public static Action createMinerAnimationAction(WorldModel world, Miner entity, int repeat_count)
	{
		Action[] func = {null};
		func[0]= (long ticks) ->
		{
			entity.removePendingAction(func[0]);
					
			entity.nextImage();
			
			if (repeat_count != 1)
			{
				Schedules.scheduleAction(world, entity, 
						Schedules.createMinerAnimationAction(world, entity, Math.max(repeat_count-1, 0)), 
						ticks + entity.getAnimationRate());
			}
			
		};
		return func[0];
	}
	public static void clearPendingActions(WorldModel world, Actionable entity)
	{
		for (Action a: entity.getPendingActions())
		{
			world.unscheduleAction(a);
		}
		entity.clearPendingActions();
	}
	
	public static void removeEntity(WorldModel world, Actionable entity)
	{
		for( Action a: entity.getPendingActions())
		{
			world.unscheduleAction(a);
		}
		entity.clearPendingActions();
		world.remove_entity(entity);
	}
}

