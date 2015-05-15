package worldobject.entities.action.mover;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;


public class Blob
extends Mover
{
	public Blob(String name, Point position, int rate, List<PImage> imgs)
	{
		super(name, position, rate, imgs);
		// TODO Auto-generated constructor stub
	}

	public Types getType()
	{
		return Types.BLOB;
	}
}
