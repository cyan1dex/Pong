import java.awt.Color;

import java.awt.Graphics;

public class Ball {

    public double x, y;
    private Vector velocity;
    public int radius = 10;
    public Color color = Color.white;

    public Ball() {
	this(50, 50);
    }

    public Ball(int x, int y) {
	this.x = x;
	this.y = y;
	velocity = new Vector(-2, 2);
    }

    public void collideWithLine(int plane) { // collision with plane
	// System.out.println("collide called with "+ plane + "\n  " + this);
	velocity = velocity.reflect(90 + plane);// reflect velocity over normal
	velocity.setMag(velocity.getMag() * -1);// invert velocity
	// System.out.println(this);
    }

    long lastCollisionTime = 0;

    public void collide(Paddle paddle) {
	boolean print = false;
	if (System.currentTimeMillis() - lastCollisionTime < 50)
	    print = true;
	int distanceFromCenter = (int) (this.y - paddle.y);
	double initialX = this.velocity.getX();
	if (print)
	    System.out.println("collide called on " + this + " at "
		    + System.currentTimeMillis() % 1000000);

	// bounces against paddle, 60¡ at top of paddle, 120¡ at bottom, 90¡ at
	// middle
	double angleMaxDifference = 60; // TODO error because paddle represented
					// as rectangle, but actually round
	if (print)
	    System.out.println("  with "
		    + (90 + distanceFromCenter / (paddle.length / 2.0)
			    * angleMaxDifference));

	collideWithLine((int) (90 + distanceFromCenter / (paddle.length / 2.0)
		* angleMaxDifference));

	if (initialX * this.velocity.getX() > 0) { // QUICK FIX for above error:
	    this.velocity.setX(-1 * this.velocity.getX());// if after reflection
							  // still moving
							  // towards paddle,
							  // invert x
	    if (print)
		System.err.println("  quick fix in collide");
	}
	if (Math.abs(velocity.getX()) < 1) {
	    velocity.setX(1 * (velocity.getX() > 0 ? 1 : -1));
	    if (print)
		System.err.println(" autoset X to " + velocity.getX());
	}

	// increase speed 5% every hit by a paddle
	velocity.setMag(velocity.getMag() * 1.05);
	if (print)
	    System.out.println("  result " + this);

	lastCollisionTime = System.currentTimeMillis();
    }

    public void move() {
	x += velocity.getX();
	y += velocity.getY();
    }

    public double getDirection() {
	return velocity.getDir();
    }

    public double getSpeed() {
	return velocity.getMag();
    }

    public void setSpeed(double s) {
	velocity.setMag(s);
    }

    public void setVelocity(Vector v) {
	velocity = v;
    }

    public void paint(Graphics g) {
	g.setColor(color);
	g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
    }

    public String toString() {
	return String.format(
		"Ball\n  X = %d, Y = %d radius: %d\n  velocity: %s", (int) x,
		(int) y, radius, velocity.toString());

    }

}
