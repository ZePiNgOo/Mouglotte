package com.mouglotte.time;

public class TimeKeeper extends TimeDependent {

	/** Current time */
	private String time;
	/** Date */
	private int year;
	private int month;
	private int day;
	/** Time */
	private int hour;
	private int minute;

	/**
	 * Constructor
	 */
	public TimeKeeper() {

		// 1/1/1 0:0
		setTime(1, 1, 1, 0, 0);
	}

	/**
	 * Set current time
	 */
	private void setTime() {

		this.time = this.day > 9 ? Integer.toString(this.day) : "0"
				+ Integer.toString(this.day);
		this.time += "/"
				+ (this.month > 9 ? Integer.toString(this.month) : "0"
						+ Integer.toString(this.month));
		this.time += "/"
				+ (this.year > 9 ? Integer.toString(this.year) : "0"
						+ Integer.toString(this.year));
		this.time += " "
				+ (this.hour > 9 ? Integer.toString(this.hour) : "0"
						+ Integer.toString(this.hour));
		this.time += ":"
				+ (this.minute > 9 ? Integer.toString(this.minute) : "0"
						+ Integer.toString(this.minute));
	}

	/**
	 * Set current time
	 * 
	 * @param day
	 *            Day
	 * @param month
	 *            Month
	 * @param year
	 *            Year
	 * @param hour
	 *            Hour
	 * @param minute
	 *            Minute
	 */
	private void setTime(int day, int month, int year, int hour, int minute) {

		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		setTime();
	}

	/**
	 * Get time
	 * 
	 * @return Time
	 */
	public String getTime() {
		return this.time;
	}

	@Override
	public void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMinute() {

		this.minute++;
		if (this.minute >= 60)
			this.minute = 0;
		setTime();
	}

	@Override
	public void eventHour() {

		this.hour++;
		if (this.hour >= 20)
			this.hour = 0;
		setTime();
	}

	@Override
	public void eventDay() {

		this.day++;
		if (this.day > 12)
			this.day = 1;
		setTime();
	}

	@Override
	public void eventMonth() {

		this.month++;
		if (this.month > 6)
			this.month = 1;
		setTime();
	}

	@Override
	public void eventSeason() {
		// Nothing
	}

	@Override
	public void eventYear() {
		this.year++;
		setTime();
	}
}
