package worldloaders;

import worldmodel.WorldModel;
import worldobject.Background;
import worldobject.entities.Blacksmith;
import worldobject.entities.Entity;
import worldobject.entities.Obstacle;
import worldobject.entities.action.*;
import worldobject.entities.action.mover.*;
import worldobject.entities.action.mover.miner.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cpe102projectwgui.CPE102ProjectWGUI;
import processing.core.PApplet;
import processing.core.PImage;
import projdata.Point;

public class Load
{
	
	
	public static List<PImage> BLOB_IMG= new ArrayList<>();
	public static List<PImage> QUAKE_IMG= new ArrayList<>();
		
			public static int PROPERTY_KEY = 0;

			public static String BGND_KEY = "background";
			public static int BGND_NUM_PROPERTIES = 4;
			public static int BGND_NAME = 1;
			public static int BGND_COL = 2;
			public static int BGND_ROW = 3;
			public static List<PImage> BGND_IMG = new ArrayList<>();;
			
                   
			public static String MINER_KEY = "miner";
			public static int MINER_NUM_PROPERTIES = 7;
			public static int MINER_NAME = 1;
			public static int MINER_LIMIT = 4;
			public static int MINER_COL = 2;
			public static int MINER_ROW = 3;
			public static int MINER_RATE = 5;
			public static int MINER_ANIMATION_RATE = 6;
			public static List<PImage> MINER_IMG = new ArrayList<>();
                   
			public static String OBSTACLE_KEY = "obstacle";
			public static int OBSTACLE_NUM_PROPERTIES = 4;
			public static int OBSTACLE_NAME = 1;
			public static int OBSTACLE_COL = 2;
			public static int OBSTACLE_ROW = 3;
			public static List<PImage> OBSTACLE_IMG= new ArrayList<>();
                   
			public static String ORE_KEY = "ore";
			public static int ORE_NUM_PROPERTIES = 5;
			public static int ORE_NAME = 1;
			public static int ORE_COL = 2;
			public static int ORE_ROW = 3;
			public static int ORE_RATE = 4;
			public static List<PImage> ORE_IMG= new ArrayList<>();
                   
			public static String SMITH_KEY = "blacksmith";
			public static int SMITH_NUM_PROPERTIES = 7;
			public static int SMITH_NAME = 1;
			public static int SMITH_COL = 2;
			public static int SMITH_ROW = 3;
			public static int SMITH_LIMIT = 4;
			public static int SMITH_RATE = 5;
			public static int SMITH_REACH = 6;
			public static List<PImage> SMITH_IMG = new ArrayList<>();
                    
			public static String VEIN_KEY = "vein";
			public static int VEIN_NUM_PROPERTIES = 6;
			public static int VEIN_NAME = 1;
			public static int VEIN_RATE = 4;
			public static int VEIN_COL = 2;
			public static int VEIN_ROW = 3;
			public static int VEIN_REACH = 5;
			public static List<PImage> VEIN_IMG= new ArrayList<>();
			        
			public static void LoadWorld(WorldModel world, String filename)
			{
				boolean run = false;
				Scanner reader= null;
				try {
					
					reader = new Scanner(new File(filename));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (reader.hasNextLine())
				{
					String[] properties= reader.nextLine().split("//s");
					if (properties != null)
					{
						if (properties[PROPERTY_KEY] == BGND_KEY)
						{
							AddBackground(world, properties);
						}
						else
						{
							AddEntity(world, properties, run);
						}
					}
					
				}
				reader.close();
			}
			
			public static void LoadWorld(WorldModel world, String filename, boolean run)
			{
				Scanner reader= null;
				try {
					reader = new Scanner(new File(filename));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (reader.hasNextLine())
				{
					String[] properties= reader.nextLine().split("//s");
					if (properties != null)
					{
						if (properties[PROPERTY_KEY] == BGND_KEY)
						{
							AddBackground(world, properties);
						}
						else
						{
							AddEntity(world, properties,run);
						}
					}
					
				}
				reader.close();
			}
			
			public static void AddBackground(WorldModel world, String[] properties)
			{
				if (properties.length >= BGND_NUM_PROPERTIES)
				{
					Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
					String name = properties[BGND_NAME];
					world.set_background(pt, new Background(name, BGND_IMG));
				}
			}
			
			public static void AddEntity(WorldModel world, String[] properties, Boolean run)
			{
				Entity newEntity= CreateFromProperties(properties);
				if (newEntity !=null)
				{
					world.add_entity(newEntity);
					if(run)
					{
						schedule_entity(world, newEntity, images);
					}
				}
			}
			
			public static Entity CreateFromProperties(String[] properties)
			{
				String key = properties[PROPERTY_KEY];
				if (properties != null)
				{
					if (key.equals(MINER_KEY))
					{
						return CreateMiner(properties);
					}
					else if (key.equals(VEIN_KEY))
					{
						return CreateVein(properties);
					}
					else if (key.equals(ORE_KEY))
					{
						return CreateOre(properties);
					}
					else if (key.equals(SMITH_KEY))
					{
						return CreateBlacksmith(properties);
					}
					else if (key.equals(OBSTACLE_KEY))
					{
						return CreateObstacle(properties);
					}
				}
				return null;
			}
			
			public static Entity CreateMiner(String[] properties)
			{
				if (properties.length== MINER_NUM_PROPERTIES)
				{
					Entity miner = new MinerFull(properties[MINER_NAME],
							Integer.parseInt(properties[MINER_LIMIT]), 
							new Point(Integer.parseInt(properties[MINER_COL]), Integer.parseInt(properties[MINER_ROW])),
							Integer.parseInt(properties[MINER_RATE]),
							MINER_IMG, Integer.parseInt(properties[MINER_ANIMATION_RATE]));
					
					return miner;
				}
				else
				{
					return null;
				}
			}
			
			public static Entity CreateVein(String[] properties)
			{
				if (properties.length== VEIN_NUM_PROPERTIES)
				{
					Entity vein = new Vein(properties[VEIN_NAME],
							Integer.parseInt(properties[VEIN_RATE]), 
							new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW])),
							VEIN_IMG, Integer.parseInt(properties[VEIN_REACH]));
					
					return vein;
				}
				else
				{
					return null;
				}
			}
			
			public static Entity CreateOre(String[] properties)
			{
				if (properties.length== ORE_NUM_PROPERTIES)
				{
					Entity ore = new Ore(properties[ORE_NAME],
							new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW])),
							ORE_IMG, Integer.parseInt(properties[ORE_RATE]));
					
					return ore;
				}
				else
				{
					return null;
				}
			}
			
			public static Entity CreateBlacksmith(String[] properties)
			{
				if (properties.length== SMITH_NUM_PROPERTIES)
				{
					Entity blacksmith = new Blacksmith(properties[SMITH_NAME],
							new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW])),
							SMITH_IMG);
					
					return blacksmith;
				}
				else
				{
					return null;
				}
			}
			
			public static Entity CreateObstacle(String[] properties)
			{
				if (properties.length== SMITH_NUM_PROPERTIES)
				{
					Entity obstacle = new Obstacle(properties[OBSTACLE_NAME],
							new Point(Integer.parseInt(properties[OBSTACLE_COL]), Integer.parseInt(properties[OBSTACLE_ROW])),
							OBSTACLE_IMG);
					
					return obstacle;
				}
				else
				{
					return null;
				}
			}
			
			
//			public static void imageLoad(String imagelist, PApplet screen)
//			{
//				Scanner reader = null;
//				try {
//					reader= new Scanner(new File(imagelist));
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				while (reader.hasNextLine())
//				{
//					String[] line = reader.nextLine().split("\\s");
//					System.out.println(line[1]);
//					imageSort(line, screen);
//					
//					
//				}
//				reader.close();
//			}
//			
//			public static void imageSort(String[] line, PApplet screen)
//			{
//				int type = Integer.parseInt(line[0]);
//				PImage temp;
//				switch (type)
//				{
//				case 1:
//					temp = screen.loadImage(line[1]);
//					Load.SMITH_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.SMITH_IMG);
//					break;
//				case 2:
//					temp = screen.loadImage(line[1]);
//					Load.blob.add(temp);
//					Load.ALLIMAGES.add(Load.blob);
//					break;
//				case 3:
//					temp = screen.loadImage(line[1]);
//					Load.BGND_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.BGND_IMG);
//					break;
//				case 4:
//					temp = screen.loadImage(line[1]);
//					Load.MINER_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.MINER_IMG);
//					break;
//				case 5:
//					temp = screen.loadImage(line[1]);
//					Load.OBSTACLE_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.OBSTACLE_IMG);
//					break;
//				case 6:
//					temp = screen.loadImage(line[1]);
//					Load.ORE_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.ORE_IMG);
//					break;
//				case 7:
//					temp = screen.loadImage(line[1]);
//					Load.quake.add(temp);
//					Load.ALLIMAGES.add(Load.quake);
//					break;
//				case 8:
//					temp = screen.loadImage(line[1]);
//					Load.BGND_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.BGND_IMG);
//					break;
//				case 9:
//					temp = screen.loadImage(line[1]);
//					Load.VEIN_IMG.add(temp);
//					Load.ALLIMAGES.add(Load.VEIN_IMG);
//					break;
//				}
//			}
}
