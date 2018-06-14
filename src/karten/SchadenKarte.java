package karten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die Schaden beim Gegner verursacht.
 *
 * @author Roman Schmidt
 *
 */
public class SchadenKarte extends Karte {

	private static final long serialVersionUID = -8874268330982446003L;

	/** Der Schaden */
	private int schaden;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 * @param schaden
	 *            Der Schaden
	 */
	public SchadenKarte(final String name, final double maechtigkeit, final int schaden, final String imgPath) {
		super(name, maechtigkeit, imgPath);
		this.schaden = schaden;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		if (gegner.isKontertDenNaechsten()) {
			ausfuehrer.setHp(ausfuehrer.getHp() - schaden);
			gegner.setKontertDenNaechsten(false);
		} else {
			gegner.setHp(gegner.getHp() + gegner.getDefBoost() - schaden - ausfuehrer.getAtkBoost());
		}
	}

	/**
	 * @return the schaden
	 */
	public int getSchaden() {
		return schaden;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fügt sofort " + schaden + " Schaden zu";
	}

}
