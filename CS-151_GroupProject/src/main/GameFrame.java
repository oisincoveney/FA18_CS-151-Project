package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings ("serial")
public class GameFrame extends JFrame
{
	private GamePanel gamePanel;
	private UpdateAgent updateAgent;
	private MenuPanel menuPanel;

    private class GamePanel extends JPanel
    {
        private PlayerComponent player;
        private AirplaneComponent planes;
        private BulletComponent bullets;
        private BackgroundComponent bg;
        
        public void paint(Graphics g)
        {
            super.paintComponent(g);
            bg.paint(g);
            player.paint(g);
            planes.paint(g);
            bullets.paint(g);
        }


        public GamePanel(Dimension dimensions)
        {
            //Initialize game components
            planes = new AirplaneComponent(dimensions);
            bullets = new BulletComponent(dimensions);
            player = new PlayerComponent(dimensions, bullets);
            bg = new BackgroundComponent();
            //Add all game components
            add(player);
            add(planes);
            add(bullets);
            //Set panel size
            setMinimumSize(dimensions);
            setPreferredSize(dimensions);
            setMaximumSize(dimensions);
        }
    }
    
	private class MenuPanel extends JPanel
	{
		private JLabel timerDisplay = new JLabel();
		private JLabel scoreDisplay = new JLabel();
		private JLabel collisionsDisplay = new JLabel();
		private JLabel planesHitDisplay = new JLabel();

		public MenuPanel()
		{
			JButton startBtn = new JButton("PLAY");
			startBtn.setFocusable(false);
			
			startBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					updateAgent.start();
			        GameFrame.this.addKeyListener(gamePanel.player.getKeyAgent());
					startBtn.setEnabled(false);
				}
			});
			
			add(new JLabel("Score: "));
			scoreDisplay.setText("0");
			add(scoreDisplay);
			
			add(new JLabel("Collisions: "));
			collisionsDisplay.setText("0");
			add(collisionsDisplay);
			
			add(new JLabel("Planes Hit: "));
			planesHitDisplay.setText("0");
			add(planesHitDisplay);
			
			add(new JLabel("Seconds Remaining:"));
			add(timerDisplay);
			
			add(startBtn);
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
		private int timeRemaining;
		private int numCollisions = 0;
		private int planesDestroyed = 0;

		private ActionListener gameClockListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				timeRemaining--;
				
				if (timeRemaining == 0)
				{
					timeRemaining = levelTimer.getDelay() / 1000;
					menuPanel.timerDisplay.setForeground(Color.BLACK);
				}
				else if (timeRemaining == 5)
				{
					menuPanel.timerDisplay.setForeground(Color.RED);
				}
				
				menuPanel.timerDisplay.setText(Integer.toString(timeRemaining));
			}
		};
		
		private ActionListener updateListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				gamePanel.bg.move();
				gamePanel.player.move();
				gamePanel.planes.move();
				gamePanel.bullets.move();
				if (!isImmune && gamePanel.player.checkCollisions(gamePanel.planes) == 1) collisionTimer.start();
				planesDestroyed += gamePanel.bullets.checkCollisions(gamePanel.planes);
				menuPanel.planesHitDisplay.setText(Integer.toString(planesDestroyed));
				
				gamePanel.repaint();
			}
		};
		
		private ActionListener spawnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				gamePanel.planes.spawn();
			}
		};
		
		private ActionListener levelListener = new ActionListener() {
			double difficultyChange = .80;
			int minDelay = 400;
			int score = 0;
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Update score
				if (numCollisions <= 2) score++;
				numCollisions = 0;
				menuPanel.scoreDisplay.setText(Integer.toString(score));
				menuPanel.collisionsDisplay.setText("0");
				
				//Update spawn settings
				spawnTimer.setDelay(Math.max(spawnTimer.getDelay() - 200, 600));
				int newDelay = (int) (spawnTimer.getDelay() * difficultyChange);
				
				if (newDelay > minDelay)
				{
					spawnTimer.setDelay(newDelay);
					if (difficultyChange < .90) difficultyChange += .01;
					gamePanel.planes.incrementMaxSpeed();
				}
			}
		};
		
		private ActionListener collisionListener = new ActionListener() {
			private final double MAX_SPEED_FACTOR = 1.0;
			private final double MIN_SPEED_FACTOR = 0.5;
			private double speedFactor = MAX_SPEED_FACTOR;
			private double changeRate = -0.01;
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (changeRate < 0)
				{
					if (speedFactor == MAX_SPEED_FACTOR)
					{
						isImmune = true;
						gamePanel.player.setBlink(true);
						collisionTimer.setDelay(10);
						menuPanel.collisionsDisplay.setText(Integer.toString(++numCollisions));
						gameClockTimer.stop();
					}
					speedFactor +=changeRate;
					
					if (speedFactor <= MIN_SPEED_FACTOR)
					{
						speedFactor = MIN_SPEED_FACTOR;
						changeRate = -changeRate;
						collisionTimer.setDelay(5000 - (int) (20 * (MAX_SPEED_FACTOR - MIN_SPEED_FACTOR) / Math.abs(changeRate)));
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
						gamePanel.player.setBlink(false);
						speedFactor = MAX_SPEED_FACTOR;
						changeRate = -changeRate;
						collisionTimer.stop();
						gameClockTimer.start();
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

		public UpdateAgent(int updateDelay, int spawnDelay, int levelDelay, int gameClockDelay)
		{
			updateTimer = new Timer(updateDelay, updateListener);
			spawnTimer = new Timer(spawnDelay, spawnListener);
			levelTimer = new Timer(levelDelay, levelListener);
			collisionTimer = new Timer(updateDelay, collisionListener);
			gameClockTimer = new Timer(gameClockDelay, gameClockListener);
			timeRemaining = (int) levelDelay / 1000;
		}
	}
	
	public void setUpdateAgent(int updateDelay, int spawnDelay, int levelDelay)
	{
		updateAgent = new UpdateAgent(updateDelay, spawnDelay, levelDelay, 1000);
		menuPanel.timerDisplay.setText(Integer.toString((int) levelDelay / 1000));
		//updateAgent.start();
	}
	
    public void setImages(Image bgImg, Image playerImg, Image[] airplaneImgs, Image missileImg)
    {
        gamePanel.bg.setImage(bgImg);
        gamePanel.player.setImage(playerImg);
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
