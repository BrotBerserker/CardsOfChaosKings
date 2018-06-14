package testing;

import java.io.IOException;

import networking.Server;

/**
 * @author Roman Schmidt
 *
 */
public class NetworkTestingServer {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new NetworkTestingServer();
	}

	/**
	 * Constructor.
	 *
	 */
	public NetworkTestingServer() {
		final Server server = new Server(1337);
		try {
			server.startServing();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
