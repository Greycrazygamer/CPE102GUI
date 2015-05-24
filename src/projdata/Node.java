package projdata;

public class Node
extends Point
{
	private double fValue;
	private double gValue;
	
	public Node(int x, int y, double gValue, double fValue)
	{
		super(x, y);
		this.fValue = fValue;
		this.gValue= gValue;
		
	}
	
	public Node(Point pt, double gValue, double fValue)
	{
		super (pt.getX(), pt.getY());
		this.fValue = fValue;
		this.gValue= gValue;
	}

	public double getfValue()
	{
		return fValue;
	}
	public void setfValue(double fValue)
	{
		this.fValue = fValue;
	}

	public void setgValue(double gValue)
	{
		this.gValue = gValue;
	}

	public double getgValue()
	{
		return gValue;
	}
	
	public Point convertToPoint()
	{
		return new Point(this.getX(), this.getY());
	}
}
