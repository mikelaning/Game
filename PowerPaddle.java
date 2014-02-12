import java.awt.Color;
import java.awt.Graphics;


public class PowerPaddle extends GameObject{
	final static int DIAMETER = 9;

	public PowerPaddle(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
	}

	public void accelerate() {}

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
	}
}
