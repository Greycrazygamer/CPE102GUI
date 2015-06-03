package worldobject.entities;

import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldobject.Background;

public class Storm
extends Obstacle
{
	public Storm(String name, Point position, List<PImage> storm)
	{
		super(name, position, storm);	
	}
	
	public Types getType()
	{
		// TODO Auto-generated method stub
		return Types.STORM;
	}

	
}
