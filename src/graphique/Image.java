package graphique;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	public static BufferedImage[] blocs;
	public static BufferedImage[] backgrounds;
	public static BufferedImage[] perso;
	
	public Image() {
		blocs = new BufferedImage[3];
		backgrounds = new BufferedImage[1];
		perso = new BufferedImage[2];
		try {
			blocs[0] = ImageIO.read(getClass().getResourceAsStream("/images/terre.png"));
			blocs[1] = ImageIO.read(getClass().getResourceAsStream("/images/roche.png"));
			blocs[2] = ImageIO.read(getClass().getResourceAsStream("/images/air.png"));
			backgrounds[0] = ImageIO.read(getClass().getResourceAsStream("/images/background_1.png"));
			perso[0] = ImageIO.read(getClass().getResourceAsStream("/images/perso_d.png"));
			perso[1] = ImageIO.read(getClass().getResourceAsStream("/images/perso_g.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
