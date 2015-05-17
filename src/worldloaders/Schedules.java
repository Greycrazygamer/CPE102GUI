package worldloaders;

import projdata.Point;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Quake;

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
           
	public static int VEIN_SPAWN_DELAY = 500;
	public static int VEIN_RATE_MIN = 8000;
	public static int VEIN_RATE_MAX = 17000;

	
	public static Action createEntityDeathAction(WorldModel world, Actionable entity)
	{
		Action<Long> creator= (Long ticks) -> 
		{
			//entity.removePendingAction();
			Point pt= entity.getPosition();
			world.remove_entity(entity);
			};
		return creator;
	}
	
	public static Action createAnimationAction(WorldModel world, Actionable entity, int repeat_count)
	{
		Action<Long> creator= (Long ticks) ->
		{
			Point[] temp= new Point[0];
			
			entity.nextImage();
			
			if (repeat_count != 1)
			{
				
			}
			
		};
		return creator;
	}
	
	public static Point findOpenAround(WorldModel world, Point pt, int distance)
	{
		for(int y=0; (-1*distance) < y && y<(distance+1); y++)
		{
			for(int x=0; (-1*distance) < y && y<(distance+1); x++)
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
	public static void scheduleAction(WorldModel world, Actionable entity, Action thingtodo, Long time)
	{
		entity.addPendingAction(thingtodo);
		world.scheduleAction(thingtodo, time);
	}
	
	public static void scheduleQuakeAnimation(WorldModel world, Quake entity, int repeat_count)
	{
		Schedules.scheduleAction(world, entity,
				Schedules.createAnimationAction(world, entity, repeat_count), 
				(long) entity.getAnimationRate());
	}
	
	public static void scheduleAnimation(WorldModel world, Quake entity)
	{
		Schedules.scheduleAction(world, entity,
				Schedules.createAnimationAction(world, entity, 0), 
				(long) entity.getAnimationRate());
	}
	
	public void clearPendingActions(WorldModel world, Actionable entity)
	{
		for (Action a: entity.getPendingActions())
		{
			world.unscheduleAction(a);
		}
		entity.clearPendingActions();
	}
	
	public void removeEntity(WorldModel world, Actionable entity)
	{
		for( Action a: entity.getPendingActions())
		{
			world.unscheduleAction(a);
		}
		entity.clearPendingActions();
		world.remove_entity(entity);
	}
}

