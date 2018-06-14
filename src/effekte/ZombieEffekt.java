package effekte;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.Spieler;

public class ZombieEffekt extends InfoEffekt {

	/** */
	private static final long serialVersionUID = 1644809301908186643L;

	private static boolean listenerAdded = false;

	/**
	 * Constructor.
	 *
	 * @param iconPath
	 * @param text
	 */
	public ZombieEffekt(final String iconPath, final String text) {
		super(iconPath, text);
	}

	@Override
	public void ausfuehren(final Spieler ziel) {
		// TODO effekt wird erst am anfang des zuges des zombies aktiv
		// -> wenn der zombie vorher getötet wird, bleibt er tot. so lassen?
		if (listenerAdded) {
			return;
		}

		ziel.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (!"HP".equals(evt.getPropertyName())) {
					return;
				}
				if ((int) evt.getNewValue() > 0) {
					return;
				}
				ziel.removePropertyChangeListener(this);
				ziel.removeEffektVorDemZug(ZombieEffekt.this);
				ziel.setHp(ziel.getMaxHP() - 10);
				ziel.setMaxHP(ziel.getMaxHP() - 10);
				listenerAdded = false;
			}
		});
		listenerAdded = true;

	}

	@Override
	public boolean entfernbarDurchEntwaffnen() {
		return false;
	}

	@Override
	public boolean entfernbarDurchSegen() {
		return false;
	}

}
