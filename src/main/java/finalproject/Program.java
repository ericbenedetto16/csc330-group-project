package finalproject;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Program extends Canvas implements Runnable {
	private static final long serialVersionUID = 7485421146162456528L;
	protected static final int WIDTH = 1200, HEIGHT = 710;
	private Thread thread;
	private boolean running = false;
	public static boolean paused = false;
	private MapView map;
	private ToolView tools;
	private SideBarView sidebar;

	public Program() {
		new Window(WIDTH, HEIGHT, "CSC330 Final Project", this);	
		map = new MapView();
		sidebar = new SideBarView();
		tools = new ToolView();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		map.render(g);
		sidebar.render(g);
		tools.render(g);
		
		bs.show();
	}
	
	public static void main(String[] args) {
		new Program();
	}

}
