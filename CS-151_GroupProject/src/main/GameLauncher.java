package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameLauncher
{
    private static Image[] airplaneImgs = new Image[4];
    private static Image bgImg, playerImg, missileImg;

    public static void main(String args[])
    {
        loadImages();
        GameFrame gameFrame = new GameFrame("Drone Game", 1200, 600);
        gameFrame.setImages(bgImg, playerImg, airplaneImgs, missileImg);
        gameFrame.setUpdateAgent(5, 2000, 90000);
        gameFrame.addKeyListener(new KeyAgent(gameFrame));
    }

    private static void loadImages()
    {
        String dir = "src/assets/";

        try
        {
            bgImg = ImageIO.read(new File(dir + "tiled_sky.png"));
            playerImg = ImageIO.read(new File(dir + "player.png"));
            missileImg = ImageIO.read(new File(dir + "missile.png"));

            for (int n = 0; n < airplaneImgs.length; n++)
            {
                airplaneImgs[n] = ImageIO.read(new File(dir + "plane" + n + ".png"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
}