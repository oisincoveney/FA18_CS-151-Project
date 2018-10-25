package main;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private static Dimension FRAME_SIZE;
	private static GamePanel gamePanel;
	private static ScorePanel scorePanel;
	
	public static void setImage(Image img) { GamePanel.img = img; }
	
	public static void update() { gamePanel.update(); }
	
	private static class GamePanel extends JPanel
	{
		private static Image img;
		private Random rnd = new Random();
		private AirplaneComponent planes = new AirplaneComponent();
		private BulletComponent bullets = new BulletComponent();
		private DroneObject drone;
		
		public void paint(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
			drone.paint(g);
			planes.paint(g);
			bullets.paint(g);
		}
		
		public void update()
		{ 
			//Move objects
			planes.move();
			//bullets.move();

			//Check collisions
			//planes.checkCollisions(drone); //Modify score here
			//bullets.checkCollisions(planes); //Can increment kill count
			
			repaint();
		}
		
		public GamePanel()
		{
			drone = new DroneObject(20, FRAME_SIZE.height / 2, 1);
			//Temp for testing
			for (int n = 0; n < 10; n++) planes.spawn(rnd.nextInt(FRAME_SIZE.width - 320) + 200, rnd.nextInt(FRAME_SIZE.height - 120) + 10);
			
			add(planes);
			add(bullets);
			add(drone);
		}
	}
	
	private static class ScorePanel extends JPanel
	{
		public ScorePanel()
		{
			add(new JLabel("Test"));
		}
	}
	
    public GameFrame(String title, int width, int height)
    {
    	super(title);
    	FRAME_SIZE = new Dimension(width, height);
    	gamePanel = new GamePanel();
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
