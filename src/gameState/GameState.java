package gameState;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class GameState {
	
	protected GameStateManager gsm;
	public static double xOffset, yOffset;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		
		this.xOffset = 0;
		this.yOffset = 0;
		
		init();
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void draw(Graphics g);
	public abstract void keyPressed(int e);
	public abstract void keyReleased(int e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseWheelMoved(MouseWheelEvent e);
}
