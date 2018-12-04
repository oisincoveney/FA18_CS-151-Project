package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings ("serial")
public class GameFrame extends JFrame
{
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private UpdateAgent updateAgent;

    public DroneComponent getDrone()
    {
        return gamePanel.player;
    }

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

        private ActionListener updateListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	gamePanel.player.move();
                gamePanel.planes.move();
                gamePanel.bullets.move();
                gamePanel.bg.move();
                if (!isImmune && gamePanel.player.checkCollisions(gamePanel.planes) == 1)
                {
                    collisionTimer.start();
                }
                gamePanel.bullets.checkCollisions(gamePanel.planes);

                gamePanel.repaint();
            }
        };

        private ActionListener spawnListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                gamePanel.planes.spawn();
            }
        };

        private ActionListener levelListener = new ActionListener()
        {
            double difficultyChange = .88;
            int minDelay = 600;

            public void actionPerformed(ActionEvent e)
            {
                int newDelay = (int) (spawnTimer.getDelay() * difficultyChange);

                if (newDelay > minDelay)
                {
                    scorePanel.changeLevel();
                    setDifficulty(newDelay);
                    if (difficultyChange < .90)
                    {
                        difficultyChange += .01;
                    }
                }
            }
        };

        private ActionListener collisionListener = new ActionListener()
        {
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
                        gamePanel.player.setBlink(true);
                        collisionTimer.setDelay(10);
                    }
                    speedFactor += changeRate;

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
                    speedFactor += changeRate;

                    if (speedFactor >= MAX_SPEED_FACTOR)
                    {
                        isImmune = false;
                        gamePanel.player.setBlink(false);
                        speedFactor = MAX_SPEED_FACTOR;
                        changeRate = -changeRate;
                        collisionTimer.stop();
                    }
                }

                gamePanel.planes.setSpeedFactor(speedFactor);
                gamePanel.bg.setAnimationSpeed(speedFactor);
            }
        };

        public void start()
        {
            updateTimer.start();
            spawnTimer.start();
            levelTimer.start();
        }

        public void setDifficulty(int spawnDelay)
        {
            spawnTimer.setDelay(spawnDelay);
        }

        public UpdateAgent(int updateDelay, int spawnDelay, int levelDelay)
        {
            updateTimer = new Timer(updateDelay, updateListener);
            spawnTimer = new Timer(spawnDelay, spawnListener);
            levelTimer = new Timer(levelDelay, levelListener);
            collisionTimer = new Timer(updateDelay, collisionListener);
        }
    }

    public void setUpdateAgent(int updateDelay, int spawnDelay, int levelDelay)
    {
        updateAgent = new UpdateAgent(updateDelay, spawnDelay, levelDelay);
        updateAgent.start();
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
        scorePanel = new ScorePanel();
        //Set the layout and add the panels
        setLayout(new BorderLayout(0, 0));
        add(gamePanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);
        addKeyListener(gamePanel.player.getKeyAgent());
        //Set termination settings, lock frame size, and make visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
