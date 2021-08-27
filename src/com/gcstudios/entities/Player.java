package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.gcstudios.graphics.Camera;
import com.gcstudios.graphics.Entity;
import com.gcstudios.graphics.World;
import com.gcstudios.main.Game;

public class Player extends Entity{
	
	private boolean up, left, down, right;
	private int dir;
	private final int LEFT = 0, RIGHT = 1;
	private double speed = 1.6;
	private int maskx = 4, masky = 2, maskw = 8, maskh = 14;
	private int hp, ivTime;

	private int frames = 0, maxFrames = 4, index = 0, ivulframe = 0;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		initPlayer();
	}
	
	public Player(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
		initPlayer();
	}

	private void initPlayer() {
		dir = RIGHT;
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		hp = 10; ivTime = 1;
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 0*Game.TILE_SIZE, Game.TILE_SIZE);
			leftPlayer[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 1*Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	
	public void render(Graphics g) {

		g.setColor(Color.WHITE);
		g.setFont(Font.getFont(Font.SANS_SERIF));
		g.drawString(String.valueOf(hp), 0, 10);
		
		if(dir == RIGHT && (ivulframe % 2 == 0)) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}else if(dir == LEFT && (ivulframe % 2 == 0)){
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}
	}
	
	public void tick() {
		boolean moved = false;
		if(right && World.isFree((int) (x + speed) + maskx, this.getY() + masky, maskw, maskh) && !isColliding((int) (x + speed) + maskx, this.getY() + masky)) {
			dir = RIGHT;
			moved = !left;
			x += speed;
		}

		if(left && World.isFree((int) (x - speed) + maskx, this.getY() + masky, maskw, maskh) && !isColliding((int) (x - speed) + maskx, this.getY() + masky)) {
			dir = LEFT;
			moved = !right;
			x -= speed;
		}

		if(up && World.isFree(this.getX() + maskx, (int) (y - speed) + masky, maskw, maskh) && !isColliding(this.getX() + maskx, (int) (y - speed) + masky)) {
			moved = !down;
			y -= speed;
		}

		if(down && World.isFree(this.getX() + maskx, (int) (y + speed) + masky, maskw, maskh) && !isColliding(this.getX() + maskx, (int) (y + speed) + masky)) {
			moved = !up;
			y += speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index = (index + 1) % rightPlayer.length;
			}
		}else {
			index = 0;
		}
		if(ivulframe > 0) ivulframe++;
		if(ivulframe == ivTime*Game.fps) ivulframe = 0;
		
		
		
		if(hp <= 0) {
			JOptionPane.showConfirmDialog(null, "Os zumbi te comeram", "Game over", JOptionPane.OK_CANCEL_OPTION);
			Game.stop();
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	private boolean isColliding(int x, int y) {
		Rectangle colBox = new Rectangle(x,y,maskw,maskh);
		for(Enemy enemy: Game.enemies) {
			if(colBox.intersects(new Rectangle(enemy.getX()+enemy.getMaskx(),enemy.getY()+enemy.getMasky(),enemy.getMaskw(),enemy.getMaskh())) && !this.isIvulnerable()) {
				this.takeDamage(enemy.getDamage());
				return true;
			}
		}
		return false;
	}
	
	public void takeDamage(int damage) {
		hp -= damage;
		ivulframe = 1;
	}
	
	public boolean isIvulnerable() {
		return this.ivulframe != 0;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
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
}
