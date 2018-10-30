package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class DroneComponent extends JComponent implements GameComponent
{
	private final double DRONE_SPEED = 1;
	private DroneObject drone;
	
	public void move()
	{
		if (!drone.checkBounds()) drone.changeDir();
		drone.move();
	}
	
	public void spawn(int x, int y)
	{
		drone = new DroneObject(x, y, DRONE_SPEED);
	}
	
	public boolean checkCollisions(GameObject obj)
	{
		if (drone.intersects(obj)) return true;
		return false;
	}
	
	public int checkCollisions(GameComponent comp)
	{
		if (comp.checkCollisions(drone)) return 1;
		return 0;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		drone.draw(g2);
	}
	
	public DroneComponent(int x, int y)
	{
		spawn(x, y);
	}
}
