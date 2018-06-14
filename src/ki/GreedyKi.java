package ki;

import model.Karte;
import model.Spieler;

/**
 * KI, die immer die Karte spielt, die für den nächsten Zug das beste Ergebnis liefert.
 *
 * @author Roman Schmidt
 *
 */
public class GreedyKi implements IKi {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ki.IKi#nextCard(model.Spieler, model.Spieler)
	 */
	@Override
	public int nextCard(final Spieler spieler, final Spieler gegner) {
		final Karte[] hand = spieler.getHand();
		int bestIndex = 0;
		int bestHpDifferenz = -999;
		int currentHPDifferenz;
		for (int i = 0; i < hand.length; i++) {
			currentHPDifferenz = spieleKarte(spieler, gegner, i);
			if (currentHPDifferenz > bestHpDifferenz) {
				bestHpDifferenz = currentHPDifferenz;
				bestIndex = i;
			}
		}
		return bestIndex;
	}

	private int spieleKarte(final Spieler spieler, final Spieler gegner, final int karte) {
		final Spieler spielerCopy = new Spieler(spieler);
		final Spieler gegnerCopy = new Spieler(gegner);
		spielerCopy.setGegner(gegnerCopy);
		gegnerCopy.setGegner(spielerCopy);
		spielerCopy.spieleKarte(karte);
		return spielerCopy.getHp() - gegnerCopy.getHp();
	}

}
