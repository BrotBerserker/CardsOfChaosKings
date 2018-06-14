package karten;

import model.Karte;
import model.Spieler;

/**
 * @author Roman Schmidt
 *
 */
public class StatsReducerKarte extends Karte {

	private static final long serialVersionUID = 6532504246656817647L;

	private int atkReduce; // positiv
	private int defReduce;

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param maechtigkeit
	 * @param imgPath
	 * @param atkReduce
	 * @param defReduce
	 */
	public StatsReducerKarte(final String name, final double maechtigkeit, final String imgPath, final int atkReduce, final int defReduce) {
		super(name, maechtigkeit, imgPath);
		this.atkReduce = atkReduce;
		this.defReduce = defReduce;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final int min = gegner.getMinBoost();
		if (-atkReduce + min < gegner.getAtkBoost()) {
			gegner.setAtkBoost(gegner.getAtkBoost() - atkReduce);
		}
		if (-defReduce + min < gegner.getDefBoost()) {
			gegner.setDefBoost(gegner.getDefBoost() - defReduce);
		}
	}

	/**
	 * @return the atkReduce
	 */
	public int getAtkReduce() {
		return atkReduce;
	}

	/**
	 * @return the defReduce
	 */
	public int getDefReduce() {
		return defReduce;
	}

	@Override
	public String toString() {
		String s = "";
		if (atkReduce > 0) {
			s = "Verringert die Atk um " + atkReduce;
			if (defReduce > 0) {
				s += " und die Def um " + defReduce;
			}
		} else {
			s = "Verringert die Def um " + defReduce;
		}
		return s;
	}
}
