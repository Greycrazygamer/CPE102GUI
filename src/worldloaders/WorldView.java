package worldloaders;

import java.util.ArrayList;
import java.util.List;

import projdata.Point;
import projdata.ViewPort;
import worldmodel.WorldModel;
import worldobject.entities.Entity;
import processing.core.*;
import cpe102projectwgui.CPE102ProjectWGUI;


public class WorldView 
{
	private ViewPort viewPort;
	private PApplet screen;
	private int viewCols;
	private int viewRows;
	private Point mousePt;
	private WorldModel world;
	private int tileWidth;
	private int tileHeight;
	private int numRows;
	private int numCols;

	
	public WorldView(int view_cols, int view_rows, PApplet screen, 
			WorldModel world, int tile_width, int tile_height)
	{
		this.viewPort= new ViewPort(0,0, view_cols, view_rows);
		this.viewCols= view_cols;
		this.viewRows= view_rows;
		this.screen= screen;
		this.world= world;
		this.tileWidth= tile_width;
		this.tileHeight= tile_height;
	}
	
	public void drawTile(PImage img, Point loc)
	{
		
		screen.image(img, loc.getX()*32 , loc.getY()*32);
	}
		
	public void drawBackground(List<PImage> bg)
	{
		
		for (int y = 0; y < viewPort.getHeight(); y++)
		{
			for (int x = 0; x< viewPort.getWidth(); x++)
			{
				
				Point wPt= viewportToWorld(new Point (x,y));
						
				//PImage img = this.world.getBackgroundImage(wPt);
				//System.out.println(img);
				//NEEDS UPDATING
				drawTile(bg.get(0), new Point(x,y));
			}
		}
	}
	
	private void drawEntities()
	{
		for (Entity e: this.world.get_entities())
		{
			if (this.viewPort.containsPoint(e.getPosition()))
			{
				Point vPt= this.worldToViewpoint(e.getPosition());
				this.drawTile(e.getImage(), vPt);
			}
		}
	}
	
	public void drawViewport()
	{
		//this.drawBackground();
		this.drawEntities();
	}
	
	
//	public void updateViewTiles(List<Point> tiles)
//	{
//		ArrayList rects = new ArrayList<>();
//	}
	
	public PImage getTileImage(Point tilePt)
	{
		Point pt = this.viewportToWorld(tilePt);
		PImage bgnd = this.world.getBackgroundImage(pt);
		Entity occupant= this.world.get_tile_occupant(pt);
		if (occupant != null)
		{
			return occupant.getImage();
		}
		else{
			return bgnd;
		}
	}
	
	public void updateView(int[] delta)
	{
		this.viewPort= this.createShiftedViewport(delta, this.numRows, this.numCols);
		this.drawViewport();
	}
	
	
	public Point viewportToWorld(Point pt)
	{
		return new Point(pt.getX() + viewPort.getTopLeft().getX(), 
				pt.getY() + viewPort.getTopLeft().getY());
	}
	
	public Point worldToViewpoint(Point pt)
	{
		return new Point(pt.getX() - viewPort.getTopLeft().getX(), 
				pt.getY() - viewPort.getTopLeft().getY());
	}
	
	public ViewPort createShiftedViewport(int[] delta, int num_rows, int num_cols)
	{
		int new_x= clamp(this.viewPort.getTopLeft().getX() + delta[0], 0, num_cols - this.viewPort.getWidth());
		int new_y= clamp(this.viewPort.getTopLeft().getY() + delta[1], 0, num_rows - this.viewPort.getHeight());
		return new ViewPort(new_x, new_y, this.viewPort.getWidth(), this.viewPort.getHeight());
	}
	public int clamp(int v, int low, int high)
	{
		return Math.min(high, Math.max(v,low));
	}
}
