package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MatchMakingPanel {

	private JPanel panel;
	private JButton createServerButton;

	private ArrayList<JButton> serverButtons = new ArrayList<JButton>();

	private ArrayList<JLabel> serverLabels = new ArrayList<JLabel>();
	private GridBagConstraints cons;
	private JPanel buttonPanel;
	private JButton vsBotButton;

	public MatchMakingPanel() {
		initPanel();
		initButtons();
		initButtonPanel();
	}

	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		cons.gridx = 0;
		cons.gridy = 1;
		cons.fill = GridBagConstraints.VERTICAL;
		panel.add(new JScrollPane(buttonPanel), cons);
	}

	public void aktualisiereServerButtons(final Map<String, String> servers) {
		cons.gridwidth = 1;
		for (final JLabel label : serverLabels) {
			buttonPanel.remove(label);
		}
		serverLabels.clear();
		for (final JButton button : serverButtons) {
			buttonPanel.remove(button);
		}
		serverButtons.clear();
		final GridBagConstraints buttCons = new GridBagConstraints();
		buttCons.insets = new Insets(5, 5, 5, 5);
		buttCons.gridy = 0;
		for (final Entry<String, String> entry : servers.entrySet()) {
			buttCons.gridx = 0;
			final JLabel label = new JLabel(entry.getKey());
			buttonPanel.add(label, buttCons);
			serverLabels.add(label);

			buttCons.gridx = 1;
			final JButton button = new JButton("Verbinden");
			buttonPanel.add(button, buttCons);
			serverButtons.add(button);

			buttCons.gridy++;
		}
	}

	private void initButtons() {
		createServerButton = new JButton("Server erstellen");
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 2;
		panel.add(createServerButton, cons);

		vsBotButton = new JButton("Spiel gegen die KI");
		cons.gridx = 2;
		panel.add(vsBotButton, cons);
		cons.gridx = 0;
	}

	private void initPanel() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @return the createServerButton
	 */
	public JButton getCreateServerButton() {
		return createServerButton;
	}

	/**
	 * @return the serverButtons
	 */
	public ArrayList<JButton> getServerButtons() {
		return serverButtons;
	}

	/**
	 * @return the serverLabels
	 */
	public ArrayList<JLabel> getServerLabels() {
		return serverLabels;
	}

	/**
	 * @return the vsBotButton
	 */
	public JButton getVsBotButton() {
		return vsBotButton;
	}

}
