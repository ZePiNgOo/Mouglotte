package com.mouglotte.specy;

public class Skill {

	/** Skills */
	private Skills skills;

	/** Type */
	private SkillType type;
	/** Value */
	private int value;

	/**
	 * Constructor
	 * 
	 * @param skills Skills
	 * @param type Type
	 */
	public Skill(Skills skills, SkillType type) {

		this.skills      = skills;
		this.type        = type;
		}

	/**
	 * Set skill value
	 * @param value Value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Get skill type
	 * @return Type
	 */
	public SkillType getType() {
		return this.type;
	}

	/**
	 * Get skill value
	 * @return Value
	 */
	public int getValue() {
		return this.value;
	}
}
