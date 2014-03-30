package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.mouglotte.game.GameState;
import com.mouglotte.map.Tile;

public class BeanTree extends FoodEntity {

	/** Image */
	private SpriteSheet image;
	/** Number of sprites in the sprite sheet */
	private int spriteNumber;
	/** Number of columns in the sprite sheet */
	private int spriteColumns;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 */
	public BeanTree(GameState game, Tile tile) {

		super(game, tile);

		// Set image
		try {
			this.image = new SpriteSheet("res/beanTree/beanTreeLevel1.png", 66,
					90);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.spriteNumber = 9;
		this.spriteColumns = 5;

		// // Set real coordinates
		// this.realX = this.x - this.image.getWidth() / 2;
		// this.realY = this.y - this.image.getHeight() + GameMap.TILE_SIZE / 2
		// - 10;

		// Begins at the first growth level
		this.level = 1;
	}

	/**
	 * Get actual sprite from the sprite sheet
	 * 
	 * @return Actuel sprite
	 */
	private Image getSprite() {

		// Index of the sprite (depending on health surplus)
		int index = 1 + this.healthSurplus / (MAX_HEALTH / this.spriteNumber);
		if (index < 1)
			index = 1;
		if (index > this.spriteNumber)
			index = this.spriteNumber;
		int row = index / (this.spriteColumns + 1);
		int column = (index % this.spriteColumns) - 1;
		if (column == -1)
			column = this.spriteColumns - 1;

		return this.image.getSprite(column, row);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eventDay() {

		// Calculate health
		calculateHealth();
	}

	@Override
	protected void eventHealthChanged(int oldHealth, int newHealth) {

		// Modification de l'image

		// If health is empty
		if (newHealth <= 0) {

			// Le beantree doit mourir

			return;
		}

		// Calculate health surplus
		calculateHealthSurplus();

		// Calculate food amount
		calculateFood(oldHealth, newHealth);
	}

	@Override
	protected void eventLevelChanged(int oldLevel, int newLevel) {

		// Modification de l'image ou du spritesheet
	}

	@Override
	protected void eventFoodChanged(int oldFood, int newFood) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void calculateHealth() {

		// Save current health
		int health = this.health;
		// Define the probability of having a gain or a loss
		int lossLevel = MAX_LEVEL + MAX_AGE;
		// Is it a gain or a loss
		int gainOrLoss = this.random.nextInt(lossLevel);

		// The higher the level is, the harder to grow it is
		lossLevel -= this.level + this.age;

		// Gain health
		if (gainOrLoss <= lossLevel) {

			addHealth(this.random
					.nextInt(MAX_AGE - this.age > 0 ? CURRENT_HEALTH_CHANGE
							* (MAX_AGE - this.age) : 0));
		}
		// Loose health
		else {
			removeHealth(this.random.nextInt(CURRENT_HEALTH_CHANGE * this.age));
		}

		// Health changed
		if (health != this.health)
			eventHealthChanged(health, this.health);
	}

	@Override
	protected void calculateHealthSurplus() {

		// Calculate health surplus
		this.healthSurplus += this.health - LEVEL_MEDIUM_HEALTH;

		// If health surplus is full
		if (this.healthSurplus >= MAX_HEALTH) {

			// Change level (will raise)
			calculateLevel();
			this.healthSurplus = LEVEL_MEDIUM_HEALTH;
		}
		// If health surplus is empty
		else if (this.healthSurplus <= 0) {

			// Change level (will decrease)
			calculateLevel();
			this.healthSurplus = LEVEL_MEDIUM_HEALTH;
		}
	}

	@Override
	protected void calculateLevel() {

		// Save current level
		int level = this.level;

		// Change level depending on health surplus
		if (this.healthSurplus >= MAX_HEALTH)
			this.level++;
		else if (this.healthSurplus <= 0)
			this.level--;

		// Maximum and minimum level
		if (this.level > MAX_LEVEL)
			this.level = MAX_LEVEL;
		if (this.level < 0)
			this.level = 0;

		// Level changed
		if (this.level != level)
			eventLevelChanged(level, this.level);
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
	public void render(GameContainer container, Graphics g) {

		super.render(container, g);

		g.drawImage(getSprite(), this.x, this.y);
		g.drawString(
				Integer.toString(this.health) + ","
						+ Integer.toString(this.healthSurplus), this.x + 10,
				this.y + 10);
	}

	@Override
	// protected void mouseLeftClicked(int x, int y, int clickCount) {
	protected void mouseLeftClicked(int x, int y) {
		// TODO Auto-generated method stub

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
