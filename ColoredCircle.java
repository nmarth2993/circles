import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ColoredCircle {
	private Ellipse2D circle;
	private Color color;
	private int tickSize;
	
	
	public ColoredCircle(Ellipse2D c1, Color c2) {
		circle = c1;
		color = c2;
		tickSize = 2;
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
		setCircle(new Ellipse2D.Double(getCircle().getX() /*+ tickSize*/, getCircle().getY() /*+ tickSize*/, getCircle().getWidth() + tickSize, getCircle().getHeight() + tickSize));
	}
	
	public void setTickSize(int s) {
		tickSize = s;
	}
	
}
