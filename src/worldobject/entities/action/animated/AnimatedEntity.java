package worldobject.entities.action.animated;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import processing.core.PImage;
import projdata.Grid;
import projdata.Node;
import projdata.PathGrid;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldmodel.WorldModel;

public abstract class AnimatedEntity
extends worldobject.entities.action.Actionable
{
	private long animationRate;
	public AnimatedEntity(String name, Point position, long rate, long animationRate, List<PImage> imgs)
	{
		super(name, position, rate, imgs);
		this.animationRate= animationRate;
	}
	
	public abstract HashSet<Node> neighborNodes(Node current, WorldModel world);
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
	
	public LinkedList<Node> aStar(Point start, Point goal, WorldModel world)
	{
		LinkedList<Point> closedset = new LinkedList<>();
		LinkedHashSet<Node> openset = new LinkedHashSet<>();
		Node current= null;
		HashMap<Node, Node> cameFrom = new HashMap<>();
		//Start Node
		openset.add(new Node(start, 0, start.distance_sq(goal)));
		
		while (openset.isEmpty() == false)
		{
			current = lowestF(openset);
			if (current.equals(goal))
			{
				return reconstructPath(cameFrom, current);
			}
			
			openset.remove(current);
			closedset.add(current);
			for (Node neighbor: this.neighborNodes(current, world))
			{
				if (closedset.contains(neighbor))
				{
					continue;
				}
				
				double tentativeG = current.getgValue() + 1;
				if (closedset.contains(neighbor) ==false || tentativeG < neighbor.getgValue())
				{
					cameFrom.put(neighbor, current);
					neighbor.setgValue(tentativeG);
					neighbor.setfValue(neighbor.getgValue() + neighbor.distance_sq(goal));
					
					if (openset.contains(neighbor) != true)
					{
						openset.add(neighbor);
					}
				}
			}
		}
		return null;
	}
	
	private LinkedList<Node> reconstructPath(HashMap<Node, Node>cameFrom, Node current)
	{
		LinkedList<Node> totalpath = new LinkedList<Node>();
		totalpath.add(current);
		while (cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			totalpath.add(current);
		}
		return totalpath;
	}
	public Node lowestF(LinkedHashSet<Node> openset)
	{
		double minval = 1000; 
		Node minNode= null;
		for (Node n: openset)
		{
			if (minval > n.getfValue())
			{
				minval = n.getfValue();
				minNode = n;
			}
				
		}
		return minNode;
	}
}
