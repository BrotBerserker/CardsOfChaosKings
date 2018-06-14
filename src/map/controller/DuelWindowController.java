package map.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import ki.GreedyKi;
import ki.IKi;
import map.view.DuelWindow;
import map.view.MapWindow;
import model.Spieler;
import model.SpielerListe;
import view.ButtonImages;
import benutzermanagement.Benutzerliste;

/**
 * @author Roman Schmidt
 *
 */
public class DuelWindowController {

	// --- Gameplay ---

	private Spieler spieler;

	private Spieler gegner;

	public static HashMap<String, Integer> currentExp;

	// --- GUI ---

	private final DuelWindow view;

	private int indexInDerMitte;

	private JButton playButton;

	private int spielerAnDerReihe;

	public static boolean auswahlAnimationenLaufen = false;

	public static boolean ingameAnimationenLaufen = false;

	public static boolean[] ssj = { false, false };

	private IKi ki;

	private static boolean cardPlayed = false;

	private MapWindow mapWindow;

	/**
	 * Konstruktor. Initialisert alles!
	 *
	 * @param mapWindow
	 *
	 * @param ip
	 *            IP-Adresse des ChatServers
	 * @param port
	 *            Port des ChatServers
	 * @param chatName
	 *            Name, der im Chat angzeigt wird. Sollte eindeutig sein.
	 */
	public DuelWindowController(final String playerDeck, final String opponentDeck, final MapWindow mapWindow) {
		this.mapWindow = mapWindow;

		// Hauptfenster aufbauen und sichtbar machen
		if (mapWindow != null) {
			mapWindow.getWindow().setVisible(false);
		}
		view = new DuelWindow();
		addWindowListener();

		// Listener für das ausspielen von Karten
		addCardButtonListenersAndMnemonics();
		addPlayButtonListenerAndMnemonic();

		initializePlayers(playerDeck, opponentDeck);
		startGame();
	}

	private void addWindowListener() {
		view.getWindow().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				niederlage();
			}
		});
	}

	private void initializePlayers(final String playerDeck, final String opponentDeck) {
		// final int deck = new DeckChooseController(view.getWindow()).show();
		spieler = new Spieler(SpielerListe.KLASSEN.get(playerDeck));
		gegner = new Spieler(SpielerListe.KLASSEN.get(opponentDeck));
		// spieler.addPropertyChangeListener(e -> refreshPlayer());
		// gegner.addPropertyChangeListener(e -> refreshOpponent());
		spieler.setGegner(gegner);
		gegner.setGegner(spieler);
		refreshPlayer();
		refreshOpponent();
		ki = new GreedyKi();
	}

	private void startGame() {
		spielerAnDerReihe = new Random().nextInt(2) + 1;
		while (true) {
			System.out.println("spielzug: " + spielerAnDerReihe);
			if (spielerAnDerReihe == 1) {
				spielzugSpieler1();
				spielerAnDerReihe = 2;
			} else if (spielerAnDerReihe == 2) {
				spielzugSpieler2();
				spielerAnDerReihe = 1;
			}
			if (spieler.getHp() <= 0 && gegner.getHp() <= 0) {
				unentschieden();
				break;
			} else if (spieler.getHp() <= 0) {
				niederlage();
				break;
			} else if (gegner.getHp() <= 0) {
				sieg();
				break;
			}
		}
	}

	private void spielzugSpieler1() {
		cardPlayed = false;
		spieler.vorDemSpielzug();
		refreshPlayer();
		view.enableButtons(true);
		while (!cardPlayed) {
			doNothing();
		}
		refreshPlayer();
		refreshOpponent();
	}

	private void spielzugSpieler2() {
		cardPlayed = false;
		gegner.vorDemSpielzug();
		refreshOpponent();
		final int nextCard = ki.nextCard(spieler, gegner);
		final String cardImage = gegner.getHand()[nextCard].getImgPath();
		gegner.spieleKarte(nextCard);
		gegner.zieheKarte();
		gegner.nachDemSpielzug();
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(500);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
				cardPlayed = true;
			};
		}.start();
		while (!cardPlayed) {
			doNothing();
		}
		ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(), cardImage);
		refreshPlayer();
		refreshOpponent();
	}

	private void doNothing() {
		System.out.flush();
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
					selectCard(x);
				}
			});
		}
	}

	/**
	 * Gibt dem Button in der Mitte das Bild der Karte an dem übergebenen Index. Aktiviert den Button zum Ausspielen der
	 * Karte.
	 *
	 * @param index
	 *            Index der Karte, deren Bild in die Mitte soll.
	 */
	private void selectCard(final int index) {
		indexInDerMitte = index;
		ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(), view.getPlayerCardsPanel().getCardButtons()[index].getIcon());
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
				playCard();
			}
		});
	}

	/**
	 * Spielt die Karte aus, die gerade in der Mitte liegt. Deaktiviert den Button zu Ausspielen und die KartenButtons.
	 */
	private void playCard() {
		view.enableButtons(false);
		playButton.setEnabled(false);
		spieler.spieleKarte(indexInDerMitte);
		spieler.zieheKarte();
		spieler.nachDemSpielzug();
		ButtonImages.addImageToButton(view.getMiddlePanel().getKarteImageButton(), "hinten.jpg");
		view.getPlayerCardsPanel().getCardButtons()[indexInDerMitte].setIcon(null);
		cardPlayed = true;
		System.out.println("played = " + cardPlayed);
	}

	/**
	 * Wird aufgerufen, wenn die Model-Klasse vom Gegner geschickt wurde. Der Gegner wird in ein Attribut übernommen,
	 * und die GUI aktualisiert.
	 *
	 * @param evt
	 *            Model-Klasse des Gegners
	 */
	private void refreshOpponent() {
		view.getOpponentCardsPanel().getPanel().setImage(new ImageIcon("images/" + gegner.getAnimationFolder() + "/hintergrund.jpg").getImage());
		if (gegner.isSsj()) {
			ssj[1] = true;
		} else {
			ssj[1] = false;
		}
		if (view.getMiddlePanel().getOpponentImageButton().getIcon() == null) {
			ingameAnimationenLaufen = true;
			// TODO hier ButtonImageThread benutzern
			// ButtonImages.startImageThread(gegner.getAnimationFolder(),
			// view.getMiddlePanel().getOpponentImageButton(), 1);
		}
		if (spieler != null && spieler.isSiehtGegnerkarten()) {
			for (int i = 0; i < gegner.getHand().length; i++) {
				ButtonImages.addImageToButton(view.getOpponentCardsPanel().getCardButtons()[i], gegner.getHand()[i].getImgPath());
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
	private void refreshPlayer() {
		view.getPlayerCardsPanel().getPanel().setImage(new ImageIcon("images/" + spieler.getAnimationFolder() + "/hintergrund.jpg").getImage());
		if (spieler.isSsj()) {
			ssj[0] = true;
		} else {
			ssj[0] = false;
		}
		if (view.getMiddlePanel().getPlayerImageButton().getIcon() == null) {
			// TODO hier ButtonImageThread benutzern
			// ingameAnimationenLaufen = true;
			// ButtonImages.startImageThread(spieler.getAnimationFolder(), view.getMiddlePanel().getPlayerImageButton(),
			// 0);
		}
		if (spieler != null && !spieler.isIstGeblendet()) {
			for (int i = 0; i < spieler.getHand().length; i++) {
				ButtonImages.addImageToButton(view.getPlayerCardsPanel().getCardButtons()[i], spieler.getHand()[i].getImgPath());
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
	 * Wird aufgerufen, wenn der Spieler gewonnen hat. Öffnet einen InfoDialog, setzt die GUI zurück, sendet einige
	 * Nachrichten in den Chat, und beendet die Verbindung des Clients.
	 */
	private void sieg() {
		JOptionPane.showMessageDialog(view.getWindow(), "Du gewinnst!\n" + Benutzerliste.EXP_WIN + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		view.getWindow().dispose();
		if (mapWindow != null) {
			mapWindow.getWindow().setVisible(true);
		}
	}

	/**
	 * Wird aufgerufen, wenn der Spieler verloren hat. Öffnet einen InfoDialog, setzt die GUI zurück, und beendet die
	 * Verbindung des Clients
	 */
	private void niederlage() {
		JOptionPane.showMessageDialog(view.getWindow(), "Du verlierst!\n" + Benutzerliste.EXP_LOSE + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		view.getWindow().dispose();
		if (mapWindow != null) {
			mapWindow.getWindow().setVisible(true);
		}
	}

	private void unentschieden() {
		JOptionPane.showMessageDialog(view.getWindow(), "Unentschieden!\n" + Benutzerliste.EXP_DRAW + " Exp beim " + spieler.getKlassenName() + " erhalten!");
		view.getWindow().dispose();
		if (mapWindow != null) {
			mapWindow.getWindow().setVisible(true);
		}
	}

}
