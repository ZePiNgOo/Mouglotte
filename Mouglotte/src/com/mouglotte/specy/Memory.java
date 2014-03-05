package com.mouglotte.specy;

import com.mouglotte.map.Tile;

public class Memory {

	/** Type */
	private MemoryType type;
	/** Position */
	private Tile tile;
	/** Mouglotte */
	private Mouglotte mouglotte;
	
	/**
	 * Constructor from a tile
	 * @param type Type
	 * @param tile Tile
	 */
	public Memory(MemoryType type, Tile tile) {
		
		this.type = type;
		this.tile = tile;
	}
	
	/**
	 * Constructor from a mouglotte located on a tile
	 * @param type Type
	 * @param tile Tile
	 * @param mouglotte Mouglotte
	 */
	public Memory(MemoryType type, Tile tile, Mouglotte mouglotte) {

		// The mouglotte was at this tile
		this.type = type;
		this.tile = tile;
		this.mouglotte = mouglotte;
	}

	/**
	 * Get type
	 * @return Type
	 */
	public MemoryType getType() {
		return this.type;
	}

	/**
	 * Get tile
	 * @return Tile
	 */
	public Tile getTile() {
		return this.tile;
	}

	/**
	 * Get mouglotte
	 * @return Mouglotte
	 */
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}	
}
