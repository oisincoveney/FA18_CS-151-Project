package main;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAgent
{
	private Timer updateTimer;
	private Timer spawnTimer;
	
	private ActionListener updateListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) { GameFrame.update(); }
	};
	
	private ActionListener spawnListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) { GameFrame.spawnTarget(); }
	};
	
	public void SetDifficulty(int spawnDelay) { spawnTimer.setDelay(spawnDelay); }
	
	public UpdateAgent(int updateDelay, int spawnDelay)
	{
		updateTimer = new Timer(updateDelay, updateListener);
		spawnTimer = new Timer(spawnDelay, spawnListener);
		updateTimer.start();
		spawnTimer.start();
	}
}