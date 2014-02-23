package com.mouglotte.specy;

public class Memory {

	// Type
	private MemoryType type;
	// Coordonnées
	private int x;
	private int y;
	// Mouglotte
	private Mouglotte mouglotte;
	
	// Constructeur à partir d'un lieu
	public Memory(MemoryType type, int x, int y) {
		
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	// Constructeur à partir d'un lieu et d'une mouglotte
	public Memory(MemoryType type, int x, int y, Mouglotte mouglotte) {

		this.type = type;
		this.x = x;
		this.y = y;
		this.mouglotte = mouglotte;
	}

	// Récupération du type
	public MemoryType getType() {
		return this.type;
	}

	// Récupération des coordonnées X
	public int getX() {
		return this.x;
	}

	// Récupération des coordonnées Y
	public int getY() {
		return this.y;
	}

	// Récupération de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}	
}
