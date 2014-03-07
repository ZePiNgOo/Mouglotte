package com.mouglotte.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Tile;
import com.mouglotte.specy.MemoryType;
import com.mouglotte.specy.Mouglotte;

public class MouglotteEntity extends MovingEntity {

	/** Mouglotte */
	private Mouglotte mouglotte;

	/** Shape (TEST) */
	private Shape shape;
	private Shape halo;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param mouglotte
	 *            Mouglotte
	 */
	public MouglotteEntity(GameState game) {

		super(game);

		// Referenced mouglotte
		this.mouglotte = new Mouglotte(this);

		// Shape (TESTS)
		this.shape = new Circle(this.x, this.y, 10);
		this.halo = new Circle(this.x, this.y, 20);

		// TESTS
		setLocation(100, 100);

	}

	@Override
	public void setLocation(int x, int y) {

		super.setLocation(x, y);

		// Move shape
		if (this.shape != null) {

			this.shape.setX(this.x - this.shape.getWidth() / 2);
			this.shape.setY(this.y - this.shape.getHeight() / 2);

			// Set mouse over
			this.over = this.shape.contains(x, y);
		}

		// Move selection halo
		if (this.halo != null && this.selected) {

			this.halo.setX(this.x - this.halo.getWidth() / 2);
			this.halo.setY(this.y - this.halo.getHeight() / 2);
		}
	}

	/**
	 * Get referenced mouglotte
	 * 
	 * @returns Referenced mouglotte
	 */
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}

	@Override
	protected void eventRealSecond() {
		this.mouglotte.eventRealSecond();
	}

	@Override
	protected void eventMinute() {
		this.mouglotte.eventMinute();
	}

	@Override
	protected void eventHour() {
		this.mouglotte.eventHour();
	}

	@Override
	protected void eventDay() {
		this.mouglotte.eventDay();
	}

	@Override
	protected void eventMonth() {
		// Nothing
	}

	@Override
	protected void eventSeason() {
		// Nothing
	}

	@Override
	public void eventYear() {

		super.eventYear();

		this.mouglotte.eventYear();
	}

	/**
	 * Walk around
	 */
	public void walkaround() {

		// Random r = new Random();
		// int destX = 0, destY = 0;
		// Tile tile = null;

		// // Une chance sur 2 de continuer dans la même direction
		// if (r.nextBoolean() == true) {
		//
		// Debug.log("MOUGLOTTE", "Mouglotte::WalkAround:Same direction");
		//
		// // Current direction
		// float dir = getDirection();
		//
		// La mouglotte marchait, on continue dans la même direction
		// int i = this.entity.getTile().getColumn();
		// if (dir.getX() != 0)
		// i = (int) (i + 5 * dir.getX() / Math.abs(dir.getX()));
		// if (i < 0)
		// i = 0;
		// if (i >= this.entity.getGame().getMap().getWidthInTiles())
		// i = this.entity.getGame().getMap().getWidthInTiles() - 1;
		// int j = this.entity.getTile().getRow();
		// if (dir.getY() != 0)
		// j = (int) (j + 5 * dir.getY() / Math.abs(dir.getY()));
		// if (j < 0)
		// j = 0;
		// if (j >= this.entity.getGame().getMap().getHeightInTiles())
		// j = this.entity.getGame().getMap().getHeightInTiles() - 1;
		// tile = new Tile(i, j);

		// On va dans une direction au hasard
		// (y compris la direction actuelle pour simplifier)
		// } else {

		// Debug.log("MOUGLOTTE", "MouglotteEntity::WalkAround:New direction");

		int randI;
		int randJ;
		double distance;
		Tile tile;

		// Find a random tile at a good distance
		do {
			randI = (int) (Math.random() * this.map.getWidthInTiles());
			randJ = (int) (Math.random() * this.map.getHeightInTiles());
			tile = this.map.getTile(randI, randJ);
			distance = Math.hypot(this.x - tile.getCenterX(),
					this.y - tile.getCenterY());
		} while (distance < 320 || distance > 640);

		// }

		// Go to new tile
		goTo(tile);
	}

	/**
	 * Search for something on a close tile
	 * 
	 * @param type
	 *            What is searched
	 * @return Tile where the searched thing has been found
	 */
	public Tile searchNear(MemoryType type) {

		return null;
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		super.render(container, g);

		Color color = g.getColor();

		// Set color
		if (this.selected)
			g.setColor(Color.blue);
		else if (this.over)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g.fill(this.shape);

		// Display selection "halo"
		if (this.selected)
			g.draw(this.halo);

		// TEST
		g.drawString(this.getX() + "," + this.getY(), this.shape.getX(),
				this.shape.getY() + 20);

		// TEST
		int x = container.getInput().getMouseX();
		int y = container.getInput().getMouseY();
		g.drawString(x + "," + y + "->" + GameMap.convNoScrollX(x) + ","
				+ GameMap.convNoScrollY(y), GameMap.convNoScrollX(x),
				GameMap.convNoScrollY(y) - 10);

		// Back to initial color
		g.setColor(color);
	}

	@Override
	protected void mouseMoved(int x, int y) {

		// Set mouse over
		this.over = this.shape.contains(x, y);
	}

	@Override
	protected void mouseLeftClicked(int x, int y) {

		// If mouse is in the shap
		if (this.shape.contains(x, y))
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi déselectionner ce qui l'était
	}

	@Override
	protected void mouseRightClicked(int x, int y) {

		// If mouglotte is selected
		if (this.selected) {

			// Convert coordinates to scrolled coordinates
			x = GameMap.convScrollX(x);
			y = GameMap.convScrollY(y);
			Tile tile = Tile.create(x, y);

			// Go to clicked location
			goTo(tile);
		}
	}
}
