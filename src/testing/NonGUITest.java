package testing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.Spieler;
import model.SpielerListe;

@SuppressWarnings("javadoc")
public class NonGUITest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		final Spieler spieler = SpielerListe.getSpieler(0);
		spieler.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				System.out.println(spieler.getHp());
			}
		});
		spieler.setHp(spieler.getHp() - 5);
		System.out.println(spieler);
	}

}
