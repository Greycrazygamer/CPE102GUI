package projdata;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import projdata.*;

import worldobject.entities.Entity;
import worldobject.entities.action.mover.Mover;

public class DataTests
{
	Entity birdy= new Mover("birdy", new Point(3,3), 5, null);
	Grid gridy= new Grid(10, 10, null);
	
	@Test
	public void testPTgetX()
	{
		Point pt= new Point(2,5);
		assertEquals(2, pt.getX());
	}
	@Test
	public void testPTgetY()
	{
		Point pt= new Point(2,5);
		assertEquals(5, pt.getY());
	}
	@Test
	public void testPTdistance_sq()
	{
		Point pt1 = new Point(1,1);
		Point pt2 = new Point(4,5);
		assertEquals(25, pt1.distance_sq(pt2), .000000001);
	}
	@Test
	public void testPT_adjacent()
	{
		Point pt1 = new Point(1,1);
		Point pt2 = new Point(1,2);
		assertTrue(pt1.adjacent(pt2));
	}
	

	
	
	@Test
	public void testGrid_setCell()
	{
		gridy.setCell(new Point(3,3), birdy);
		assertEquals(birdy, gridy.getCell(new Point(3,3)));
	}
	@Test
	public void testGrid_getCell()
	{
		gridy.setCell(new Point(3,3), birdy);
		assertEquals(birdy, gridy.getCell(new Point(3,3)));
	}
	
	
}
