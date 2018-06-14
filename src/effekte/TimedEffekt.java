/**
 *
 */
package effekte;

import model.Effekt;
import model.EffektKonstanten;
import model.IEffektNachwirkung;
import model.Spieler;

/**
 * Ein {@link Effekt}, der nur eine bestimmte Anzahl von Runden aktiv ist. Diese Klasse ist ein Wrapper, in den andere
 * Effekte reingestopft werden können, damit diese nach einer bestimmten Zeit aufhören zu wirken.
 *
 * @author Sigi
 *
 */
public class TimedEffekt extends Effekt {

	private static final long serialVersionUID = -5869213622498165355L;

	/** Der Zeitlich begrenzte Effekt */
	private Effekt effekt;

	/** Die Anzahl der Runden, nach der der Effekt ausläuft */
	private int anzahlRunden;

	/** Gibt an, ob der Effekt noch aktiv oder schon ausgelaufen ist */
	private boolean aktiv;

	/** Wird aufgerufen, wenn der Effekt ausläuft */
	private IEffektNachwirkung nachwirkung;

	/**
	 * Constructor.
	 *
	 * @param effekt
	 *            Der eigentliche Effekt
	 * @param anzahlRunden
	 *            Die Anzahl der Runden
	 */
	public TimedEffekt(final Effekt effekt, final int anzahlRunden) {
		this(effekt, anzahlRunden, null, null);
	}

	/**
	 * Constructor.
	 *
	 * @param effekt
	 *            Der eigentliche Effekt
	 * @param anzahlRunden
	 *            Die Anzahl der Runden
	 * @param nachwirkung
	 *            Die Nachwirkung, die am Ende des TimedEffekts auftreten soll
	 */
	public TimedEffekt(final Effekt effekt, final int anzahlRunden, final IEffektNachwirkung nachwirkung) {
		this(effekt, anzahlRunden, null, nachwirkung);
	}

	/**
	 * Constructor.
	 *
	 * @param effekt
	 *            Der eigentliche Effekt
	 * @param anzahlRunden
	 *            Die Anzahl der Runden
	 * @param tag
	 *            Markiert den Effekt mit einem Tag, siehe dazu {@link EffektKonstanten}
	 */
	public TimedEffekt(final Effekt effekt, final int anzahlRunden, final String tag) {
		this(effekt, anzahlRunden, tag, null);
	}

	/**
	 * Constructor.
	 *
	 * @param effekt
	 *            Der eigentliche Effekt
	 * @param anzahlRunden
	 *            Die Anzahl der Runden
	 * @param tag
	 *            Markiert den Effekt mit einem Tag, siehe dazu {@link EffektKonstanten}
	 * @param nachwirkung
	 *            Die Nachwirkung, die am Ende des TimedEffekts auftreten soll
	 */
	public TimedEffekt(final Effekt effekt, final int anzahlRunden, final String tag, final IEffektNachwirkung nachwirkung) {
		super(tag, effekt.getIconPath());
		this.effekt = effekt;
		this.anzahlRunden = anzahlRunden;
		this.nachwirkung = nachwirkung;
		aktiv = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Effekt#ausfuehren(model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ziel) {
		effekt.ausfuehren(ziel);
		anzahlRunden--;
		if (anzahlRunden <= 0) {
			aktiv = false;
			if (nachwirkung != null) {
				nachwirkung.ausfuehren(ziel);
			}
		}
	}

	/**
	 * Führt manuell die Nachwirkung dieses Effekts aus.
	 *
	 * @param ziel
	 *            Spieler, den die Nachwirkung treffen soll
	 */
	public void nachwirkungAusfuehren(final Spieler ziel) {
		if (nachwirkung != null && ziel != null) {
			nachwirkung.ausfuehren(ziel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Effekt#entfernbarDurchEntwaffnen()
	 */
	@Override
	public boolean entfernbarDurchEntwaffnen() {
		return effekt.entfernbarDurchEntwaffnen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Effekt#entfernbarDurchSegen()
	 */
	@Override
	public boolean entfernbarDurchSegen() {
		return effekt.entfernbarDurchSegen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Effekt#istAktiv()
	 */
	@Override
	public boolean istAktiv() {
		return aktiv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimedEffekt other = (TimedEffekt) obj;
		if (aktiv != other.aktiv) {
			return false;
		}
		// if (anzahlRunden != other.anzahlRunden) {
		// return false;
		// }
		if (effekt == null) {
			if (other.effekt != null) {
				return false;
			}
		} else if (!effekt.equals(other.effekt)) {
			return false;
		}
		if (nachwirkung == null) {
			if (other.nachwirkung != null) {
				return false;
			}
		}
		// else if (!nachwirkung.equals(other.nachwirkung)) {
		// return false;
		// }
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + effekt.toString() + " (" + anzahlRunden + " Runden)";
	}

}
