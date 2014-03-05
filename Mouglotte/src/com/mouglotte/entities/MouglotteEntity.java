package com.mouglotte.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Path;
import com.mouglotte.map.Path.Step;
import com.mouglotte.map.Tile;
import com.mouglotte.map.UnitMover;
import com.mouglotte.specy.Mouglotte;
import com.mouglotte.utilities.Debug;

public class MouglotteEntity extends Entity {

	/** Mouglotte */
	private Mouglotte mouglotte;

	/** Shape (TEST) */
	private Shape shape;
	private Shape halo;

	/** Path */
	private Path path;

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
		setLocation(100,100);
		
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
	 * Define current path
	 * 
	 * @param path
	 *            Current path
	 */
	private void setPath(Path path) {
		this.path = path;
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

		// Do or continue an action
		this.mouglotte.action();
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventSeason() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventYear() {

		super.eventYear();

		this.mouglotte.eventYear();
	}

	/**
	 * Go to somewhere
	 * 
	 * @param tile
	 *            Go to this tile
	 */
	public void goTo(Tile tile) {

		// goTo(tile.getCenterX(), tile.getCenterY());

		Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::GoTo");

		// If location is not on the map, do nothing
		// if (!this.game.getMap().contains(x, y))
		// return;
		if (tile == null)
			return;

		// Initialize visited tiles
		this.game.getMap().clearVisited();

		// Search path
		// Path path = this.game.getMap().findPath(new UnitMover(3), this.x,
		// this.y, x, y);
		Path path = this.game.getMap().findPath(this.getTile(), tile);
		setPath(path);

		if (path == null)
			Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::GoTo:No path found");
		else
			Debug.log(
					"MOUGLOTTE_ENTITY",
					"MouglotteEntity::GoTo" + tile.getCenterX() + ","
							+ tile.getCenterY());

		Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::GoTo::End");
	}

	/**
	 * Go to somewhere
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
//	public void goTo(int x, int y) {
//
//	}

	@Override
	public void move() {

		// Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::Move");

		if (this.path != null) {

			// Move to the center of the next tile
			int destX = this.path.getStep(0).getX() * GameMap.TILE_SIZE
					+ GameMap.TILE_SIZE / 2;
			int destY = this.path.getStep(0).getY() * GameMap.TILE_SIZE
					+ GameMap.TILE_SIZE / 2;

			// Move one pixel to this location
			int dirX = destX - this.x;
			if (dirX != 0)
				dirX = dirX * 1 / Math.abs(dirX);
			int dirY = destY - this.y;
			if (dirY != 0)
				dirY = dirY * 1 / Math.abs(dirY);

			// Set location
			setLocation(this.x + dirX, this.y + dirY);

			// Arrived at destination
			if (arrived(this.path.getStep(0))) {

				// Step is done, delete
				this.path.removeStep(0);

				// Debug.log("MOUGLOTTE_ENTITY",
				// "MouglotteEntity::Move:Path step done");
			}

			// No more step, path is done
			if (this.path.getLength() == 0) {

				this.path = null;

				// Debug.log("MOUGLOTTE_ENTITY",
				// "MouglotteEntity::Move:Path done");
			}
		} else
			Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::Move:No path");

		// Debug.log("MOUGLOTTE_ENTITY", "MouglotteEntity::Move::End");
	}

	/**
	 * Cancel path
	 */
	public void clearPath() {
		this.path = null;
	}

	/**
	 * Is the mouglotte at destination
	 * 
	 * @return true if the mouglotte is at destination
	 */
	public boolean arrived(Step step) {

		if (this.path != null) {

			// Move to the center of the next tile
			int destX = step.getX() * GameMap.TILE_SIZE + GameMap.TILE_SIZE / 2;
			int destY = step.getY() * GameMap.TILE_SIZE + GameMap.TILE_SIZE / 2;

			// Precision is one pixel
			if (Math.abs(this.x - destX) <= 1 && Math.abs(this.y - destY) <= 1)
				return true;
			else
				return false;

		} else
			return true;
	}

	/**
	 * Is the mouglotte at destination
	 * 
	 * @return true if the mouglotte is at destination
	 */
	public boolean arrived() {
		if (this.path != null)
			return arrived(this.path.getLastStep());
		else
			return true;
	}

	@Override
	public void render(GameContainer container, Graphics g) {

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

		// TESTS
		if (this.path != null)
			for (int a = 0; a > this.path.getLength(); a++)
				g.fillRect(this.path.getStep(a).getX(), this.path.getStep(a)
						.getY(), 2, 2);

		// Back to initial color
		g.setColor(color);
	}

	@Override
	protected void mouseMoved(int x, int y) {

		// Set mouse over
		this.over = this.shape.contains(x, y);

		// Search path
//		Path path = this.game.getMap().findPath(new UnitMover(3), this.x,
//				this.y, x, y);
//		setPath(path);
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
