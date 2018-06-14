package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Karte;
import model.Klasse;

public class DeckInfoView {

	private JDialog dialog;
	private Klasse klasse;
	private GridBagConstraints cons;

	public DeckInfoView(final Klasse klasse, final JFrame owner) {
		this.klasse = klasse;
		initDialog(owner);
		addButtons();
		adjustSize(owner);
		dialog.setVisible(true);
	}

	private void adjustSize(final JFrame owner) {
		dialog.setBounds(owner.getBounds().width / 2 - owner.getBounds().width / 2 + owner.getBounds().x, owner.getBounds().height / 2 - owner.getBounds().height / 2 + owner.getBounds().y,
				owner.getBounds().width, owner.getBounds().height);
	}

	private void addButtons() {
		final List<Karte> deck = klasse.getDeck();
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		cons.gridx = 0;
		cons.gridy = 0;
		CardImageButton butt;
		for (final Karte karte : deck) {
			butt = new CardImageButton();
			cons.insets.bottom = 0;
			// ButtonImages.addImageToButton(butt, karte.getImgPath());
			ButtonImages.addKarteToButton(butt, karte);
			buttonPanel.add(butt, cons);
			cons.insets.bottom = 5;
			cons.gridy += 1;
			buttonPanel.add(new JLabel((int) karte.getMaechtigkeit() + "x"), cons);
			cons.gridy -= 1;
			cons.gridx += 1;
			if (cons.gridx % 5 == 0) {
				cons.gridx = 0;
				cons.gridy += 2;
			}
		}
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.fill = GridBagConstraints.BOTH;
		dialog.add(new JScrollPane(buttonPanel), cons);
	}

	private void initDialog(final JFrame owner) {
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setTitle("Klasse: " + klasse.getName());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(2, 5, 2, 5);
	}

}
