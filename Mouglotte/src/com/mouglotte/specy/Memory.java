package com.mouglotte.specy;

import com.mouglotte.entities.Entity;
import com.mouglotte.map.Tile;

public class Memory {

	/** Max points */
	private final static int MAX_POINTS = 1000;
	/** Points to reward */
	public final static int REWARD_POINTS = 100;
	public final static int REWARD_LOW_POINTS = 50;
	/** Points to penalize */
	public final static int PENALIZE_POINTS = 100;
	public final static int PENALIZE_LOW_POINTS = 50;

	/** Type */
	private MemoryType type;
	/** Position */
	private Tile tile;
	/** Acted with this entity */
	private Entity actedWith;
	/** Points */
	private int points;

	/**
	 * Constructor from a tile
	 * 
	 * @param type
	 *            Type
	 * @param tile
	 *            Tile
	 */
	public Memory(MemoryType type, Tile tile) {

		// Je pense qu'il n'y aura jamais de souvenir dans entity
		this.type = type;
		this.tile = tile;
	}

	/**
	 * Constructor from an entity located on a tile
	 * 
	 * @param type
	 *            Type
	 * @param entity
	 *            Acted with this entity
	 */
	public Memory(MemoryType type, Entity entity) {

		// The entity acted with was at this tile
		this.type = type;
		this.actedWith = entity;
		this.tile = entity.getTile();
	}

	/**
	 * Set tile location
	 * 
	 * @param tile
	 *            Tile
	 */
	public void setTile(Tile tile) {

		// If the entity has moved the tile has to be updated
		this.tile = tile;
	}

	/**
	 * Set points
	 * 
	 * @param points
	 *            Points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Get type
	 * 
	 * @return Type
	 */
	public MemoryType getType() {
		return this.type;
	}

	/**
	 * Get tile
	 * 
	 * @return Tile
	 */
	public Tile getTile() {
		return this.tile;
	}

	/**
	 * Get the entity acted with
	 * 
	 * @return Entity acted with
	 */
	public Entity getEntity() {
		return this.actedWith;
	}

	/**
	 * Get points
	 * 
	 * @return Points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Is there points for the memory
	 * 
	 * @return True if the memory has points
	 */
	public boolean hasPoints() {
		return this.points > 0;
	}

	/**
	 * Reward points
	 * 
	 * @param points
	 *            Points to reward
	 */
	public void reward(int points) {
		this.points += points;
		if (this.points > MAX_POINTS)
			this.points = MAX_POINTS;
	}

	/**
	 * Penalize points
	 * 
	 * @param points
	 *            Points to penalize
	 */
	public void penalize(int points) {
		this.points -= points;
		if (this.points < 0)
			this.points = 0;
	}
}
