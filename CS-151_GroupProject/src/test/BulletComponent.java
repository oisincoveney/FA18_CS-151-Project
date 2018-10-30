package test;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class BulletComponent extends JComponent implements GameComponent
{
	private LinkedList<BulletObject> bullets;
	private final Dimension panelDimensions;
	private final double BULLET_SPEED = 4;
	private Random rnd = new Random();
	
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
	
	public void spawn()
	{
		/* TEMP */
		//Will need to spawn on the player
		int x = 40, y = rnd.nextInt(panelDimensions.height - 100) + 10;
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
	
	public BulletComponent(Dimension panelDimensions)
	{
		this.panelDimensions = panelDimensions;
		BulletObject.setMax(panelDimensions.width + 20);
		bullets = new LinkedList<BulletObject>();
	}
}