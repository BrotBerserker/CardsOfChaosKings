package karten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die den Atk- oder Def-Boost erhöht oder vermindert.
 *
 * @author Roman Schmidt
 *
 */
// TODO StatsBooster und StatsReducer zusammenfassen
public class StatsBoosterKarte extends Karte {

	private static final long serialVersionUID = -8702492231587178153L;

	/** Der Atk-Boost */
	private int atkBoost;

	/** Der Def-Boost */
	private int defBoost;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 * @param atkBoost
	 *            Atk-Boost
	 * @param defBoost
	 *            Def-Boost
	 */
	public StatsBoosterKarte(final String name, final double maechtigkeit, final String imgPath, final int atkBoost, final int defBoost) {
		super(name, maechtigkeit, imgPath);
		this.atkBoost = atkBoost;
		this.defBoost = defBoost;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final int min = ausfuehrer.getMinBoost();
		if (atkBoost + min > ausfuehrer.getAtkBoost()) {
			ausfuehrer.setAtkBoost(ausfuehrer.getAtkBoost() + atkBoost);
		}
		if (defBoost + min > ausfuehrer.getDefBoost()) {
			ausfuehrer.setDefBoost(ausfuehrer.getDefBoost() + defBoost);
		}
	}

	/**
	 * @return the atkBoost
	 */
	public int getAtkBoost() {
		return atkBoost;
	}

	/**
	 * @return the defBoost
	 */
	public int getDefBoost() {
		return defBoost;
	}

	@Override
	public String toString() {
		String s = "";
		if (atkBoost > 0) {
			s = "Erhöht die Atk um " + atkBoost;
			if (defBoost > 0) {
				s += " und die Def um " + defBoost;
			}
		} else {
			s = "Erhöht die Def um " + defBoost;
		}
		return s;
	}
}
