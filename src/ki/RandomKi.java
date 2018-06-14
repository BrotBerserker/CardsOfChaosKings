package ki;

import java.util.Random;

import model.Spieler;

/**
 * KI, die eine zufällige Karte spielt.
 *
 * @author Roman Schmidt
 *
 */
public class RandomKi implements IKi {

	/*
	 * (non-Javadoc)
	 *
	 * @see ki.IKi#nextCard(model.Spieler)
	 */
	@Override
	public int nextCard(final Spieler spieler, final Spieler gegner) {
		try {
			Thread.sleep(888);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return new Random().nextInt(spieler.getHand().length);
	}
}
