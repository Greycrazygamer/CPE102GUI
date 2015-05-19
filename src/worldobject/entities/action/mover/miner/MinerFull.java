package worldobject.entities.action.mover.miner;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Blacksmith;
import worldobject.entities.action.mover.Ore;


public class MinerFull
extends Miner
{

	public MinerFull(String name, int resource_limit, Point position, int rate,
			List<PImage> imgs, long animation_rate)
	{
		super(name, resource_limit, position, rate, imgs, animation_rate);
		// TODO Auto-generated constructor stub
	}

	public boolean minerToSmith(WorldModel world, Blacksmith smith)
	{
		Point entity_pt = this.getPosition();
		if (smith == null)
		{
			return true;
		}
		Point smith_pt = smith.getPosition();
		if (entity_pt.adjacent(smith_pt))
		{
			smith.set_resource_count(smith.get_resource_count() + this.getResourceCount());
			this.setResourceCount(0);
			return true;
		}
		else
		{
			Point newPt= this.nextPositon(world, smith_pt);
			return false;
		}
	}
	
	public MinerNotFull tryTransformMinerFull(WorldModel world)
	{
		MinerNotFull new_entity= new MinerNotFull(this.getName(), this.getResourceLimit(),
				this.getPosition(), this.getRate(), this.getImages(), this.getAnimationRate());
		
		return new_entity;
	}

	
	public boolean startAction(WorldModel world)
	{
		Blacksmith smith = (Blacksmith) world.find_nearest(this.getPosition(), Types.BLACKSMITH);
		return this.minerToSmith(world, smith);
	}

	
	public Miner transformType(WorldModel world)
	{
		return this.tryTransformMinerFull(world);
	}
	

}
