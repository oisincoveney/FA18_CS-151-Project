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
        protected boolean moveUp, moveDown, moveLeft, moveRight;

        public void draw(Graphics2D g2) { g2.drawImage(img, (int) x, (int) y, null); }

        public void move()
        {
        	int yDir = (moveUp) ? (moveDown ? 0 : -1) : (moveDown ? 1 : 0);
        	int xDir = (moveLeft) ? (moveRight ? 0 : -1) : (moveRight ? 1 : 0);
        	if ((yDir == -1 && getTop() >= 10) || (yDir == 1 && getBottom() <= panelDimensions.height - 10)) y += v * yDir;
        	if ((xDir == -1 && getLeft() >= 20) || (xDir == 1 && getRight() <= panelDimensions.width / 4)) x += v * xDir / 2;
    	}

        public double getLeft() { return x; }

        public double getRight() { return x + img.getWidth(null); }

        public double getTop() { return y; }

        public double getBottom() { return y + img.getHeight(null); }
        
        public boolean checkBounds() { return true; }

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

    public void move() { drone.move(); }

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

    public DroneComponent(Dimension panelDimensions)
    {
        this.panelDimensions = panelDimensions;
        //this.bullets = bullets;
        spawn(20, (panelDimensions.height / 2) - 100);
    }
}
