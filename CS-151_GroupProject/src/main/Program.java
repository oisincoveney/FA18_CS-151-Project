import java.awt.*;
import javax.swing.*;

public class Program
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      GamePanel panel = new GamePanel();
      frame.setLayout(new BorderLayout());
      frame.add(new GamePanel(), BorderLayout.CENTER);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(panel.getWidth(), panel.getHeight());
      frame.setVisible(true);
   }
}