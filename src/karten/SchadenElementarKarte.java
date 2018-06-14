/**
 *
 */
package karten;

import model.EffektKonstanten;
import model.Karte;
import model.Spieler;
import effekte.LebensEffekt;
import effekte.TimedEffekt;

/**
 * Eine {@link Karte}, die dem Gegner einen Schadenseffekt für mehrere Runden gibt.
 *
 * @author Sigi
 *
 */
public class SchadenElementarKarte extends Karte {

	private static final long serialVersionUID = -1259483849522845477L;

	/** Schaden pro Runde */
	private int schaden;

	/** Anzahl Runden */
	private int runden;

	/**
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 * @param schaden
	 *            Der Schaden pro Runde
	 * @param runden
	 *            Anzahl Runden
	 */
	public SchadenElementarKarte(final String name, final double maechtigkeit, final String imgPath, final int schaden, final int runden) {
		super(name, maechtigkeit, imgPath);
		this.schaden = schaden;
		this.runden = runden;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final TimedEffekt tee = new TimedEffekt(new LebensEffekt("images/effektIcons/fireIcon.png", -schaden, false, false), runden, EffektKonstanten.ELEMENTAR_EFFEKT);
		gegner.addEffektNachDemZug(tee);
	}

	@Override
	public String toString() {
		return "[Elementar] Verursacht " + runden + " Runden lang " + schaden + " Schaden pro Runde";
	}

}
