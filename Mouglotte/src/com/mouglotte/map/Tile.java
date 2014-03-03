package com.mouglotte.map;

public class Tile {

	// Champ de vision
	// Actuellement en pixel, à mettre en tiles
	// private final int VISUAL_FIELD = 32;

	/** Location */
	private int x = 0;
	private int y = 0;

	/** Column and row in the tiles table */
	private int i = 0;
	private int j = 0;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param i
	 *            Column index in the tiles table
	 * @param j
	 *            Row index in the tiles table
	 */
	public Tile(int x, int y, int i, int j) {
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
	}

	/**
	 * Get x position
	 * 
	 * @return x position
	 */
	public int getX() {
		return (int) (this.x + GameMap.offsetX);
	}

	/**
	 * Get y position
	 * 
	 * @return y position
	 */
	public int getY() {
		return (int) (this.y + GameMap.offsetY);
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
		return this.x + GameMap.TILE_SIZE / 2;
	}

	/**
	 * Get y position of the center of the tile
	 * 
	 * @return y position of the center of the tile
	 */
	public int getCenterY() {
		return this.y + GameMap.TILE_SIZE / 2;
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
		return x >= this.x && x <= this.x + GameMap.TILE_SIZE && y >= this.y
				&& y <= this.y + GameMap.TILE_SIZE;
	}
}
