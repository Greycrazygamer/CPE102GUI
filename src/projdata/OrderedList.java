package projdata;
import java.io.FileNotFoundException;
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
	
	public ArrayList<ListItem> getList()
	{
		return this.list;
	}
	
	public int getSize()
	{
		return this.list.size();
	}
	public void insert(Action item, long ord)
	{
		
		int size= this.list.size();
		int idx = 0;
					
		while (idx < size && this.list.get(idx).getOrd() < ord)
		{
			idx += 1;
		}
		this.list.add(idx, new ListItem(item, ord));
		
	}
	
	public void remove(Action item)
	{
//		System.out.println("remove");
		int size= this.list.size();
		int idx = 0;
		while (idx < size && this.list.get(idx).getItem() != (item))
		{
			idx += 1;
		}
		if (idx < size)
		{
			list.remove(idx);
		}
	}
	
	public ListItem head()
	{
		
		if (this.list != null)
		{
		 return this.list.get(0);
		}
		else
		{
			return null;
		}
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
