package com.mouglotte.specy;

import java.util.Random;

import org.newdawn.slick.GameContainer;

import com.mouglotte.entities.Entity;
import com.mouglotte.entities.EntityInterface;
import com.mouglotte.entities.FoodEntity;
import com.mouglotte.entities.MouglotteEntity;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.genetics.Karyotype;
import com.mouglotte.map.Tile;
import com.mouglotte.utilities.Debug;

public class Mouglotte {

	/** Walk around max distance */
	private final int WALK_AROUND_DISTANCE = 1000;

	/** Entity */
	private MouglotteEntity entity;

	/** Karyotype */
	private Karyotype karyotype;
	/** Memories */
	private Memories memories;
	/** Traits */
	private Traits traits;
	/** Needs */
	private Needs needs;
	/** Desires */
	private Desires desires;

	/** Birthdate */
	private long birthDate;

	/** Decision */
	private DecisionType decision = DecisionType.NEED_HUNGER;
	/** Action */
	private ActionType action;
	/** Act with */
	private Entity actWith;

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
	 * Begin or stop searching
	 * 
	 * @param searching
	 *            True to begin to search
	 */
	private void setSearching(boolean searching) {

		this.action = ActionType.SEARCHING;

		// Dans le cas d'un appel par decide() on peut éventuellement garder le
		// même besoin/envie et on le refait passer à searching alors qu'il
		// était en fulfilling

		switch (this.decision) {
		case NEED_HUNGER:
		case NEED_REST:
		case NEED_SOCIAL:
		case NEED_FUN:
			this.needs.setSearching(searching);
			if (searching == true) {
				this.desires.setSearching(false);
				this.desires.setFulfilling(false);
			}
			break;
		case DESIRE_HUNGER:
		case DESIRE_REST:
		case DESIRE_SOCIAL:
		case DESIRE_FUN:
		case DESIRE_LOVE:
		case DESIRE_FIGHT:
		case DESIRE_WORK:
			this.desires.setSearching(searching);
			if (searching == true) {
				this.needs.setSearching(false);
				this.needs.setFulfilling(false);
			}
			break;
		default:
		}
	}

	/**
	 * Begin or stop fulfilling
	 * 
	 * @param fulfilling
	 *            True to begin to fulfill
	 */
	private void setFulfilling(boolean fulfilling) {

		switch (this.decision) {
		case NEED_HUNGER:
		case NEED_REST:
		case NEED_SOCIAL:
		case NEED_FUN:
			this.needs.setFulfilling(fulfilling);
			if (fulfilling == true) {
				this.desires.setSearching(false);
				this.desires.setFulfilling(false);
			}
			break;
		case DESIRE_HUNGER:
		case DESIRE_REST:
		case DESIRE_SOCIAL:
		case DESIRE_FUN:
		case DESIRE_LOVE:
		case DESIRE_FIGHT:
		case DESIRE_WORK:
			this.desires.setFulfilling(fulfilling);
			if (fulfilling == true) {
				this.needs.setSearching(false);
				this.needs.setFulfilling(false);
			}
			break;
		default:
		}
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
		// this.actionInProgress = false;

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
	 * Do an action
	 */
	public void action() {

		Debug.log("MOUGLOTTE", "Mouglotte::Action");

		// // TESTS
		// if (this.entity.isSelected()) {
		// continueAction();
		// return;
		// }

		// Idle
		if (this.action == ActionType.IDLE)
			idle();
		// Fulfill
		else if (this.action.isFulfilling())
			fulfill();
		// Search
		else if (this.action == ActionType.SEARCHING)
			search();
		// Nothing to do
		// else
		// nothingToDo();

		// // Try to fullfill the action
		// tryToFulfill();
		//
		// // If an action is in progress, continue until it's done
		// if (this.actionInProgress)
		// continueAction();
		// else
		// newAction();

		Debug.log("MOUGLOTTE", "Mouglotte::Action::End");
	}

	/**
	 * Decide what to do
	 */
	private void decide() {

		Debug.log("MOUGLOTTE", "Mouglotte::Decide");

		// Ajouter la gestion des ordres et des solicitations extérieurs

		// Follow a need
		if (this.needs.getCurrent().getValue() > this.desires.getCurrent()
				.getValue())
			this.decision = DecisionType.getDecisionType(this.needs
					.getCurrent().getType());

		// Follow a desire
		else
			this.decision = DecisionType.getDecisionType(this.desires
					.getCurrent().getType());

		Debug.log("MOUGLOTTE", "Mouglotte::Decide:Decision=" + this.decision);

		// If the current action can be interrupted
		if (!this.action.hasToFinish()) {

			// Starting to search
			setSearching(true);

			// A TESTER, on va passer dans search() qui va récupérer le souvenir
			// // Get closest memory if exist
			// Memory memory = this.memories.getClosest(this.decision,
			// this.entity.getTile(), false);
			//
			// // Memory found
			// if (memory != null) {
			//
			// Debug.log("MOUGLOTTE", "Mouglotte::Decide:Memory found");
			//
			// // Set current memory
			// this.memories.setCurrent(memory);
			//
			// // Go to memory place
			// this.entity.goTo(memory.getTile());
			//
			// // Nothing to do
			// } else {
			// nothingToDo();
			// }
		} else
			Debug.log("MOUGLOTTE", "Mouglotte::Decide:Finishing current action");

		Debug.log("MOUGLOTTE", "Mouglotte::Decide::End");
	}

	/**
	 * Begin a new action
	 */
	// private void newAction() {
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::NewAction");
	//
	// // // Action in progress
	// // this.actionInProgress = true;
	//
	// // Get closest memory if exist
	// Memory memory = this.memories.getCloser(this.decision,
	// this.entity.getX(), this.entity.getY());
	//
	// // Memory found
	// if (memory != null) {
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::NewAction:Memory found");
	//
	// // Go to memory place
	// this.entity.goTo(memory.getTile());
	//
	// // Nothing to do
	// } else {
	// nothingToDo();
	// }
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::NewAction::End");
	// }

	/**
	 * Continue the current action
	 */
	// private void continueAction() {
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction");
	//
	// // Marcher
	// // walk();
	//
	// Debug.log("MOUGLOTTE", "Mouglotte::ContinueAction::End");
	// }

	/**
	 * Idle
	 */
	private void idle() {

		Debug.log("MOUGLOTTE", "Mouglotte::Idle");

		// this.actionInProgress = false;
		// Il faut gérer un idle time

		Debug.log("MOUGLOTTE", "Mouglotte::Idle::End");
	}

	/**
	 * Try to fulfill the current action. If the action is fulfillable then
	 * fulfill. If the action is fulfillable on a close tile then go to this
	 * tile.
	 */
	private void search() {

		Debug.log("MOUGLOTTE", "Mouglotte::Search");

		// ATTENTION, si on vient d'un decide() on est resté sur la même tile
		// En plus search() est lancé toutes les secondes, donc on a sûrement
		// changé de tile
		// si on marchait
		// If we're on the same tile don't try again
		if (!this.entity.isOnANewTile())
			return;

		// Search near
		Tile tile = this.entity.searchNear(MemoryType
				.getMemoryType(this.decision));

		// We've found what we're looking for
		if (tile != null) {

			Debug.log(
					"MOUGLOTTE",
					"Mouglotte::Search:"
							+ MemoryType.getMemoryType(this.decision)
							+ " found");

			// Destination is cancelled
			this.entity.stop();

			// If we're on the right tile
			if (tile.contains(this.entity.getX(), this.entity.getY())) {

				// Fulfill
				fulfill();

				// If we're close to the tile
			} else
				// Let's go
				this.entity.goTo(tile);

			// We've not found what we're looking for
		} else {

			Debug.log(
					"MOUGLOTTE",
					"Mouglotte::Search:"
							+ MemoryType.getMemoryType(this.decision)
							+ " not found");

			// If we're is at destination
			if (this.entity.hasReachedTarget()) {

				Debug.log("MOUGLOTTE", "Mouglotte::Search:Destination reached");

				// Penalize current memory
				this.memories.penalizeCurrent(Memory.PENALIZE_POINTS);

				// Get another closest memory if exist
				Memory memory = this.memories.getClosest(this.decision,
						this.entity.getTile(), true);
				// Refresh memory
				this.memories.refresh();

				// Memory found
				if (memory != null) {

					Debug.log("MOUGLOTTE",
							"Mouglotte::Search:Another memory found");

					// Set current memory
					this.memories.setCurrent(memory);

					// Go to memory place
					this.entity.goTo(memory.getTile());

					// Nothing to do
				} else {
					nothingToDo();
				}
			}
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Search::End");
	}

	/**
	 * Fulfill current need, desire or order
	 */
	private void fulfill() {

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill");

		// Starting to fulfill (or going on)
		setFulfilling(true);

		// Fulfill with the right action
		switch (this.decision) {
		case NEED_HUNGER:
		case DESIRE_HUNGER:
			eat();
			break;
		case NEED_REST:
		case DESIRE_REST:
			rest();
			break;
		case NEED_SOCIAL:
		case DESIRE_SOCIAL:
			talk();
			break;
		case NEED_FUN:
		case DESIRE_FUN:
			play();
			break;
		case DESIRE_LOVE:
			fuck();
		case DESIRE_FIGHT:
			fight();
			break;
		case DESIRE_WORK:
			work();
			break;
		default:
			break;
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill::End");
	}

	private void eat() {

		// Beginning to eat
		if (this.action != ActionType.EATING) {

			if (this.actWith != null) {

				// If the food entity has food
				if (((FoodEntity) this.actWith).hasFood()) {

					// Reward current memory
					this.memories.rewardCurrent(Memory.REWARD_POINTS);

					// If the food entity hasn't more food
				} else {

					// Low reward current memory
					this.memories.rewardCurrent(Memory.REWARD_LOW_POINTS);
				}

				// Save memory
				// If there's a current memory then it's no use to save it
				if (!this.memories.hasCurrent())
					this.memories.put(new Memory(MemoryType
							.getMemoryType(this.decision), this.entity));

				// Pour le moment on ne gère pas quand il y a pas assez de food
				// Cela obligerait à corréler la quantité mangée et le fulfill
				// Quelque soit le temps qu'on y passe, on mange la même
				// quantité
				// Sinon il faurait gérer le temps passé (a la fin de l'action)
				// Consume food
				((FoodEntity) this.actWith).consume(FoodEntity.CONSUME_AMOUNT);
			}
		}

		// Change action (or go on)
		this.action = ActionType.EATING;

		// Gestion de l'animation (dans MouglotteEntity)

		// If the decision has changed
		if (!this.decision.isEating()) {

			// The action has to end
			this.action = null;
		}
	}

	private void rest() {

		// Beginning to rest
		if (this.action != ActionType.RESTING) {
		}

		// Change action (or go on)
		this.action = ActionType.RESTING;

		// Gestion de l'animation (dans MouglotteEntity)

		// If the decision has changed
		if (!this.decision.isResting()) {

			// The action has to end
			this.action = null;
		}
	}

	private void talk() {

		// Beginning to talk
		if (this.action != ActionType.TALKING) {

			if (this.actWith != null) {

				// Check if the entity wants to talk with me
				boolean wantsToTalk = ((MouglotteEntity) this.actWith)
						.getMouglotte().wantsToTalk(this);

				// If a memory was followed
				if (this.memories.getCurrent() != null) {

					// If the entity we're talking with was the one in the
					// memory
					if (this.memories.getCurrent().getEntity() == this.actWith) {

						// Reward current memory
						if (wantsToTalk)
							this.memories.rewardCurrent(Memory.REWARD_POINTS);
						else
							this.memories
									.penalizeCurrent(Memory.PENALIZE_LOW_POINTS);

						// Update location of the memory
						this.memories.getCurrent().setTile(
								this.actWith.getTile());

						// If the entity we're talking with was not the one in
						// the memory
					} else {

						// Try to get the same memory type with this entity
						Memory memory = this.memories.get(this.memories
								.getCurrent().getType(), this.actWith);

						// A memory exists with the same type and this entity
						if (memory != null) {

							// Reward the memory
							if (wantsToTalk)
								memory.reward(Memory.REWARD_POINTS);
							else
								memory.penalize(Memory.PENALIZE_LOW_POINTS);

							// Update location of the memory
							memory.setTile(this.actWith.getTile());

							// No memory exists with the same type and this
							// entity
						} else {

							// Save memory
							if (wantsToTalk) {
								memory = new Memory(
										MemoryType.getMemoryType(this.decision),
										this.entity);
								memory.setPoints(Memory.REWARD_POINTS);
								this.memories.put(memory);
							}
						}

					}
					// If no memory was followed
				} else {

					// Try to get the same memory type with this entity
					Memory memory = this.memories.get(
							MemoryType.getMemoryType(this.decision),
							this.actWith);

					// A memory exists with the same type and this entity
					if (memory != null) {

						// Reward the memory
						if (wantsToTalk)
							memory.reward(Memory.REWARD_POINTS);
						else
							memory.penalize(Memory.PENALIZE_LOW_POINTS);

						// Update location of the memory
						memory.setTile(this.actWith.getTile());

						// No memory exists with the same type and this entity
					} else {

						// Save memory
						if (wantsToTalk) {
							memory = new Memory(
									MemoryType.getMemoryType(this.decision),
									this.entity);
							memory.setPoints(Memory.REWARD_POINTS);
							this.memories.put(memory);
						}
					}
				}
			}
		}

		// Change action (or go on)
		this.action = ActionType.TALKING;

		// Gestion de l'animation (dans MouglotteEntity)
		//
		if (this.actWith != null) {
			if (((MouglotteEntity) this.actWith).getMouglotte().wantsToTalk(
					this)) {

			}
		}

		// If the other entity doesn't want to talk anymore
		if (this.actWith != null) {
			
			if (!((MouglotteEntity) this.actWith).getMouglotte().wantsToTalk(
					this)) {
				// The action has to end (nobody to talk with)
			}
			
			if (!((MouglotteEntity) this.actWith).getMouglotte().getDecision()
					.isSocial()) {

				// The action has to end (nobody to talk with)
				this.action = null;
			}
		}

		// If the decision has changed
		if (!this.decision.isSocial()) {

			// The action has to end
			this.action = null;
		}
	}

	private void play() {

		this.action = ActionType.PLAYING;

	}

	private void fuck() {

		this.action = ActionType.FUCKING;

	}

	private void fight() {

		this.action = ActionType.FIGHTING;

	}

	private void work() {

		this.action = ActionType.WORKING;

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
	 * Act with what ?
	 * 
	 * @param entity
	 *            Entity to act with
	 */
	public void actWith(Entity entity) {
		this.actWith = entity;
	}

	/**
	 * Check if the mouglotte wants to talk with another one
	 * 
	 * @param with
	 *            Mouglotte to talk with
	 * @return True if the mouglotte wants to talk
	 */
	public boolean wantsToTalk(Mouglotte with) {

		return false;
	}
}
