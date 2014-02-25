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
	
	
}
