package worldobject.entities.action.animated.miner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Load;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Storm;
import worldobject.entities.action.Ore;


public abstract class Miner
extends worldobject.entities.action.animated.AnimatedEntity
{
	private int resource_limit;
	private int resource_count;
	
		
	public Miner(String name, int resource_limit, 
			Point position, long rate, List<PImage> imgs, long animation_rate)
	{
		super(name, position, rate, animation_rate, imgs);
		this.resource_limit= resource_limit;
		this.resource_count= 2;
			
	}
	
	public void setResourceCount(int n)
	{
		this.resource_count= n;
	}
	
	public int getResourceCount()
	{
		return this.resource_count;
	}
	
	public int getResourceLimit()
	{
		return this.resource_limit;
	}
	
	public Types getType()
	{
		return Types.MINER;
	}
	
	public Point nextPositon(WorldModel world, Point dest)
	{
		int horiz= this.sign(dest.getX() - this.getPosition().getX());
		Point new_pt = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
		
		if (horiz == 0 || world.is_occupied(new_pt))
		{
			int vert = this.sign(dest.getY() - this.getPosition().getY());
			new_pt= new Point(this.getPosition().getX(), this.getPosition().getY()+vert);
			
			if (vert ==0 || world.is_occupied(new_pt))
			{
				new_pt= new Point(this.getPosition().getX(), this.getPosition().getY());
			}
			
		}
		return new_pt;
	}
	
	public Miner tryTransformMiner(WorldModel world)
	{
		Miner new_entity= transformType(world);
		if (this != new_entity)
		{
			
			this.removeEntity(world);
			world.add_entity(new_entity);
			Schedules.scheduleMinerAnimation(world, new_entity);
		}
		
		return new_entity;
	}
	
	public Action createMinerAction(WorldModel world)
	{
		Action[] func = {null};
		func[0]= (long current_ticks) ->
		{
			
			this.removePendingAction(func[0]);
			
			int found = this.startAction(world);
			
			Miner new_entity = this;
			if (found==1)
			{
				new_entity= this.tryTransformMiner(world);
				
			}
			else if(found==2)
			{
				if (!(this instanceof MinerStorm) && Load.stormint < 7)
				{
					Load.stormint++;
					Point temp = this.getPosition();
					Miner stormy= this.transformStormMiner(world);
					Schedules.clearPendingActions(world, this);
					this.removeEntity(world);
					world.add_entity(world.createLightning(temp, stormy, current_ticks));
				}
			}
			
			long temp = current_ticks + new_entity.getRate();
			Schedules.scheduleAction(world, new_entity, 
					new_entity.createMinerAction(world), temp);
		};
			
		return func[0];
	}
	public void scheduleMiner(WorldModel world, long ticks)
	{
		Schedules.scheduleAction(world, this, this.createMinerAction(world), ticks + this.getRate());
		Schedules.scheduleMinerAnimation(world, this);
	}
	
	public HashSet<Point> neighborStorms(WorldModel world)
	{
		HashSet<Point> temp = new HashSet<>();
		Point UP = new Point(this.getPosition().getX(), this.getPosition().getY()-1);
		Point DOWN= new Point(this.getPosition().getX(), this.getPosition().getY()+1);
		Point RIGHT= new Point(this.getPosition().getX()+1, this.getPosition().getY());
		Point LEFT= new Point(this.getPosition().getX()-1, this.getPosition().getY());
		if (world.within_bounds(UP) && world.get_tile_occupant(UP) instanceof Storm)
				temp.add(UP);
		if (world.within_bounds(DOWN) && world.get_tile_occupant(DOWN) instanceof Storm)
			temp.add(DOWN);
		if (world.within_bounds(LEFT) && world.get_tile_occupant(LEFT) instanceof Storm)
			temp.add(LEFT);
		if (world.within_bounds(RIGHT) && world.get_tile_occupant(RIGHT) instanceof Storm)
			temp.add(RIGHT);
		return temp;
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
//				System.out.println("up");
				temp.add(UP);
		if (world.within_bounds(DOWN) && world.is_empty(DOWN)|| DOWN.equals(goal))
			temp.add(DOWN);
		if (world.within_bounds(RIGHT) && world.is_empty(RIGHT)|| RIGHT.equals(goal))
			temp.add(RIGHT);
		if (world.within_bounds(LEFT) && world.is_empty(LEFT)|| LEFT.equals(goal))
			temp.add(LEFT);
		return temp;
	}
	public abstract int startAction(WorldModel world);
	public abstract Miner transformType(WorldModel world);
	public abstract Miner transformStormMiner(WorldModel world);
	
}
