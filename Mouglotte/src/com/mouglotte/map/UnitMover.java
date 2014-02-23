package com.mouglotte.map;

/**
 * A mover to represent one of a ID based unit in our example
 * game map
 *
 * @author Kevin Glass
 */
public class UnitMover implements Mover {
	
	private int type;
	
	// Constructeur
	public UnitMover(int type) {
		this.type = type;
	}
	
	// Récupération du type d'unité
	public int getType() {
		return type;
	}
}
