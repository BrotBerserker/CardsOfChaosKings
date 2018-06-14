package specialkarten;

import model.IEffektNachwirkung;
import model.Karte;
import model.Spieler;
import effekte.InfoEffekt;
import effekte.TimedEffekt;

/**
 * Eine {@link Karte}, mit der der Spieler für eine bestimmte Anzahl Runden die Karten des Gegners sehen kann.
 *
 * @author Roman Schmidt
 *
 */
public class Einsicht extends Karte {

	private static final long serialVersionUID = 3527223152979131783L;

	/** Anzahl der Runden, die man die Karten des Gegners sehen kann */
	public static final int ANZAHL_RUNDEN = 3;

	/**
	 * Constructor.
	 *
	 */
	public Einsicht() {
		super("Einsicht", Karte.SPECIALKARTEN_MAECHTIGKEIT, "Einsicht.jpg");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final TimedEffekt ee = new TimedEffekt(new InfoEffekt("images/effektIcons/lightningIcon.png", "Sieht Gegnerkarten"), 3, new IEffektNachwirkung() {
			private static final long serialVersionUID = -6914101142920109050L;

			@Override
			public void ausfuehren(final Spieler ziel) {
				ziel.setSiehtGegnerkarten(false);
			}
		});
		if (!ausfuehrer.hatEffektVorDemZug(ee)) {
			ausfuehrer.setSiehtGegnerkarten(true);
			ausfuehrer.addEffektVorDemZug(ee);
		}
	}

	@Override
	public String toString() {
		return "Offenbart für " + ANZAHL_RUNDEN + " Runden die Handkarten des Gegners";
	}

}
