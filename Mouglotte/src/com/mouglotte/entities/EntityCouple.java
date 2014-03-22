package com.mouglotte.entities;

public class EntityCouple {

	/** Couple of entities */
	private Entity origin;
	private Entity target;

	/**
	 * Constructor
	 * 
	 * @param origin
	 *            First entity
	 * @param target
	 *            Second entity
	 */
	public EntityCouple(Entity origin, Entity target) {
		this.origin = origin;
		this.target = target;
	}

	/**
	 * Get origin of the couple
	 * 
	 * @return Origin entity
	 */
	public Entity getOrigin() {
		return this.origin;
	}

	/**
	 * Get target of the couple
	 * 
	 * @return Target entity
	 */
	public Entity getTarget() {
		return this.target;
	}
}
