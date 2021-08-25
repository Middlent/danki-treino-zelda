package com.gcstudios.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Entity {

	public static BufferedImage ENTITY_GUN    = Game.spritesheet.getSprite(0*16,3*16,16);
	public static BufferedImage ENTITY_MEDKIT = Game.spritesheet.getSprite(1*16,3*16,16);
	public static BufferedImage ENTITY_BULLET = Game.spritesheet.getSprite(2*16,3*16,16);
	public static BufferedImage ENTITY_PLAYER = Game.spritesheet.getSprite(3*16,0*16,16);
	public static BufferedImage ENTITY_ENEMY  = Game.spritesheet.getSprite(3*16,2*16,16);
	
	protected double x, y;
	protected int width, height;
	protected BufferedImage sprite;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public Entity(int x, int y, int size, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, width, height, null);
	}
	
	public void tick() {
	}

	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	
}
