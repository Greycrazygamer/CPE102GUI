package worldobject.entities.action.animated.miner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Load;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Blacksmith;
import worldobject.entities.action.Ore;
import worldobject.entities.action.animated.Blob;
import worldobject.entities.action.animated.Wyvern;


public class MinerStorm
extends Miner
{

	public MinerStorm(String name, int resource_limit, Point position, long rate,
			List<PImage> imgs, long animation_rate)
	{
		super(name, resource_limit, position, rate, imgs, animation_rate);
		// TODO Auto-generated constructor stub
	}

	public int minerToWyvern(WorldModel world, Wyvern bob)
	{
		Point entity_pt = this.getPosition();
		if (bob == null)
		{
			LinkedList<Point> fail= new LinkedList<>();
			fail.add(this.getPosition());
			this.setDrawPath(fail);
			return 0;
		}
		Point blob_pt = bob.getPosition();
		if (entity_pt.adjacent(blob_pt))
		{
			bob.removeEntity(world);
			world.add_entity(world.createLightning(blob_pt, null, System.currentTimeMillis()));
			return 0;
		}
		else
		{
			Point newPt= this.aStar(blob_pt, world).getFirst();
//			this.APrint();
//			Point newPt= this.nextPositon(world, smith_pt);
			world.move_entity(this, newPt);
			return 0;
		}
	}
	
	public int startAction(WorldModel world)
	{
		Wyvern blob = (Wyvern) world.find_nearest(this.getPosition(), Types.WYVERN);
		return this.minerToWyvern(world, blob);
	}

	@Override
	public Miner transformType(WorldModel world)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Miner transformStormMiner(WorldModel world)
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
