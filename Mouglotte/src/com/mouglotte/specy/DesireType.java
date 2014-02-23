package com.mouglotte.specy;

public enum DesireType {

	// Besoins
	HUNGER, REST, SOCIAL, FUN,
	// Envies
	LOVE, FIGHT, WORK;

	//
	// //Constructeur
	// GeneType(String name){
	// this.name = name;
	// }
	//
	// Conversion en String
	public String toString() {

		switch (DesireType.this) {
		case HUNGER:
			return "HUNGER";
		case REST:
			return "REST";
		case SOCIAL:
			return "SOCIAL";
		case FUN:
			return "FUN";
		case LOVE:
			return "LOVE";
		case FIGHT:
			return "FIGHT";
		case WORK:
			return "WORK";
		default:
			return "???";
		}
	}

}
