package ssjkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die die aktuellen SSJ-Punkte des Spielers erh�ht.
 *
 * @author RomSch
 *
 */
public class SSJBoostKarte extends Karte {

	private static final long serialVersionUID = -174322476274385943L;

	/** Wert, um den die SSJ-Punkte erh�ht werden */
	private int boost;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            M�chtigkeit der Karte
	 * @param boost
	 *            Wert, um den die SSJ-Punkte erh�ht werden
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 */
	public SSJBoostKarte(final String name, final double maechtigkeit, final int boost, final String imgPath) {
		super(name, maechtigkeit, imgPath);
		this.boost = boost;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setSsjPoints(ausfuehrer.getSsjPoints() + boost);
	}

	@Override
	public String toString() {
		return "Erh�ht die SSJ-Punkte sofort um " + boost;
	}
}
