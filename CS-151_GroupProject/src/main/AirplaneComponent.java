package main;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class AirplaneComponent extends GameComponent
{
	private final double AIRPLANE_SPEED = 1;
	private final int NUM_IMAGES;
	private Random rnd = new Random();
	
	public void spawn(int x, int y)
	{
		//Y needs to be changed to be more accurate depending on panel height and image height
		objects.addLast(new AirplaneObject(rnd.nextInt(NUM_IMAGES - 1), x, y, AIRPLANE_SPEED));
	}
	
	public AirplaneComponent()
	{
		objects = new LinkedList<GameObject>();
		NUM_IMAGES = AirplaneObject.imgCount();
	}
}