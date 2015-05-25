package worldobject.entities.action.animated;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import processing.core.PImage;
import projdata.Grid;
import projdata.Node;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldmodel.WorldModel;

public abstract class AnimatedEntity
extends worldobject.entities.action.Actionable
{
	private long animationRate;
	private LinkedList<Point> drawPath= new LinkedList<>();
	private HashSet<Point> searchPath= new HashSet<>();
	
	public AnimatedEntity(String name, Point position, long rate, long animationRate, List<PImage> imgs)
	{
		super(name, position, rate, imgs);
		this.animationRate= animationRate;
	}
	
	public abstract HashSet<Node> neighborNodes(Node current, Node goal, WorldModel world);
	public LinkedList<Point> getDrawPath()
	{
		return this.drawPath;
	}
	public HashSet<Point> getSearchPath()
	{
		return this.searchPath;
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
	
	public LinkedList<Point> aStar(Point ptgoal, WorldModel world)
	{
//		System.out.println("Goal: " + ptgoal.printXY());
		LinkedHashSet<Node> closedset = new LinkedHashSet<>();
		LinkedHashSet<Node> openset = new LinkedHashSet<>();
		LinkedList<Point> failure= new LinkedList<Point>();
		Node goal = new Node(ptgoal, 0, 0);
		Node current= new Node(this.getPosition(), 0, getPosition().distance_sq(goal));
		HashMap<Node, Node> cameFrom = new HashMap<>();
		//Start Node
		openset.add(current);
		searchPath.add(current);
		
		while (openset.isEmpty() == false)
		{
//			PRINT DEBUGS
//			System.out.println("Open");
//			this.NodePrint(openset, "XY");
//			this.NodePrint(openset, "F");
			
			current = lowestF(openset, 150000);
//			PRINT DEBUGS
//			System.out.print("Position: " +this.getPosition().printXY()+" ");
//			System.out.print("Current: "+ current.printXY()+ "F: " +current.getfValue());
//			System.out.println("Goal" + goal.printXY());
			if(current == null)
			{
				System.out.println(this.getName());
			}
			if (current.equals(goal))
			{
				return reconstructPath(cameFrom, current);
			}
			
			openset.remove(current);
			closedset.add(current);
			HashSet<Node> neighbors = this.neighborNodes(current, goal, world);
			
//			PRINT DEBUGS
//			System.out.println("Neigh");
//			this.NodePrint(neighbors, "XY");
//			this.NodePrint(neighbors, "F");
//			System.out.println();
			for (Node neighbor: neighbors)
			{
				if (closedset.contains(neighbor))
				{
					continue;
				}
				
				double tentativeG = current.getgValue() + 1;
				if (closedset.contains(neighbor) ==  false || tentativeG < neighbor.getgValue())
				{
					cameFrom.put(neighbor, current);
					neighbor.setgValue(tentativeG);
					neighbor.setfValue(neighbor.getgValue() + neighbor.distance_sq(goal));
					
					if (openset.contains(neighbor) != true)
					{
						openset.add(neighbor);
						searchPath.add(neighbor);
					}
				}
			}
		}
		failure.add(getPosition());
		this.drawPath= failure;
		return failure;
	}
	
	private LinkedList<Point> reconstructPath(HashMap<Node, Node>cameFrom, Node current)
	{
		LinkedList<Point> totalpath = new LinkedList<Point>();
		totalpath.add(current);
		while (cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			totalpath.add(current.convertToPoint());
		}
			totalpath.removeLast();
			this.drawPath= totalpath;
			Collections.reverse(drawPath);
			return totalpath;
		
	}
	public Node lowestF(LinkedHashSet<Node> openset, double inital)
	{
		double minval = inital; 
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
	
	public void APrint()
	{
		System.out.print("(X,Y):");
			for (Point n: this.drawPath)
			{
				System.out.print(n.printXY());
			}
			System.out.println("<end>");
		
	}
	
	public void NodePrint(LinkedHashSet<Node> set, String value)
	{
		if (value== "X")
		{
			System.out.print("X: ");
			for (Node n: set)
			{
				System.out.print(n.getX() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "Y")
		{
			System.out.print("Y: ");
			for (Node n: set)
			{
				System.out.print(n.getY() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "XY")
		{
			System.out.print("(X,Y):");
			for (Node n: set)
			{
				System.out.print(n.printXY());
			}
			System.out.println("<end>");
		}
		if (value== "G")
		{
			System.out.print("G: ");
			for (Node n: set)
			{
				System.out.print(n.getgValue() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "F")
		{
			for (Node n: set)
			{
				System.out.print("F: "+n.getfValue() + " ");
			}
			System.out.println("<end>");
		}
	}
	
	public void NodePrint(HashSet<Node> set, String value)
	{
		if (value== "X")
		{
			System.out.print("X: ");
			for (Node n: set)
			{
				System.out.print(n.getX() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "Y")
		{
			System.out.print("Y: ");
			for (Node n: set)
			{
				System.out.print(n.getY() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "XY")
		{
			System.out.print("(X,Y):");
			for (Node n: set)
			{
				System.out.print(n.printXY());
			}
			System.out.println("<end>");
		}
		if (value== "G")
		{
			System.out.print("G: ");
			for (Node n: set)
			{
				System.out.print(n.getgValue() + " ");
			}
			System.out.println("<end>");
		}
		if (value== "F")
		{
			for (Node n: set)
			{
				System.out.print("F: "+n.getfValue() + " ");
			}
			System.out.println("<end>");
		}
	}
}
