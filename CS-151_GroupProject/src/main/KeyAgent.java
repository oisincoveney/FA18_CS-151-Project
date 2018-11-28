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
        System.out.println("e = " + e);
        if (e.getKeyChar() == ' ')
        {
            drone.shoot();
        }
        else if (e.getKeyChar() == 'w')
        {
            drone.moveUp();
        }
        else if (e.getKeyChar() == 's')
        {
            drone.moveDown();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        System.out.println("e = " + e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        System.out.println("e = " + e);
    }
}
