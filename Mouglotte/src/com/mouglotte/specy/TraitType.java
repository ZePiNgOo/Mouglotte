package com.mouglotte.specy;

public enum TraitType {

	// Traits
	STRENGTH, INTELLIGENCE;

//	// Conversion en String
//	public String toString() {
//
//		switch (TraitType.this) {
//		case STRENGTH:
//			return "STRENGTH";
//		case INTELLIGENCE:
//			return "INTELLIGENCE";
//		default:
//			return "???";
//		}
//	}
	
	/**
	 * Get trait type from conversation
	 * 
	 * @param conversation
	 *            Conversation
	 * @return Trait type
	 */
	public static TraitType getTraitType(Conversation conversation) {
		switch (conversation) {
		case STRENGTH:
			return STRENGTH;
		default:
			return null;
		}
	}
}
