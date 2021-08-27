package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.gcstudios.graphics.Camera;
import com.gcstudios.graphics.Entity;
import com.gcstudios.graphics.World;
import com.gcstudios.main.Game;

public class Enemy extends Entity{

	private final int LEFT = 0, RIGHT = 1;
	private double speed = 1;
	private int dir;
	private int maskx = 4, masky = 2, maskw = 8, maskh = 14;
	private int range = 100, will;
	private int hp, damage;
	
	private int frames = 0, maxFrames = 4, index = 0;
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	
	public Enemy(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
		initEnemy();
	}

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		initEnemy();
	}

	private void initEnemy() {
		dir = RIGHT;
		rightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage[4];
		will = Game.random.nextInt(range);
		hp = 2; damage = 1;
		
		for(int i = 0; i < 4; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 2*Game.TILE_SIZE, Game.TILE_SIZE);
			leftEnemy[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 3*Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	
	
	public void tick() {
		
		boolean moved = false;
		
		if(will > (int) (Game.player.getX() -  x) * Integer.signum((int) (Game.player.getX() -  x)) &&
				will > (int) (Game.player.getY() -  y) * Integer.signum((int) (Game.player.getY() -  y))) {
			if((int) (Game.player.getX() -  x) > 0 && World.isFree((int) (x + speed) + maskx, this.getY() + masky, maskw, maskh) && !isColliding((int) (x + speed) + maskx, this.getY() + masky)) {
				dir = RIGHT;
				x += speed;
				moved = true;
			}else if((int) (Game.player.getX() -  x) < 0 && World.isFree((int) (x - speed) + maskx, this.getY() + masky, maskw, maskh) && !isColliding((int) (x - speed) + maskx, this.getY() + masky)) {
				dir = LEFT;
				x -= speed;
				moved = true;
			}
			if((int) (Game.player.getY() - y) < 0 && World.isFree(this.getX() + maskx, (int) (y - speed) + masky, maskw, maskh) && !isColliding(this.getX() + maskx, (int) (y - speed) + masky)) {
				y -= speed;
				moved = true;
			}else if((int) (Game.player.getY() - y) > 0 && World.isFree(this.getX() + maskx, (int) (y + speed) + masky, maskw, maskh) && !isColliding(this.getX() + maskx, (int) (y + speed) + masky)) {
				y += speed;
				moved = true;
			}
		}else if(0 <= (int) (Game.player.getX() -  x) * Integer.signum((int) (Game.player.getX() -  x)) &&
				0 <= (int) (Game.player.getY() -  y) * Integer.signum((int) (Game.player.getY() -  y))) {
			will = Game.random.nextInt(range);
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index = (index + 1) % rightEnemy.length;
				will = Game.random.nextInt(range);
			}
		}else {
			index = 0;
		}
		
	}
	
	private boolean isColliding(int x, int y) {
		Rectangle colBox = new Rectangle(x,y,maskw,maskh);
		for(Enemy enemy: Game.enemies) {
			if(enemy != this) {
				if(colBox.intersects(new Rectangle(enemy.getX()+enemy.getMaskx(),enemy.getY()+enemy.getMasky(),enemy.getMaskw(),enemy.getMaskh()))) {
					return true;
				}
			}
		}
		if(colBox.intersects(new Rectangle(Game.player.getX()+Game.player.getMaskx(),Game.player.getY()+Game.player.getMasky(),Game.player.getMaskw(),Game.player.getMaskh()))) {
			if(!Game.player.isIvulnerable()) Game.player.takeDamage(damage);
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		if(dir == RIGHT) {
			g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}else if(dir == LEFT){
			g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}
	}

	public int getMaskx() {
		return maskx;
	}

	public void setMaskx(int maskx) {
		this.maskx = maskx;
	}

	public int getMasky() {
		return masky;
	}

	public void setMasky(int masky) {
		this.masky = masky;
	}

	public int getMaskw() {
		return maskw;
	}

	public void setMaskw(int maskw) {
		this.maskw = maskw;
	}

	public int getMaskh() {
		return maskh;
	}

	public void setMaskh(int maskh) {
		this.maskh = maskh;
	}
	
	public int getDamage() {
		return this.damage;
	}

}
