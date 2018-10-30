package test;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class DroneComponent extends JComponent implements GameComponent
{
	private final BulletComponent bullets;
	private final double DRONE_SPEED = 1;
	private DroneObject drone;
	
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
	
	public DroneComponent(Dimension panelDimensions, BulletComponent bullets)
	{
		this.bullets = bullets;
		DroneObject.setMax(panelDimensions.height - 20);
		spawn(20, (panelDimensions.height / 2) - 100);
	}
}
