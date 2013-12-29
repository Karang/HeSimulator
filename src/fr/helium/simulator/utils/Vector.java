package fr.helium.simulator.utils;

public class Vector {
	
	public static final Vector ZERO = new Vector(0, 0, 0);
	
	public static final Vector UP = new Vector(0, 1, 0);
	public static final Vector DOWN = new Vector(0, -1, 0);
	public static final Vector NORTH = new Vector(0, 0, 1);
	public static final Vector SOUTH = new Vector(0, 0, -1);
	public static final Vector WEST = new Vector(-1, 0, 0);
	public static final Vector EAST = new Vector(1, 0, 0);
	
	public final float x, y, z;
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public Vector normalize() {
		float length = length();
		return new Vector(x/length, y/length, z/length);
	}
	
	public Vector mul(float k) {
		return new Vector(x*k, y*k, z*k);
	}
	
	public Vector add(float x, float y, float z) {
		return new Vector(this.x+x, this.y+y, this.z+z);
	}
	
	public Vector add(Vector v) {
		return add(v.x, v.y, v.z);
	}
	
	public Vector sub(float x, float y, float z) {
		return add(-x, -y, -z);
	}
	
	public Vector sub(Vector v) {
		return add(-v.x, -v.y, -v.z);
	}
	
	public float distance(Vector v) {
		return v.sub(this).length();
	}
	
	public float dot(Vector v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	public Vector cross(Vector v) {
		return new Vector(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
	}
	
	public Vector min(Vector v) {
		return new Vector(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
	}
	
	public Vector max(Vector v) {
		return new Vector(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
	}
	
	public Vector lerp(Vector v, float r) {
		return new Vector(MathHelper.lerp(x, v.x, r), MathHelper.lerp(y, v.y, r), MathHelper.lerp(z, v.z, r));
	}
	
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
	public boolean equals(Vector v) {
		if (this==v) return true;
		return (x==v.x && y==v.y && z==v.z);
	}
}
