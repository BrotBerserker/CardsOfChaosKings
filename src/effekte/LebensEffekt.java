package effekte;

import model.Effekt;
import model.Spieler;
import specialkarten.Entwaffnen;
import specialkarten.Segen;

/**
 * Ein {@link Effekt}, der die HP des betroffenen Spielers jede Runde um einen bestimmten Betrag erhöht oder verringert.
 *
 * @author Roman Schmidt
 *
 */
public class LebensEffekt extends Effekt {

	private static final long serialVersionUID = -2742575457248490143L;

	/** Anzahl der HP, die jede Runde auf die HP des Spielers addiert werden (kann negativ sein) */
	private int lebenseffekt;

	/** Gibt an, ob dieser Effekt durch {@link Entwaffnen} entfernt werden kann */
	private boolean durchEntwaffnen;

	/** Gibt an, ob dieser Effekt durch {@link Segen} entfernt werden kann */
	private boolean durchSegen;

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 * @param lebenseffekt
	 *            Anzahl der Lebenspunkte, die pro Runde addiert werden (kann negativ sein)
	 */
	public LebensEffekt(final String iconPath, final int lebenseffekt) {
		this(iconPath, lebenseffekt, true, true);
	}

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 * @param lebenseffekt
	 *            Anzahl der Lebenspunkte, die pro Runde addiert werden (kann negativ sein)
	 * @param entfernbarDurchEntwaffnen
	 *            Ob der Effekt durch {@link Entwaffnen} entfernt werden kann
	 * @param entfernbarDurchSegen
	 *            Ob der Effekt durch {@link Segen} entfernt werden kann
	 */
	public LebensEffekt(final String iconPath, final int lebenseffekt, final boolean entfernbarDurchEntwaffnen, final boolean entfernbarDurchSegen) {
		super(iconPath);
		durchEntwaffnen = entfernbarDurchEntwaffnen;
		durchSegen = entfernbarDurchSegen;
		this.lebenseffekt = lebenseffekt;
	}

	/**
	 * @return the lebenseffekt
	 */
	public int getLebenseffekt() {
		return lebenseffekt;
	}

	/**
	 * @param lebenseffekt
	 *            the lebenseffekt to set
	 */
	public void setLebenseffekt(final int lebenseffekt) {
		this.lebenseffekt = lebenseffekt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#ausfuehren(model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ziel) {
		ziel.setHp(ziel.getHp() + lebenseffekt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (durchEntwaffnen ? 1231 : 1237);
		result = prime * result + (durchSegen ? 1231 : 1237);
		result = prime * result + lebenseffekt;
		return result;
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LebensEffekt other = (LebensEffekt) obj;
		if (durchEntwaffnen != other.durchEntwaffnen) {
			return false;
		}
		if (durchSegen != other.durchSegen) {
			return false;
		}
		if (lebenseffekt != other.lebenseffekt) {
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
		return "Leben pro Zug: " + lebenseffekt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#entfernbarDurchEntwaffnen()
	 */
	@Override
	public boolean entfernbarDurchEntwaffnen() {
		return lebenseffekt > 0 && durchEntwaffnen;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#entfernbarDurchSegen()
	 */
	@Override
	public boolean entfernbarDurchSegen() {
		return lebenseffekt < 0 && durchSegen;
	}

}
