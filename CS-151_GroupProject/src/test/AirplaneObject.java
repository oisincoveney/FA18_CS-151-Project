package test;
import java.awt.Graphics2D;
import java.awt.Image;

//TODO: Add code so that the scoreboard knows if the airplane got away
public class AirplaneObject implements GameObject
{
	private static int minX = 0;
	private static Image[] images;
	private int imgIndex;
	private double x, y;
	private double v;
	
	public static void setImages(Image[] images) { AirplaneObject.images = images; }
	
	public static int imgCount() { return images.length; }
	
	public void draw(Graphics2D g2) { g2.drawImage(images[imgIndex], (int)x, (int)y, null); }

	public void move() { x -= v; }
	
	public double getLeft() { return x; }

	public double getRight() { return x + images[imgIndex].getWidth(null); }

	public double getTop() { return y; }

	public double getBottom() { return y + images[imgIndex].getHeight(null); }
	
	public boolean checkBounds() { return (getRight() > minX); } 
	
	public boolean intersects(GameObject o)
	{
		return getLeft() < o.getRight() && getRight() > o.getLeft() &&
				getBottom() > o.getTop() && getTop() < o.getBottom();
	}

	public AirplaneObject(int imgIndex, double x, double y, double v)
	{
		this.imgIndex = imgIndex;
		this.x = x;
		this.y = y;
		this.v = v;
	}
}
