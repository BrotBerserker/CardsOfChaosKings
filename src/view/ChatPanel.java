package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatPanel {

	private JPanel panel;
	private GridBagConstraints cons;
	private JTextArea textArea;
	private JTextField inputField;
	private JButton sendButton;
	private JTextArea aktiveSpieler;
	private JButton dissButton;
	private JButton konterButton;
	private JButton sirButton;

	public ChatPanel() {
		initPanel();
		initTextAreas();
		initInput();
	}

	private void initInput() {
		cons.gridwidth = 1;
		inputField = new JTextField();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weighty = 0;
		cons.gridwidth = 1;
		cons.gridx = 0;
		cons.gridy = 1;
		panel.add(inputField, cons);

		sendButton = new JButton("Senden");
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(sendButton, cons);

		dissButton = new JButton("Diss");
		cons.gridx = 2;
		cons.gridy = 1;
		panel.add(dissButton, cons);

		konterButton = new JButton("Krasser Konter");
		cons.gridx = 3;
		cons.gridy = 1;
		panel.add(konterButton, cons);

		sirButton = new JButton("Like a Sir!");
		cons.gridx = 4;
		cons.gridy = 1;
		panel.add(sirButton, cons);

	}

	private void initTextAreas() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.weightx = 20;
		cons.weighty = 1;
		cons.fill = GridBagConstraints.BOTH;
		JScrollPane sbar = new JScrollPane(textArea);
		sbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(sbar, cons);

		aktiveSpieler = new JTextArea();
		aktiveSpieler.setEditable(false);
		cons.gridx = 1;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.gridwidth = 4;
		panel.add(new JScrollPane(aktiveSpieler), cons);
	}

	private void initPanel() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.LIGHT_GRAY);
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
	 * @return the textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * @return the inputField
	 */
	public JTextField getInputField() {
		return inputField;
	}

	/**
	 * @return the sendButton
	 */
	public JButton getSendButton() {
		return sendButton;
	}

	/**
	 * @return the aktiveSpieler
	 */
	public JTextArea getAktiveSpieler() {
		return aktiveSpieler;
	}

	/**
	 * @return the dissButton
	 */
	public JButton getDissButton() {
		return dissButton;
	}

	/**
	 * @return the konterButton
	 */
	public JButton getKonterButton() {
		return konterButton;
	}

	/**
	 * @return the sirButton
	 */
	public JButton getSirButton() {
		return sirButton;
	}

}
