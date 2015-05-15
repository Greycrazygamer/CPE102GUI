package projdata;
import java.util.ArrayList;
import java.util.List;

import projdata.ListItem;

public class OrderedList
{
	private ArrayList<ListItem> list;
	
	public OrderedList()
	{
		this.list = new ArrayList<ListItem>();
	}
	
	public void insert(Object item, int ord)
	{
		int size= this.list.size();
		int idx = 0;
		while (idx < size && this.list.get(idx).getOrd() < ord)
		{
			idx += 1;
		}
		
		
	}
	
	public void remove(Object item)
	{
		int size= this.list.size();
		int idx = 0;
		while (idx < size && this.list.get(idx).getItem() != item)
		{
			idx += 1;
		}
		if (idx < size)
		{
			list.subList(idx, idx+1).clear();
		}
	}
	
	
}
