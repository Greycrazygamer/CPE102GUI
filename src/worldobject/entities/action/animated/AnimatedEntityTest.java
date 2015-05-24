package worldobject.entities.action.animated;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Test;

import processing.core.PImage;
import projdata.Node;
import projdata.PathGrid;
import projdata.Point;

public class AnimatedEntityTest
{
	PathGrid grid = new PathGrid(5,4);
	AnimatedEntity boo= new Blob("boo", new Point(3,1), 0, null, 0);
	LinkedHashSet<Node> openset = new LinkedHashSet<Node>();
	@Test
	public void testAStar()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testlowestF()
	{
		openset.add(new Node(3,1, 0, 4));
		assertEquals(4, boo.lowestF(openset).getfValue(), 0.0001);
	}

}
