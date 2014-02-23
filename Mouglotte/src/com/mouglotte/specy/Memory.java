package com.mouglotte.specy;

public class Memory {

	// Type
	private MemoryType type;
	// Coordonn�es
	private int x;
	private int y;
	// Mouglotte
	private Mouglotte mouglotte;
	
	// Constructeur � partir d'un lieu
	public Memory(MemoryType type, int x, int y) {
		
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	// Constructeur � partir d'un lieu et d'une mouglotte
	public Memory(MemoryType type, int x, int y, Mouglotte mouglotte) {

		this.type = type;
		this.x = x;
		this.y = y;
		this.mouglotte = mouglotte;
	}

	// R�cup�ration du type
	public MemoryType getType() {
		return this.type;
	}

	// R�cup�ration des coordonn�es X
	public int getX() {
		return this.x;
	}

	// R�cup�ration des coordonn�es Y
	public int getY() {
		return this.y;
	}

	// R�cup�ration de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}	
}
