package main;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAgent implements ActionListener
{
	private Timer updateTimer = new Timer(5, this);
    
	public void actionPerformed(ActionEvent e) { GameFrame.update(); }
	
	public UpdateAgent()
	{
		updateTimer.start();
	}
}
