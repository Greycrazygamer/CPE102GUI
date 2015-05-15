package worldobject.entities.action;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Actionable
extends worldobject.entities.Entity
{

	public Actionable(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
		
	}
	
	public Types getType()
	{
		return Types.ACTIONABLE;
	}
}
