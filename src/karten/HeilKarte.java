package karten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die den Spieler heilt.
 *
 * @author Roman Schmidt
 *
 */
public class HeilKarte extends Karte {

	private static final long serialVersionUID = 1587278593384519627L;

	/** HP, die geheilt werden */
	private int heilwert;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param heilwert
	 *            HP, die geheilt werden
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 */
	public HeilKarte(final String name, final double maechtigkeit, final int heilwert, final String imgPath) {
		super(name, maechtigkeit, imgPath);
		this.heilwert = heilwert;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setHp(ausfuehrer.getHp() + heilwert);
	}

	/**
	 * @return the heilwert
	 */
	public int getHeilwert() {
		return heilwert;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Heilt sofort um " + heilwert;
	}

}
