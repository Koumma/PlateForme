package jeu;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Jeu {
	
	public static void main(String[] args) {
		JFrame f = new JFrame("territirom");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLayout(new BorderLayout());
		f.add(new GamePanel(),BorderLayout.CENTER);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
