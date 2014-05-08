

public class CompPaddle extends Paddle {

    // easy, only moves when ball moves towards it
    // medium, moves faster
    // hard, moves to where the ball will hit
    // impossible, moves faster than hard

    // public static enum Difficulty{//ball moves faster in harder difficulties?
    // Easy (1),
    // Medium (3),
    // Hard (5),
    // Impossible (10);
    // private final int speed; //paddle will move every "speed" cycles
    // private Difficulty (int speed){
    // this.speed = speed;
    // }
    // public int speed() {return speed;}
    // }
    //
    // Difficulty difficulty = Difficulty.Easy;
    private double speed = 5;
    public static final double speedIncrement = 0.48;
    public static final int STOPPED = 0;
    public GameDisplay view; //so the computer can respond to the game environment

    public CompPaddle() {
	direction = STOPPED;
    }
    
    public CompPaddle(GameDisplay g){
	view = g;
	x = view.getWidth() - 10;
	y = view.getHeight() / 2;
    }

    public void move() {
	if (view != null
		&& view.ball != null
		&& (view.ball.getDirection() < 90 || view.ball.getDirection() > 270)) {
	    direction = view.ball.y > y ? 1 : -1; // only moves when the ball
						  // moves towards it
	} else
	    direction = STOPPED;
	y += direction * speed;
    }

    public void increaseDifficulty() {
	speed += speedIncrement;
    }

    public void decreaseDifficulty() {
	if (speed > speedIncrement)
	    speed -= speedIncrement;
    }

    public String toString() {
	return String.format("Comp%s\nspeed: %.2f", super.toString(), speed);
    }

}
