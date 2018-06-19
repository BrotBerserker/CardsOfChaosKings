package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Klasse;
import model.SpielerListe;
import view.DeckChooseView;
import view.MainWindow;

/**
 * Controller für die {@link DeckChooseView}. Dialog, über den der Benutzer im Multiplayer ein Deck auswählen kann.
 *
 * @author RomSch
 *
 */
public class DeckChooseController {

	private DeckChooseView view;
	private JFrame mainview;

	/**
	 * Der Index des ausgewählten Decks.
	 */
	private String auswahl;

	/**
	 * Konstruktor.
	 *
	 * @param mainview
	 *            Das Hauptfenster aus dem {@link MainWindow}.
	 */
	public DeckChooseController(final JFrame mainview) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					view = new DeckChooseView(mainview);
					initActionListeners();
					DeckChooseController.this.mainview = mainview;
				}
			});
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void initActionListeners() {
		final JButton buttons[] = view.getButtons();

		for (int i = 0; i < buttons.length; i++) {
			final String j = (String)SpielerListe.KLASSEN.keySet().toArray()[i];
			buttons[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					auswahl = j;
					view.getDialog().dispose();
				}
			});
		}

		// wenn der Dialog geschlossen wird, wird ein Deck per Random ausgewählt
		view.getDialog().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				auswahl = (String)SpielerListe.KLASSEN.values().toArray()[new Random().nextInt(SpielerListe.KLASSEN.size())];
				Klasse klasse = SpielerListe.getKlasse(auswahl);
				while (klasse.getRequiredExp() > MainWindowController.currentExp.get(SpielerListe.getPfadByKlassenName(klasse.getName()))) {
					auswahl = (String)SpielerListe.KLASSEN.values().toArray()[new Random().nextInt(SpielerListe.KLASSEN.size())];
					klasse = SpielerListe.getKlasse(auswahl);
				}
				super.windowClosing(e);
			}
		});
	}

	/**
	 * Zeigt den Dialog. Gibt nach dem Schließen des Dialogs einen int mit dem Index des ausgewählten Decks zurück.
	 *
	 * @return Index des ausgewählten Decks.
	 */
	public String show() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
					view.getDialog().pack();
					view.getDialog()
							.setBounds(mainview.getBounds().width / 2 - view.getDialog().getBounds().width / 2 + mainview.getBounds().x,
									mainview.getBounds().height / 2 - view.getDialog().getBounds().height / 2 + mainview.getBounds().y, view.getDialog().getBounds().width,
									view.getDialog().getBounds().height);
					view.getDialog().setVisible(true);
				}
			});
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return auswahl;
	}
}
