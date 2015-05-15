package worldloaders;

import processing.core.PApplet;

public class Controller
{

	public static int[] keyPresses(char key)
	{
		int deltaX= 0;
		int deltaY= 0;
		int[] delta = new int[2];
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
