package specialkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die alle negativen Effekte (mit Ausnahmen) vom Spieler entfernt.
 *
 * @author Roman Schmidt
 *
 */
public class Segen extends Karte {

	private static final long serialVersionUID = 2488984794329238805L;

	/**
	 * Constructor.
	 *
	 */
	public Segen() {
		super("Segen", Karte.ENTWAFFNEN_SEGEN_MAECHTIGKEIT, "segen.jpg");
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final int min = ausfuehrer.getMinBoost();
		if (ausfuehrer.getAtkBoost() < min) {
			ausfuehrer.setAtkBoost(min);
		}
		if (ausfuehrer.getDefBoost() < min) {
			ausfuehrer.setDefBoost(min);
		}
		ausfuehrer.segen();
	}

	@Override
	public String toString() {
		return "Entfernt alle normalen negativen Effekte vom Spieler";
	}
}
