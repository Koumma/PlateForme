package objets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import graphique.Image;

public class Inventaire {

	private ArrayList<Objet> hotbar;

	private ArrayList<Objet> inventaire1;

	private int sel;

	public Inventaire() {
		this.sel = 1;
		this.hotbar = new ArrayList<Objet>();
		this.inventaire1 = new ArrayList<Objet>();
	}
	
	public void tick(){
		ArrayList<Objet> aEnlever = new ArrayList<Objet>();
		
		for (Objet objet : hotbar) {
			if (objet.getNb() == 0) {
				aEnlever.add(objet);
			}
		}
		
		hotbar.removeAll(aEnlever);
		
	}

	public void ajoute(Objet o) {
		this.inventaire1.add(o);
	}

	public void ajouteHotbar(Objet o) {
		
		ArrayList<Objet> aAjouter = new ArrayList<Objet>();

		if (hotbar.isEmpty()) {
			hotbar.add(o);
		} else {
			
			for (Objet objet : hotbar) {
				if (objet.getId() == o.getId()) {
					objet.incrementeNb();
				} else {
					
					boolean nouveau = true;
					
					for (int i = 0; i < hotbar.size(); i++) {
						if (objet.getId() != hotbar.get(i).getId()) {
							nouveau = false;
						}
					}
					
					if (nouveau) {
						aAjouter.add(o);					
					}
					
				}
			}
		}
		
		hotbar.addAll(aAjouter);
	}

	public void draw(Graphics g) {
		int i = 0;
		for (Objet objet : hotbar) {
			
			if (objet != null) {
				i++;
				g.setColor(Color.BLACK);
				g.drawImage(Image.blocs[objet.getId() - 1], 100 * (i + 1), 50, 15, 15, null);
				g.drawString("" + objet.getNb(), 100 * (i + 1) - 10, 40);
				g.setColor(new Color(100, 100, 50, 100));
				g.fillRect(100 * (this.sel) + 100, 35, 30, 30);
			}
		}
	}
	
	public Objet getObjetSel() {
		if (this.sel - 1 < hotbar.size() && this.sel - 1 >= 0) {
			return hotbar.get(sel - 1);	
		} else {
			return null;
		}
	}
	
	public void incremente() {
		if (this.sel < hotbar.size()) {
			this.sel++;			
		}
	}
	
	public void decremente() {
		if (this.sel > 1) {
			this.sel--;					
		}
	}

}
