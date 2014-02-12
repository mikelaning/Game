import java.awt.*;

public class Paddle extends GameObject {
	final static int HEIGHT = 15;
	static int WIDTH = 50;
	
	public void upPaddleWidth() {
		WIDTH += 30;
	}
	public void downPaddleWidth() {
		WIDTH -=30;
	}

	public Paddle(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 22, 0, 0, WIDTH, HEIGHT);
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;
	}

	public void draw(Graphics g) {
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
