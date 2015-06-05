package worldloaders;

import worldmodel.WorldModel;
import worldobject.Background;
import worldobject.entities.Blacksmith;
import worldobject.entities.Entity;
import worldobject.entities.Obstacle;
import worldobject.entities.Storm;
import worldobject.entities.action.*;
import worldobject.entities.action.animated.*;
import worldobject.entities.action.animated.miner.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cpe102projectwgui.CPE102ProjectWGUI;
import processing.core.PApplet;
import processing.core.PImage;
import projdata.Point;
import projdata.Types;

public class Load
{
	
	
	public static List<PImage> BLOB_IMG= new ArrayList<>();
	public static List<PImage> QUAKE_IMG= new ArrayList<>();
	public static List<PImage> LIGHTNING_IMG= new ArrayList<>();
	public static List<PImage> FLAME_IMG= new ArrayList<>();
	public static List<PImage> STORM_MINER_IMG= new ArrayList<>();
	public static List<PImage> WYVERN_IMG= new ArrayList<>();
	public static int i=0;
	public static int stormint=0;
	
	
		
			public static int PROPERTY_KEY = 0;

			public static String BGND_KEY = "background";
			public static int BGND_NUM_PROPERTIES = 4;
			public static int BGND_NAME = 1;
			public static int BGND_COL = 2;
			public static int BGND_ROW = 3;
			public static List<PImage> BGND_GRASS_IMG = new ArrayList<>();
			public static List<PImage> BGND_ROCK_IMG = new ArrayList<>();
			
			public static int STORM_NAME = 1;
			public static int STORM_COL = 2;
			public static int STORM_ROW = 3;
			public static List<PImage> STORM_IMG = new ArrayList<>();
			
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
			public static int SMITH_COL  = 2;
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
			        
		
			public static void LoadWorld(WorldModel world, String filename, long ticks)
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
					String[] properties= reader.nextLine().split("\\s");
					
					if (properties != null)
					{
						
						if (properties[PROPERTY_KEY].equals(BGND_KEY))
						{
							AddBackground(world, properties);
						}
						else
						{
							AddEntity(world, properties, run, ticks);
						}
					}
					
				}
				reader.close();
			}
			
			public static void LoadWorld(WorldModel world, String filename, boolean run, long ticks)
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
					String[] properties= reader.nextLine().split("\\s");
					if (properties != null)
					{
						if (properties[PROPERTY_KEY].equals(BGND_KEY))
						{
							AddBackground(world, properties);
						}
						else
						{
							AddEntity(world, properties,run, ticks);
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
					if (properties[1].equals("grass"))
					{
						world.set_background(pt, new Background(name, BGND_GRASS_IMG));
					}
					if (properties[1].equals("rocks"))
					{
						world.set_background(pt, new Background(name, BGND_ROCK_IMG));
					}
					
				}
			}
			
			public static void AddEntity(WorldModel world, String[] properties, Boolean run, long ticks)
			{
				Entity newEntity= CreateFromProperties(properties);
				
				if (newEntity !=null)
				{
					world.add_entity(newEntity);
					if(run)
					{
					Load.scheduleEntity(world, newEntity, ticks);
					}
				}
			}
			
			public static void scheduleEntity(WorldModel world, Entity entity, long ticks)
			{
				if (entity.getType()== Types.MINER)
				{
					System.out.println("MinerInitScheduled");
					Miner new_entity = (Miner) entity;
					new_entity.scheduleMiner(world, ticks);
				}
				else if(entity.getType()== Types.VEIN)
				{
					System.out.println("VeinInitScheduled");
					Vein new_entity = (Vein) entity;
					new_entity.scheduleVein(world, ticks);
				}
				else if(entity.getType()== Types.ORE)
				{
					System.out.println("OreInitScheduled");
					Ore new_entity= (Ore) entity;
					new_entity.scheduleOre(world, ticks);
				}
			}
			
			public static Entity CreateFromProperties(String[] properties)
			{
				String key = properties[PROPERTY_KEY];
				
				if (properties != null)
				{
					
					if (key.equals(MINER_KEY))
					{
						System.out.println("new Miner");
						return Load.CreateMiner(properties);
					}
					else if (key.equals(VEIN_KEY))
					{
						System.out.println("new Vein");
						return Load.CreateVein(properties);
					}
					else if (key.equals(ORE_KEY))
					{
						System.out.println("new Ore");
						return Load.CreateOre(properties);
					}
					else if (key.equals(SMITH_KEY))
					{
						System.out.println("new Smith");
						return Load.CreateBlacksmith(properties);
					}
					else if (key.equals(OBSTACLE_KEY))
					{
						return Load.CreateObstacle(properties);
					}
				}
				return null;
			}
			
			public static void MouseStorm(WorldModel world, Point pt)
			{
				for (int i = pt.getY()-1; (i < pt.getY()+1); i++)
				{
					for(int j = pt.getX()-1; (j< pt.getX()+1); j++)
					{
						Point temp= new Point(j,i);
						if (world.within_bounds(temp) && world.is_empty(temp))
						{
							world.add_entity(new Storm("Storm" + pt.getX() + pt.getY(), temp, Load.STORM_IMG));
						}
					}
				}
								
			}
			public static void LoadStorm(WorldModel world, String filename, long ticks)
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
					i++;
					String[] properties= reader.nextLine().split("\\s");
					if (properties != null)
					{
						Point pt = new Point(Integer.parseInt(properties[STORM_COL]), Integer.parseInt(properties[STORM_ROW]));
						String name = properties[STORM_NAME];
						if (world.is_empty(pt))
						{
							world.add_entity(new Storm(name, pt, STORM_IMG));
						}
						Point temp= new Point(pt.getX()-1, pt.getY());
						if (world.is_empty(temp)&& i<11)
						{
							Wyvern dragon = new Wyvern("dragon", temp, Schedules.WYVERN_RATE, Load.WYVERN_IMG, Schedules.WYVERN_ANIMATION_RATE);
							world.add_entity(world.createLightning(temp, dragon, ticks));
						}
						
					}
					
				}
				i = 0;
				reader.close();
			}
			
			
			
			
					
					
			public static Entity CreateMiner(String[] properties)
			{
				if (properties.length== MINER_NUM_PROPERTIES)
				{
					Entity miner = new MinerNotFull(properties[MINER_NAME],
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
				if (properties.length== OBSTACLE_NUM_PROPERTIES)
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
			
					
			public static void imageLoad(String imagelist, PApplet screen)
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
					imageSort(line, screen);
					
					
				}
				reader.close();
			}
			
			private static final int COLOR_MASK = 0xffffff;

			   // Called with color for which alpha should be set and alpha value.
			   //PImage img = setAlpha(loadImage("wyvern1.bmp"), color(255, 255, 255), 0));
			public static PImage setAlpha(PImage img, int maskColor, int alpha)
			{
			      int alphaValue = alpha << 24;
			      int nonAlpha = maskColor & COLOR_MASK;
			      img.format = PApplet.ARGB;
			      img.loadPixels();
			      for (int i = 0; i < img.pixels.length; i++)
			      {
			         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
			         {
			            img.pixels[i] = alphaValue | nonAlpha;
			         }
			      }
			      img.updatePixels();
			      return img;
			}
			   
			public static void imageSort(String[] line, PApplet screen)
			{
				int type = Integer.parseInt(line[0]);
				PImage temp;
				PImage temp2;
				String name= line[1].trim();
				switch (type)
				{
				case 1:
					temp = screen.loadImage(name);
					
					Load.SMITH_IMG.add(temp);
					break;
				case 2:
					temp = screen.loadImage(name);
					temp2= Load.setAlpha(temp, screen.color(255, 255, 255), 0);
					Load.BLOB_IMG.add(temp2);
					break;
				case 3:
					temp = screen.loadImage(name);
					Load.BGND_GRASS_IMG.add(temp);
					break;
				case 4:
					temp = screen.loadImage(name);
					temp2= Load.setAlpha(temp, screen.color(252, 252, 252), 0);
					Load.MINER_IMG.add(temp2);
					break;
				case 5:
					temp = screen.loadImage(name);
					Load.OBSTACLE_IMG.add(temp);
					break;
				case 6:
					temp = screen.loadImage(name);
					temp2= Load.setAlpha(temp, screen.color(201, 26, 26), 0);
					Load.ORE_IMG.add(temp2);
					break;
				case 7:
					temp = screen.loadImage(name);
					temp2= Load.setAlpha(temp, screen.color(255, 255, 255), 0);
					Load.QUAKE_IMG.add(temp2);
					break;
				case 8:
					temp = screen.loadImage(name);
					Load.BGND_ROCK_IMG.add(temp);
					break;
				case 9:
					temp = screen.loadImage(name);
					Load.VEIN_IMG.add(temp);
					break;
				case 10:
					temp = screen.loadImage(name);
					Load.LIGHTNING_IMG.add(temp);
					break;
				case 11:
					temp = screen.loadImage(name);
					Load.STORM_IMG.add(temp);
					break;
				case 12:
					temp = screen.loadImage(name);
					Load.STORM_MINER_IMG.add(temp);
					break;
				case 13:
					temp = screen.loadImage(name);
					Load.FLAME_IMG.add(temp);
					break;
				case 14:
					temp = screen.loadImage(name);
					temp2= Load.setAlpha(temp, screen.color(255, 255, 255), 0);
					Load.WYVERN_IMG.add(temp2);
					break;
				}
			}	
			
}