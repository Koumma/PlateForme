package maping;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import entites.Player;
import gameState.GameState;
import graphique.Image;
import jeu.GamePanel;
import objets.Bloc;
import objets.BlocTombe;
import objets.Objet;

public class Map {

	private String path;
	private int width, height;

	private Bloc[][] blocs;

	Bloc blocSelectionne = null;


	Rectangle caTouche;

	Player player;
	Rectangle hitbox;

	public CopyOnWriteArrayList<BlocTombe> blocTombes;

	public Map(String loadPath, Player player) {
		this.player = player;
		this.hitbox = new Rectangle(player.getPoint(), player.getDimension());
		this.path = loadPath;
		this.blocTombes = new CopyOnWriteArrayList<BlocTombe>();
		randomMap();
		// loadMap();
	}

	private void randomMap() {
		this.width = 20;
		this.height = 80;

		blocs = new Bloc[this.height][this.width];

		// initialisation à AIR
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.blocs[i][j] = new Bloc(i * Bloc.TAILLE, j * Bloc.TAILLE, 0);
			}
		}

		// sol au fond
		for (int i = 0; i < this.height; i++) {
			for (int j = this.width - 1; j >= this.width - 4; j--) {
				this.blocs[i][j] = new Bloc(i * Bloc.TAILLE, j * Bloc.TAILLE, 2);
			}
		}

		this.blocs[0][0] = new Bloc(0, 0, 1);
		this.blocs[1][0] = new Bloc(Bloc.TAILLE, 0, 1);

	}

	// map prédéfinie
	private void loadMap() {
		InputStream is = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());

			blocs = new Bloc[height][width];

			for (int i = 0; i < height; i++) {
				String line = br.readLine();

				String[] tokens = line.split("\\s+");

				for (int j = 0; j < width; j++) {
					blocs[i][j] = new Bloc(j * Bloc.TAILLE, i * Bloc.TAILLE, Integer.parseInt(tokens[j]));
				}
			}

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics g) {

		// background
		g.drawImage(Image.backgrounds[0], -(int) GameState.xOffset / 2 - 200, -(int) GameState.yOffset / 2 - 350, 1600,
				1200, null);

		if (caTouche != null) {
			g.fillRect(caTouche.x, caTouche.y, caTouche.width, caTouche.height);
		}

		// blocs
		for (int i = 0; i < blocs.length; i++) {
			for (int j = 0; j < blocs[0].length; j++) {
				blocs[i][j].draw(g);
			}
		}
		for (BlocTombe blocTombe : blocTombes) {
			if (blocTombe != null) {
				blocTombe.draw(g);
			}
		}
	}

	public Bloc[][] getBlocs() {
		return blocs;
	}

	public void mousePressed(MouseEvent e) {

		for (int i = 0; i < blocs.length; i++) {
			for (int j = 0; j < blocs[0].length; j++) {

				// pour ne pas placer sur soi
				if (!hitbox.intersects(new Rectangle(blocs[i][j].x - (int) GameState.xOffset,
						blocs[i][j].y - (int) GameState.yOffset, Bloc.TAILLE, Bloc.TAILLE))) {

					if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK
							|| e.getButton() == 1) {

						if (new Rectangle(blocs[i][j].x - (int) GameState.xOffset,
								blocs[i][j].y - (int) GameState.yOffset, Bloc.TAILLE, Bloc.TAILLE)
										.contains(e.getPoint())
								&& blocs[i][j].getId() != 0) {

							if (new Point(blocs[i][j].x - (int) GameState.xOffset,
									blocs[i][j].y - (int) GameState.yOffset).distance(hitbox.getCenterX(),
											hitbox.getCenterY()) < 110) {

								this.blocTombes.add(new BlocTombe(blocs[i][j]));
								blocs[i][j].detruire();

							}
						}
					} else if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK
							|| e.getButton() == 3) {
						if (new Rectangle(blocs[i][j].x - (int) GameState.xOffset,
								blocs[i][j].y - (int) GameState.yOffset, Bloc.TAILLE, Bloc.TAILLE)
										.contains(e.getPoint())
								&& blocs[i][j].getId() == 0) {

							// si le bloc a une base on peut le poser
							if (blocs[i + 1][j].getId() != 0 || blocs[i - 1][j].getId() != 0
									|| blocs[i][j + 1].getId() != 0 || blocs[i][j - 1].getId() != 0) {
								if (new Point(blocs[i][j].x - (int) GameState.xOffset,
										blocs[i][j].y - (int) GameState.yOffset).distance(hitbox.getCenterX(),
												hitbox.getCenterY()) < 110) {

									Objet objetSel = this.player.getInventaire().getObjetSel();

									if (objetSel != null) {
										
									if (objetSel.getNb() > 0) {
										blocs[i][j] = new Bloc(blocs[i][j], objetSel.getId());
										objetSel.decrementeNb();
									}
									}

								}
							}

						}
					}

				}
			}
		}

	}

	public void mouseMoved(MouseEvent e) {
		for (int i = 0; i < blocs.length; i++) {
			for (int j = 0; j < blocs[0].length; j++) {
				if (new Rectangle(blocs[i][j].x - (int) GameState.xOffset, blocs[i][j].y - (int) GameState.yOffset,
						Bloc.TAILLE, Bloc.TAILLE).contains(e.getPoint()) && blocs[i][j].getId() != 0) {
					if (new Point(blocs[i][j].x - (int) GameState.xOffset, blocs[i][j].y - (int) GameState.yOffset)
							.distance(hitbox.getCenterX(), hitbox.getCenterY()) < 110) {

						blocSelectionne = blocs[i][j];
						blocSelectionne.selectionne();
					}

				} else {
					blocSelectionne = null;
					blocs[i][j].deselectionne();
				}

			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}


	public void tick() {
		
		hitbox.setBounds(new Rectangle(player.getPoint(), player.getDimension()));

		for (int i = 0; i < blocTombes.size(); i++) {

			for (int j = i; j < blocTombes.size(); j++) {

				if (blocTombes.get(i).getId() == blocTombes.get(j).getId()) {

					int distance = (int) new Point((int) blocTombes.get(i).getCenterX(),
							(int) blocTombes.get(i).getCenterY())
									.distance(new Point((int) blocTombes.get(j).getCenterX(),
											(int) blocTombes.get(j).getCenterY()));
					if (distance < 20) {
						int newX = (int) (blocTombes.get(j).getX());
						int newY = (int) (blocTombes.get(j).getY());
						blocTombes.get(i).setLocation(newX, newY);
					}
				}
			}
		}

	}

}
