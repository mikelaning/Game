import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game implements Runnable {
   public void run() {
      // Top-level frame
      final JFrame frame = new JFrame("Breakout");
      frame.setLocation(300, 300);

      // Main playing area
      final PongCourt court = new PongCourt();
      frame.add(court, BorderLayout.CENTER);

      // Reset button
      final JPanel panel = new JPanel();
      frame.add(panel, BorderLayout.NORTH);
      final JButton reset = new JButton("Reset");
      reset.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            court.reset();
         }
      });
      panel.add(reset);
      
      // help window
      final JButton help = new JButton("Help/Pause");
      help.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    		  court.helpon();
    		  JOptionPane.showMessageDialog(panel, "Instructions:" +  
    				  "\n- Move using the arrow keys" +
    				  "\n- Do not let the ball hit the ground" +
    				  "\n- Catch power ups to earn points" +
    				  "\n- Miss the ball or a power up your score will decrease" +
    				  "\n- Destroy all of the bricks to win" +
    				  "\n- Try to beat your high score!");
    		  //indicates that the help screen has been exited.
    		  if(JOptionPane.OK_CANCEL_OPTION == JOptionPane.OK_CANCEL_OPTION) {
    			  court.helpoff();//game resumes
    		  }
    	  }
      });
      panel.add(help);

      // Put the frame on the screen
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // Start the game running
      
      court.reset();
      
      }

   /*
    * Get the game started!
    */
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Game());
   }

}
