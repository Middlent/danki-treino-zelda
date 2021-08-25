package com.gcstudios.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gcstudio.tiles.FloorTile;
import com.gcstudio.tiles.WallTile;
import com.gcstudios.entities.Bullet;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Gun;
import com.gcstudios.entities.Medkit;
import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class World {
	
	private Tile[] tiles;
	
	public static int WIDTH, HEIGHT;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int i = 0; i < map.getWidth() ; i++) {
				for(int j = 0; j < map.getHeight(); j++) {
					tiles[i + j*map.getWidth()] = new FloorTile(i*16, j*16,Tile.TILE_FLOOR);
					switch(pixels[i + j*map.getWidth()]){
						case 0xFF777777: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.UL_TILE_WALL);
							break; 
						case 0xFF888888: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.U_TILE_WALL);
							break; 
						case 0xFF999999: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.UR_TILE_WALL);
							break; 
						case 0xFF444444: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.L_TILE_WALL);
							break; 
						case 0xFF666666: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.R_TILE_WALL);
							break; 
						case 0xFF111111: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.DL_TILE_WALL);
							break; 
						case 0xFF222222: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.D_TILE_WALL);
							break; 
						case 0xFF333333: 
							tiles[i + j*map.getWidth()] = new WallTile(i*16, j*16,Tile.DR_TILE_WALL);
							break; 
						case 0xFFFF0000: 
							Game.entities.add(new Enemy(i*16, j*16, 16, Entity.ENTITY_ENEMY));
							break; 
						case 0xFF00FF00: 
							Game.entities.add(new Medkit(i*16, j*16, 16, Entity.ENTITY_MEDKIT));
							break; 
						case 0xFF0000FF: 
							Game.player = new Player(i*16,j*16,16,Entity.ENTITY_PLAYER);
							break;
						case 0xFFFFFF00: 
							Game.entities.add(new Bullet(i*16, j*16, 16, Entity.ENTITY_BULLET));
							break; 
						case 0xFFFF00FF: 
							Game.entities.add(new Gun(i*16, j*16, 16, Entity.ENTITY_GUN));
							break; 
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yStart = Camera.y >> 4;
		int yFinal = yStart + (Game.WIDTH >> 4);
		
		for(int i = xStart; i <= xFinal; i++) {
			for(int j = yStart; j <= yFinal; j++) {
				if(i < 0 ||i >= WIDTH|| j < 0|| j >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[i + j*WIDTH];
				tile.render(g);
			}
		}
	}

}
