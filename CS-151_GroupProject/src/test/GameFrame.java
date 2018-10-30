package test;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private static Image img;
	private Dimension FRAME_SIZE;
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
		
		public void spawnBullet() { bullets.spawn(); }
		
		public GamePanel(Dimension dimensions)
		{
			drone = new DroneComponent(dimensions); //Change so components know frame size
			planes = new AirplaneComponent(dimensions);
			bullets = new BulletComponent(dimensions);
			
			add(drone);
			add(planes);
			add(bullets);
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
    	FRAME_SIZE = new Dimension(width, height);
    	gamePanel = new GamePanel(FRAME_SIZE);
    	scorePanel = new ScorePanel();
    	//Set the layout and add the panels
    	setLayout(new BorderLayout());
    	add(gamePanel, BorderLayout.CENTER);
    	add(scorePanel, BorderLayout.SOUTH);
        //Set termination settings, visible and specify frame size
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(FRAME_SIZE);
		setPreferredSize(FRAME_SIZE);
		setMaximumSize(FRAME_SIZE);
		setResizable(false);
    }
}
