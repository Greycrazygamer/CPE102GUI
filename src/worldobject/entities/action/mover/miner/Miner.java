package worldobject.entities.action.mover.miner;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.action.mover.Ore;


public abstract class Miner
extends worldobject.entities.action.mover.Mover
{
	private int resource_limit;
	private int resource_count;
	private int animation_rate;
	
	public Miner(String name, int resource_limit, 
			Point position, int rate, List<PImage> imgs, int animation_rate)
	{
		super(name, position, rate, imgs);
		this.resource_limit= resource_limit;
		this.resource_count= 2;
		this.animation_rate= animation_rate;
		
	}

	public int getAnimationRate()
	{
		return this.animation_rate;
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
	
	public Miner tryTransformMiner(WorldModel world, Miner transformed_Entity)
	{
		Miner new_entity= transformed_Entity;
		if (this != new_entity)
		{
			Schedules.clearPendingActions(world, this);
			world.remove_entity_at(getPosition());
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
			
			Ore ore = (Ore) world.find_nearest(this.getPosition(), Types.ORE);
			boolean found = this.startAction(world);
			
			Miner new_entity = this;
			if (found)
			{
				new_entity= this.tryTransformMiner(world, transformType(world));
			}
			
			Schedules.scheduleAction(world, new_entity, 
					new_entity.createMinerAction(world), current_ticks + new_entity.getRate());
		};
			
		return func[0];
	}
	
	public abstract boolean startAction(WorldModel world);
	public abstract Miner transformType(WorldModel world);
	
}
