package model;

import java.util.Comparator;

/**
 * Implementiert {@link Comparator} und kann benutzt werden, um Klassen anhand ihrer benötigten Exp zu sortieren.
 *
 * @author RomSch
 *
 */
public class KlassenComparator implements Comparator<Klasse> {

	@Override
	public int compare(final Klasse o1, final Klasse o2) {
		return o1.getRequiredExp() - o2.getRequiredExp();
	}

}
