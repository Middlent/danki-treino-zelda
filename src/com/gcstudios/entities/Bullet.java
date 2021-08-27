package com.gcstudios.entities;

import java.awt.image.BufferedImage;

public class Bullet extends Item{

	public Bullet(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
	}

	public Bullet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
