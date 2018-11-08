package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private UpdateAgent updateAgent;
	
	private class GamePanel extends JPanel
	{
		private DroneComponent drone;
		private AirplaneComponent planes;
		private BulletComponent bullets;
		private Image img;
		
		public void paint(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
			drone.paint(g);
			planes.paint(g);
			bullets.paint(g);
		}
		
		public void setImage(Image img) { this.img = img; }
		
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
		private int currentLevel = 1;
		
		public void changeLevel()
		{
			currentLevel++;
			//change label and stuff
		}
		
		public ScorePanel()
		{
			add(new JLabel("Test"));
		}
	}
	
	private class UpdateAgent
	{
		private Timer updateTimer;
		private Timer spawnTimer;
		private Timer levelTimer;
		private Timer collisionTimer;
		private boolean isImmune = false;
		
		private ActionListener updateListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				gamePanel.drone.move();
				gamePanel.planes.move();
				gamePanel.bullets.move();
				if (!isImmune && gamePanel.drone.checkCollisions(gamePanel.planes) == 1)
				{
					startCollisionTimer();
				}
				gamePanel.bullets.checkCollisions(gamePanel.planes);
				
				gamePanel.repaint();
			}
		};
		
		private ActionListener spawnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				gamePanel.planes.spawn();
				/* TEMP */
				//Testing bullet collisions with planes
				gamePanel.drone.shoot();
			}
		};
		
		private ActionListener levelListener = new ActionListener() {
			double difficultyChange = .88;
			int minDelay = 600;
			
			public void actionPerformed(ActionEvent e)
			{
				int newDelay = (int) (spawnTimer.getDelay() * difficultyChange);
				
				if (newDelay > minDelay)
				{
					scorePanel.changeLevel();	
					setDifficulty(newDelay);
					if (difficultyChange < .90) difficultyChange += .01;
				}
			}
		};
		
		private ActionListener collisionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.planes.setSpeedFactor(1.0);
				collisionTimer.stop();
				isImmune = false;
			}
			
		};
		
		public void start()
		{
			updateTimer.start();
			spawnTimer.start();
			levelTimer.start();
		}
		
		public void setDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }
		
		public void startCollisionTimer()
		{
			isImmune = true;
			gamePanel.planes.setSpeedFactor(0.6);
			collisionTimer.start();
		}
		
		public UpdateAgent(int updateDelay, int spawnDelay, int levelDelay)
		{
			updateTimer = new Timer(updateDelay, updateListener);
			spawnTimer = new Timer(spawnDelay, spawnListener);
			levelTimer = new Timer(levelDelay, levelListener);
			collisionTimer = new Timer(5000, collisionListener);
		}
	}
	
	public void setUpdateAgent(int updateDelay, int spawnDelay, int levelDelay)
	{
		updateAgent = new UpdateAgent(updateDelay, spawnDelay, levelDelay);
		updateAgent.start();
	}
	
	public void setImages(Image bgImg, Image playerImg, Image[] airplaneImgs, Image missileImg)
	{
		gamePanel.setImage(bgImg);
		gamePanel.drone.setImage(playerImg);
		gamePanel.planes.setImages(airplaneImgs);
		gamePanel.bullets.setImage(missileImg);
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
