package test;
import java.awt.Graphics2D;
import java.awt.Image;

public class DroneObject implements GameObject
{
	private static int minY = 10, maxY;
	private static Image img;
	private double x, y;
	private double v;
	
	public static void setMax(int maxY) { DroneObject.maxY = maxY; }
	
	public static void setImage(Image img) { DroneObject.img = img; }
	
	public void draw(Graphics2D g2) { g2.drawImage(img, (int)x, (int)y, null); }

	public void move() { y += v; }
	
	public void changeDir() { v = -v; }
	
	public double getLeft() { return x; }

	public double getRight() { return x + img.getWidth(null); }

	public double getTop() { return y; }

	public double getBottom() { return y + img.getHeight(null); }
	
	public boolean checkBounds() { return (getTop() >= minY && getBottom() <= maxY); }
	
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
