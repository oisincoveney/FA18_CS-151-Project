package test;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private UpdateAgent updateAgent;
	
	private class ScorePanel extends JPanel
	{
		public ScorePanel()
		{
			add(new JLabel("Test"));
		}
	}
	
	public void setUpdateAgent(int updateDelay, int spawnDelay)
	{
		updateAgent = new UpdateAgent(gamePanel, updateDelay, spawnDelay);
		updateAgent.start();
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
