package main;

import java.awt.*;

public class BackgroundComponent
{
	private final int BOUNDS = 1200;
	private double animationSpeed = 0.6;
    private BackgroundObject[] bg;
    private Image img;
    
    public void setImage(Image img) { this.img = img; }
    
    public void setAnimationSpeed(double animationSpeed) { this.animationSpeed = animationSpeed; }
    
    public void move()
    {
       bg[0].move();
       bg[1].move();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        bg[0].draw(g2);
        bg[1].draw(g2);
    }

    private class BackgroundObject
    {
        private double x;
        
        public void draw(Graphics2D g2) { g2.drawImage(img, (int)x, 0, null); }
        
        public void move()
        {
        	 if (x <= -BOUNDS) x = BOUNDS;
             x -= animationSpeed;
        }
        
        public BackgroundObject(int x) { this.x = x; }
    }

    public BackgroundComponent()
    {
        bg = new BackgroundObject[] { new BackgroundObject(0), new BackgroundObject(1200) };
    }
}
