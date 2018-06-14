package specialkarten;

import java.util.Arrays;
import java.util.List;

import model.Karte;
import model.Spieler;

public class Koerperteilwurf extends Karte {

	/** */
	private static final long serialVersionUID = 4127748794992272035L;

	private int damage;

	private String textName;

	private static final List<String> NAMES = Arrays.asList("Kopf", "Linker Arm", "Rechter Arm", "Linkes Bein", "Rechtes Bein", "");// anti
	// IndexOutOfBounds
	// verhindern
	private static final List<String> TEXTS = Arrays.asList("Kopf", "Linken Arm", "Rechten Arm", "Linkes Bein", "Rechtes Bein", "");
	private static final List<String> IMAGES = Arrays.asList("kopf.jpg", "linkerarm.jpg", "rechterarm.jpg", "linkesbein.jpg", "rechtesbein.jpg", "kopf.jpg");
	private static final List<String> FOLDERS = Arrays.asList("zombie", "zombie/kaputt1", "zombie/kaputt2", "zombie/kaputt3", "zombie/kaputt4", "zombie/kaputt5");

	public Koerperteilwurf(final int damage) {
		this(NAMES.get(0), TEXTS.get(0), IMAGES.get(0), damage);
	}

	public Koerperteilwurf(final String name, final String textName, final String imgPath, final int damage) {
		super(name, Karte.SPECIALKARTEN_MAECHTIGKEIT, imgPath);
		this.damage = damage;
		this.textName = textName;
	}

	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		ausfuehrer.setHp(0);
		gegner.setHp(gegner.getHp() - damage);

		ausfuehrer.getDeck().remove(this);
		ausfuehrer.getDeck().add(getNextWurfKarte());
		ausfuehrer.zieheNeueHand();
		ausfuehrer.setAnimationFolder(FOLDERS.get(FOLDERS.indexOf(ausfuehrer.getAnimationFolder()) + 1));
	}

	private Koerperteilwurf getNextWurfKarte() {
		return new Koerperteilwurf(NAMES.get(NAMES.indexOf(getName()) + 1), TEXTS.get(TEXTS.indexOf(textName) + 1), IMAGES.get(IMAGES.indexOf(getImgPath()) + 1), damage);
	}

	@Override
	public String toString() {
		return textName + " auf den Gegner werfen. Verursacht sofort " + damage + " Schaden.";
	}

}
