package benutzermanagement;

import java.util.HashMap;
import java.util.Map;

import model.Pfad;

/**
 * Repräsentiert einen Benutzer für den Multiplayer. Besitzt Nickname und Passwort fürs Login und eine Map, die jedem
 * {@link Pfad} einen Exp-Wert zuordnet.
 *
 * @author Roman Schmidt
 *
 */
public class Benutzer {

	private String nickname;
	private String passwort;
	private Map<String, Integer> pfadExp = new HashMap<String, Integer>();

	/**
	 * @param nickname
	 *            Nickname zum einloggen
	 * @param passwort
	 *            PW zum einloggen
	 * @param pfadExp
	 *            Pfad-Exp Map
	 */
	public Benutzer(final String nickname, final String passwort, final Map<String, Integer> pfadExp) {
		this.nickname = nickname;
		this.passwort = passwort;
		this.pfadExp = pfadExp;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * @param passwort
	 *            the passwort to set
	 */
	public void setPasswort(final String passwort) {
		this.passwort = passwort;
	}

	/**
	 * @return the exp
	 */
	public Map<String, Integer> getPfadExp() {
		return pfadExp;
	}

	/**
	 * Fügt diesem Spieler bei einem Pfad eine Exp-Menge hinzu.
	 *
	 * @param pfad
	 *            Name des Pfads
	 * @param exp
	 *            Erhaltene Exp
	 */
	public void addExpToPfad(final String pfad, final int exp) {
		pfadExp.put(pfad, pfadExp.get(pfad) + exp);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Benutzer [nickname=" + nickname + ", passwort=" + passwort + ", exp=" + pfadExp + "]";
	}

}
