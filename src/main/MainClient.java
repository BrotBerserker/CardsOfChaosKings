package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import controller.MainWindowController;

/**
 * Enthält die Main zum Starten eines Clients.
 *
 * @author Roman Schmidt
 *
 */
public class MainClient {

	/**
	 * Startet einen Client.
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final String ip = JOptionPane.showInputDialog("Host-IP: ", "localhost");
		new MainWindowController(ip, 2345);
	}
}
