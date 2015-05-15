package worldloaders;

import processing.core.PApplet;

public class Controller
{

	public static int[] keyPresses(PApplet screen)
	{
		char key = screen.key;
		int deltaX= 0;
		int deltaY= 0;
		int[] delta = new int[1];
		switch(key)
		{
		case 'w':
			deltaY++;
			break;
		case 'a':
			deltaX--;
			break;
		case 's':
			deltaY--;
			break;
		case 'd':
			deltaX++;
			break;
		}
		delta[0]=deltaX;
		delta[1]=deltaY;
		return delta;
	}
}
