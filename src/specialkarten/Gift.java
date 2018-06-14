package specialkarten;

import model.Karte;
import model.Spieler;
import effekte.LebensEffekt;

/**
 * Eine {@link Karte}, die den Gegner mit einem Effekt belegt, der ihm jede Runde HP abzieht.
 *
 * @author Roman Schmidt
 *
 */
public class Gift extends Karte {

	private static final long serialVersionUID = -3971585151113402807L;
	private int schaden = -3;

	/**
	 * Constructor.
	 *
	 */
	public Gift() {
		super("Gift", Karte.SPECIALKARTEN_MAECHTIGKEIT, "Gift.jpg");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final LebensEffekt le = new LebensEffekt("images/effektIcons/vergiftet.png", schaden);
		if (!gegner.hatEffektNachDemZug(le)) {
			gegner.addEffektNachDemZug(le);
		}
	}

	@Override
	public String toString() {
		return "Fügt dem Gegner " + (-schaden) + " Schaden pro Runde zu";
	}

}
