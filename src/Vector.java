public class Vector {
    private double x = 0, y = 0, mag = 0, dir = 0;

    // Things to Consider:
    // Direction (angle) will always be within 0 : 360, in Degrees
    // X and Y will maintain their signs

    /**
     * Initializes a vector by components
     * 
     * @param x
     *            : horizontal component
     * @param y
     *            : vertical component
     */
    public Vector(double x, double y) {

	this.x = x;
	this.y = y;
	this.mag = this.calcMag();
	this.dir = this.calcDir();
    }

    public Vector() {
	this(1, 1);
    }

    /**
     * copies a vector
     */
    public Vector(Vector old) {
	this(old.getX(), old.getY());
    }

    // utility methods

    private double calcMag() {
	return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    private double calcDir() {
	double ret = 0;
	if (!(Math.abs(this.x) < 0.0000001))
	    ret = Math.toDegrees(Math.atan(this.y / this.x)); //returns vals [-90,90]
	else
	    ret = (-90 * this.y / Math.abs(this.y)) + 180; //if x = 0, direction is y component only
	if (x < 0) 
	    ret += 180; //if x is negative, must flip angle due to range of atan
	return referenceAngle(ret);
    }

    private static double referenceAngle(double angle) {
	double ret = angle % 360;
	if (ret < 0)
	    ret += 360;
	return ret;
    }

    // end utility methods

    // setters and getters

    public double getX() {
	return this.x;
    }

    public double getY() {
	return this.y;
    }

    public double getMag() {
	return this.mag;
    }

    public double getDir() {
	return this.dir;
    }

    public void setX(double newX) {
	this.x = newX;
	this.mag = this.calcMag();
	this.dir = this.calcDir();
    }

    public void setY(double newY) {
	this.y = newY;
	this.mag = this.calcMag();
	this.dir = this.calcDir();
    }

    public void setMag(double newMag) {
	this.x = x * (newMag / this.mag);
	this.y = y * (newMag / this.mag);
	this.mag = calcMag();
	this.dir = calcDir();
    }

    public void setDir(double newDir) {
	newDir = referenceAngle(newDir);
	this.x = mag * Math.cos(Math.toRadians(newDir));
	this.y = mag * Math.sin(Math.toRadians(newDir));
	this.dir = calcDir();
    }

    // end setters and getters

    // vector ops

    public String toString() {
	return String.format("< %.2f, %.2f > (%.2f, %.2f)", x, y, mag, dir);
    }

    public double dotProd(Vector a) {
	return this.getX() * a.getX() + this.getY() * a.getY();
    }

    public Vector xProd(Vector a) {
	Vector ret = new Vector();
	// TODO
	assert false;
	return ret;
    }

    public Vector reflect(Vector other) {
	return this.reflect(other.getDir());
    }

    public Vector reflect(double angle) {
	angle = dir + 2 * (angle - dir);// adds twice the difference between the
					// axis and the current direction
	Vector ret = new Vector(this);
	ret.setDir(angle);
	return ret;
    }

    public Vector add(Vector a) {
	return new Vector(a.getX() + this.getX(), this.getY() + a.getY());
    }

    // end vector ops
}
