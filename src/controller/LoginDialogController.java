package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import view.LoginDialog;

/**
 * Controller für den {@link LoginDialog}. Wird nicht benutzt :O
 *
 * @author Roman Schmidt
 *
 */
public class LoginDialogController {

	private LoginDialog view;
	private String username;
	private String pw;

	/**
	 * @param owner
	 */
	public LoginDialogController(final JFrame owner) {
		view = new LoginDialog(owner);
		addActionListeners();
	}

	/**
	 *
	 */
	private void addActionListeners() {
		view.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				username = view.getUsernameField().getText();
				pw = view.getPwField().getText();
			}
		});
	}

	/**
	 *
	 */
	public void show() {
		view.getDialog().setVisible(true);
	}

	/**
	 *
	 */
	public void hide() {
		view.getDialog().dispose();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the pw
	 */
	public String getPw() {
		return pw;
	}

}
