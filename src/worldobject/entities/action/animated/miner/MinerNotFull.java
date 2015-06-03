package worldobject.entities.action.animated.miner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Load;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Storm;
import worldobject.entities.action.Ore;


public class MinerNotFull
extends Miner
{

	public MinerNotFull(String name, int resource_limit, Point position,
			long rate, List<PImage> imgs, long animation_rate)
	{
		super(name, resource_limit, position, rate, imgs, animation_rate);
		// TODO Auto-generated constructor stub
	}
	
	public int minerToOre(WorldModel world, Ore ore)
	{
		if (ore ==null)
		{
			LinkedList<Point> fail= new LinkedList<>();
			fail.add(this.getPosition());
			this.setDrawPath(fail);
			return 0;
		}
		if (this.getPosition().adjacent(ore.getPosition()))
		{
			this.setResourceCount(1 + this.getResourceCount());
			ore.removeEntity(world);
			return 1;
		}
	
		else
		{
			HashSet<Point> bad = this.neighborStorms(world);
				if (bad.isEmpty()==false)
				{
					return 2;
				}
	
			Point newPt= this.aStar(ore.getPosition(), world).getFirst();
			world.move_entity(this, newPt);
			return 0;
		}
	}
	
	public Miner tryTransformMinerNotFull(WorldModel world)
	{
		if (this.getResourceCount() <= this.getResourceLimit()+1)
		{
			return this;
		}
		
		else
		{
			Miner new_entity = new MinerFull(
					this.getName(), this.getResourceLimit(),
					this.getPosition(), this.getRate(),
					this.getImages(), this.getAnimationRate());
			return new_entity;
		}
	}

	public int startAction(WorldModel world)
	{
		Ore ore = (Ore) world.find_nearest(this.getPosition(), Types.ORE);
		return this.minerToOre(world, ore);
	}

	
	public Miner transformType(WorldModel world)
	{
		return this.tryTransformMinerNotFull(world);
	}

	
	public Miner transformStormMiner(WorldModel world)
	{
		Miner new_entity = new MinerStorm(
				this.getName(), this.getResourceLimit(),
				this.getPosition(), this.getRate(),
				Load.STORM_MINER_IMG, this.getAnimationRate());
		return new_entity;
	}
}
