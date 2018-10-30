package test;

public interface GameComponent
{
	public void move();
	
	public void spawn(int x, int y);
	
	public boolean checkCollisions(GameObject obj);
	
	public int checkCollisions(GameComponent comp);
	
	public void paint(java.awt.Graphics g);
}