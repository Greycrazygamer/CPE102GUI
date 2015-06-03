package cpe102projectwgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.System;

import org.hamcrest.core.IsInstanceOf;

import processing.core.PApplet;
import processing.core.PImage;
import projdata.Node;
import projdata.Point;
import worldloaders.Controller;
import worldloaders.Load;
import worldloaders.WorldView;
import worldmodel.WorldModel;
import worldobject.Background;
import worldobject.entities.Entity;
import worldobject.entities.Storm;
import worldobject.entities.action.animated.AnimatedEntity;
import worldobject.entities.action.animated.Lightning;
import worldobject.entities.action.animated.miner.MinerNotFull;


public class CPE102ProjectWGUI extends PApplet 
{
	
	public String WORLD_FILE = "src/projdata/gaia.sav";
	public String IMAGE_FILE = "src/projdata/imagelist";
	public String STORM_FILE = "src/projdata/storms";
	public int SCREEN_WIDTH = 640;
	public int SCREEN_HEIGHT = 480;
	public int TILE_WIDTH = 32;
	public int TILE_HEIGHT = 32;
	public boolean RUN_AFTER_LOAD= true;
		
	
	private long next_time = 0;
	private long refresh=0;
	private WorldView view;
	private WorldModel world;
	public Background DEFAULT_BACKGROUND;
	public List<PImage> back;
	public PImage temp;	
	public PImage yellowpath;
	public PImage redpath;

	
	

	
	public void setup()
	{
		temp= loadImage("none.bmp");
		redpath= loadImage("reddot.png");
		yellowpath= loadImage("bg00.png");
		yellowpath= Load.setAlpha(yellowpath, color(100, 100,100), 10);
	
		size(SCREEN_WIDTH,SCREEN_HEIGHT);	
		back= new ArrayList<>();
		back.add(temp);
		DEFAULT_BACKGROUND= new Background("default_background", back);
		
		world = new WorldModel(30, 40, DEFAULT_BACKGROUND);
		view = new WorldView(SCREEN_WIDTH / TILE_WIDTH,
			      SCREEN_HEIGHT / TILE_HEIGHT , this, world, 32, 32);
		

		Load.imageLoad(IMAGE_FILE, this);
		
		
		Load.LoadWorld(world, WORLD_FILE, RUN_AFTER_LOAD, System.currentTimeMillis());
		
		view.drawViewport();
	}

	public void draw() 
	{
		long time = System.currentTimeMillis();
		if (refresh< time)
	    {
			view.drawViewport();
			

	      // perform actions previous to current time
	    	refresh = System.currentTimeMillis() +100;
	    }
		
		// A simplified action scheduling handler
	    
	    if (next_time<time)
	    {
	    	this.DRAWPATH();
	    	world.updateOnTime(time);
	    	this.worldEvent(time);
	      // perform actions previous to current time
	    	next_time = System.currentTimeMillis() +100;
	    }
	         

		
	}
	
	public void DRAWPATH()
	{
		Point pt = new Point(view.realMousePosX(), view.realMousePosY());
		if(world.get_tile_occupant(pt) instanceof AnimatedEntity)
		{
			AnimatedEntity guy = (AnimatedEntity) world.get_tile_occupant(pt);
			for (Point dtn: guy.getSearchPath())
			{
				view.drawPathTile(yellowpath, dtn);
			}
			for (Point dtp: guy.getDrawPath())
			{
				view.drawPathTile(redpath, dtp);
			}
			view.drawTile(guy.getImage(), guy.getPosition());
			
		}
	}
	
	public void worldEvent(long time)
	{
		if (mousePressed)
		{
			Point pt = new Point(view.realMousePosX(), view.realMousePosY());
			System.out.println(pt.printXY());
			Load.MouseStorm(world, pt);
			Load.LoadStorm(world, STORM_FILE, time);
		}
	}
	
	
	public void keyPressed()
	{
		int deltaX= 0;
		int deltaY= 0;
		int[] delta = new int[2];
		switch(key)
		{
		case 'w':
			
			deltaY--;
			break;
		case 'a':
			deltaX--;
			break;
		case 's':
			deltaY++;
			break;
		case 'd':
			deltaX++;
			break;
		}
		delta[0]=deltaX;
		delta[1]=deltaY;
		view.updateView(delta);
		
	}
	public static void main(String _args[]) {
		PApplet.main(new String[] { CPE102ProjectWGUI.class.getName() });
	}
}
