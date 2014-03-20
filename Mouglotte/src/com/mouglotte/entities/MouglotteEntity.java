package com.mouglotte.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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

	private int numero;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 */
	public MouglotteEntity(GameState game, Tile tile) {

		super(game, tile);

		// Referenced mouglotte
		this.mouglotte = new Mouglotte(this);

		// TESTS
		// setLocation(this.map.getTile(2, 2));
		this.numero = tile.getColumn();
		this.selected = true;

		// Shape (TESTS)
		this.shape = new Circle(this.x, this.y, 10);
		this.halo = new Circle(this.x, this.y, 20);
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
	public boolean contains(int x, int y) {

		return this.shape.contains(GameMap.convNoScrollX(x),
				GameMap.convNoScrollY(y));
	}

	@Override
	public void update(GameContainer container, long delta) {

		super.update(container, delta);

		// Move shape
		if (this.shape != null) {

			this.shape.setX(this.x - this.shape.getWidth() / 2);
			this.shape.setY(this.y - this.shape.getHeight() / 2);
		}

		// Move selection halo
		if (this.halo != null && this.selected) {

			this.halo.setX(this.x - this.halo.getWidth() / 2);
			this.halo.setY(this.y - this.halo.getHeight() / 2);
		}
	}

	@Override
	public void eventRealSecond() {
		this.mouglotte.eventRealSecond();
	}

	@Override
	public void eventMinute() {
		this.mouglotte.eventMinute();
	}

	@Override
	public void eventHour() {
		this.mouglotte.eventHour();
	}

	@Override
	public void eventDay() {
		this.mouglotte.eventDay();
	}

	@Override
	public void eventMonth() {
		// Nothing
	}

	@Override
	public void eventSeason() {
		// Nothing
	}

	@Override
	public void eventYear() {

		// Happy birthday
		this.age++;

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

		// If nothing searched, then you got it
		if (type == null)
			return this.tile;

		Tile retTile = this.tile;
		Entity actWith = null;

		// Starts with current tile
		switch (type) {
		case FOOD:
			actWith = retTile.getFood();
			break;
		case FRIEND:
			actWith = retTile.getFriend(this.mouglotte);
			break;
		case LOVER:
			actWith = retTile.getLover(this.mouglotte);
			break;
		case ENEMY:
			actWith = retTile.getEnemy(this.mouglotte);
			break;
		case WORK:
			actWith = retTile.getWork();
			break;
		default:
			break;
		}

		// Something to act with has been found
		if (actWith != null) {

			// The mouglotte will act with this
			this.mouglotte.actWith(actWith);
			// It has been found on the tile
			return retTile;
		}

		// Search through neighbors
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// Not a neighbor, it's the current tile
				if ((i == 0) && (j == 0)) {
					continue;
				}

				// Get neighbor tile
				retTile = this.map.getTile(this.tile.getColumn() + i,
						this.tile.getRow() + j);

				if (retTile != null) {
					switch (type) {
					case FOOD:
						actWith = retTile.getFood();
						break;
					case FRIEND:
						actWith = retTile.getFriend(this.mouglotte);
						break;
					case LOVER:
						actWith = retTile.getLover(this.mouglotte);
						break;
					case ENEMY:
						actWith = retTile.getEnemy(this.mouglotte);
						break;
					case WORK:
						actWith = retTile.getWork();
						break;
					default:
						break;
					}
				}

				// Something to act with has been found
				if (actWith != null) {

					// The mouglotte will act with this
					this.mouglotte.actWith(actWith);
					// It has been found on the tile
					return retTile;
				}
			}
		}

		// Nothing found
		return null;
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		// super.render(container, g);

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

		// TEST : x,y/tile
		g.drawString(
				this.getX() + "," + this.getY() + "/" + this.tile.getColumn()
						+ "," + this.tile.getRow(), this.shape.getX(),
				this.shape.getY() + 20);

		// TEST : position de la souris
		int x = container.getInput().getMouseX();
		int y = container.getInput().getMouseY();
		g.drawString(x + "," + y + "->" + GameMap.convNoScrollX(x) + ","
				+ GameMap.convNoScrollY(y), GameMap.convNoScrollX(x),
				GameMap.convNoScrollY(y) - 10);

		// Back to initial color
		g.setColor(color);
	}

	@Override
	// public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	//
	// // Set mouse over
	// this.over = contains(GameMap.convNoScrollX(newx),
	// GameMap.convNoScrollY(newy));
	//
	// // Il faut faire un petit idle
	// }
	public void mouseMoved(int x, int y) {

		// Set mouse over
		this.over = contains(GameMap.convNoScrollX(x), GameMap.convNoScrollY(y));

		// Il faut faire un petit idle
	}

	@Override
	// protected void mouseLeftClicked(int x, int y, int clickCount) {
	protected void mouseLeftClicked(int x, int y) {

		int num = this.numero;

		// If mouse is in the shape
		if (this.over)
			this.selected = true;
		else
			this.selected = false;

		// Il faut faire un petit idle

		// Mais il faut aussi déselectionner ce qui l'était
	}

	@Override
	protected void mouseRightClicked(int x, int y) {

		int num = this.numero;

		// If mouglotte is selected
		if (this.selected) {

			// Convert coordinates to scrolled coordinates
			// x = GameMap.convScrollX(x);
			// y = GameMap.convScrollY(y);
			Tile tile = this.map.getTileAtPosition(x, y);

			// Go to clicked location
			goTo(tile);
		}
	}
}
