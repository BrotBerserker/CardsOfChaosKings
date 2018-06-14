package karten;

import model.EffektKonstanten;
import model.Karte;
import model.Spieler;
import effekte.LebensEffekt;
import effekte.TimedEffekt;

/**
 * Eine {@link Karte}, die dem Spieler einen Heilungseffekt für mehrere Runden gibt.
 *
 * @author Roman Schmidt
 *
 */
public class RegElementarKarte extends Karte {

	private static final long serialVersionUID = 5044823758079231542L;

	/** HP, die pro Runde geheilt werden */
	private int heilung;

	/** Anzahl der Runden */
	private int runden;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 * @param heilung
	 *            HP, die pro Runde geheilt werden
	 * @param runden
	 *            Anzahl der Runden
	 */
	public RegElementarKarte(final String name, final double maechtigkeit, final String imgPath, final int heilung, final int runden) {
		super(name, maechtigkeit, imgPath);
		this.heilung = heilung;
		this.runden = runden;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final TimedEffekt tee = new TimedEffekt(new LebensEffekt("images/effektIcons/liferegeneration.png", heilung, false, false), runden, EffektKonstanten.ELEMENTAR_EFFEKT);
		ausfuehrer.addEffektNachDemZug(tee);
	}

	@Override
	public String toString() {
		return "[Elementar] Regeneriert " + runden + " Runden lang " + heilung + " HP pro Runde";
	}

}
