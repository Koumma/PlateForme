package gameState;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import entites.Player;
import maping.Map;
import objets.BlocTombe;
import objets.Objet;

public class Level1 extends GameState {

	private Player player;

	private Map map;

	public Level1(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		player = new Player(25, 46);
		map = new Map("/maps/map1.map", player);

		xOffset = -200;
		yOffset = -400;

	}

	@Override
	public void tick() {
		
		ArrayList<BlocTombe> aEnlever = new ArrayList<BlocTombe>();

		player.tick(map.getBlocs());
		
		for (BlocTombe blocTombe : map.blocTombes) {
			
			if (blocTombe != null) {
				if (player.getCenterPoint().distance(new Point((int) blocTombe.getCenterX() - (int) GameState.xOffset,
						(int) blocTombe.getCenterY() - (int) GameState.yOffset)) < 50) {
					player.ajoute(new Objet(blocTombe.getId()));
					aEnlever.add(blocTombe);
				}
				blocTombe.tick(map.getBlocs());
			}
		}
		
		map.tick();
		
		map.blocTombes.removeAll(aEnlever);
	}

	@Override
	public void draw(Graphics g) {
		map.draw(g);
		player.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		map.mousePressed(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		map.mouseMoved(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		map.mouseReleased(e);
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		player.mouseWheelMoved(e);
	}

}
