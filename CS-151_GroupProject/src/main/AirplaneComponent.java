package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class AirplaneComponent extends JComponent implements GameComponent
{
	private LinkedList<AirplaneObject> planes;
	private final Dimension panelDimensions;
	private final double MAX_SPEED = 1.2;
	private Random rnd = new Random();
	private int spawnDir = 1;
	
	public void move()
	{
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (plane.checkBounds()) plane.move();
			else i.remove();
		}
	}
	
	public void spawn()
	{
		spawn(panelDimensions.width + rnd.nextInt(200) + 60, spawnDir * rnd.nextInt((panelDimensions.height / 2) - 30) + (panelDimensions.height / 2) - 20);
		spawnDir = -spawnDir;
	}
	
	public void spawn(int x, int y)
	{
		planes.addLast(new AirplaneObject(rnd.nextInt(AirplaneObject.imgCount()), x, y, MAX_SPEED - (rnd.nextInt(4) * 0.1)));
	}
	
	
	public boolean checkCollisions(GameObject obj)
	{
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (plane.intersects(obj))
			{
				i.remove();
				return true;
			}
		}
		return false;
	}
	
	public int checkCollisions(GameComponent comp)
	{
		int numCollisions = 0;
		Iterator<AirplaneObject> i = planes.iterator();
		while (i.hasNext())
		{
			GameObject plane = i.next();
			if (comp.checkCollisions(plane))
			{
				i.remove();
				numCollisions++;
			}
		}
		return numCollisions;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for (GameObject plane : planes) plane.draw(g2);
	}
	
	public AirplaneComponent(Dimension panelDimensions)
	{
		this.panelDimensions = panelDimensions;
		planes = new LinkedList<AirplaneObject>();
	}
}