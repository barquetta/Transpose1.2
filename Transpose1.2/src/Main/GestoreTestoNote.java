package Main;

/**
 * @author Bruno Scrivo
 * 
 */
import java.util.*;
import java.io.*;

public class GestoreTestoNote implements Serializable {
	private static final long serialVersionUID = 3671034098310364545L;
	// Variabili di istanza
	String testo;
	int righe = 0;
	ArrayList<Testo> arrayTesto = new ArrayList<Testo>();
	ArrayList<RigaNota> notes = new ArrayList<RigaNota>();
	ArrayList<Boolean> ordine = new ArrayList<Boolean>();

	// Fine variabili di istanza
	private GestoreTestoNote() {
	}

	public GestoreTestoNote(String s) {
		scan(s);
	}

	/**
	 * Metodo di supporto al costruttore che consente la scansione di una
	 * stringa, al fine di smistare le note e il testo.
	 * 
	 * @param a
	 *            : stringa che corrisponde all'input preso da una TextArea (in
	 *            ambito grafico)
	 */
	public boolean hasNote() {
		return notes.size() != 0;
	}

	private void scan(String a) {
		this.testo = new String(a);
		ArrayList<String> ll = new ArrayList<String>();
		Scanner sc = new Scanner(a);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			if (s.length() == 0) {
				s = " ";
				ll.add(s);
			} else
				ll.add(s);
		}
		for (int i = 0; i < ll.size(); i++) {
			String cur = ll.get(i);
			if (cur.charAt(0) == '.') {
				cur = cur.trim();
				this.addNote(cur.substring(1, cur.length()));
				ordine.add(i, false);
			} else {
				this.addText(cur);
				ordine.add(i, true);
			}
		}

	}// scan

	public GestoreTestoNote transpose(int quanto) {
		GestoreTestoNote g = new GestoreTestoNote();
		ArrayList<RigaNota> nuova = new ArrayList<RigaNota>();
		g.ordine = this.ordine;
		g.testo = this.testo;
		g.arrayTesto = this.arrayTesto;
		g.righe = this.righe;
		for (RigaNota n : notes)
			nuova.add(n.transpose(quanto));
		g.notes = nuova;
		return g;
	}

	public GestoreTestoNote trasformaDiesis() {
		GestoreTestoNote g = new GestoreTestoNote();
		ArrayList<RigaNota> nuova = new ArrayList<RigaNota>();
		g.ordine = this.ordine;
		g.testo = this.testo;
		g.arrayTesto = this.arrayTesto;
		g.righe = this.righe;
		for (RigaNota n : notes)
			nuova.add(n.trasformaDiesis());
		g.notes = nuova;
		return g;
	}

	public GestoreTestoNote trasformaBemolli() {
		GestoreTestoNote g = new GestoreTestoNote();
		ArrayList<RigaNota> nuova = new ArrayList<RigaNota>();
		g.ordine = this.ordine;
		g.testo = this.testo;
		g.arrayTesto = this.arrayTesto;
		g.righe = this.righe;
		for (RigaNota n : notes)
			nuova.add(n.trasformaBemolli());
		g.notes = nuova;
		return g;
	}

	public void addText(String testo) {
		Testo t = new Testo(testo);
		arrayTesto.add(t);
	}// addText

	public void addNote(String note) {
		RigaNota n = new RigaNota(note);
		notes.add(n);
	}// addNote

	public String toString() {
		int c = 0, d = 0, e = 0;
		StringBuilder sb = new StringBuilder();
		while (e < ordine.size()) {
			boolean flag = ordine.get(e++);
			if (flag)
				sb.append(arrayTesto.get(c++) + "\n");
			else
				sb.append(notes.get(d++) + "\n");
		}
		return sb.toString();
	}// toString()
}
