package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
	private static Image img;
	private DroneComponent drone;
	private AirplaneComponent planes;
	private BulletComponent bullets;
	
	public static void setImage(Image img) { GamePanel.img = img; }
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		drone.paint(g);
		planes.paint(g);
		bullets.paint(g);
	}
	
	public void move()
	{
		drone.move();
		planes.move();
		bullets.move();
	}
	
	public void checkCollisions()
	{
		drone.checkCollisions(planes);
		bullets.checkCollisions(planes);
	}
	
	public void spawnTarget() { planes.spawn(); }
	
	public void spawnBullet() { drone.shoot(); }
	
	public GamePanel(Dimension dimensions)
	{
		//Initialize game components
		planes = new AirplaneComponent(dimensions);
		bullets = new BulletComponent(dimensions);
		drone = new DroneComponent(dimensions, bullets);
		//Add all game components
		add(drone);
		add(planes);
		add(bullets);
		//Set panel size
        setMinimumSize(dimensions);
		setPreferredSize(dimensions);
		setMaximumSize(dimensions);
	}
}

