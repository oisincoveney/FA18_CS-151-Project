package main;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAgent
{
	private GameFrame.GamePanel gamePanel;
	private GameFrame.ScorePanel scorePanel;
	private Timer updateTimer;
	private Timer spawnTimer;
	private Timer levelTimer;
	
	private ActionListener updateListener = new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			gamePanel.move();
			gamePanel.checkCollisions();
			gamePanel.repaint();
		}
	};
	
	private ActionListener spawnListener = new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			gamePanel.spawnTarget();
			/* TEMP */
			//Testing bullet collisions with planes
			gamePanel.spawnBullet();
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
	
	public void start()
	{
		updateTimer.start();
		spawnTimer.start();
		levelTimer.start();
	}
	
	public void setDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }
	
	public UpdateAgent(GameFrame.GamePanel gamePanel, GameFrame.ScorePanel scorePanel, int updateDelay, int spawnDelay, int levelDelay)
	{
		this.gamePanel = gamePanel;
		this.scorePanel = scorePanel;
		updateTimer = new Timer(updateDelay, updateListener);
		spawnTimer = new Timer(spawnDelay, spawnListener);
		levelTimer = new Timer(levelDelay, levelListener);
	}
}