package specialkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, mit der alle positiven Effekte (mit Ausnahmen) vom Gegner entfernt werden.
 *
 * @author Roman Schmidt
 *
 */
public class Entwaffnen extends Karte {

	private static final long serialVersionUID = 4918555781402292012L;

	/**
	 * Constructor.
	 *
	 */
	public Entwaffnen() {
		super("Entwaffnen", Karte.ENTWAFFNEN_SEGEN_MAECHTIGKEIT, "entwaffnen.jpg");
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final int min = gegner.getMinBoost();
		if (gegner.getAtkBoost() > min) {
			gegner.setAtkBoost(min);
		}
		if (gegner.getDefBoost() > min) {
			gegner.setDefBoost(min);
		}
		gegner.entwaffnen();
	}

	@Override
	public String toString() {
		return "Entfernt alle normalen positiven Effekte vom Gegner";
	}

}
