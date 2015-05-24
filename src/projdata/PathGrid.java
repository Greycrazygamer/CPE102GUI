package projdata;

import worldobject.WorldObject;

public class PathGrid
{
	
	private int width;
	private int height;
	private Node[][] cells;
	
	public PathGrid(int width, int height)
	{
		this.width= width;
		this.height= height;
		this.cells = new Node[height][width];
		
		for (int i=0; i < height; i++)
		{
			for (int j=0; j <width; j++)
			{
				this.cells[i][j] = null;
			}
		}
	}
	
	public void setCell(Node node)
	{
		this.cells[node.getY()][node.getX()] = node;
	}
	
	public Node getCell(Node pt)
	{
		return this.cells[pt.getY()][pt.getX()];
	}
	
	
}
