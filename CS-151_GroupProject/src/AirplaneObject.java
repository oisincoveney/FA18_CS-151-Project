import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class AirplaneObject implements GameObject
{
	final BufferedImage img;
	Point2D.Double position;
	double velocity, angle;

	public void draw(Graphics g)
	{
		g.drawImage(img, (int)position.x, (int)position.y, null);
	}
	
	public void translate(double dx, double dy)
	{
		position.x += dx;
		position.y += dy;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)position.x, (int)position.y, img.getWidth(), img.getHeight());
	}
	
	public AirplaneObject(BufferedImage img, int x, int y, double velocity, double angle)
	{
		this.img = img;
		this.position = new Point2D.Double(x, y);
		this.velocity = velocity;
		this.angle = angle;
	}
}