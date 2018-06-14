package main;

import networking.ChatServer;

/**
 * Enthält die Main zum Starten des Chatservers.
 * 
 * @author RomSch
 *
 */
public class MainChatServer {

	/**
	 * Startet den Hauptchatserver.
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println("-Chat Server started-");
		new ChatServer(2345).start();
	}
}
