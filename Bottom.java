import java.awt.Graphics;


public class Bottom extends GameObject {
	public Bottom(int courtwidth, int courtheight) {
		super(0, courtheight-2, 0, 0, courtwidth, 1);
	}

	public void accelerate() {}

	public void draw(Graphics g) {}
}
