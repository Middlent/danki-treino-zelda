package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.gcstudios.graphics.Camera;
import com.gcstudios.graphics.Entity;
import com.gcstudios.graphics.World;
import com.gcstudios.main.Game;

public class Player extends Entity{
	
	private boolean up, left, down, right;
	private boolean colliding;
	private Gun gun = null;
	private int dir;
	private double speed = 1.6;
	private int maskx = 4, masky = 2, maskw = 8, maskh = 14;
	private int hp, maxHp = 10, ammo = 0, maxAmmo = 12;
	
	public static final int LEFT = -1, RIGHT = 1;

	private int frames = 0, maxFrames = 4, index = 0, ivulframe = 0, ivTime;
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
		hp = maxHp; ivTime = 1;
		super.setMask(maskx, masky, maskw, maskh);
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 0*Game.TILE_SIZE, Game.TILE_SIZE);
			leftPlayer[i] = Game.spritesheet.getSprite((3+i)*Game.TILE_SIZE, 1*Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	
	public void render(Graphics g) {
		if(dir == RIGHT && (ivulframe % 2 == 0)) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}else if(dir == LEFT && (ivulframe % 2 == 0)){
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		}
		if(gun != null) {
			renderGun(g);
		}
	}
	
	public void tick() {
		super.tick();
		boolean moved = false;
		colliding = this.haveCollisions();
		if(right && World.isFree((int) (x + speed) + maskx, this.getY() + masky, maskw, maskh) && !colliding) {
			dir = RIGHT;
			moved = !left;
			x += speed;
		}

		if(left && World.isFree((int) (x - speed) + maskx, this.getY() + masky, maskw, maskh) && !colliding) {
			dir = LEFT;
			moved = !right;
			x -= speed;
		}

		if(up && World.isFree(this.getX() + maskx, (int) (y - speed) + masky, maskw, maskh) && !colliding) {
			moved = !down;
			y -= speed;
		}

		if(down && World.isFree(this.getX() + maskx, (int) (y + speed) + masky, maskw, maskh) && !colliding) {
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
			//JOptionPane.showConfirmDialog(null, "Os zumbi te comeram", "Game over", JOptionPane.OK_CANCEL_OPTION);
			//Game.stop();
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	
	private void renderGun(Graphics g) {
		/*/modo dir 
		if(dir == RIGHT) {
			gunx = ((int)x + (int)(width*1) - (int)(gun.getWidth()*0.4)) - Camera.x;
			guny = ((int)y + (int)(height*0.5) - (int)(gun.getHeight()*0.5)) - Camera.y;
		} else if(dir == LEFT) {
			gunx = ((int)x + (int)(width*0) + (int)(gun.getWidth()*0.4)) - Camera.x;
			guny = ((int)y + (int)(height*0.5) - (int)(gun.getHeight()*0.5)) - Camera.y;
		}
		
		g.drawImage(ENTITY_GUN, gunx, guny, gun.getWidth()*dir, gun.getHeight(), null);
		/*/
		//modo mouse
		
		int mouseDistanceX = (int) x + (int) ((Game.frame.getX()+8)/Game.SCALE)+Game.TILE_SIZE - (int) (MouseInfo.getPointerInfo().getLocation().getX()/Game.SCALE) - (int) Camera.x;
		int mouseDistanceY = (int) y + (int) ((Game.frame.getY()+32)/Game.SCALE)+Game.TILE_SIZE - (int) (MouseInfo.getPointerInfo().getLocation().getY()/Game.SCALE) - (int) Camera.y;
		Point mouseLocation = new Point(- mouseDistanceX + (int)(width*0.5),- mouseDistanceY + (int)(height*0.5));
		
		double rotation = 0;
		if (mouseLocation.distance(0, 0) != 0) rotation = Math.asin(mouseLocation.getY()/mouseLocation.distance(0, 0));
		
		int gunSidex = 1;
		int gunSidey = 1;
		if(mouseLocation.getX() != 0) gunSidex = Integer.signum((int) mouseLocation.getX());
		if(mouseLocation.getY() != 0) gunSidey = Integer.signum((int) mouseLocation.getY());
		mouseLocation.x = (int) (mouseLocation.getX() * gunSidex);
		mouseLocation.y = (int) (mouseLocation.getY() * gunSidey);
		
		double gunSize = Integer.signum((int) (Math.sin(rotation)*1000)) * Math.sin(rotation) +  Integer.signum((int) (Math.cos(rotation)*1000)) * Math.cos(rotation);
		
		
		//double rotation = Math.toRadians (degree);
		
		double rotationX =  gun.getWidth() / 2;
		double rotationY =  gun.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotation, rotationX, rotationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		int gunDistance = 1;
		

		int gunOffsetX = (int) ((gunDistance+ (width/2))*Math.cos(rotation)) * gunSidex - (8 * (gunSidex-1));
		int gunOffsetY = (int) ((gunDistance+ (height/2))*Math.sin(rotation));
		int gunx = ((int)x + gunOffsetX) - Camera.x;
		int guny = ((int)y + gunOffsetY) - Camera.y;
		
		
		g.drawImage(op.filter(ENTITY_GUN, null), gunx + gunDistance, guny, (int) (gun.getWidth()*gunSize) * gunSidex, (int) (gun.getHeight()*gunSize), null);
		
		/*/
		System.out.println(mouseLocation + 
				" x:" + x + 
				" y:" + y + 
				" framex:" + (int) ((Game.frame.getX()+8 )/Game.SCALE) + 
				" framey:" + (int) ((Game.frame.getY()+32)/Game.SCALE) + 
				" camerax:" + (int) Camera.x + 
				" cameray:" + (int) Camera.y + 
				" mousex:" + (int) (MouseInfo.getPointerInfo().getLocation().getX()/Game.SCALE) + 
				" mousey:" + (int) (MouseInfo.getPointerInfo().getLocation().getY()/Game.SCALE) + 
				" rotation:" + rotation +
				" distancia: " + mouseLocation.distance(0, 0) +
				" largura: " +(int) (gun.getWidth()*gunSize)+ 
				" altura: " +(int) (gun.getHeight()*gunSize)+
				" gox: " + gunOffsetX
				);
		/*/
	}
	
	private boolean haveCollisions() {
		
		List<Item> removed = new ArrayList<Item>();
		for(Item item: Game.items) {
			if(this.isColliding(item)) {
				if(item instanceof Medkit) {
					if(hp<maxHp) if(((Medkit) item).take()) {
						hp += 1;
					}
				}else if(item instanceof Bullet) {
					if(ammo < maxAmmo) if(((Bullet) item).take()) {
						ammo += 1;
					}
				}else if(item instanceof Gun) {
					this.gun = (Gun) item;
					removed.add(item);
				}
			}
		}
		Game.items.removeAll(removed);
		
		for(Enemy enemy: Game.enemies) {
			if(this.isColliding(enemy) && !this.isIvulnerable()) {
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

	public void shoot() {
		if(gun != null) {
			if(gun.shoot()) {
				System.out.println("pew pew");
			}
		}
	}

	public void recharge() {
		if(gun != null) {
			if(gun.getAmmo() < gun.getMaxAmmo() && ammo > 0) {
				if(ammo <= gun.getMaxAmmo() - gun.getAmmo()) {
					gun.recharge(ammo);
					ammo = 0;
				}else {
					ammo -= gun.getMaxAmmo() - gun.getAmmo();
					gun.recharge(gun.getMaxAmmo() - gun.getAmmo());
				}
			}
		}
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

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
	public Gun getGun() {
		return this.gun;
	}

	public void setGun(Gun gun) {
		this.gun = gun;
	}

	public boolean hasGun() {
		return this.gun != null;
	}
	
	
}
