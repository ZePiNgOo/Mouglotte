package com.mouglotte.specy;

public enum Conversation {

	// A faire évoluer en classe pour gérer les conversation au sujet d'une
	// entité pour vérifier les points communs plus finement

	// Desires
	FOOD, REST, SOCIAL, FUN, LOVE, FIGHT, WORK,
	// Traits
	STRENGTH,
	// Skills
	FIGHTING, FISHING, WOODCUTING;

	/** Minimum value to appreciate a conversation */
	public final static int VALUE_TO_APPRECIATE = 100;

	/**
	 * Is the conversation about a desire
	 * 
	 * @return True if the conversation is about a desire
	 */
	public boolean isDesire() {
		return this == FOOD || this == REST || this == SOCIAL || this == FUN
				|| this == LOVE || this == FIGHT || this == WORK;
	}

	/**
	 * Is the conversation about a trait
	 * 
	 * @return True if the conversation is about a trait
	 */
	public boolean isTrait() {
		return this == STRENGTH;
	}

	/**
	 * Is the conversation about a skill
	 * 
	 * @return True if the conversation is about a skill
	 */
	public boolean isSkill() {
		return this == FIGHTING || this == FISHING || this == WOODCUTING;
	}
}
