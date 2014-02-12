import java.awt.Color;
import java.awt.Graphics;


public class PowerSlow extends GameObject{

	final static int DIAMETER = 9;

	public PowerSlow(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
	}
	
	public void accelerate() {}

	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
	}
}
