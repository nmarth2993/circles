import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

import javax.swing.*;

public class Test {
	JFrame frame;
	ArtPanel panel;

	public Test() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 500));
		panel = new ArtPanel();
		MouseCapture m = new MouseCapture();
		panel.addMouseListener(m);
		panel.addMouseMotionListener(m);
		panel.createCircles();
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
		final static int circleCreationIntervalMin = 50;
		final static int circleCreationIntervalMax = 300;

		final static int MAX_SIZE = 30;

		final static int diameter = 10;

		ArrayList<ColoredCircle> circles;
		Random r;

		Point mousePos;

		int red;
		int green;
		int blue;

		public Point getMousePos() {
			return mousePos;
		}

		public void setMousePos(Point mousePos) {
			this.mousePos = mousePos;
		}

		boolean mousePressed;

		public boolean isMousePressed() {
			return mousePressed;
		}

		public void setMousePressed(boolean mousePressed) {
			this.mousePressed = mousePressed;
		}

		public ArtPanel() {
			mousePos = new Point(250, 250);
			mousePressed = false;
			r = new Random();
			circles = new ArrayList<ColoredCircle>();
			new Thread(() -> {
				for (;;) {
					ArrayList<ColoredCircle> cList = new ArrayList<ColoredCircle>(circles);
					for (ColoredCircle c : cList) {
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
				final int gradientDelay = 5;
				for (;;) {

				}
			}).start();
		}

		public void createCircles() {
			new Thread(() -> {
				for (;;) {
					circles.add(new ColoredCircle(
							new Ellipse2D.Double(mousePos.getX(), mousePos.getY(), diameter, diameter),
							new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)), r.nextInt(9) + 1));
					if (isMousePressed()) {
						/*
						 * circles.add(new ColoredCircle( new Ellipse2D.Double(mousePos.getX() + 50,
						 * mousePos.getY(), diameter, diameter), new Color(r.nextInt(256),
						 * r.nextInt(256), r.nextInt(256)), r.nextInt(9) + 1));
						 */
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							Thread.sleep(r.nextInt(circleCreationIntervalMax) + circleCreationIntervalMin);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			ArrayList<ColoredCircle> drawingShapes = new ArrayList<ColoredCircle>(circles);

			/*
			 * if (circles.size() > MAX_SIZE) { drawingShapes = new
			 * ArrayList<ColoredCircle>( circles.subList(circles.size() - MAX_SIZE,
			 * circles.size() - 1)); }
			 */

			panel.setBackground(new Color(red, green, blue));

			for (ColoredCircle c : drawingShapes) {
				g2d.setColor(c.getColor());
				g2d.fill(c.getCircle());
			}
		}
	}

	class MouseCapture implements MouseMotionListener, MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				ColoredCircle.setGrowing((!ColoredCircle.isGrowing()));
				return;
			}
			/*
			 * if (SwingUtilities.isMiddleMouseButton(e)) {
			 * panel.setMiddleMousePressed(true); }
			 */

			panel.setMousePressed(true);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setMousePressed(false);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				mousePressed(e);
			}
			mouseMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			panel.setMousePos(e.getPoint());
		}

	}
}
