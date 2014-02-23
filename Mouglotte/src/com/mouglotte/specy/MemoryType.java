package com.mouglotte.specy;

public enum MemoryType {

	// Souvenirs
	FOOD, FRIEND, FIGHT;

	// Conversion en String
	public String toString() {

		switch (MemoryType.this) {
		case FOOD:
			return "FOOD";
		case FRIEND:
			return "FRIEND";
		case FIGHT:
			return "FIGHT";
		default:
			return "???";
		}
	}
}
