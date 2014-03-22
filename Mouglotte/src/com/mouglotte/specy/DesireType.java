package com.mouglotte.specy;

public enum DesireType {

	// Like needs
	HUNGER, REST, SOCIAL, FUN,
	// Pure desires
	LOVE, FIGHT, WORK;

	/**
	 * Get desire type from conversation
	 * 
	 * @param conversation
	 *            Conversation
	 * @return Desire type
	 */
	public static DesireType getDesireType(Conversation conversation) {
		switch (conversation) {
		case FOOD:
			return HUNGER;
		case REST:
			return REST;
		case SOCIAL:
			return SOCIAL;
		case FUN:
			return FUN;
		case LOVE:
			return LOVE;
		case FIGHT:
			return FIGHT;
		case WORK:
			return WORK;
		default:
			return null;
		}
	}

//	// Conversion en String
//	public String toString() {
//
//		switch (DesireType.this) {
//		case HUNGER:
//			return "HUNGER";
//		case REST:
//			return "REST";
//		case SOCIAL:
//			return "SOCIAL";
//		case FUN:
//			return "FUN";
//		case LOVE:
//			return "LOVE";
//		case FIGHT:
//			return "FIGHT";
//		case WORK:
//			return "WORK";
//		default:
//			return "???";
//		}
//	}

}
