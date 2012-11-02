package Esecuzione;

/**
 * @author Bruno Scrivo
 * MainClass di Transpose1.2
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import Main.*;

public class Start {
	public static void main(String[] args) {
		// Controlla la risoluzione del monitor per adattare la finestra.
		boolean res = verificaRisoluzione();
		if (res) {
			TransposeGUIMin t = new TransposeGUIMin();
			t.setSize(1200 * 69 / 100, 690 * 74 / 100);
			t.setVisible(true);
		} else {
			TransposeGUI t = new TransposeGUI();
			t.setSize(1200, 690);
			t.setVisible(true);
		}
	}

	private static boolean verificaRisoluzione() {

		Toolkit MyTool = Toolkit.getDefaultToolkit();
		Dimension MySchermo = MyTool.getScreenSize();
		int Altezza = (int) MySchermo.getHeight();
		int Larghezza = (int) MySchermo.getWidth();
		if (Larghezza <= 1366 * 80 / 100 || Altezza <= 768 * 80 / 100)
			return true;
		else
			return false;
	}
}