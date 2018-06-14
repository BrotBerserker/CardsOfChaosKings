package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import model.Effekt;
import model.Spieler;

/**
 * @author Roman Schmidt
 *
 */
public class MiddlePanel {

	private JPanel panel;
	private GridBagConstraints cons;
	private JButton spieler;
	private JButton gegner;
	private CardImageButton karteImageButton;
	private JButton playButton;
	private JButton playerImageButton;
	private JButton opponentImageButton;
	private JProgressBar playerHpBar;
	private JProgressBar opponentHpBar;
	private JTextField playerInfoView;
	private JTextField opponentInfoView;
	private JProgressBar playerSSJBar;
	private JProgressBar opponentSSJBar;
	private JPanel playerPanel;
	private JPanel opponentPanel;
	private JPanel playerEffekteVorDemZugPanel;
	private JPanel playerEffekteNachDemZugPanel;
	private JPanel opponentEffekteVorDemZugPanel;
	private JPanel opponentEffekteNachDemZugPanel;

	/**
	 *
	 */
	public MiddlePanel() {
		initPanel();
		initMidButtons();
		initPlayerImageButtons();
		initPlayerPanel();
		initOpponentPanel();
	}

	private void initPlayerPanel() {
		playerPanel = new JPanel();
		playerPanel.setOpaque(false);
		playerPanel.setLayout(new GridBagLayout());
		final GridBagConstraints playerCons = new GridBagConstraints();
		playerCons.insets = new Insets(5, 5, 5, 5);

		playerInfoView = new JTextField();
		playerInfoView.setEditable(false);
		playerCons.gridx = 0;
		playerCons.gridy = 0;
		playerCons.fill = GridBagConstraints.HORIZONTAL;
		playerCons.weightx = 1;
		playerCons.weighty = 0;
		playerCons.gridwidth = 1;
		playerCons.anchor = GridBagConstraints.NORTH;
		// playerPanel.add(new JScrollPane(playerInfoView), playerCons);
		playerPanel.add(playerInfoView, playerCons);
		// playerPanel.add(new JTextField("Robert"), playerCons);

		playerEffekteVorDemZugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playerEffekteVorDemZugPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playerEffekteVorDemZugPanel.setBackground(Color.GRAY);
		playerCons.gridx = 0;
		playerCons.gridy = 1;
		playerPanel.add(playerEffekteVorDemZugPanel, playerCons);

		playerEffekteNachDemZugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playerEffekteNachDemZugPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playerEffekteNachDemZugPanel.setBackground(Color.GRAY);
		playerCons.gridx = 0;
		playerCons.gridy = 2;
		playerPanel.add(playerEffekteNachDemZugPanel, playerCons);

		playerCons.gridwidth = 2;
		playerHpBar = new JProgressBar();
		playerHpBar.setForeground(Color.RED);
		playerHpBar.setBackground(Color.WHITE);
		playerHpBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playerHpBar.setStringPainted(true);
		playerHpBar.setMaximum(100);
		playerCons.fill = GridBagConstraints.HORIZONTAL;
		playerCons.gridx = 0;
		playerCons.gridy = 3;
		playerCons.weightx = 1;
		playerPanel.add(playerHpBar, playerCons);

		playerSSJBar = new JProgressBar();
		playerSSJBar.setMaximum(100);
		playerSSJBar.setForeground(Color.BLUE);
		playerSSJBar.setBackground(Color.WHITE);
		playerSSJBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playerSSJBar.setStringPainted(true);
		playerCons.gridx = 0;
		playerCons.gridy = 4;
		playerPanel.add(playerSSJBar, playerCons);

		cons.gridx = 4;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.weighty = 1;
		panel.add(playerPanel, cons);
	}

	private void initOpponentPanel() {
		opponentPanel = new JPanel();
		opponentPanel.setOpaque(false);
		opponentPanel.setLayout(new GridBagLayout());
		final GridBagConstraints playerCons = new GridBagConstraints();
		playerCons.insets = new Insets(5, 5, 5, 5);

		opponentInfoView = new JTextField();
		opponentInfoView.setEditable(false);
		playerCons.gridx = 0;
		playerCons.gridy = 0;
		playerCons.fill = GridBagConstraints.BOTH;
		playerCons.weightx = 1;
		playerCons.weighty = 0;
		opponentPanel.add(new JScrollPane(opponentInfoView), playerCons);

		opponentEffekteVorDemZugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		opponentEffekteVorDemZugPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		opponentEffekteVorDemZugPanel.setBackground(Color.GRAY);
		playerCons.gridx = 0;
		playerCons.gridy = 1;
		playerCons.weighty = 0;
		opponentPanel.add(opponentEffekteVorDemZugPanel, playerCons);

		opponentEffekteNachDemZugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		opponentEffekteNachDemZugPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		opponentEffekteNachDemZugPanel.setBackground(Color.GRAY);
		playerCons.gridx = 0;
		playerCons.gridy = 2;
		opponentPanel.add(opponentEffekteNachDemZugPanel, playerCons);

		opponentHpBar = new JProgressBar();
		opponentHpBar.setForeground(Color.RED);
		opponentHpBar.setBackground(Color.WHITE);
		opponentHpBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		opponentHpBar.setStringPainted(true);
		opponentHpBar.setMaximum(100);
		playerCons.fill = GridBagConstraints.HORIZONTAL;
		playerCons.gridx = 0;
		playerCons.gridy = 3;
		playerCons.weighty = 0;
		opponentPanel.add(opponentHpBar, playerCons);

		opponentSSJBar = new JProgressBar();
		opponentSSJBar.setMaximum(100);
		opponentSSJBar.setForeground(Color.BLUE);
		opponentSSJBar.setBackground(Color.WHITE);
		opponentSSJBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		opponentSSJBar.setStringPainted(true);
		playerCons.gridx = 0;
		playerCons.gridy = 4;
		opponentPanel.add(opponentSSJBar, playerCons);

		cons.gridx = 1;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.weighty = 1;
		panel.add(opponentPanel, cons);
	}

	private void initPlayerImageButtons() {
		cons.gridheight = 1;
		cons.weightx = 0;

		playerImageButton = new JButton();
		cons.gridx = 5;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(playerImageButton, cons);

		opponentImageButton = new JButton();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.WEST;
		panel.add(opponentImageButton, cons);
	}

	/**
	 *
	 */
	private void initMidButtons() {
		cons.gridheight = 1;

		karteImageButton = new CardImageButton();
		ButtonImages.addImageToButton(karteImageButton, "hinten.jpg");
		cons.gridx = 3;
		cons.gridy = 0;
		panel.add(karteImageButton, cons);

		playButton = new JButton("Ausspielen");
		playButton.setEnabled(false);
		cons.gridx = 2;
		cons.gridy = 0;
		panel.add(playButton, cons);
	}

	/**
	 *
	 */
	private void initPanel() {
		panel = new ImagePanel(new ImageIcon("images/hoelle.jpg").getImage());
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	public void setPlayerHpBarValue(final int newValue) throws InterruptedException {
		setBarValue(newValue, playerHpBar, "HP: ");
	}

	public void setOpponentHpBarValue(final int newValue) throws InterruptedException {
		setBarValue(newValue, opponentHpBar, "HP: ");
	}

	public void setPlayerSSJBarValue(final int newValue) throws InterruptedException {
		setBarValue(newValue, playerSSJBar, "SSJ: ");
	}

	public void setOpponentSSJBarValue(final int newValue) throws InterruptedException {
		setBarValue(newValue, opponentSSJBar, "SSJ: ");
	}

	private void setBarValue(final int newValue, final JProgressBar bar, final String string) throws InterruptedException {
		if (newValue > bar.getMaximum()) {
			setBarValue(bar.getMaximum(), bar, string);
		} else if (newValue < 0) {
			setBarValue(0, bar, string);
		} else {
			while (newValue > bar.getValue()) {
				Thread.sleep(10);
				bar.setValue(bar.getValue() + 1);
				bar.setString(string + bar.getValue() + "/" + bar.getMaximum());
			}
			while (newValue < bar.getValue()) {
				Thread.sleep(10);
				bar.setValue(bar.getValue() - 1);
				bar.setString(string + bar.getValue() + "/" + bar.getMaximum());
			}
		}
	}

	public void aktualisiereSpielerEffekte(final Spieler spieler) {
		playerInfoView.setText("");
		final StringBuilder newText = new StringBuilder();
		newText.append(spieler.getSpielerName() + "\n");
		// newText.append("Atk: " + spieler.getAtkBoost() + "\n");
		// newText.append("Def: " + spieler.getDefBoost());
		// newText.append("Vor dem Zug:\n");
		playerEffekteVorDemZugPanel.removeAll();
		for (final Effekt effekt : spieler.getEffekteVorDemZug()) {
			// newText.append(" - " + effekt.toString() + "\n");
			addIcon(playerEffekteVorDemZugPanel, effekt.getIconPath(), effekt.toString());
		}
		playerEffekteVorDemZugPanel.revalidate();
		// newText.append("Nach dem Zug:\n");
		playerEffekteNachDemZugPanel.removeAll();
		for (final Effekt effekt : spieler.getEffekteNachDemZug()) {
			// newText.append(" - " + effekt.toString() + "\n");
			addIcon(playerEffekteNachDemZugPanel, effekt.getIconPath(), effekt.toString());
		}
		playerEffekteNachDemZugPanel.revalidate();

		playerInfoView.setText(newText.toString());
	}

	public void aktualisiereGegnerEffekte(final Spieler spieler) {
		opponentInfoView.setText("");
		final StringBuilder newText = new StringBuilder();
		newText.append(spieler.getSpielerName() + "\n");
		// newText.append("Atk: " + spieler.getAtkBoost() + "\n");
		// newText.append("Def: " + spieler.getDefBoost() + "\n");
		// newText.append("Vor dem Zug:\n");
		opponentEffekteVorDemZugPanel.removeAll();
		for (final Effekt effekt : spieler.getEffekteVorDemZug()) {
			// newText.append(" - " + effekt.toString() + "\n");
			addIcon(opponentEffekteVorDemZugPanel, effekt.getIconPath(), effekt.toString());
		}
		opponentEffekteVorDemZugPanel.revalidate();
		// newText.append("Nach dem Zug:\n");
		opponentEffekteNachDemZugPanel.removeAll();
		for (final Effekt effekt : spieler.getEffekteNachDemZug()) {
			// newText.append(" - " + effekt.toString() + "\n");
			addIcon(opponentEffekteNachDemZugPanel, effekt.getIconPath(), effekt.toString());
		}
		opponentEffekteNachDemZugPanel.revalidate();

		opponentInfoView.setText(newText.toString());
	}

	private void addIcon(final JPanel panel, final String pathname, final String description) {
		// System.out.println(description);
		// System.out.println(pathname);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(pathname));
		} catch (final IOException e) {
			System.out.println("Can't read Pathname: " + pathname);
		}
		final JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setToolTipText(description);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		panel.add(picLabel);
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @return the spieler
	 */
	public JButton getSpieler() {
		return spieler;
	}

	/**
	 * @return the gegner
	 */
	public JButton getGegner() {
		return gegner;
	}

	/**
	 * @return the karteImageButton
	 */
	public CardImageButton getKarteImageButton() {
		return karteImageButton;
	}

	/**
	 * @return the playButton
	 */
	public JButton getPlayButton() {
		return playButton;
	}

	/**
	 * @return the playerImageButton
	 */
	public JButton getPlayerImageButton() {
		return playerImageButton;
	}

	/**
	 * @return the opponentImageButton
	 */
	public JButton getOpponentImageButton() {
		return opponentImageButton;
	}

	/**
	 * @return the playerHpBar
	 */
	public JProgressBar getPlayerHpBar() {
		return playerHpBar;
	}

	/**
	 * @return the opponentHpBar
	 */
	public JProgressBar getOpponentHpBar() {
		return opponentHpBar;
	}

	/**
	 * @return the cons
	 */
	public GridBagConstraints getCons() {
		return cons;
	}

	/**
	 * @return the playerInfoView
	 */
	public JTextField getPlayerInfoView() {
		return playerInfoView;
	}

	/**
	 * @return the opponentInfoView
	 */
	public JTextField getOpponentInfoView() {
		return opponentInfoView;
	}

	/**
	 * @return the playerSSJBar
	 */
	public JProgressBar getPlayerSSJBar() {
		return playerSSJBar;
	}

	/**
	 * @return the opponentSSJBar
	 */
	public JProgressBar getOpponentSSJBar() {
		return opponentSSJBar;
	}

	/**
	 * @return the playerPanel
	 */
	public JPanel getPlayerPanel() {
		return playerPanel;
	}

	/**
	 * @return the opponentPanel
	 */
	public JPanel getOpponentPanel() {
		return opponentPanel;
	}

}
