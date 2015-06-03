package worldloaders;

import projdata.Point;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.animated.AnimatedEntity;
import worldobject.entities.action.animated.Blob;
import worldobject.entities.action.animated.Quake;
import worldobject.entities.action.animated.miner.Miner;

public class Schedules
{
	public static int BLOB_RATE_SCALE = 4;
	public static int BLOB_ANIMATION_RATE_SCALE = 50;
	public static int BLOB_ANIMATION_MIN = 1;
	public static int BLOB_ANIMATION_MAX = 3;
    
	public static int WYVERN_RATE = 500;
	public static int WYVERN_ANIMATION_RATE = 100;
	
	public static int ORE_CORRUPT_MIN = 20000;
	public static int ORE_CORRUPT_MAX = 30000;
           
	public static int QUAKE_STEPS = 10;
	public static int QUAKE_DURATION = 1100;
	public static int QUAKE_ANIMATION_RATE = 100;
	
	public static int LIGHTNING_STEPS = 10;
	public static int LIGHTNING_DURATION = 600;
	public static int LIGHTNING_ANIMATION_RATE = 100;
        
	public static int FLAME_STEPS = 7;
	public static int FLAME_DURATION = 700;
	public static int FLAME_ANIMATION_RATE = 100;
	
	public static int VEIN_SPAWN_DELAY = 400;
	public static int VEIN_RATE_MIN = 8000;
	public static int VEIN_RATE_MAX = 17000;

	
	
	
	
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
		
		entity.addPendingAction(thingtodo);
		world.scheduleAction(thingtodo, time);
	}
	
	
	public static void scheduleMinerAnimation(WorldModel world, Miner entity)
	{
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
	
	
}

