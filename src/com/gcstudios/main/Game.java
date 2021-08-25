package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

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
	
	public static int WIDTH = 320, HEIGHT = 180, SCALE = 4;
	public static World world;
	public static Spritesheet spritesheet;
	public static  List<Entity> entities;
	public static Player player;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addKeyListener(this);
		initFrame();

		Game.spritesheet = new Spritesheet("/img/spritesheet.png");
		this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		
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
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
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
	
	public void stop() {
		
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
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
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
