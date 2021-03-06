package com.mouglotte.specy;

import java.util.Random;

import com.mouglotte.entities.Entity;
import com.mouglotte.entities.FoodEntity;
import com.mouglotte.entities.MouglotteEntity;
import com.mouglotte.entities.Relation;
import com.mouglotte.entities.Relations;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.genetics.Karyotype;
import com.mouglotte.map.Tile;
import com.mouglotte.utilities.Debug;

public class Mouglotte {

	// /** Walk around max distance */
	// private final static int WALK_AROUND_DISTANCE = 1000;
	/** Max idle time (in minutes) */
	private final static int MAX_IDLE_TIME = 5;

	/** Entity */
	private MouglotteEntity entity;

	/** Karyotype */
	private Karyotype karyotype;
	/** Memories */
	private Memories memories;
	/** Traits */
	private Traits traits;
	/** Skills */
	private Skills skills;
	/** Needs */
	private Needs needs;
	/** Desires */
	private Desires desires;

	/** Birthdate */
	private long birthDate;

	/** Decision */
	private DecisionType decision;
	/** Action */
	private ActionType action;
	/** Act with */
	private Entity actWith;
	/** Action time (in minutes) */
	private double actionTime;

	/**
	 * Constructor
	 * 
	 * @param entity
	 *            Entity
	 */
	public Mouglotte(MouglotteEntity entity) {

		// Entity
		this.entity = entity;
		// Karyotype
		this.karyotype = new Karyotype();
		// Memory
		this.memories = new Memories(this);
		// Traits
		this.traits = new Traits(this);
		// Skills
		this.skills = new Skills(this);
		// Needs
		this.needs = new Needs(this);
		// Desires
		this.desires = new Desires(this);

		// Decision
		this.decision = DecisionType.NEED_HUNGER;

		// Birth date
		this.birthDate = System.currentTimeMillis();

		// Initialize traits
		initTraits();
		// Initialize skills
		initSkills();
		// Initialize needs
		initNeeds();
		// Initialize desires
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

		// Dans le cas d'un appel par decide() on peut �ventuellement garder le
		// m�me besoin/envie et on le refait passer � searching alors qu'il
		// �tait en fulfilling

		// If the decision is a need
		if (this.decision.isNeed()) {
			this.needs.setSearching(searching);
			if (searching == true) {
				this.desires.setSearching(false);
				this.desires.setFulfilling(false);
			}
			// If the decision is a desire
		} else if (this.decision.isDesire()) {
			this.desires.setSearching(searching);
			if (searching == true) {
				this.needs.setSearching(false);
				this.needs.setFulfilling(false);
			}
		}
	}

	/**
	 * Begin or stop fulfilling
	 * 
	 * @param fulfilling
	 *            True to begin to fulfill
	 */
	private void setFulfilling(boolean fulfilling) {

		// Start fulfilling
		// Real action will be determined in fulfill()
		if (fulfilling == true) {
			this.action = ActionType.FULFILLING;
		}

		// If the decision is a need
		if (this.decision.isNeed()) {
			this.needs.setFulfilling(fulfilling);
			if (fulfilling == true) {
				this.desires.setSearching(false);
				this.desires.setFulfilling(false);
			}
			// If the decision is a desire
		} else if (this.decision.isDesire()) {
			this.desires.setFulfilling(fulfilling);
			if (fulfilling == true) {
				this.needs.setSearching(false);
				this.needs.setFulfilling(false);
			}
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
	 * Get entity
	 * 
	 * @return Entity
	 */
	public MouglotteEntity getEntity() {
		return this.entity;
	}

	/**
	 * Get need value
	 * 
	 * @param type
	 *            Need type
	 * @return Need value
	 */
	public int getNeedValue(NeedType type) {
		return this.needs.get(type).getValue();
	}

	/**
	 * Get desire value
	 * 
	 * @param type
	 *            Desire type
	 * @return Desire value
	 */
	public int getDesireValue(DesireType type) {
		return this.desires.get(type).getValue();
	}

	/**
	 * Get trait value
	 * 
	 * @param type
	 *            Trait type
	 * @return Trait value
	 */
	public int getTraitValue(TraitType type) {
		return this.traits.get(type).getValue();
	}

	/**
	 * Get skill value
	 * 
	 * @param type
	 *            Skill type
	 * @return Skill value
	 */
	public int getSkillValue(SkillType type) {
		return this.skills.get(type).getValue();
	}

	// Naissance
	// public static Mouglotte birth() {
	//
	// return new Mouglotte();
	// }

	/**
	 * Is the other mouglotte a friend ?
	 * 
	 * @param other
	 *            Other mouglotte
	 * @return True if the other mouglotte is a friend
	 */
	public boolean isFriend(Mouglotte other) {
		return false;
	}

	/**
	 * Is the other mouglotte a lover ?
	 * 
	 * @param other
	 *            Other mouglotte
	 * @return True if the other mouglotte is a lover
	 */
	public boolean isLover(Mouglotte other) {
		return false;
	}

	/**
	 * Is the other mouglotte an enemy ?
	 * 
	 * @param other
	 *            Other mouglotte
	 * @return True if the other mouglotte is an enemy
	 */
	public boolean isEnemy(Mouglotte other) {
		return false;
	}

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
	 * Initialization of the skills
	 */
	private void initSkills() {

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
	public int getGeneValue(String name) {
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

		// Fulfill
		if (this.action != null && this.action.isFulfilling())
			fulfill();

		Debug.log("MOUGLOTTE", "Mouglotte::EventMinute::End");
	}

	/**
	 * Event called every mouglotte hour
	 */
	public void eventHour() {

		Debug.log("MOUGLOTTE", "Mouglotte::EventHour");

		// Needs increase
		this.needs.increase();

		// Needs raise and the most urgent need is chosen
		// A new desire is chosen if the current desire is fulfilling or
		// couldn't be

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

		// Debug.log("MOUGLOTTE", "Mouglotte::Action");

		// // TESTS
		// if (this.entity.isSelected()) {
		// continueAction();
		// return;
		// }

		// No action
		if (this.action == null)
			decide();
		// Idle
		if (this.action.isIdle())
			idle();
		// Le fulfill se fait toutes les minutes
		// // Fulfill
		// else if (this.action.isFulfilling())
		// fulfill();
		// Search
		else if (this.action.isSearching())
			search();

		// Debug.log("MOUGLOTTE", "Mouglotte::Action::End");
	}

	/**
	 * Decide what to do
	 */
	private void decide() {

		Debug.log("MOUGLOTTE", "Mouglotte::Decide");

		// Ajouter la gestion des ordres et des solicitations ext�rieurs

		// If no current action or current one can be interrupted
		if (this.action == null || !this.action.hasToFinish()) {

			// Decide current need
			this.needs.decide();
			// Decide current desire
			this.desires.decide();

			// Follow a need
			if (this.needs.getCurrent().getValue() > this.desires.getCurrent()
					.getValue())
				this.decision = DecisionType.getDecisionType(this.needs
						.getCurrent().getType());

			// Follow a desire
			else
				this.decision = DecisionType.getDecisionType(this.desires
						.getCurrent().getType());

			Debug.log("MOUGLOTTE", "Mouglotte::Decide:Decision="
					+ this.decision);

			// Starting to search
			setSearching(true);

			// Dans le futur REST n�cessitera un search �galement
			// Search memory
			if (this.decision.isResting()) {

				// Starting to fulfill (or going on)
				setFulfilling(true);
			} else {

				// Search for a memory
				searchMemory(false);

				// Follow a memory
				followMemory();
			}

			// If there's already an action that has to be finished
		} else
			Debug.log("MOUGLOTTE", "Mouglotte::Decide:Finishing current action");

		Debug.log("MOUGLOTTE", "Mouglotte::Decide::End");
	}

	/**
	 * Idle
	 */
	private void idle() {

		Debug.log("MOUGLOTTE", "Mouglotte::Idle");

		// Beginning to idle
		if (this.action != ActionType.IDLE) {

			// Set idle time (in minutes)
			this.actionTime = Math.random() * MAX_IDLE_TIME;
		}

		// Change action (or go on)
		this.action = ActionType.IDLE;

		// Consume idle time
		// (supposed idle is called every real second (1 minute = 3 real
		// seconds))
		this.actionTime -= 1 / 3;

		// Gestion de l'animation (dans MouglotteEntity)

		// If idle time is finished
		if (this.actionTime <= 0) {

			// The action has to end
			this.action = null;
			this.actionTime = 0;
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Idle::End");
	}

	/**
	 * Try to fulfill the current action. If the action is fulfillable then
	 * fulfill. If the action is fulfillable on a close tile then go to this
	 * tile.
	 */
	private void search() {

		Debug.log("MOUGLOTTE", "Mouglotte::Search");

		// ATTENTION, si on vient d'un decide() on est rest� sur la m�me tile
		// En plus search() est lanc� toutes les secondes, donc on a s�rement
		// chang� de tile si on marchait
		// If we're on the same tile don't try again
		// if (!this.entity.isOnANewTile())
		// return;

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
			if (tile.contains(this.entity)) {

				// Start fulfilling
				setFulfilling(true);

				// If we're close to the tile
			} else
				// Let's go
				this.entity.headToward(tile);

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

				// Penalize current memory (if exists)
				this.memories.penalizeCurrent(Memory.PENALIZE_POINTS);

				// Search memory (excluding current)
				searchMemory(true);

				// Follow a memory
				followMemory();

				// If we're not at destination
			} else {

				// If we're not on a path
				if (!this.entity.isOnAPath()) {

					// Search memory (excluding current)
					searchMemory(true);

					// Follow a memory
					followMemory();
				}
			}
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Search::End");
	}

	/**
	 * Search for a memory
	 * 
	 * @param excludeCurrent
	 *            True to exclude the current memory
	 */
	private void searchMemory(boolean excludeCurrent) {

		Debug.log("MOUGLOTTE", "Mouglotte::SearchMemory");

		// Pour les tests : searchMemory uniquement pour FOOD
		switch (MemoryType.getMemoryType(this.decision)) {
		case ENEMY:
		case FRIEND:
		case LOVER:
		case WORK:
			setFulfilling(true);
			return;
		default:
			break;
		}

		// Get the closest memory if exist
		// (eventually exclude current memory)
		Memory memory = this.memories.getClosest(this.decision,
				this.entity.getTile(), excludeCurrent);
		// Refresh memory
		this.memories.refresh();

		// Memory found
		if (memory != null) {

			Debug.log("MOUGLOTTE", "Mouglotte::SearchMemory:memory found");

			// Set current memory
			this.memories.setCurrent(memory);
		}

		Debug.log("MOUGLOTTE", "Mouglotte::SearchMemory::End");
	}

	/**
	 * Follow a memory
	 */
	private void followMemory() {

		// If a memory has been found
		if (this.memories.hasCurrent()) {

			// Go to memory place
			this.entity.goTo(this.memories.getCurrent().getTile());

			// No memory found
		} else {
			// Nothing to do
			nothingToDo();
		}
	}

	/**
	 * Fulfill current need, desire or order
	 */
	private void fulfill() {

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill");

		// Starting to fulfill (or going on)
		setFulfilling(true);

		// Needs and desires change
		// If they are fulfilling, they decrease
		this.needs.fulfill();
		this.desires.fulfill();

		// If the need or the desire is not fulfilled
		if ((this.decision.isNeed() && this.needs.isFulfilling())
				|| (this.decision.isDesire() && this.desires.isFulfilling())) {

			// Fulfill with the right action
			if (this.decision.isEating())
				eat();
			else if (this.decision.isResting())
				rest();
			else if (this.decision.isSocial())
				talk();
			else if (this.decision.isFun())
				play();
			else if (this.decision.isLoving())
				fuck();
			else if (this.decision.isFighting())
				fight();
			else if (this.decision.isWorking())
				work();
		}
		// If the need or the desire is fulfilled
		else {
			Debug.log("MOUGLOTTE", "Mouglotte::Fulfill:Fulfilled is done");
			decide();
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Fulfill::End");
	}

	/**
	 * Eat
	 */
	private void eat() {

		Debug.log("MOUGLOTTE", "Mouglotte::Eat");

		FoodEntity food = (FoodEntity) this.actWith;

		// Beginning to eat
		if (this.action != ActionType.EATING) {

			// Reward for food memory
			this.memories.rewardFood(food);
		}

		// Change action (or go on)
		this.action = ActionType.EATING;

		// Pour le moment on ne g�re pas quand il y a pas assez de food
		// Cela obligerait � corr�ler la quantit� mang�e et le fulfill
		// Quelque soit le temps qu'on y passe, on mange la m�me
		// quantit�
		// Sinon il faurait g�rer le temps pass� (a la fin de l'action)
		// Consume food
		food.removeFood(FoodEntity.CONSUME_AMOUNT);

		// If all the food has been consumed
		if (!food.hasFood()) {

			// Penalize current memory
			this.memories.penalizeCurrent(Memory.PENALIZE_LOW_POINTS);
		}

		// Gestion de l'animation (dans MouglotteEntity)

		// If the decision has changed
		// If everything has been consumed
		if (!this.decision.isEating() || !food.hasFood()) {

			// The action has to end
			this.action = null;
			this.actWith = null;
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Eat::End");
	}

	/**
	 * Rest
	 */
	private void rest() {

		Debug.log("MOUGLOTTE", "Mouglotte::Rest");

		// Beginning to rest
		if (this.action != ActionType.RESTING) {
		}

		// Change action (or go on)
		this.action = ActionType.RESTING;

		// G�rer les r�veils intempestifs, si quelqu'un approche
		// On peut imaginer un random ou un gene "sommeil l�ger"

		// Gestion de l'animation (dans MouglotteEntity)

		// If the decision has changed
		if (!this.decision.isResting()) {

			// The action has to end
			this.action = null;
			this.actWith = null;
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Rest::End");
	}

	/**
	 * Talk
	 */
	private void talk() {

		Debug.log("MOUGLOTTE", "Mouglotte::Talk");

		MouglotteEntity other = (MouglotteEntity) this.actWith;

		// Beginning to talk
		if (this.action != ActionType.TALKING) {

			// Reward for social memory
			this.memories.rewardSocial(other);
		}

		// Change action (or go on)
		this.action = ActionType.TALKING;

		// If the other mouglotte wants to talk
		if (other != null && other.getMouglotte().wantsToTalk(this)) {

			// Talk with the other one
			talkWith(other.getMouglotte());

			// Gestion de l'animation (dans MouglotteEntity)

		}
		// If the other entity doesn't want to talk anymore
		else {

			// The action has to end (nobody to talk with)
			this.action = null;
		}

		// If the decision has changed
		if (!this.decision.isSocial()) {

			// The action has to end
			this.action = null;
			this.actWith = null;
		}

		Debug.log("MOUGLOTTE", "Mouglotte::Talk::End");
	}

	/**
	 * Play
	 */
	private void play() {

		Debug.log("MOUGLOTTE", "Mouglotte::Play");

		this.action = ActionType.PLAYING;

		Debug.log("MOUGLOTTE", "Mouglotte::Play::End");

	}

	private void fuck() {

		Debug.log("MOUGLOTTE", "Mouglotte::Fuck");

		this.action = ActionType.FUCKING;

		Debug.log("MOUGLOTTE", "Mouglotte::Fuck::End");

	}

	private void fight() {

		Debug.log("MOUGLOTTE", "Mouglotte::Fight");

		this.action = ActionType.FIGHTING;

		Debug.log("MOUGLOTTE", "Mouglotte::Fight::End");

	}

	private void work() {

		Debug.log("MOUGLOTTE", "Mouglotte::Work");

		this.action = ActionType.WORKING;

		Debug.log("MOUGLOTTE", "Mouglotte::Work::End");

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
	 * @param other
	 *            Mouglotte to talk with
	 * @return True if the mouglotte wants to talk
	 */
	public boolean wantsToTalk(Mouglotte other) {

		// Get relation between me and other mouglotte
		Relation relation = Relations.get(this.entity, other.getEntity());

		// If the relation is bad
		if (relation != null && relation.isBad()) {
			return false;
		}

		// V�rifier si la mouglotte n'est pas en train de faire quelque chose de
		// plus important
		// Le motif de refus n'influence pas le penalize du souvenir, par contre
		// il influencera le degr� d'amiti�

		return true;
	}

	/**
	 * Talk to another mouglotte
	 * 
	 * @param other
	 *            Mouglotte to talk with
	 */
	public void talkWith(Mouglotte other) {

		if (other == null)
			return;

		// Get relation between me and other mouglotte
		Relation relation = Relations.get(this.entity, other.getEntity());

		// If the relation doesn't exist, create it
		if (relation == null) {
			Relations.create(this.entity, other.getEntity());
			relation = Relations.get(this.entity, other.getEntity());
		}

		// L� les deux mouglottes parlent en m�me temps de choses diff�rents
		// Soit on g�re une conversation chacun son tour avec random ou gene de
		// bavard
		// Soit la conversation devient un objet plus complexe

		// Find a conversation and talk about it
		// Get the value of this conversation for friendship reward
		int value = other.talkAbout(findConversation());

		// If the conversation was appreciated
		if (value > 0) {
			// Reward relation
			relation.reward(value);
		}
		// If the conversation was not appreciated
		else if (value < 0) {
			// Penalize relation
			relation.penalize(Math.abs(value));
		}
	}

	/**
	 * Fin a conversation
	 * 
	 * @return Conversation
	 */
	private Conversation findConversation() {

		Conversation ret = Conversation.FOOD;
		double max = Math.random()
				* this.desires.get(DesireType.HUNGER).getValue();
		double value = Math.random()
				* this.desires.get(DesireType.REST).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.desires.get(DesireType.SOCIAL).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.desires.get(DesireType.FUN).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.desires.get(DesireType.LOVE).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.desires.get(DesireType.FIGHT).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.desires.get(DesireType.WORK).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.traits.get(TraitType.STRENGTH).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.skills.get(SkillType.FISHING).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random() * this.skills.get(SkillType.FIGHTING).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}
		value = Math.random()
				* this.skills.get(SkillType.WOODCUTING).getValue();
		if (value > max) {
			max = value;
			ret = Conversation.REST;
		}

		return ret;
	}

	/**
	 * Talk about a conversation
	 * 
	 * @param conversation
	 *            Conversation
	 * @return Value of the conversation
	 */
	public int talkAbout(Conversation conversation) {

		if (conversation == null)
			return 0;

		// Gain apport� par la conversation

		// Calcul de la valeur de la conversation
		// Plut�t al�atoire, il serait bon de pr�voir des go�ts pour chaque type
		// de conversation
		double value = 0;

		if (conversation.isDesire()) {
			value = Math.random()
					* this.desires.get(DesireType.getDesireType(conversation))
							.getValue();
		} else if (conversation.isTrait()) {
			value = Math.random()
					* this.traits.get(TraitType.getTraitType(conversation))
							.getValue();
		} else if (conversation.isSkill()) {
			value = Math.random()
					* this.skills.get(SkillType.getSkillType(conversation))
							.getValue();
		}

		return (int) value;
	}
}
