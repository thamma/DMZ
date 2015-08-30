package me.thamma.DMZ.utils;

public class Cantor {
	public static long compute(long x, long y) {
		return (x + y) * (x + y + 1) / 2 + y;
	}

	public static long computeX(long z) {
		long j = (long) Math.floor(Math.sqrt(0.25 + 2 * z) - 0.5);
		return j - (z - j * (j + 1) / 2);
	}

	public static long computeY(long z) {
		long j = (long) Math.floor(Math.sqrt(0.25 + 2 * z) - 0.5);
		return z - j * (j + 1) / 2;
	}

}

class Tupel {
	public int x;
	public int y;

	public Tupel(int x, int y) {
		this.x = x;
		this.y = y;
	}
}