package Geometry;

public class Vector {
	public static final Vector ZERO_VECTOR = new Vector(0, 0);
	
	private double _x;
	private double _y;
	
	public Vector(double x, double y) {
		_x = x;
		_y = y;
	}
	
	public double x() {
		return _x;
	}
	
	public double y() {
		return _y;
	}
	
	public Vector plus(Vector other) {
		return new Vector(x() + other.x(), y() + other.y());
	}
	
	public Vector minus(Vector other) {
		return new Vector(x() - other.x(), y() - other.y());
	}
	
	public double length() {
		return Math.sqrt(x() * x() + y() * y());
	}
	
	public Vector normalized() {
		double length = length();
		return new Vector(x() / length, y() / length());
	}

	public Vector scale(double amount) {
		return new Vector(x() * amount, y() * amount);
	}
	
	public Vector scale(Vector amount) {
		return new Vector(x() * amount.x(), y() * amount.y());
	}
	
	public String toString() {
		return "(" + x() + ", " + y() + ")";
	}
	
	public static Vector random() {
		return new Vector(Math.random(), Math.random());
	}
}
