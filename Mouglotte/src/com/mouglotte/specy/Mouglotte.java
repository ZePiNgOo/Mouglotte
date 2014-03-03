package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.mouglotte.entities.EntityInterface;
import com.mouglotte.entities.MouglotteEntity;
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

public class Mouglotte implements EntityInterface {

	// Distance de promenade au hasard
	private final int WALK_AROUND_DISTANCE = 1000;

	// Jeu
	// private GameState game;
	/** Entity */
	private MouglotteEntity entity;

	// Caryotype
	private Karyotype karyotype;
	// Mémoire
	private Memories memories;
	// Traits de caractères
	private Traits traits;
	// Besoins
	private Needs needs;
	// Envies
	private Desires desires;
	// Décision
	private DecisionType decision = DecisionType.NEED_HUNGER;
	// // Graphismes
	// private MouglotteGraph graphics;

	/** Birthdate */
	private long birthDate;
	// // Age
	// private int age;

	// // Chemin
	// private Path path;

	// Action en cours
	private boolean actionInProgress;
	// Temps écoulé
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

	public Mouglotte(MouglotteEntity entity) {

		// // Jeu
		// this.game = game;
		// Entity
		this.entity = entity;
		// Caryotype
		this.karyotype = new Karyotype();
		// Mémoire
		this.memories = new Memories(getGeneValue("ZE"));
		// Traits de caractère
		this.traits = new Traits(this);
		// Besoins
		this.needs = new Needs(this);
		// Envies
		this.desires = new Desires(this);

		// Date de naissance
		this.birthDate = System.currentTimeMillis();

		// // Graphismes
		// this.graphics = new MouglotteGraph(this, this.game);

		// Initialisation des traits de caractères
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
	// public void setLocation(int x, int y) {
	// this.graphics.setLocation(x, y);
	// }

	/**
	 * Get age
	 * 
	 * @return Age
	 */
	public int getAge() {
		return this.entity.getAge();
	}

	/**
	 * Get x position
	 * 
	 * @return x position
	 */
	// public int getX() {
	// return this.graphics.getX();
	// }

	/**
	 * Get y position
	 * 
	 * @return y position
	 */
	// public int getY() {
	// return this.graphics.getY();
	// }

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
	// public boolean isSelected() {
	// return this.graphics.isSelected();
	// }

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

	/**
	 * Update mouglotte
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	// public void update(GameContainer container, int delta) {
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::Update");
	//
	// delta *= GameState.TIME_FACTOR;
	//
	// // Calcul du temps passé
	// this.pastTime += delta;
	//
	// // It's mandatory tu put continueAction or walk here to have a smooth
	// // movement
	// continueAction();
	//
	// // Toutes les secondes réelles
	// if (this.pastTime >= 1000) {
	//
	// // Une seconde réelle s'est écoulée
	// this.secondesR++;
	// this.pastTime -= 1000;
	// // On effectue une action
	// action();
	// }
	//
	// // On fait un truc toutes les 3 secondes réelles
	// // = minute mouglotte
	// if (this.secondesR >= 3) {
	//
	// // Une minute mouglotte s'est écoulée
	// this.minutes++;
	// this.secondesR = 0;
	// // Evénement lancé toutes les minutes
	// eventMinute();
	// }
	//
	// // Toutes les 3 minutes réelles
	// // = heure mouglotte
	// if (this.minutes >= 60) {
	//
	// // Une heure mouglotte s'est écoulée
	// this.hours++;
	// this.minutes = 0;
	// // Evénement lancé toutes les heures
	// eventHour();
	// }
	//
	// // Toutes les heures réelles
	// // = jour mouglotte
	// if (this.hours >= 20) {
	//
	// // Un jour mouglotte s'est écoulé
	// this.days++;
	// this.hours = 0;
	// // Evénement lancé tous les jours
	// eventDay();
	// }
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::Update::End");
	// }

	@Override
	public void update(GameContainer container, int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	/**
	 * Event called every mouglotte minute
	 */
	public void eventMinute() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventMinute");

		// Needs and desires change
		// If they are fulfilling, they decrease

		// Needs
		this.needs.eventMinute();
		// Desires
		this.desires.eventMinute();

		Debug.log("MOUGLOTTE", "Mouglotte::EventMinute::End");
	}

	/**
	 * Event called every mouglotte hour
	 */
	public void eventHour() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventHour");

		// Needs raise and the most urgent need is chosen
		// A new desire is chosen if the current desire is fulfilling or
		// couldn't be

		// Needs
		this.needs.eventHour();
		// Desires
		this.desires.eventHour();

		// Take a decision
		// The need or the desire is chosen
		decide();

		Debug.log("MOUGLOTTE", "Mouglotte::EventHour::End");
	}

	/**
	 * Event called every mouglotte day
	 */
	public void eventDay() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventDay");
		Debug.log("MOUGLOTTE", "Mouglotte::EventDay::End");
	}

	@Override
	public void eventMonth() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventSeason() {
		// TODO Auto-generated method stub

	}

	/**
	 * Event called every mouglotte year
	 */
	public void eventYear() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventYear");

		// Traits change
		this.traits.eventYear();

		Debug.log("MOUGLOTTE", "Mouglotte::EventYear::End");
	}

	/**
	 * Decide what to do
	 */
	private void decide() {

		Debug.log("MOUGLOTTE", "Mouglotte::Decide");

		// Ajouter la gestion des ordres et des solicitations extérieurs

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
		Debug.log("MOUGLOTTE", "Mouglotte::Decide::End");
	}

	/**
	 * Do an action
	 */
	public void action() {

		Debug.log("MOUGLOTTE", "Mouglotte::Action");

		// Pour les tests
		if (this.entity.isSelected()) {
			continueAction();
			return;
		}

		// Rechercher si l'action peut être conclue
		// (nourriture à proximité,...)
		actionFulfilled();

		// Une action va à son terme sauf si un événement arrive
		if (this.actionInProgress)
			continueAction();
		else
			newAction();

		Debug.log("MOUGLOTTE", "Mouglotte::Action::End");
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

		// Search near
		Tile tile = this.entity.getGame().getMap()
				.searchNear(memoryType, this.entity.getX(), this.entity.getY());

		// La mouglotte a trouvé ce qu'elle cherche
		if (tile != null) {

			Debug.log("MOUGLOTTE", "Mouglotte::ActionFulfilled:" + memoryType
					+ " found");

			// Destination is cancelled
			this.entity.clearPath();

			// La mouglotte est au bon endroit
			if (tile.contains(this.entity.getX(), this.entity.getY()))
				// Accomplissons
				fulfill();

			// La mouglotte est à côté de l'endroit
			else
				// Allons-y
				this.entity.goTo(tile.getX(), tile.getY());

			// La mouglotte n'a pas trouvé ce qu'elle cherche
		} else {

			Debug.log("MOUGLOTTE", "Mouglotte::ActionFulfilled:" + memoryType
					+ " not found");

			// If mouglotte is at destination
			if (this.entity.arrived()) {
				// L'action est terminée, on n'a rien trouvé
				// Une nouvelle action va être déclenchée
				this.actionInProgress = false;

				Debug.log("MOUGLOTTE", "Mouglotte::ActionFulfilled:No path");
			}
		}

		Debug.log("MOUGLOTTE", "Mouglotte::ActionFulfilled::End");
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

		// Recherche d'un souvenir concernant la décision en cours
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

		// Récupération du souvenir le plus proche
		if (memoryType != null)
			memory = this.memories.getCloser(memoryType, this.entity.getX(),
					this.entity.getY());

		// Aller à l'endroit du souvenir
		if (memory != null)
			this.entity.goTo(memory.getX(), memory.getY());
		// Rien à faire
		else
			nothingToDo();

		Debug.log("MOUGLOTTE", "Mouglotte::NewAction::End");
	}

	/**
	 * Continue the current action
	 */
	private void continueAction() {

		Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction");

		// Marcher
		walk();

		Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction::End");
	}

	/**
	 * Nothing to do, try to find what to do (except needs, desires or orders)
	 */

	private void nothingToDo() {

		Debug.log("MOUGLOTTE", "Mouglotte::NothingToDo");

		Random r = new Random();

		// Une chance sur 2 de ne rien faire
		if (r.nextBoolean() == true)
			idle();
		// Une chance sur 2 de se promener
		else
			walkAround();

		Debug.log("MOUGLOTTE", "Mouglotte::NothingToDo::End");
	}

	/**
	 * Walk around
	 */

	private void walkAround() {

		Debug.log("MOUGLOTTE", "Mouglotte::WalkAround");

		Random r = new Random();
		int destX = 0, destY = 0;

		// Une chance sur 2 de continuer dans la même direction
		if (r.nextBoolean() == true) {

			Debug.log("MOUGLOTTE", "Mouglotte::WalkAround:Same direction");

			// Current direction
			Vector2f dir = this.entity.getDirection();

			// La mouglotte marchait, on continue dans la même direction
			if (dir.getX() != 0)
				destX = (int) (this.entity.getX() + WALK_AROUND_DISTANCE
						* dir.getX() / Math.abs(dir.getX()));
			if (dir.getY() != 0)
				destY = (int) (this.entity.getY() + WALK_AROUND_DISTANCE
						* dir.getY() / Math.abs(dir.getY()));

			// On va dans une direction au hasard
			// (y compris la direction actuelle pour simplifier)
		} else {

			Debug.log("MOUGLOTTE", "Mouglotte::WalkAround:New direction");

			// Find a random destination on the map
			// Destination is on a circle of radius WALK_AROUND_DISTANCE
			while (!this.entity.getGame().getMap().contains(destX, destY)) {

				destX = r.nextInt(WALK_AROUND_DISTANCE);
				if (r.nextBoolean() == true)
					destY = -(destX - WALK_AROUND_DISTANCE);
				else
					destY = destX - WALK_AROUND_DISTANCE;
			}

			// switch (r.nextInt(8)) {
			// case 0:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
			// break;
			// case 1:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
			// break;
			// case 2:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
			// break;
			// case 3:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
			// break;
			// case 4:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
			// break;
			// case 5:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
			// break;
			// case 6:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
			// break;
			// case 7:
			// destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
			// destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
			// break;
			// }
		}

		// Aller à la destination trouvée
		this.entity.goTo(destX, destY);

		Debug.log("MOUGLOTTE", "Mouglotte::WalkAround::End");
	}

	/**
	 * Walk or continue walking
	 */
	private void walk() {

		Debug.log("MOUGLOTTE", "Mouglotte::Walk");

		this.entity.move();

		Debug.log("MOUGLOTTE", "Mouglotte::Walk::End");
	}

	/**
	 * Idle
	 */
	private void idle() {

		Debug.log("MOUGLOTTE", "Mouglotte::Idle");

		Debug.log("MOUGLOTTE", "Mouglotte::Idle::End");
	}

	/**
	 * Fulfill current need, desire or order
	 */
	private void fulfill() {

		MemoryType memoryType = null;

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill");

		// A décliner en véritable actions
		// eat(), talk(), fuck(),...

		if (this.decision != null)
			switch (this.decision) {
			case NEED_HUNGER:
				memoryType = MemoryType.FOOD;
			case NEED_SOCIAL:
				memoryType = MemoryType.FRIEND;
			case NEED_FUN:
				memoryType = MemoryType.FRIEND;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_HUNGER:
				memoryType = MemoryType.FOOD;
			case DESIRE_SOCIAL:
				memoryType = MemoryType.FRIEND;
			case DESIRE_FUN:
				memoryType = MemoryType.FRIEND;
			case DESIRE_LOVE:
				memoryType = MemoryType.LOVER;
			case DESIRE_FIGHT:
				memoryType = MemoryType.ENEMY;
			case DESIRE_WORK:
				memoryType = MemoryType.WORK;
				this.desires.setFulfilling(true);
				break;
			default:
				break;
			}

		// Save memory
		this.memories.put(new Memory(memoryType, this.entity.getX(),
				this.entity.getY()));

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill::End");
	}
}
