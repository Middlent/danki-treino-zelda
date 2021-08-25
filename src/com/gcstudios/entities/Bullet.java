package com.gcstudios.entities;

import java.awt.image.BufferedImage;

import com.gcstudios.graphics.Entity;

public class Bullet extends Entity{

	public Bullet(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
	}

	public Bullet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
