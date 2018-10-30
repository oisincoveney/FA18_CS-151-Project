package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class BulletComponent extends JComponent implements GameComponent
{
	private LinkedList<BulletObject> bullets;
	private final double BULLET_SPEED = 1;
	
	public void move()
	{
		Iterator<BulletObject> i = bullets.iterator();
		while (i.hasNext())
		{
			GameObject bullet = i.next();
			if (bullet.checkBounds()) bullet.move();
			else i.remove();
		}
	}
	
	public void spawn(int x, int y)
	{
		//Y needs to be changed to be more accurate depending on drone image height and bullet image height
		bullets.addLast(new BulletObject(x, y, BULLET_SPEED));
	}

	public boolean checkCollisions(GameObject obj)
	{
		Iterator<BulletObject> i = bullets.iterator();
		while (i.hasNext())
		{
			GameObject bullet = i.next();
			if (bullet.intersects(obj))
			{
				i.remove();
				return true;
			}
		}
		return false;
	}
	
	public int checkCollisions(GameComponent comp)
	{
		int numCollisions = 0;
		Iterator<BulletObject> i = bullets.iterator();
		while (i.hasNext())
		{
			GameObject bullet = i.next();
			if (comp.checkCollisions(bullet))
			{
				i.remove();
				numCollisions++;
			}
		}
		return numCollisions;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for (GameObject bullet : bullets) bullet.draw(g2);
	}
	
	public BulletComponent()
	{
		bullets = new LinkedList<BulletObject>();
	}
}