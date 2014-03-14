package com.mouglotte.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;

public class Mushroom extends FoodEntity {

	/** Image */
	private Image image;
	/** Real coordinates */
	private int realX;
	private int realY;

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
	public Mushroom(GameState game, int i, int j, int amount) {

		super(game, amount);

		// Set tile
		this.tile = this.game.getMap().getTile(i, j);

		// Set location
		setLocation(this.tile);

		// Set image
		try {
			this.image = new Image("res/mushroom.png", new Color(255, 0, 255));
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Set real coordinates
		this.realX = this.x - this.image.getWidth() / 2;
		this.realY = this.y - this.image.getHeight() + GameMap.TILE_SIZE / 2
				- 10;
	}

	@Override
	protected void eventRealSecond() {
		// Nothing
	}

	@Override
	protected void eventMinute() {
		// Nothing
	}

	@Override
	protected void eventHour() {
		// Nothing
	}

	@Override
	protected void eventDay() {
		// Nothing
	}

	@Override
	protected void eventMonth() {
		// Nothing
		// Eventually growing ?
	}

	@Override
	protected void eventSeason() {
		// Nothing
		// Eventually die/born ?
	}

	@Override
	protected void mouseMoved(int x, int y) {

		// Si l'image est généralisée dans FoodEntity (voire Entity) on peut
		// gérer cela dans FoodEntity (voire Entity)

		// Check if mouse is on the image (as a rectangle)
		if (GameMap.convNoScrollX(x) >= this.realX
				&& GameMap.convNoScrollX(x) <= this.realX
						+ this.image.getWidth()
				&& GameMap.convNoScrollY(y) >= this.realY
				&& GameMap.convNoScrollY(y) <= this.realY
						+ this.image.getHeight())
			this.over = true;
	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// Nothing
		// If giving fruits, could be a target for food
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		g.drawImage(this.image, realX, realY);
	}

}
