package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benutzermanagement.Benutzerliste;

/**
 * Wird für jeden Client, der sich mit dem {@link ChatServer} verbindet, erstellt und gestartet.
 *
 * @author RomSch
 *
 */
public class ChatServerThread extends Thread {
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ChatServerThread[] threads;

	private static List<String> aktiveNutzer = new ArrayList<String>();

	private static Map<String, String> aktiveServer = new HashMap<String, String>();

	/**
	 * Constructor.
	 *
	 * @param client
	 *            Der Client, der durch diesen Thread behandelt werden soll.
	 * @param threads
	 *            Die {@link ChatServerThread}s der anderen Spieler
	 */
	public ChatServerThread(final Socket client, final ChatServerThread[] threads) {
		this.client = client;
		this.threads = threads;
	}

	@Override
	// TODO überarbeiten
	public void run() {
		try {
			getStreams();
			while (true) {
				// Nachricht des Clients empfangen
				final String x = (String) in.readObject();

				// Disconnect ("/quit;username")
				if (x.startsWith("/quit")) {
					client.close();
					for (int i = 0; i < threads.length; i++) {
						if (threads[i] == this) {
							threads[i] = null;
						}
					}
					aktiveNutzer.remove(x.substring(x.indexOf(";") + 1));
					sendeAktiveBenutzer();
					break;
				}

				// Betritt zum Chat ("/join;username")
				else if (x.startsWith("/join")) {
					final String[] split = x.split(";");
					aktiveNutzer.add(split[1]);
					sendeAktiveBenutzer();
				}

				// Login auf dem Chatserver ("/login;username;password")
				else if (x.startsWith("/login")) {
					final String[] split = x.split(";");
					final boolean erfolg = Benutzerliste.getInstance().pruefeLogin(split[1], split[2]);
					if (erfolg) {
						out.writeObject(Benutzerliste.getInstance().getPfadExp(split[1]));
					}
					out.writeObject(erfolg);
				}

				// Match-Server erstellen ("/createServer;name")
				else if (x.startsWith("/createServer")) {
					final String[] split = x.split(";");
					new Thread() {
						@Override
						public void run() {
							final Server server = new Server(0);
							final int port = server.getPort();
							aktiveServer.put(split[1], port + "");
							try {
								sendeAktiveServer();
								out.writeObject("/joinMe;" + aktiveServer.get(split[1]));
								server.startServing();
								server.closeConnection();
							} catch (ClassNotFoundException | IOException e) {
								e.printStackTrace();
								try {
									out.writeObject("/fehler;" + e.getMessage());
								} catch (final IOException e1) {
									e1.printStackTrace();
								}
							}
						};
					}.start();
				}

				// Gegen einen Bot spielen ("/createBotServer;name")
				else if (x.startsWith("/createBotServer")) {
					new Thread() {
						@Override
						public void run() {
							final Server server = new Server(0, true);
							final int port = server.getPort();
							try {
								out.writeObject("/joinMe;" + port);
								server.startServing();
								server.closeConnection();
							} catch (ClassNotFoundException | IOException e) {
								e.printStackTrace();
								try {
									out.writeObject("/fehler;" + e.getMessage());
								} catch (final IOException e1) {
									e1.printStackTrace();
								}
							}
						};
					}.start();
				}

				// Match-Server entfernen ("/removeServer;name")
				else if (x.startsWith("/removeServer")) {
					aktiveServer.remove(x.split(";")[1]);
					sendeAktiveServer();
				}

				// Liste der aktiven Server anfordern ("/requestServer")
				else if (x.startsWith("/requestServer")) {
					out.writeObject(aktiveServer);
				}

				// Chatnachricht
				else {
					for (final ChatServerThread t : threads) {
						if (t != null) {
							t.getOut().writeObject(x);
						}
					}
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Teilt allen Clients mit, wer gerade online ist.
	 *
	 * @throws IOException
	 */
	private void sendeAktiveBenutzer() throws IOException {
		for (final ChatServerThread t : threads) {
			if (t != null) {
				t.getOut().reset();
				t.getOut().writeObject(aktiveNutzer);
			}
		}
	}

	/**
	 * Teilt allen Clients mit, welche Match-Server gerade offen sind.
	 *
	 * @throws IOException
	 */
	private void sendeAktiveServer() throws IOException {
		for (final ChatServerThread t : threads) {
			if (t != null) {
				t.getOut().reset();
				t.getOut().writeObject(aktiveServer);
			}
		}
	}

	/**
	 * Holt sich In- und Outputstream zur Kommunikation mit dem Client.
	 *
	 * @throws IOException
	 */
	private void getStreams() throws IOException {
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
	}

	/**
	 * @return the client
	 */
	public Socket getClient() {
		return client;
	}

	/**
	 * @return the in
	 */
	public ObjectInputStream getIn() {
		return in;
	}

	/**
	 * @return the out
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

}
