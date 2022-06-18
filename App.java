import javax.swing.*;
import java.awt.event.*;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        runGUI();
      }
    });
  }
  private static void runGUI() {
    JFrame frame = new JFrame("3D Coords");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Panel panel = new Panel();
    frame.addKeyListener(new KeyListener(){
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {}
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {}
      }
      public void keyReleased(KeyEvent e) {}
      public void keyTyped(KeyEvent e) {}
    });

    frame.addMouseListener(new MouseListener(){
      public void mouseClicked(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {
        panel.handleMousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        panel.handleMouseReleased();
      }
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
    });

    frame.addMouseMotionListener(new MouseMotionListener() {
      public void mouseDragged(MouseEvent e) {
        panel.handleMouseDragged(e.getX(), e.getY());
      }
      public void mouseMoved(MouseEvent e) {}
    });
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
