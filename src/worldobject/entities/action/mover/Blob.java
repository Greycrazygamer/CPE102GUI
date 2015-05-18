package worldobject.entities.action.mover;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Quake;


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
	
	public ArrayList blobToVein(WorldModel world, Vein vein)
	{	
		if (this.getPosition().adjacent(vein.getPosition()))
		{
			Schedules.removeEntity(world, vein);
		}
		else
		{
			Point newPt= this.blobNextPosition(world, vein.getPosition());
			Actionable oldEntity = (Actionable) world.get_tile_occupant(newPt);
			if (oldEntity.getType() == Types.ORE)
			{
				Schedules.removeEntity(world, oldEntity);
			}
			return world.move_entity(this, newPt);
		}
		return null;
	}
	public Action createBlobAction(WorldModel world)
	{
		Action[] func = {null};
		func[0] = (long current_ticks) ->
		{
			this.removePendingAction(func[0]);
			
			Vein vein = (Vein) world.find_nearest(getPosition(), Types.VEIN);
			ArrayList points= this.blobToVein(world, vein);
			
			long next_time = current_ticks + this.getRate() *2;
			
			if(points == null)
			{
				Quake quake= world.createQuake(vein.getPosition(), current_ticks);
				world.add_entity(quake);
				long nextTime = current_ticks + this.getRate()*2;
			}
			
			Schedules.scheduleAction(world, this, 
					this.createBlobAction(world), next_time);
			
		};
		
		return func[0];
	}
	
	public void scheduleBlob(WorldModel world, long ticks)
	{
		Schedules.scheduleAction(world, this, 
			this.createBlobAction(world), ticks + this.getRate());
		Schedules.scheduleBlobAnimation(world, this);
	}
}
