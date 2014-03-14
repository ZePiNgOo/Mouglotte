package com.mouglotte.entities;

import com.mouglotte.game.GameState;

public abstract class FoodEntity extends Entity {

	/** Consume amount */
	public final static int CONSUME_AMOUNT = 100;
	
	/** Food amount */
	protected int foodAmount = 0;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param amount
	 *            Food amount
	 */
	public FoodEntity(GameState game, int amount) {

		super(game);

		// Food amount
		this.foodAmount = amount;
	}

	/**
	 * Get food amount
	 * 
	 * @return Food amount
	 */
	public int getFoodAmount() {
		return this.foodAmount;
	}

	/**
	 * Is there food ?
	 * 
	 * @return True if the entity has food
	 */
	public boolean hasFood() {
		return (foodAmount > 0);
	}

	/**
	 * Consume food
	 * 
	 * @param amount
	 *            Amount of food consumed
	 */
	public void consume(int amount) {
		this.foodAmount -= amount;
		if (this.foodAmount < 0)
			this.foodAmount = 0;
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
