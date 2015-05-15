//package worldobject;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import projdata.Point;
//import projdata.Types;
//
//import worldmodel.WorldModel;
//import worldobject.entities.Entity;
//import worldobject.entities.action.Actionable;
//import worldobject.entities.action.mover.Mover;
//import worldobject.entities.action.mover.miner.Miner;
//
//public class WorldObjectTests
//{
//	private Entity rabbit= new Actionable("rabbit", new Point(2,2), null);
//	private Mover birdy= new Mover("birdy", new Point(4,5), 6, null);
//	private Background bg= new Background("test", null);
//	private Miner goffer= new Miner("goffer", 7, new Point(7,3), 0, null, 12);
//	private WorldModel wor= new WorldModel(10, 10, bg);
//	
//	@Test
//	public void testMiner_getType()
//	{
//		assertTrue(Types.MINER==goffer.getType());
//	}
//
//	@Test
//	public void testMiner_getAnimationRate()
//	{
//		assertEquals(12,goffer.getAnimationRate());
//	}
//
//	@Test
//	public void testMiner_setResourceCount()
//	{
//		goffer.setResourceCount(32);
//		assertEquals(32, goffer.getResourceCount());
//	}
//
//	@Test
//	public void testMiner_getResourceCount()
//	{
//		assertEquals(2, goffer.getResourceCount());
//	}
//
//	@Test
//	public void testMiner_getResourceLimit()
//	{
//		assertEquals(2, goffer.getResourceCount());
//	}
//
//	@Test
//	public void testMiner_nextPosition()
//	{
//		assertTrue(new Point(7,4).equals(goffer.nextPositon(wor, new Point(7,6))));
//	
//	}
//
//	@Test
//	public void testMover_getRate()
//	{
//		assertTrue(6==birdy.getRate());
//	}
//
//	@Test
//	public void testEntity_setPosition()
//	{
//		rabbit.setPosition(new Point(5,5));
//		assertTrue(rabbit.getPosition().equals(new Point(5,5)));
//	}
//
//	@Test
//	public void testEntity_getPosition()
//	{
//		assertTrue(rabbit.getPosition().equals(new Point(2,2)));
//	}
//
//	@Test
//	public void testWorldObject_getName()
//	{
//		assertEquals("birdy", birdy.getName());
//	}
//
//	@Test
//	public void testSign()
//	{
//		assertTrue(-1==rabbit.sign(-25));
//	}
//}
