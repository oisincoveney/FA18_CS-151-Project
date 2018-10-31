package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class DroneComponent extends JComponent implements GameComponent
{
	private DroneObject drone;
	private BulletComponent bullets;
	private Dimension panelDimensions;
	private double DRONE_SPEED = 1;
	private Image img;
	
	private class DroneObject implements GameObject
	{
		private double x, y;
		private double v;
		
		public void draw(Graphics2D g2) { g2.drawImage(img, (int)x, (int)y, null); }

		public void move() { y += v; }
		
		public void changeDir() { v = -v; }
		
		public double getLeft() { return x; }

		public double getRight() { return x + img.getWidth(null); }

		public double getTop() { return y; }

		public double getBottom() { return y + img.getHeight(null); }
		
		public boolean checkBounds() { return (getTop() >= 10 && getBottom() <= panelDimensions.height - 10); }
		
		public boolean intersects(GameObject o)
		{
			return getLeft() < o.getRight() && getRight() > o.getLeft() &&
					getBottom() > o.getTop() && getTop() < o.getBottom();
		}

		public DroneObject(double x, double y, double v)
		{
			this.x = x;
			this.y = y;
			this.v = v;
		}
	}
	
	public void move()
	{
		if (!drone.checkBounds()) drone.changeDir();
		drone.move();
	}
	
	public void shoot()
	{
		bullets.spawn((int) drone.getRight() - 60, (int) drone.getBottom() - 4);
	}	
	
	public void spawn(int x, int y)
	{
		drone = new DroneObject(x, y, DRONE_SPEED);
	}
	
	public boolean checkCollisions(GameObject obj)
	{
		return drone.intersects(obj);
	}
	
	public int checkCollisions(GameComponent comp)
	{
		return (comp.checkCollisions(drone)) ? 1 : 0;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		drone.draw(g2);
	}
	
	public void setImage(Image img) { this.img = img; }
	
	public DroneComponent(Dimension panelDimensions, BulletComponent bullets)
	{
		this.panelDimensions = panelDimensions;
		this.bullets = bullets;
		spawn(20, (panelDimensions.height / 2) - 100);
	}
}
