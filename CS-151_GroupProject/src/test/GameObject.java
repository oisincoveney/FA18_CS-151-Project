package test;
import java.awt.Graphics2D;

public interface GameObject
{
	public void draw(Graphics2D g2);

	public double getLeft();

	public double getRight();

	public double getTop();

	public double getBottom();
	
	public abstract void move();
	
	public abstract boolean checkBounds();
	
	public boolean intersects(GameObject o);
}
