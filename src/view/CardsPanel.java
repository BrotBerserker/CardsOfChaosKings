package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;

import model.Spieler;

/**
 * @author Roman Schmidt
 *
 */
public class CardsPanel {

	/**
	 *
	 */
	private ImagePanel panel;
	private GridBagConstraints cons;
	private CardImageButton[] cardButtons;

	/**
	 *
	 */
	public CardsPanel(final boolean spieler) {
		initPanel();
		if (spieler) {
			initSpielerView();
		} else {
			initGegnerView();
		}
	}

	private void initGegnerView() {
		cardButtons = new CardImageButton[Spieler.ANZAHL_HANDKARTEN];
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 2;
		cons.weighty = 2;
		cons.gridy = 0;
		cons.gridheight = 2;
		for (int i = 0; i < cardButtons.length; i++) {
			cons.gridx = i + 1;
			cardButtons[i] = new CardImageButton();
			cardButtons[i].setBorderPainted(false);
			ButtonImages.addImageToButton(cardButtons[i], "hinten.jpg");
			panel.add(cardButtons[i], cons);
		}

	}

	private void initSpielerView() {
		cardButtons = new CardImageButton[Spieler.ANZAHL_HANDKARTEN];
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 2;
		cons.weighty = 2;
		cons.gridy = 0;
		cons.gridheight = 2;
		for (int i = 0; i < cardButtons.length; i++) {
			cons.gridx = i;
			// cardButtons[i] = new JButton();
			// cardButtons[i].setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
			cardButtons[i] = new CardImageButton("", "", "");
			ButtonImages.addImageToButton(cardButtons[i], "hinten.jpg");
			panel.add(cardButtons[i], cons);
		}
	}

	/**
	 *
	 */
	private void initPanel() {
		panel = new ImagePanel(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(10, 10, 10, 10);
	}

	/**
	 * @return the panel
	 */
	public ImagePanel getPanel() {
		return panel;
	}

	/**
	 * @return the cardButtons
	 */
	public CardImageButton[] getCardButtons() {
		return cardButtons;
	}
}
