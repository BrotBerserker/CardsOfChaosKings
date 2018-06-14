package karten;

import java.util.List;

import model.Effekt;
import model.EffektKonstanten;
import model.Karte;
import model.Spieler;
import effekte.TimedEffekt;

/**
 * Eine {@link Karte}, die alle Elementareffekte vom Gegner und vom Spieler entfernt und je nach der Anzahl Schaden oder
 * Heilung verteilt.
 *
 * @author Roman Schmidt
 *
 */
public class ElementarZerstoererKarte extends Karte {

	private static final long serialVersionUID = 1L;

	/** HP, die pro zerstörtem Elementar hinzugefügt werden (kann negativ sein) */
	private int leben;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name der Karte
	 * @param maechtigkeit
	 *            Mächtigkeit der Karte
	 * @param imgPath
	 *            Pfad zum Bild der Karte
	 * @param leben
	 *            Leben, das pro zerstörtem Elementar hinzugefügt wird (negatives Lebem wird dem Gegner abgezogen)
	 */
	public ElementarZerstoererKarte(final String name, final double maechtigkeit, final String imgPath, final int leben) {
		super(name, maechtigkeit, imgPath);
		this.leben = leben;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see model.Karte#ausfuehren(model.Spieler, model.Spieler)
	 */
	@Override
	public void ausfuehren(final Spieler ausfuehrer, final Spieler gegner) {
		int anzahl = 0;
		anzahl += zerstoereElementare(ausfuehrer, ausfuehrer.getEffekteNachDemZug());
		anzahl += zerstoereElementare(ausfuehrer, ausfuehrer.getEffekteVorDemZug());
		anzahl += zerstoereElementare(gegner, gegner.getEffekteNachDemZug());
		anzahl += zerstoereElementare(gegner, gegner.getEffekteVorDemZug());

		final int hp = leben * anzahl;
		if (leben > 0) {
			ausfuehrer.setHp(ausfuehrer.getHp() + hp);
		} else {
			gegner.setHp(gegner.getHp() + hp);
		}
	}

	/**
	 * Durchsucht die Liste von Effekten, entfernt alle Elementareffekte, führt deren Nachwirkungen aus und gibt die
	 * Anzahl der Elementareffekte zurück.
	 *
	 * @param spieler
	 *            Ziel für die Nachwirkungen
	 * @param effekte
	 *            Effektliste
	 * @return Anzahl der Elementareffekte
	 */
	private int zerstoereElementare(final Spieler spieler, final List<Effekt> effekte) {
		int anzahl = 0;
		for (int i = 0; i < effekte.size(); i++) {
			final Effekt effekt = effekte.get(i);
			if (effekt.getTag().equals(EffektKonstanten.ELEMENTAR_EFFEKT)) {
				if (effekt instanceof TimedEffekt) {
					((TimedEffekt) effekt).nachwirkungAusfuehren(spieler);
				}
				effekte.remove(i);
				i--;
				anzahl++;
			}
		}
		return anzahl;
	}

	@Override
	public String toString() {
		String s = "Opfert alle Elementare und ";
		if (leben > 0) {
			s += "heilt um " + leben + " pro zerstörtem Elementar";
		} else {
			s += "fügt " + (-leben) + " Schaden pro zerstörtem Elementar zu";
		}
		return s;
	}
}
