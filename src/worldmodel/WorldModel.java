package worldmodel;

import worldobject.Background;
import worldobject.WorldObject;
import worldobject.entities.Entity;
import worldobject.entities.Obstacle;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Action;
import projdata.OrderedList;
import projdata.Schedules;
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
	private OrderedList actionQueue;
	
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
		Schedules action;
		if (this.within_bounds(pt) && occupany.getCell(pt) != null)
		{
			Entity entity = (Entity) occupany.getCell(pt);
			entity.setPosition(new Point(-1, -1));
			//Actions.remove_entity(this, entity);
			occupany.setCell(pt, null);
		}
	}
	//public schedule_action
	//public unschedule_action
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
		this.actionQueue.insert(thingtodo, time);
	}
	
	public void unscheduleAction(Action thingtodo)
	{
		this.actionQueue.remove(thingtodo);
	}
}
