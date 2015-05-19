package cpe102projectwgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.System;

import processing.core.PApplet;
import processing.core.PImage;
import projdata.Point;
import worldloaders.Controller;
import worldloaders.Load;
import worldloaders.WorldView;
import worldmodel.WorldModel;
import worldobject.Background;


public class CPE102ProjectWGUI extends PApplet 
{
	
	public String WORLD_FILE = "src/projdata/gaia.sav";
	public String IMAGE_FILE = "src/projdata/imagelist";
	public int SCREEN_WIDTH = 640;
	public int SCREEN_HEIGHT = 480;
	public int TILE_WIDTH = 32;
	public int TILE_HEIGHT = 32;
	public boolean RUN_AFTER_LOAD= true;
		
	
	private long next_time = 0;
	private WorldView view;
	private WorldModel world;
	public Background DEFAULT_BACKGROUND;
	public List<PImage> back;
	public PImage temp;	
	
	

	
	public void setup()
	{
		temp= loadImage("none.bmp");
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
		view.drawViewport();
		
		// A simplified action scheduling handler
	    long time = System.currentTimeMillis();
	    if (time >= next_time)
	    {
	    	world.updateOnTime(time);
	    	
	      // perform actions previous to current time
	    	next_time = System.currentTimeMillis() +1000;
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
