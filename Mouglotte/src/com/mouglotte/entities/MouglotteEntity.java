package com.mouglotte.entities;

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

		// TESTS
		setLocation(this.map.getTile(2, 2));

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

		Tile neighbor = null;
		Entity actWith = null;

		// Starts with current tile
		switch (type) {
		case FOOD:
			actWith = this.tile.getFood();
		case FRIEND:
			actWith = this.tile.getFriend(this.mouglotte);
		case LOVER:
			actWith = this.tile.getLover(this.mouglotte);
		case ENEMY:
			actWith = this.tile.getEnemy(this.mouglotte);
		case WORK:
			actWith = this.tile.getWork();

			// Something to act with has been found
			if (actWith != null) {

				// The mouglotte will act with this
				this.mouglotte.actWith(actWith);
				// It has been found on the tile
				return this.tile;
			}
			break;
		default:
			break;
		}

		// Search through neighbors
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// Not a neighbor, its the current tile
				if ((i == 0) && (j == 0)) {
					continue;
				}
				
				// Get neighbor tile
				neighbor = this.map.getTile(this.tile.getColumn() + i,
						this.tile.getRow() + j);

				switch (type) {
				case FOOD:
					actWith = neighbor.getFood();
				case FRIEND:
					actWith = neighbor.getFriend(this.mouglotte);
				case LOVER:
					actWith = neighbor.getLover(this.mouglotte);
				case ENEMY:
					actWith = neighbor.getEnemy(this.mouglotte);
				case WORK:
					actWith = neighbor.getWork();

					// Something to act with has been found
					if (actWith != null) {

						// The mouglotte will act with this
						this.mouglotte.actWith(actWith);
						// It has been found on the tile
						return neighbor;
					}
					break;
				default:
					break;
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
	protected void mouseMoved(int x, int y) {

		// Set mouse over
		this.over = this.shape.contains(GameMap.convNoScrollX(x),
				GameMap.convNoScrollY(y));

		// Il faut faire un petit idle
	}

	@Override
	protected void mouseLeftClicked(int x, int y) {

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
