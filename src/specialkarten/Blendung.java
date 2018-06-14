package specialkarten;

import model.IEffektNachwirkung;
import model.Karte;
import model.Spieler;
import effekte.InfoEffekt;
import effekte.TimedEffekt;

/**
 * Ein {@link Karte}, die den Gegner blendet. Dadurch zieht er neue Karten und kann für eine bestimmte Anzahl Runden
 * seine Karten nicht sehen.
 *
 * @author Roman Schmidt
 *
 */
public class Blendung extends Karte {

	private static final long serialVersionUID = 4911265518747886461L;

	/** Anzahl der Runden, die der Gegner seine Karten nicht sehen kann */
	public static final int ANZAHL_RUNDEN = 3;

	/**
	 * Constructor.
	 *
	 */
	public Blendung() {
		super("Blendung", Karte.SPECIALKARTEN_MAECHTIGKEIT, "blendung.jpg");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		final TimedEffekt be = new TimedEffekt(new InfoEffekt("images/effektIcons/lightningIcon.png", "Geblendet"), ANZAHL_RUNDEN, new IEffektNachwirkung() {
			private static final long serialVersionUID = 1L;

			@Override
			public void ausfuehren(final Spieler ziel) {
				ziel.setIstGeblendet(false);
			}
		});
		if (!gegner.hatEffektNachDemZug(be)) {
			// for (int i = 0; i < gegner.getHand().length; i++) {
			// gegner.getHand()[i] = null;
			// gegner.zieheKarte();
			// }
			gegner.zieheNeueHand();
			gegner.setIstGeblendet(true);
			gegner.addEffektNachDemZug(be);
		}
	}

	@Override
	public String toString() {
		return "Lässt den Gegner " + Spieler.ANZAHL_HANDKARTEN + " neue Karten ziehen und macht ihn " + ANZAHL_RUNDEN + " Runden lang blind";
	}
}
