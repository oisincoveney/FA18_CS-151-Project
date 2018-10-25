package main;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameLauncher
{
    public static void main(String args[])
    {
    	loadImages();
    	new GameFrame("Drone Game");
    	//Initialize timer
    }
    
    private static void loadImages()
    {
    	String dir = "src/assets/";
    	
    	try
    	{
    		GameFrame.GamePanel.setImage(ImageIO.read(new File(dir + "background_sky.png")));
    		//Images not yet sourced
    		//DroneObject.setImage(ImageIO.read(new File(dir + "test")));
    		//BulletObject.setImage(ImageIO.read(new File(dir + "test")));
    		
    		Image[] airplaneImages = new Image[4];
    		for (int n = 0; n < 4; n++)
    		{
    			airplaneImages[n] = ImageIO.read(new File(dir + "plane" + n + ".png"));
    		}
    		AirplaneObject.setImages(airplaneImages);
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
}