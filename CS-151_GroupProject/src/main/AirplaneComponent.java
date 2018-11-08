package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class AirplaneComponent extends JComponent implements GameComponent
{
	private LinkedList<AirplaneObject> planes;
	private Dimension panelDimensions;
	private double MAX_SPEED = 1.2;
	private double speedFactor = 1.0;
	private Image[] images;
	private Random rnd = new Random();
	private int spawnDir = 1;
	
	private class AirplaneObject implements GameObject
	{
		private int planeType;
		private double x, y;
		private double v;
		
		public void draw(Graphics2D g2) { g2.drawImage(images[planeType], (int)x, (int)y, null); }

		public void move() { x -= v * speedFactor; }
		
		public double getLeft() { return x; }

		public double getRight() { return x + images[planeType].getWidth(null); }

		public double getTop() { return y; }

		public double getBottom() { return y + images[planeType].getHeight(null); }
		
		public boolean checkBounds() { return (getRight() > 0); } 
		
		public boolean intersects(GameObject o)
		{
			return getLeft() < o.getRight() && getRight() > o.getLeft() &&
					getBottom() > o.getTop() && getTop() < o.getBottom();
		}

		public AirplaneObject(int imgIndex, double x, double y, double v)
		{
			this.planeType = imgIndex;
			this.x = x;
			this.y = y;
			this.v = v;
		}
	}
	
	public void move()
	{
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (plane.checkBounds()) plane.move();
			else i.remove();
		}
	}
	
	public void spawn()
	{
		spawn(panelDimensions.width + rnd.nextInt(200) + 60, spawnDir * rnd.nextInt((panelDimensions.height / 2) - 30) + (panelDimensions.height / 2) - 20);
		spawnDir = -spawnDir;
	}
	
	public void spawn(int x, int y)
	{
		planes.addLast(new AirplaneObject(rnd.nextInt(images.length), x, y, MAX_SPEED - (rnd.nextInt(4) * 0.1)));
	}
	
	
	public boolean checkCollisions(GameObject obj)
	{
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (plane.intersects(obj))
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
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (comp.checkCollisions(plane))
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
		for (GameObject plane : planes) plane.draw(g2);
	}
	
	public void setSpeedFactor(double speedFactor) { this.speedFactor = speedFactor; }
	
	public void setImages(Image[] images) { this.images = images; }
	
	public AirplaneComponent(Dimension panelDimensions)
	{
		this.panelDimensions = panelDimensions;
		planes = new LinkedList<AirplaneObject>();
	}
}