package com.mouglotte.specy;

public class Need {

	/** Needs */
	private Needs needs;

	/** Type */
	private NeedType type;
	/** Need value */
	private int value;
	/** Related desire */
	private Desire relatedDesire;

	/** Need increase depending on age */
	private int babyHourGain;
	private int childHourGain;
	private int adultHourGain;
	private int oldHourGain;
	/** Need decrease during fulfilling */
	private int fulfillLoss;

	/**
	 * Constructor
	 * 
	 * @param needs
	 *            Needs
	 * @param type
	 *            Type
	 * @param babyHourGain
	 *            Gain by hour when baby
	 * @param childHourGain
	 *            Gain by hour when child
	 * @param adultHourGain
	 *            Gain by hour when adult
	 * @param oldHourGain
	 *            Gain by hour when old
	 * @param fulfillLoss
	 *            Loss by minute
	 */
	public Need(Needs needs, NeedType type, int babyHourGain,
			int childHourGain, int adultHourGain, int oldHourGain,
			int fulfillLoss) {

		this.needs = needs;
		this.type = type;
		this.babyHourGain = babyHourGain;
		this.childHourGain = childHourGain;
		this.adultHourGain = adultHourGain;
		this.oldHourGain = oldHourGain;
		this.fulfillLoss = fulfillLoss;
	}

	/**
	 * Get type
	 * 
	 * @return Type
	 */
	public NeedType getType() {
		return this.type;
	}

	/**
	 * Get need value
	 * 
	 * @return Value
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Set related desire
	 * 
	 * @param desire
	 *            Related desire
	 */
	public void setRelatedDesire(Desire desire) {
		this.relatedDesire = desire;
	}

	/**
	 * Increase need
	 */
	public void increase() {

		// Baby
		if (this.needs.getMouglotte().getAge() < 1)
			this.value += this.babyHourGain;
		// Child
		else if (this.needs.getMouglotte().getAge() < 2)
			this.value += this.childHourGain;
		// Adult
		else if (this.needs.getMouglotte().getAge() < 26)
			this.value += this.adultHourGain;
		// Old
		else
			this.value += this.oldHourGain;
	}

	/**
	 * Fulfill need
	 */
	public void fulfill() {

		// Decrease need
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;

		// Fulfill related desire
		if (this.relatedDesire != null)
			this.relatedDesire.fulfillFromNeed();
	}

	/**
	 * Fulfill need from the related desire
	 */
	public void fulfillFromDesire() {

		// Decrease need
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;
	}
}
