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
		frame.addKeyListener(new kbListen());
		panel.setBackground(Color.BLACK);
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
		

		final static int cycleRate = 5;

		final static int MAX_SIZE = 7000;

		final static int diameter = 10;

		Thread circlesThread;
		Thread tickThread;
		Thread arrayThread;

		ArrayList<ColoredCircle> circles;
		Color bgColor;
		Random r;

		Point mousePos;


		double x1;
		double y1;
		
		int degrees;
		
		int red;
		int green;
		int blue;
		
		int radius = 50;
		
		boolean starburst;
		boolean rotate;

		public void setDegrees(int degrees) {
			this.degrees = degrees;
		}
		
		public boolean isRotate() {
			return rotate;
		}

		public void setRotate(boolean rotate) {
			this.rotate = rotate;
		}

		public void setStarburst(boolean starburst) {
			this.starburst = starburst;
		}

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

			cycleColors();

			createAndStartCircleThread();
			createAndStartTickThread();
			createAndStartArrayThread();
			
			createAndStartDegreesThread();
		}
		
		private void createAndStartDegreesThread() {
			new Thread(() -> {
				for (;;) {
					if (rotate) {
						degrees++;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		private void cycleColors() {
			new Thread(() -> {
				for (;;) {
					int red = 255;
					int green = 0;
					int blue = 0;

					for (; green < 255; green++) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (; red > 0; red--) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (; blue < 255; blue++) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (; green > 0; green--) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (; red < 255; red++) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (; blue > 0; blue--) {
						bgColor = new Color(red, green, blue);
						try {
							Thread.sleep(cycleRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		private void createAndStartArrayThread() {
			arrayThread = new Thread(new Runnable() {
				public void run() {
					for (;;) {
						if (circles.size() > MAX_SIZE) {
							synchronized (circles) {
								int size = circles.size();
								circles = new ArrayList<ColoredCircle>(
										new ArrayList<ColoredCircle>(circles).subList(size - MAX_SIZE, size));
							}
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			arrayThread.start();
		}

		private void createAndStartTickThread() {
			tickThread = new Thread(() -> {
				for (;;) {
					synchronized (circles) {
						ArrayList<ColoredCircle> cList = new ArrayList<ColoredCircle>(circles);
						for (ColoredCircle c : cList) {
							c.tick();
							// TODO: got an NPE on this line: look into that
						}
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
			});
			tickThread.start();
		}

		private void createAndStartCircleThread() {
			circlesThread = new Thread(() -> {
				for (;;) {
					circles.add(new ColoredCircle(new Ellipse2D.Double(mousePos.getX() - diameter / 2,
							mousePos.getY() - diameter / 2, diameter, diameter), bgColor, r.nextInt(9) + 1));
					if (starburst) {
						if (rotate) {
							x1 = radius * Math.cos(Math.toRadians(degrees));
							y1 = radius * Math.sin(Math.toRadians(degrees));
						}
						else {
							x1 = 1;
							y1 = 1;
						}
						circles.add(new ColoredCircle(
								new Ellipse2D.Double(mousePos.getX() + (radius - x1) - diameter / 2,
										mousePos.getY() - diameter / 2, diameter, diameter),
								bgColor, r.nextInt(9) + 1));
						circles.add(new ColoredCircle(
								new Ellipse2D.Double(mousePos.getX() - (radius - x1) - diameter / 2,
										mousePos.getY()- diameter / 2, diameter, diameter),
								bgColor, r.nextInt(9) + 1));
						circles.add(new ColoredCircle(
								new Ellipse2D.Double(mousePos.getX() - diameter / 2,
										mousePos.getY() - diameter / 2 - (radius - y1), diameter, diameter),
								bgColor, r.nextInt(9) + 1));
						circles.add(new ColoredCircle(
								new Ellipse2D.Double(mousePos.getX() - diameter / 2,
										mousePos.getY() - diameter / 2 + (radius - y1), diameter, diameter),
								bgColor, r.nextInt(9) + 1));
					}
					if (isMousePressed()) {
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
			});
			circlesThread.start();
		}

		public ArrayList<ColoredCircle> getCircleList() {
			return circles;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			ArrayList<ColoredCircle> drawingShapes = new ArrayList<ColoredCircle>(circles);

//			panel.setBackground(bgColor);

			for (ColoredCircle c : drawingShapes) {
				g2d.setColor(c.getColor());
				g2d.fill(c.getCircle());
			}
		}
	}

	class MouseCapture implements MouseMotionListener, MouseListener {
		int cycle;

		public MouseCapture() {
			cycle = 0;
		}

		public void nextCycle() {
			cycle++;
			if (cycle > 3) {
				cycle = 0;
			}
		}

		private void cycle(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				if (cycle == 0) {
					ColoredCircle.setGrowing(false);
					panel.setStarburst(false);
				} else if (cycle == 1) {
					ColoredCircle.setGrowing(true);
					panel.setStarburst(false);
				} else if (cycle == 2) {
					panel.setStarburst(true);
					ColoredCircle.setGrowing(false);
				} else {
					panel.setStarburst(true);
					ColoredCircle.setGrowing(true);
				}
				nextCycle();

				return;
			}
		}

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
			cycle(e);

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
	
	class kbListen implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'c') {
				synchronized (panel.getCircleList()) {
					panel.getCircleList().clear();
				}
			}
			else if (e.getKeyChar() == 'r') {
				panel.setDegrees(0);
				panel.setRotate(!panel.isRotate());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
	}
}
