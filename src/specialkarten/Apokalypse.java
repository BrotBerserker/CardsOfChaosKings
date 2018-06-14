package specialkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, die beiden Spielern 50 HP zieht.
 *
 * @author RomSch
 *
 */
public class Apokalypse extends Karte {

	private static final long serialVersionUID = 2392139685370996035L;

	private int schaden = 50;

	/**
	 * Constructor.
	 *
	 */
	public Apokalypse() {
		super("Apolakypse", Karte.SPECIALKARTEN_MAECHTIGKEIT, "apokalypse.jpg");
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setHp(ausfuehrer.getHp() - schaden);
		gegner.setHp(gegner.getHp() - schaden);
	}

	@Override
	public String toString() {
		return "Verursacht bei beiden Spielern sofort " + schaden + " Schaden";
	}
}
