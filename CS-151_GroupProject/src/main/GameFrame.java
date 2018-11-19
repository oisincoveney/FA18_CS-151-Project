package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private GamePanel gamePanel;
	private UpdateAgent updateAgent;
	private MenuPanel menuPanel;

	public int timeRemaining = 90;
	public JLabel timerDisplay = new JLabel();
	public JLabel victorLabel = new JLabel();
	public int score;
	public JLabel scoreLabel;
	public JButton startBtn;

	private class GamePanel extends JPanel
	{
		private DroneComponent drone;
		private AirplaneComponent planes;
		private BulletComponent bullets;
		private BackgroundComponent bg;
		private Image img;

		public void paint(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
			bg.paint(g);
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
			bg = new BackgroundComponent();
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

	private class MenuPanel extends JPanel{

		private int currentLevel = 1;

		public void changeLevel()
		{
			currentLevel++;
			//change label and stuff
		}

		public MenuPanel(){
			add(new JLabel("Score: "));
			scoreLabel = new JLabel(Integer.toString(score));
			add(scoreLabel);

			add(new JLabel("Seconds Remaining:"));
			add(timerDisplay);

			victorLabel.setForeground(Color.GREEN);
			add(victorLabel);

			startBtn = new JButton("PLAY");
			add(startBtn);
			startBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateAgent.start();
					startBtn.setEnabled(false);
				}
			});
		}
	}
	
	private class UpdateAgent
	{
		private Timer updateTimer;
		private Timer spawnTimer;
		private Timer levelTimer;
		private Timer collisionTimer;
		private Timer gameClockTimer;
		private boolean isImmune = false;

		private ActionListener gameClockListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timeRemaining == 0){
					updateAgent.stop();
					scoreLabel.setText(Integer.toString(score+1));
					victorLabel.setText("VICTORY");
					startBtn.setEnabled(true);
				}
				else if (timeRemaining <= 11){
					timeRemaining -= 1;
					timerDisplay.setText(Integer.toString(timeRemaining));
					timerDisplay.setForeground(Color.RED);
				}
				else
					timeRemaining -= 1;
					timerDisplay.setText(Integer.toString(timeRemaining));
			}
		};
		
		private ActionListener updateListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				gamePanel.drone.move();
				gamePanel.planes.move();
				gamePanel.bullets.move();
				if (!isImmune && gamePanel.drone.checkCollisions(gamePanel.planes) == 1) collisionTimer.start();
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
					menuPanel.changeLevel();
					setDifficulty(newDelay);
					if (difficultyChange < .90) difficultyChange += .01;
				}
			}
		};
		
		private ActionListener collisionListener = new ActionListener() {
			private final double MAX_SPEED_FACTOR = 1.0;
			private final double MIN_SPEED_FACTOR = 0.5;
			private double speedFactor = MAX_SPEED_FACTOR;
			private double changeRate = -0.01;
			
			public void actionPerformed(ActionEvent e)
			{
				if (changeRate < 0)
				{
					if (speedFactor == MAX_SPEED_FACTOR)
					{
						isImmune = true;
						gamePanel.drone.setBlink(true);
						collisionTimer.setDelay(10);
					}
					speedFactor +=changeRate;
					
					if (speedFactor <= MIN_SPEED_FACTOR)
					{
						speedFactor = MIN_SPEED_FACTOR;
						changeRate = -changeRate;
						collisionTimer.setDelay(5000);
					}
				}
				else
				{
					if (speedFactor == MIN_SPEED_FACTOR)
					{
						collisionTimer.setDelay(10);
					}
					speedFactor +=changeRate;
					
					if (speedFactor >= MAX_SPEED_FACTOR)
					{
						isImmune = false;
						gamePanel.drone.setBlink(false);
						speedFactor = MAX_SPEED_FACTOR;
						changeRate = -changeRate;
						collisionTimer.stop();
					}
				}
				
				gamePanel.planes.setSpeedFactor(speedFactor);
			}
		};
		
		public void start()
		{
			updateTimer.start();
			spawnTimer.start();
			levelTimer.start();
			gameClockTimer.start();
		}

		public void stop() {
			updateTimer.stop();
			spawnTimer.stop();
			levelTimer.stop();
			gameClockTimer.stop();
		}
		
		public void setDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }

		public UpdateAgent(int updateDelay, int spawnDelay, int levelDelay, int gameClockDelay)
		{
			updateTimer = new Timer(updateDelay, updateListener);
			spawnTimer = new Timer(spawnDelay, spawnListener);
			levelTimer = new Timer(levelDelay, levelListener);
			collisionTimer = new Timer(updateDelay, collisionListener);
			gameClockTimer = new Timer(gameClockDelay, gameClockListener);
		}
	}
	
	public void setUpdateAgent(int updateDelay, int spawnDelay, int levelDelay, int gameClockDelay)
	{
		updateAgent = new UpdateAgent(updateDelay, spawnDelay, levelDelay, gameClockDelay);
		//updateAgent.start();
	}
	
	public void setImages(Image bgImg, Image playerImg, Image[] airplaneImgs, Image missileImg)
	{
		gamePanel.bg.setImage(bgImg);
		gamePanel.drone.setImage(playerImg);
		gamePanel.planes.setImages(airplaneImgs);
		gamePanel.bullets.setImage(missileImg);
	}
	
    public GameFrame(String title, int width, int height)
    {
    	//Create the frame and initialize the components
    	super(title);
    	gamePanel = new GamePanel(new Dimension(width, height));
    	menuPanel = new MenuPanel();
    	//Set the layout and add the panels
    	setLayout(new BorderLayout(0, 0));
    	add(gamePanel, BorderLayout.CENTER);
    	add(menuPanel, BorderLayout.NORTH);
        //Set termination settings, lock frame size, and make visible
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
        setVisible(true);
		pack();
    }
}
