package projdata;
import java.lang.reflect.Array;

import projdata.Point;
import worldobject.Background;
import worldobject.WorldObject;
import worldobject.entities.Entity;


public class Grid
{
	private final int EMPTY = 0;
	private final int GATHERER= 1;
	private final int GENERATOR = 2;
	private final int RESOURCE = 3;
	
	private int width;
	private int height;
	private WorldObject[][] cells;
	
	public Grid(int width, int height, WorldObject occupany_value)
	{
		this.width= width;
		this.height= height;
		this.cells = new WorldObject[height][width];
		
		for (int i=0; i < height; i++)
		{
			for (int j=0; j <width; j++)
			{
				this.cells[i][j] = occupany_value;
			}
		}
	}
	
	public void setCell(Point pt, WorldObject bgnd)
	{
		this.cells[pt.getY()][pt.getX()] = bgnd;
	}
	
	public WorldObject getCell(Point pt)
	{
		return this.cells[pt.getY()][pt.getX()];
	}
	
	
}
