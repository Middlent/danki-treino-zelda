package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Gun extends Item{
	
	private int ammo, maxAmmo = 6;

	public Gun(int x, int y, int size, BufferedImage sprite) {
		super(x, y, size, sprite);
		initGun();
	}

	public Gun(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		initGun();
	}
	
	private void initGun() {
		super.setMask(0, 5, 15, 7);
		ammo = maxAmmo;
	}
	
	public boolean shoot() {
		if(ammo - 1 >= 0) {
			ammo -= 1;
			return true;
		}
		return false;
	}
	
	public void recharge(int ammo) {
		this.ammo += ammo;
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

	

}
