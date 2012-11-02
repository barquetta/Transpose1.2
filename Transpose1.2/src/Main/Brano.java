package Main;

/**
 * @author Bruno Scrivo
 * 
 */

import java.awt.Font;
import java.io.*;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class Brano implements Serializable {
	private static final long serialVersionUID = -936365361359327864L;
	// Variabili di istanza
	String titolo;
	GestoreTestoNote t;
	Nota tonalita;

	// Costruttore
	public Brano(String titolo) {
		this.titolo = new String(titolo);
	}

	public void scan(String scan) {
		t = new GestoreTestoNote(scan);
	}

	public Brano(String titolo, Nota tonalita) {
		this.titolo = new String(titolo);
		this.tonalita = tonalita;
	}

	public boolean hasNote() {
		return t != null && t.hasNote();
	}

	public Brano(File f) throws IOException, ClassNotFoundException {
		try {
			String file = f.getAbsolutePath();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			Brano nuovo = (Brano) ois.readObject();
			this.titolo = nuovo.titolo;
			this.t = nuovo.t;
			this.tonalita = nuovo.tonalita;

		} catch (Exception E) {
			throw new IOException();
		}

	}

	public Brano trasposta(int quanto) {
		if (t != null && t.hasNote()) {
			Brano b;
			if (tonalita != null)
				b = new Brano(titolo, tonalita.transpose(quanto));
			else
				b = new Brano(titolo);
			GestoreTestoNote n = t.transpose(quanto);
			b.t = n;
			return b;
		} else {
			return this;
		}
	}

	public void salvaTesto(String file) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Titolo: " + titolo + "\n");
		sb.append("Tonalità: " + tonalita + "\n");
		sb.append("\n");
		sb.append("\n");
		sb.append(this.toString());
		JTextPane p = new JTextPane();
		Font f = new Font("font", 4, 18);
		;
		p.setFont(f);
		p.setText(sb.toString());

		StyledDocument doc = p.getStyledDocument();
		RTFEditorKit kit = new RTFEditorKit();
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			kit.write(outStream, doc, 0, doc.getLength());
			outStream.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Brano trasformaDiesis() {
		Brano nuovo = new Brano(titolo, tonalita);
		GestoreTestoNote g = t.trasformaDiesis();
		nuovo.t = g;
		return nuovo;
	}

	public Brano trasformaBemolli() {
		Brano nuovo = new Brano(titolo, tonalita);
		GestoreTestoNote g = t.trasformaBemolli();
		nuovo.t = g;
		return nuovo;
	}

	public boolean salvaFile(String file) throws IOException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(this);
			oos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String titolo() {
		return titolo;
	}

	public String tonalita() {
		return tonalita.toString();
	}

	public Nota tonalitaN() {
		return tonalita;
	}

	public String toString() {
		return t.toString();
	}

	/*
	 * METODI DELLA VERSIONE PRECEDENTE public void aggiungiNota(Nota nota)
	 * {n.add(nota);} metodi usati nella precedente versione public String
	 * toString (int notePerRiga){ if (notePerRiga<=0 || notePerRiga>30) throw
	 * new IllegalArgumentException(); StringBuilder sb = new StringBuilder();
	 * int cont = 0; Iterator<Nota> it = n.iterator(); while (it.hasNext()) { if
	 * (cont == notePerRiga) { sb.append('\n');sb.append('\n'); cont = 0; }
	 * sb.append(it.next()+ "		"); cont ++; } return sb.toString(); }
	 */// FINE METODI PRECEDENTI

}