package test;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class DroneComponent extends JComponent implements GameComponent
{
	private final Dimension panelDimensions;
	private final double DRONE_SPEED = 1;
	private DroneObject drone;
	
	public void move()
	{
		if (!drone.checkBounds()) drone.changeDir();
		drone.move();
	}
	
	public void spawn()
	{
		drone = new DroneObject(20, (panelDimensions.height / 2) - 100, DRONE_SPEED);
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
	
	public DroneComponent(Dimension panelDimensions)
	{
		this.panelDimensions = panelDimensions;
		DroneObject.setMax(panelDimensions.height - 20);
		spawn();
	}
}
