package Main;

/**
 * @author Bruno Scrivo
 * 
 */
import java.io.*;
import java.util.*;

public class RigaNota implements Iterable<Nota>, Serializable {
	private static final long serialVersionUID = -2204314309322734481L;
	ArrayList<Nota> notes = new ArrayList<Nota>();

	public RigaNota() {
	}

	public RigaNota(String note) {
		int spazi = 0;
		StringBuilder nota = new StringBuilder();
		for (int i = 0; i < note.length(); i++) {
			if (note.charAt(i) == ' ') {
				if (i - 1 >= 0 && note.charAt(i - 1) != ' ') {
					Nota n = new Nota(nota.toString());
					n.aggiungiSpazio(spazi);
					notes.add(n);
					spazi = 0;
					nota = new StringBuilder();
				}// if interno
				spazi++;
			} else {
				nota.append(note.charAt(i));
			}
			if (i == note.length() - 1 && i - 1 >= 0
					&& note.charAt(i - 1) != ' ') {
				Nota n = new Nota(nota.toString());
				n.aggiungiSpazio(spazi);
				notes.add(n);
				spazi = 0;
				nota = new StringBuilder();
			}
		}
	}

	public RigaNota transpose(int quanto) {
		RigaNota n = new RigaNota();
		ArrayList<Nota> nuovo = new ArrayList<Nota>();
		for (Nota d : notes) {
			nuovo.add(d.transpose(quanto));
		}
		n.notes = nuovo;
		return n;
	}

	public RigaNota trasformaDiesis() {
		RigaNota n = new RigaNota();
		ArrayList<Nota> nuovo = new ArrayList<Nota>();
		for (Nota d : notes) {
			nuovo.add(d.trasformaDiesis());
		}
		n.notes = nuovo;
		return n;
	}

	public RigaNota trasformaBemolli() {
		RigaNota n = new RigaNota();
		ArrayList<Nota> nuovo = new ArrayList<Nota>();
		for (Nota d : notes) {
			nuovo.add(d.trasformaBemolli());
		}
		n.notes = nuovo;
		return n;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('.');
		for (Nota n : notes)
			sb.append(n.toStringSpazio());
		return sb.toString();
	}// toString

	@Override
	public Iterator<Nota> iterator() {
		return notes.iterator();
	}

}