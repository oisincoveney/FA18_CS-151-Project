package main;

import java.awt.*;

public class BackgroundComponent {
    private Image img;
    private BackgroundObject bg;
    private int x;

    public void setImage(Image img) { this.img = img; }

    public void spawn()
    {
        bg = new BackgroundObject();
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D g3 = (Graphics2D) g;
        bg.draw(g2,g3);
    }

    private class BackgroundObject{
        public void draw(Graphics2D g2, Graphics2D g3) {
            if (x > 1200) {
                x = 0;
            }
            x += 1;
            g2.drawImage(img, x-1200, 0, null);
            g3.drawImage(img, x, 0, null);
        }
    }

    public BackgroundComponent(){
        spawn();
    }
}
