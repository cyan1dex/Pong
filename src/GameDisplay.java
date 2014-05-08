import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameDisplay extends JPanel implements Runnable {

    Paddle player;
    CompPaddle comp;
    Ball ball;
    public Pong pongFrame;
    private boolean twoPlayers;
    private int playerScore = 0, compScore = 0;
    private Color textColor = Color.white;
    private int borderWidth = 2;
    public double framerate = 50;
    public boolean displayInstructions = true;
    private boolean willDisplayMessage = false;
    private String message = "";
    static final int MAX_SCORE = 21;

    public void paint(Graphics g) {
	// background
	g.setColor(this.getBackground());
	g.fillRect(0, 0, getWidth(), getHeight());

	// dividing lines
	// a line of 10 dashes down the center
	g.setColor(Color.darkGray);
	final int dashes = 10;
	final int lineWidth = 10;
	for (int i = 0; i < getHeight(); i += getHeight() / dashes) {
	    g.fillRect(getWidth() / 2, i - lineWidth / 2, lineWidth,
		    getHeight() / dashes / 2);
	}

	if (displayInstructions)
	    drawInstructions(g);
	if (willDisplayMessage)
	    drawMessage(g);

	if (ball != null)
	    ball.paint(g);
	if (player != null)
	    player.paint(g);
	if (comp != null)
	    comp.paint(g);
	drawScore(g);

    }

    public void updatePositions() {

	if (isBallHittingWall()) {
	    ball.collideWithLine(0);
	    while (isBallHittingWall())
		ball.move();
	}
	if (isBallHit(player)) {
	    ball.collide(player);
	    while (isBallHit(player))
		ball.move();
	} else if (isBallHit(comp)) {
	    ball.collide(comp);
	    while (isBallHit(comp))
		ball.move();
	} else if (isBallScored()) {
	    displayInstructions = false;
	    if (ball.x > getWidth() / 2) {
		playerWonRound(true);
	    } else {
		playerWonRound(false);
	    }
	    repaint();
	    this.reset();
	} else {
	    ball.move();
	    comp.move();
	}
    }

    private void playerWonRound(boolean playerWon) {
	if (playerWon) {
	    playerScore++;
	    displayMessage("Player Scores!");
	    comp.increaseDifficulty();// difficulty varies directly with
				      // the player's score
	} else {
	    compScore++;
	    displayMessage("Computer Scores!");
	    // comp.decreaseDifficulty();
	}
	if (playerScore >= MAX_SCORE || compScore >= MAX_SCORE) {
	    displayMessage(playerScore > compScore ? "Player Wins!" : "Computer Wins!");
	    try {Thread.sleep(2000); } catch (Exception e) {}
	    playerScore = 0;
	    compScore = 0;
	    comp = null;
	    comp = new CompPaddle(this);
	}

    }

    private boolean isBallHittingWall() {
	return Math.abs(ball.y - getHeight() / 2) > getHeight() / 2
		- borderWidth - ball.radius;
	// distance between ball and middle of screen >
	// 1/2 height of screen - border - radius
    }

    private boolean isBallHit(Paddle paddle) { // returns whether the ball is
					       // being hit by a paddle
					       // System.out.println(ball);
	boolean isWithinPaddleLength = Math.abs(ball.y - paddle.y) < paddle.length / 2 + ball.radius/2;
	boolean isTouchingPaddle = Math.abs(ball.x - paddle.x) < paddle.width
		/ 2 + ball.radius;
	return isWithinPaddleLength && isTouchingPaddle;
    }

    private boolean isBallScored() {
	return (int) ball.x + ball.radius > comp.x - comp.width / 2
		+ borderWidth
		|| (int) ball.x - ball.radius < player.x + player.width / 2
			- borderWidth;

    }

    private void drawInstructions(Graphics g) {
	g.setFont(new Font("Sans Serif", Font.PLAIN, 18));
	g.setColor(Color.lightGray);
	String[] lines = { "Move your paddle (left) with the mouse",
		"Click to Pause/Unpause", "Don't let the ball hit your wall!", String.format("Play to %d points!",MAX_SCORE) };
	int height = getHeight() / 4 * 3;
	for (String s : lines) {
	    g.drawString(s, getWidth() / 4, height);
	    height += 30;
	}
    }

    private void drawMessage(Graphics g) {
	g.setFont(new Font("Sans Serif", Font.PLAIN, 16));
	g.setColor(Color.lightGray);
	g.drawString(message, getWidth() / 3, getHeight() / 3);
    }

    public void clearMessage() {
	displayMessage(null);
    }

    public void displayMessage(String s) {
	if (s == "" || s == null)
	    willDisplayMessage = false;
	else {
	    willDisplayMessage = true;
	    message = s;
	}
	repaint();
    }

    // public void setKeyboardControl(boolean kc) {
    // if (kc) {
    // this.removeMouseMotionListener(player);
    // this.addKeyListener(player);
    // if (twoPlayers)
    // this.addKeyListener(comp);
    // }
    // else{
    // this.removeKeyListener(player);
    // this.addMouseMotionListener(player);
    // if(twoPlayers)
    // this.removeKeyListener(comp);
    // }
    // }

    // public void setTwoPlayers(boolean two){
    // this.twoPlayers = two;
    // if(this.twoPlayers){
    // this.addKeyListener(comp);
    // }
    // }

    private void drawScore(Graphics g) {
	g.setColor(textColor);
	g.setFont(new Font("SansSerif", Font.BOLD, 24));
	int fontHeight = 40;
	g.drawString("" + playerScore, getWidth() / 4, fontHeight);
	g.drawString("" + compScore, getWidth() / 4 * 3, fontHeight);
    }

    public void init() {
	player = new Paddle();
	this.addMouseMotionListener(player);
	comp = new CompPaddle(this);
	ball = new Ball();
	player.x = 10;
	player.y = getHeight() / 2;
	this.displayInstructions = true;
	reset();

    }

    public void reset() {
	System.out.println("***RESET***");
	this.setBackground(Color.BLACK);
	ball.x = getWidth() / 2;
	ball.y = getHeight() / 2;
	ball.setVelocity(new Vector(-5, Math.random() * 8 - 4));// random
								// starting
								// angle
	ball.setSpeed(6 + (playerScore / 2));// starting speed varies
						     // with player score
	try {
	    Thread.sleep(2000);
	} catch (Exception e) {
	}
	displayMessage("");
    }

    @Override
    public void run() {
	while (true) {
	    updatePositions();
	    repaint();
	    try {
		Thread.sleep((int) (1 / framerate * 1000));
	    } catch (InterruptedException e) {
		return;
	    }
	}
    }
}
