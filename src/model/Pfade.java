package model;

/**
 * Enum mit allen Pfaden.
 *
 * @author RomSch
 *
 */
public enum Pfade {

	/** Der Pfad des Magiers */
	MAGIERPFAD("Pfad des Magiers"),

	/** Der Pfad des Kriegers */
	KRIEGERPFAD("Pfad des Kriegers"),

	/** Der Pfad des Jaegers */
	JAEGERPFAD("Pfad des Jaegers"),

	/** Der Pfad der Untoten */
	UNTOTENPFAD("Pfad der Untoten");

	/** Der volle Name des Pfads */
	private String text;

	/**
	 * Constructor.
	 *
	 * @param text
	 *            Der volle Name des Pfads
	 */
	private Pfade(final String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}
