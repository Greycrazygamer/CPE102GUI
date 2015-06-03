package worldobject.entities.action.animated;

import java.util.HashSet;
import java.util.List;

import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.action.animated.miner.Miner;

public class Lightning
extends AnimatedEntity
{
	
	public Lightning(String name, Point position, List<PImage> imgs, int animationRate)
	{
		super(name, position, 0, animationRate, imgs);
	
	}
	
	
	public Action createLightningAction(WorldModel world, Miner replacement) {
        Action[] func = {null};
        func[0] = (ticks) -> {
            this.removePendingAction(func[0]);
            this.removeEntity(world);
            if (replacement !=null)
            {
            	world.createMinerStorm(replacement, ticks);
            }
            
        };
        return func[0];
    }
	public void scheduleLightning(WorldModel world, Miner replacement, long ticks)
	{
		this.scheduleAnimation(world, Schedules.LIGHTNING_STEPS);
        this.scheduleAction(world, this.createLightningAction(world, replacement), ticks+Schedules.LIGHTNING_DURATION);
	}
	
	public HashSet<Node> neighborNodes(Node current, Node goal, WorldModel world)
	{
		// TODO Auto-generated method stub
		return null;
	}
}