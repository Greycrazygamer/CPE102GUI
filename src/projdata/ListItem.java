package projdata;

public class ListItem
{
	private Object item;
	private int ord;
	
	public ListItem(Object item, int ord)
	{
		this.item= item;
		this.ord= ord;
	}
	
	public boolean __eq__( ListItem b)
	{
		return this.item == b.item && this.ord == b.ord;
	}
	
	public int getOrd()
	{
		return this.ord;
	}
	
	public Object getItem()
	{
		return this.item;
	}
}
