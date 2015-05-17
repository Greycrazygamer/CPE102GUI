package projdata;

import worldloaders.Action;

public class ListItem
{
	private Action item;
	private long ord;
	
	public ListItem(Action item, long ord)
	{
		this.item= item;
		this.ord= ord;
	}
	
	public boolean __eq__( ListItem b)
	{
		return this.item == b.item && this.ord == b.ord;
	}
	
	public long getOrd()
	{
		return this.ord;
	}
	
	
	public Action getItem()
	{
		return this.item;
	}
}
