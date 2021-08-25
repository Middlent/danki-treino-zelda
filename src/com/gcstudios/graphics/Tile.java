package com.gcstudios.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Tile {
	
	public static BufferedImage UL_TILE_WALL = Game.spritesheet.getSprite(0*16, 0*16, 16);
	public static BufferedImage U_TILE_WALL  = Game.spritesheet.getSprite(1*16, 0*16, 16);
	public static BufferedImage UR_TILE_WALL = Game.spritesheet.getSprite(2*16, 0*16, 16);
	public static BufferedImage L_TILE_WALL  = Game.spritesheet.getSprite(0*16, 1*16, 16);
	public static BufferedImage TILE_FLOOR   = Game.spritesheet.getSprite(1*16, 1*16, 16);
	public static BufferedImage R_TILE_WALL  = Game.spritesheet.getSprite(2*16, 1*16, 16);
	public static BufferedImage DL_TILE_WALL = Game.spritesheet.getSprite(0*16, 2*16, 16);
	public static BufferedImage D_TILE_WALL  = Game.spritesheet.getSprite(1*16, 2*16, 16);
	public static BufferedImage DR_TILE_WALL = Game.spritesheet.getSprite(2*16, 2*16, 16);
	
	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
