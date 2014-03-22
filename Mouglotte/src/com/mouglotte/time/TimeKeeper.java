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

		this.time = (String) (this.day > 9 ? this.day : "0" + this.day);
		this.time += "/" + (this.month > 9 ? this.month : "0" + this.month);
		this.time += "/" + (this.year > 9 ? this.year : "0" + this.year);
		this.time += " " + (this.hour > 9 ? this.hour : "0" + this.hour);
		this.time += ":" + (this.minute > 9 ? this.minute : "0" + this.minute);
		// this.time = this.day + "/" + this.month + "/" + this.year + " "
		// + this.hour + ":" + this.minute;
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
