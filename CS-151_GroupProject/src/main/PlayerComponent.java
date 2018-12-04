package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PlayerComponent extends DroneComponent implements GameComponent
{
    private BulletComponent bullets;
    private KeyAgent keyAgent;
    private UpdateAgent updateAgent;
    private boolean isReloading;
    private boolean isBlinking;
    
    private class KeyAgent implements KeyListener
    {
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

		@Override
		public void keyTyped(KeyEvent e)
		{
			if (e.getKeyChar() == ' ') shoot();
		}
    }
    
    private class UpdateAgent
    {
        private Timer reloadTimer;
        private Timer blinkTimer;

        private ActionListener reloadListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	isReloading = false;
            }
        };
        
        private ActionListener blinkListener = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	isBlinking = !isBlinking;
            }
		};
        
        public UpdateAgent(int reloadDelay, int blinkDelay)
        {
        	reloadTimer = new Timer(reloadDelay, reloadListener);
        	blinkTimer = new Timer(blinkDelay, blinkListener);
			reloadTimer.setRepeats(false);
        }
    }
    
    @Override
    public void paint(Graphics g)
    {   	
    	if (!isBlinking) super.paint(g);
    }
    
    public void shoot()
    {
    	if (!isReloading)
    	{
    		SoundEffect.SHOOT.play();
    		bullets.spawn((int) drone.getRight() - 60, (int) drone.getBottom() - 4);
    		isReloading = true;
    		updateAgent.reloadTimer.start();
    	}
	}
    
    public KeyAgent getKeyAgent() { return keyAgent; }
    
    public void init()
    {
    	super.init();
    	isReloading = false;
    	isBlinking = false;
    	updateAgent = new UpdateAgent(500, 200);
    }
    
    public void setBlink(boolean blink)
    {
    	isBlinking = blink;
    	if (blink) updateAgent.blinkTimer.start();
    	else updateAgent.blinkTimer.stop();
    }
    
	public PlayerComponent(Dimension panelDimensions, BulletComponent bullets)
	{
		super(panelDimensions);
	    this.bullets = bullets;
	    keyAgent = new KeyAgent();
	    init();
	}
}
