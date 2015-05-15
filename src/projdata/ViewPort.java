package projdata;

public class ViewPort
{

	private int width;
	private int height;
	private Point topLeft;
	
	public ViewPort(int x, int y, int width, int height)
	{
		this.topLeft= new Point(x,y);
		this.width= width;
		this.height= height;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Point getTopLeft()
	{
		return topLeft;
	}

	public boolean containsPoint(Point entPt)
	{
		return (topLeft.getX()<= entPt.getX() && entPt.getX() <= width
		&& topLeft.getY()<= entPt.getY() && entPt.getY() <= height);
	}
}
