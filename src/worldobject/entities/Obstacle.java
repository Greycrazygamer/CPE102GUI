package worldobject.entities;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Obstacle
extends Entity
{

	public Obstacle(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
		// TODO Auto-generated constructor stub
	}

	
	public Types getType()
	{
		// TODO Auto-generated method stub
		return Types.OBSTACLE;
	}

}
