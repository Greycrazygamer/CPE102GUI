package worldobject.entities.action.mover;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;


public class Blob
extends Mover
{
	private int animationRate;
	public Blob(String name, Point position, int rate, List<PImage> imgs, int animationRate)
	{
		super(name, position, rate, imgs);
		this.animationRate= animationRate;
		// TODO Auto-generated constructor stub
	}
	
	public int getAnimationRate()
	{
		return this.animationRate;
	}
		
	public Types getType()
	{
		return Types.BLOB;
	}
}
