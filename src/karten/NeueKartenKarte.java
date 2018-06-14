package karten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die den Spieler neue Karten ziehen lässt.
 *
 * @author Roman Schmidt
 *
 */
public class NeueKartenKarte extends Karte {

	private static final long serialVersionUID = 5505988617886134317L;

	/**
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 */
	public NeueKartenKarte(final String name, final double maechtigkeit, final String imgPath) {
		super(name, maechtigkeit, imgPath);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		// for (int i = 0; i < ausfuehrer.getHand().length; i++) {
		// ausfuehrer.getHand()[i] = null;
		// ausfuehrer.zieheKarte();
		// }
		ausfuehrer.zieheNeueHand();
	}

	@Override
	public String toString() {
		return "Zieht sofort " + Spieler.ANZAHL_HANDKARTEN + " neue Handkarten";
	}
}
