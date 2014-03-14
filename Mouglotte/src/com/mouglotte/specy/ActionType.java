package com.mouglotte.specy;

public enum ActionType {

	IDLE, SEARCHING, EATING, RESTING, TALKING, PLAYING, FUCKING, FIGHTING, WORKING;

	/**
	 * Is this a fulfilling action ?
	 * 
	 * @return True if the action is fulfilling a need or a desire
	 */
	public boolean isFulfilling() {
		return this == EATING || this == RESTING || this == TALKING
				|| this == PLAYING || this == FUCKING || this == FIGHTING
				|| this == WORKING;
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
