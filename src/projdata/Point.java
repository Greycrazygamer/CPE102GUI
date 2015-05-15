package projdata;

import java.lang.Math;

public class Point
{
	private int x;
	private int y;
	
	public Point(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}
	
	public double distance_sq(Point that)
	{
		return (Math.pow((this.getX() - that.getX()), 2) 
				+ Math.pow((this.getY() - that.getY()), 2));
	}
	
	public boolean adjacent(Point pt)
	{
		return ((this.getX() == pt.getX() && Math.abs(this.getY() - pt.getY()) == 1) || 
				(this.getY() == pt.getY() && Math.abs(this.getX() - pt.getX()) == 1 ));
	}
	
	public boolean equals(Point pt)
	{
		if (this.getX() == pt.getX() && this.getY() == pt.getY())
		{
			return true;
		}
		else{
			return false;
		}
	}
}
