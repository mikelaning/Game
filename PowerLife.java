import java.awt.Color;
import java.awt.Graphics;


public class PowerLife extends GameObject{

	final static int DIAMETER = 9;

	public PowerLife(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
	}
	
	public void accelerate() {}

	public void draw(Graphics g) {
		g.setColor(Color.yellow.darker());
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
	}

}


