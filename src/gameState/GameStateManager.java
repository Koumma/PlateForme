package gameState;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Stack;

public class GameStateManager {
	
	public Stack<GameState> states;
	
	public GameStateManager() {
		states = new Stack<GameState>();
		states.push(new Menu(this));
	}
	
	public void tick() {
		states.peek().tick();
	}
	
	public void draw(Graphics g) {
		states.peek().draw(g);
	}
	
	public void keyPressed(int e) {
		states.peek().keyPressed(e);

	}
	
	public void keyReleased(int e) {
		states.peek().keyReleased(e);
	}
	
	public void mousePressed(MouseEvent e) {
		states.peek().mousePressed(e);
	}
	
	

	public void mouseMoved(MouseEvent e) {
		states.peek().mouseMoved(e);
	}

	

	public void mouseReleased(MouseEvent e) {
		states.peek().mouseReleased(e);
	}

	

	public void mouseWheelMoved(MouseWheelEvent e) {
		states.peek().mouseWheelMoved(e);
	}
	
}
