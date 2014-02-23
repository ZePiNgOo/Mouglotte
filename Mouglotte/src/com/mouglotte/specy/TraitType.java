package com.mouglotte.specy;

public enum TraitType {

	// Traits de caract�res
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
