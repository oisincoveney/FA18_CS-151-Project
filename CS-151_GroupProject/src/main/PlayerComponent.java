package main;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class PlayerComponent extends DroneComponent
{
    private BulletComponent bullets;
    private KeyAgent keyAgent;
    private UpdateAgent updateAgent;
    private boolean isReloading = false;
    
    private class KeyAgent implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            if (e.getKeyChar() == ' ') shoot();
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
        	if (e.getKeyChar() == 'w') drone.moveUp = true; 
        	else if (e.getKeyChar() == 's') drone.moveDown = true;
        	else if (e.getKeyChar() == 'a') drone.moveLeft = true;
        	else if (e.getKeyChar() == 'd') drone.moveRight = true;
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
        	if (e.getKeyChar() == 'w') drone.moveUp = false; 
        	else if (e.getKeyChar() == 's') drone.moveDown = false;
        	else if (e.getKeyChar() == 'a') drone.moveLeft = false;
        	else if (e.getKeyChar() == 'd') drone.moveRight = false;
        }
    }
    
    private class UpdateAgent
    {
        private Timer reloadTimer;

        private ActionListener reloadListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	isReloading = false;
            }
        };
        
        public UpdateAgent(int reloadDelay)
        {
        	reloadTimer = new Timer(reloadDelay, reloadListener);
			reloadTimer.setRepeats(false);
        }
    }
    
    public void shoot()
    {
    	if (!isReloading)
    	{
    		bullets.spawn((int) drone.getRight() - 60, (int) drone.getBottom() - 4);
    		isReloading = true;
    		updateAgent.reloadTimer.start();
    	}
	}
    
    public KeyAgent getKeyAgent() { return keyAgent; }
    
	public PlayerComponent(Dimension panelDimensions, BulletComponent bullets)
	{
		super(panelDimensions);
	    this.bullets = bullets;
	    keyAgent = new KeyAgent();
	    updateAgent = new UpdateAgent(500);
	}
}
