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
import worldloaders.Load;
import worldloaders.WorldView;
import worldmodel.WorldModel;
import worldobject.Background;


public class CPE102ProjectWGUI extends PApplet 
{
	
	public String WORLD_FILE = "gaia.sav";
	public String IMAGE_FILE = "src/projdata/imagelist";
	public int SCREEN_WIDTH = 640;
	public int SCREEN_HEIGHT = 480;
	public int TILE_WIDTH = 32;
	public int TILE_HEIGHT = 32;
	
	
	
	private long next_time;
	private WorldView view;
	private WorldModel world;
	public Background bgent;
	
	
	public void master_load(WorldModel world, ArrayList<PImage> images, File filename)
	{
		
	}
	
	
	public void setup()
	{
//		ellipse(10,10,10,10);
		imageLoad(IMAGE_FILE);
		//System.out.print(ALLIMAGES);
		size(SCREEN_WIDTH,SCREEN_HEIGHT);
			//Load.LoadWorld(world, images, file, run);
		world = new WorldModel(32, 32, bgent);
		view = new WorldView(SCREEN_WIDTH / TILE_WIDTH,
			      SCREEN_HEIGHT / TILE_HEIGHT , this, world, 32, 32);
		
		//view.drawBackground(background);
		//view.drawTile(background.get(0), new Point(0,0));
		
	}

	public void draw() 
	{
		 // A simplified action scheduling handler
	      long time = System.currentTimeMillis();
	      if (time >= next_time)
	     	{
	         // perform actions previous to current time
	     	}
	         

		
	}
	
	public void imageLoad(String imagelist)
	{
		Scanner reader = null;
		try {
			reader= new Scanner(new File(imagelist));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (reader.hasNextLine())
		{
			String[] line = reader.nextLine().split("\\s");
			System.out.println(line[1]);
			imageSort(line);
			
			
		}
		reader.close();
	}
	
	public void imageSort(String[] line)
	{
		int type = Integer.parseInt(line[0]);
		PImage temp;
		switch (type)
		{
		case 1:
			temp = loadImage(line[1]);
			Load.SMITH_IMG.add(temp);
			Load.ALLIMAGES.add(Load.SMITH_IMG);
			break;
		case 2:
			temp = loadImage(line[1]);
			Load.blob.add(temp);
			Load.ALLIMAGES.add(Load.blob);
			break;
		case 3:
			temp = loadImage(line[1]);
			Load.BGND_IMG.add(temp);
			Load.ALLIMAGES.add(Load.BGND_IMG);
			break;
		case 4:
			temp = loadImage(line[1]);
			Load.MINER_IMG.add(temp);
			Load.ALLIMAGES.add(Load.MINER_IMG);
			break;
		case 5:
			temp = loadImage(line[1]);
			Load.OBSTACLE_IMG.add(temp);
			Load.ALLIMAGES.add(Load.OBSTACLE_IMG);
			break;
		case 6:
			temp = loadImage(line[1]);
			Load.ORE_IMG.add(temp);
			Load.ALLIMAGES.add(Load.ORE_IMG);
			break;
		case 7:
			temp = loadImage(line[1]);
			Load.quake.add(temp);
			Load.ALLIMAGES.add(Load.quake);
			break;
		case 8:
			temp = loadImage(line[1]);
			Load.BGND_IMG.add(temp);
			Load.ALLIMAGES.add(Load.BGND_IMG);
			break;
		case 9:
			temp = loadImage(line[1]);
			Load.VEIN_IMG.add(temp);
			Load.ALLIMAGES.add(Load.VEIN_IMG);
			break;
		}
	}	
	public static void main(String _args[]) {
		PApplet.main(new String[] { CPE102ProjectWGUI.class.getName() });
	}
}
