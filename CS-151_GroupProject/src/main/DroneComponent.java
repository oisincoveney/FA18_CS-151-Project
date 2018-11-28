package main;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings ("serial")
public class DroneComponent extends JComponent implements GameComponent
{
    protected DroneObject drone;
    private Dimension panelDimensions;
    private double DRONE_SPEED = 1;
    private Image img;
    private boolean blink = false;
    private int blinkTimer = 0;
    private final int BLINK_DURATION = 20;


    protected class DroneObject implements GameObject
    {
        private double x, y;
        private double v;
        private boolean moveUp, moveDown;

        public void draw(Graphics2D g2) { g2.drawImage(img, (int) x, (int) y, null); }

        public void move()
        {
        	int dir = (moveUp) ? (moveDown ? 0 : -1) : (moveDown ? 1 : 0);
        	y += v * dir;
    	}

        public double getLeft() { return x; }

        public double getRight() { return x + img.getWidth(null); }

        public double getTop() { return y; }

        public double getBottom() { return y + img.getHeight(null); }
        
        public boolean checkBounds()
        {
        	if (moveUp) return getTop() >= 10;
        	if (moveDown) return getBottom() <= panelDimensions.height - 10;
            return false;
        }

        public boolean intersects(GameObject o)
        {
            return getLeft() < o.getRight() && getRight() > o.getLeft() &&
                    getBottom() > o.getTop() && getTop() < o.getBottom();
        }

        public DroneObject(double x, double y, double v)
        {
            this.x = x;
            this.y = y;
            this.v = v;
        }
    }

    public void move() { if (drone.checkBounds()) drone.move(); }

    public void spawn(int x, int y) { drone = new DroneObject(x, y, DRONE_SPEED); }

    public boolean checkCollisions(GameObject obj) { return drone.intersects(obj); }

    public int checkCollisions(GameComponent comp) { return (comp.checkCollisions(drone)) ? 1 : 0; }

    public void paint(Graphics g)
    {
        if (blink)
        {
            blinkTimer++;
            if (blinkTimer >= BLINK_DURATION)
            {
                blinkTimer = -BLINK_DURATION;
            }
            if (blinkTimer < 0)
            {
                return;
            }
        }
        Graphics2D g2 = (Graphics2D) g;
        drone.draw(g2);
    }

    public void setImage(Image img) { this.img = img; }

    public void setBlink(boolean blink) { this.blink = blink; }
    
    public void setDir(int dir, boolean event)
    {
    	if (dir == -1) drone.moveUp = event;
    	else if (dir == 1) drone.moveDown = event;
	}

    public DroneComponent(Dimension panelDimensions)
    {
        this.panelDimensions = panelDimensions;
        //this.bullets = bullets;
        spawn(20, (panelDimensions.height / 2) - 100);
    }
}
