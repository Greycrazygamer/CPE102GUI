package worldmodel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import worldobject.entities.Entity;
import worldobject.entities.action.Actionable;
import worldobject.entities.action.animated.AnimatedEntity;
import worldobject.entities.action.animated.miner.Miner;

import java.util.Comparator;

import org.junit.Test;

import projdata.Point;
import projdata.Types;


public class WorldModelTest
{

	
//	private WorldModel wor= new WorldModel(10, 10, null);
//	private Entity rabbit= new Actionable("rabbit", new Point(2,2), null);
//	private Entity birdy= new AnimatedEntity("birdy", new Point(4,5), 6, null);
//	//private Entity goffer= new Miner("goffer", 0, new Point(7,3), 0, null, 0);
//	private ArrayList<Entity> listtest= new ArrayList<>();
//	
//	@Test
//	public void testWithin_bounds()
//	{
//		Point truther= new Point(3,4);
//		assertTrue(wor.within_bounds(truther));
//		
//		Point falser= new Point(11, 132);
//		assertFalse(wor.within_bounds(falser));
//	}
//
//	@Test
//	public void testIs_occupied()
//	{
//		wor.add_entity(rabbit);
//		assertTrue(wor.is_occupied(rabbit.getPosition()));
//	}
//	
//	@Test
//	public void testIs_empty()
//	{
//		wor.add_entity(rabbit);
//		wor.remove_entity(rabbit);
//		assertTrue(wor.is_empty(new Point(2,2)));
//	}
//
////	@Test
////	public void testFind_nearest()
////	{
////		wor.add_entity(birdy);
////		wor.add_entity(goffer);
////		wor.add_entity(rabbit);
////		assertTrue(goffer.equals(wor.find_nearest(new Point(1,1), Types.MINER)));
////		
////	}
//
//	@Test
//	public void testAdd_entity()
//	{
//		wor.add_entity(rabbit);
//		assertTrue(wor.is_occupied(rabbit.getPosition()));
//	}
//
//	@Test
//	public void testMove_entity()
//	{
//		wor.add_entity(rabbit);
//		System.out.println(wor.move_entity(rabbit, new Point(9,9)));
//		assertEquals(rabbit, wor.get_tile_occupant(new Point(9,9)));
//	}
//
//	@Test
//	public void testRemove_entity()
//	{
//		wor.add_entity(birdy);
//		wor.remove_entity(birdy);
//		assertEquals(null, wor.get_tile_occupant(birdy.getPosition()));
//	}
//
//	@Test
//	public void testRemove_entity_at()
//	{
//		wor.add_entity(birdy);
//		wor.remove_entity_at(new Point(4,5));
//		assertEquals(null, wor.get_tile_occupant(new Point(4,5)));
//		
//	}
//
//	
////	@Test
////	public void testGet_tile_occupant()
////	{
////		wor.add_entity(goffer);
////		assertEquals(goffer, wor.get_tile_occupant(new Point(7,3)));
////	}
////
////	@Test
////	public void testGet_entities()
////	{
////		wor.add_entity(birdy);
////		wor.add_entity(rabbit);
////		wor.add_entity(goffer);
////		listtest.add(birdy);
////		listtest.add(rabbit);
////		listtest.add(goffer);
////		assertEquals(listtest, wor.get_entities());
////	}

}
