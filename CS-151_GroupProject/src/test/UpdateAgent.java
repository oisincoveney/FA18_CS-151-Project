package test;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAgent
{
	private GamePanel gamePanel;
	private Timer updateTimer;
	private Timer spawnTimer;
	
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
	
	public void start()
	{
		updateTimer.start();
		spawnTimer.start();
	}
	
	public void setDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }
	
	public UpdateAgent(GamePanel gamePanel, int updateDelay, int spawnDelay)
	{
		this.gamePanel = gamePanel;
		updateTimer = new Timer(updateDelay, updateListener);
		spawnTimer = new Timer(spawnDelay, spawnListener);
	}
}