package main;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class BulletComponent extends GameComponent
{
	private final double BULLET_SPEED = 1;
	
	public void spawn(int x, int y)
	{
		//Y needs to be changed to be more accurate depending on drone image height and bullet image height
		objects.addLast(new BulletObject(x, y, BULLET_SPEED));
	}
	
	public int checkCollisions(AirplaneComponent planes)
	{
		int numCollisions = 0;
		Iterator<GameObject> iter = objects.iterator();
		while (iter.hasNext())
		{
			GameObject bullet = iter.next();
			if (planes.checkCollisions(bullet))
			{
				iter.remove();
				numCollisions++;
			}
		}
		return numCollisions;
	}
	
	public BulletComponent()
	{
		objects = new LinkedList<GameObject>();
	}
}