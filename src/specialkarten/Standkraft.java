package specialkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die die maximalen und die aktuellen HP des Spielers um einen Wert erhöht.
 *
 * @author Roman Schmidt
 *
 */
public class Standkraft extends Karte {

	private static final long serialVersionUID = -9146996459423498164L;
	private int hp = 25;

	/**
	 * Constructor.
	 *
	 */
	public Standkraft() {
		super("Standkraft", Karte.SPECIALKARTEN_MAECHTIGKEIT, "Standkraft.jpg");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setMaxHP(ausfuehrer.getMaxHP() + hp);
		ausfuehrer.setHp(ausfuehrer.getHp() + hp);
	}

	@Override
	public String toString() {
		return "Erhöht die aktuellen und die maximalen HP um " + hp;
	}
}
