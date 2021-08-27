package com.gcstudios.entities;

import java.awt.image.BufferedImage;

public class Gun extends Item{

	public Gun(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
	}

	public Gun(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
