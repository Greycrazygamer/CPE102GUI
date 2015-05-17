package projdata;
import java.util.ArrayList;
import java.util.List;

import projdata.ListItem;
import worldloaders.Action;

public class OrderedList
{
	private ArrayList<ListItem> list;
	
	public OrderedList()
	{
		this.list = new ArrayList<>();
	}
	
	public void insert(Action item, long time)
	{
		int size= this.list.size();
		int idx = 0;
		while (idx < size && this.list.get(idx).getOrd() < time)
		{
			idx += 1;
		}
		this.list.add(idx, new ListItem(item, time));
		
	}
	
	public void remove(Action item)
	{
		int size= this.list.size();
		int idx = 0;
		while (idx < size && this.list.get(idx).getItem() != item)
		{
			idx += 1;
		}
		if (idx < size)
		{
			list.set(idx, null);
		}
	}
	
	public ListItem head()
	{
		if (this.list != null)
		{
		 return this.list.get(0);
		}
		else
		{return null;}
	}
	
	public ListItem pop()
	{
		if (this.list != null)
		{
			ListItem temp= this.list.get(0);
			this.list.remove(0);
			return temp;
		}
		else
		{return null;}
	}
}
