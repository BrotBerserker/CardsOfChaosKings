/*
 * Autor: Roman S.
 * Aufgabe: 
 * Datum: 13.09.2013
 * Status: in Arbeit
 */
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import ki.GreedyKi;
import ki.IKi;
import model.Spieler;
import model.SpielerListe;
import benutzermanagement.Benutzerliste;

public class Server {

	private ServerSocket server;
	private Socket client;
	private int spielerAnDerReihe = 1;
	private PlayerThread[] players = new PlayerThread[2];
	private Spieler model0;
	private Spieler model1;

	private boolean vsBot = false;

	private IKi ki;

	public Server(final int port) {
		try {
			server = new ServerSocket(port);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public Server(final int port, boolean vsBot) {
		this.vsBot = vsBot;
		try {
			server = new ServerSocket(port);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return server.getLocalPort();
	}

	public void startServing() throws IOException, ClassNotFoundException {
		System.out.println("-Server started. Port: " + server.getLocalPort() + "-");
		initializePlayer1();
		initializePlayer2();
		sendeModelsAnClients();
		spielerAnDerReihe = new Random().nextInt(2) + 1;
		try {
			while (true) {
				if (spielerAnDerReihe == 1) {
					spielzugSpieler1();
				} else if (spielerAnDerReihe == 2) {
					spielzugSpieler2();
				} else if (spielerAnDerReihe == 0) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Gegner hat die Verbindung unterbrochen!");
		} finally {
			// closeConnection();
		}
	}

	private void initializePlayer2() throws IOException {
		if (!vsBot) {
			client = server.accept();
			players[1] = new PlayerThread(client, players, true);
			players[1].out.writeObject("Deck auswaehlen!");
			String spielerStr = players[1].in.readLine();
			String spielerName = players[1].in.readLine();
			//int spielerIndex = Integer.parseInt(spielerStr);
			model1 = readSpieler(spielerStr);
			model1.setSpielerName(spielerName);
		} else {
			ki = new GreedyKi();
			players[0].out.writeObject("Deck auswaehlen!");
			String spielerStr = players[0].in.readLine();
			players[0].in.readLine();// muss gemacht werden, weil normalerweise
										// der Spielername mitübergeben wird
			//int spielerIndex = Integer.parseInt(spielerStr);
			model1 = readSpieler(spielerStr);
			model1.setSpielerName("Rüdiger (Bot)");
		}
		model1.setGegner(model0);
		model0.setGegner(model1);
	}

	private void initializePlayer1() throws IOException {
		client = server.accept();
		players[0] = new PlayerThread(client, players, true);
		System.out.println("vorher");
		players[0].out.writeObject("Deck auswaehlen!");
		String spielerStr = players[0].in.readLine();
		String spielerName = players[0].in.readLine();
		//int spielerIndex = String.parseInt(spielerStr);
		model0 = readSpieler(spielerStr);
		model0.setSpielerName(spielerName);
		System.out.println("vorher21");
	}

	private void spielzugSpieler2() throws IOException {
		model1.vorDemSpielzug();
		sendeModelsAnClients();
		int index;
		if (!vsBot) {
			players[1].out.writeObject("Bitte Karte spielen!");
			String line = players[1].in.readLine();
			index = Integer.parseInt(line);
		} else {
			index = ki.nextCard(model1, model0);
		}
		sendeKarteAnSpieler1(index);
		// spieleKarteSpieler2(index);
		model1.spieleKarte(index);
		model1.zieheKarte();
		model1.nachDemSpielzug();
		sendeModelsAnClients();
		siegNiederlageOderWeiter();
	}

	private void spielzugSpieler1() throws IOException {
		model0.vorDemSpielzug();
		sendeModelsAnClients();
		players[0].out.writeObject("Bitte Karte spielen!");
		String line = players[0].in.readLine();
		int index = Integer.parseInt(line);
		if (!vsBot) {
			sendeKarteAnSpieler2(index);
		}
		// spieleKarteSpieler1(index);
		model0.spieleKarte(index);
		model0.zieheKarte();
		model0.nachDemSpielzug();
		sendeModelsAnClients();
		siegNiederlageOderWeiter();
	}

	private void siegNiederlageOderWeiter() throws IOException {
		if (model1.getHp() <= 0 && model0.getHp() <= 0) {
			players[0].out.writeObject("UNENTSCHIEDEN");
			if (!vsBot) {
				players[1].out.writeObject("UNENTSCHIEDEN");
			}
			Benutzerliste.getInstance().addExp(model0.getSpielerName(), Benutzerliste.EXP_DRAW, SpielerListe.getPfadByKlassenName(model0.getKlassenName()));
			if (!vsBot) {
				Benutzerliste.getInstance().addExp(model1.getSpielerName(), Benutzerliste.EXP_DRAW, SpielerListe.getPfadByKlassenName(model1.getKlassenName()));
			}
			spielerAnDerReihe = 0;
		} else if (model0.getHp() <= 0) {
			players[0].out.writeObject("NIEDERLAGE");
			if (!vsBot) {
				players[1].out.writeObject("SIEG");
			}
			Benutzerliste.getInstance().addExp(model0.getSpielerName(), Benutzerliste.EXP_LOSE, SpielerListe.getPfadByKlassenName(model0.getKlassenName()));
			if (!vsBot) {
				Benutzerliste.getInstance().addExp(model1.getSpielerName(), Benutzerliste.EXP_WIN, SpielerListe.getPfadByKlassenName(model1.getKlassenName()));
			}
			spielerAnDerReihe = 0;
		} else if (model1.getHp() <= 0) {
			players[0].out.writeObject("SIEG");
			if (!vsBot) {
				players[1].out.writeObject("NIEDERLAGE");
			}
			Benutzerliste.getInstance().addExp(model0.getSpielerName(), Benutzerliste.EXP_WIN, SpielerListe.getPfadByKlassenName(model0.getKlassenName()));
			if (!vsBot) {
				Benutzerliste.getInstance().addExp(model1.getSpielerName(), Benutzerliste.EXP_LOSE, SpielerListe.getPfadByKlassenName(model1.getKlassenName()));
			}
			spielerAnDerReihe = 0;
		} else {
			if (spielerAnDerReihe == 1) {
				spielerAnDerReihe = 2;
			} else {
				spielerAnDerReihe = 1;
			}
		}
	}

	private void sendeKarteAnSpieler2(final int index) throws IOException {
		players[1].out.reset();
		players[1].out.writeObject(index);
	}

	private void sendeKarteAnSpieler1(final int index) throws IOException {
		players[0].out.reset();
		players[0].out.writeObject(index);
	}

	private void sendeModelsAnClients() throws IOException {
		players[0].out.reset();
		players[0].out.writeObject("Spieler kommt!");
		players[0].out.reset();
		players[0].out.writeObject(model0);
		players[0].out.reset();
		players[0].out.writeObject("Gegner kommt!");
		players[0].out.reset();
		players[0].out.writeObject(model1);

		if (!vsBot) {
			players[1].out.reset();
			players[1].out.writeObject("Spieler kommt!");
			players[1].out.reset();
			players[1].out.writeObject(model1);
			players[1].out.reset();
			players[1].out.writeObject("Gegner kommt!");
			players[1].out.reset();
			players[1].out.writeObject(model0);
		}
	}

	public void closeConnection() throws IOException {
		new Thread() {
			@Override
			public void run() {
				try {
					players[0].in.close();
					players[0].out.close();
					players[1].in.close();
					players[1].out.close();
				} catch (Exception e) {
				}
			}
		}.start();
		server.close();
		server = null;
		System.out.println("-Server closed-");
	}

	private Spieler readSpieler(final String key) {
		return SpielerListe.getSpieler(key);
	}
}

class PlayerThread {

	// public PlayerThread[] players;
	public BufferedReader in;
	public ObjectOutputStream out;

	public PlayerThread(final Socket client, final PlayerThread[] players, final boolean istAmZug) {
		// this.players = players;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new ObjectOutputStream(client.getOutputStream());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
