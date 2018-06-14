package model;

/**
 * Ein Pfad (z.B. Pfad des Magiers) enthält mehrere Klassen (z.B. Magier, Beschwörer, ...). Durch Duelle erhält man Exp
 * für den Pfad, zu der die gespielte Klasse gehört. Hat man genügend Exp in einem Pfad gesammelt, wird die nächste
 * Klasse in diesem Pfad freigeschaltet.
 *
 * @author Roman Schmidt
 *
 */
public class Pfad {

	/** Name des Pfads */
	private String name;

	/** Klassen des Pfads */
	private Klasse[] klassen;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name
	 * @param klassen
	 *            Klassen
	 */
	public Pfad(final String name, final Klasse... klassen) {
		this.name = name;
		this.klassen = klassen;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the klassen
	 */
	public Klasse[] getKlassen() {
		return klassen;
	}

}
