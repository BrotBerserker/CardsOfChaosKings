package testing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

import model.Spieler;
import networking.Client;

/**
 * @author Roman Schmidt
 *
 */
public class NetworkTestingClient {

	private Client client;
	private boolean spielerKommtAlsNaechstes;
	private Spieler spieler;
	private Spieler gegner;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new NetworkTestingClient();
	}

	/**
	 * Constructor.
	 *
	 */
	public NetworkTestingClient() {
		client = new Client("localhost", "1337");
		client.start();
		client.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (evt.getNewValue().equals("Deck auswaehlen!")) {
					System.out.println("Bitte Deck wählen!");
					client.send("0");
				} else if (evt.getNewValue().equals("Spieler kommt!")) {
					spielerKommtAlsNaechstes = true;
				} else if (evt.getNewValue().equals("Gegner kommt!")) {
					spielerKommtAlsNaechstes = false;
				} else if (evt.getNewValue() instanceof Spieler) {
					if (spielerKommtAlsNaechstes) {
						spieler = (Spieler) evt.getNewValue();
						System.out.println("Spieler: " + spieler);
					} else {
						gegner = (Spieler) evt.getNewValue();
						System.out.println("Gegner: " + gegner);
					}
				} else if (evt.getNewValue().equals("Bitte Karte spielen!")) {
					System.out.println("Bitte Karte spielen!");
					client.send(new Scanner(System.in).nextLine());
				} else if (evt.getNewValue() instanceof Integer) {
					System.out.println("Gegner spielt: " + gegner.getHand()[(Integer) evt.getNewValue()]);
				} else {
					System.out.println(evt.getNewValue());
				}
			}
		});
	}

	/**
	 * @return the spieler
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * @return the gegner
	 */
	public Spieler getGegner() {
		return gegner;
	}

}
