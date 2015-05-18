package worldmodel;

import worldloaders.Action;
import worldloaders.Load;
import worldloaders.Schedules;
import worldobject.Background;
import worldobject.WorldObject;
import worldobject.entities.Entity;
import worldobject.entities.Obstacle;
import worldobject.entities.action.Quake;
import worldobject.entities.action.mover.Blob;
import worldobject.entities.action.mover.Ore;
import worldobject.entities.action.mover.Vein;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PImage;
import projdata.ListItem;
import projdata.OrderedList;
import projdata.Grid;
import projdata.Point;
import projdata.Types;


public class WorldModel {
	private int num_rows;
	private int num_cols;
	private ArrayList<Entity> entities;
	private Grid background;
	private Grid occupany;
	private Background backentity;
	public OrderedList actionQueue;
	
	public WorldModel( int num_rows, int num_cols, Background backentity)
	{
		this.num_rows=num_rows;
		this.num_cols=num_cols;
		this.backentity= backentity;
		this.entities = new ArrayList<>();
		this.background = new Grid(num_cols, num_rows, backentity);
		this.occupany = new Grid(num_cols, num_rows, null);
		this.actionQueue = new OrderedList();
	}

	public void updateOnTime(long ticks)
	{
		ListItem next = this.actionQueue.pop();
		while ((next !=null) && next.getOrd()< ticks)
		{
			this.actionQueue.pop();
			next.getItem().make(ticks);
		}
		
		
	}
	
	public boolean within_bounds(Point pt)
	{
		return (pt.getX() >= 0 && pt.getX() < this.num_cols 
				&& pt.getY() >= 0 && pt.getY() < this.num_rows);
	}
	
	public boolean is_occupied(Point pt)
	{
		return (this.within_bounds(pt) && occupany.getCell(pt) != null);
	}
	
	public boolean is_empty(Point pt)
	{
		return (this.within_bounds(pt) && occupany.getCell(pt) == null);
	}
	
	public Object find_nearest(Point pt, Types type) 
	{
		
		ArrayList<Entity> oftype = new ArrayList<Entity>();
		int mindex =0;
		for (Entity e : this.entities)
		{	
			if (type == e.getType())
			{
				oftype.add(e);
			}
		}
		double smallest = oftype.get(0).getPosition().distance_sq(pt);
		for (Entity i : oftype)
		{
			if (i.getPosition().distance_sq(pt) < smallest)
			{
				mindex = oftype.indexOf(i);
			}
			
		}
		return oftype.get(mindex);
	}
	
	
	public void add_entity(Entity entity)
	{
		Point pt= entity.getPosition();
		if (this.within_bounds(pt))
		{
			Entity old_entity = (Entity) occupany.getCell(pt);
			if (old_entity != null)
			{
				
			}
			occupany.setCell(pt, entity);
			entities.add(entity);
		}
	}
	
	public ArrayList move_entity(Entity entity, Point pt)
	{
		ArrayList<Point> tiles = new ArrayList<Point>();
		if (this.within_bounds(pt))
		{
			Point old_pt= entity.getPosition();
			occupany.setCell(old_pt, null);
			tiles.add(old_pt);
			occupany.setCell(pt, entity);
			tiles.add(pt);
			entity.setPosition(pt);
		}
		
		return tiles;
	}
	
	public void remove_entity(Entity entity)
	{
		this.remove_entity_at(entity.getPosition());
	}
	
	public void remove_entity_at(Point pt)
	{
		if (this.within_bounds(pt) && occupany.getCell(pt) != null)
		{
			Entity entity = (Entity) occupany.getCell(pt);
			entity.setPosition(new Point(-1, -1));
			this.entities.remove(entity);
			occupany.setCell(pt, null);
		}
	}
	
	public PImage getBackgroundImage(Point pt)
	{
		if (this.within_bounds(pt))
		{
			return this.background.getCell(pt).getImage();
		}
		
		else
		{
			return null;
		}
	}
	
	public WorldObject get_background(Point pt)
	{
		if (this.within_bounds(pt))
		{
			return background.getCell(pt);
		}
		else
		{
			return null;
		}
	}
	
	public void set_background(Point pt, Background bgnd)
	{
		if (this.within_bounds(pt))
		{
			background.setCell(pt, bgnd);
		}
	}
	
	public  Entity get_tile_occupant(Point pt)
	{
		if (this.within_bounds(pt))
		{
			return (Entity) occupany.getCell(pt);
		}
		else{
			return null;
		}
	}
	
	public ArrayList<Entity> get_entities()
	{
		return this.entities;
	}
	
	public void scheduleAction(Action thingtodo, long time)
	{
		System.out.println("hi");
		this.actionQueue.insert(thingtodo, time);
	}
	
	public void unscheduleAction(Action thingtodo)
	{
		this.actionQueue.remove(thingtodo);
	}
	
	public Blob createBlob(String name, Point pt, int rate, long ticks)
	{
		Random r = new Random();
		int i1 = r.nextInt(Schedules.BLOB_ANIMATION_MAX - Schedules.BLOB_ANIMATION_MIN + 1) + Schedules.BLOB_ANIMATION_MIN;
		i1= i1*Schedules.BLOB_ANIMATION_RATE_SCALE;
		Blob blob =new Blob(name, pt, rate, Load.BLOB_IMG, i1);
		return blob;
	}
	
	public Vein createVein(String name, Point pt, long ticks)
	{
		Random r = new Random();
		int i1 = r.nextInt(Schedules.VEIN_RATE_MAX - Schedules.VEIN_RATE_MIN + 1) + Schedules.VEIN_RATE_MIN;
		Vein vein = new Vein(name, i1, pt, Load.VEIN_IMG);
		return vein;
	}
	
	public Ore createOre(String name, Point pt, long ticks)
	{
		Random r = new Random();
		int i1 = r.nextInt(Schedules.VEIN_RATE_MAX - Schedules.VEIN_RATE_MIN + 1) + Schedules.ORE_CORRUPT_MIN;
		Ore ore = new Ore(name, pt, Load.ORE_IMG, i1);
		return ore;
	}
	
	public Quake createQuake(Point pt, long ticks)
	{
		Quake quake = new Quake("quake", pt, Load.QUAKE_IMG, Schedules.QUAKE_ANIMATION_RATE);
		quake.scheduleQuake(this, ticks);
		return quake;
	}
}
