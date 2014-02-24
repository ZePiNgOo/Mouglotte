package com.mouglotte.specy;

public enum MemoryType {

	// Souvenirs
	FOOD, FRIEND, LOVER, ENEMY, WORK;

	// Conversion en String
	public String toString() {

		switch (MemoryType.this) {
		case FOOD:
			return "FOOD";
		case FRIEND:
			return "FRIEND";
		case LOVER:
			return "LOVER";
		case ENEMY:
			return "ENEMY";
		case WORK:
			return "WORK";
		default:
			return "???";
		}
	}
}
