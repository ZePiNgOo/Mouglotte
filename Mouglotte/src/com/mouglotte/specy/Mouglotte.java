package com.mouglotte.specy;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import com.mouglotte.entities.EntityInterface;
import com.mouglotte.entities.MouglotteEntity;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.genetics.Karyotype;
import com.mouglotte.map.Tile;
import com.mouglotte.utilities.Debug;

public class Mouglotte {

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

	// // Temps écoulé
	// private int pastTime = 0;
	// private int secondesR = 0;
	// private int minutes = 0;
	// private int hours = 0;
	// private int days = 0;

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

		// Initialisation des traits de caractères
		initTraits();
		// Initialisation des besoins
		initNeeds();
		// Initialisation des envies
		initDesires();
	}

	/**
	 * Get age
	 * 
	 * @return Age
	 */
	public int getAge() {
		return this.entity.getAge();
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

	public void eventRealSecond() {

		// Do or continue an action
		action();
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

		// A new action begins
		this.actionInProgress = false;

		Debug.log("MOUGLOTTE", "Mouglotte::EventHour::End");
	}

	/**
	 * Event called every mouglotte day
	 */
	public void eventDay() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventDay");
		Debug.log("MOUGLOTTE", "Mouglotte::EventDay::End");
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

		// Follow a need
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

			// Follow a desire
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

		// TESTS
		if (this.entity.isSelected()) {
			continueAction();
			return;
		}

		// Try to fullfill the action
		tryToFulfill();

		// If an action is in progress, continue until it's done
		if (this.actionInProgress)
			continueAction();
		else
			newAction();

		Debug.log("MOUGLOTTE", "Mouglotte::Action::End");
	}

	/**
	 * Try to fulfill the current action. If the action is fulfillable then
	 * fulfill. If the action is fulfillable on a close tile then go to this
	 * tile.
	 */
	private void tryToFulfill() {

		Debug.log("MOUGLOTTE", "Mouglotte::tryToFulfill");

		// If we're on the same tile don't try again
		if (!this.entity.isOnANewTile())
			return;

		// Search near
		Tile tile = this.entity.searchNear(Memories
				.getMemoryType(this.decision));

		// We've found what we're looking for
		if (tile != null) {

			Debug.log(
					"MOUGLOTTE",
					"Mouglotte::tryToFulfill:"
							+ Memories.getMemoryType(this.decision) + " found");

			// Destination is cancelled
			this.entity.stop();

			// If we're on the right tile
			if (tile.contains(this.entity.getX(), this.entity.getY()))
				// Fulfill
				fulfill();

			// If we're close to the tile
			else
				// Let's go
				this.entity.goTo(tile);

			// We've not found what we're looking for
		} else {

			Debug.log(
					"MOUGLOTTE",
					"Mouglotte::tryToFulfill:"
							+ Memories.getMemoryType(this.decision)
							+ " not found");

			// If we're is at destination
			if (this.entity.hasReachedTarget()) {
				// Action is done, we've found nothing
				// A new action will start
				this.actionInProgress = false;

				Debug.log("MOUGLOTTE",
						"Mouglotte::tryToFulfill:Destination reached");
			}
		}

		Debug.log("MOUGLOTTE", "Mouglotte::tryToFulfill::End");
	}

	/**
	 * Begin a new action
	 */
	private void newAction() {

		Debug.log("MOUGLOTTE", "Mouglotte::NewAction");

		// Action in progress
		this.actionInProgress = true;

		// Get closest memory if exist
		Memory memory = this.memories.getCloser(this.decision,
				this.entity.getX(), this.entity.getY());

		// Memory found
		if (memory != null) {

			Debug.log("MOUGLOTTE", "Mouglotte::NewAction:Memory found");

			// Go to memory place
			this.entity.goTo(memory.getTile());

			// Nothing to do
		} else {
			nothingToDo();
		}

		Debug.log("MOUGLOTTE", "Mouglotte::NewAction::End");
	}

	/**
	 * Continue the current action
	 */
	private void continueAction() {

		Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction");

		// Marcher
		// walk();

		Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction::End");
	}

	/**
	 * Nothing to do, try to find what to do (except needs, desires or orders)
	 */

	private void nothingToDo() {

		Debug.log("MOUGLOTTE", "Mouglotte::NothingToDo");

		Random r = new Random();

		// 50% chance to do nothing
		if (r.nextBoolean() == true)
			idle();
		// 50% chance to walk around
		else
			walkAround();

		Debug.log("MOUGLOTTE", "Mouglotte::NothingToDo::End");
	}

	/**
	 * Walk around
	 */

	private void walkAround() {

		Debug.log("MOUGLOTTE", "Mouglotte::WalkAround");

		this.entity.walkaround();

		Debug.log("MOUGLOTTE", "Mouglotte::WalkAround::End");
	}

	/**
	 * Idle
	 */
	private void idle() {

		Debug.log("MOUGLOTTE", "Mouglotte::Idle");

		this.actionInProgress = false;

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
				this.needs.setFulfilling(true);
				break;
			case NEED_SOCIAL:
				memoryType = MemoryType.FRIEND;
				this.needs.setFulfilling(true);
				break;
			case NEED_FUN:
				memoryType = MemoryType.FRIEND;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_HUNGER:
				memoryType = MemoryType.FOOD;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_SOCIAL:
				memoryType = MemoryType.FRIEND;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_FUN:
				memoryType = MemoryType.FRIEND;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_LOVE:
				memoryType = MemoryType.LOVER;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_FIGHT:
				memoryType = MemoryType.ENEMY;
				this.needs.setFulfilling(true);
				break;
			case DESIRE_WORK:
				memoryType = MemoryType.WORK;
				this.desires.setFulfilling(true);
				break;
			default:
				break;
			}

		// Save memory
		this.memories.put(new Memory(memoryType, this.entity.getTile()));

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill::End");
	}
}
