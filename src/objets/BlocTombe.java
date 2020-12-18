package objets;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameState.GameState;
import graphique.Image;
import physique.Collision;

public class BlocTombe extends Bloc {

	public static final int TAILLE = 10;

	private boolean enChute = true;
	private double vitesseMaxChute = 5;
	private double vitesseChute = 0.1;

	public BlocTombe(int x, int y, int id) {
		super(x, y, id);
		setBounds(x, y, TAILLE, TAILLE);
		this.id = id;
		this.selected = false;
		this.detruit = false;
	}

	public BlocTombe(Bloc b, int id) {
		super(b, id);
		setBounds(b.x, b.y, TAILLE, TAILLE);
		this.id = id;
		this.selected = false;
		this.detruit = false;
	}

	public BlocTombe(Bloc b) {
		super(b, b.getId());
		setBounds(x, y, TAILLE, TAILLE);
		this.selected = false;
		this.detruit = false;
	}

	@Override
	public void draw(Graphics g) {
		if (this.id != 0) {
			g.drawImage(Image.blocs[this.id - 1], x - (int) GameState.xOffset, y - (int) GameState.yOffset, width,
					height, null);
		}
	}

	public void tick(Bloc[][] b) {

		if (!enChute) {
			enChute = true;
		}

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {

				if (b[i][j].getId() != 0) {

					if (new Rectangle(x, y, TAILLE, TAILLE).intersects(b[i][j])) {
						enChute = false;
					}

				}

			}
		}

		if (enChute) {
			y += vitesseChute;

			if (vitesseChute < vitesseMaxChute) {
				vitesseChute += 0.1;
			}
		} else {
			vitesseChute = 0.1;
		}

		

	}

}
