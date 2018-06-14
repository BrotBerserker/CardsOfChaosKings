package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Roman Schmidt
 * 
 */
public class LoginDialog {

	private JDialog dialog;
	private GridBagConstraints cons;
	private JLabel usernameLabel;
	private JLabel pwLabel;
	private JTextField usernameField;
	private JTextField pwField;
	private JButton loginButton;
	private JButton registerButton;

	/**
	 * 
	 */
	public LoginDialog(JFrame owner) {
		initDialog(owner);
		initLabels();
		initTextFields();
		initButtons();
		dialog.pack();
		dialog.setBounds(owner.getBounds().width / 2 - dialog.getBounds().width / 2 + owner.getBounds().x, owner.getBounds().height / 2
				- dialog.getBounds().height / 2 + owner.getBounds().y, dialog.getBounds().width, dialog.getBounds().height);
	}

	/**
	 * 
	 */
	private void initButtons() {
		cons.gridwidth = 1;
		loginButton = new JButton("Login");
		cons.gridx = 2;
		cons.gridy = 2;
		dialog.add(loginButton, cons);

		registerButton = new JButton("Registrieren");
		cons.gridx = 1;
		cons.gridy = 2;
		dialog.add(registerButton, cons);
	}

	/**
	 * 
	 */
	private void initTextFields() {
		cons.gridwidth = 2;
		usernameField = new JTextField(20);
		cons.gridx = 1;
		cons.gridy = 0;
		dialog.add(usernameField, cons);

		pwField = new JPasswordField(20);

		cons.gridx = 1;
		cons.gridy = 1;
		dialog.add(pwField, cons);
	}

	/**
	 * 
	 */
	private void initLabels() {
		cons.anchor = GridBagConstraints.EAST;

		usernameLabel = new JLabel("Benutzername: ");
		cons.gridx = 0;
		cons.gridy = 0;
		dialog.add(usernameLabel, cons);

		pwLabel = new JLabel("Passwort: ");
		cons.gridx = 0;
		cons.gridy = 1;
		dialog.add(pwLabel, cons);
	}

	/**
	 * 
	 */
	private void initDialog(JFrame owner) {
		dialog = new JDialog(owner);
		dialog.setModal(true);
		dialog.setTitle("Anmelden");
		dialog.setLayout(new GridBagLayout());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	/**
	 * @return the dialog
	 */
	public JDialog getDialog() {
		return dialog;
	}

	/**
	 * @return the usernameLabel
	 */
	public JLabel getUsernameLabel() {
		return usernameLabel;
	}

	/**
	 * @return the pwLabel
	 */
	public JLabel getPwLabel() {
		return pwLabel;
	}

	/**
	 * @return the usernameField
	 */
	public JTextField getUsernameField() {
		return usernameField;
	}

	/**
	 * @return the pwField
	 */
	public JTextField getPwField() {
		return pwField;
	}

	/**
	 * @return the loginButton
	 */
	public JButton getLoginButton() {
		return loginButton;
	}

	/**
	 * @return the registerButton
	 */
	public JButton getRegisterButton() {
		return registerButton;
	}

}
