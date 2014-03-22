package com.mouglotte.entities;

public class Relation {

	/** Max points */
	private final static int MAX_POINTS = 1000;
	/** Min points */
	private final static int MIN_POINTS = -1000;

	/** Entities */
	private EntityCouple couple;
	/** Points */
	private int points;

	public Relation(Entity origin, Entity target) {

		this.couple = new EntityCouple(origin, target);
	}

	/**
	 * Get entity couple
	 * 
	 * @return Couple
	 */
	public EntityCouple getEntityCouple() {
		return this.couple;
	}

	/**
	 * Get origin
	 * 
	 * @return Origin of the relation
	 */
	public Entity getOrigin() {
		return this.couple.getOrigin();
	}

	/**
	 * Get target
	 * 
	 * @return Target of the relation
	 */
	public Entity getTarget() {
		return this.couple.getTarget();
	}

	/**
	 * Get points
	 * 
	 * @return Points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Is this a bad relation ?
	 * 
	 * @return True if this is a bad relation
	 */
	public boolean isBad() {
		return this.points < 0;
	}

	/**
	 * Reward points
	 * 
	 * @param points
	 *            Points to reward
	 */
	public void reward(int points) {
		this.points += points;
		if (this.points > MAX_POINTS)
			this.points = MAX_POINTS;
	}

	/**
	 * Penalize points
	 * 
	 * @param points
	 *            Points to penalize
	 */
	public void penalize(int points) {
		this.points -= points;
		if (this.points < MIN_POINTS)
			this.points = MIN_POINTS;
	}
}
