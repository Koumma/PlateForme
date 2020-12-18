package objets;

public class Objet {

	private int id;
	
	private int nb;
	
	public Objet(int id) {
		this.id = id;
		this.nb = 1;
	}
	
	public int getId() {
		return id;
	}
	
	public int getNb() {
		return nb;
	}
	
	public void incrementeNb() {
		this.nb++;
	}
	
	public void decrementeNb() {
		this.nb--;
	}
	
}
