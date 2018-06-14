package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import effekte.InfoEffekt;
import effekte.SSJControlEffekt;

/**
 * Repräsentiert einen Spieler in einem Match.
 *
 * @author Roman Schmidt
 *
 */
public class Spieler implements Serializable {

	private static final long serialVersionUID = 3542322237440458822L;

	// #-------Konstanten-----#

	/** Die Anzahl der Handkarten */
	public static final int ANZAHL_HANDKARTEN = 5;

	/** Standardmäßiger Atk- und Def-Boost wenn man kein SSJ ist */
	public static final int DEFAULT_BOOSTS = 0;

	/** Standardmäßiger Atk- und Def-Boost wenn man SSJ ist */
	public static final int SSJ_BOOSTS = 2;

	// #-------Attribute des Spielers-----#

	/** Name des Spielers */
	private String spielerName = "Anonym";

	/** Aktuelle HP */
	private int hp;

	/** Aktuelle SSJ-Punkte */
	private int ssjPoints;

	/** Der aktuelle Atk-Boost */
	private int atkBoost = 0;

	/** Der aktuelle Def-Boost */
	private int defBoost = 0;

	/** True, falls der Spieler SSJ ist */
	private boolean ssj = false;

	/** Die aktuellen Handkarten */
	Karte[] hand = new Karte[ANZAHL_HANDKARTEN];

	/** Der Gegner */
	private Spieler gegner;

	// #-------Attribute von der Klasse-----#

	/** Name der Klasse */
	private final String klassenName;

	/** Maximale HP */
	private int maxHP;

	/** Maximale SSJ-Punkte */
	private int maxSSJPoints;

	/** Das Deck */
	private final List<Karte> deck;

	/** Summe der Mächtigkeiten aller Karten im Deck */
	private int gesamtMaechtigkeit;

	/** Pfad zu den Bildern der Klasse */
	private String animationFolder;

	/** Effekte, die vor dem Ausspielen einer Karte ausgeführt werden */
	private List<Effekt> effekteVorDemZug = new ArrayList<Effekt>();

	/** Effekte, die nach dem Ausspielen einer Karte ausgeführt werden */
	private List<Effekt> effekteNachDemZug = new ArrayList<Effekt>();

	// #-------Attribute für Specialkarten-----#
	// #--Einsicht--#
	/** True, falls der Spieler die Karten des Gegners sieht */
	private boolean siehtGegnerkarten = false;

	// #--Konter--#
	/** True, falls der nächste erhaltene Schaden auf den Gegner reflektiert wird */
	private boolean kontertDenNaechsten = false;

	// #--Geblendet--#
	/** True, falls der Spieler seine eigenen Karten nicht sehen kann */
	private boolean istGeblendet = false;

	// #-------Effekte-----#

	private InfoEffekt atkEffekt = new InfoEffekt("images/effektIcons/atk.png", "Atk: 0");
	private InfoEffekt defEffekt = new InfoEffekt("images/effektIcons/def.png", "Def: 0");

	/** PropertyChangeSupport */
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * Adds a {@link PropertyChangeListener}.
	 *
	 * @param listener
	 *            Der Listener
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a {@link PropertyChangeListener}.
	 *
	 * @param listener
	 *            Der Listener
	 */
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Constructor.
	 *
	 * @param klasse
	 *            Die Klasse die gespielt wird
	 */
	public Spieler(final Klasse klasse) {
		ssjPoints = 0;
		maxSSJPoints = klasse.getMaxSSJ();
		hp = klasse.getMaxHP();
		maxHP = klasse.getMaxHP();
		deck = new ArrayList<Karte>(klasse.getDeck());
		klassenName = klasse.getName();
		animationFolder = klasse.getAnimationFolder();
		standardEffekte(klasse);
		berechneGesamtMaechtigkeit();
		anfangsKartenZiehen();
	}

	/**
	 * Constructor.
	 *
	 * @param copy
	 *            Spieler, der kopiert werden soll
	 */
	public Spieler(final Spieler copy) {
		spielerName = copy.spielerName;
		ssjPoints = copy.ssjPoints;
		maxSSJPoints = copy.maxSSJPoints;
		hp = copy.hp;
		maxHP = copy.maxHP;
		deck = new ArrayList<>(copy.deck);
		klassenName = copy.klassenName;
		animationFolder = copy.animationFolder;
		hand = Arrays.copyOf(copy.hand, copy.hand.length);
		gegner = copy.gegner;
		atkBoost = copy.atkBoost;
		defBoost = copy.defBoost;
		ssj = copy.ssj;
		gesamtMaechtigkeit = copy.gesamtMaechtigkeit;
		effekteVorDemZug = new ArrayList<>(copy.effekteVorDemZug);
		effekteNachDemZug = new ArrayList<>(copy.effekteNachDemZug);
		siehtGegnerkarten = copy.siehtGegnerkarten;
		kontertDenNaechsten = copy.kontertDenNaechsten;
		istGeblendet = copy.istGeblendet;
	}

	/**
	 * Fügt dem Spieler am Anfang des Spiels Effekte hinzu. Aktuell: {@link SSJControlEffekt}
	 *
	 * @param klasse
	 */
	private void standardEffekte(final Klasse klasse) {
		addEffektVorDemZug(atkEffekt);
		addEffektVorDemZug(defEffekt);
		addEffektVorDemZug(new SSJControlEffekt("images/effektIcons/lightningIcon.png"));
		for (final Effekt effekt : klasse.getEffectsVorDemZug()) {
			addEffektVorDemZug(effekt);
		}
	}

	/**
	 * Zieht die ersten Karten. Siehe {@link Spieler#ANZAHL_HANDKARTEN}
	 */
	private void anfangsKartenZiehen() {
		for (int i = 0; i < hand.length; i++) {
			zieheKarte();
		}
	}

	/**
	 * Zieht eine Karte falls Platz auf der Hand ist.
	 */
	public void zieheKarte() {
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null) {
				hand[i] = waehleKarte();
				break;
			}
		}
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Zieht alle Handkarten neu.
	 */
	public void zieheNeueHand() {
		for (int i = 0; i < hand.length; i++) {
			hand[i] = null;
			zieheKarte();
		}
	}

	/**
	 * Gibt eine zufällig ausgewählte {@link Karte} aus dem Deck zurück. Hier wird mit den Mächtigkeiten gerechnet.
	 *
	 * @return Eine Karte
	 */
	private Karte waehleKarte() {
		final int ran = new Random().nextInt(gesamtMaechtigkeit);
		int uebertrag = 0;
		for (int i = 0; i < deck.size(); i++) {
			if (ran <= deck.get(i).getMaechtigkeit() + uebertrag) {
				return deck.get(i);
			} else {
				uebertrag += deck.get(i).getMaechtigkeit();
			}
		}
		return null;
	}

	/**
	 * Berechnet die Summe der Mächtigkeiten aller Karten im Deck
	 */
	private void berechneGesamtMaechtigkeit() {
		for (final Karte karte : deck) {
			gesamtMaechtigkeit += karte.getMaechtigkeit();
		}
	}

	/**
	 * Führt die Karte am gegebenen Index aus und entfernt sie aus der Hand.
	 *
	 * @param index
	 *            Index der Handkarte
	 */
	public void spieleKarte(final int index) {
		hand[index].ausfuehren(this, gegner);
		hand[index] = null;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(final int hp) {
		this.hp = hp;
		if (hp > maxHP) {
			this.hp = maxHP;
		}
		if (hp < 0) {
			this.hp = 0;
		}
		propertyChangeSupport.firePropertyChange("HP", null, hp);// TODO enum für propertyNames
	}

	/**
	 * @return the deck
	 */
	public List<Karte> getDeck() {
		return deck;
	}

	/**
	 * @return the hand
	 */
	public Karte[] getHand() {
		return hand;
	}

	/**
	 * @return the gegner
	 */
	public Spieler getGegner() {
		return gegner;
	}

	/**
	 * @param gegner
	 *            the gegner to set
	 */
	public void setGegner(final Spieler gegner) {
		this.gegner = gegner;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the maxHP
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * @return the atkBoost
	 */
	public int getAtkBoost() {
		return atkBoost;
	}

	/**
	 * @param atkBoost
	 *            the atkBoost to set
	 */
	public void setAtkBoost(final int atkBoost) {
		this.atkBoost = atkBoost;
		atkEffekt.setText("Atk: " + atkBoost);
		if (atkBoost > 0) {
			atkEffekt.setIconPath("images/effektIcons/atkboost.png");
		} else if (atkBoost < 0) {
			atkEffekt.setIconPath("images/effektIcons/atkreduce.png");
		} else {
			atkEffekt.setIconPath("images/effektIcons/atk.png");
		}
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the defBoost
	 */
	public int getDefBoost() {
		return defBoost;
	}

	/**
	 * @param defBoost
	 *            the defBoost to set
	 */
	public void setDefBoost(final int defBoost) {
		this.defBoost = defBoost;
		defEffekt.setText("Def: " + defBoost);
		if (defBoost > 0) {
			defEffekt.setIconPath("images/effektIcons/defboost.png");
		} else if (defBoost < 0) {
			defEffekt.setIconPath("images/effektIcons/defreduce.png");
		} else {
			defEffekt.setIconPath("images/effektIcons/def.png");
		}
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the klassenName
	 */
	public String getKlassenName() {
		return klassenName;
	}

	/**
	 * @return the animationFolder
	 */
	public String getAnimationFolder() {
		return animationFolder;
	}

	/**
	 * @param animationFolder
	 *            the animationFolder to set
	 */
	public void setAnimationFolder(final String animationFolder) {
		this.animationFolder = animationFolder;
	}

	/**
	 * @return the siehtGegnerkarten
	 */
	public boolean isSiehtGegnerkarten() {
		return siehtGegnerkarten;
	}

	/**
	 * @param maxHP
	 *            the maxHP to set
	 */
	public void setMaxHP(final int maxHP) {
		this.maxHP = maxHP;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the kontertDenNaechsten
	 */
	public boolean isKontertDenNaechsten() {
		return kontertDenNaechsten;
	}

	/**
	 * @param kontertDenNaechsten
	 *            the kontertDenNaechsten to set
	 */
	public void setKontertDenNaechsten(final boolean kontertDenNaechsten) {
		this.kontertDenNaechsten = kontertDenNaechsten;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the spielerName
	 */
	public String getSpielerName() {
		return spielerName;
	}

	/**
	 * @return the ssjPoints
	 */
	public int getSsjPoints() {
		return ssjPoints;
	}

	/**
	 * @param ssjPoints
	 *            the ssjPoints to set
	 */
	public void setSsjPoints(final int ssjPoints) {
		this.ssjPoints = ssjPoints;
		if (ssjPoints >= maxSSJPoints) {
			this.ssjPoints = maxSSJPoints;
			ssj = true;
			if (atkBoost < SSJ_BOOSTS) {
				setAtkBoost(SSJ_BOOSTS);
			}
			if (defBoost < SSJ_BOOSTS) {
				setDefBoost(SSJ_BOOSTS);
			}
		} else if (ssjPoints <= 0) {
			this.ssjPoints = 0;
			ssj = false;
		}
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the maxSSJPoints
	 */
	public int getMaxSSJPoints() {
		return maxSSJPoints;
	}

	/**
	 * @param maxSSJPoints
	 *            the maxSSJPoints to set
	 */
	public void setMaxSSJPoints(final int maxSSJPoints) {
		this.maxSSJPoints = maxSSJPoints;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the ssj
	 */
	public boolean isSsj() {
		return ssj;
	}

	/**
	 * Gibt den minimalen Atk- und Def-Boost zurück. Hängt davon ab, ob der Spieler SSJ ist.
	 *
	 * @return
	 */
	public int getMinBoost() {
		if (ssj) {
			return SSJ_BOOSTS;
		} else {
			return DEFAULT_BOOSTS;
		}
	}

	/**
	 * @param spielerName
	 *            the spielerName to set
	 */
	public void setSpielerName(final String spielerName) {
		this.spielerName = spielerName;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @return the istGeblendet
	 */
	public boolean isIstGeblendet() {
		return istGeblendet;
	}

	/**
	 * @return the effekteVorDemZug
	 */
	public List<Effekt> getEffekteVorDemZug() {
		return effekteVorDemZug;
	}

	/**
	 * @return the effekteNachDemZug
	 */
	public List<Effekt> getEffekteNachDemZug() {
		return effekteNachDemZug;
	}

	/**
	 * @param istGeblendet
	 *            the istGeblendet to set
	 */
	public void setIstGeblendet(final boolean istGeblendet) {
		this.istGeblendet = istGeblendet;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * @param siehtGegnerkarten
	 *            the siehtGegnerkarten to set
	 */
	public void setSiehtGegnerkarten(final boolean siehtGegnerkarten) {
		this.siehtGegnerkarten = siehtGegnerkarten;
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Fügt dem Spieler einen {@link Effekt} hinzu, der jede Runde vor dem Ausspielen einer Karte ausgeführt wird.
	 *
	 * @param effekt
	 *            Der Effekt
	 */
	public void addEffektVorDemZug(final Effekt effekt) {
		effekteVorDemZug.add(effekt);
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Fügt dem Spieler einen {@link Effekt} hinzu, der jede Runde nach dem Ausspielen einer Karte ausgeführt wird.
	 *
	 * @param effekt
	 *            Der Effekt
	 */
	public void addEffektNachDemZug(final Effekt effekt) {
		effekteNachDemZug.add(effekt);
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Entfernt einen {@link Effekt}, der vor dem Ausspielen einer Karte ausgeführt wurde.
	 *
	 * @param effekt
	 *            Der Effekt
	 */
	public void removeEffektVorDemZug(final Effekt effekt) {
		effekteVorDemZug.remove(effekt);
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Entfernt einen {@link Effekt}, der nach dem Ausspielen einer Karte ausgeführt wurde.
	 *
	 * @param effekt
	 *            Der Effekt
	 */
	public void removeEffektNachDemZug(final Effekt effekt) {
		effekteNachDemZug.remove(effekt);
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Prüft, ob der Spieler einen {@link Effekt} hat, der vor dem Ausspielen einer Karte ausgeführt wird.
	 *
	 * @param effekt
	 *            Der zu suchende Effekt.
	 * @return True, falls vorhanden.
	 */
	public boolean hatEffektVorDemZug(final Effekt effekt) {
		return effekteVorDemZug.contains(effekt);
	}

	/**
	 * Prüft, ob der Spieler einen {@link Effekt} hat, der nach dem Ausspielen einer Karte ausgeführt wird.
	 *
	 * @param effekt
	 *            Der zu suchende Effekt
	 * @return True, falls vorhanden
	 */
	public boolean hatEffektNachDemZug(final Effekt effekt) {
		return effekteNachDemZug.contains(effekt);
	}

	/**
	 * Entfernt alle {@link Effekt}e, bei denen {@link Effekt#entfernbarDurchEntwaffnen()} true zurückgibt.
	 */
	public void entwaffnen() {
		for (int i = 0; i < effekteVorDemZug.size(); i++) {
			if (effekteVorDemZug.get(i).entfernbarDurchEntwaffnen()) {
				effekteVorDemZug.remove(i);
				i--;
			}
		}
		for (int i = 0; i < effekteNachDemZug.size(); i++) {
			if (effekteNachDemZug.get(i).entfernbarDurchEntwaffnen()) {
				effekteNachDemZug.remove(i);
				i--;
			}
		}
	}

	/**
	 * Entfernt alle {@link Effekt}e, bei denen {@link Effekt#entfernbarDurchSegen()} true zurückgibt.
	 */
	public void segen() {
		for (int i = 0; i < effekteVorDemZug.size(); i++) {
			if (effekteVorDemZug.get(i).entfernbarDurchSegen()) {
				effekteVorDemZug.remove(i);
				i--;
			}
		}
		for (int i = 0; i < effekteNachDemZug.size(); i++) {
			if (effekteNachDemZug.get(i).entfernbarDurchSegen()) {
				effekteNachDemZug.remove(i);
				i--;
			}
		}
	}

	/**
	 * Wird vor dem Ausspielen einer Karte ausgeführt. Führt die entsprechenden {@link Effekt}e aus und entfernt
	 * inaktive/abgelaufene Effekte.
	 */
	public void vorDemSpielzug() {
		for (int i = 0; i < effekteVorDemZug.size(); i++) {
			effekteVorDemZug.get(i).ausfuehren(this);
		}
		removeInaktiveEffekte();
	}

	/**
	 * Wird nach dem Ausspielen einer Karte ausgeführt. Führt die entsprechenden {@link Effekt}e aus und entfernt
	 * inaktive/abgelaufene Effekte.
	 */
	public void nachDemSpielzug() {
		for (final Effekt effekt : effekteNachDemZug) {
			effekt.ausfuehren(this);
		}
		removeInaktiveEffekte();
		propertyChangeSupport.firePropertyChange("", null, this);
	}

	/**
	 * Entfernt alle {@link Effekt}e, bei denen {@link Effekt#istAktiv()} false zurückgibt.
	 */
	private void removeInaktiveEffekte() {
		for (int i = 0; i < effekteVorDemZug.size(); i++) {
			if (!effekteVorDemZug.get(i).istAktiv()) {
				effekteVorDemZug.remove(i);
				i--;
			}
		}
		for (int i = 0; i < effekteNachDemZug.size(); i++) {
			if (!effekteNachDemZug.get(i).istAktiv()) {
				effekteNachDemZug.remove(i);
				i--;
			}
		}
	}

	@Override
	public String toString() {
		return "Spieler [hp=" + hp + ", deck=" + deck + ", hand=" + Arrays.toString(hand) + ", gesamtMaechtigkeit=" + gesamtMaechtigkeit + "]";
	}
}
