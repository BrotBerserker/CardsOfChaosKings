package specialkarten;

import model.Karte;
import model.Spieler;
import effekte.LebensEffekt;

/**
 * Eine {@link Karte}, die den Spieler mit einem Effekt belegt, der jede Runde HP regeneriert.
 *
 * @author Roman Schmidt
 *
 */
public class Regeneration extends Karte {

	private static final long serialVersionUID = 3099122769779178685L;
	private int heilung = 2;

	/**
	 * Constructor.
	 *
	 */
	public Regeneration() {
		super("Regeneration", Karte.SPECIALKARTEN_MAECHTIGKEIT, "Regeneration.jpg");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final LebensEffekt le = new LebensEffekt("images/effektIcons/liferegeneration.png", heilung);
		if (!ausfuehrer.hatEffektNachDemZug(le)) {
			ausfuehrer.addEffektNachDemZug(le);
		}
	}

	@Override
	public String toString() {
		return "Stellt pro Runde " + heilung + " HP wieder her";
	}
}
