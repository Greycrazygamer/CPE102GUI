package worldobject.entities.action.animated;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsInstanceOf;

import processing.core.PImage;
import projdata.Point;
import projdata.Types;
import worldloaders.Action;
import worldloaders.Schedules;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.Ore;
import worldobject.entities.action.Vein;


public class Blob
extends AnimatedEntity
{
	public Blob(String name, Point position, long rate, List<PImage> imgs, long animationRate)
	{
		super(name, position, rate, animationRate, imgs);
		
		// TODO Auto-generated constructor stub
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
				&& !(world.get_tile_occupant(newPt) instanceof Ore)))
		{
			int vert = sign(destPt.getY() - this.getPosition().getY());
			newPt = new Point(this.getPosition().getX(), this.getPosition().getY()+vert);
			if(vert==0 ||(world.is_occupied(newPt)
					&& !(world.get_tile_occupant(newPt) instanceof Ore)))
			{
				newPt= this.getPosition();
			}
		}
		return newPt;
	}
	
	public boolean blobToVein(WorldModel world, Vein vein)
	{	
		if (vein == null)
		{
			return false;
		}
		if (this.getPosition().adjacent(vein.getPosition()))
		{
			return true;
		}
		else
		{
			Point newPt= this.blobNextPosition(world, vein.getPosition());
			Actionable oldEntity = (Actionable) world.get_tile_occupant(newPt);
			System.out.println(oldEntity);
			if (oldEntity instanceof Ore)
			{
				Schedules.removeEntity(world, oldEntity);
			}
			world.move_entity(this, newPt);
			return false;
		}
		
	}
	public Action createBlobAction(WorldModel world)
	{
		Action[] func = {null};
		func[0] = (long current_ticks) ->
		{
			this.removePendingAction(func[0]);
			
			Vein vein = (Vein) world.find_nearest(getPosition(), Types.VEIN);
			boolean found= this.blobToVein(world, vein);
			
			long nextTime = current_ticks + this.getRate();
			if(found)
			{
				Quake quake= world.createQuake(vein.getPosition(), current_ticks);
				world.remove_entity(vein);
				world.add_entity(quake);
				nextTime = current_ticks + this.getRate()*2;
			}
			
			this.scheduleAction(world, this.createBlobAction(world), nextTime);
			
		};
		
		return func[0];
	}
	
	public void scheduleBlob(WorldModel world, Action action, long ticks)
	{	
		//this.scheduleAnimation(world, 0);
		this.scheduleAction(world, this.createBlobAction(world), ticks);
	}
}
