package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Klasse;
import model.SpielerListe;
import controller.MainWindowController;

public class DeckChooseView {

	private JDialog dialog;
	private GridBagConstraints cons;
	private JButton[] buttons;

	/**
	 * @return the buttons
	 */
	public JButton[] getButtons() {
		return buttons;
	}

	public DeckChooseView(final JFrame owner) {
		System.out.println("lalsapläas");
		initDialog(owner);
		initButtons();
	}

	private void initButtons() {
		buttons = new JButton[SpielerListe.KLASSEN.size()];
		cons.insets = new Insets(5, 5, 5, 5);
		int i = 0;
		for (int j=0;j<  SpielerListe.KLASSEN.values().toArray().length;j++) {
			final Klasse klasse=(Klasse)SpielerListe.KLASSEN.values().toArray()[j];
			cons.gridx = i;
			cons.gridy = 0;
			buttons[i] = new JButton();
			if (klasse.getRequiredExp() > MainWindowController.currentExp.get(SpielerListe.getPfadByKlassenName(klasse.getName()))) {
				// do nothing
			} else {
				new ButtonImageThread(klasse.getAnimationFolder(), buttons[i]).start();
				dialog.add(buttons[i], cons);
				cons.gridy = 1;
				dialog.add(new JLabel(klasse.getName()), cons);
			}
			i++;
		}
		dialog.pack();
	}

	private void initDialog(final JFrame owner) {
		dialog = new JDialog(owner);
		dialog.setTitle("Wähle ein Deck aus!");
		dialog.setModal(true);

		dialog.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
	}

	/**
	 * @return the dialog
	 */
	public JDialog getDialog() {
		return dialog;
	}

}
