package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Spieler;
import model.SpielerListe;
import networking.ChatClient;
import networking.Client;
import view.ButtonImageThread;
import view.ButtonImages;
import view.LoginDialog;
import view.MainWindow;
import benutzermanagement.Benutzerliste;
import chat.ChatAusruf;

/**
 * Controller für das {@link MainWindow}. Macht so ziemlich alles.
 *
 * @author Roman Schmidt
 *
 */
public class MainWindowController {

	// --- Gameplay ---

	/** Gibt an, ob die Anzeige für den aktuellen Spieler oder den Gegner als nächstes aktuelisiert wird. */
	private boolean spielerKommtAlsNaechstes;

	/** Der Spieler, der durch diese GUI gesteuert wird */
	private Spieler spieler;

	/** Der aktuelle Gegner */
	private Spieler gegner;

	/** Der Client, der für das Networking während eines Matches zuständig ist */
	private Client client;

	/** Die aktuellen Exp des Spielers: Key ist der Pfad, Value die Exp für diesen Pfad */
	public static Map<String, Integer> currentExp;

	// --- Chat ---

	/** Client, der für das Senden und Empfangen von Chatnachrichten zuständig ist */
	private ChatClient chatClient;

	/** Der Name des Spielers im Chat (= Login Nickname) */
	private String chatName;

	/** Liste von im Chat angemeldeten Spielern */
	private List<String> aktiveSpieler;

	/** Liste von aktuell laufenden Match-Servern */
	private Map<String, String> aktiveServer;

	// --- GUI ---

	/** Die View halt */
	private MainWindow view;

	/** Index der Handkarte, die der Spieler in die Mitte gelegt hat, um sie auszuspielen */
	private int indexInDerMitte;

	private JButton playButton;

	private JTextField chatInputField;

	private JTextArea chatArea;

	/** Gibt an, ob die Animationen im {@link DeckChooseController} laufen sollen */
	public static boolean auswahlAnimationenLaufen = false;

	/** Gibt an, ob Spieler (index 0) oder Gegner (index 1) SSJ sind. */
	public static boolean[] ssj = { false, false };

	/** Die IP-Adresse des ChatServers */
	private String ip;

	private ButtonImageThread playerImageThread;

	private ButtonImageThread gegnerImageThread;

	/**
	 * Konstruktor. Initialisert alles!
	 *
	 * @param ip
	 *            IP-Adresse des ChatServers
	 * @param port
	 *            Port des ChatServers
	 * @param chatName
	 *            Name, der im Chat angzeigt wird. Sollte eindeutig sein.
	 */
	public MainWindowController(final String ip, final int port) {
		this.ip = ip;

		// Hauptfenster aufbauen und sichtbar machen
		view = new MainWindow();

		// Mit Chatserver verbinden, einloggen und ChatListener anmelden
		chatClient = new ChatClient(ip, port);
		login();
		addChatListeners();
		sendJoin();
		requestServerList();

		// WindowListener für das Schließen des Hauptfensters anmelden
		addWindowListener();

		// Hotkeys für die Tabbed Pane setzen, TabbedPaneListener für die Farbe
		// des Chat-Tabs
		view.getTabbedPane().setMnemonicAt(0, KeyEvent.VK_G);
		view.getTabbedPane().setMnemonicAt(1, KeyEvent.VK_C);
		view.getTabbedPane().setMnemonicAt(2, KeyEvent.VK_M);
		view.getTabbedPane().setMnemonicAt(3, KeyEvent.VK_F);
		addTabbedPaneListener();

		// Listener für die Buttons im MatchMaking-Tab anmelden
		addMatchMakingListeners();

		// Listener für das Ausspielen von Karten
		addCardButtonListenersAndMnemonics();
		addPlayButtonListenerAndMnemonic();

		// Zusätzliche GUI-Initialisierungen
		resetGUI();
	}

	/**
	 * Öffnet den Login-Dialog und loggt den Benutzer auf dem Chatserver ein.
	 */
	private void login() {
		// TODO Login: leere Eingabe verhindern
		// TODO Login: Login durch Dialogschließen verhindern
		// TODO Login: prüfen, ob bereits eingeloggt
		// TODO LoginDialogController benutzen
		final LoginDialog loginDialog = new LoginDialog(view.getWindow());

		final PropertyChangeListener loginListener = new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof HashMap<?, ?>) {
					currentExp = (HashMap<String, Integer>) evt.getNewValue();
				} else if (evt.getNewValue() instanceof Boolean) {
					final boolean erfolg = (boolean) evt.getNewValue();
					if (erfolg) {
						chatClient.removePropertyChangeListener(this);
						loginDialog.getDialog().setVisible(false);
						chatName = loginDialog.getUsernameField().getText();
					} else {
						JOptionPane.showMessageDialog(view.getWindow(), "Benutzer / Passwort falsch!", "Login fehlgeschlagen!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};

		chatClient.addPropertyChangeListener(loginListener);

		loginDialog.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					chatClient.sendText("/login;" + loginDialog.getUsernameField().getText() + ";" + loginDialog.getPwField().getText());
				} catch (final IOException e1) {
				}
			}
		});

		loginDialog.getPwField().addActionListener(loginDialog.getLoginButton().getActionListeners()[0]);

		loginDialog.getDialog().setVisible(true);
	}

	/**
	 * Gönnt sich die Liste der aktuellen Match-Server.
	 */
	private void requestServerList() {
		try {
			chatClient.sendText("/requestServers");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt Folgende Listener zum ermöglichen des Chats:
	 * <ul>
	 * <li>ActionListener für das Eingabefeld und den Senden-Button
	 * <li>ActionListener für die Diss-, Konter- und Sir-Button
	 * <li>PropertyChangeListener zum Aktualisieren des Chats, wenn jemand etwas geschickt hat, oder um die
	 * Wer-ist-online-Liste zu aktualisieren
	 * </ul>
	 */
	private void addChatListeners() {
		chatInputField = view.getChatPanel().getInputField();
		chatArea = view.getChatPanel().getTextArea();
		view.getChatPanel().getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					sendChatText();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		view.getChatPanel().getDissButton().setMnemonic(KeyEvent.VK_D);
		view.getChatPanel().getDissButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					sendChatText(ChatAusruf.getInstance().getBeleidigung());
					abklingzeitButton(view.getChatPanel().getDissButton());
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		view.getChatPanel().getKonterButton().setMnemonic(KeyEvent.VK_K);
		view.getChatPanel().getKonterButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					sendChatText(ChatAusruf.getInstance().getAntwort());
					abklingzeitButton(view.getChatPanel().getKonterButton());
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		view.getChatPanel().getSirButton().setMnemonic(KeyEvent.VK_S);
		view.getChatPanel().getSirButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					sendChatText(ChatAusruf.getInstance().getSir());
					abklingzeitButton(view.getChatPanel().getSirButton());
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		chatInputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					sendChatText();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		chatClient.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				final Object newValue = evt.getNewValue();
				if (newValue instanceof String) {
					verarbeiteString((String) newValue);
				} else if (newValue instanceof ArrayList<?>) {
					aktualisiereAktiveSpieler(newValue);
				} else if (newValue instanceof HashMap<?, ?>) {
					aktualisiereAktiveServer(newValue);
				}
			}
		});
	}

	/**
	 * Deaktiviert den übergebenen Button für eine gewisse Zeit.
	 *
	 * @param button
	 *            Der Button
	 */
	private void abklingzeitButton(final JButton button) {
		button.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(7777);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
				button.setEnabled(true);
			};
		}.start();
	}

	/**
	 * Verarbeitet einen String, der vom Chatserver an diesen Client gesendet wurde. Kann zum Beitreten eines Matches,
	 * zum Anzeigen eines Fehlers oder zum Anzeigen einer neuen Chatnachricht führen.
	 *
	 * @param newValue
	 */
	private void verarbeiteString(final String newValue) {
		if (newValue.startsWith("/joinMe")) {
			connectToGame(ip, newValue.split(";")[1]);
		} else if (newValue.startsWith("/fehler")) {
			JOptionPane.showMessageDialog(view.getWindow(), newValue.split(";")[1], "Fehler!", JOptionPane.ERROR_MESSAGE);
			resetGUI();
		} else {
			aktualisiereChatTextArea(newValue);
		}
	}

	/**
	 * Sendet den Text, der in der Chattextbox steht, an den ChatClient. Sendet nicht, falls die Textbox leer ist oder
	 * nur Leerzeichen enthält.
	 *
	 * @throws IOException
	 */
	private void sendChatText() throws IOException {
		if (!chatInputField.getText().trim().equals("")) {
			sendChatText(chatInputField.getText());
			chatInputField.setText("");
		}
	}

	/**
	 * Sendet den übergebenen String an den ChatClient. Fügt den Namen des Spielers vorne an.
	 *
	 * @param str
	 *            Zu sendender Text.
	 * @throws IOException
	 */
	private void sendChatText(final String str) throws IOException {
		chatClient.sendText(chatName + ": " + str);
	}

	/**
	 * Sendet den übergebenen String mit einer Markierung als Systemnachricht in den Chat.
	 *
	 * @param str
	 *            Zu sendende Systemnachricht.
	 */
	private void sendSystemText(final String str) {
		try {
			chatClient.sendText("##System: " + str);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fügt den übergebenen String in einer neuen Zeile der ChatTextArea hinzu und scrollt diese nach ganz unten. Ändert
	 * anschließend die Farbe des ChatTabs.
	 *
	 * @param newValue
	 */
	private void aktualisiereChatTextArea(final Object newValue) {
		chatArea.setText(chatArea.getText() + newValue + "\n");
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
		if (view.getTabbedPane().getSelectedIndex() != 1) {
			view.getTabbedPane().setBackgroundAt(1, Color.GREEN);
		}
	}

	/**
	 * Aktualisiert die Liste aller aktiven Chat-Teilnehmer und deren IP-Adressen mit Hilfe einer HashMap.
	 *
	 * @param newValue
	 *            HashMap, die Namen der Spieler und deren IP-Adressen enthält.
	 */
	@SuppressWarnings("unchecked")
	private void aktualisiereAktiveSpieler(final Object newValue) {
		aktiveSpieler = (ArrayList<String>) newValue;
		view.getChatPanel().getAktiveSpieler().setText("");
		for (final String spieler : aktiveSpieler) {
			view.getChatPanel().getAktiveSpieler().setText(view.getChatPanel().getAktiveSpieler().getText() + " - " + spieler + "\n");
		}
	}

	/**
	 * Aktualisiert die Liste aller aktiven Match-Server,
	 *
	 * @param newValue
	 */
	@SuppressWarnings("unchecked")
	private void aktualisiereAktiveServer(final Object newValue) {
		aktiveServer = (HashMap<String, String>) newValue;
		view.getMatchMakingPanel().aktualisiereServerButtons(aktiveServer);
		addServerButtonListeners();
	}

	/**
	 * Fügt den Buttons in der Liste der aktiven Match-Server Listeners zum Beitreten der Matches hinzu.
	 */
	private void addServerButtonListeners() {
		final ArrayList<JButton> buttons = view.getMatchMakingPanel().getServerButtons();
		for (int i = 0; i < buttons.size(); i++) {
			final int x = i;
			buttons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final String port = aktiveServer.get(view.getMatchMakingPanel().getServerLabels().get(x).getText());
					try {
						chatClient.sendText("/removeServer;" + view.getMatchMakingPanel().getServerLabels().get(x).getText());
					} catch (final IOException e1) {
						e1.printStackTrace();
					}
					connectToGame(ip, port);
					view.getTabbedPane().setEnabledAt(2, false);
					view.getTabbedPane().setSelectedIndex(0);
				}
			});
		}
	}

	/**
	 * Sendet dem ChatClient ein /join mit Name und IP-Adresse, um die Wer-ist-online-Liste zu aktualisieren
	 */
	private void sendJoin() {
		try {
			chatClient.sendText("/join;" + chatName);
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		sendSystemText(chatName + " ist dem Chat beigetreten!");
	}

	/**
	 * Sorgt durch einen WindowListener dafür, dass beim Schließen des Hauptfensters eine Abmeldung am Chat sowie das
	 * Schließen aller Verbindungen ausgelöst wird.
	 */
	private void addWindowListener() {
		view.getWindow().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				sendSystemText(chatName + " hat den Chat verlassen!");
				try {
					chatClient.sendText("/removeServer;" + chatName);
					chatClient.sendText("/quit;" + chatName);
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
				closeClientConnection();
				super.windowClosing(e);
			}
		});
	}

	/**
	 * Setzt die Farbe des Chat-Tabs zurück auf den Standard Wert, wenn dieser angeklickt wird.
	 */
	private void addTabbedPaneListener() {
		view.getTabbedPane().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				if (view.getTabbedPane().getSelectedIndex() == 1) {
					view.getTabbedPane().setBackgroundAt(1, null);
				}
			}
		});
	}

	/**
	 * Füg den Buttons im MatchMaking-Tab Listeners hinzu, um einen Server zu erstellen oder einem Spiel beizutreten.
	 * Beim Erstellen eines Servers wird automatisch ein Client zum Verbinden mit dem Server erstellt. Deaktiviert
	 * anschließend den MatchMaking-Tab.
	 */
	private void addMatchMakingListeners() {
		view.getMatchMakingPanel().getCreateServerButton().setMnemonic(KeyEvent.VK_E);
		view.getMatchMakingPanel().getCreateServerButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createServer();
				view.getTabbedPane().setEnabledAt(2, false);
				view.getTabbedPane().setSelectedIndex(0);
			}
		});
		view.getMatchMakingPanel().getVsBotButton().setMnemonic(KeyEvent.VK_B);
		view.getMatchMakingPanel().getVsBotButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				createBotServer();
				view.getTabbedPane().setEnabledAt(2, false);
				view.getTabbedPane().setSelectedIndex(0);
			}
		});
	}

	/**
	 * Started einen Thread, in dem ein neuer Spielserver angelegt und gestartet wird. Das Erstellen des Servers wird
	 * anschließend im Chat mitgeteilt.
	 *
	 * Ist der Server fertig bzw. das Spiel vorbei, oder tritt ein Fehler auf, wird die Verbindungen vom Server beendet
	 * und die GUI zurückgesetzt.
	 *
	 */
	private void createServer() {
		try {
			chatClient.sendText("/createServer;" + chatName);
			sendSystemText(chatName + " hat ein Spiel erstellt!");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Started einen Thread, in dem ein neuer Spielserver angelegt und gestartet wird. Das Erstellen des Servers wird
	 * anschließend im Chat mitgeteilt.
	 *
	 * Ist der Server fertig bzw. das Spiel vorbei, oder tritt ein Fehler auf, wird die Verbindungen vom Server beendet
	 * und die GUI zurückgesetzt.
	 *
	 */
	private void createBotServer() {
		try {
			chatClient.sendText("/createBotServer;" + chatName);
			sendSystemText(chatName + " spielt jetzt gegen die KI!");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt und startet einen Client zum Verbinden mit einem Spielserver. Fügt dem Client anschließend einen
	 * PorpertyChangeListener zum Bearbeiten der Empfangenen Daten hinzu.
	 *
	 * @param ip
	 *            IP-Adresse des Servers
	 * @param port
	 *            Port des Servers
	 */
	private void connectToGame(final String ip, final String port) {
		view.getMiddlePanel().getOpponentImageButton().setIcon(null);
		view.getMiddlePanel().getPlayerImageButton().setIcon(null);
		client = new Client(ip, port);
		client.start();
		addPropertyChangeListener();
		view.enableButtons(false);
	}

	/**
	 * Fügt den KartenButtons ActionListener hinzu, durch die die entsprechende Karte in die Mitte gelegt wird.
	 */
	private void addCardButtonListenersAndMnemonics() {
		for (int i = 0; i < view.getPlayerCardsPanel().getCardButtons().length; i++) {
			final int x = i;
			view.getPlayerCardsPanel().getCardButtons()[i].setMnemonic(KeyEvent.VK_1 + x);
			view.getPlayerCardsPanel().getCardButtons()[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					karteInDieMitte(x,spieler.isIstGeblendet());
				}
			});
			
			view.getPlayerCardsPanel().getCardButtons()[i].addMouseListener(new MouseAdapter() {
				@Override
	            public void mouseReleased(MouseEvent event){
	                if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
	                	karteInDieMitte(x, spieler.isIstGeblendet());
	                	spieleKarte();
	                }
	            }
			}); /**/
		}
	}

	/**
	 * Gibt dem Button in der Mitte das Bild der Karte an dem übergebenen Index. Aktiviert den Button zum Ausspielen der
	 * Karte.
	 *
	 * @param index
	 *            Index der Karte, deren Bild in die Mitte soll.
	 */
	private void karteInDieMitte(final int index, final boolean blind) {
		indexInDerMitte = index;
		// ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(),
		// view.getPlayerCardsPanel().getCardButtons()[index].getIcon());
		if(!blind){
		ButtonImages.addKarteToButton(view.getMiddlePanel().getKarteImageButton(), spieler.getHand()[index]);
		}else{
			ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(),"hinten.jpg");
		}
		playButton.setEnabled(true);
	}

	/**
	 * Fügt dem Button zum Ausspielen einer Karte einen ActionListener hinzu.
	 */
	private void addPlayButtonListenerAndMnemonic() {
		playButton = view.getMiddlePanel().getPlayButton();
		playButton.setMnemonic(KeyEvent.VK_ENTER);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				spieleKarte();
			}
		});
	}

	/**
	 * Spielt die Karte aus, die gerade in der Mitte liegt. Deaktiviert den Button zu Ausspielen und die KartenButtons.
	 */
	private void spieleKarte() {
		client.send(String.valueOf(indexInDerMitte));
		view.enableButtons(false);
		playButton.setEnabled(false);
		// ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(), "hinten.jpg");
		// view.getPlayerCardsPanel().getCardButtons()[indexInDerMitte].setIcon(null);
		
		//Damit nach einem Blind die ausgespielte Karte gesehen werden kann wird sie nach dem ausspielen angezeigt
		ButtonImages.addKarteToButton(view.getMiddlePanel().getKarteImageButton(), spieler.getHand()[indexInDerMitte]);
		//Gespielte Karte wird durch eine Kartenrückseite ersetzt
		ButtonImages.addImageToButton(view.getPlayerCardsPanel().getCardButtons()[indexInDerMitte], "hinten.jpg");
	}

	/**
	 * Setzt die GUI auf einen Zustand, in dem kein Spiel stattfindet. Der MatchMakingTab wird aktiviert, die
	 * KartenButtons werden deaktiviert, die Animationen gestoppt, und die HPBars zurückgesetzt.
	 */
	private void resetGUI() {
		view.getTabbedPane().setEnabledAt(2, true);
		view.enableButtons(false);
		for (final JButton butt : view.getPlayerCardsPanel().getCardButtons()) {
			ButtonImages.addImageToButton(butt, "hinten.jpg");
		}
		for (final JButton butt : view.getOpponentCardsPanel().getCardButtons()) {
			ButtonImages.addImageToButton(butt, "hinten.jpg");
		}
		view.getMiddlePanel().getPlayerHpBar().setMinimum(0);
		view.getMiddlePanel().getPlayerHpBar().setMaximum(100);
		view.getMiddlePanel().getOpponentHpBar().setMinimum(0);
		view.getMiddlePanel().getOpponentHpBar().setMaximum(100);
		try {
			view.getMiddlePanel().setPlayerHpBarValue(0);
			view.getMiddlePanel().setOpponentHpBarValue(0);
			view.getMiddlePanel().setPlayerSSJBarValue(0);
			view.getMiddlePanel().setOpponentSSJBarValue(0);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Der PropertyChangeListener wartet darauf, dass eine Nachricht vom Server kommt. Anschließend wird eine
	 * entsprechende Methode aufgerufen.
	 */
	private void addPropertyChangeListener() {
		client.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				System.out.println("adsfadsf");
				
				if (evt.getNewValue().equals("Deck auswaehlen!")) {
					chooseDeck();
				} else if (evt.getNewValue().equals("Spieler kommt!")) {
					spielerKommtAlsNaechstes = true;
				} else if (evt.getNewValue().equals("Gegner kommt!")) {
					spielerKommtAlsNaechstes = false;
				} else if (evt.getNewValue() instanceof Spieler) {
					if (spielerKommtAlsNaechstes) {
						aktualisiereSpieler(evt);
					} else {
						aktualisiereGegner(evt);
					}
				} else if (evt.getNewValue().equals("Bitte Karte spielen!")) {
					chooseKarte();
				} else if (evt.getNewValue() instanceof Integer) {
					gegnerSpieltKarte(evt);
				} else if (evt.getNewValue().equals("SIEG")) {
					sieg();
				} else if (evt.getNewValue().equals("NIEDERLAGE")) {
					niederlage();
				} else if (evt.getNewValue().equals("UNENTSCHIEDEN")) {
					unentschieden();
				} else {
					System.out.println("Es kam Ramsch: " + evt.getNewValue());
				}
			}
		});
	}

	/**
	 * Wird aufgerufen, wenn der Gegner eine Karte ausspielt. Das Bild der Karte wird in die Mitte gelegt, und nach
	 * einiger Zeit wieder "weggenommen";
	 *
	 * @param evt
	 */
	private void gegnerSpieltKarte(final PropertyChangeEvent evt) {
		// ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(), gegner.getHand()[(Integer)
		// evt.getNewValue()].getImgPath());
		ButtonImages.addKarteToButton(view.getMiddlePanel().getKarteImageButton(), gegner.getHand()[(Integer) evt.getNewValue()]);
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		// view.getMiddlePanel().getKarteImageButton().setIcon(new
		// ImageIcon("images/hinten.jpg"));
	}

	/**
	 * Wird aufgerufen, wenn die Model-Klasse vom Gegner geschickt wurde. Der Gegner wird in ein Attribut übernommen,
	 * und die GUI aktualisiert.
	 *
	 * @param evt
	 *            Model-Klasse des Gegners
	 */
	private void aktualisiereGegner(final PropertyChangeEvent evt) {
		gegner = (Spieler) evt.getNewValue();
		view.getOpponentCardsPanel().getPanel().setImage(new ImageIcon("images/" + gegner.getAnimationFolder() + "/hintergrund.jpg").getImage());
		if (view.getMiddlePanel().getOpponentImageButton().getIcon() == null) {
			gegnerImageThread = new ButtonImageThread(gegner.getAnimationFolder(), view.getMiddlePanel().getOpponentImageButton());
			gegnerImageThread.start();
		}
		gegnerImageThread.setAnimationFolder(gegner.getAnimationFolder());
		gegnerImageThread.setSsj(gegner.isSsj());
		if (spieler != null && spieler.isSiehtGegnerkarten()) {
			for (int i = 0; i < gegner.getHand().length; i++) {
				// ButtonImages.addImageToButton(view.getOpponentCardsPanel().getCardButtons()[i],
				// gegner.getHand()[i].getImgPath());
				ButtonImages.addKarteToButton(view.getOpponentCardsPanel().getCardButtons()[i], gegner.getHand()[i]);
			}
		} else {
			for (int i = 0; i < gegner.getHand().length; i++) {
				ButtonImages.addImageToButton(view.getOpponentCardsPanel().getCardButtons()[i], "hinten.jpg");
			}
		}
		view.getMiddlePanel().getOpponentHpBar().setMinimum(0);
		view.getMiddlePanel().getOpponentHpBar().setMaximum(gegner.getMaxHP());
		view.getMiddlePanel().getOpponentSSJBar().setMinimum(0);
		view.getMiddlePanel().getOpponentSSJBar().setMaximum(gegner.getMaxSSJPoints());
		try {
			view.getMiddlePanel().setOpponentHpBarValue(gegner.getHp());
			view.getMiddlePanel().setOpponentSSJBarValue(gegner.getSsjPoints());
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		view.getMiddlePanel().aktualisiereGegnerEffekte(gegner);
	}

	/**
	 * Wird aufgerufen, wenn die Model-Klasse vom Spieler geschickt wurde. Der Spieler wird in ein Attribut übernommen,
	 * und die GUI aktualisiert.
	 *
	 * @param evt
	 *            Model-Klasse des Spielers
	 */
	private void aktualisiereSpieler(final PropertyChangeEvent evt) {
		spieler = (Spieler) evt.getNewValue();
		view.getPlayerCardsPanel().getPanel().setImage(new ImageIcon("images/" + spieler.getAnimationFolder() + "/hintergrund.jpg").getImage());
		if (view.getMiddlePanel().getPlayerImageButton().getIcon() == null) {
			playerImageThread = new ButtonImageThread(spieler.getAnimationFolder(), view.getMiddlePanel().getPlayerImageButton());
			playerImageThread.start();
		}
		playerImageThread.setAnimationFolder(spieler.getAnimationFolder());
		playerImageThread.setSsj(spieler.isSsj());
		if (spieler != null && !spieler.isIstGeblendet()) {
			for (int i = 0; i < spieler.getHand().length; i++) {
				// ButtonImages.addImageToButton(view.getPlayerCardsPanel().getCardButtons()[i],
				// spieler.getHand()[i].getImgPath());
				ButtonImages.addKarteToButton(view.getPlayerCardsPanel().getCardButtons()[i], spieler.getHand()[i]);
			}
		} else {
			for (int i = 0; i < spieler.getHand().length; i++) {
				ButtonImages.addImageToButton(view.getPlayerCardsPanel().getCardButtons()[i], "hinten.jpg");
			}
		}
		view.getMiddlePanel().getPlayerHpBar().setMinimum(0);
		view.getMiddlePanel().getPlayerHpBar().setMaximum(spieler.getMaxHP());
		view.getMiddlePanel().getPlayerSSJBar().setMinimum(0);
		view.getMiddlePanel().getPlayerSSJBar().setMaximum(spieler.getMaxSSJPoints());
		try {
			view.getMiddlePanel().setPlayerHpBarValue(spieler.getHp());
			view.getMiddlePanel().setPlayerSSJBarValue(spieler.getSsjPoints());
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		view.getMiddlePanel().getPlayerHpBar().setString("HP: " + spieler.getHp() + "/" + spieler.getMaxHP());
		view.getMiddlePanel().aktualisiereSpielerEffekte(spieler);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler vom Server gebeten wird, sein Deck auszuwählen. Öffnet einen Dialog zum Wählen
	 * des Decks. Dieser gibt den Index des gewählten Decks zurück, der anschließend an den Server gesendet wird.
	 */
	private void chooseDeck() {
		auswahlAnimationenLaufen = true;
		final String deck = new DeckChooseController(view.getWindow()).show() + "";
		auswahlAnimationenLaufen = false;
		client.send(deck);
		client.send(chatName);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler an der Reihe ist. Aktiviert die KartenButtons.
	 */
	private void chooseKarte() {
		view.enableButtons(true);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler gewonnen hat. Öffnet einen InfoDialog, setzt die GUI zurück, sendet einige
	 * Nachrichten in den Chat, und beendet die Verbindung des Clients.
	 */
	private void sieg() {
		sendSystemText(spieler.getSpielerName() + " hat " + gegner.getSpielerName() + " im Duell besiegt!");
		try {
			chatClient.sendText(spieler.getSpielerName() + ": " + ChatAusruf.getInstance().getSieg());
			chatClient.sendText(gegner.getSpielerName() + ": " + ChatAusruf.getInstance().getNiederlage());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(view.getWindow(), "Du gewinnst!\n" + Benutzerliste.EXP_WIN + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		// currentExp.put(spieler.getKlassenName(), Benutzerliste.EXP_WIN);
		final String pfad = SpielerListe.getPfadByKlassenName(spieler.getKlassenName());
		currentExp.put(pfad, currentExp.get(pfad) + Benutzerliste.EXP_WIN);
		resetGUI();
		closeClientConnection();
	}

	/**
	 * Wird aufgerufen, wenn der Spieler verloren hat. Öffnet einen InfoDialog, setzt die GUI zurück, und beendet die
	 * Verbindung des Clients.
	 */
	private void niederlage() {
		JOptionPane.showMessageDialog(view.getWindow(), "Du verlierst!\n" + Benutzerliste.EXP_LOSE + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		// currentExp.put(spieler.getKlassenName(), Benutzerliste.EXP_LOSE);
		final String pfad = SpielerListe.getPfadByKlassenName(spieler.getKlassenName());
		currentExp.put(pfad, currentExp.get(pfad) + Benutzerliste.EXP_LOSE);
		resetGUI();
		closeClientConnection();
	}

	/**
	 * Wird beim Unentschieden aufgerufen. Öffnet einen InfoDialog, setzt die GUI zurück, und beendet die Verbindung des
	 * Clients.
	 */
	private void unentschieden() {
		try {
			chatClient.sendText(spieler.getSpielerName() + ": Unentschieden! " + ChatAusruf.getInstance().getBeleidigung());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		// currentExp.put(spieler.getKlassenName(), Benutzerliste.EXP_DRAW);
		final String pfad = SpielerListe.getPfadByKlassenName(spieler.getKlassenName());
		currentExp.put(pfad, currentExp.get(pfad) + Benutzerliste.EXP_DRAW);
		JOptionPane.showMessageDialog(view.getWindow(), "Unentschieden!\n" + Benutzerliste.EXP_DRAW + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		resetGUI();
		closeClientConnection();
		// currentExp += Benutzerliste.EXP_DRAW;
	}

	/**
	 * Schließt die Verbindung des Clients und setzt das Attribut auf null.
	 */
	private void closeClientConnection() {
		if (client != null) {
			try {
				client.closeConnection();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			client = null;
		}
	}

}
