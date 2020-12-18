package objets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gameState.GameState;
import graphique.Image;

public class Bloc extends Rectangle{
	
	public static final int TAILLE = 16;
	
	protected int id;
	
	protected boolean selected;
	
	protected boolean detruit;
	
	
	public Bloc(int x, int y, int id) {
		setBounds(x, y, TAILLE, TAILLE);
		this.id = id;
		this.selected = false;
		this.detruit = false;
	}
	
	public Bloc(Bloc bloc, int id) {
		setBounds(bloc.x, bloc.y, TAILLE, TAILLE);
		this.id = id;
		this.selected = false;
		this.detruit = false;
	}

	public void draw(Graphics g) {
		if (id != 0 && !detruit) {
			g.setColor(Color.BLACK);
			g.drawImage(Image.blocs[id - 1], x - (int)GameState.xOffset, y - (int)GameState.yOffset, width, height, null);
			if (selected) {
				g.setColor(new Color(255, 255, 0, 100));
				g.fillRect(x - (int)GameState.xOffset, y - (int)GameState.yOffset, TAILLE, TAILLE);
			}
		} else if (id == 0) {
			g.drawImage(Image.blocs[2], x - (int)GameState.xOffset, y - (int)GameState.yOffset, width, height, null);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public void selectionne() {
		this.selected = true;
	}

	public void deselectionne() {
		this.selected = false;
	}
	
	public void detruire() {
		this.id = 0;
	}

	
	
	
	
}
