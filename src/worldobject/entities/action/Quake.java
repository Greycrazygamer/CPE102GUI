package worldobject.entities.action;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import worldloaders.Schedules;
import worldmodel.WorldModel;


public class Quake
extends Actionable
{
	private int animationRate;
	public Quake(String name, Point position, List<PImage> imgs, int animationRate)
	{
		super(name, position, imgs);
		this.animationRate= animationRate;
		
		// TODO Auto-generated constructor stub
	}
	public int getAnimationRate()
	{
		return animationRate;
	}
	
	public void scheduleQuake(WorldModel world, long ticks)
	{
		Schedules.scheduleAnimation(world, this);
		Schedules.scheduleAction(world, this, 
								Schedules.createEntityDeathAction(world, this),
								ticks+ Schedules.QUAKE_DURATION);
	}
}
