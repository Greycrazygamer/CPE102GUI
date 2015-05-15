package worldobject.entities;


import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Blacksmith
extends Entity
{
	private int resource_count;
	
	public Blacksmith(String name, Point position, List<PImage> imgs)
	{
		super(name, position, imgs);
		this.resource_count=0;
	}
	
	public void set_resource_count(int n)
	{
		this.resource_count= n;
	}
	
	public int get_resource_count()
	{
		return this.resource_count;
	}
	
	public Types getType()
	{
		return Types.BLACKSMITH;
	}
}
