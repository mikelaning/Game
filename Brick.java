import java.awt.*;



public class Brick extends GameObject {
	
	final static int HEIGHT = 10;
	final static int WIDTH = 50;
	private boolean b = true;
		
	public Brick (int x, int y) {
		super(x, y, 0, 0, WIDTH, HEIGHT);
		
	}
	// sets to false when a brick gets hit
	public void setBoolean() {
		b = false;
	}
	
	public boolean GetBoolean() {
		return b;
	}
		
	public void draw(Graphics g) {
		if (b) {
			g.fillRect(x, y, WIDTH, HEIGHT);
		}
	}
	@Override
	public void accelerate() {
		// TODO Auto-generated method stub
		
	}
	

}

	
