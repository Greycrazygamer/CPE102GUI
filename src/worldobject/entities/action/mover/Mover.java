package worldobject.entities.action.mover;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Mover
extends worldobject.entities.action.Actionable
{
	private long rate;
	public Mover(String name, Point position, long rate, List<PImage> imgs)
	{
		super(name, position, imgs);
		this.rate= rate;
	}
	
	public long getRate()
	{
		return this.rate;
	}
	
	public Types getType()
	{
		return Types.MOVER;
	}
}
