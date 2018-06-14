package map.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import view.CardsPanel;
import view.ChatPanel;
import view.MatchMakingPanel;
import view.MiddlePanel;

/**
 * @author Roman Schmidt
 * 
 */
public class DuelWindow {

	private JFrame window;
	private GridBagConstraints cons;
	private CardsPanel opponentCardsPanel;
	private CardsPanel playerCardsPanel;
	private MiddlePanel middlePanel;
	private ChatPanel chatPanel;
	private MatchMakingPanel matchMakingPanel;

	/**
	 *
	 */
	public DuelWindow() {
		intiWindow();
		initPanels();
		enableButtons(false);
		window.setUndecorated(true);
		window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
	}

	/**
	 *
	 */
	private void initPanels() {
		opponentCardsPanel = new CardsPanel(false);
		chatPanel = new ChatPanel();
		matchMakingPanel = new MatchMakingPanel();

		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 2;
		cons.weighty = 2;
		window.add(opponentCardsPanel.getPanel(), cons);

		playerCardsPanel = new CardsPanel(true);
		cons.gridx = 0;
		cons.gridy = 2;
		window.add(playerCardsPanel.getPanel(), cons);

		middlePanel = new MiddlePanel();
		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		window.add(middlePanel.getPanel(), cons);
	}

	/**
	 *
	 */
	private void intiWindow() {
		window = new JFrame("Hauptfenster");
		window.setIconImage(new ImageIcon("images/icon.png").getImage());
		window.getContentPane().setBackground(Color.BLACK);
		window.setBounds(110, 45, 1200, 875);
		window.setResizable(true);
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {
		window.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	/**
	 * @return the window
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * @return the playerCardsPanel
	 */
	public CardsPanel getOpponentCardsPanel() {
		return opponentCardsPanel;
	}

	/**
	 * @return the opponentCardsPanel
	 */
	public CardsPanel getPlayerCardsPanel() {
		return playerCardsPanel;
	}

	/**
	 * @return the middlePanel
	 */
	public MiddlePanel getMiddlePanel() {
		return middlePanel;
	}

	/**
	 * @return the chatPanel
	 */
	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	/**
	 * @return the matchMakingPanel
	 */
	public MatchMakingPanel getMatchMakingPanel() {
		return matchMakingPanel;
	}

	public void enableButtons(final boolean enabled) {
		for (final JButton butt : playerCardsPanel.getCardButtons()) {
			butt.setEnabled(enabled);
		}
	}

	//	public void showSpacebarAction(String actionName, Actio)
}
