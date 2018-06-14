package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * @author Roman Schmidt
 * 
 */
public class MainWindow {

	private JFrame window;
	private GridBagConstraints cons;
	private CardsPanel opponentCardsPanel;
	private CardsPanel playerCardsPanel;
	private MiddlePanel middlePanel;
	private JTabbedPane tabbedPane;
	private ChatPanel chatPanel;
	private MatchMakingPanel matchMakingPanel;

	/**
	 * 
	 */
	public MainWindow() {
		intiWindow();
		initPanels();
		window.setVisible(true);
	}

	/**
	 * 
	 */
	private void initPanels() {
		opponentCardsPanel = new CardsPanel(false);
		chatPanel = new ChatPanel();
		matchMakingPanel = new MatchMakingPanel();
		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.addTab("Gegner", opponentCardsPanel.getPanel());
		tabbedPane.addTab("Chat", chatPanel.getPanel());
		tabbedPane.addTab("Matchmaking", matchMakingPanel.getPanel());
		// TODO hier den Button weg
		JButton pfadbutt = new JButton("Pfade");
		tabbedPane.addTab("Fortschritt", pfadbutt);
		pfadbutt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PfadeView(window);

			}
		});

		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 2;
		cons.weighty = 2;
		window.add(tabbedPane, cons);

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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void enableButtons(boolean enabled) {
		for (JButton butt : playerCardsPanel.getCardButtons()) {
			butt.setEnabled(enabled);
		}
	}
}
