package networking;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client zum Verbinden mit dem {@link ChatServer}.
 *
 * @author RomSch
 *
 */
public class ChatClient {
	private Socket server;
	private ObjectOutputStream out;
	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * Constructor.
	 *
	 * @param ip
	 *            IP-Adresse des ChatServers
	 * @param port
	 *            Port des ChatServers
	 */
	public ChatClient(final String ip, final int port) {
		try {
			server = new Socket(ip, port);
			out = new ObjectOutputStream(server.getOutputStream());
			new ReadThread(server).start();
			propertyChangeSupport = new PropertyChangeSupport(this);
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sendet einen String an den {@link ChatServer}
	 *
	 * @param text
	 * @throws IOException
	 */
	public void sendText(final String text) throws IOException {
		out.writeObject(text);
	}

	/**
	 * Fügt einen {@link PropertyChangeListener} hinzu. Events werden gefeuert, wenn eine Nachricht vom Server kommt.
	 *
	 * @param listener
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Entfernt einen {@link PropertyChangeListener}.
	 *
	 * @param listener
	 */
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Thread, der Nachrichten vom Server liest und als Events mit dem {@link PropertyChangeSupport} abfeuert.
	 * 
	 * @author RomSch
	 *
	 */
	class ReadThread extends Thread {

		private Socket server;
		private ObjectInputStream in;

		public ReadThread(final Socket server) {
			this.server = server;
		}

		@Override
		public void run() {
			try {
				in = new ObjectInputStream(server.getInputStream());
				while (true) {
					final Object obj = in.readObject();
					propertyChangeSupport.firePropertyChange("Incoming Text", null, obj);
				}
			} catch (final IOException e) {

			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}