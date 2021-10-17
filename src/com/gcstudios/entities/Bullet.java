package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Bullet extends Item{
	
	private int ivTime, ivulframe = 0;

	public Bullet(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
		initBullet();
	}

	public Bullet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		initBullet();
	}
	
	private void initBullet() {
		super.setMask(4, 1, 7, 13);
		ivTime = 30;
	}
	
	public void render(Graphics g) {
		if (ivulframe == 0) {
			super.render(g);
		}
	}
	
	public void tick() {
		super.tick();
		if(ivulframe > 0) ivulframe++;
		if(ivulframe == ivTime*Game.fps) ivulframe = 0;
	}
	
	public boolean take() {
		if(ivulframe == 0) {
			this.ivulframe = 1;
			return true;
		}
		return false;
	}

}
