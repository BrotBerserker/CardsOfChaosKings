package karten;

import model.EffektKonstanten;
import model.IEffektNachwirkung;
import model.Karte;
import model.Spieler;
import effekte.StatsEffekt;
import effekte.TimedEffekt;

/**
 * Eine {@link Karte}, die den Spieler oder den Gegner mit einem Elementareffekt belegt, der den Atk- oder den Def-Boost
 * eine bestimmte Anzahl Runden jede Runde auf einen bestimmten Wert setzt.
 *
 * @author Roman Schmidt
 *
 */
public class StatsElementarKarte extends Karte {

	private static final long serialVersionUID = -7850438844911153821L;

	/** True, wenn der Spieler Ziel des Effekts ist */
	private boolean spielerIstZiel;

	/** Atk-Boost */
	private int atk;

	/** Def-Boost */
	private int def;

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
	 * @param atk
	 *            Atk-Boost
	 * @param def
	 *            Def-Boost
	 * @param runden
	 *            Anzahl Runden
	 * @param spieler
	 *            true, falls der Spieler das Ziel ist
	 */
	public StatsElementarKarte(final String name, final double maechtigkeit, final String imgPath, final int atk, final int def, final int runden, final boolean spieler) {
		super(name, maechtigkeit, imgPath);
		spielerIstZiel = spieler;
		this.atk = atk;
		this.def = def;
		this.runden = runden;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final int oldAtk;
		final int oldDef;

		final Spieler ziel = spielerIstZiel ? ausfuehrer : gegner;

		oldAtk = ziel.getAtkBoost();
		oldDef = ziel.getDefBoost();
		final TimedEffekt te = createTimedEffekt(oldAtk, oldDef);
		if (!ziel.hatEffektVorDemZug(te)) {
			ziel.addEffektVorDemZug(te);
		}

		// if (spielerIstZiel) {
		// oldAtk = ausfuehrer.getAtkBoost();
		// oldDef = ausfuehrer.getDefBoost();
		// final TimedEffekt te = createTimedEffekt(oldAtk, oldDef);
		// if (!ausfuehrer.hatEffektNachDemZug(te)) {
		// ausfuehrer.addEffektNachDemZug(te);
		// }
		// } else {
		// oldAtk = gegner.getAtkBoost();
		// oldDef = gegner.getDefBoost();
		// final TimedEffekt te = createTimedEffekt(oldAtk, oldDef);
		// if (!gegner.hatEffektVorDemZug(te)) {
		// gegner.addEffektVorDemZug(te);
		// }
		// }
	}

	/**
	 * Erstellt einen {@link TimedEffekt}, der einen {@link StatsEffekt} laufen lässt und wenn er ausläuft, die Stats
	 * des Ziels wieder auf die ursprünglichen Werte setzt.
	 *
	 * @param oldAtk
	 *            Ursprünglicher Atk-Boost
	 * @param oldDef
	 *            Ursprünglicher Def-Boost
	 * @return Der TimedEffekt
	 */
	private TimedEffekt createTimedEffekt(final int oldAtk, final int oldDef) {
		final TimedEffekt te = new TimedEffekt(new StatsEffekt("images/effektIcons/lightningIcon.png", atk, def), runden, EffektKonstanten.ELEMENTAR_EFFEKT, new IEffektNachwirkung() {
			private static final long serialVersionUID = -3858370518306943534L;

			@Override
			public void ausfuehren(final Spieler ziel) {
				ziel.setAtkBoost(oldAtk);
				ziel.setDefBoost(oldDef);
			}
		});
		return te;
	}

	@Override
	public String toString() {
		String s = "[Elementar] ";
		if (atk != StatsEffekt.NO_EFFECT) {
			s += "Setzt die Atk des " + (atk > 0 ? "Spielers" : "Gegners") + " für " + runden + " Runden auf " + atk;
		}
		if (def != StatsEffekt.NO_EFFECT) {
			s += "Setzt die Def des " + (def > 0 ? "Spielers" : "Gegners") + " für " + runden + " Runden auf " + def;
		}
		return s;
	}
}
