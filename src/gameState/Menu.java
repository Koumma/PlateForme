package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import jeu.GamePanel;

public class Menu extends GameState {

	private String[] options = { "Jouer", "Aide", "Quitter" };

	private Integer selection = 0;

	public Menu(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {

	}

	@Override
	public void tick() {

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(40, 100, 200));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		for (int i = 0; i < options.length; i++) {
			if (i == selection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			g.setFont(new Font("Arial", Font.PLAIN, 60));
			g.drawString(options[i], GamePanel.WIDTH / 2 - 150, 100 + i * 100);
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_DOWN) {
			selection++;
			if (selection >= options.length) {
				selection = 0;
			}
		} else if (k == KeyEvent.VK_UP) {
			selection--;
			if (selection < 0) {
				selection = options.length - 1;
			}
		}

		if (k == KeyEvent.VK_ENTER) {
			switch (selection) {
			case 0:
				gsm.states.push(new Level1(gsm));
				break;
			case 1:

				break;
			case 2:
				System.exit(0);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void keyReleased(int k) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
