public interface GameObject
{
	void draw(java.awt.Graphics g);
	
	java.awt.Rectangle getBounds();
	
	void translate(double dx, double dy);
}