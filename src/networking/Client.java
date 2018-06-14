/*
 * Autor: Roman S.
 * Aufgabe:
 * Datum: 13.09.2013
 * Status: in Arbeit
 */
package networking;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

/**
 * Client für ein Multiplayer-Match.
 *
 * @author RomSch
 *
 */
public class Client extends Thread {

	private Socket server;
	private PrintStream out;
	private ObjectInputStream in;
	private PropertyChangeSupport proSupport = new PropertyChangeSupport(this);
	private String ip;
	private String port;

	/**
	 * Constructor.
	 *
	 * @param ip
	 *            IP-Adresse des {@link Server}s
	 * @param port
	 *            Port des {@link Server}s
	 */
	public Client(final String ip, final String port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			server = new Socket(ip, Integer.parseInt(port));
			System.out.println("-Client started #" + new Random().nextInt(100) + "-");
			in = new ObjectInputStream(server.getInputStream());
			out = new PrintStream(server.getOutputStream());
			while (true) {
				final Object inputObject = in.readObject();
				proSupport.firePropertyChange("ServerInput", null, inputObject);
				if (inputObject.equals("SIEG") || inputObject.equals("NIEDERLAGE")) {
					break;
				}
			}
		} catch (final IOException e) {
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void closeConnection() throws IOException {
		in.close();
		out.close();
		server.close();
		System.out.println("-Client closed-");
	}

	public void addPropertyChangeListener(final PropertyChangeListener l) {
		proSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(final PropertyChangeListener l) {
		proSupport.removePropertyChangeListener(l);
	}

	public void send(final String str) {
		out.println(str);
	}
}
