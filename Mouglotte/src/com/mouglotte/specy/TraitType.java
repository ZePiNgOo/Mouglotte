package com.mouglotte.specy;

public enum TraitType {

	// Traits de caractères
	STRENGTH, INTELLIGENCE;

	// Conversion en String
	public String toString() {

		switch (TraitType.this) {
		case STRENGTH:
			return "STRENGTH";
		case INTELLIGENCE:
			return "INTELLIGENCE";
		default:
			return "???";
		}
	}
}
