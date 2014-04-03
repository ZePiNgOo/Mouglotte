package com.mouglotte.entities;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Tile;

public abstract class FoodEntity extends Entity {

	/** Maximum health */
	protected final static int MAX_HEALTH = 1000;
	/** Current health change (gain or loss) */
	protected static int CURRENT_HEALTH_CHANGE = 10;
	/** Levels (value of minimum health for each level) */
	protected static int LEVEL_1 = 1;
	protected static int LEVEL_2 = 2;
	protected static int LEVEL_3 = 3;
	protected static int LEVEL_4 = 4;
	protected static int LEVEL_5 = 5;
	protected static int LEVEL_6 = 6;
	protected static int LEVEL_7 = 7;
	protected static int LEVEL_8 = 8;
	protected static int LEVEL_9 = 9;
	protected static int LEVEL_10 = 10;
	protected static int MAX_LEVEL = LEVEL_10;
	protected static int LEVEL_MEDIUM_HEALTH = 500;
	/** Maximum age */
	protected static int MAX_AGE = 10;
	/** Maximum food amount */
	protected static int MAX_FOOD = 1000;
	/** Consume amount (by minute) */
	public static int CONSUME_AMOUNT = 5;

	/** Heath */
	protected int health;
	/** Growth level */
	protected int level;
	/** Health surplus (needed to gain a level) */
	protected int healthSurplus;
	/** Food amount */
	protected int food;

	/** Random */
	protected Random random = new Random();

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 */
	public FoodEntity(GameState game, Tile tile) {

		super(game, tile);

		// Level
		setLevel(LEVEL_1);
	}

	/**
	 * Set level of the entity
	 * 
	 * @param level
	 *            Level
	 */
	public void setLevel(int level) {

		// Level
		this.level = level;
		if (this.level > MAX_LEVEL)
			this.level = MAX_LEVEL;
		// Health
		this.health = LEVEL_MEDIUM_HEALTH;
		this.healthSurplus = LEVEL_MEDIUM_HEALTH;
		// Food
		this.food = MAX_FOOD - (MAX_FOOD/MAX_LEVEL) * (MAX_LEVEL - this.level);
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
		return (this.food > 0);
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
	public void removeFood(int food) {
		this.food -= food;
		if (this.food < 0)
			this.food = 0;
	}

	@Override
	public void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMinute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventHour() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventDay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventYear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMonth() {
		// Nothing
		// Eventually giving fruits ?
	}

	@Override
	public void eventSeason() {
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
	 * Calculate health surplus
	 * 
	 */
	protected abstract void calculateHealthSurplus();

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

	/**
	 * Entity dies
	 */
	protected void die() {
		// Entity is removed from its tile
		this.getTile().removeEntity(this);
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		g.drawString(
				Integer.toString(this.level) + ","
						+ Integer.toString(this.food), this.x
						+ GameMap.TILE_SIZE / 2, this.y + GameMap.TILE_SIZE / 2);
	}

	@Override
	// protected void mouseLeftClicked(int x, int y, int clickCount) {
	protected void mouseLeftClicked(int x, int y) {

		// If mouse is on the mushroom
		if (this.over)
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi déselectionner ce qui l'était
	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	// public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub

	}
}
