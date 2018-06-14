/**
 *
 */
package model;

import java.io.Serializable;

import effekte.TimedEffekt;

/**
 * Interface, mit einer Methode, die aufgerufen werden kann, wenn ein {@link TimedEffekt} abläuft.
 *
 * @author Sigi
 *
 */
public interface IEffektNachwirkung extends Serializable {

	/**
	 * Wird bei Ablauf eines {@link TimedEffekt} aufgerufen.
	 *
	 * @param ziel
	 *            Der Spieler, auf dem der Effekt liegt
	 */
	public void ausfuehren(Spieler ziel);

}
