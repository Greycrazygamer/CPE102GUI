package worldobject.entities.action.mover;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Mover
extends worldobject.entities.action.Actionable
{
	private int rate;
	public Mover(String name, Point position, int rate, List<PImage> imgs)
	{
		super(name, position, imgs);
		this.rate= rate;
	}
	
	public int getRate()
	{
		return this.rate;
	}
	
	public Types getType()
	{
		return Types.MOVER;
	}
}
