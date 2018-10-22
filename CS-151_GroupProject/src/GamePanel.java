import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 600;
	private static AirplaneManager airplaneManager;
	private static ThreadManager threadManager;
	
	private class AirplaneManager extends JComponent implements ObjectManager
	{
		ArrayList<AirplaneObject> airplanes;
		
		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			for (int n = 0; n < airplanes.size(); n++)
			{
				airplanes.get(n).draw(g2);
			}
		}

		public void move()
		{
			int maxX = GamePanel.this.getWidth();
			for (int n = 0; n < airplanes.size(); n++)
			{
				AirplaneObject airplane = airplanes.get(n);
				Rectangle bounds = airplane.getBounds();
				airplane.translate((bounds.x > maxX) ? -(maxX + bounds.getWidth()) : airplane.velocity*Math.cos(airplane.angle), airplane.velocity*Math.sin(airplane.angle));
			}
		}
		
		public AirplaneManager(int numAirplanes)
		{
			Random rnd = new Random();
			try
			{
				BufferedImage img = ImageIO.read(new File("src/images/plane.png"));
				airplanes = new ArrayList<>(numAirplanes);
				for (int n = 0; n < numAirplanes; n++)
				{
					airplanes.add(new AirplaneObject(img, rnd.nextInt(PANEL_WIDTH) - PANEL_WIDTH - img.getWidth(), rnd.nextInt(PANEL_HEIGHT - 100) + 10, 0.1 * (rnd.nextInt(6) + 10), 0.0));
				}
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	private class ThreadManager extends Thread implements Runnable
	{
		private final int sleepDelay;

		public void run()
		{
			while (true)
			{
				airplaneManager.move();
				repaint();
				try { Thread.sleep(sleepDelay); } catch (InterruptedException e) { e.printStackTrace(); }
			}	
		}
		
		public ThreadManager(int sleepDelay)
		{
			this.sleepDelay = sleepDelay;
		}
	}
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		airplaneManager.paint(g);
	}
	
	public GamePanel()
	{
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setBackground(Color.cyan);
		airplaneManager = new AirplaneManager(8);
		threadManager = new ThreadManager(10);
		threadManager.start();
		add(airplaneManager);
	}
}
