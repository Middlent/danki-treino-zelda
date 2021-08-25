package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.graphics.Camera;
import com.gcstudios.graphics.Entity;
import com.gcstudios.graphics.World;
import com.gcstudios.main.Game;

public class Player extends Entity{
	
	private boolean up, left, down, right;
	private int dir;
	private final int LEFT = 0, RIGHT = 1;
	private double speed = 1.6;
	
	private int frames = 0, maxFrames = 4, index = 0;
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
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((3+i)*16, 0, 16);
			leftPlayer[i] = Game.spritesheet.getSprite((3+i)*16, 16, 16);
		}
	}
	
	public void render(Graphics g) {
		if(dir == RIGHT) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}else if(dir == LEFT){
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}
	}
	
	public void tick() {
		boolean moved = false;
		if(right) {
			dir = RIGHT;
			moved = !left;
			x += speed;
		}

		if(left) {
			dir = LEFT;
			moved = !right;
			x -= speed;
		}

		if(up) {
			moved = !down;
			y -= speed;
		}

		if(down) {
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
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);
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
}
