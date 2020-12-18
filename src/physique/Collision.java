package physique;

import java.awt.Point;
import java.awt.Rectangle;

import gameState.GameState;
import objets.Bloc;

public class Collision {

	public static boolean collisionBas(int x, int y, int width, int height, Bloc b) {
		if (Collision.playerBlock(new Point(x + (int) GameState.xOffset + 2, y + height + (int) GameState.yOffset + 1),
				b)
				|| Collision.playerBlock(
						new Point(x + width + (int) GameState.xOffset - 2, y + height + (int) GameState.yOffset + 1),
						b)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean collisionHaut(int x, int y, int width, int height, Bloc b) {
		if (Collision.playerBlock(new Point(x + (int) GameState.xOffset + 1, y + (int) GameState.yOffset), b)
				|| Collision
						.playerBlock(new Point(x + width + (int) GameState.xOffset - 2, y + (int) GameState.yOffset), b)
				|| new Rectangle(x + (int) GameState.xOffset, y + (int) GameState.yOffset - 2, width, height)
						.intersects(b)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean collisionGauche(int x, int y, int width, int height, Bloc b) {
		if (Collision.playerBlock(new Point(x + (int) GameState.xOffset - 1, y + (int) GameState.yOffset + 2), b)
				|| Collision.playerBlock(
						new Point(x + (int) GameState.xOffset - 1, y + height + (int) GameState.yOffset - 1), b)
				|| new Rectangle(x + (int) GameState.xOffset - 3, y + (int) GameState.yOffset, width, height)
						.intersects(b)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean collisionDroite(int x, int y, int width, int height, Bloc b) {
		if (Collision.playerBlock(new Point(x + width + (int) GameState.xOffset, y + (int) GameState.yOffset + 2), b)
				|| Collision.playerBlock(
						new Point(x + width + (int) GameState.xOffset, y + height + (int) GameState.yOffset - 1), b)
				|| new Rectangle(x + (int) GameState.xOffset + 3, y + (int) GameState.yOffset, width, height)
						.intersects(b)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean playerBlock(Point p, Bloc b) {

		return b.contains(p);
	}

	public static boolean collisionMonter(int x, int y, int width, int height, Bloc b, boolean droite, boolean gauche) {
		if (droite && Collision.playerBlock(new Point(x + width + (int) GameState.xOffset + 3,
				y + height + (int) GameState.yOffset - 3), b)
				|| gauche && Collision.playerBlock(new Point(x + (int) GameState.xOffset - 2,
						y + height + (int) GameState.yOffset - 3), b)) {
			return true;
		}else {
			return false;			
		}
	}
		
		

}
