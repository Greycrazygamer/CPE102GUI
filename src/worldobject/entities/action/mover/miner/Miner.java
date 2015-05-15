package worldobject.entities.action.mover.miner;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldmodel.WorldModel;


public class Miner
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
	
}
