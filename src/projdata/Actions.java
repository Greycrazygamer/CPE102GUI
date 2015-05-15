package projdata;

import worldmodel.WorldModel;
import worldobject.entities.Entity;

public class Actions
{
	Action<String> stringAdder = (String s1, String s2) -> s1+s2;
	
	private Action<java.lang.String> addStrings(String s1, String s2) {
		return stringAdder;
		}
	
	public static void remove_entity(WorldModel world, Entity entity)
	{
		
		//	for (action: PASS
	}
}
