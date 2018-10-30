package main;
import java.awt.Graphics2D;
import java.awt.Image;

public class BulletObject implements GameObject
{
	private static int maxX;
	private static Image img;
	private double x, y;
	private double v;
	
	public static void setMax(int maxX) { BulletObject.maxX = maxX; }
	
	public static void setImage(Image img) { BulletObject.img = img; }
	
	public void draw(Graphics2D g2) { g2.drawImage(img, (int)x, (int)y, null); }

	public void move() { x += v; }
	
	public double getLeft() { return x; }

	public double getRight() { return x + img.getWidth(null); }

	public double getTop() { return y; }

	public double getBottom() { return y + img.getHeight(null); }
	
	public boolean checkBounds() { return (getLeft() < maxX); }
	
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
