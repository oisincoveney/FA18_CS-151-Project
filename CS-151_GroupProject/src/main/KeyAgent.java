package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAgent implements KeyListener
{
    private DroneComponent drone;

    public KeyAgent(GameFrame frame)
    {
        drone = frame.getDrone();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        if (e.getKeyChar() == ' ') drone.shoot();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    	if (e.getKeyChar() == 'w') drone.setDir(-1, true); 
    	else if (e.getKeyChar() == 's') drone.setDir(1, true); 
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    	if (e.getKeyChar() == 'w') drone.setDir(-1, false); 
    	else if (e.getKeyChar() == 's') drone.setDir(1, false);
    }
}
