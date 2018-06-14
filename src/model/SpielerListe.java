package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import karten.ElementarZerstoererKarte;
import karten.HeilKarte;
import karten.NeueKartenKarte;
import karten.RegElementarKarte;
import karten.SchadenElementarKarte;
import karten.SchadenKarte;
import karten.StatsBoosterKarte;
import karten.StatsElementarKarte;
import karten.StatsReducerKarte;
import specialkarten.Apokalypse;
import specialkarten.Blendung;
import specialkarten.Einsicht;
import specialkarten.Entwaffnen;
import specialkarten.Gift;
import specialkarten.Koerperteilwurf;
import specialkarten.Konter;
import specialkarten.Regeneration;
import specialkarten.Segen;
import specialkarten.Standkraft;
import ssjkarten.SSJBoostKarte;
import effekte.StatsEffekt;
import effekte.ZombieEffekt;

/**
 * Hier werden die ganzen Pfade, Klassen und Karten durch Code erstellt. Wäre vielleicht cool mit einer DSL zu machen
 * oder so.
 *
 * @author RomSch
 *
 */
public class SpielerListe { // TODO Anderer Name, z.B. Klassenliste

	/** Alle verfügbaren Klassen, mit dem Namen als Key und der Klasse selbst als Value */
	public static final Map<String, Klasse> KLASSEN = new HashMap<String, Klasse>();

	/** Liste aller Pfade */
	public static final List<Pfad> PFADE = new ArrayList<Pfad>();

	// public static final String KRIEGERPFAD = "Pfad des Kriegers";
	// public static final String MAGIERPFAD = "Pfad des Magiers";
	// public static final String JAEGERPFAD = "Pfad des Jaegers";
	

	@SuppressWarnings("unused")
	private static final SpielerListe instance = new SpielerListe();

	private SpielerListe() {
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Magier
		// -----------------------------------------------------------------------------------------------------
		final Klasse magier = createMagier();
		KLASSEN.put("Magier", magier);
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Beschwörer
		// -----------------------------------------------------------------------------------------------------
		final Klasse beschwoerer = createBeschwoerer();
		KLASSEN.put("Beschwörer", beschwoerer);
		// -----------------------------------------------------------------------------------------------------
		// Pfad des Magiers
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		PFADE.add(new Pfad(Pfade.MAGIERPFAD.getText(), magier, beschwoerer));
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Barbar
		// -----------------------------------------------------------------------------------------------------
		final Klasse barbar = createBarbar();
		KLASSEN.put("Barbar", barbar);
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Ritter
		// -----------------------------------------------------------------------------------------------------
		final Klasse ritter = createRitter();
		KLASSEN.put("Ritter", ritter);
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Paladin
		// -----------------------------------------------------------------------------------------------------
		final Klasse paladin = createPaladin();
		KLASSEN.put("Paladin", paladin);
		// -----------------------------------------------------------------------------------------------------
		// Pfad des Kriegers
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		PFADE.add(new Pfad(Pfade.KRIEGERPFAD.getText(), barbar, ritter, paladin));
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Jäger
		// -----------------------------------------------------------------------------------------------------
		final Klasse jaeger = createJaeger();
		KLASSEN.put("Jäger", jaeger);
		// -----------------------------------------------------------------------------------------------------
		// Pfad des Jägers
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		PFADE.add(new Pfad(Pfade.JAEGERPFAD.getText(), jaeger));
		// -----------------------------------------------------------------------------------------------------
		// Klasse: Zombie
		// -----------------------------------------------------------------------------------------------------
		final Klasse zombie = createZombie();
		KLASSEN.put("Zombie", zombie);
		// -----------------------------------------------------------------------------------------------------
		// Pfad der Untoten
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		PFADE.add(new Pfad(Pfade.UNTOTENPFAD.getText(), zombie));
		// -----------------------------------------------------------------------------------------------------
		// TODO: Mehr Klassen und Pfade! Siehe Piratenpad: https://piratenpad.de/p/Klassen,_Pfade,_Karten
		// -----------------------------------------------------------------------------------------------------
		// final Klasse chaosKing = createChaosKing();
		// KLASSEN.put("Chaos King", chaosKing);
		// PFADE.add(new Pfad(Pfade.JAEGERPFAD.getText(), jaeger, chaosKing));
	}

	private Klasse createZombie() {
		final List<Karte> zombieKarten = new ArrayList<Karte>();
		// zombieKarten.add(new Gift());
		zombieKarten.add(new Koerperteilwurf(30));

		final List<Effekt> effekte = new ArrayList<Effekt>();
		effekte.add(new ZombieEffekt("images/effektIcons/zombie/kopf.png", "Kopf"));
		effekte.add(new ZombieEffekt("images/effektIcons/zombie/linkerArm.png", "Linker Arm"));
		effekte.add(new ZombieEffekt("images/effektIcons/zombie/rechterArm.png", "Rechter Arm"));
		effekte.add(new ZombieEffekt("images/effektIcons/zombie/linkesBein.png", "Linkes Bein"));
		effekte.add(new ZombieEffekt("images/effektIcons/zombie/rechtesBein.png", "Rechtes Bein"));

		final Klasse zombie = new Klasse("Zombie", 50, 100, zombieKarten, "zombie", 0, effekte);
		return zombie;
	}

	@SuppressWarnings("unused")
	private Klasse createChaosKing() {
		final ArrayList<Karte> chaosKarten = new ArrayList<Karte>();
		chaosKarten.add(new Konter());
		chaosKarten.add(new Gift());
		chaosKarten.add(new Regeneration());
		chaosKarten.add(new Apokalypse());
		chaosKarten.add(new Standkraft());
		chaosKarten.add(new Einsicht());
		chaosKarten.add(new Blendung());
		chaosKarten.add(new SSJBoostKarte("SSJ Boost", 1, 100, "barbar/ssjkarte.jpg"));
		final Klasse chaosKing = new Klasse("Chaos King", 25, 100, chaosKarten, "ritter", 1000);
		return chaosKing;
	}

	private Klasse createJaeger() {
		final ArrayList<Karte> jaegerKarten = new ArrayList<Karte>();
		jaegerKarten.add(new SchadenKarte("Hinterhalt", 10, 13, "hinterhalt.jpg"));
		jaegerKarten.add(new SchadenKarte("Schuss", 20, 10, "schuss.jpg"));
		jaegerKarten.add(new SchadenKarte("Messerstich", 25, 7, "messerstich.jpg"));
		jaegerKarten.add(new HeilKarte("Heiltrank", 5, 8, "heiltrank.jpg"));
		jaegerKarten.add(new HeilKarte("Heilkräuter", 10, 5, "heilkraeuter.jpg"));
		jaegerKarten.add(new StatsReducerKarte("Jagdhorn", 5, "jagdhorn.jpg", 1, 1));
		jaegerKarten.add(new StatsBoosterKarte("Wolf", 5, "wolf.jpg", 2, 0));
		jaegerKarten.add(new NeueKartenKarte("Munition", 3, "Nachschub.jpg"));
		jaegerKarten.add(new Gift());
		jaegerKarten.add(new Regeneration());
		jaegerKarten.add(new Konter());
		jaegerKarten.add(new Entwaffnen());
		jaegerKarten.add(new Segen());
		jaegerKarten.add(new SSJBoostKarte("Konzentration", 3, 25, "jaeger/ssjkarte.jpg"));
		final Klasse jaeger = new Klasse("Jäger", 120, 100, jaegerKarten, "jaeger", 0);
		return jaeger;
	}

	private Klasse createPaladin() {
		final ArrayList<Karte> paladinKarten = new ArrayList<Karte>();
		paladinKarten.add(new SchadenKarte("Hammerschlag", 15, 8, "hammerschlag.jpg"));
		paladinKarten.add(new SchadenKarte("Morgenstern", 18, 6, "morgenstern.jpg"));
		paladinKarten.add(new SchadenKarte("Heiliger Schlag", 20, 4, "heiligerSchlag.jpg"));
		paladinKarten.add(new HeilKarte("Heiltrank", 8, 7, "heiltrank.jpg"));
		paladinKarten.add(new HeilKarte("Heilzauber", 10, 5, "heilzauber.jpg"));
		paladinKarten.add(new StatsBoosterKarte("Göttlicher Schild", 4, "goettlicherSchild.jpg", 0, 4));
		paladinKarten.add(new NeueKartenKarte("Karten des Himmels", 3, "kartendeshimmels.jpg"));
		paladinKarten.add(new Regeneration());
		paladinKarten.add(new Gift());
		paladinKarten.add(new Apokalypse());
		paladinKarten.add(new Blendung());
		paladinKarten.add(new Entwaffnen());
		paladinKarten.add(new Segen());
		paladinKarten.add(new SSJBoostKarte("Fokus", 3, 25, "paladin/ssjkarte.jpg"));
		final Klasse paladin = new Klasse("Paladin", 200, 100, paladinKarten, "paladin", 50);
		return paladin;
	}

	private Klasse createRitter() {
		final ArrayList<Karte> ritterKarten = new ArrayList<Karte>();
		ritterKarten.add(new SchadenKarte("Schlag", 15, 9, "schlag.jpg"));
		ritterKarten.add(new SchadenKarte("Schildstoss", 18, 6, "schildstoss.jpg"));
		ritterKarten.add(new SchadenKarte("Schubser", 15, 3, "schubser.jpg"));
		ritterKarten.add(new HeilKarte("Speisekarte", 10, 6, "speisekarte.jpg"));
		ritterKarten.add(new StatsReducerKarte("Schrei", 5, "schrei.jpg", 0, 3));
		ritterKarten.add(new StatsBoosterKarte("Schild", 5, "schild.jpg", 0, 2));
		ritterKarten.add(new StatsBoosterKarte("Blutdurst", 5, "blutdurst.jpg", 3, 0));
		ritterKarten.add(new NeueKartenKarte("Neue Waffen", 3, "Waffenaustausch.jpg"));
		ritterKarten.add(new Regeneration());
		ritterKarten.add(new Gift());
		ritterKarten.add(new Standkraft());
		ritterKarten.add(new Entwaffnen());
		ritterKarten.add(new Segen());
		ritterKarten.add(new SSJBoostKarte("Trainieren", 3, 25, "ritter/ssjkarte.jpg"));
		final Klasse ritter = new Klasse("Ritter", 150, 100, ritterKarten, "ritter", 25);
		return ritter;
	}

	private Klasse createBarbar() {
		final ArrayList<Karte> barbarKarten = new ArrayList<Karte>();
		barbarKarten.add(new SchadenKarte("Knüppelschlag", 10, 12, "knueppel.jpg"));
		barbarKarten.add(new SchadenKarte("Hinkelsteinwurf", 20, 9, "hinkelstein.jpg"));
		barbarKarten.add(new SchadenKarte("Biss", 25, 6, "biss.jpg"));
		barbarKarten.add(new HeilKarte("Schinken", 5, 7, "schinken.jpg"));
		barbarKarten.add(new HeilKarte("Höhle", 10, 5, "hoehle.jpg"));// ja
		barbarKarten.add(new StatsReducerKarte("Stinken", 5, "stinken.jpg", 1, 2));
		barbarKarten.add(new StatsBoosterKarte("Met", 5, "met.jpg", 2, 0));
		barbarKarten.add(new NeueKartenKarte("Holzfass", 3, "munition.jpg"));
		barbarKarten.add(new Gift());
		barbarKarten.add(new Regeneration());
		barbarKarten.add(new Entwaffnen());
		barbarKarten.add(new Segen());
		barbarKarten.add(new SSJBoostKarte("Wut", 3, 25, "barbar/ssjkarte.jpg"));
		final Klasse barbar = new Klasse("Barbar", 130, 100, barbarKarten, "barbar", 0);
		return barbar;
	}

	private Klasse createBeschwoerer() {
		final ArrayList<Karte> beschwoererKarten = new ArrayList<Karte>();
		beschwoererKarten.add(new NeueKartenKarte("Manaaustausch", 3, "Mana-Austausch.jpg"));
		beschwoererKarten.add(new Entwaffnen());
		beschwoererKarten.add(new Segen());
		beschwoererKarten.add(new Regeneration());
		beschwoererKarten.add(new Gift());
		beschwoererKarten.add(new SchadenElementarKarte("Feuerelementar", 10, "feuerelementar.jpg", 4, 3));
		beschwoererKarten.add(new SchadenElementarKarte("Feuerelementar", 10, "feuerelementar.jpg", 1, 10));
		beschwoererKarten.add(new RegElementarKarte("Wasserelementar", 10, "wasserelementar.jpg", 4, 3));
		beschwoererKarten.add(new RegElementarKarte("Wasserelementar", 10, "wasserelementar.jpg", 1, 10));
		beschwoererKarten.add(new StatsElementarKarte("Sturmelementar", 10, "sturmelementar.jpg", -2, StatsEffekt.NO_EFFECT, 3, false));
		beschwoererKarten.add(new StatsElementarKarte("Eiselementar", 10, "eiselementar.jpg", StatsEffekt.NO_EFFECT, 2, 3, true));
		beschwoererKarten.add(new ElementarZerstoererKarte("Elementzerstörung", 10, "apokalypse.jpg", -5));
		beschwoererKarten.add(new ElementarZerstoererKarte("Elementabsorbtion", 10, "blendung.jpg", 5));
		final Klasse beschwoerer = new Klasse("Beschwörer", 100, 100, beschwoererKarten, "beschwoerer", 25);
		return beschwoerer;
	}

	private Klasse createMagier() {
		final ArrayList<Karte> magierKarten = new ArrayList<Karte>();
		magierKarten.add(new SchadenKarte("Blitz", 10, 15, "blitz.jpg"));
		magierKarten.add(new SchadenKarte("Feuerball", 15, 12, "feuer.jpg"));
		magierKarten.add(new SchadenKarte("Erdkugel", 20, 9, "erdkugel.jpg"));
		magierKarten.add(new HeilKarte("Heiltrank", 5, 7, "heiltrank.jpg"));
		magierKarten.add(new HeilKarte("Heilzauber", 10, 5, "heilzauber.jpg"));
		magierKarten.add(new StatsBoosterKarte("Stab", 5, "stab.jpg", 5, 0));
		magierKarten.add(new StatsBoosterKarte("Robe", 5, "robe.jpg", 0, 1));
		magierKarten.add(new NeueKartenKarte("Manaaustausch", 3, "Mana-Austausch.jpg"));
		magierKarten.add(new Regeneration());
		magierKarten.add(new Gift());
		magierKarten.add(new Einsicht());
		magierKarten.add(new Entwaffnen());
		magierKarten.add(new Segen());
		magierKarten.add(new SSJBoostKarte("Energie Sammeln", 3, 25, "magier/ssjkarte.jpg"));
		final Klasse magier = new Klasse("Magier", 100, 100, magierKarten, "magier", 0);
		return magier;
	}

	/**
	 * Gibt die {@link Klasse} an einem bestimmten Index zurück.
	 *
	 * @param index
	 *            Der Index
	 * @return Klasse
	 */
	// TODO KLASSEN war vorher eine Liste, deshalb der Zugriff über Index. Jetzt ist das unschön.
	public static Klasse getKlasse(final int index) {
		return (Klasse) KLASSEN.values().toArray()[index];
	}

	/**
	 * Erstellt einen neuen {@link Spieler} mit der Klasse am gegebenen Index.
	 *
	 * @param index
	 *            Der Index
	 * @return Spieler
	 */
	public static Spieler getSpieler(final int index) {
		// final Spieler s = new Spieler(KLASSEN.get(index));
		final Spieler s = new Spieler((Klasse) KLASSEN.values().toArray()[index]);
		return s;
	}

	/**
	 * Gibt zu eine {@link Klasse} den Namen des {@link Pfad}s zurück.
	 *
	 * @param klassenname
	 *            Die Klasse
	 * @return Der Pfad, zu dem die Klasse gehört
	 */
	public static String getPfadByKlassenName(final String klassenname) {
		for (final Pfad pfad : PFADE) {
			for (final Klasse klasse : pfad.getKlassen()) {
				if (klasse.getName().equals(klassenname)) {
					return pfad.getName();
				}
			}
		}
		return "Gibts nicht!";
	}
}
