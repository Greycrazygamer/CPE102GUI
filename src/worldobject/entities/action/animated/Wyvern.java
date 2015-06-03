package worldobject.entities.action.animated;

import java.util.HashSet;
import java.util.List;

import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldmodel.WorldModel;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Ore;
import worldobject.entities.action.Vein;

public class Wyvern
extends AnimatedEntity
{
	public Wyvern(String name, Point position, long rate, List<PImage> imgs, long animationRate)
	{
		super(name, position, rate, animationRate, imgs);
		
		// TODO Auto-generated constructor stub
	}
	
	public Types getType()
	{
		return Types.WYVERN;
	}
	
	public boolean wyvernToAnything(WorldModel world, Actionable target)
	{	
		if (target == null)
		{
			return false;
		}
		if (this.getPosition().adjacent(target.getPosition()))
		{
			return true;
		}
		else
		{
			Point newPt= this.aStar(target.getPosition(), world).getFirst();
			world.move_entity(this, newPt);
			return false;
		}
		
	}
	public Action createWyvernAction(WorldModel world)
	{
		Action[] func = {null};
		func[0] = (long current_ticks) ->
		{
			this.removePendingAction(func[0]);
			
			Actionable target = (Actionable) world.find_nearests(getPosition(), Types.MINERFULL, Types.BLOB);
			boolean found= this.wyvernToAnything(world, target);
			
			long nextTime = current_ticks + this.getRate();
			if(found)
			{
				Flame flame= world.createFlame(target.getPosition(), current_ticks);
				target.removeEntity(world);
				world.add_entity(flame);
				nextTime = current_ticks + this.getRate();
			}
			
			this.scheduleAction(world, this.createWyvernAction(world), nextTime);
			
		};
		
		return func[0];
	}
	
	public void scheduleWyvern(WorldModel world, long ticks)
	{	
		this.scheduleAnimation(world, 0);
		this.scheduleAction(world, this.createWyvernAction(world), ticks);
	}

	public HashSet<Node> neighborNodes(Node current, Node goal, WorldModel world)
	{
		HashSet<Node> temp = new HashSet<>();
		double newG= current.getgValue() +1;
		Node UP = new Node(current.getX(), current.getY()-1, newG, 0);
		UP.setfValue(UP.getgValue()+ UP.distance_sq(goal));
		Node DOWN= new Node(current.getX(), current.getY()+1, current.getgValue()+1, 0);
		DOWN.setfValue(DOWN.getgValue()+ DOWN.distance_sq(goal));
		Node RIGHT= new Node(current.getX()+1, current.getY(), current.getgValue()+1, 0);
		RIGHT.setfValue(RIGHT.getgValue()+ RIGHT.distance_sq(goal));
		Node LEFT= new Node(current.getX()-1, current.getY(), current.getgValue()+1, 0);
		LEFT.setfValue(LEFT.getgValue()+ LEFT.distance_sq(goal));
		if (world.within_bounds(UP) && world.is_empty(UP)|| UP.equals(goal))

				temp.add(UP);
		if (world.within_bounds(DOWN) && world.is_empty(DOWN)|| DOWN.equals(goal))

				temp.add(DOWN);
		if (world.within_bounds(RIGHT) && world.is_empty(RIGHT))

			temp.add(RIGHT);
		if (world.within_bounds(LEFT) && world.is_empty(LEFT)|| LEFT.equals(goal))
			temp.add(LEFT);
		return temp;
	}
	
}