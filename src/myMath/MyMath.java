package myMath;

public class MyMath {

	public static long lcm(long x, long y) {
		return x * y / gcd(x, y); 
	}

	public static long gcd(long x, long y) {
		long t;
		while(true) {
			if (x < y) {
				t = x;
				x = y;
				y = t;
			}
			if(x % y == 0) {
				return y;
			}
			x -= y * (x / y);
		}
	}

}
