package com.gcstudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.main.Game;


public class UI {
	
	private double hpWitdh = 0.35 * Game.WIDTH, hpHeight = 0.05 * Game.HEIGHT;
	
	public void render(Graphics g) {
		hPHUD(g);
	}
	
	public void postRender(Graphics g) {
		postHPHUD(g);
		postAmmoHUD(g);
		if(Game.player.hasGun()) {
			postGunHUD(g);
		}
	}
	
	public void tick() {
	}
	
	private void hPHUD(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(5, 5,(int) hpWitdh,(int) hpHeight);
		
		g.setColor(new Color(0,155,0));
		g.fillRect(5, 5,(int) ((double)Game.player.getHp()/(double)Game.player.getMaxHp() * hpWitdh),(int) hpHeight);
	}
	
	private void postHPHUD(Graphics g) {
		g.setColor(Color.WHITE);
		String textHp = String.valueOf(Game.player.getHp()) + " / " + String.valueOf(Game.player.getMaxHp());
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,(int) hpHeight*Game.SCALE));
		int textHpWidth = g.getFontMetrics().stringWidth(textHp);
		g.drawString(textHp, (int) (5 + (hpWitdh*Game.SCALE - textHpWidth)/2), (int) ((0.8*hpHeight+5)*Game.SCALE));
	}
	
	private void postAmmoHUD(Graphics g) {
		g.setColor(Color.WHITE);
		String textAmmo = String.valueOf(Game.player.getAmmo()) + " / " + String.valueOf(Game.player.getMaxAmmo());
		int textAmmoWidth = g.getFontMetrics().stringWidth(textAmmo);
		g.drawString(textAmmo, (int) ((Game.WIDTH * Game.SCALE - textAmmoWidth) - 10), (int) (Game.HEIGHT * Game.SCALE) - 5);
		
		g.drawImage(Entity.ENTITY_BULLET, (int) ((Game.WIDTH * Game.SCALE - textAmmoWidth) - 10) - 5  - Entity.ENTITY_BULLET.getWidth(), (int) (Game.HEIGHT * Game.SCALE) - 5 - Entity.ENTITY_BULLET.getHeight(), Entity.ENTITY_BULLET.getWidth(), Entity.ENTITY_BULLET.getHeight() + 1, null);
	
	}
	
	private void postGunHUD(Graphics g) {
		g.setColor(Color.WHITE);
		String textAmmo = String.valueOf(Game.player.getGun().getAmmo()) + " / " + String.valueOf(Game.player.getGun().getMaxAmmo());
		int textAmmoWidth = g.getFontMetrics().stringWidth(textAmmo);
		g.drawString(textAmmo, (int) ((Game.WIDTH * Game.SCALE - textAmmoWidth) - 10), (int) (Game.HEIGHT * Game.SCALE) - 5 - Entity.ENTITY_BULLET.getHeight() - 5);
		
		g.drawImage(Entity.ENTITY_GUN, (int) ((Game.WIDTH * Game.SCALE - textAmmoWidth) - 10) - 5  - Entity.ENTITY_GUN.getWidth(), (int) (Game.HEIGHT * Game.SCALE) - 5 - Entity.ENTITY_BULLET.getHeight() - Entity.ENTITY_GUN.getHeight() - 5, Entity.ENTITY_GUN.getWidth(), Entity.ENTITY_GUN.getHeight() + 1, null);
	
	}

}
