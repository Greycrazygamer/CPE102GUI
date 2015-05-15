package worldobject.entities.action.mover;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;


public class Vein
extends Mover
{

	private int reach;
	
	public Vein(String name, int rate, Point position, List<PImage> imgs, int reach)
	{
		super(name, position, rate, imgs);
		this.reach = reach;
		// TODO Auto-generated constructor stub
	}
	
	public Types getType()
	{
		return Types.VEIN;
	}
	
	public int getReach()
	{
		return this.reach;
	}
	
}
