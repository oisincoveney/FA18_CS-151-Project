package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class BulletComponent extends JComponent implements GameComponent
{
	private LinkedList<BulletObject> bullets;
	private Dimension panelDimensions;
	private final double MIN_BULLET_SPEED = 0.5;
	private final double MAX_BULLET_SPEED = 4.0;
	private final double BULLET_ACCELERATION = 0.05;
	private Image img;
	
	protected class BulletObject implements GameObject
	{
		private double x, y;
		private double v;
		
		public void draw(Graphics2D g2) { g2.drawImage(img, (int)x, (int)y, null); }

		public void move()
		{
			if (v < MAX_BULLET_SPEED) v += BULLET_ACCELERATION;
			x += v;
		}
		
		public double getLeft() { return x; }

		public double getRight() { return x + img.getWidth(null); }

		public double getTop() { return y; }

		public double getBottom() { return y + img.getHeight(null); }
		
		public boolean checkBounds() { return (getLeft() < panelDimensions.width / 2); }
		
		public boolean intersects(GameObject o)
		{
			return getLeft() < o.getRight() && getRight() > o.getLeft() &&
					getBottom() > o.getTop() && getTop() < o.getBottom();
		}

		public BulletObject(double x, double y, double v)
		{
			this.x = x;
			this.y = y;
			this.v = v;
		}
	}
	
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
		bullets.addLast(new BulletObject(x, y, MIN_BULLET_SPEED));
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
	
	public void setImage(Image img) { this.img = img; }
	
	public void init()
	{
		bullets = new LinkedList<BulletObject>();
	}
	
	public BulletComponent(Dimension panelDimensions)
	{
		this.panelDimensions = panelDimensions;
		init();
	}
}