package model;

import java.io.Serializable;

import specialkarten.Entwaffnen;
import specialkarten.Gift;
import specialkarten.Segen;

/**
 * Abstrakte Klasse. Repräsentiert eine Karte mit Name, Bild, usw.
 *
 * @author Roman Schmidt
 *
 */
public abstract class Karte implements Serializable {

	private static final long serialVersionUID = 7263411183887628539L;

	/** Name der Karte */
	private String name;

	/** Mächtigkeit der Karte (=Anzahl im Deck) */
	private double maechtigkeit;

	/** Pfad zum Bild der Karte */
	private String imgPath;

	/** Mächtigkeit der Spezialkarten, z.B. {@link Gift} */
	public static final int SPECIALKARTEN_MAECHTIGKEIT = 1;

	/** Mächtigkiet von {@link Entwaffnen} und {@link Segen} */
	public static final int ENTWAFFNEN_SEGEN_MAECHTIGKEIT = 3;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 */
	public Karte(final String name, final double maechtigkeit, final String imgPath) {
		this.imgPath = imgPath;
		this.name = name;
		this.maechtigkeit = maechtigkeit;
	}

	/**
	 * Wird aufgerufen, wenn die Karte ausgespielt wird.
	 *
	 * @param ausfuehrer
	 *            Der Spieler, der die Karte gespielt hat
	 * @param gegner
	 *            Der Gegner
	 */
	public abstract void ausfuehren(Spieler ausfuehrer, Spieler gegner);

	/**
	 * @return the maechtigkeit
	 */
	public double getMaechtigkeit() {
		return maechtigkeit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Karte [name=" + name + ", maechtigkeit=" + maechtigkeit + "]";
	}

}
