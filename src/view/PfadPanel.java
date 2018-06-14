package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Klasse;
import model.KlassenComparator;
import model.Pfad;
import controller.MainWindowController;

public class PfadPanel {
	private JPanel panel;
	private Pfad pfad;
	private GridBagConstraints cons;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JLabel currentExpLabel;
	private Integer currentExp;
	private ArrayList<Klasse> klassen;

	public PfadPanel(Pfad pfad) {
		this.pfad = pfad;
		initPanel();
		initLabel();
		addButtons();
	}

	private void initLabel() {
		currentExp = MainWindowController.currentExp.get(pfad.getName());
		currentExpLabel = new JLabel("Aktuelle Exp: " + currentExp.toString());
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(currentExpLabel, cons);
	}

	private void initPanel() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	private void addButtons() {
		Klasse[] klassenArray = pfad.getKlassen();
		klassen = new ArrayList<Klasse>();
		Collections.addAll(klassen, klassenArray);
		Collections.sort(klassen, new KlassenComparator());
		cons.gridx = 0;
		cons.gridy = 1;
		JButton button;
		for (Klasse klasse : klassen) {
			button = new JButton();
			buttons.add(button);
			ButtonImages.addImageToButton(button, klasse.getAnimationFolder() + "/idle1.jpg");
			if (klasse.getRequiredExp() > currentExp) {
				button.setEnabled(false);
			}
			panel.add(button, cons);
			cons.gridy = 2;
			panel.add(new JLabel(klasse.getName()), cons);
			cons.gridy = 3;
			panel.add(new JLabel(klasse.getRequiredExp() + " Exp"), cons);
			cons.gridy = 1;
			cons.gridx += 1;
		}
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @return the buttons
	 */
	public ArrayList<JButton> getButtons() {
		return buttons;
	}

	/**
	 * @return the klassen
	 */
	public ArrayList<Klasse> getKlassen() {
		return klassen;
	}

}
