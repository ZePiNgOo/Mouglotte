package com.mouglotte.specy;

public enum ActionType {

	IDLE, SEARCHING, FULFILLING, EATING, RESTING, TALKING, PLAYING, FUCKING, FIGHTING, WORKING;

	/**
	 * Is this a searching action ?
	 * 
	 * @return True if the action is searching for a need or a desire
	 */
	public boolean isSearching() {
		return this == SEARCHING;
	}

	/**
	 * Is this a fulfilling action ?
	 * 
	 * @return True if the action is fulfilling a need or a desire
	 */
	public boolean isFulfilling() {
		return this == FULFILLING || this == EATING || this == RESTING
				|| this == TALKING || this == PLAYING || this == FUCKING
				|| this == FIGHTING || this == WORKING;
	}

	/**
	 * Is this an idle action ?
	 * 
	 * @return True if the action is idle
	 */
	public boolean isIdle() {
		return this == IDLE;
	}

	/**
	 * Can this action be interrupted ?
	 * 
	 * @return True if the action has to go to its end (cannot be interrupted)
	 */
	public boolean hasToFinish() {
		return this == WORKING;
	}
}
