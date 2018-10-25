package main;
import javax.swing.*;

public class BackgroundPlane {
    public BackgroundPlane(){
        JFrame frame = new JFrame("Drone Game");


        ImageIcon bg = new ImageIcon("src/assets/background_sky.png");
        JLabel imgLabel = new JLabel(bg);

        frame.add(imgLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1000,600);

    }
}
