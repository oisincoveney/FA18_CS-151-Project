package main;
import java.awt.*;
import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public abstract class GameComponent extends JComponent
{
	protected LinkedList<GameObject> objects;
	
	public void move()
	{
		Iterator<GameObject> i = objects.iterator();
		while (i.hasNext())
		{
			GameObject obj = i.next();
			if (obj.checkBounds()) obj.move();
			else i.remove();
		}
	}
	
	public boolean checkCollisions(GameObject other)
	{
		Iterator<GameObject> iter = objects.iterator();
		while (iter.hasNext())
		{
			GameObject obj = iter.next();
			if (obj.intersects(other))
			{
				iter.remove();
				return true;
			}
		}
		return false;
	}
	
	public abstract void spawn(int x, int y);
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for (GameObject obj : objects) obj.draw(g2);
	}
}