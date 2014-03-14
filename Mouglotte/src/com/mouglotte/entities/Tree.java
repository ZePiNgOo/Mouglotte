package com.mouglotte.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.mouglotte.game.GameState;

public class Tree extends WorkingEntity {

	/** Shape (TEST) */
	private Shape shape;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param i
	 *            Column index
	 * @param j
	 *            Row index
	 */
	public Tree(GameState game, int i, int j) {

		// Working entity of type WOODCUTING
		super(game, WorkType.WOODCUTING);

		// Set tile
		this.tile = this.game.getMap().getTile(i, j);

		// Set location
		setLocation(this.tile);

		// Shape (TESTS)
		// this.x and this.y indicate the center of the tile
		this.shape = new Rectangle(this.x - 25, this.y - 140, 50, 150);
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
		// Eventually giving fruits ?
	}

	@Override
	protected void eventSeason() {
		// Nothing
		// Eventually blossom/loose leaves ?
	}

	@Override
	protected void mouseMoved(int x, int y) {
		// Nothing (hard to handle mouse over)
	}

	@Override
	protected void mouseLeftClicked(int x, int y) {
		// Nothing
	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// Nothing
		// If giving fruits, could be a target for food
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		Color color = g.getColor();

		// Set color
		g.setColor(Color.green);

		g.fill(this.shape);

		// Back to initial color
		g.setColor(color);
	}

}
