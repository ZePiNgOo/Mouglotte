package com.mouglotte.map;

public class Tile {

	// Champ de vision
	// Actuellement en pixel, � mettre en tiles
	private final int VISUAL_FIELD = 32;

	// Coordonn�es en pixel
	private int x, y;
	// Coordonn�es en zone
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

	// R�cup�ration de la position
	public int getX() {
		return this.x;
	}

	// R�cup�ration de la position
	public int getY() {
		return this.y;
	}
	
	// R�cup�ration du centre de la zone
	public static int getCenterX(int x) {
		return 0;
	}

	// La zone contient les coordonn�es
	public boolean contains(int x, int y) {
		return x >= this.x && x <= this.x + GameMap.TILE_SIZE && y >= this.y
				&& y <= this.y + GameMap.TILE_SIZE;
	}

}
