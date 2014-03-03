package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.mouglotte.game.GameState;
import com.mouglotte.map.Tile;

public abstract class Entity {

	/** Game */
	protected GameState game;

	/** Age */
	protected int age;

	/** Time passed */
	protected int passedTime = 0;
	protected int secondesR = 0;
	protected int minutes = 0;
	protected int hours = 0;
	protected int days = 0;
	protected int months = 0;
	protected int seasons = 0;
	protected int years = 0;

	/** Location */
	protected int x = 0;
	protected int y = 0;
	protected int lastX = 0;
	protected int lastY = 0;
	/** Direction */
	protected Vector2f direction = new Vector2f(0,0);
	/** Tile */
	protected Tile tile;

	/** Mouse over */
	protected boolean over = false;
	/** Selected */
	protected boolean selected = false;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            GameState
	 */

	public Entity(GameState game) {

		// Game
		this.game = game;
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

		// Last position
		this.lastX = this.x;
		this.lastY = this.y;
		// New position
		this.x = x;
		this.y = y;
		
		// Direction
		this.direction = new Vector2f(this.x - this.lastX,this.y - this.lastY);

		// Tile containing these coordinates
		this.tile = this.game.getMap().getTile(x, y);
	}

	/**
	 * Define current location
	 * 
	 * @param tile
	 *            Tile
	 */
	public void setLocation(Tile tile) {

		this.tile = tile;

		// Last position
		this.lastX = this.x;
		this.lastY = this.y;
		// x and y at the center of the tile
		this.x = this.tile.getCenterX();
		this.y = this.tile.getCenterY();
		
		// Direction
		this.direction = new Vector2f(this.x - this.lastX,this.y - this.lastY);
	}

	/**
	 * Get game
	 * 
	 * @return Game
	 */
	public GameState getGame() {
		return this.game;
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
	 * Get x location
	 * 
	 * @return x location
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get y location
	 * 
	 * @return y location
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Get direction
	 * @return Direction
	 */
	public Vector2f getDirection() {
		return this.direction;
	}
	
	/**
	 * Get tile containing the entity
	 * 
	 * @return Tile containing the entity
	 */
	public Tile getTile() {
		return this.tile;
	}

	/**
	 * Is entity selected
	 * 
	 * @return true if entity is selected
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Update entity
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	public void update(GameContainer container, int delta) {

		// Passed time
		this.passedTime += delta;

		// Move (if entity has to move)
		move();

		// Every real second
		if (this.passedTime >= 1000) {

			// One real second has passed
			this.secondesR++;
			this.passedTime -= 1000;
			// Event every real second
			eventRealSecond();
		}

		// Every 3 real seconds
		// = mouglotte minute
		if (this.secondesR >= 3) {

			// One mouglotte minute has passed
			this.minutes++;
			this.secondesR = 0;
			// Event every mouglotte minute
			eventMinute();
		}

		// Every 3 real minutes
		// = mouglotte hour
		if (this.minutes >= 60) {

			// One mouglotte hour has passed
			this.hours++;
			this.minutes = 0;
			// Event every mouglotte hour
			eventHour();
		}

		// Every real hour
		// = mouglotte day
		if (this.hours >= 20) {

			// One mouglotte day has passed
			this.days++;
			this.hours = 0;
			// Event every mouglotte day
			eventDay();
		}

		// Every 12 real hours
		// = mouglotte month
		if (this.days >= 12) {

			// One mouglotte month has passed
			this.months++;
			this.days = 0;
			// Event every mouglotte month
			eventMonth();
		}

		// Every 24 real hours
		// = mouglotte season
		if (this.months >= 2) {

			// One mouglotte season has passed
			this.seasons++;
			this.months = 0;
			// Event every mouglotte season
			eventSeason();
		}

		// Every 3 real days
		// = mouglotte year
		if (this.seasons >= 3) {

			// One mouglotte year has passed
			this.years++;
			this.seasons = 0;
			// Event every mouglotte year
			eventYear();
		}

		// Handle inputs
		handleInputs(container, delta);
	}

	/**
	 * Event called every real second
	 */
	protected abstract void eventRealSecond();

	/**
	 * Event called every mouglotte minute
	 */
	protected abstract void eventMinute();

	/**
	 * Event called every mouglotte hour
	 */
	protected abstract void eventHour();

	/**
	 * Event called every mouglotte day
	 */
	protected abstract void eventDay();

	/**
	 * Event called every mouglotte month
	 */
	protected abstract void eventMonth();

	/**
	 * Event called every mouglotte season
	 */
	protected abstract void eventSeason();

	/**
	 * Event called every mouglotte year
	 */
	public void eventYear() {

		// Happy birthday
		this.age++;
	}

	/**
	 * Move
	 */
	protected abstract void move();

	/**
	 * Handle inputs
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	protected void handleInputs(GameContainer container, int delta) {
		
		// Mouse moved
		mouseMoved(container.getInput().getMouseX(), container.getInput().getMouseY());
		
		// Mouse left clicked
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
			mouseLeftClicked(container.getInput().getMouseX(), container.getInput().getMouseY());
			
			// Mouse right clicked
		if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
			mouseRightClicked(container.getInput().getMouseX(), container.getInput().getMouseY());
	}
	
	/**
	 * Mouse moved
	 * @param x x position
	 * @param y y position
	 */
	protected abstract void mouseMoved(int x, int y);

	/**
	 * Mouse left clicked
	 * @param x x position
	 * @param y y position
	 */
	protected abstract void mouseLeftClicked(int x, int y);

	/**
	 * Mouse right clicked
	 * @param x x position
	 * @param y y position
	 */
	protected abstract void mouseRightClicked(int x, int y);

	/**
	 * Render
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	public abstract void render(GameContainer container, Graphics g);
}
