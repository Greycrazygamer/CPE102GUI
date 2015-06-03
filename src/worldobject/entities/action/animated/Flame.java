package worldobject.entities.action.animated;

import java.util.HashSet;
import java.util.List;

import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.action.Ore;
import worldobject.entities.action.Vein;

public class Flame
extends AnimatedEntity
{
	
	public Flame(String name, Point position, List<PImage> imgs, int animationRate)
	{
		super(name, position, 0, animationRate, imgs);
			
		// TODO Auto-generated constructor stub
	}
	
	
	public Action createFlameAction(WorldModel world) {
        Action[] func = {null};
        func[0] = (ticks) -> {
        	Point temp= this.getPosition();
            this.removePendingAction(func[0]);
            this.removeEntity(world);
            Vein vein= world.createVein("vein", temp, ticks);
            world.add_entity(vein);
            
        };
        return func[0];
    }
	public void scheduleFlame(WorldModel world, long ticks)
	{
		this.scheduleAnimation(world, Schedules.FLAME_STEPS);
        this.scheduleAction(world, this.createFlameAction(world), ticks + Schedules.FLAME_DURATION);
	}
	
	public HashSet<Node> neighborNodes(Node current, Node goal, WorldModel world)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

