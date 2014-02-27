package com.mouglotte.map;

public class Tile {

	// Champ de vision
	// Actuellement en pixel, à mettre en tiles
	private final int VISUAL_FIELD = 32;

	// Coordonnées en pixel
	private int x, y;
	// Coordonnées en zone
	private int i, j;
	// Type de zone
	private int type;

	// Constructeur
	public Tile(int x, int y, int i, int j, int type) {
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
		this.type = type;
	}

	// Récupération de la position
	public int getX() {
		return this.x;
	}

	// Récupération de la position
	public int getY() {
		return this.y;
	}
	
	// Récupération du centre de la zone
	public static int getCenterX(int x) {
		return 0;
	}

	// La zone contient les coordonnées
	public boolean contains(int x, int y) {
		return x >= this.x && x <= this.x + GameMap.TILE_SIZE && y >= this.y
				&& y <= this.y + GameMap.TILE_SIZE;
	}

}
