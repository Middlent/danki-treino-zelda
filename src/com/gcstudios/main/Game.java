package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Item;
import com.gcstudios.entities.Player;
import com.gcstudios.graphics.Entity;
import com.gcstudios.graphics.Spritesheet;
import com.gcstudios.graphics.World;


public class Game extends Canvas implements Runnable,KeyListener{
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean isRunning;
	private BufferedImage image;
	private JFrame frame;
	
	public static int WIDTH_RATIO = 16, HEIGHT_RATIO = 9, SCREEN_SIZE = 13, SCALE = 4, TILE_SIZE = 16, WIDTH = SCREEN_SIZE*WIDTH_RATIO, HEIGHT = SCREEN_SIZE*HEIGHT_RATIO, fps = 60;
	public static World world;
	public static Spritesheet spritesheet;
	public static  List<Entity> entities;
	public static  List<Item> items;
	public static  List<Enemy> enemies;
	public static Player player;
	public static Random random;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addKeyListener(this);
		initFrame();
		
		random = new Random();
		Game.spritesheet = new Spritesheet("/img/spritesheet.png");
		this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		Game.items = new ArrayList<Item>();
		Game.enemies = new ArrayList<Enemy>();
		
		Game.world = new World("/img/maps/1.png");
		
		Game.entities.add(player);
		
	}
	
	private void initFrame() {
		frame = new JFrame("Game");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000 / fps;
		double delta = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
			}
		}
		stop();
	}
	
	public void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public static void stop() {
		System.exit(0);
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		Game.world.render(g);
		for(int i = 0; i < items.size(); i++) {
			Entity e = items.get(i);
			e.render(g);
		}
		for(int i = 0; i < enemies.size(); i++) {
			Entity e = enemies.get(i);
			e.render(g);
		}
		player.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(true);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.setDown(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(true);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			player.setRight(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(false);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.setDown(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(false);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			player.setRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_EQUALS && player.getSpeed()<=2) {
			player.setSpeed(player.getSpeed() + 0.1);
		}else if(e.getKeyCode() == KeyEvent.VK_MINUS && player.getSpeed()>=1){
			player.setSpeed(player.getSpeed() - 0.1);
		}
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
}
