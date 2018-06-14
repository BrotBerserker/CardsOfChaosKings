package model;

import java.io.Serializable;

import specialkarten.Entwaffnen;
import specialkarten.Segen;

/**
 * Abstrakte Klasse. Ein Effekt liegt auf einem Spieler und wird jede Runde ausgeführt.
 *
 * @author Roman Schmidt
 *
 */
public abstract class Effekt implements Serializable {

	private static final long serialVersionUID = -8270058080806978969L;

	/** Tag, z.B Elementareffekt, siehe {@link EffektKonstanten} */
	private String tag;

	/** Pfad zum Bild */
	private String iconPath;

	/**
	 * Constructor.
	 *
	 * @param tag
	 *            Tag, siehe {@link EffektKonstanten}
	 * @param iconPath
	 *            Pfad zum Bild
	 */
	public Effekt(final String tag, final String iconPath) {
		if (tag == null) {
			this.tag = "";
		} else {
			this.tag = tag;
		}
		this.iconPath = iconPath;
	}

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Bild
	 */
	public Effekt(final String iconPath) {
		// kein tag
		this(null, iconPath);
	}

	/**
	 * Wird jede Runde aufgerufen.
	 *
	 * @param ziel
	 *            Der Spieler, auf dem der Effekt liegt
	 */
	public abstract void ausfuehren(Spieler ziel);

	/**
	 * Gibt an, ob der Effekt durch {@link Entwaffnen} entfernbar ist.
	 *
	 * @return true, falls entfernbar
	 */
	public abstract boolean entfernbarDurchEntwaffnen();

	/**
	 * Gibt an, ob der Effekt durch {@link Segen} entfernbar ist.
	 *
	 * @return true, falls entfernbar
	 */
	public abstract boolean entfernbarDurchSegen();

	/**
	 * Gibt an, ob der Effekt noch aktiv ist
	 *
	 * @return true, falls aktiv
	 */
	public boolean istAktiv() {
		return true;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath
	 *            the iconPath to set
	 */
	public void setIconPath(final String iconPath) {
		this.iconPath = iconPath;
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
		final Effekt other = (Effekt) obj;
		if (tag == null) {
			if (other.tag != null) {
				return false;
			}
		} else if (!tag.equals(other.tag)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		final String tag = getTag();
		if (tag != null && !tag.trim().equals("")) {
			str.append(tag + ": ");
		}
		return str.toString();
	}

}
