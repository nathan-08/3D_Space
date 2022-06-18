import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*; // Point2D, AffineTransform

class Point {
  public int x;
  public int y;
  public int z;
  public Point(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
public class Panel extends JPanel {
  private boolean mouseDown = false;
  private AffineTransform transform;
  private Point prevMousePosition = new Point(0, 0, 0);
  private double angle_z = 0.0;
  private double angle_y = 0.0;
  private int toInt(double x) {
    return (int)(float)Math.round(x);
  }
  public Dimension getPreferredSize() {
    return new Dimension(800, 800);
  }
  public void handleMousePressed() {}
  public void handleMouseReleased() {
    mouseDown = false;
  }
  public void handleMouseDragged(int x, int y) {
    int delta_x = Math.abs(x - prevMousePosition.x);
    int delta_y = Math.abs(y - prevMousePosition.y);
    if (!mouseDown) {
      mouseDown = true;
      delta_x = 0;
      delta_y = 0;
    }
    if (y > prevMousePosition.y) {
      angle_z -= Math.PI*(double)delta_y / 400.0;
    }
    else if (y < prevMousePosition.y) {
      angle_z += Math.PI*(double)delta_y / 400.0;
    }
    if (x > prevMousePosition.x) {
      angle_y -= Math.PI*(double)delta_x / 400.0;
    }
    else if (x < prevMousePosition.x) {
      angle_y += Math.PI*(double)delta_x / 400.0;
    }
    prevMousePosition.x = x;
    prevMousePosition.y = y;
    repaint();
  }
  private void rotate_z(Point p, double a) {
    /* [ cos a, -sin a,  0 ] [ x ]
     * [ sin a,  cos a,  0 ] [ y ]
     * [ 0,      0,      1 ] [ z ]
    */
    int xx = toInt(p.x * Math.cos(a) - p.y * Math.sin(a));
    int yy = toInt(p.x * Math.sin(a) + p.y * Math.cos(a));
    int zz = p.z;
    p.x = xx;
    p.y = yy;
    p.z = zz;
  }
  private void rotate_y(Point p, double a) {
    /* [ cos a, 0,-sin a ] [ x ]
     * [ 0,     1,     0 ] [ y ]
     * [ sin a, 0, cos a ] [ z ]
    */
    int xx = toInt(p.x * Math.cos(a) - p.z * Math.sin(a));
    int yy = p.y;
    int zz = toInt(p.x * Math.sin(a) + p.z * Math.cos(a));
    p.x = xx;
    p.y = yy;
    p.z = zz;
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    int w = getWidth();
    int h = getHeight();

    transform = new AffineTransform();
    transform.translate(getWidth()/2, getHeight()/2);
    transform.scale(1,-1);
    g2d.setTransform(transform);

    g2d.setColor(new Color(0xa0, 0xa0, 0xf0));

    Point left = new Point (-w/4, 0, 0);
    Point right = new Point(w/4, 0, 0);
    Point up = new Point   (0, w/4, 0);
    Point down = new Point (0, -w/4, 0);

    rotate_z(left,  angle_z);
    rotate_z(right, angle_z);
    rotate_z(up,    angle_z);
    rotate_z(down,  angle_z);

    rotate_y(left,  angle_y);
    rotate_y(right, angle_y);
    rotate_y(up,    angle_y);
    rotate_y(down,  angle_y);

    g2d.setStroke(new BasicStroke(20));
    if (mouseDown) g2d.setColor(Color.orange);
    g2d.drawLine(left.x, left.y, right.x, right.y);
    g2d.drawLine(up.x, up.y, down.x, down.y);
  }
}

