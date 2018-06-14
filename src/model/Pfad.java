package model;

/**
 * Ein Pfad (z.B. Pfad des Magiers) enth�lt mehrere Klassen (z.B. Magier, Beschw�rer, ...). Durch Duelle erh�lt man Exp
 * f�r den Pfad, zu der die gespielte Klasse geh�rt. Hat man gen�gend Exp in einem Pfad gesammelt, wird die n�chste
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
