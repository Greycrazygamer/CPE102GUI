package worldobject.entities;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;


public abstract class Entity 
extends worldobject.WorldObject
{
	private Point position;
	
	public Entity(String name, Point position, List<PImage> imgs)
	{
		super(name, imgs);
		this.position= position;
	}
	
	public void setPosition(Point newpos)
	{
		this.position = newpos;
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public abstract Types getType();
}


