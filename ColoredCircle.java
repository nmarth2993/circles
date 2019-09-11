import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class ColoredCircle {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	private static boolean growing;

	private Ellipse2D circle;
	private Color color;
	private int sizeTick;
	private int distanceTick;

	public ColoredCircle(Ellipse2D c1, Color c2, boolean up, boolean down, boolean left, boolean right) {
		circle = c1;
		color = c2;
		sizeTick = 1;
		distanceTick = 2;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}

	public ColoredCircle(Ellipse2D c1, Color c2, int direction) {
		circle = c1;
		color = c2;
		sizeTick = 1;
		distanceTick = 2;

		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;

		if (direction == 1) {
			this.up = true;
		} else if (direction == 2) {
			this.down = true;
		} else if (direction == 3) {
			this.left = true;
		} else if (direction == 4) {
			this.right = true;
		} else if (direction == 5) {
			this.up = true;
			this.left = true;
		} else if (direction == 6) {
			this.up = true;
			this.right = true;
		} else if (direction == 7) {
			this.down = true;
			this.left = true;
		} else {
			this.down = true;
			this.right = true;
		}
	}

	public static boolean isGrowing() {
		return growing;
	}

	public static void setGrowing(boolean g) {
		growing = g;
	}

	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D e) {
		circle = e;
	}

	public Color getColor() {
		return color;
	}

	public void tick() {
		/*
		 * if (direction == TOP_LEFT) { setCircle(new
		 * Ellipse2D.Double(getCircle().getX() - distanceTick, getCircle().getY() -
		 * distanceTick, getCircle().getWidth() + sizeTick, getCircle().getHeight() +
		 * sizeTick)); } else if (direction == TOP_RIGHT) { setCircle(new
		 * Ellipse2D.Double(getCircle().getX() + distanceTick, getCircle().getY() -
		 * distanceTick, getCircle().getWidth() + sizeTick, getCircle().getHeight() +
		 * sizeTick)); } else if (direction == BOTTOM_LEFT) { setCircle(new
		 * Ellipse2D.Double(getCircle().getX() - distanceTick, getCircle().getY() +
		 * distanceTick, getCircle().getWidth() + sizeTick, getCircle().getHeight() +
		 * sizeTick)); } else { setCircle(new Ellipse2D.Double(getCircle().getX() +
		 * distanceTick, getCircle().getY() + distanceTick, getCircle().getWidth() +
		 * sizeTick, getCircle().getHeight() + sizeTick)); }
		 */
//		System.out.println("(" + getCircle().getX() + ", " + getCircle().getY() + ")");
		if (up) {
			setCircle(new Ellipse2D.Double(getCircle().getX(), getCircle().getY() - distanceTick,
					getCircle().getWidth(), getCircle().getHeight()));
		}
		if (down) {
			setCircle(new Ellipse2D.Double(getCircle().getX(), getCircle().getY() + distanceTick,
					getCircle().getWidth(), getCircle().getHeight()));
		}
		if (left) {
			setCircle(new Ellipse2D.Double(getCircle().getX() - distanceTick, getCircle().getY(),
					getCircle().getWidth(), getCircle().getHeight()));
		}
		if (right) {
			setCircle(new Ellipse2D.Double(getCircle().getX() + distanceTick, getCircle().getY(),
					getCircle().getWidth(), getCircle().getHeight()));
		}
		if (growing) {
			setCircle(new Ellipse2D.Double(getCircle().getX(), getCircle().getY(), getCircle().getWidth() + sizeTick,
					getCircle().getHeight() + sizeTick));
		}
//		circle.setFrame(
//				new Rectangle((int) getCircle().getWidth() + sizeTick, (int) getCircle().getHeight() + sizeTick));

	}

	public void setSizeTick(int s) {
		sizeTick = s;
	}

	public void setDistanceTick(int d) {
		distanceTick = d;
	}

}
