package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Map.Entry;

public class Needs {

	/** Mouglotte */
	private Mouglotte mouglotte;

	/** Needs */
	private Hashtable<NeedType, Need> needs;
	/** Current need */
	private Need current;
	/** Mouglotte is searching for current need */
	private boolean searching;
	/** Current need is fulfilling */
	private boolean fulfilling;

	/**
	 * Constructor
	 * 
	 * @param mouglotte
	 *            Mouglotte
	 */
	public Needs(Mouglotte mouglotte) {

		// Mouglotte
		this.mouglotte = mouglotte;
		// Intialize needs
		this.needs = new Hashtable<NeedType, Need>();
	}

	/**
	 * Get mouglotte
	 * 
	 * @return Mouglotte
	 */
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}

	/**
	 * Get need from type
	 * 
	 * @param type
	 *            Type
	 * @return Need
	 */
	public Need get(NeedType type) {
		return this.needs.get(type);
	}

	/**
	 * Get current need
	 * 
	 * @return Current need
	 */
	public Need getCurrent() {
		return this.current;
	}

	/**
	 * Calculate current need
	 */
	private void calculateCurrent() {

		int value = 0;
		int maxValue = 0;

		// Read through all needs
		for (final Entry<NeedType, Need> need : this.needs.entrySet()) {

			// Get need with the maximum value
			value = need.getValue().getValue();
			if (value > maxValue) {

				maxValue = value;
				// This need is the most urgent
				this.current = need.getValue();
			}
		}
	}

	/**
	 * Set mouglotte is searching for current need
	 * 
	 * @param searching
	 *            True if mouglotte is searching for current need
	 */
	public void setSearching(boolean searching) {
		this.searching = searching;
		if (searching)
			this.fulfilling = false;
	}

	/**
	 * Set current need is fulfilling
	 * 
	 * @param searching
	 *            True if current need si fulfilling
	 */
	public void setFulfilling(boolean fulfilling) {
		this.fulfilling = fulfilling;
		if (fulfilling)
			this.searching = false;
	}

	/**
	 * Is mouglotte searching for the current need ?
	 * 
	 * @return True if mouglotte is searching for the current need ?
	 */
	public boolean isSearching() {
		return this.searching;
	}

	/**
	 * Is mouglotte fulfilling for the current need ?
	 * 
	 * @return True if mouglotte is fulfilling for the current need ?
	 */
	public boolean isFulfilling() {
		return this.fulfilling;
	}

	/**
	 * Add need
	 * 
	 * @param need
	 *            Need to add
	 */
	public void put(Need need) {
		this.needs.put(need.getType(), need);
	}

	/**
	 * Fulfill current need
	 */
	public void fulfill() {

		// Fulfill current need if it has to be
		if (this.current != null && this.fulfilling) {

			this.current.fulfill();

			// If the need is completely fulfilled
			if (this.current.getValue() <= 0)
				this.setFulfilling(false);
		}
	}
	
	/**
	 * Increase needs
	 */
	public void increase() {

		// Read through all needs
		for (final Entry<NeedType, Need> need : this.needs.entrySet()) {
			// Increase need
			need.getValue().increase();
		}
	}

	/**
	 * Decide current need
	 */
	public void decide() {

		// Needs always increase, so current need stays the same
		// If it is fulfilling then it can be exceeded by another one

		// If the is no current need
		// Or if the current need is fulfilling
		if ((this.current == null) || this.fulfilling) {

			// Set current need
			calculateCurrent();
			// Initialization
			this.searching = false;
			this.fulfilling = false;
		}
	}
}
