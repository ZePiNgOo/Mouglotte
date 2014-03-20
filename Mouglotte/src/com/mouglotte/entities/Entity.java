package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Tile;
import com.mouglotte.time.TimeDependent;

public abstract class Entity extends TimeDependent {

	/** Game */
	protected GameState game;

	/** Age */
	protected int age;

	/** Location */
	protected int x = 0;
	protected int y = 0;
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
	 * @param tile
	 *            Tile
	 */

	public Entity(GameState game, Tile tile) {

		// Game
		this.game = game;

		// Set location
		setLocation(tile);
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

		// New position
		this.x = x;
		this.y = y;

		// Tile containing these coordinates
		// x,y are the real coordinates, not scrolled
		this.tile = this.game.getMap().getTileAtPosition(
				GameMap.convScrollX(x), GameMap.convScrollY(y));
		this.tile.addEntity(this);
	}

	/**
	 * Define current location
	 * 
	 * @param tile
	 *            Tile
	 */
	public void setLocation(Tile tile) {

		this.tile = tile;
		this.tile.addEntity(this);

		// x and y at the center of the tile
		this.x = this.tile.getCenterX();
		this.y = this.tile.getCenterY();
	}

	// /**
	// * Set mouse over
	// *
	// * @param mouseOver
	// * True if mouse is over
	// */
	// public void setMouseOver(boolean mouseOver) {
	// this.over = mouseOver;
	// }

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
	 * Check if the entity contains the coordinates
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return The entity contains the coordinates
	 */
	public abstract boolean contains(int x, int y);

	@Override
	public void update(GameContainer container, long delta) {

		// // Passed time
		// this.passedTime += delta;
		//
		// // Every real second
		// if (this.passedTime >= 1000) {
		//
		// // One real second has passed
		// this.secondesR++;
		// this.passedTime -= 1000;
		// // Event every real second
		// eventRealSecond();
		// }
		//
		// // Every 3 real seconds
		// // = mouglotte minute
		// if (this.secondesR >= 3) {
		//
		// // One mouglotte minute has passed
		// this.minutes++;
		// this.secondesR = 0;
		// // Event every mouglotte minute
		// eventMinute();
		// }
		//
		// // Every 3 real minutes
		// // = mouglotte hour
		// if (this.minutes >= 60) {
		//
		// // One mouglotte hour has passed
		// this.hours++;
		// this.minutes = 0;
		// // Event every mouglotte hour
		// eventHour();
		// }
		//
		// // Every real hour
		// // = mouglotte day
		// if (this.hours >= 20) {
		//
		// // One mouglotte day has passed
		// this.days++;
		// this.hours = 0;
		// // Event every mouglotte day
		// eventDay();
		// }
		//
		// // Every 12 real hours
		// // = mouglotte month
		// if (this.days >= 12) {
		//
		// // One mouglotte month has passed
		// this.months++;
		// this.days = 0;
		// // Event every mouglotte month
		// eventMonth();
		// }
		//
		// // Every 24 real hours
		// // = mouglotte season
		// if (this.months >= 2) {
		//
		// // One mouglotte season has passed
		// this.seasons++;
		// this.months = 0;
		// // Event every mouglotte season
		// eventSeason();
		// }
		//
		// // Every 3 real days
		// // = mouglotte year
		// if (this.seasons >= 3) {
		//
		// // One mouglotte year has passed
		// this.years++;
		// this.seasons = 0;
		// // Event every mouglotte year
		// eventYear();
		// }

		super.update(container, delta);

		// Handle inputs
		handleInputs(container, delta);
	}

	// @Override
	// protected void eventRealSecond() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventMinute() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventHour() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventDay() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventMonth() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventSeason() {
	// // Implement if needed in sub class
	// }
	//
	// @Override
	// protected void eventYear() {
	// // Implement if needed in sub class
	// }

	/**
	 * Handle inputs
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	protected void handleInputs(GameContainer container, long delta) {

		// Convert coordinates to not scrolled coordinates
		int x = container.getInput().getMouseX();
		int y = container.getInput().getMouseY();
		// x = GameMap.convNoScrollX(container.getInput().getMouseX());
		// y = GameMap.convNoScrollY(container.getInput().getMouseY());
		// A moving entity has to keep its real coordinates (non scrolled)
		// because it's impossible to scroll the coordinates of every entity
		// when the map is scrolled

		// Mouse moved
		mouseMoved(x, y);

		// Mouse left clicked
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
			mouseLeftClicked(x, y);

		// Mouse right clicked
		if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			// x = container.getInput().getMouseX();
			// y = container.getInput().getMouseY();
			// x = GameMap.convNoScrollX(container.getInput().getMouseX());
			// y = GameMap.convNoScrollY(container.getInput().getMouseY());
			mouseRightClicked(x, y);
		}
	}

	/**
	 * Render
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	public abstract void render(GameContainer container, Graphics g);

	/**
	 * Mouse clicked event
	 * 
	 * @param button
	 *            Button clicked
	 * @param x
	 *            x position of the mouse
	 * @param y
	 *            y position of the mouse
	 * @param clickCount
	 *            Number of clicks
	 */
//	@Override
//	public void mouseClicked(int button, int x, int y, int clickCount) {
//
//		// x = container.getInput().getMouseX();
//		// y = container.getInput().getMouseY();
//		// x = GameMap.convNoScrollX(container.getInput().getMouseX());
//		// y = GameMap.convNoScrollY(container.getInput().getMouseY());
//
//		if (button == 0)
//			mouseLeftClicked(x, y, clickCount);
//		else if (button == 1)
//			mouseRightClicked(x, y);
//	}

	/**
	 * Mouse left clicked
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
//	protected abstract void mouseLeftClicked(int x, int y, int clickCount);
	protected abstract void mouseLeftClicked(int x, int y);

	/**
	 * Mouse right clicked
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	protected abstract void mouseRightClicked(int x, int y);

	/**
	 * Mouse moved event
	 * 
	 * @param oldx
	 *            Old x position of the mouse
	 * @param oldy
	 *            Old y position of the mouse
	 * @param newx
	 *            Current x position of the mouse
	 * @param newy
	 *            Current y position of the mouse
	 */
//	@Override
//	public abstract void mouseMoved(int oldx, int oldy, int newx, int newy);
	protected abstract void mouseMoved(int x, int y);
//
//	@Override
//	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mousePressed(int arg0, int arg1, int arg2) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseReleased(int arg0, int arg1, int arg2) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseWheelMoved(int arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void inputEnded() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void inputStarted() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean isAcceptingInput() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void setInput(Input arg0) {
//		// TODO Auto-generated method stub
//
//	}
}
