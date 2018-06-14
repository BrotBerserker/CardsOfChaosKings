package ki;

import model.Spieler;

/**
 * KI-Interface.
 *
 * @author Roman Schmidt
 *
 */
public interface IKi {

	/**
	 * Gibt der Index der als nächstes zu spielenden Handkarte zurück.
	 *
	 * @param spieler
	 *            Der Spieler
	 * @param gegner
	 *            Der Gegener
	 * @return Der Index
	 */
	public abstract int nextCard(Spieler spieler, Spieler gegner);

}