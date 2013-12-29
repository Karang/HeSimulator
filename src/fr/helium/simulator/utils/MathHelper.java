package fr.helium.simulator.utils;

public class MathHelper {

	public static Vector vectorLerp(float isolevel, Vector p1, Vector p2, float a, float b) {
		if (abs(isolevel - a) < 0.0001)
			return p1;
		if (abs(isolevel - b) < 0.0001)
			return p2;
		float mu = (isolevel - a) / (b - a);
		return new Vector(p1.x + mu * (p2.x - p1.x), p1.y + mu * (p2.y - p1.y), p1.z + mu * (p2.z - p1.z));
	}
	
	public static int nextPowOfTwo(double n) {
		int pow = 1;
		while (n > pow) {
			pow *= 2;
		}
		return pow;
	}
	
	public static float square(float a) {
		return a*a;
	}
	
	public static float abs(float a) {
		return (a>0) ? a : -a;
	}
	
	public static float lerp(float a, float b, float r) {
		//return (1-r)*a + r*b;
		return a + r*(b-a);
	}
	
	public static float clamp(float n, float min, float max) {
		if (n<min) return min;
		if (n>max) return max;
		return n;
	}
	
	public static int sign(float n) {
		if (n<0) return -1;
		return 1;
	}
}
