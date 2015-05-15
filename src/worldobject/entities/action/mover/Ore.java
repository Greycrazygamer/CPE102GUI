package worldobject.entities.action.mover;
import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;


public class Ore
extends Mover
{
	
	public Ore(String name, Point position, List<PImage> imgs, int rate)
	{
		super(name, position, rate, imgs);
		// TODO Auto-generated constructor stub
	}

	public Types getType()
	{
		return Types.ORE;
	}
}
