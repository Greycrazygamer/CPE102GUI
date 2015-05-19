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
	public void insert(Action item, long time)
	{
		int size= this.list.size();
		
		int idx = 0;
		if (this.list.size() >0)
			{
			System.out.println(this.list.get(idx).getOrd());
			}
			
		while (idx < size && this.list.get(idx).getOrd() < time)
		{
			
			idx += 1;
		}
		this.list.add(idx, new ListItem(item, time));
		
	}
	
	public void remove(Action item)
	{
//		System.out.println("remove");
		int size= this.list.size();
		int idx = 0;
		while (idx < size && !this.list.get(idx).getItem().equals(item))
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
		{
			System.out.println("nothing");
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
