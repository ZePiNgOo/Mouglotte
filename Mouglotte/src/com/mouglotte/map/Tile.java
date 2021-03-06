package com.mouglotte.map;

import java.util.LinkedList;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.mouglotte.entities.Entity;
import com.mouglotte.entities.FoodEntity;
import com.mouglotte.entities.MouglotteEntity;
import com.mouglotte.entities.WorkingEntity;
import com.mouglotte.specy.Desire;
import com.mouglotte.specy.DesireType;
import com.mouglotte.specy.Mouglotte;

public class Tile {

	/** Column and row in the tiles table */
	private int i = 0;
	private int j = 0;

	// /** Blocked */
	// private boolean blocked = false;

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
	// @SuppressWarnings("unchecked")
	public LinkedList<Entity> getEntities() {
		// return ((LinkedList<Entity>) this.entities.clone());
		return this.entities;
	}

	/**
	 * Get a food entity on the tile
	 * 
	 * @return Food entity
	 */
	public FoodEntity getFood() {

		for (Entity entity : this.entities) {
			if (entity instanceof FoodEntity)
				return (FoodEntity) entity;
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
			if (entity instanceof MouglotteEntity)
				if (((MouglotteEntity) entity).getMouglotte().isFriend(current))
					return entity;
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
			if (entity instanceof MouglotteEntity)
				if (((MouglotteEntity) entity).getMouglotte().isLover(current))
					return entity;
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
			if (entity instanceof MouglotteEntity)
				if (((MouglotteEntity) entity).getMouglotte().isEnemy(current))
					return entity;
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
			if (entity instanceof WorkingEntity)
				return entity;
		}
		return null;
	}

	/**
	 * Update tile
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 * @throws SlickException
	 */
	public void update(GameContainer container, long delta)
			throws SlickException {

		// Attention on update aussi les mouglottes
		// Update entities
		for (Entity entity : this.entities) {
			entity.update(container, delta);
		}
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
	 * Is the entity in the tile ?
	 * 
	 * @param entity
	 *            Entity
	 * @return True if the entity is in the tile
	 */
	public boolean contains(Entity entity) {
		// return entity.getX() >= getX()
		// && entity.getX() <= getX() + GameMap.TILE_SIZE
		// && entity.getY() >= getY()
		// && entity.getY() <= getY() + GameMap.TILE_SIZE;
		return this.entities.contains(entity);
	}

	/**
	 * Is the tile blocked ?
	 * 
	 * @return True if tile is blocked
	 */
	public boolean isBlocked() {

		return this.entities.size() > 0;
	}

	/**
	 * Render tile
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 * @throws SlickException
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		// Attention on update aussi les mouglottes
		// Update entities
		for (Entity entity : this.entities) {
			entity.render(container, g);
		}
	}
}
