package com.gcstudios.graphics;

public class Camera {
	
	public static int x;
	public static int y;
	
	public static int clamp(int pos, int min, int max) {
		if(pos < min) {
			pos = min;
		}
		if(pos > max) {
			pos = max;
		}
		return pos;
	}

}
