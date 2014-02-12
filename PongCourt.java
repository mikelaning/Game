import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class PongCourt extends JComponent {
	private Ball ball;
	private Paddle paddle;
	private ArrayList<Brick> brick;

	//bottom row
	private Bottom bottom;

	Random rand = new Random();
	//life power up initializing
	private PowerLife life;
	private int lifenum = rand.nextInt(30);
	private boolean lifebool;

	//paddle power up initializing
	private PowerPaddle p;
	private int padnum = rand.nextInt(30);
	private boolean padbool;
	private boolean bigbool;

	//slow power up initializing
	private PowerSlow slow;
	private int slownum = rand.nextInt(30);
	private boolean slowbool;

	private int interval = 35; // Milliseconds between updates.
	private Timer timer;       // Each time timer fires we animate one step.

	//initializes court size
	final int COURTWIDTH  = 330;
	final int COURTHEIGHT = 250;

	final int PADDLE_VEL = 6;  // How fast does the paddle move
	int lives = 3;// initial lives
	int score = 0;// initial score
	int highscore= 0;// initial high score
	int high= 0;//saved high score
	int[]arr;// array of keeping track of amount of bricks.


	public PongCourt() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);

		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) { tick(); }});
		timer.start();

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					paddle.setVelocity(-PADDLE_VEL, 0);
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					paddle.setVelocity(PADDLE_VEL, 0);
				else if (e.getKeyCode() == KeyEvent.VK_R)
					reset();
			}

			public void keyReleased(KeyEvent e) {
				paddle.setVelocity(0, 0);
			}
		});
		// After a PongCourt object is built and installed in a container
		// hierarchy, somebody should invoke reset() to get things started... 
	}

	/** Set the state of the state of the game to its initial value and 
	    prepare the game for keyboard input. */
	public void reset() {
		timer.start(); // starts timer after losing/winning
		timer.setInitialDelay(1000);
		timer.restart();
		ball = new Ball(0, 95, 3, 3);
		if (bigbool){
			paddle.downPaddleWidth(); // sets paddle to original length.
		}
		bigbool = false;// always false on reset
		paddle = new Paddle(COURTWIDTH, COURTHEIGHT);
		bottom = new Bottom(COURTWIDTH, COURTHEIGHT);
		// specific brick has not been hit yet
		padbool = false;
		lifebool = false; 
		slowbool = false;
		lives = 3;
		score = 0;
		requestFocusInWindow();
		// creates an array list of bricks
		brick = new ArrayList<Brick>();
		for (int i = 0; i< COURTWIDTH/55; i++) {
			for (int j = 0; j< 5; j++) {
				Brick b = new Brick(i*55, j*15);
				brick.add(b);
			}
		}
		arr= new int[brick.size()];// indicator for how many bricks are left
		// Make power ups
		life = new PowerLife(brick.get(lifenum).x+25,brick.get(lifenum).y,0,4);
		p = new PowerPaddle(brick.get(padnum).x+25, brick.get(padnum).y,0,4);
		slow = new PowerSlow(brick.get(slownum).x+25,brick.get(slownum).y,0,4);
		high = highscore;
	}

	//if help button is clicked
	public void helpon() {
		timer.stop();

	}
	//when help screen is exited
	public void helpoff() {
		timer.start();
		requestFocusInWindow();

	}

	/** Update the game one timestep by moving the ball and the paddle. */
	void tick() {
		ball.setBounds(getWidth(), getHeight());
		ball.move();
		paddle.setBounds(getWidth(), getHeight());
		paddle.move();
		ball.bounce(paddle.intersects(ball));

		// for loop to see if ball is intersecting with brick
		for (int i=0; i<brick.size(); i++) {
			if (brick.get(i).GetBoolean()) {
				ball.bounce(brick.get(i).intersects(ball));
				if (!brick.get(i).intersects(ball).equals(Intersection.NONE)) {
					brick.get(i).setBoolean();//destroys brick
					score ++;
					arr = new int[arr.length -1];// bricks left is decreased
				}
			}
		}

		// brick with the life power up gets hit
		if (!brick.get(lifenum).GetBoolean()) {
			life.setBounds(getWidth(), getHeight() +20);
			life.move();
			lifebool = true;
			//paddle hits life power up
			if (paddle.intersects(life).equals(Intersection.UP)){
				lives ++;
				score ++;
				life.setVelocity(0, 0);
				life.y = getHeight() + 10;//prevents more intersecting
				lifebool = false;//not being drawn
			}
			//bottom hits life power up
			if (bottom.intersects(life).equals(Intersection.UP)){
				score --;
				life.setVelocity(0, 0);
				life.y = getHeight() + 10;
				lifebool = false;
			}
		}

		// brick with paddle power up get hit
		if (!brick.get(padnum).GetBoolean()){
			p.setBounds(getWidth(), getHeight() +20);
			p.move();
			padbool = true;
			//paddle hits paddle paddle power up
			if (paddle.intersects(p).equals(Intersection.UP)){
				score ++;
				paddle.upPaddleWidth();
				bigbool = true;// paddles is now big
				int n = paddle.x;//stores paddles current position
				paddle = new Paddle(COURTWIDTH, COURTHEIGHT);//makes paddle with new length
				paddle.x = n;//updates position of paddle
				p.setVelocity(0, 0);
				p.y = getHeight() + 10;
				padbool = false;

			}
			//bottom hits paddle booster
			if (bottom.intersects(p).equals(Intersection.UP)){
				score --;
				p.setVelocity(0, 0);
				p.y = getHeight() + 10;
				padbool = false;
			}
		}

		//brick with slow power up gets hit
		if (!brick.get(slownum).GetBoolean()){
			slow.setBounds(getWidth(), getHeight() +20);
			slow.move();
			slowbool = true;
			//paddle hits slow power up
			if (paddle.intersects(slow).equals(Intersection.UP)){
				score ++;
				//changes ball velocity
				ball.setVelocity(ball.velocityX, ball.velocityY -1);
				slow.setVelocity(0, 0);
				slow.y = getHeight() + 10;
				slowbool = false;
			}
			//bottom hits slow power up
			if (bottom.intersects(p).equals(Intersection.UP)){
				score -= 1;
				slow.setVelocity(0, 0);
				slow.y = getHeight() + 10;
				slowbool = false;
			}

		}

		//highscore updates
		if (highscore<score) {
			highscore = score;
		}

		//paddle misses the ball
		if (!ball.intersects(bottom).equals(Intersection.NONE)){
			lives = lives -1;
			score = score -1;
			//reset ball location and speed
			ball.x = 0;
			ball.y = 95;
			ball.setVelocity(3, 3);
			timer.setInitialDelay(1000);//delays
			timer.restart();
			// gets rid of power ups after dying
			if (bigbool) {
				paddle.downPaddleWidth();
				bigbool = false;
			}
			paddle = new Paddle(COURTWIDTH, COURTHEIGHT);//resets paddle
		}
		repaint(); // Repaint indirectly calls paintComponent.  
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		setBackground(Color.white);
		g.setColor(Color.BLUE);
		ball.draw(g);
		g.setColor(Color.CYAN);
		paddle.draw(g);
		g.setColor(Color.RED.darker().darker().darker());
		//draws the array of bricks
		for (int i = 0; i < brick.size(); i++){
			brick.get(i).draw(g);
		}
		g.setColor(Color.RED);
		// losing message
		if (lives <= 0) {
			if (high < highscore){
				g.drawString("Highscore! YOU LOSE", COURTWIDTH/2-70, COURTHEIGHT/2);
			}
			else
			g.drawString("YOU LOSE", COURTWIDTH/2-30, COURTHEIGHT/2);
			timer.stop();
		}
		g.setColor(Color.GREEN);
		// winning message
		if (arr.length == 0) {
			if (highscore == 33){
				g.drawString("Congratulations! Max High Score! YOU WIN!", 
						COURTWIDTH/2-130, COURTHEIGHT/2);
			}
			else if (high < highscore){
				g.drawString("Highscore! YOU WIN!", COURTWIDTH/2-70, COURTHEIGHT/2);
			}
			else
			g.drawString("YOU WIN!", COURTWIDTH/2-30, COURTHEIGHT/2);
			timer.stop();
		}

		g.setColor(Color.black);
		g.drawString("Score", COURTWIDTH - 80, COURTHEIGHT - 10);
		g.drawString("Lives", 10, COURTHEIGHT - 10);
		g.drawString("HighScore", COURTWIDTH/2 -40, COURTHEIGHT - 10);
		g.setColor(Color.blue);
		g.drawString(Integer.toString(score),COURTWIDTH - 40, COURTHEIGHT - 10); 
		g.setColor(Color.RED);
		g.drawString(Integer.toString(lives), 50, COURTHEIGHT - 10);
		g.setColor(Color.ORANGE.darker());
		g.drawString(Integer.toString(highscore), COURTWIDTH/2 + 30, COURTHEIGHT - 10);

		// draws power ups when brick is hit
		if (lifebool) {
			life.draw(g);
		}
		if (padbool) {
			p.draw(g);
		}
		if (slowbool) {
			slow.draw(g);
		}

	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURTWIDTH, COURTHEIGHT);
	}
}
