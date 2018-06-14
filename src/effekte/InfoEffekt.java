package effekte;

import model.Effekt;
import model.Spieler;

/**
 * Ein {@link Effekt}, der keine Auswirkung hat, sondern nur einen bestimmten Text und ein Bild anzeigt.
 *
 * @author RomSch
 *
 */
public class InfoEffekt extends Effekt {

	private static final long serialVersionUID = 275246710694378324L;

	/** Text, der in {@link InfoEffekt#toString()} zurückgegeben wird */
	private String text;

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 *            Pfad zum Icon des Effekts
	 * @param text
	 *            Text, der angezeigt werden soll
	 */
	public InfoEffekt(final String iconPath, final String text) {
		super(iconPath);
		this.text = text;
	}

	@Override
	public void ausfuehren(final Spieler ziel) {
		// do nothing
	}

	@Override
	public boolean entfernbarDurchEntwaffnen() {
		return false;
	}

	@Override
	public boolean entfernbarDurchSegen() {
		return false;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
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
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		final InfoEffekt other = (InfoEffekt) obj;
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
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
		return text;
	}

}
