package com.mouglotte.specy;

public enum DecisionType {

	// Needs
	NEED_HUNGER, NEED_REST, NEED_SOCIAL, NEED_FUN,
	// Desires
	DESIRE_HUNGER, DESIRE_REST, DESIRE_SOCIAL, DESIRE_FUN, DESIRE_LOVE, DESIRE_FIGHT, DESIRE_WORK;

	/**
	 * Get decision type from need type
	 * 
	 * @param type
	 *            Need type
	 * @return Decision type
	 */
	public static DecisionType getDecisionType(NeedType type) {

		switch (type) {
		case HUNGER:
			return DecisionType.NEED_HUNGER;
		case REST:
			return DecisionType.NEED_REST;
		case SOCIAL:
			return DecisionType.NEED_SOCIAL;
		case FUN:
			return DecisionType.NEED_FUN;
		default:
			return null;
		}
	}

	/**
	 * Get decision type from desire type
	 * 
	 * @param type
	 *            Desire type
	 * @return Decision type
	 */
	public static DecisionType getDecisionType(DesireType type) {

		switch (type) {
		case HUNGER:
			return DecisionType.DESIRE_HUNGER;
		case REST:
			return DecisionType.DESIRE_REST;
		case SOCIAL:
			return DecisionType.DESIRE_SOCIAL;
		case FUN:
			return DecisionType.DESIRE_FUN;
		case LOVE:
			return DecisionType.DESIRE_LOVE;
		case FIGHT:
			return DecisionType.DESIRE_FIGHT;
		case WORK:
			return DecisionType.DESIRE_WORK;
		default:
			return null;
		}
	}

	/**
	 * Is this a eating decision ?
	 * 
	 * @return True if the decision is to eat
	 */
	public boolean isEating() {
		return this == NEED_HUNGER || this == DESIRE_HUNGER;
	}

	/**
	 * Is this a resting decision ?
	 * 
	 * @return True if the decision is to rest
	 */
	public boolean isResting() {
		return this == NEED_REST || this == DESIRE_REST;
	}

	/**
	 * Is this a social decision ?
	 * 
	 * @return True if the decision is to be social
	 */
	public boolean isSocial() {
		return this == NEED_SOCIAL || this == DESIRE_SOCIAL;
	}

	/**
	 * Is this a fun decision ?
	 * 
	 * @return True if the decision is to have
	 */
	public boolean isFun() {
		return this == NEED_FUN || this == DESIRE_FUN;
	}

	/**
	 * Is this a love decision ?
	 * 
	 * @return True if the decision is to have love
	 */
	public boolean isLoving() {
		return this == DESIRE_LOVE;
	}

	/**
	 * Is this a fight decision ?
	 * 
	 * @return True if the decision is to have a fight
	 */
	public boolean isFighting() {
		return this == DESIRE_FIGHT;
	}

	/**
	 * Is this a work decision ?
	 * 
	 * @return True if the decision is to work
	 */
	public boolean isWorking() {
		return this == DESIRE_WORK;
	}
}
