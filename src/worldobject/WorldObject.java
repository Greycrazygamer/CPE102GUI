package worldobject;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;
import projdata.Point;

public class WorldObject
{
	private String name;
	private List<PImage> imgs;
	private int current_img;
	
	public WorldObject(String name, List<PImage> imgs)
	{
		this.name= name;
		this.imgs= imgs;
		this.current_img=0;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public List<PImage> getImages()
	{
		return this.imgs;
	}
	
	public void nextImage()
	{
		this.current_img= (this.current_img+1) % this.imgs.size();
	}
	
	public PImage getImage()
	{
		return this.imgs.get(current_img);
	}
	
	protected int sign(int d)
	{
		if (d < 0)
		{
			return -1;
		}
		else if (d>0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	
}




