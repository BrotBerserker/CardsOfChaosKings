package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Pfad;
import view.DeckInfoView;
import view.MainWindow;
import view.PfadPanel;

/**
 * Controller für das {@link PfadPanel}.
 *
 * @author RomSch
 *
 */
public class PfadPanelController {

	private PfadPanel view;
	private final JFrame owner;

	/**
	 * Constructor.
	 *
	 * @param pfad
	 *            Der Pfad, der angezeigt werden soll
	 * @param owner
	 *            Der Fenster vom {@link MainWindow}.
	 */
	public PfadPanelController(final Pfad pfad, final JFrame owner) {
		view = new PfadPanel(pfad);
		this.owner = owner;
		addListeners();
	}

	/**
	 * Fügt allen Buttons des Panels einen Listener hinzu.
	 */
	private void addListeners() {
		for (int i = 0; i < view.getButtons().size(); i++) {
			addListener(view.getButtons().get(i), i);
		}
	}

	/**
	 * Fügt dem Button an einem Index einen {@link ActionListener} hinzu, sodass beim Klick eine {@link DeckInfoView}
	 * erscheint.
	 *
	 * @param butt
	 *            Der Button
	 * @param i
	 *            Der Index des Buttons
	 */
	private void addListener(final JButton butt, final int i) {
		butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new DeckInfoView(view.getKlassen().get(i), owner);
			}
		});
	}

	/**
	 * @return Das JPanel
	 */
	public JPanel getPanel() {
		return view.getPanel();
	}

}
