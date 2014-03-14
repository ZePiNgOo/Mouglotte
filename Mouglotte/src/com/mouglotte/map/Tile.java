package com.mouglotte.map;

import java.util.LinkedList;
import java.util.Map.Entry;

import com.mouglotte.entities.Entity;
import com.mouglotte.specy.Desire;
import com.mouglotte.specy.DesireType;
import com.mouglotte.specy.Mouglotte;

public class Tile {

	/** Column and row in the tiles table */
	private int i = 0;
	private int j = 0;

	/** Blocked */
	private boolean blocked = false;

	/** List of entities in this tile */
	private LinkedList<Entity> entities;

	/**
	 * Constructor
	 * 
	 * @param i
	 *            Column index in the tiles table
	 * @param j
	 *            Row index in the tiles table
	 */
	public Tile(int i, int j) {

		// Column and row
		this.i = i;
		this.j = j;

		// Entities in this tile
		entities = new LinkedList<Entity>();
	}

	/**
	 * Create a tile from coordinates
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return Tile
	 */
	public static Tile create(int x, int y) {
		return new Tile(
				(int) ((x - GameMap.offsetX) / GameMap.TILE_SIZE + GameMap.tileOffsetX),
				(int) ((y - GameMap.offsetY) / GameMap.TILE_SIZE + GameMap.tileOffsetY));
	}

	/**
	 * Get x position
	 * 
	 * @return x position
	 */
	public int getX() {
		// return (int) ((this.i - GameMap.tileOffsetX) * GameMap.TILE_SIZE +
		// GameMap.offsetX);
		return this.i * GameMap.TILE_SIZE;
	}

	/**
	 * Get y position
	 * 
	 * @return y position
	 */
	public int getY() {
		// return (int) ((this.j - GameMap.tileOffsetY) * GameMap.TILE_SIZE +
		// GameMap.offsetY);
		return this.j * GameMap.TILE_SIZE;
	}

	/**
	 * Get column index
	 * 
	 * @return Column index
	 */
	public int getColumn() {
		return this.i;
	}

	/**
	 * Get row index
	 * 
	 * @return Row index
	 */
	public int getRow() {
		return this.j;
	}

	/**
	 * Get x position of the center of the tile
	 * 
	 * @return x position of the center of the tile
	 */
	public int getCenterX() {
		return getX() + GameMap.TILE_SIZE / 2;
	}

	/**
	 * Get y position of the center of the tile
	 * 
	 * @return y position of the center of the tile
	 */
	public int getCenterY() {
		return getY() + GameMap.TILE_SIZE / 2;
	}

	/**
	 * Get entities in this tile
	 * 
	 * @return Entities in this tile
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Entity> getEntities() {
		return ((LinkedList<Entity>) this.entities.clone());
	}

	/**
	 * Get a food entity on the tile
	 * 
	 * @return Food entity
	 */
	public Entity getFood() {

		for (Entity entity : this.entities) {
			// if (entity == FoodEntity)
			// return entity;
		}
		return null;
	}

	/**
	 * Get a mouglotte friend entity on the tile
	 * 
	 * @return Mouglotte friend entity
	 */
	public Entity getFriend(Mouglotte current) {

		for (Entity entity : this.entities) {
			// if (entity == MouglotteEntity)
			// if (entity.isFriend(current)
			// return entity;
		}
		return null;
	}

	/**
	 * Get a mouglotte lover entity on the tile
	 * 
	 * @return Mouglotte lover entity
	 */
	public Entity getLover(Mouglotte current) {

		for (Entity entity : this.entities) {
			// if (entity == MouglotteEntity)
			// if (entity.isLover(current)
			// return entity;
		}
		return null;
	}

	/**
	 * Get a mouglotte enemy entity on the tile
	 * 
	 * @return Mouglotte enemy entity
	 */
	public Entity getEnemy(Mouglotte current) {

		for (Entity entity : this.entities) {
			// if (entity == MouglotteEntity)
			// if (entity.isEnemy(current)
			// return entity;
		}
		return null;
	}

	/**
	 * Get a working entity on the tile
	 * 
	 * @return Working entity
	 */
	public Entity getWork() {

		for (Entity entity : this.entities) {
			// if (entity == WorkingEntity)
			// return entity;
		}
		return null;
	}

	/**
	 * Add an entity to this tile
	 * 
	 * @param entity
	 *            Entity to add
	 */
	public void addEntity(Entity entity) {
		if (!this.entities.contains(entity))
			this.entities.add(entity);
	}

	/**
	 * Remove an entity from this tile
	 * 
	 * @param entity
	 *            Entity to remove
	 */
	public void removeEntity(Entity entity) {
		this.entities.remove(entity);
	}

	/**
	 * Are the coordinates in the tile ?
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return True if the coordinates are in the tile
	 */
	public boolean contains(int x, int y) {
		return x >= getX() && x <= getX() + GameMap.TILE_SIZE && y >= getY()
				&& y <= getY() + GameMap.TILE_SIZE;
	}

	/**
	 * Is the tile blocked ?
	 * 
	 * @return True if tile is blocked
	 */
	public boolean isBlocked() {
		return this.blocked;
	}
}
