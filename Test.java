import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Test {
	JFrame frame;
	ArtPanel panel;
	
	public Test() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 500));
		panel = new ArtPanel();
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame.setDefaultLookAndFeelDecorated(true);
			new Test();
		});
	}
	
	class ArtPanel extends JPanel {
		ArrayList<ColoredCircle> circles;
		Random r;
		Color color;
		
		final static int circleCreationIntervalMin = 100;
		final static int circleCreationIntervalMax = 1000;
		
		final static int size = 100;
		final static int diameter = 10;
		
		public ArtPanel() {
			r = new Random();
			circles = new ArrayList<ColoredCircle>();
			color = new Color(0, 0, 0);
			new Thread(() -> {
				for (;;) {
					for (ColoredCircle c : circles) {
						c.tick();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
			}).start();
			
			new Thread(() -> {
				for (;;) {
					color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
				}
			}).start();
			
			new Thread(() -> {
				for (;;) {
					circles.add(new ColoredCircle(new Ellipse2D.Double(r.nextInt(size) + 1, r.nextInt(size) + 1, diameter, diameter), color));
					try {
						Thread.sleep(r.nextInt(circleCreationIntervalMax) + circleCreationIntervalMin);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for (ColoredCircle c : circles) {
				g2d.setColor(c.getColor());
				g2d.fill(c.getCircle());
			}
		}
	}
}
