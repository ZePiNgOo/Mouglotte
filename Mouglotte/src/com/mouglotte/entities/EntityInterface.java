package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;

public interface EntityInterface {

	/**
	 * Get age
	 * 
	 * @return Age
	 */
	public int getAge();
	
	/**
	 * Update entity
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	public void update(GameContainer container, int delta);

	/**
	 * Event called every real second
	 */
	public void eventRealSecond();

	/**
	 * Event called every mouglotte minute
	 */
	public void eventMinute();

	/**
	 * Event called every mouglotte hour
	 */
	public void eventHour();

	/**
	 * Event called every mouglotte day
	 */
	public void eventDay();

	/**
	 * Event called every mouglotte month
	 */
	public void eventMonth();

	/**
	 * Event called every mouglotte season
	 */
	public void eventSeason();

	/**
	 * Event called every mouglotte year
	 */
	public void eventYear();
}
