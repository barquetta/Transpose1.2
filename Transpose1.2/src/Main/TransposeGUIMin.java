package Main;

/**
 * @author Bruno Scrivo
 * 
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import Esecuzione.Start;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

@SuppressWarnings("all")
public class TransposeGUIMin extends JFrame {
	private static final long serialVersionUID = -1991043948089404814L;
	// private final Color c = Color.white;
	private JFrame frame = new JFrame("Errore!");
	Integer[] semitones = { -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1,
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private JComboBox slider = new JComboBox(semitones);
	private JMenuItem Nuovo, Apri, SalvaTesto, SalvaFile, Esci, Tastiera,
			AggiungiElemento, Vis, TransposeBrano, TransAcc, about,
			TrasformaDiesis, TrasformaBemolli;
	private Brano b;
	private File fileDiSalvataggio = null;
	private Iterator it;
	private JTextField title = new JTextField(20 * 70 / 100),
			tone = new JTextField(20 * 70 / 100);
	private JPanel jPanel_2, jPanel_4, insert;
	private JPanel jPanel_3, j6, j7, bottoni, fronte;
	public static JTextArea ta; // Text area
	private JScrollPane sbrText; // Scroll pane for text area
	private JFileChooser jfc;
	private String file;
	private boolean rimuovibile = false;
	private JPanel contentPane;
	private Integer capacita = 53;
	private String tipo = null;
	private JLabel barraTitle = new JLabel("Titolo: ");
	private JLabel barraTone = new JLabel("Tonalità: ");
	private JLabel testo;
	private JButton acquisisci, nuovo, inserisci, esci, salvaTesto, help1;
	private boolean acquisition = false;
	private JPopupMenu Pmenu;
	private JMenuItem taglia, copia, incolla;

	public TransposeGUIMin() {
		Pmenu = new JPopupMenu();
		taglia = new JMenuItem("Taglia");
		taglia.addMouseListener(new DXListener());
		Pmenu.add(taglia);
		copia = new JMenuItem("Copia");
		copia.addMouseListener(new DXListener());
		Pmenu.add(copia);
		incolla = new JMenuItem("Incolla");
		incolla.addMouseListener(new DXListener());
		Pmenu.add(incolla);
		Pmenu.addMouseListener(new DXListener());
		nuovo = new JButton("Nuovo brano");
		inserisci = new JButton("Riga note");
		Color c = new Color(238, 238, 238);
		nuovo.setBackground(c);
		inserisci.setBackground(c);

		// Aggiungo gli URL
		URL url[] = new URL[10];
		ImageIcon img[] = new ImageIcon[10];

		url[0] = Start.class.getResource("img/normal_post.gif");
		url[1] = Start.class.getResource("img/ico_link_pagina.gif");
		url[2] = Start.class.getResource("img/stampa.gif");
		url[3] = Start.class.getResource("img/exit.gif");
		url[4] = Start.class.getResource("img/help.gif");
		url[5] = Start.class.getResource("img/salva.gif");
		url[6] = Start.class.getResource("img/image1.jpg"); // favicon

		img[0] = new ImageIcon(url[0]);
		img[1] = new ImageIcon(url[1]);
		img[2] = new ImageIcon(url[2]);
		img[3] = new ImageIcon(url[3]);
		img[4] = new ImageIcon(url[4]);
		img[5] = new ImageIcon(url[5]);
		img[6] = new ImageIcon(url[6]);

		nuovo.setIcon(img[0]);
		inserisci.setIcon(img[1]);

		Image immagineIc = Toolkit.getDefaultToolkit().getImage(url[6]);
		this.setIconImage(immagineIc);

		acquisisci = new JButton("Acquisisci");
		acquisisci.setIcon(img[2]);

		esci = new JButton("Esci");
		esci.setIcon(img[3]);

		help1 = new JButton("Help");
		help1.setIcon(img[4]);

		salvaTesto = new JButton("Salva");
		salvaTesto.setIcon(img[5]);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (verificaUscita())
					System.exit(0);
			}
		});
		this.setVisible(true);

		AscoltatoreEventiAzione listener = new AscoltatoreEventiAzione();
		contentPane = (JPanel) this.getContentPane();
		jPanel_2 = new JPanel();
		jPanel_3 = new JPanel();
		jPanel_4 = new JPanel();
		bottoni = new JPanel();
		insert = new JPanel();
		j6 = new JPanel();
		fronte = new JPanel();
		j7 = new JPanel();
		ta = new NoTabTextArea("", 18 * 70 / 100, 43 * 70 / 100);
		ta.setVisible(true);
		Font f = new Font("font", 4, 20);
		ta.setFont(f);
		ta.append("Benvenuto");
		ta.setLineWrap(true);
		testo = new JLabel("Transposer 1.2 (light version)");
		sbrText = new JScrollPane(ta);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ta.addMouseListener(new DXListener());
		// Aggiusto l'auto-scroll della text Area. Lo faccio su JScrollPane
		sbrText.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					BoundedRangeModel brm = sbrText.getVerticalScrollBar()
							.getModel();
					boolean wasAtBottom = true;

					public void adjustmentValueChanged(AdjustmentEvent e) {
						if (!brm.getValueIsAdjusting()) {
							if (wasAtBottom)
								brm.setValue(brm.getMaximum());
						} else
							wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm
									.getMaximum());

					}
				});
		Font font = new Font("Arial", 2, 20);
		testo.setFont(font);
		fronte.add(testo);
		fronte.setBackground(new Color(225, 229, 238));
		fronte.setOpaque(true);
		contentPane.setLayout(null);
		bottoni.setBackground(new Color(225, 229, 238));
		contentPane.setBackground(new Color(225, 229, 238));
		jPanel_3.setBackground(new Color(225, 229, 238));
		jPanel_2.setBackground(new Color(225, 229, 238));
		jPanel_4.setBackground(new Color(225, 229, 238));
		j6.setBackground(new Color(225, 229, 238));
		j7.setBackground(new Color(225, 229, 238));
		ta.setBackground(new Color(249, 254, 216));
		tone.setBackground(new Color(245, 249, 244));
		title.setBackground(new Color(245, 249, 244));
		insert.setBackground(new Color(225, 229, 238));
		nuovo.setBackground(new Color(208, 252, 190));
		inserisci.setBackground(new Color(193, 250, 208));
		addComponent(contentPane, jPanel_2, 15 * 70 / 100, 30 * 70 / 100,
				700 * 70 / 100, 70 * 70 / 100);
		addComponent(contentPane, jPanel_3, 15 * 70 / 100, 125 * 70 / 100,
				900 * 70 / 100, 505 * 70 / 100);
		addComponent(contentPane, jPanel_4, 950 * 70 / 100, 320 * 70 / 100,
				150 * 70 / 100, 90 * 70 / 100);
		addComponent(contentPane, j6, 15 * 70 / 100, 50 * 70 / 100,
				618 * 70 / 100, 90 * 70 / 100);
		addComponent(contentPane, j7, 15 * 70 / 100, 70 * 70 / 100,
				618 * 70 / 100, 90 * 70 / 100);
		addComponent(contentPane, insert, 930 * 70 / 100, 200 * 70 / 100,
				250 * 70 / 100, 108 * 70 / 100);
		addComponent(contentPane, bottoni, 780 * 70 / 100, 20 * 70 / 100,
				400 * 70 / 100, 100 * 70 / 100);
		// addComponent(contentPane, fronte,15*70/100, 10*70/100, 700*70/100,
		// 60*70/100);
		// contentPane.setBackground(c)
		bottoni.add(salvaTesto);
		bottoni.add(esci);
		bottoni.add(help1);
		// Titoli e bordi
		jPanel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottoni.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		TitledBorder t = new TitledBorder(" Song ");
		t.setTitleColor(new Color(1, 10, 253));
		jPanel_2.setBorder(t);
		TitledBorder t1 = new TitledBorder(" Text/Chords ");
		t1.setTitleColor(new Color(1, 10, 253));
		jPanel_3.setBorder(t1);
		jPanel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		TitledBorder t2 = new TitledBorder(" Transpose ");
		t2.setTitleColor(new Color(1, 10, 253));
		jPanel_4.setBorder(t2);
		insert.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		insert.add(nuovo);
		insert.add(inserisci);
		j6.add(barraTone);
		j6.add(tone);
		j7.add(barraTitle);
		j7.add(title);
		jPanel_2.add(j7);
		jPanel_2.add(j6);

		jPanel_3.add(sbrText);
		jPanel_4.add(slider);
		jPanel_3.add(acquisisci);
		acquisisci.setSize(21 * 70 / 100, 116 * 70 / 100);
		barraTitle.setLocation(11 * 70 / 100, 7 * 70 / 100);
		barraTone.setLocation(25 * 70 / 100, 63 * 70 / 100);

		// ---- setto il titolo, posizione e dimensione della finestra
		// principale
		this.setTitle("Transposer 1.2 (light version)");
		this.setLocation(new Point(0, 0));
		this.setSize(new Dimension(850 * 70 / 100, 500 * 70 / 100));
		// creazione barra dei menu'
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setVisible(true);
		// creazione file menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setVisible(true);
		menuBar.add(fileMenu);
		JMenu OperazioniMenu = new JMenu("Accordi");
		menuBar.add(OperazioniMenu);
		JMenu OperazioniColl = new JMenu("Transpose");
		menuBar.add(OperazioniColl);
		JMenu help = new JMenu("Help");
		menuBar.add(help);

		// creazione sotto menu
		Tastiera = new JMenuItem("Tastiera");
		help.add(Tastiera);
		Tastiera.addActionListener(listener);
		Nuovo = new JMenuItem("Nuovo brano");
		fileMenu.add(Nuovo);
		Nuovo.addActionListener(listener);
		Apri = new JMenuItem("Apri");
		fileMenu.add(Apri);
		Apri.addActionListener(listener);

		esci.addActionListener(listener);
		help1.addActionListener(listener);
		salvaTesto.addActionListener(listener);
		fileMenu.addSeparator();

		SalvaTesto = new JMenuItem("Salva come testo");
		fileMenu.add(SalvaTesto);
		SalvaTesto.addActionListener(listener);

		SalvaFile = new JMenuItem("Salva come file");
		fileMenu.add(SalvaFile);
		SalvaFile.addActionListener(listener);

		fileMenu.addSeparator();

		Esci = new JMenuItem("Esci");
		fileMenu.add(Esci);
		Esci.addActionListener(listener);

		AggiungiElemento = new JMenuItem("Acquisisci brano");
		OperazioniMenu.add(AggiungiElemento);
		AggiungiElemento.addActionListener(listener);

		acquisisci.addActionListener(listener);
		inserisci.addActionListener(listener);
		nuovo.addActionListener(listener);
		Vis = new JMenuItem("Visualizza accordi");
		OperazioniMenu.add(Vis);
		Vis.addActionListener(listener);
		ta.addMouseListener(new DXListener());

		OperazioniMenu.addSeparator();

		TrasformaDiesis = new JMenuItem("Trasforma # in B");
		OperazioniMenu.add(TrasformaDiesis);
		TrasformaDiesis.addActionListener(listener);

		TrasformaBemolli = new JMenuItem("Trasforma B in #");
		OperazioniMenu.add(TrasformaBemolli);
		TrasformaBemolli.addActionListener(listener);

		TransposeBrano = new JMenuItem("Transpose brano");
		OperazioniColl.add(TransposeBrano);
		TransposeBrano.addActionListener(listener);

		TransAcc = new JMenuItem("Transpose accordo");
		OperazioniColl.add(TransAcc);
		TransAcc.addActionListener(listener);

		about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(listener);
		slider.setSelectedItem(0);
		slider.setPrototypeDisplayValue(-123);
		slider.addActionListener(listener);
		menuIniziale();

	}// Costruttore

	private void deleteHtml() {
		try {
			File f = new File("guida.html");
			f.delete();
		} catch (Exception e) {
		}
	}

	private void creaHtml() {
		String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01//EN' 'http://www.w3.org/TR/html4/strict.dtd'>"
				+ "<html><head>"
				+ "  <title>guida</title>"
				+ "</head><body>"
				+ "<span style='font-weight: bold; color: rgb(255, 102, 102);'>Guida all'uso di Transposer</span><br>"
				+ "<br>"
				+ "<span style='text-decoration: underline;'>Creazione di un nuovo brano</span><br>"
				+ "<ul>"
				+ "  <li>&nbsp;&nbsp; Cliccare sul menu' File - Nuovo Brano (in alternativa sul tasto Nuovo Brano;</li>"
				+ "  <li>&nbsp;&nbsp; Inserire Titolo e Tonalita' e cliccare su Ok;<br>"
				+ "  </li>"
				+ "  <li>&nbsp;&nbsp; Inserire il testo e le note nel campo Text/Chords; <br>"
				+ "  </li>"
				+ "  <li>&nbsp;&nbsp; Cliccare sul tasto Acquisisci per poter compiere operazioni sulle note.</li>"
				+ "</ul>"
				+ "<span style='text-decoration: underline;'>Trasposizione di un brano</span><br>"
				+ "<ul>"
				+ "  <li>&nbsp;&nbsp; Acquisire le note tramite il tasto Acquisisci;</li>"
				+ "  <li>&nbsp;&nbsp; Spostarsi sullo slider Transpose (a destra del software) e scegliere di quanto trasportare il brano;</li>"
				+ "  <li>&nbsp;&nbsp; Per info sulla trasposizione consultare la tastiera dal menu' Help - Tastiera.</li>"
				+ "</ul>"
				+ "<span style='text-decoration: underline; color: black;'>Salvataggio di un brano</span><br>"
				+ "<br>"
				+ "Transposer prevede due tipi di salvataggi: uno <span style='font-weight: bold;'>testuale</span>"
				+ "- Salva come testo (utile per stampare il brano appena scritto e/o"
				+ "modificare la formattazione di quest'ultimo) in un formato eseguibile"
				+ "da qualunque PC (.rtf) anche se non vi sono installati pacchetti&nbsp;"
				+ "di editor testo quali Microsoft Word.<br>"
				+ "N.B. questo formato <span style='font-weight: bold;'>non consente</span> di apportare successive modifiche al brano nel caso in cui dovesse essere riutilizzato dal programma Transposer.<br>"
				+ "<br>"
				+ "Il secondo salvataggio previsto e' un formato (.chd) <span style='font-weight: bold;'>supportato esclusivamente da Transposer </span>- Salva come&nbsp; file. <br>"
				+ "Consente di effettuare tutte le operazioni previste dal programma a patto che il file non venga manomesso.<br>"
				+ "N.B. questo formato <span style='font-weight: bold;'>consente</span> di apportare successive modifiche al brano nel caso in cui dovesse essere riutilizzato dal programma Transposer.<br>"
				+ "<br>"
				+ "Si consiglia di effettuare due salvataggi (Salva come File e Salva come"
				+ "Testo) ogni qualvolta che viene creato un nuovo Brano in modo da poter"
				+ "rendere possibili successive modifiche oltre alla stampa.<br>"
				+ "<br>"
				+ "<ul>"
				+ "  <li>&nbsp; Salvare come testo: Menu' <span style='font-weight: bold;'>File</span> - <span style='font-weight: bold;'>Salva come testo</span> - Scegliere il percorso di salvataggio e cliccare su <span style='font-weight: bold;'>Salva</span>.</li>"
				+ "  <li>&nbsp; Salvare come testo: Menu' <span style='font-weight: bold;'>File</span> - <span style='font-weight: bold;'>Salva come file</span> - Scegliere il percorso di salvataggio e cliccare su <span style='font-weight: bold;'>Salva.</span></li>"
				+ "</ul>"
				+ "<span style='text-decoration: underline;'>Stampa di un brano</span><br>"
				+ "<ul>"
				+ "  <li>&nbsp;&nbsp; Salvare il brano come testo (procedura sopra descritta);</li>"
				+ "  <li>&nbsp;&nbsp; Aprire il file appena creato e stamparlo.</li>"
				+ "</ul>"
				+ "<div><span style='text-decoration: underline;'>Apertura di un brano gia' salvato</span><br>"
				+ "</div>"
				+ "<br>"
				+ "E' possibile modificare un brano salvato in precedenza (Salvataggio come file sopra descritto):<br>"
				+ "<ul>"
				+ "  <li>&nbsp;&nbsp; Menu' File - Apri - Scegliere il percorso in cui si trova il file (formato supportato .chd)</li>"
				+ "  <li>&nbsp;&nbsp; Apportare le dovute modifiche e salvare come file.<br>"
				+ "  </li>"
				+ "</ul>"
				+ "<br>"
				+ "<br>"
				+ "<span style='text-decoration: underline;'></span>Note per l'uso:<br>"
				+ "<ol>"
				+ "  <li>Transposer non supporta l'uso del tasto TAB. Utilizzare la barra spaziatrice per distanziare il testo (o note).</li>"
				+ "  <li>Transposer e' programmato per differenziare gli accordi dal testo."
				+ "Per inserire una riga di note aggiungere preventivamente il carattere"
				+ "'.' (punto) prima di qualsiasi altra cosa in modo che assuma la"
				+ "seguente forma:&nbsp; <br>"
				+ "'.Mi&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Fa&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Sol&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "La' (ovviamente senza l'uso delle apici);<br>"
				+ "Cio' non vale per il testo che puo' essere inserito senza alcun carattere che lo preceda:<br>"
				+ "'Testo da inserire'.</li>"
				+ "  <li>E' possibile copiare il brano da un'altra fonte e incollarlo"
				+ "all'interno di Transposer a patto che venga rispettata la forma sopra"
				+ "descritta dai punti 1. e 2.</li>"
				+ "  <li>Gli accordi supportati rispecchiano la notazione standard. Inoltre Transposer e' <span class='st'><em>case insensitivity</em></span>,"
				+ "cio' significa che non desta particolare attenzione ai caratteri"
				+ "maiuscoli/minuscoli. L'accordo puo' essere scritto in entrambi i modi:"
				+ "se rispetta la notazione standard verra' riconosciuto.<br>"
				+ "  </li>"
				+ "  <li><span style='font-weight: bold;'>Al termine della scrittura di"
				+ "testo/accordi, acquisire tali elementi mediante il tasto 'Acquisisci'"
				+ "altrimenti nessuna operazione verra' compiuta.</span></li>"
				+ "</ol>"
				+ "Osservazione: sono presenti due versioni di Transposer. Quella standard"
				+ "e quella light. Quest'ultima verra' eseguita nel caso in cui la"
				+ "risoluzione fosse inferiore all'80% della risoluzione seguente: 1366 x"
				+ "768 pixel.<br>" + "" + "</body>" + "</html>";
		try {
			FileOutputStream file = new FileOutputStream("guida.html");
			PrintStream Output = new PrintStream(file);
			Output.print(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addComponent(Container container, Component c, int x, int y,
			int width, int height) {
		c.setBounds(x, y, width, height);
		container.add(c);
	}// addComponent

	private class AscoltatoreEventiAzione implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == about) {
				JOptionPane
						.showMessageDialog(
								null,
								"Transposer 1.2 (light version). Developer Bruno Scrivo \n",
								"About", JOptionPane.PLAIN_MESSAGE);
			}
			if (e.getSource() == Nuovo) {
				FrameCap FrameCap = new FrameCap();
				FrameCap.setVisible(true);
				acquisition = false;
			}
			if (e.getSource() == AggiungiElemento) {
				try {
					String chord = ta.getText().replaceAll("\t", " ");
					b.scan(chord);
					ta.setText(b.toString());
					JOptionPane.showMessageDialog(frame,
							"Accordi acquisiti con successo!");
					acquisition = true;
				} catch (Exception v) {
					JOptionPane.showMessageDialog(frame,
							"Errore: " + v.getMessage());
				}
			}
			if (e.getSource() == TransposeBrano) {
				TransB tran = new TransB();
				tran.setVisible(true);
			}
			if (e.getSource() == slider) {
				if (b != null && b.hasNote()) {
					Integer how = (Integer) slider.getSelectedItem();
					b = b.trasposta(how);
					tone.setText(b.tonalita());
					ta.setText(b.toString());
					slider.setSelectedItem(0);
				} else {
					slider.setSelectedItem(0);
					JOptionPane
							.showMessageDialog(frame,
									"Nessunta nota da trasporre! Assicurarsi di aver acquisito il brano");
				}

			}
			if (e.getSource() == TransAcc) {
				TransAcc tran1 = new TransAcc();
				tran1.setVisible(true);
			}
			if (e.getSource() == inserisci) {
				ta.append("\n" + ".");
			}
			if (e.getSource() == nuovo) {
				FrameCap FrameCap = new FrameCap();
				FrameCap.setVisible(true);
				acquisition = false;
			}
			if (e.getSource() == acquisisci) {
				try {
					String chord = ta.getText().replaceAll("\t", " ");
					b.scan(chord);
					ta.setText(b.toString());
					JOptionPane.showMessageDialog(frame,
							"Accordi acquisiti con successo!");
					acquisition = true;
				} catch (Exception v) {
					JOptionPane.showMessageDialog(frame,
							"Errore: " + v.getMessage());
				}
			}
			if (e.getSource() == Vis) {
				ta.setText(b.toString());
			}
			if (e.getSource() == Tastiera) {
				Key tast = new Key();
				tast.setVisible(true);
			}
			if (e.getSource() == SalvaTesto) {
				if (acquisition) {
					JFileChooser chooser = new JFileChooser();
					try {
						chooser.setSelectedFile(new File(b.titolo()));
						if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
							fileDiSalvataggio = chooser.getSelectedFile();

							if (fileDiSalvataggio != null) {
								String file = fileDiSalvataggio
										.getAbsolutePath();
								String sub = ".rtf";
								if (file.indexOf(sub) < 0)
									b.salvaTesto(fileDiSalvataggio
											.getAbsolutePath() + ".rtf");
								else
									b.salvaTesto(fileDiSalvataggio
											.getAbsolutePath());
								JOptionPane.showMessageDialog(frame,
										"File Salvato con successo!");

							}
						} else
							JOptionPane.showMessageDialog(frame,
									"Salvataggio fallito!");
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(frame,
								"Salvataggio fallito!");
					}
				} else {
					JOptionPane
							.showMessageDialog(frame,
									"Prima di salvare effettuare modifiche nel campo di testo");
				}
			}
			if (e.getSource() == SalvaFile) {
				if (acquisition) {
					JFileChooser chooser = new JFileChooser();
					try {
						chooser.setSelectedFile(new File(b.titolo()));
						if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
							fileDiSalvataggio = chooser.getSelectedFile();

							if (fileDiSalvataggio != null) {
								String file = fileDiSalvataggio
										.getAbsolutePath();
								String sub = ".chd";
								if (file.indexOf(sub) < 0) {
									b.salvaFile(fileDiSalvataggio
											.getAbsolutePath() + ".chd");
								} else
									b.salvaFile(fileDiSalvataggio
											.getAbsolutePath());
								JOptionPane.showMessageDialog(frame,
										"File Salvato con successo!");

							}
						} else
							JOptionPane.showMessageDialog(frame,
									"Salvataggio fallito!");
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(frame,
								"Salvataggio fallito!");
					}
				} else {
					JOptionPane
							.showMessageDialog(frame,
									"Prima di salvare effettuare modifiche nel campo di testo");
				}
			}
			if (e.getSource() == esci)
				if (verificaUscita())
					System.exit(-1);
			if (e.getSource() == salvaTesto) {
				if (acquisition) {
					JFileChooser chooser = new JFileChooser();
					try {
						chooser.setSelectedFile(new File(b.titolo()));
						if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
							fileDiSalvataggio = chooser.getSelectedFile();

							if (fileDiSalvataggio != null) {
								String file = fileDiSalvataggio
										.getAbsolutePath();
								String sub = ".chd";
								if (file.indexOf(sub) < 0) {
									b.salvaFile(fileDiSalvataggio
											.getAbsolutePath() + ".chd");
								} else
									b.salvaFile(fileDiSalvataggio
											.getAbsolutePath());
								JOptionPane.showMessageDialog(frame,
										"File Salvato con successo!");

							}
						} else
							JOptionPane.showMessageDialog(frame,
									"Salvataggio fallito!");
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(frame,
								"Salvataggio fallito!");
					}
				} else {
					JOptionPane
							.showMessageDialog(frame,
									"Prima di salvare effettuare modifiche nel campo di testo");
				}
			}
			if (e.getSource() == help1) {
				creaHtml();
				String str;
				if (System.getProperty("os.name").equals("Linux"))
					str = "file://" + System.getProperty("user.dir")
							+ "/guida.html";
				else
					str = System.getProperty("user.dir").replace('\\', '/')
							.replaceAll(" ", "%20")
							+ "/guida.html";
				try {
					Desktop.getDesktop().browse(new URI(str));
				} catch (Exception aa) {
					aa.printStackTrace();

				}
			}
			if (e.getSource() == Apri) {
				JFileChooser chooser = new JFileChooser();
				try {
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						if (!chooser.getSelectedFile().exists()) {
							JOptionPane.showMessageDialog(null,
									"File inesistente!");
						} else {
							fileDiSalvataggio = chooser.getSelectedFile();
							try {
								String salvaN = fileDiSalvataggio
										.getAbsolutePath();
								File f = new File(salvaN);
								b = new Brano(f);
								ta.setText(b.toString());
								title.setText(b.titolo());
								tone.setText(b.tonalita());
								menuAvviato();
							} catch (Exception ioe) {

								JOptionPane
										.showMessageDialog(null,
												"Fallimento apertura. File malformato!");
							}
						}
					} else
						JOptionPane
								.showMessageDialog(null, "Nessuna apertura!");
				} catch (Exception exc) {

				}
			}
			if (e.getSource() == Esci)
				if (verificaUscita())
					System.exit(-1);
			if (e.getSource() == TrasformaDiesis) {
				b = b.trasformaDiesis();
				ta.setText(b.toString());
			}
			if (e.getSource() == TrasformaBemolli) {
				b = b.trasformaBemolli();
				ta.setText(b.toString());
			}

		}
	}

	private boolean verificaUscita() {
		int option = JOptionPane.showConfirmDialog(null, "Continuare ?",
				"Tutti i dati non salvati andranno persi!",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			deleteHtml();
		}
		return option == JOptionPane.YES_OPTION;
	}// verificaUscita

	private class FrameCap extends JFrame implements ActionListener {
		private JTextField tit = new JTextField("", 12 * 70 / 100);
		private JTextField ton = new JTextField("", 12 * 70 / 100);

		private JButton ok;

		public FrameCap() {
			setTitle("Nuovo brano");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Titolo ", JLabel.RIGHT));
			p.add(tit);
			p.add(new JLabel("Tonalità ", JLabel.CENTER));
			p.add(ton);
			p.add(ok = new JButton("OK"));
			add(p);
			tit.addActionListener(this);
			ton.addActionListener(this);
			ok.addActionListener(this);
			setLocation(110, 210);
			setSize(450 * 70 / 100, 90 * 70 / 100);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok) {
				String titolo = (tit.getText()).toUpperCase();
				if (titolo.length() != 0) {
					try {
						Nota tono = new Nota(ton.getText());
						b = new Brano(titolo, tono);
						menuAvviato();
						title.setText(titolo);
						tone.setText(tono.toString());
						ta.setText("");
						this.dispose();
					} catch (Exception s) {
						JOptionPane.showMessageDialog(frame,
								"Accordo non riconosciuto!");
					}
				}// if
				else
					JOptionPane.showMessageDialog(frame, "Titolo non valido!");
			}
			if (e.getSource() == ton) {
				String titolo = (tit.getText()).toUpperCase();
				if (titolo.length() != 0) {
					try {
						Nota tono = new Nota(ton.getText());
						b = new Brano(titolo, tono);
						menuAvviato();
						title.setText(titolo);
						tone.setText(tono.toString());
						ta.setText("");
						this.dispose();
					} catch (Exception s) {
						JOptionPane.showMessageDialog(frame,
								"Accordo non riconosciuto!");
					}
				}// if
				else
					JOptionPane.showMessageDialog(frame, "Titolo non valido!");
			}

		}// actionPerformed
	}// FrameCap

	private class NoTabTextArea extends JTextArea {
		public NoTabTextArea(String s, int a, int b) {
			super(s, a, b);
		}

		protected void processComponentKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				transferFocus();
				e.consume();
			} else {
				super.processComponentKeyEvent(e);
			}
		}
	}

	private class Key extends JFrame implements ActionListener {

		public Key() {
			setTitle("Tastiera");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			add(p);
			URL url = Start.class.getResource("img/image.jpg");
			ImageIcon immagine = new ImageIcon(url);
			JLabel sfondo = new JLabel(immagine);
			sfondo.setVisible(true);
			p.add(sfondo);
			setLocation(110, 210);
			setSize(660, 280);
		}

		public void actionPerformed(ActionEvent e) {
		}// actionPerformed
	}// FrameCap

	private class DXListener extends MouseAdapter {

		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (ta.getText().length() == 0) {
					copia.setEnabled(false);
					taglia.setEnabled(false);
				} else {
					copia.setEnabled(true);
					taglia.setEnabled(true);
				}
				Pmenu.show(e.getComponent(), e.getX(), e.getY());
			}
			if (e.getSource() == copia)
				ta.copy();
			if (e.getSource() == taglia)
				ta.cut();
			if (e.getSource() == incolla)
				ta.paste();
		}

	}

	private class TransB extends JFrame implements ActionListener {
		private JTextField quanto = new JTextField("", 12);
		private JButton ok;

		public TransB() {
			setTitle("Trasposizione Brano");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Semitoni ", JLabel.RIGHT));
			p.add(quanto);
			p.add(ok = new JButton("OK"));
			add(p);
			quanto.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250, 340);
			setSize(180, 90);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok) {
				try {
					Integer how = Integer.parseInt(quanto.getText());
					b = b.trasposta(how);
					tone.setText(b.tonalita());
					ta.setText(b.toString());
					this.dispose();
				} catch (Exception s) {
					JOptionPane.showMessageDialog(frame, "Numero non valido!");
				}
			}
			if (e.getSource() == quanto) {
				try {
					Integer how = Integer.parseInt(quanto.getText());
					b = b.trasposta(how);
					tone.setText(b.tonalita());
					ta.setText(b.toString());
					this.dispose();
				} catch (Exception s) {
					JOptionPane.showMessageDialog(frame, "Numero non valido!");
				}
			}

		}// actionPerformed
	}// FrameTrasposizione

	private class TransAcc extends JFrame implements ActionListener {
		private JTextField chord = new JTextField("", 10);
		private JButton ok;
		private JComboBox slider1 = new JComboBox(semitones);

		public TransAcc() {
			slider1.setSelectedItem(0);
			setTitle("Trasposizione veloce");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Accordo", JLabel.RIGHT));
			p.add(chord);
			p.add(slider1);
			p.add(ok = new JButton("Fine"));
			add(p);
			chord.addActionListener(this);
			slider1.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250, 340);
			setSize(400, 90);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok)
				this.dispose();

			if (e.getSource() == slider1) {
				try {
					Nota n = new Nota(chord.getText());
					Integer q = (Integer) slider1.getSelectedItem();
					chord.setText(n.transpose(q).toString());
					slider1.setSelectedItem(0);
				} catch (Exception d) {
					slider1.setSelectedItem(0);
					JOptionPane.showMessageDialog(frame, "Accordo non valido!");
				}
			}

		}// actionPerformed
	}// FrameTrasposizioneVeloce

	public void menuIniziale() {
		Nuovo.setEnabled(true);
		Apri.setEnabled(true);
		Esci.setEnabled(true);
		SalvaTesto.setEnabled(false);
		SalvaFile.setEnabled(false);
		AggiungiElemento.setEnabled(false);
		Vis.setEnabled(false);
		TransposeBrano.setEnabled(false);
		TransAcc.setEnabled(true);
		slider.setEnabled(false);
		title.setEnabled(false);
		tone.setEnabled(false);
		ta.setEnabled(false);
		TrasformaDiesis.setEnabled(false);
		TrasformaBemolli.setEnabled(false);
		jPanel_4.setEnabled(false);
		inserisci.setEnabled(false);
		acquisisci.setEnabled(false);
		salvaTesto.setEnabled(false);
	}

	public void menuAvviato() {

		Nuovo.setEnabled(true);
		Apri.setEnabled(true);
		Esci.setEnabled(true);
		SalvaTesto.setEnabled(true);
		SalvaFile.setEnabled(true);
		AggiungiElemento.setEnabled(true);
		Vis.setEnabled(true);
		TransposeBrano.setEnabled(true);
		TransAcc.setEnabled(true);
		slider.setEnabled(true);
		title.setEnabled(true);
		tone.setEnabled(true);
		ta.setEnabled(true);
		TrasformaDiesis.setEnabled(true);
		TrasformaBemolli.setEnabled(true);
		jPanel_4.setEnabled(true);
		inserisci.setEnabled(true);
		acquisisci.setEnabled(true);
		salvaTesto.setEnabled(true);
	}
}
