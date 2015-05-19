package worldobject.entities.action.animated;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.action.Actionable;


public class Quake
extends AnimatedEntity
{
	
	public Quake(String name, Point position, List<PImage> imgs, int animationRate)
	{
		super(name, position, 0, animationRate, imgs);
			
		// TODO Auto-generated constructor stub
	}
	
	
	public Action createQuakeAction(WorldModel world) {
        Action[] func = {null};
        func[0] = (ticks) -> {
            this.removePendingAction(func[0]);
            this.removeEntity(world);
        };
        return func[0];
    }
	public void scheduleQuake(WorldModel world, long ticks)
	{
		this.scheduleAnimation(world, Schedules.QUAKE_STEPS);
        this.scheduleAction(world, this.createQuakeAction(world), ticks + Schedules.QUAKE_DURATION);
	}
}
