package jeu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;


import gameState.GameStateManager;
import graphique.Image;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private Thread thread;
	private boolean isRunning = false;
	
	
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	private boolean mouseDown = false;
	private boolean dragging = false;

	
	private MouseEvent me;

	
	private GameStateManager gsm;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setFocusable(true);
		
		new Image();
		
		start();
	}
	

	

	
	private void initThread() {
			new Thread() {
				public void run() {
					while (mouseDown) {
						gsm.mousePressed(me);
						
					}
				}
			}.start();
	}
	
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long start, elapsed, wait;
		gsm = new GameStateManager();
		while (isRunning) {
			start = System.nanoTime();
			
			tick();
			repaint();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime -elapsed / 1000000;
			
			if (wait < 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		gsm.tick();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		gsm.draw(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.me = e;
		mouseDown = true;
		initThread();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
		mouseDown = false;
	}

	

	@Override
	public void mouseMoved(MouseEvent e) {
		this.me = e;
		gsm.mouseMoved(e);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		this.me = e;
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gsm.mouseWheelMoved(e);
	}

}
