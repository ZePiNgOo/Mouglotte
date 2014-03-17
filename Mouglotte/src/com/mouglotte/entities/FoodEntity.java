package com.mouglotte.entities;

import java.util.Random;

import com.mouglotte.game.GameState;

public abstract class FoodEntity extends Entity {

	/** Maximum health */
	protected final static int MAX_HEALTH = 1000;
	/** Current health change (gain or loss) */
	protected static int CURRENT_HEALTH_CHANGE = 10;
	protected static int CURRENT_HEALTH_CHANGE_RANDOM = 1;
	/** Levels (value of minimum health for each level) */
	protected static int LEVEL_1_HEALTH = 0;
	protected static int LEVEL_2_HEALTH = 100;
	protected static int LEVEL_3_HEALTH = 200;
	protected static int LEVEL_4_HEALTH = 300;
	protected static int LEVEL_5_HEALTH = 400;
	/** Maximum food amount */
	protected static int MAX_FOOD = 1000;
	/** Consume amount */
	public static int CONSUME_AMOUNT = 100;

	/** Heath */
	protected int health;
	/** Growth level */
	protected int level;
	/** Food amount */
	protected int food = 0;

	/** Random */
	protected Random random = new Random();

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param i
	 *            Column index
	 * @param j
	 *            Row index
	 * @param amount
	 *            Food amount
	 */
	public FoodEntity(GameState game, int i, int j) {

		super(game);

		// Set tile
		this.tile = this.game.getMap().getTile(i, j);

		// Set location
		setLocation(this.tile);
	}

	/**
	 * Get food amount
	 * 
	 * @return Food amount
	 */
	public int getFood() {
		return this.food;
	}

	/**
	 * Is there food ?
	 * 
	 * @return True if the entity has food
	 */
	public boolean hasFood() {
		return (food > 0);
	}

	/**
	 * Add health to the entity
	 * 
	 * @param health
	 *            Health to add
	 */
	protected void addHealth(int health) {
		this.health += health;
		if (this.health >= MAX_HEALTH)
			this.health = MAX_HEALTH;
	}

	/**
	 * Remove health from the entity
	 * 
	 * @param health
	 *            Health to remove
	 */
	protected void removeHealth(int health) {
		this.health -= health;
		if (this.health < 0)
			this.health = 0;
	}
	
	/**
	 * Add food to the entity
	 * 
	 * @param food
	 *            Food to add
	 */
	protected void addFood(int food) {
		this.food += food;
		if (this.food >= MAX_FOOD)
			this.food = MAX_FOOD;
	}

	/**
	 * Remove food from the entity
	 * 
	 * @param food
	 *            Food to remove
	 */
	protected void removeFood(int food) {
		this.food -= food;
		if (this.food < 0)
			this.food = 0;
	}

	@Override
	protected void eventMonth() {
		// Nothing
		// Eventually giving fruits ?
	}

	@Override
	protected void eventSeason() {
		// Nothing
		// Eventually blossom/loose leaves ?
	}

	/**
	 * Event called if health has changed
	 * 
	 * @param oldHealth
	 *            Old health
	 * @param newHealth
	 *            New health
	 */
	protected abstract void eventHealthChanged(int oldHealth, int newHealth);

	/**
	 * Event called if level has changed
	 * 
	 * @param oldLevel
	 *            Old level
	 * @param newLevel
	 *            New level
	 */
	protected abstract void eventLevelChanged(int oldLevel, int newLevel);

	/**
	 * Event called if food amount has changed
	 * 
	 * @param oldFood
	 *            Old food amount
	 * @param newFood
	 *            New food amount
	 */
	protected abstract void eventFoodChanged(int oldFood, int newFood);

	/**
	 * Calculate health
	 */
	protected abstract void calculateHealth();

	/**
	 * Calculate level
	 */
	protected abstract void calculateLevel();

	/**
	 * Calculate food amount
	 * 
	 * @param oldHealth
	 *            Old health
	 * @param newHealth
	 *            New health
	 */
	protected abstract void calculateFood(int oldHealth, int newHealth);

	@Override
	protected void mouseLeftClicked(int x, int y) {

		// If mouse is on the entity
		if (this.over)
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi déselectionner ce qui l'était
	}
}
