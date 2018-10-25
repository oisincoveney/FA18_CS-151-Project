package test;
import java.awt.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
	private static final int PANEL_WIDTH = 1000, PANEL_HEIGHT = 600;
	private AirplaneComponent airplanes = new AirplaneComponent();
	private BulletComponent bullets = new BulletComponent();
	private DroneObject drone; // = new DroneObject();
	
	//Consider re-coding in a way to take the code off the panel, changing GameComponent to an interface
	private class AirplaneComponent extends GameComponent
	{
		private static final float AIRPLANE_SPEED = 1;
		private static final int NUM_IMAGES = 4;
		private Random rnd = new Random();
		
		//Consider moving to GameComponent as checkCollisions(GameObject o), consider issue of updating score
		public void checkCollisions()
		{
			Iterator<GameObject> i = objects.iterator();
			while (i.hasNext())
			{
				GameObject airplane = i.next();
				if (airplane.intersects(drone))
				{
					i.remove();
					//SCORE HANDLING STUFF
				}
			}
		}
		
		public void spawn()
		{
			//Y needs to be changed to be more accurate depending on panel height and image height
			objects.addLast(new AirplaneObject(rnd.nextInt(NUM_IMAGES - 1), GamePanel.this.getWidth() + 20, rnd.nextInt(GamePanel.this.getHeight() - 60) + 10, AIRPLANE_SPEED));
		}
		
		private Image[] loadImages()
		{
			Image[] images = new Image[NUM_IMAGES];
			try
			{
				for (int n = 0; n < NUM_IMAGES; n++) images[n] = ImageIO.read(new File("assets/plane" + n + ".png"));
			} catch (IOException e) { e.printStackTrace(); }
			return images;
		}
		
		public AirplaneComponent()
		{
			objects = new LinkedList<GameObject>();
			AirplaneObject.setImages(loadImages());
		}
	}
	
	//Consider re-coding in a way to take the code off the panel
	//May be issue despawning airplane and bullet at same time
	private class BulletComponent extends GameComponent
	{
		private static final float BULLET_SPEED = 1;
		
		//Consider moving to GameComponent as checkCollisions(LinkedList<GameObject> o2), consider issue of tracking number killed
		public void checkCollisions()
		{
			Iterator<GameObject> i = objects.iterator();
			while (i.hasNext())
			{
				GameObject bullet = i.next();
				Iterator<GameObject> i2 = airplanes.objects.iterator();
				while (i2.hasNext())
				{
					GameObject airplane = i2.next();
					if (bullet.intersects(airplane))
					{
						i2.remove();
						i.remove();
						break;
					}
				}
			}
		}
		
		public void spawn()
		{
			//Y needs to be changed to be more accurate depending on drone image height and bullet image height
			objects.addLast(new BulletObject(drone.getRight(), drone.getTop(), BULLET_SPEED));
		}
		
		public BulletComponent()
		{
			objects = new LinkedList<GameObject>();
			BulletObject.setMax(GamePanel.this.getWidth());
		}
	}
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		airplanes.paint(g);
		bullets.paint(g);
	}
	
	public void update()
	{
		//should run from the timer 
		//PLAYER CANNOT MOVE
		airplanes.move();
		bullets.move();
		//CHECK COLLISIONS
		//STUFF
	}
	
	public GamePanel(int height, int width)
	{
		//CONSIDER ISSUE OF RESIZING FRAME: BEST TO RESTRICT
		//Could add listener to update h/w
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setBackground(Color.cyan); //NEEDS TO BE CHANGED TO THE BG IMAGE from bgPlane
		add(airplanes);
		add(bullets);
		add(drone);
	}
}