package entites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import gameState.GameState;
import graphique.Image;
import jeu.GamePanel;
import objets.Bloc;
import objets.Inventaire;
import objets.Objet;
import physique.Collision;

public class Player {

	private boolean droite, gauche;

	private boolean actuelleD = true;

	private double x, y;
	private int width, height;

	private boolean collisionHaut = false;

	private double vitesse = 1.5;
	private double vitesseMax = 3.5;


	private double vitesseSaut = 5;
	private double vitesseSautActuelle = vitesseSaut;

	private double vitesseMaxChute = 5;
	private double vitesseChute = 0.1;

	private boolean enSaut = false;
	private boolean enChute = false;
	
	private boolean montePossible = true;

	boolean monte;
	
	private Inventaire inventaire;
	
	Rectangle lol;

	public Player(int width, int height) {
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		this.width = width;
		this.height = height;
		this.inventaire = new Inventaire();
	}

	public void tick(Bloc[][] b) {
		
		montePossible = true;
		monte = false;

		this.inventaire.tick();

		int xi = (int) x;
		int yi = (int) y;

		
		for (int i = 0; i < b.length; i++) {

			for (int j = 0; j < b[0].length; j++) {

				if (b[i][j].getId() != 0) {
					
					
					lol = new Rectangle(xi, yi - Bloc.TAILLE + 3, width, height + Bloc.TAILLE - 3); 
					
					
					if (Collision.collisionHaut(xi, yi - Bloc.TAILLE + 3, width, height + Bloc.TAILLE - 3, b[i][j])) {
						montePossible = false;
					}
					
					
					if (Collision.collisionMonter(xi - 1, yi, width, height, b[i][j], droite, gauche) && montePossible) {
						System.out.println("aller");
						monte = true;
					}
					
					
					// droite
					if (Collision.collisionDroite(xi, yi, width, height, b[i][j])) {
						droite = false;
					}

					// gauche
					if (Collision.collisionGauche(xi, yi, width, height, b[i][j])) {
						gauche = false;
					}
					
					// haut
					if (Collision.collisionHaut(xi, yi, width, height, b[i][j])) {
						enSaut = false;						
					}

					// bas
					if (Collision.collisionBas(xi, yi, width, height, b[i][j])) {
						y--;
						GameState.yOffset--;
						enChute = false;
						collisionHaut = true;
					} else if (new Rectangle(xi + (int) GameState.xOffset, yi + (int) GameState.yOffset + 3, width,
							height).intersects(b[i][j])) {
						enChute = false;
						collisionHaut = true;
					} else {
						if (!collisionHaut && !enSaut) {
							enChute = true;
						}
					}
				}
			}
		}
		

		collisionHaut = false;
		
		
		if (monte) {
			GameState.yOffset -= Bloc.TAILLE;			
		}
		
		if (droite) {
			GameState.xOffset += vitesse;
			
			if (vitesse < vitesseMax) {
				vitesse += 0.06;
			}
			
		} 

		if (gauche) {
			GameState.xOffset -= vitesse;
			
			if (vitesse < vitesseMax) {
				vitesse += 0.06;
			}
		} 
		if (!gauche && ! droite) {
			vitesse = 1.5;
		}

		if (enSaut) {
			GameState.yOffset -= vitesseSautActuelle;
			vitesseSautActuelle -= 0.1;
			if (vitesseSautActuelle < 0) {
				vitesseSautActuelle = vitesseSaut;
				enSaut = false;
				enChute = true;
			}
		}

		if (enChute) {
			GameState.yOffset += vitesseChute;

			if (vitesseChute < vitesseMaxChute) {
				vitesseChute += 0.1;
			}
		} else {
			vitesseChute = 0.1;
		}

	}

	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, width, height);
		if (droite) {
			g.drawImage(Image.perso[0], (int) x, (int) y, width, height, null);
			actuelleD = true;
		} else if (gauche) {
			g.drawImage(Image.perso[1], (int) x, (int) y, width, height, null);
			actuelleD = false;
		} else if (actuelleD) {
			g.drawImage(Image.perso[0], (int) x, (int) y, width, height, null);
		} else {
			g.drawImage(Image.perso[1], (int) x, (int) y, width, height, null);
		}
		
		g.drawRect(lol.x, lol.y, lol.width, lol.height);

		inventaire.draw(g);

	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_D)
			droite = true;
		if (k == KeyEvent.VK_Q)
			gauche = true;
		if (k == KeyEvent.VK_SPACE && !enSaut && !enChute)
			enSaut = true;
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_D)
			droite = false;
		if (k == KeyEvent.VK_Q)
			gauche = false;
	}

	public Point getCenterPoint() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}

	public void ajoute(Objet o) {
		this.inventaire.ajouteHotbar(o);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			this.inventaire.decremente();
		} else {
			this.inventaire.incremente();
		}
	}
	
	public Inventaire getInventaire() {
		return inventaire;
	}
	
	public Point getPoint() {
		return new Point((int)x, (int)y);
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}

}
