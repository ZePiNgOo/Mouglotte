package com.mouglotte.specy;

public enum NeedType {

	// Besoins
	HUNGER, REST, SOCIAL, FUN;

	// Conversion en String
	public String toString() {

		switch (NeedType.this) {
		case HUNGER:
			return "HUNGER";
		case REST:
			return "REST";
		case SOCIAL:
			return "SOCIAL";
		case FUN:
			return "FUN";
		default:
			return "???";
		}
	}
}
