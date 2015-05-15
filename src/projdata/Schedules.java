package projdata;

import worldloaders.Controller;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Quake;

public class Schedules
{
	
	
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

