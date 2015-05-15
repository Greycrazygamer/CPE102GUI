package worldobject.entities.action.mover.miner;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;


public class MinerNotFull
extends Miner
{

	public MinerNotFull(String name, int resource_limit, Point position,
			int rate, List<PImage> imgs, int animation_rate)
	{
		super(name, resource_limit, position, rate, imgs, animation_rate);
		// TODO Auto-generated constructor stub
	}

}
