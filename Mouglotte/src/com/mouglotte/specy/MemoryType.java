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
	
	/**
	 * Get memory type from decision type
	 * 
	 * @param decision
	 *            Decision type
	 * @return Memory type
	 */
	public static MemoryType getMemoryType(DecisionType decision) {

		switch (decision) {
		case NEED_HUNGER:
		case DESIRE_HUNGER:
			return MemoryType.FOOD;
		case NEED_SOCIAL:
		case DESIRE_SOCIAL:
			return MemoryType.FRIEND;
		case NEED_FUN:
		case DESIRE_FUN:
			return MemoryType.FRIEND;
		case DESIRE_LOVE:
			return MemoryType.LOVER;
		case DESIRE_FIGHT:
			return MemoryType.ENEMY;
		case DESIRE_WORK:
			return MemoryType.WORK;
		default:
			return null;
		}
	}
}
