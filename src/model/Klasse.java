package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Klasse, z.B. Magier, Schurke usw.
 *
 * @author Roman Schmidt
 *
 */
public class Klasse {

	/** Name der Klasse */
	private String name;

	/** Die maximalen HP */
	private int maxHP;

	/** Die maximalen SSJ-Punkte */
	private int maxSSJ;

	/** Das Deck */
	private List<Karte> deck;

	/** Name des Ordners mit den Bildern für die Animation */
	private String animationFolder;

	/** Exp, die zum Freischalten dieser Klasse erforderlich sind */
	private int requiredExp;

	/** True, falls die Klasse freigeschaltet wurde */
	private boolean freigeschaltet;

	/** Besondere Effekte (vor dem Zug), die diese Klasse von Anfang an hat */
	private List<Effekt> effectsVorDemZug;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name
	 * @param maxHP
	 *            Maximale HP
	 * @param maxSSJ
	 *            Maximale SSJ-Punkte
	 * @param deck
	 *            Deck
	 * @param animationFolder
	 *            Ordner mit den Animationsbildern
	 * @param requiredExp
	 *            Erforderliche Exp zum freischalten
	 * @param effectsVorDemZug
	 *            Besondere Effekte, die diese Klasse von Anfang an hat
	 */
	public Klasse(final String name, final int maxHP, final int maxSSJ, final List<Karte> deck, final String animationFolder, final int requiredExp, final List<Effekt> effectsVorDemZug) {
		this.name = name;
		this.maxHP = maxHP;
		this.maxSSJ = maxSSJ;
		this.deck = deck;
		this.animationFolder = animationFolder;
		this.requiredExp = requiredExp;
		this.effectsVorDemZug = effectsVorDemZug;
	}

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Name
	 * @param maxHP
	 *            Maximale HP
	 * @param maxSSJ
	 *            Maximale SSJ-Punkte
	 * @param deck
	 *            Deck
	 * @param animationFolder
	 *            Ordner mit den Animationsbildern
	 * @param requiredExp
	 *            Erforderliche Exp zum freischalten
	 */
	public Klasse(final String name, final int maxHP, final int maxSSJ, final List<Karte> deck, final String animationFolder, final int requiredExp) {
		this(name, maxHP, maxSSJ, deck, animationFolder, requiredExp, new ArrayList<Effekt>());
	}

	/**
	 * @return the startEffects
	 */
	public List<Effekt> getEffectsVorDemZug() {
		return effectsVorDemZug;
	}

	/**
	 * @return the freigeschaltet
	 */
	public boolean isFreigeschaltet() {
		return freigeschaltet;
	}

	/**
	 * @param freigeschaltet
	 *            the freigeschaltet to set
	 */
	public void setFreigeschaltet(final boolean freigeschaltet) {
		this.freigeschaltet = freigeschaltet;
	}

	/**
	 * @return the exp
	 */
	public int getRequiredExp() {
		return requiredExp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the maxHP
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * @return the maxSSJ
	 */
	public int getMaxSSJ() {
		return maxSSJ;
	}

	/**
	 * @return the deck
	 */
	public List<Karte> getDeck() {
		return deck;
	}

	/**
	 * @return the animationFolder
	 */
	public String getAnimationFolder() {
		return animationFolder;
	}
	
	

}
