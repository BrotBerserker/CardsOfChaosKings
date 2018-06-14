package effekte;

import model.Effekt;
import model.Spieler;

/**
 * Ein {@link Effekt}, der die SSJ-Punkte des Spielers jede Runde um einen bestimmten Betrag erhöht, falls er kein SSj
 * ist, und um einen bestimmten Betrag erniedrigt, falls er SSJ ist.
 *
 * @author Roman Schmidt
 *
 */
public class SSJControlEffekt extends Effekt {

	private static final long serialVersionUID = -689491536207737973L;

	/** SSJ-Punkte, die erhalten werden, wenn man kein SSJ ist */
	private int plus;

	/** SSJ-Punkte, die verloren werden, wenn man SSJ ist */
	private int minus;

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 * @param plus
	 *            SSJ-Punkte, die erhalten werden, wenn man kein SSJ ist
	 * @param minus
	 *            SSJ-Punkte, die verloren werden, wenn man SSJ ist
	 */
	public SSJControlEffekt(final String iconPath, final int plus, final int minus) {
		super(iconPath);
		this.plus = plus;
		this.minus = minus;
	}

	/**
	 * Constructor. Setzt SSJ-Plus auf 3 und SSJ-Minus auf 10.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 */
	public SSJControlEffekt(final String iconPath) {
		this(iconPath, 3, 10);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Effekt#ausfuehren(model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ziel) {
		if (ziel.isSsj()) {
			ziel.setSsjPoints(ziel.getSsjPoints() - minus);
		} else {
			ziel.setSsjPoints(ziel.getSsjPoints() + plus);
		}
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
		result = prime * result + minus;
		result = prime * result + plus;
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
		final SSJControlEffekt other = (SSJControlEffekt) obj;
		if (minus != other.minus) {
			return false;
		}
		if (plus != other.plus) {
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
		return "SSJ: +" + plus + " / -" + minus;
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

}
