package benutzermanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton. Beinhaltet eine Liste aller aktuell registrierten Benutzer. Die Benutzer werden im Konstruktor aus einer
 * Textdatei gelesen. //TODO Datenbank statt Textdatei, nicht alle Benutzer auf einmal lesen.
 *
 * @author Roman Schmidt
 *
 */
public class Benutzerliste {

	private List<Benutzer> benutzers;
	private static Benutzerliste instance;
	private TextFileHandler handler;

	/** Exp, die man bei einem Sieg erhält. */
	public static final int EXP_WIN = 3;

	/** Exp, die man bei einer Niderlage erhält. */
	public static final int EXP_LOSE = 1;

	/** Exp, die man bei einem Unentschieden erhält. */
	public static final int EXP_DRAW = 2;

	/**
	 * @throws IOException
	 *
	 */
	private Benutzerliste() {
		try {
			handler = new TextFileHandler();
			benutzers = handler.readBenutzers();
		} catch (final IOException e) {
			benutzers = new ArrayList<Benutzer>();
		}
	}

	/**
	 * Gibt die einzige Instanz dieser zurück.
	 *
	 * @return Die Instanz
	 */
	public static Benutzerliste getInstance() {
		if (instance == null) {
			instance = new Benutzerliste();
		}
		return instance;
	}

	/**
	 * Fügt einen neuen Benutzer hinzu und schreibt ihn in die Textdatei.
	 *
	 * @param neuerBenutzer
	 *            Der neue Benutzer
	 * @throws IllegalArgumentException
	 *             Geworfen, falls der Nickname des neuen Benutzers schon existiert.
	 * @throws IOException
	 *             Geworfen, falls das Schreiben in die Datei fehlschlägt.
	 */
	public void addBenutzer(final Benutzer neuerBenutzer) throws IllegalArgumentException, IOException {
		for (final Benutzer benutzer : benutzers) {
			if (benutzer.getNickname().equals(neuerBenutzer.getNickname())) {
				throw new IllegalArgumentException("Benutzer schon vorhanden!");
			}
		}
		benutzers.add(neuerBenutzer);
		handler.writeBenutzers(benutzers);
	}

	/**
	 * Gibt die Exp-Map eines Benutzers mit einem bestimmten Nicknamen zurück. In der Map steht als Key der Pfad und den
	 * dazugehörigen Exp als Value.
	 *
	 * @param nickname
	 *            Nickname des Nutezrs
	 * @return Die Map
	 */
	public Map<String, Integer> getPfadExp(final String nickname) {
		for (final Benutzer benutzer : benutzers) {
			if (benutzer.getNickname().equals(nickname)) {
				return benutzer.getPfadExp();
			}
		}
		return new HashMap<String, Integer>();
	}

	/**
	 * Fügt einem Benutzer Exp für einen bestimmten Pfad hinzu und schreibt die neuen Exp in die Textdatei.
	 *
	 * @param nickname
	 *            Der Nickname des Benutzers
	 * @param exp
	 *            Die erhaltenen Exp
	 * @param pfad
	 *            Der Pfad
	 * @throws IOException
	 *             Geworfen falls das Schreiben fehlschlägt.
	 */
	public void addExp(final String nickname, final int exp, final String pfad) throws IOException {
		for (final Benutzer benutzer : benutzers) {
			if (benutzer.getNickname().equals(nickname)) {
				benutzer.addExpToPfad(pfad, exp);
			}
		}
		handler.writeBenutzers(benutzers);
	}

	/**
	 * Prüft, ob das Passwort zu dem Nickname passt.
	 * 
	 * @param user
	 *            Nickname
	 * @param pw
	 *            Passwort
	 * @return True, falls es passt
	 */
	public boolean pruefeLogin(final String user, final String pw) {
		for (final Benutzer benutzer : benutzers) {
			if (benutzer.getNickname().equals(user) && benutzer.getPasswort().equals(pw)) {
				return true;
			}
		}
		return false;
	}

}
