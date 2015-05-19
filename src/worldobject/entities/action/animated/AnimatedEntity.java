package worldobject.entities.action.animated;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldmodel.WorldModel;

public class AnimatedEntity
extends worldobject.entities.action.Actionable
{
	private long animationRate;
	public AnimatedEntity(String name, Point position, long rate, long animationRate, List<PImage> imgs)
	{
		super(name, position, rate, imgs);
		this.animationRate= animationRate;
	}
	
	public long getAnimationRate()
	{
		return this.animationRate;
	}
	
	 public Action createAnimationAction(WorldModel world, int repeatCount) {
	        Action[] func = {null};
	        func[0] = (long ticks) -> {
	            this.removePendingAction(func[0]);
	            this.nextImage();

	            if (repeatCount != 1) {
	                this.scheduleAction(world, this.createAnimationAction(world, Math.max(repeatCount - 1, 0)), ticks+ this.getAnimationRate());
	            }
	        };
	        return func[0];
	    }
	
	 public void schedule(WorldModel world, Action action, long time) {
	        this.addPendingAction(action);
	        this.scheduleAnimation(world, 0);
	        world.scheduleAction(action, time);
	    }

	    public void scheduleAnimation(WorldModel world, int repeatCount) {
	        this.scheduleAction(world, this.createAnimationAction(world, repeatCount), this.getAnimationRate());
	    }
	 
	public Types getType()
	{
		return Types.AnimatedEntity;
	}
}
