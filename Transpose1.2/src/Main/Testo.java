package Main;
/**
 * @author Bruno Scrivo
 * 
 */
import java.io.Serializable;

public class Testo implements Serializable {

	private static final long serialVersionUID = -7518209330910870362L;
	String testo;

	public Testo(String testo) {
		this.testo = new String(testo);
	}

	public String toString() {
		return testo;
	}
}