package specialkarten;

import model.Karte;
import model.Spieler;

/**
 * Eine {@link Karte}, mit der man den nächsten Angriff des Gegners auf ihn reflektieren kann. Die Wirkung tritt beim
 * nächsten Angriff des Gegners nach dem Ausspielen dieser Karte ein.
 *
 * @author RomSch
 *
 */
public class Konter extends Karte {

	private static final long serialVersionUID = -4488400644656785932L;

	/**
	 * Constructor.
	 *
	 */
	public Konter() {
		super("Konter", Karte.SPECIALKARTEN_MAECHTIGKEIT, "konter.jpg");
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setKontertDenNaechsten(true);
	}

	@Override
	public String toString() {
		return "Reflektiert den Schaden des nächsten normalen Angriffs des Gegners";
	}

}
