package com.mouglotte.utilities;

import org.newdawn.slick.GameContainer;

public abstract class TimeDependent {

	/** Time passed */
	int passedTime = 0;
	int secondesR = 0;
	int minutes = 0;
	int hours = 0;
	int days = 0;
	int months = 0;
	int seasons = 0;
	int years = 0;
	
	/**
	 * Update
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	public void update(GameContainer container, long delta) {

		// Passed time
		this.passedTime += delta;

		// Every real second
		if (this.passedTime >= 1000) {

			// One real second has passed
			this.secondesR++;
			this.passedTime -= 1000;
			// Event every real second
			eventRealSecond();
		}

		// Every 3 real seconds
		// = mouglotte minute
		if (this.secondesR >= 3) {

			// One mouglotte minute has passed
			this.minutes++;
			this.secondesR = 0;
			// Event every mouglotte minute
			eventMinute();
		}

		// Every 3 real minutes
		// = mouglotte hour
		if (this.minutes >= 60) {

			// One mouglotte hour has passed
			this.hours++;
			this.minutes = 0;
			// Event every mouglotte hour
			eventHour();
		}

		// Every real hour
		// = mouglotte day
		if (this.hours >= 20) {

			// One mouglotte day has passed
			this.days++;
			this.hours = 0;
			// Event every mouglotte day
			eventDay();
		}

		// Every 12 real hours
		// = mouglotte month
		if (this.days >= 12) {

			// One mouglotte month has passed
			this.months++;
			this.days = 0;
			// Event every mouglotte month
			eventMonth();
		}

		// Every 24 real hours
		// = mouglotte season
		if (this.months >= 2) {

			// One mouglotte season has passed
			this.seasons++;
			this.months = 0;
			// Event every mouglotte season
			eventSeason();
		}

		// Every 3 real days
		// = mouglotte year
		if (this.seasons >= 3) {

			// One mouglotte year has passed
			this.years++;
			this.seasons = 0;
			// Event every mouglotte year
			eventYear();
		}

		// A AJOUTER DANS ENTITY UNIQUEMENT
		// Handle inputs
//		handleInputs(container, delta);
	}
	
	/**
	 * Event called every real second
	 */
	protected abstract void eventRealSecond();

	/**
	 * Event called every mouglotte minute
	 */
	protected abstract void eventMinute();

	/**
	 * Event called every mouglotte hour
	 */
	protected abstract void eventHour();

	/**
	 * Event called every mouglotte day
	 */
	protected abstract void eventDay();

	/**
	 * Event called every mouglotte month
	 */
	protected abstract void eventMonth();

	/**
	 * Event called every mouglotte season
	 */
	protected abstract void eventSeason();

	/**
	 * Event called every mouglotte year
	 */
	protected abstract void eventYear();
}
