package com.gcstudios.graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Entity {

	//Carregamento dos Sprites
	public static BufferedImage ENTITY_GUN    = Game.spritesheet.getSprite(0*16,3*16,16);
	public static BufferedImage ENTITY_MEDKIT = Game.spritesheet.getSprite(1*16,3*16,16);
	public static BufferedImage ENTITY_BULLET = Game.spritesheet.getSprite(2*16,3*16,16);
	public static BufferedImage ENTITY_PLAYER = Game.spritesheet.getSprite(3*16,0*16,16);
	public static BufferedImage ENTITY_ENEMY  = Game.spritesheet.getSprite(3*16,2*16,16);
	
	//Declaração das variáveis
	protected double x, y, mx, my;
	protected int width, height;
	protected BufferedImage sprite;
	protected Rectangle mask;
	
	//Construtor da Entidade com altura e largura diferentes
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		mx = 0;
		my = 0;
		this.mask = new Rectangle(x,y,width,height);
	}

	//Construtor da Entidade com altura e largura iguais
	public Entity(int x, int y, int size, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;
		this.sprite = sprite;

		mx = 0;
		my = 0;
		this.mask = new Rectangle(x,y,width,height);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, width, height, null);
	}
	
	public void tick() {
		this.updateMask();
	}

	private void updateMask() {
		this.mask.setLocation((int)(x+mx) - Camera.x, (int)(y+my) - Camera.y);
	}

	public Rectangle getMask() {
		return mask;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		mx = maskx;
		my = masky;
		this.mask = new Rectangle((int)(maskx + x),(int)(masky + y),mwidth,mheight);
	}
	
	public boolean isColliding(Entity entity) {
		return this.mask.intersects(entity.getMask()); 
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
