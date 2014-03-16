package com.mouglotte.specy;

import java.util.Hashtable;

public class Skills {

	/** Mouglotte */
	private Mouglotte mouglotte;
	
	/** Skills list */
	private Hashtable<SkillType, Skill> skills;
	
	/**
	 * Constructor
	 * @param mouglotte Mouglotte
	 */
	public Skills(Mouglotte mouglotte) {
		
		this.mouglotte = mouglotte;
		this.skills = new Hashtable<SkillType, Skill>();
	}
	
	/**
	 * Get mouglotte
	 * @return Mouglotte
	 */
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}
	
	/**
	 * Get skill from type
	 * @param type Type
	 * @return Skill
	 */
	public Skill get(SkillType type) {
		return this.skills.get(type);
	}
	
	/**
	 * Add skill
	 * @param skill Skill
	 */
	public void put(Skill skill) {
		this.skills.put(skill.getType(), skill);
	}	
}
