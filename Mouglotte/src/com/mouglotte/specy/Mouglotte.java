package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.mouglotte.game.GameState;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.genetics.Karyotype;
import com.mouglotte.graphics.MouglotteGraph;
import com.mouglotte.graphics.MouglotteListener;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Path;
import com.mouglotte.map.Tile;
import com.mouglotte.map.UnitMover;
import com.mouglotte.utilities.Debug;
import com.mouglotte.utilities.MouglotteUtilities;

public class Mouglotte {

	// Distance de promenade au hasard
	private final int WALK_AROUND_DISTANCE = 10;

	// Jeu
	private GameState game;

	// Caryotype
	private Karyotype karyotype;
	// M�moire
	private Memories memories;
	// Traits de caract�res
	private Traits traits;
	// Besoins
	private Needs needs;
	// Envies
	private Desires desires;
	// D�cision
	private DecisionType decision = DecisionType.NEED_HUNGER;
	// Graphismes
	private MouglotteGraph graphics;

	/** Birthdate */
	private long birthDate;
	// Age
	private int age;

	// Chemin
	private Path path;

	// Action en cours
	private boolean actionInProgress;
	// Temps �coul�
	private int pastTime = 0;
	private int secondesR = 0;
	private int minutes = 0;
	private int hours = 0;
	private int days = 0;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            GameState
	 */

	public Mouglotte(GameState game) {

		// Jeu
		this.game = game;
		// Caryotype
		this.karyotype = new Karyotype();
		// M�moire
		this.memories = new Memories(getGeneValue("ZE"));
		// Traits de caract�re
		this.traits = new Traits(this);
		// Besoins
		this.needs = new Needs(this);
		// Envies
		this.desires = new Desires(this);

		// Date de naissance
		this.birthDate = System.currentTimeMillis();

		// Graphismes
		this.graphics = new MouglotteGraph(this, this.game);

		// Initialisation des traits de caract�res
		initTraits();
		// Initialisation des besoins
		initNeeds();
		// Initialisation des envies
		initDesires();
	}

	/**
	 * Define current location
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */

	public void setLocation(int x, int y) {
		this.graphics.setLocation(x, y);
	}

	/**
	 * Define current path
	 * 
	 * @param path
	 *            Current path
	 */

	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Get age
	 * 
	 * @return Age
	 */

	public int getAge() {
		return this.age;
	}

	/**
	 * Get x position
	 * 
	 * @return x position
	 */
	public int getX() {
		return this.graphics.getX();
	}

	/**
	 * Get y position
	 * 
	 * @return y position
	 */
	public int getY() {
		return this.graphics.getY();
	}

	/**
	 * Get decision type
	 * 
	 * @return Decision type
	 */
	public DecisionType getDecision() {
		return this.decision;
	}

	/**
	 * Get hunger need value
	 * 
	 * @return Hunger need value
	 */
	public int getNeedHungerValue() {
		return this.needs.get(NeedType.HUNGER).getValue();
	}

	/**
	 * Get rest need value
	 * 
	 * @return Rest need value
	 */
	public int getNeedRestValue() {
		return this.needs.get(NeedType.REST).getValue();
	}

	/**
	 * Get social need value
	 * 
	 * @return Social need value
	 */
	public int getNeedSocialValue() {
		return this.needs.get(NeedType.SOCIAL).getValue();
	}

	/**
	 * Get fun need value
	 * 
	 * @return Fun need value
	 */
	public int getNeedFunValue() {
		return this.needs.get(NeedType.FUN).getValue();
	}

	/**
	 * Get hunger desire value
	 * 
	 * @return Hunger desire value
	 */
	public int getDesireHungerValue() {
		return this.desires.get(DesireType.HUNGER).getValue();
	}

	/**
	 * Get rest desire value
	 * 
	 * @return Rest desire value
	 */
	public int getDesireRestValue() {
		return this.desires.get(DesireType.REST).getValue();
	}

	/**
	 * Get social desire value
	 * 
	 * @return Social desire value
	 */
	public int getDesireSocialValue() {
		return this.desires.get(DesireType.SOCIAL).getValue();
	}

	/**
	 * Get fun desire value
	 * 
	 * @return Fun desire value
	 */
	public int getDesireFunValue() {
		return this.desires.get(DesireType.FUN).getValue();
	}

	/**
	 * Get love desire value
	 * 
	 * @return Love desire value
	 */
	public int getDesireLoveValue() {
		return this.desires.get(DesireType.LOVE).getValue();
	}

	/**
	 * Get fight desire value
	 * 
	 * @return Fight desire value
	 */
	public int getDesireFightValue() {
		return this.desires.get(DesireType.FIGHT).getValue();
	}

	/**
	 * Get work desire value
	 * 
	 * @return Work desire value
	 */
	public int getDesireWorkValue() {
		return this.desires.get(DesireType.WORK).getValue();
	}

	/**
	 * Is mouglotte selected
	 * 
	 * @return true if mouglotte is selected
	 */
	public boolean isSelected() {
		return this.graphics.isSelected();
	}

	// Naissance
	// public static Mouglotte birth() {
	//
	// return new Mouglotte();
	// }

	/**
	 * Initialization of the traits
	 */
	private void initTraits() {

		// Traits, Type, birthValue, babyGrowth, childGrowth, adultGrowth,
		// oldGrowth, exerciseGain, exerciceLoss

		// Force
		Trait trait = new Trait(this.traits, TraitType.STRENGTH,
				getGeneValue("AA"), getGeneValue("CF"), getGeneValue("GY"),
				getGeneValue("BS"), getGeneValue("RT"));
		this.traits.put(trait);

	}

	/**
	 * Initialization of the needs
	 */
	private void initNeeds() {

		// Needs, Type, babyHourGain, childHourGain, adultHourGain, oldHourGain,
		// fulfillLoss

		// Faim
		Need need = new Need(this.needs, NeedType.HUNGER, getGeneValue("MQ"),
				getGeneValue("JK"), getGeneValue("NS"), getGeneValue("IZ"),
				getGeneValue("VS"));
		this.needs.put(need);
		// Repos
		need = new Need(this.needs, NeedType.REST, getGeneValue("YU"),
				getGeneValue("ED"), getGeneValue("JI"), getGeneValue("MA"),
				getGeneValue("CS"));
		this.needs.put(need);
		// Social
		need = new Need(this.needs, NeedType.SOCIAL, getGeneValue("LM"),
				getGeneValue("YA"), getGeneValue("FD"), getGeneValue("OO"),
				getGeneValue("IT"));
		this.needs.put(need);
		// Divertissement
		need = new Need(this.needs, NeedType.FUN, getGeneValue("QN"),
				getGeneValue("UY"), getGeneValue("CV"), getGeneValue("TT"),
				getGeneValue("XS"));
		this.needs.put(need);
	}

	/**
	 * Initialization of the desires
	 */
	private void initDesires() {

		// Desires, Type, babyValue, childValue, adultValue, oldValue,
		// fulfillLoss

		// Faim
		Desire desire = new Desire(this.desires, DesireType.HUNGER,
				getGeneValue("AD"), getGeneValue("FF"), getGeneValue("BG"),
				getGeneValue("CX"), getGeneValue("UR"));
		desire.setRelatedNeed(this.needs.get(NeedType.HUNGER));
		this.desires.put(desire);

		// Repos
		desire = new Desire(this.desires, DesireType.REST, getGeneValue("EZ"),
				getGeneValue("FC"), getGeneValue("HE"), getGeneValue("NZ"),
				getGeneValue("PS"));
		desire.setRelatedNeed(this.needs.get(NeedType.REST));
		this.desires.put(desire);

		// Social
		desire = new Desire(this.desires, DesireType.SOCIAL,
				getGeneValue("JU"), getGeneValue("LS"), getGeneValue("MD"),
				getGeneValue("NC"), getGeneValue("ID"));
		desire.setRelatedNeed(this.needs.get(NeedType.SOCIAL));
		this.desires.put(desire);

		// Divertissement
		desire = new Desire(this.desires, DesireType.FUN, getGeneValue("KZ"),
				getGeneValue("DP"), getGeneValue("JA"), getGeneValue("SO"),
				getGeneValue("BQ"));
		desire.setRelatedNeed(this.needs.get(NeedType.FUN));
		this.desires.put(desire);

		// Amour
		desire = new Desire(this.desires, DesireType.LOVE, getGeneValue("IS"),
				getGeneValue("GZ"), getGeneValue("DA"), getGeneValue("DF"),
				getGeneValue("FZ"));
		this.desires.put(desire);

		// Combat
		desire = new Desire(this.desires, DesireType.FIGHT, getGeneValue("JL"),
				getGeneValue("NE"), getGeneValue("SC"), getGeneValue("YE"),
				getGeneValue("KS"));
		this.desires.put(desire);

		// Travail
		desire = new Desire(this.desires, DesireType.WORK, getGeneValue("ZJ"),
				getGeneValue("US"), getGeneValue("FQ"), getGeneValue("WJ"),
				getGeneValue("VT"));
		this.desires.put(desire);

	}

	/**
	 * Get gene value
	 * 
	 * @param name
	 *            The name of the gene
	 * 
	 * @return Gene value
	 */
	private int getGeneValue(String name) {
		return Genetics.getValue(name, this.karyotype);
	}

	// // Ev�nement ex�cut� par le timer
	// public void event(long time) {
	//
	// if (MouglotteUtilities.isMinute(time))
	// eventMinute();
	// else if (MouglotteUtilities.isHour(time))
	// eventHour();
	// else if (MouglotteUtilities.isDay(time))
	// eventDay();
	// else if (MouglotteUtilities.isYear(time))
	// eventYear();
	// }

	/**
	 * Update mouglotte
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	public void update(GameContainer container, int delta) {

		// Calcul du temps pass�
		this.pastTime += delta;
		this.pastTime *= GameState.TIME_FACTOR;

		// Toutes les secondes r�elles
		if (this.pastTime >= 1000) {

			// Une seconde r�elle s'est �coul�e
			this.secondesR++;
			this.pastTime -= 1000;
			// On effectue une action
			action();
		}

		// On fait un truc toutes les 3 secondes r�elles
		// = minute mouglotte
		if (this.secondesR >= 3) {

			// Une minute mouglotte s'est �coul�e
			this.minutes++;
			this.secondesR = 0;
			// Ev�nement lanc� toutes les minutes
			eventMinute();
		}

		// Toutes les 3 minutes r�elles
		// = heure mouglotte
		if (this.minutes >= 60) {

			// Une heure mouglotte s'est �coul�e
			this.hours++;
			this.minutes = 0;
			// Ev�nement lanc� toutes les heures
			eventHour();
		}

		// Toutes les heures r�elles
		// = jour mouglotte
		if (this.hours >= 20) {

			// Un jour mouglotte s'est �coul�
			this.days++;
			this.hours = 0;
			// Ev�nement lanc� tous les jours
			eventDay();
		}

	}

	/**
	 * Event called every mouglotte minute
	 */
	public void eventMinute() {

		// Les besoins et les envies �voluent
		// S'ils sont en cours d'accomplissement ils baissent

		// Pour les besoins
		this.needs.eventMinute();
		// Pour les envies
		this.desires.eventMinute();
	}

	/**
	 * Event called every mouglotte hour
	 */
	public void eventHour() {

		// Les besoins augmentent et le besoin le plus pressant est choisi
		// Une nouvelle envie est choisie si l'envie courante est en train de
		// s'accomplir ou n'a pas pu l'�tre

		// Pour les besoins
		this.needs.eventHour();
		// Pour les envies
		this.desires.eventHour();

		// Prise de d�cision
		// Le besoin ou l'envie est choisi
		decide();
	}

	/**
	 * Event called every mouglotte day
	 */
	public void eventDay() {

	}

	/**
	 * Event called every mouglotte year
	 */
	public void eventYear() {

		// Joyeux anniversaire
		this.age++;

		// Pour les traits de caract�res
		this.traits.eventYear();
	}

	/**
	 * Decide what to do
	 */
	private void decide() {

		Debug.log("MOUGLOTTE", "Mouglotte::Decide");

		// Ajouter la gestion des ordres et des solicitations ext�rieurs

		// Une nouvelle action va commencer ensuite
		this.actionInProgress = false;

		// Je veux suivre un besoin
		if (this.needs.getCurrent().getValue() > this.desires.getCurrent()
				.getValue()) {

			this.needs.setSearching(true);
			this.desires.setSearching(false);
			this.desires.setFulfilling(false);

			switch (this.needs.getCurrent().getType()) {
			case HUNGER:
				this.decision = DecisionType.NEED_HUNGER;
				break;
			case REST:
				this.decision = DecisionType.NEED_REST;
				break;
			case SOCIAL:
				this.decision = DecisionType.NEED_SOCIAL;
				break;
			case FUN:
				this.decision = DecisionType.NEED_FUN;
				break;
			}

			// Je veux suivre une envie
		} else {

			this.desires.setSearching(true);
			this.needs.setSearching(false);
			this.needs.setFulfilling(false);

			switch (this.desires.getCurrent().getType()) {
			case HUNGER:
				this.decision = DecisionType.DESIRE_HUNGER;
				break;
			case REST:
				this.decision = DecisionType.DESIRE_REST;
				break;
			case SOCIAL:
				this.decision = DecisionType.DESIRE_SOCIAL;
				break;
			case FUN:
				this.decision = DecisionType.DESIRE_FUN;
				break;
			case LOVE:
				this.decision = DecisionType.DESIRE_LOVE;
				break;
			case FIGHT:
				this.decision = DecisionType.DESIRE_FIGHT;
				break;
			case WORK:
				this.decision = DecisionType.DESIRE_WORK;
				break;
			}
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Decide:Decision=" + this.decision);
	}

	/**
	 * Do an action
	 */
	private void action() {

		Debug.log("MOUGLOTTE", "Mouglotte::Action");

		// Pour les tests
		if (this.graphics.isSelected()) {
			continueAction();
			return;
		}

		// Rechercher si l'action peut �tre conclue
		// (nourriture � proximit�,...)
		actionFulfilled();

		// Une action va � son terme sauf si un �v�nement arrive
		if (this.actionInProgress)
			continueAction();
		else
			newAction();
	}

	/**
	 * Try to fulfill the current action
	 */
	private void actionFulfilled() {

		Debug.log("MOUGLOTTE", "Mouglotte::ActionFulFilled");

		MemoryType memoryType = null;

		// Qu'est ce que la mouglotte recherche ?
		switch (this.decision) {
		case NEED_HUNGER:
		case DESIRE_HUNGER:
			memoryType = MemoryType.FOOD;
			break;
		case NEED_SOCIAL:
		case DESIRE_SOCIAL:
			memoryType = MemoryType.FRIEND;
			break;
		case DESIRE_LOVE:
			memoryType = MemoryType.LOVER;
			break;
		case DESIRE_FIGHT:
			memoryType = MemoryType.ENEMY;
			break;
		case DESIRE_WORK:
			memoryType = MemoryType.WORK;
			break;
		default:
			break;
		}

		// Recherche � c�t�
		Tile tile = this.game.getMap().searchNear(memoryType,
				this.graphics.getX(), this.graphics.getY());

		// La mouglotte a trouv� ce qu'elle cherche
		if (tile != null) {

			// La destination est annul�e
			this.path = null;

			// La mouglotte est au bon endroit
			if (tile.contains(this.graphics.getX(), this.graphics.getY()))
				// Accomplissons
				fulfill();

			// La mouglotte est � c�t� de l'endroit
			else
				// Allons-y
				goTo(tile.getX(), tile.getY());

			// La mouglotte n'a pas trouv� ce qu'elle cherche
		} else {

			// Si la mouglotte est � destination
			if (this.path == null)
				// L'action est termin�e, on n'a rien trouv�
				// Une nouvelle action va �tre d�clench�e
				this.actionInProgress = false;
		}
	}

	/**
	 * Begin a new action
	 */
	private void newAction() {

		Debug.log("MOUGLOTTE", "Mouglotte::NewAction");

		MemoryType memoryType = null;
		Memory memory = null;

		// Action en cours
		this.actionInProgress = true;

		// Recherche d'un souvenir concernant la d�cision en cours
		if (this.decision != null)
			switch (this.decision) {
			case NEED_HUNGER:
			case DESIRE_HUNGER:
				memoryType = MemoryType.FOOD;
				break;
			case NEED_SOCIAL:
			case DESIRE_SOCIAL:
				memoryType = MemoryType.FRIEND;
				break;
			case DESIRE_LOVE:
				memoryType = MemoryType.LOVER;
				break;
			case DESIRE_FIGHT:
				memoryType = MemoryType.ENEMY;
				break;
			case DESIRE_WORK:
				memoryType = MemoryType.WORK;
				break;
			default:
				break;
			}

		// R�cup�ration du souvenir le plus proche
		if (memoryType != null)
			memory = this.memories.getCloser(memoryType, this.graphics.getX(),
					this.graphics.getY());

		// Aller � l'endroit du souvenir
		if (memory != null)
			goTo(memory.getX(), memory.getY());
		// Rien � faire
		else
			nothingToDo();
	}

	/**
	 * Continue the current action
	 */
	private void continueAction() {

		Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction");

		// Marcher
		walk();
	}

	/**
	 * Goto somewhere
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	public void goTo(int x, int y) {

		Debug.log("MOUGLOTTE", "Mouglotte::GoTo " + x + "," + y);

		// Si la souris est sortie de la carte on ne fait rien
		if (!this.game.getMap().contains(x, y))
			return;

		// R�initialisation des zones visit�es
		this.game.getMap().clearVisited();

		// Recherche du chemin
		setPath(this.game.getMap().findPath(new UnitMover(3), getX(), getY(),
				x, y));
	}

	/**
	 * Nothing to do, try to find what to do (except needs, desires or orders)
	 */

	private void nothingToDo() {

		Random r = new Random();

		// Une chance sur 2 de ne rien faire
		if (r.nextBoolean() == true)
			idle();
		// Une chance sur 2 de se promener
		else
			walkAround();
	}

	/**
	 * Walk around
	 */

	private void walkAround() {

		Debug.log("MOUGLOTTE", "Mouglotte::WalkAround");

		Random r = new Random();
		int destX = 0, destY = 0;

		// Une chance sur 2 de continuer dans la m�me direction
		if (r.nextBoolean() == true) {

			// Direction actuelle
			int dirX = this.graphics.getX() - this.graphics.getLastX();
			int dirY = this.graphics.getY() - this.graphics.getLastY();

			// La mouglotte marchait, on continue dans la m�me direction
			if (dirX != 0)
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * dirX
						/ Math.abs(dirX);
			if (dirY != 0)
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * dirY
						/ Math.abs(dirY);

			// On va dans une direction au hasard
			// (y compris la direction actuelle pour simplifier)
		} else {

			switch (r.nextInt(8)) {
			case 0:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			case 1:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			case 2:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
				break;
			case 3:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 4:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 5:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 6:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
				break;
			case 7:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			}
		}

		// Aller � la destination trouv�e
		goTo(destX, destY);
	}

	/**
	 * Walk or continue walking
	 */
	private void walk() {

		Debug.log("MOUGLOTTE", "Mouglotte::Walk");

		if (this.path != null) {

			// Le step renvoie une position en zone

			// D�placement direct au centre de la zone
			// setLocation(this.path.getStep(0).getX()
			// * this.game.getMap().TILE_SIZE
			// + this.game.getMap().TILE_SIZE / 2, this.path.getStep(0)
			// .getY()
			// * this.game.getMap().TILE_SIZE
			// + this.game.getMap().TILE_SIZE / 2);

			this.game.getMap();
			this.game.getMap();
			// On doit aller au milieu de la prochaine zone
			int destX = this.path.getStep(0).getX() * GameMap.TILE_SIZE
					+ GameMap.TILE_SIZE / 2;
			this.game.getMap();
			this.game.getMap();
			int destY = this.path.getStep(0).getY() * GameMap.TILE_SIZE
					+ GameMap.TILE_SIZE / 2;

			// On avance d'un pixel dans cette direction
			int dirX = destX - getX();
			if (dirX != 0)
				dirX /= Math.abs(dirX);
			int dirY = destY - getY();
			if (dirY != 0)
				dirY /= Math.abs(dirY);

			setLocation(getX() + dirX, getY() + dirY);

			// Si on est arriv� au centre de la zone
			if (getX() + dirX == destX && getY() + dirY == destY)
				// L'�tape est termin�e, on la supprime
				this.path.removeStep(0);

			// S'il n'y a plus d'�tape alors le chemin est termin�
			if (this.path.getLength() == 0)
				this.path = null;
		}
	}

	/**
	 * Idle
	 */

	private void idle() {

		Debug.log("MOUGLOTTE", "Mouglotte::Idle");
	}

	/**
	 * Fulfill current need, desire or order
	 */
	private void fulfill() {

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill");

		// A d�cliner en v�ritable actions
		// eat(), talk(), fuck(),...

		if (this.decision != null)
			switch (this.decision) {
			case NEED_HUNGER:
			case NEED_REST:
			case NEED_SOCIAL:
			case NEED_FUN:
				this.needs.setFulfilling(true);
				break;
			case DESIRE_HUNGER:
			case DESIRE_REST:
			case DESIRE_SOCIAL:
			case DESIRE_FUN:
			case DESIRE_LOVE:
			case DESIRE_FIGHT:
			case DESIRE_WORK:
				this.desires.setFulfilling(true);
				break;
			default:
				break;
			}
	}

	/**
	 * Render mouglotte
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		this.graphics.render(container, g);
	}
}
