package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import com.mouglotte.game.GameState;

public class BeanTree extends FoodEntity {

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
	 *            Amount of food
	 */
	public BeanTree(GameState game, int i, int j) {

		super(game, j, j);

		// // Set image
		// try {
		// this.image = new Image("res/mushroom.png", new Color(255, 0, 255));
		// } catch (SlickException e) {
		// e.printStackTrace();
		// }
		//
		// // Set real coordinates
		// this.realX = this.x - this.image.getWidth() / 2;
		// this.realY = this.y - this.image.getHeight() + GameMap.TILE_SIZE / 2
		// - 10;

		// Begins at the first growth level
		this.level = 1;
	}

	@Override
	protected void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventMinute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventHour() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventDay() {

		// Calculate health
		calculateHealth();

		// Calculate growth level
		calculateLevel();
	}

	@Override
	protected void eventMonth() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventSeason() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventYear() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventHealthChanged(int oldHealth, int newHealth) {

		// Modification de l'image
		
		
		// Calculate food amount
		calculateFood(oldHealth, newHealth);
	}

	@Override
	protected void eventLevelChanged(int oldLevel, int newLevel) {

		// Modification de l'image ou du spritesheet
	}

	@Override
	protected void calculateHealth() {

		// Save current health
		int health = this.health;
		// Define the probability of having a gain or a loss
		int lossLevel = 5;
		// Is it a gain or a loss
		int gainOrLoss = this.random.nextInt(lossLevel);
		// Value of health gain or loss
		int update = this.random.nextInt(CURRENT_HEALTH_CHANGE);

		// The higher the level is, the harder to grow it is
		switch (this.level) {
		case 0:
			break;
		case 1:
			lossLevel = lossLevel - 1;
			break;
		case 2:
			lossLevel = lossLevel - 2;
			break;
		case 3:
			lossLevel = lossLevel - 3;
			break;
		case 4:
			lossLevel = lossLevel - 4;
			break;
		case 5:
			lossLevel = lossLevel - 5;
			break;
		}

		// Gain or loose health
		if (gainOrLoss <= lossLevel)
			addHealth(update);
		else
			removeHealth(update);

		// Health changed
		eventHealthChanged(health, this.health);
	}

	@Override
	protected void calculateLevel() {

		// Save current level
		int level = this.level;

		// Calculate level depending on health
		if (this.health <= LEVEL_1_HEALTH)
			this.level = 0;
		else if (this.health < LEVEL_2_HEALTH)
			this.level = 1;
		else if (this.health < LEVEL_3_HEALTH)
			this.level = 2;
		else if (this.health < LEVEL_4_HEALTH)
			this.level = 3;
		else if (this.health < LEVEL_5_HEALTH)
			this.level = 4;
		else
			this.level = 5;

		// If level has changed
		if (this.level != level) {
			// Level changed
			eventLevelChanged(level, this.level);
		}
	}

	@Override
	protected void calculateFood(int oldHealth, int newHealth) {

		// If health gained
		if (newHealth > oldHealth) {
			// Add food
			addFood(newHealth - oldHealth);
		}
		// If health lost
		else if (oldHealth > newHealth) {
			// Remove food
			removeFood(oldHealth - newHealth);
		}
	}

	@Override
	protected void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseLeftClicked(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventFoodChanged(int oldFood, int newFood) {
		// TODO Auto-generated method stub

	}
}
