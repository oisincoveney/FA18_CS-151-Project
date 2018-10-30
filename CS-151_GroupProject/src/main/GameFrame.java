package main;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private static Image img;
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	
	public static void setImage(Image img) { GameFrame.img = img; }
	
	public void update()
	{
		gamePanel.move();
		gamePanel.checkCollisions();
		gamePanel.repaint();
	}
	
	public void spawnTarget() { gamePanel.spawnTarget(); }
	
	public void spawnBullet() { gamePanel.spawnBullet(); }
	
	private class GamePanel extends JPanel
	{
		private DroneComponent drone;
		private AirplaneComponent planes;
		private BulletComponent bullets;
		
		public void paint(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
			drone.paint(g);
			planes.paint(g);
			bullets.paint(g);
		}
		
		public void move()
		{
			drone.move();
			planes.move();
			bullets.move();
		}
		
		public void checkCollisions()
		{
			drone.checkCollisions(planes);
			bullets.checkCollisions(planes);
		}
		
		public void spawnTarget() { planes.spawn(); }
		
		public void spawnBullet() { drone.shoot(); }
		
		public GamePanel(Dimension dimensions)
		{
			//Initialize game components
			planes = new AirplaneComponent(dimensions);
			bullets = new BulletComponent(dimensions);
			drone = new DroneComponent(dimensions, bullets);
			//Add all game components
			add(drone);
			add(planes);
			add(bullets);
			//Set panel size
	        setMinimumSize(dimensions);
			setPreferredSize(dimensions);
			setMaximumSize(dimensions);
		}
	}
	
	private class ScorePanel extends JPanel
	{
		public ScorePanel()
		{
			add(new JLabel("Test"));
		}
	}
	
    public GameFrame(String title, int width, int height)
    {
    	//Create the frame and initialize the components
    	super(title);
    	gamePanel = new GamePanel(new Dimension(width, height));
    	scorePanel = new ScorePanel();
    	//Set the layout and add the panels
    	setLayout(new BorderLayout(0, 0));
    	add(gamePanel, BorderLayout.CENTER);
    	add(scorePanel, BorderLayout.SOUTH);
        //Set termination settings, lock frame size, and make visible
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
        setVisible(true);
		pack();
    }
}
