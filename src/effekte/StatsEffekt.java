package effekte;

import model.Effekt;
import model.Spieler;

/**
 * Ein {@link Effekt}, der den ATK- und/oder den DEF-Boost des Spielers jede Runde auf einen festen Wert setzt.
 *
 * @author Roman Schmidt
 *
 */
public class StatsEffekt extends Effekt {

	/**
	 *
	 */
	private static final long serialVersionUID = -5578734586929159959L;

	/** Wird als ATK- oder DEF-Boost angegeben, wenn der ensprechende Wert nicht geändert werden soll */
	public static final int NO_EFFECT = 88;

	/** ATK-Boost */
	private int atk;

	/** DEF-Boost */
	private int def;

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 * @param atk
	 *            ATK-Boost
	 * @param def
	 *            DEF-Boost
	 */
	public StatsEffekt(final String iconPath, final int atk, final int def) {
		super(iconPath);
		this.atk = atk;
		this.def = def;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#ausfuehren(model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ziel) {
		if (atk != NO_EFFECT) {
			ziel.setAtkBoost(atk);
		}
		if (def != NO_EFFECT) {
			ziel.setDefBoost(def);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#entfernbarDurchEntwaffnen()
	 */
	@Override
	public boolean entfernbarDurchEntwaffnen() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#entfernbarDurchSegen()
	 */
	@Override
	public boolean entfernbarDurchSegen() {
		return false;
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
		final StatsEffekt other = (StatsEffekt) obj;
		if (atk != other.atk) {
			return false;
		}
		if (def != other.def) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (atk != NO_EFFECT) {
			builder.append("Atk = " + atk + " ");
		}
		if (def != NO_EFFECT) {
			builder.append("Def = " + def);
		}
		return builder.toString();
	}
}
