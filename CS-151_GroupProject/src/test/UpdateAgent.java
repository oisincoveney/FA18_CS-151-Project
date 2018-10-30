package test;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAgent
{
	private GameFrame gameFrame;
	private Timer updateTimer;
	private Timer spawnTimer;
	
	private ActionListener updateListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) { gameFrame.update(); }
	};
	
	private ActionListener spawnListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) { gameFrame.spawnTarget(); }
	};
	
	public void SetDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }
	
	public UpdateAgent(GameFrame gameFrame, int updateDelay, int spawnDelay)
	{
		this.gameFrame = gameFrame;
		updateTimer = new Timer(updateDelay, updateListener);
		spawnTimer = new Timer(spawnDelay, spawnListener);
		updateTimer.start();
		spawnTimer.start();
	}
}