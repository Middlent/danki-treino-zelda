package com.gcstudios.entities;

import java.awt.image.BufferedImage;

import com.gcstudios.graphics.Entity;

public class Enemy extends Entity{

	public Enemy(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
	}

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
