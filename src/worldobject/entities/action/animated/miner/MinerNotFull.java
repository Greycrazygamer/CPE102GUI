package worldobject.entities.action.animated.miner;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
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
	
	public boolean minerToOre(WorldModel world, Ore ore)
	{
		if (ore ==null)
		{
			return false;
		}
		if (this.getPosition().adjacent(ore.getPosition()))
		{
			this.setResourceCount(1 + this.getResourceCount());
			ore.removeEntity(world);
			return true;
		}
		else
		{
			Point newPt= this.aStar(ore.getPosition(), world).getFirst();
//			this.APrint();
//			Point newPt = this.nextPositon(world, ore.getPosition());
			world.move_entity(this, newPt);
			return false;
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

	public boolean startAction(WorldModel world)
	{
		Ore ore = (Ore) world.find_nearest(this.getPosition(), Types.ORE);
		return this.minerToOre(world, ore);
	}

	
	public Miner transformType(WorldModel world)
	{
		return this.tryTransformMinerNotFull(world);
	}
}
