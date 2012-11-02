package Main;

/**
 * @author Bruno Scrivo
 * 
 */
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nota implements Serializable {
	private static final long serialVersionUID = 9053199441587943394L;

	// Variabili di istanza utili
	boolean negativo = false;
	Pattern PATTERN = Pattern
			.compile(
					"(((RE|SOL|LA)[#B]?)|((DO|FA)[#]?)|((MI|SI)[B]?))[\\-\\+]?(M|MIN|MAJ|SUS|DIM|AUG)?[245679]*[11]?[13]?(M|MIN|MAJ|SUS|DIM|AUG)?[\\-\\+]?",
					Pattern.CASE_INSENSITIVE);
	int spazi;
	// Ricorda che tra mi e fa e tra si e do passa un semitono :-)
	String nota;
	String content = "";
	String[][] noteMusicali = {
			{ "DO", "DO#", "RE", "RE#", "MI", "FA", "FA#", "SOL", "SOL#", "LA",
					"LA#", "SI" },
			{ "", "REB", "", "MIB", "", "", "SOLB", "", "LAB", "", "SIB", "" } };

	public Nota(String nota) {
		Matcher m = PATTERN.matcher(nota);
		if (!m.matches())
			throw new IllegalArgumentException("'" + nota + "'"
					+ " non è una nota!");
		String cur = m.group(1).toUpperCase();
		if (nota.length() > cur.length()) {
			String cont = nota.substring(cur.length(), nota.length());
			this.content = cont;
		}
		this.nota = cur;
	}

	public void aggiungiSpazio(int spazio) {
		spazi = spazio;
	}

	public int spazi() {
		return spazi;
	}

	public boolean isNote(String note) {
		Matcher m = PATTERN.matcher(nota);
		return m.matches();
	}

	public String toStringSpazio() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < spazi; i++)
			sb.append(" ");
		sb.append(this);
		return sb.toString();
	}

	public Nota trasformaDiesis() {
		if (nota.indexOf("#") >= 0) {
			for (int i = 0; i < noteMusicali[0].length; i++) {
				if (nota.equals(noteMusicali[0][i])
						&& noteMusicali[1][i].length() != 0) {
					Nota n = new Nota(noteMusicali[1][i]);
					n.aggiungiSpazio(spazi);
					return n;
				}
			}
		}
		Nota n = new Nota(nota);
		n.aggiungiSpazio(spazi);
		return n;
	}

	public Nota trasformaBemolli() {
		if (nota.indexOf("B") >= 0) {
			for (int i = 0; i < noteMusicali[0].length; i++) {
				if (nota.equals(noteMusicali[1][i])
						&& noteMusicali[0][i].length() != 0) {
					Nota n = new Nota(noteMusicali[0][i]);
					n.aggiungiSpazio(spazi);
					return n;
				}
			}
		}
		Nota n = new Nota(nota);
		n.aggiungiSpazio(spazi);
		return n;
	}

	public Nota transpose(int quanto) {
		if (quanto < -12 || quanto > 12)
			throw new IllegalArgumentException();
		if (quanto < 0) {
			negativo = true;
			quanto = -quanto;
		}

		int pos = 0;
		// cerco la posizione della nota
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < noteMusicali[0].length; j++)
				if (nota.equals(noteMusicali[i][j])) {
					pos = j;
					break;
				}

		int provvisorio;
		if (negativo)
			provvisorio = pos - quanto;
		else
			provvisorio = pos + quanto;
		if (provvisorio >= 0 && provvisorio < noteMusicali[0].length) {
			Nota nuova = new Nota(noteMusicali[0][provvisorio] + content);
			nuova.aggiungiSpazio(spazi);
			return nuova;
		}
		if (provvisorio < 0) {
			Nota nuova = new Nota(noteMusicali[0][noteMusicali[0].length
					+ provvisorio]
					+ content);
			nuova.aggiungiSpazio(spazi);
			return nuova;
		} else {
			Nota nuova = new Nota(noteMusicali[0][provvisorio
					- noteMusicali[0].length]
					+ content);
			nuova.aggiungiSpazio(spazi);
			return nuova;
		}
	}

	public String toString() {
		return nota + content;
	}

	/*
	 * Costruttore precedente //nota = nota.compare; if (!nota.matches(PATTERN))
	 * throw new IllegalArgumentException(nota + " Non è una nota!"); String
	 * pattern = "^(DO|RE|MI|FA|SOL|LA|SI|DO)[#]?[B]?"; // da cambiare :-)
	 * Scanner sl = new Scanner (nota); sl.useDelimiter(pattern); if
	 * (sl.hasNext()){ this.nota = sl.findInLine(pattern); this.content =
	 * sl.next(); } else { this.nota = nota; }
	 */
}