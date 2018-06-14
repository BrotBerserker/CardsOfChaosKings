package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Der Server, der die im Chat eingeloggten Spieler verwaltet. Wartet in einer Endlosschleife auf Verbindungen und
 * startet für jeden verbundenen Client einen {@link ChatServerThread}.
 *
 * @author RomSch
 *
 */
public class ChatServer extends Thread {

	private ServerSocket server;
	private Socket client;
	private static final int maxClientCount = 10;
	private ChatServerThread[] threads = new ChatServerThread[maxClientCount];

	/**
	 * Constructor.
	 *
	 * @param port
	 *            Port, auf dem der Server erreichbar sein soll.
	 */
	public ChatServer(final int port) {
		try {
			server = new ServerSocket(port);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				client = server.accept();
				for (int i = 0; i < maxClientCount; i++) {
					if (threads[i] == null) {
						threads[i] = new ChatServerThread(client, threads);
						threads[i].start();
						break;
					}
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
