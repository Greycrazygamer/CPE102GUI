package worldobject.entities.action.mover;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldmodel.WorldModel;


public class Blob
extends Mover
{
	private int animationRate;
	public Blob(String name, Point position, int rate, List<PImage> imgs, int animationRate)
	{
		super(name, position, rate, imgs);
		this.animationRate= animationRate;
		// TODO Auto-generated constructor stub
	}
	
	public int getAnimationRate()
	{
		return this.animationRate;
	}
		
	public Types getType()
	{
		return Types.BLOB;
	}
	
	public Point blobNextPosition(WorldModel world, Point destPt)
	{
		int horiz = sign(destPt.getX() - this.getPosition().getX());
		Point newPt = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
		
		if (horiz == 0 || (world.is_occupied(newPt)
				&& world.get_tile_occupant(newPt).getType() != Types.ORE))
		{
			int vert = sign(destPt.getY() - this.getPosition().getY());
			newPt = new Point(this.getPosition().getX(), this.getPosition().getY()+vert);
			if(vert==0 ||(world.is_occupied(newPt)
					&& world.get_tile_occupant(newPt).getType() != Types.ORE))
			{
				newPt= this.getPosition();
			}
		}
		return newPt;
	}
}
