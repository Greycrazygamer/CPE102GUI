package worldobject.entities.action;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;


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

}
