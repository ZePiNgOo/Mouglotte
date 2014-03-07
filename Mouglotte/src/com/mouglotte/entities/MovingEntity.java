package com.mouglotte.entities;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Mover;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Path;
import com.mouglotte.map.Tile;
import com.mouglotte.map.Vector;
import com.mouglotte.utilities.Debug;

public abstract class MovingEntity extends Entity implements Mover {

	/** Stop distance (in pixel) */
	protected final static int STOP_DISTANCE = 1;
	protected final static float SENSOR_RADIUS = 3;
	protected final static float TURN_RATE = (float) (3 * Math.PI);
	protected final static float WALKING_SPEED = 20;

	/** Map */
	protected GameMap map;
	/** Path */
	protected Path path;
	protected int pathIndex;

	/** Last position */
	protected int lastX = 0;
	protected int lastY = 0;
	protected Tile lastTile;

	/** Target */
	protected int targetX = 0;
	protected int targetY = 0;
	protected Tile targetTile;

	/** Direction */
	protected Vector vector;

	/** Sensors */
	protected ObstacleSensor[] sensors;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 */
	public MovingEntity(GameState game) {

		super(game);

		// Map
		this.map = this.game.getMap();

		// Movement vector
		this.vector = new Vector(0, 0);

		// Obstacles sensors
		this.sensors = buildObstacleSensors();
	}

	@Override
	public void setLocation(int x, int y) {

		// Last position
		this.lastX = this.x;
		this.lastY = this.y;
		if (this.lastTile != this.tile)
			this.lastTile = this.tile;

		// New location
		super.setLocation(x, y);
	}

	@Override
	public void setLocation(Tile tile) {

		// Last position
		this.lastX = this.x;
		this.lastY = this.y;
		if (this.lastTile != this.tile)
			this.lastTile = this.tile;

		// New location
		super.setLocation(tile);
	}

	/**
	 * Set target location
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	protected void setTargetLocation(int x, int y) {

		this.targetX = x;
		this.targetY = y;

		// Tile containing these coordinates
		this.targetTile = this.game.getMap().getTileAtPosition(x, y);
	}

	/**
	 * Set target location
	 * 
	 * @param tile
	 *            Tile
	 */
	protected void setTargetLocation(Tile tile) {

		this.targetTile = tile;

		// x and y at the center of the tile
		this.targetX = this.targetTile.getCenterX();
		this.targetY = this.targetTile.getCenterY();
	}

	/**
	 * Set new target location
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	protected void setNewTargetLocation(int x, int y) {
		setTargetLocation(x, y);
		this.vector.setMagnitude(WALKING_SPEED);
	}

	/**
	 * Set new target location
	 * 
	 * @param tile
	 *            Tile
	 */
	protected void setNewTargetLocation(Tile tile) {
		setTargetLocation(tile);
		this.vector.setMagnitude(WALKING_SPEED);
	}

	/**
	 * Get map
	 * 
	 * @return Map
	 */
	public GameMap getMap() {
		return this.map;
	}

	/**
	 * Get current direction (in radians)
	 * 
	 * @return Direction
	 */
	protected float getDirection() {
		return this.vector.getDirection();
	}

	/**
	 * Get direction to target (in radians)
	 * 
	 * @return Direction
	 */
	protected float getDirectionToTarget() {
		return Vector.getDirectionFromDeltas(this.targetX - this.x,
				this.targetY - this.y);
	}

	/**
	 * Gets a {@link Point2D} relative to this entity, rotated in the direction
	 * of travel, and assuming the center of the entity is the origin of the
	 * coordinate system. For example, if a entity's direction of travel is 'A',
	 * and their center point is (Px, Py), then this method will return (rx, rx)
	 * rotated by 'A', and then translated by (Px, Py).
	 * 
	 * @param rx
	 *            relative x position
	 * @param ry
	 *            relative y position
	 * @return a {@link Point2D.Float} representing the specified coordinates,
	 *         relative to the entity's center, and rotation (direction).
	 */
	protected Point2D.Float getRelativePointFromCenter(float rx, float ry) {
		float resultX = (float) ((rx * (Math.cos(this.vector.getDirection()))) - (ry * (Math
				.sin(this.vector.getDirection()))));
		float resultY = (float) ((rx * (Math.sin(this.vector.getDirection()))) + (ry * (Math
				.cos(this.vector.getDirection()))));

		resultX += this.x;
		resultY += this.y;

		return (new Point2D.Float(resultX, resultY));
	}

	/**
	 * Is the entity on a new tile ?
	 * 
	 * @return True if the entity is on a new tile
	 */
	public boolean isOnANewTile() {
		return this.tile != this.lastTile;
	}

	/**
	 * Build obstacle sensors
	 * 
	 * @return Sensors
	 */
	private ObstacleSensor[] buildObstacleSensors() {
		return (new ObstacleSensor[] {
				new ObstacleSensor(this, SENSOR_RADIUS, -SENSOR_RADIUS,
						TURN_RATE, 0.1f),
				new ObstacleSensor(this, SENSOR_RADIUS, SENSOR_RADIUS,
						-TURN_RATE, 0.1f),
				new ObstacleSensor(this, 2 * SENSOR_RADIUS,
						-SENSOR_RADIUS * 1.5f, TURN_RATE / 2, 0.5f),
				new ObstacleSensor(this, 2 * SENSOR_RADIUS,
						SENSOR_RADIUS * 1.5f, -TURN_RATE / 2, 0.5f),
				new ObstacleSensor(this, 3 * SENSOR_RADIUS,
						-SENSOR_RADIUS * 2.5f, TURN_RATE / 3, 1),
				new ObstacleSensor(this, 3 * SENSOR_RADIUS,
						SENSOR_RADIUS * 2.5f, -TURN_RATE / 3, 1),
				new ObstacleSensor(this, 4 * SENSOR_RADIUS, -SENSOR_RADIUS,
						TURN_RATE / 3, 1),
				new ObstacleSensor(this, 4 * SENSOR_RADIUS, SENSOR_RADIUS,
						-TURN_RATE / 3, 1), });
	}

	@Override
	public void update(GameContainer container, int delta) {

		// Move entity
		move(delta);

		super.update(container, delta);
	}

	/**
	 * Move entity
	 * 
	 * @param delta
	 */
	protected void move(int delta) {

		// Eventually remove entity from last tile (only if on a new tile)
		this.tile.removeEntity(this);

		// If the entity has reached its target
		if (hasReachedTarget()) {

			Debug.log("MOVING", "MovingEntity::Move:Target reached");

			// If the entity is still on a path
			if (isOnAPath()) {

				// Head toward next target if exists
				this.pathIndex++;
				if (this.pathIndex >= this.path.getLength()) {
					stop();
				} else {
					// headToward(this.path.getX(this.pathIndex),
					// this.path.getY(this.pathIndex));
					headToward(this.map.getTile(this.path.getX(this.pathIndex),
							this.path.getY(this.pathIndex)));
				}
			}

			// If the entity hasn't reached its target
		} else {

			if (isOnAPath()) {
				// Debug.log("MOVING", "MovingEntity::Move:Target not reached");

				boolean collisionSteeringUsed = false;
				float speedMultiplier = 1.0f;

				MovingEntity entitySensed = null;
				boolean theRelativeTileIsBlocked = false;

				// Basic delta and direction establishment
				// Vector deltaVector = Vector.getVectorFromComponents(
				// (float) (this.vector.getMagnitude() * Math
				// .cos(getDirection())) * (delta / 1000.0f),
				// (float) (this.vector.getMagnitude() * Math
				// .sin(getDirection())) * (delta / 1000.0f));
				Vector deltaVector = Vector.getVectorFromComponents(
						(float) (this.vector.getMagnitude() * Math
								.cos(getDirection())), (float) (this.vector
								.getMagnitude() * Math.sin(getDirection())));

				for (ObstacleSensor s : this.sensors) {
					entitySensed = s.relativePointSensesEntity(this.map);
					theRelativeTileIsBlocked = s
							.relativeTileIsBlocked(this.map);
					if (theRelativeTileIsBlocked || (entitySensed != null)) {
						this.vector.setDirection(this.vector.getDirection()
								+ (s.turnRate * (delta / 1000.0f)));
						speedMultiplier = s.speedMultiplier;
						collisionSteeringUsed = true;
						break;
					}
				}

				// Target steering - only done if collision steering was not
				// used
				if (!collisionSteeringUsed) {
					float targetDirectionDelta = getDirectionToTarget()
							- getDirection();
					if (targetDirectionDelta < 0) {
						targetDirectionDelta += 2 * (Math.PI);
					}
					if (targetDirectionDelta > 2 * Math.PI) {
						targetDirectionDelta -= 2 * Math.PI;
					}

					if (targetDirectionDelta > Math.PI / 4) {
						if (targetDirectionDelta < Math.PI) {
							this.vector.setDirection(this.vector.getDirection()
									+ (TURN_RATE * (delta / 1000.0f)));
						} else {
							this.vector.setDirection(this.vector.getDirection()
									- (TURN_RATE * (delta / 1000.0f)));
						}
					}
				}

				// Set entity location to the new location
				int newX = (int) (this.x + deltaVector.getXComponent());
				int newY = (int) (this.y + deltaVector.getYComponent());
				if (newX != this.x && newY != this.y) {
					Debug.log("MOVING", "MovingEntity::Move:Moving to " + newX
							+ "," + newY);
					setLocation(newX, newY);
				}

				// lastTileMapBlock = getCoordinatesOfCurrentBlock();
			}
		}

		// Register entity in the new tile
		this.tile.addEntity(this);

		// if (this.path != null) {
		//
		// // Move to the center of the next tile
		// int destX = this.path.getStep(0).getX() * GameMap.TILE_SIZE
		// + GameMap.TILE_SIZE / 2;
		// int destY = this.path.getStep(0).getY() * GameMap.TILE_SIZE
		// + GameMap.TILE_SIZE / 2;
		//
		// // Move one pixel to this location
		// int dirX = destX - this.x;
		// if (dirX != 0)
		// dirX = dirX * 1 / Math.abs(dirX);
		// int dirY = destY - this.y;
		// if (dirY != 0)
		// dirY = dirY * 1 / Math.abs(dirY);
		//
		// // Set location
		// setLocation(this.x + dirX, this.y + dirY);
		//
		// // Arrived at destination
		// if (hasReachedTarget(this.path.getStep(0))) {
		//
		// // Step is done, delete
		// this.path.removeStep(0);
		// }
		//
		// // No more step, path is done
		// if (this.path.getLength() == 0) {
		// this.path = null;
		// }
		// }
	}

	/**
	 * Cancel path
	 */
	// protected void clearPath() {
	// this.path = null;
	// }

	/**
	 * Send entity to a location
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	protected void headToward(int x, int y) {
		setTargetLocation(x, y);
	}

	/**
	 * Send entity to a location
	 * 
	 * @param tile
	 *            Tile
	 */
	protected void headToward(Tile tile) {
		setTargetLocation(tile);
	}

	/**
	 * Stop moving
	 */
	public void stop() {
		this.vector = new Vector(0, 0);
		this.targetX = this.x;
		this.targetY = this.y;
		this.targetTile = null;
		this.path = null;
		this.pathIndex = 0;
	}

	/**
	 * Has the entity reached its target
	 * 
	 * @return true if the entity is at destination
	 */
	// public boolean hasReachedTarget() {
	// if (this.path != null)
	// return hasReachedTarget(this.path.getLastStep());
	// else
	// return true;
	// }

	/**
	 * Has the entity reached its target
	 * 
	 * @return true if the entity is at destination
	 */
	// public boolean hasReachedTarget(Step step) {
	//
	// if (this.path != null) {
	//
	// // Move to the center of the next tile
	// int destX = step.getX() * GameMap.TILE_SIZE + GameMap.TILE_SIZE / 2;
	// int destY = step.getY() * GameMap.TILE_SIZE + GameMap.TILE_SIZE / 2;
	//
	// // Precision is one pixel
	// if (Math.abs(this.x - destX) <= 1 && Math.abs(this.y - destY) <= 1)
	// return true;
	// else
	// return false;
	//
	// } else
	// return true;
	// }

	public boolean hasReachedTarget() {
		return (distanceToPoint(this.targetX, this.targetY) <= STOP_DISTANCE);
	}

	/**
	 * Distance between the current position and the target
	 * 
	 * @return Distance
	 */
	protected float distanceToTarget() {
		return distanceToPoint(this.targetX, this.targetY);
	}

	/**
	 * Distance between the current position and a point
	 * 
	 * @param x
	 *            x position of the point
	 * @param y
	 *            y position of the point
	 * @return Distance
	 */
	protected float distanceToPoint(int x, int y) {
		return (float) Math.hypot(this.x - x, this.y - y);
	}

	/**
	 * Is the entity on a path
	 * 
	 * @return True if the entity is on a path
	 */
	protected boolean isOnAPath() {
		return (this.path != null);
	}

	/**
	 * Go to somewhere
	 * 
	 * @param tile
	 *            Go to this tile
	 */
	public void goTo(Tile tile) {

		if (tile == null)
			return;

		// Initialize visited tiles
		this.map.clearVisited();

		// Search path
		Path path = this.map.findPath(this, this.getTile(), tile);

		// Set path
		headAlongPath(path);

		if (path == null)
			Debug.log("MOVING_ENTITY", "MovingEntity::GoTo:No path found");
		else
			Debug.log(
					"MOVING_ENTITY",
					"MovingEntity::GoTo " + tile.getCenterX() + ","
							+ tile.getCenterY());
	}

	/**
	 * Walk along the path
	 * 
	 * @param path
	 *            Path
	 */
	protected void headAlongPath(Path path) {

		if (path != null) {

			this.path = path;
			this.pathIndex = 0;
			if (this.path.getLength() > 0)
				// setNewTargetPoint(this.path.getX(this.pathIndex),
				// this.path.getY(this.pathIndex));
				setNewTargetLocation(this.map.getTile(
						this.path.getX(this.pathIndex),
						this.path.getY(this.pathIndex)));
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		// TESTS
		if (this.path != null)
			for (int a = 0; a > this.path.getLength(); a++)
				// g.fillRect(this.path.getStep(a).getX(), this.path.getStep(a)
				// .getY(), 2, 2);
				g.fillRect(
						this.map.getTile(this.path.getX(a), this.path.getY(a))
								.getCenterX(),
						this.map.getTile(this.path.getX(a), this.path.getY(a))
								.getCenterY(), 2, 2);
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

	/**
	 * This class implements a simple obstacle sensor, used to tell the
	 * Pedestrian when an obstacle is encountered.
	 */
	private class ObstacleSensor {

		/** The Pedestrian associated with this ObstacleSensor. */
		private MovingEntity entity;
		private float rx, ry;
		private float speedMultiplier;
		private float turnRate;

		/**
		 * Builds a new obstacle sensor.
		 * 
		 * @param ped
		 *            the Pedestrian who owns this sensor.
		 * @param relativeX
		 *            the x-coordinate of the position relative to the
		 *            Pedestrian's center point and direction of travel
		 * @param relativeY
		 *            the y-coordinate of the position relative to the
		 *            Pedestrian's center point, and direction of travel
		 * @param turnRate
		 *            the rate at which the Pedestrian should turn when this
		 *            sensor indicates an obstacle
		 * @param speedMultiplier
		 *            the speed multiplier applied to the Pedestrian's speed,
		 *            when this sensor indicates an obstacle
		 */
		public ObstacleSensor(MovingEntity entity, float relativeX,
				float relativeY, float turnRate, float speedMultiplier) {
			this.entity = entity;
			this.rx = relativeX;
			this.ry = relativeY;
			this.turnRate = turnRate;
			this.speedMultiplier = speedMultiplier;
		}

		/**
		 * Gets whether or not the PedestrianTileBasedMap tile is blocked, or
		 * open.
		 * 
		 * @param tileMap
		 *            the PedestrianTileBasedMap to use
		 * @return true, if the corresponding tile is blocked, false if it is
		 *         open.
		 */
		public boolean relativeTileIsBlocked(GameMap map) {

			// Get the point of the entity that may encounter an obstacle
			Point2D.Float relativePoint = this.entity
					.getRelativePointFromCenter(rx, ry);

			// Get the tile this point is in
			Tile tileSensed = map.getTile(
					(int) (relativePoint.x / GameMap.TILE_SIZE),
					(int) (relativePoint.y / GameMap.TILE_SIZE));

			// Search for an entity in this tile
			if (tileSensed != null)
				return tileSensed.isBlocked();
			return false;
		}

		/**
		 * Gets whether or not this sensor senses a Pedestrian.
		 * 
		 * @param tileMap
		 *            the PedestrianTileBasedMap to use, to get the TileState,
		 *            and the resulting list of Pedestrians in that tile
		 * @return true if a Pedestrian is detected, false otherwise.
		 */
		public MovingEntity relativePointSensesEntity(GameMap map) {

			MovingEntity entitySensed = null;

			// Get the point of the entity that may encounter an obstacle
			Point2D.Float relativePoint = this.entity
					.getRelativePointFromCenter(rx, ry);

			// Get the tile this point is in
			Tile tileSensed = map.getTile(
					(int) (relativePoint.x / GameMap.TILE_SIZE),
					(int) (relativePoint.y / GameMap.TILE_SIZE));

			// Search for an entity in this tile
			if (tileSensed != null) {
				LinkedList<Entity> entities = tileSensed.getEntities();

				for (Entity e : entities) {
					if (Math.hypot(relativePoint.x - e.getX(), relativePoint.y
							- e.getX()) <= SENSOR_RADIUS) {
						entitySensed = (MovingEntity) e;
						break;
					}
				}
			}

			return entitySensed;
		}

	}

}
