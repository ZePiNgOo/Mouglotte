package com.mouglotte.specy;

public enum SkillType {

	FIGHTING, FISHING, WOODCUTING;

	/**
	 * Get skill type from conversation
	 * 
	 * @param conversation
	 *            Conversation
	 * @return Skill type
	 */
	public static SkillType getSkillType(Conversation conversation) {
		switch (conversation) {
		case FIGHTING:
			return FIGHTING;
		case FISHING:
			return FISHING;
		case WOODCUTING:
			return WOODCUTING;
		default:
			return null;
		}
	}
}
