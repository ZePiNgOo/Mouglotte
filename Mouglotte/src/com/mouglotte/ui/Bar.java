package com.mouglotte.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 * Progress bar
 * 
 * @author A178414
 * 
 */
public class Bar extends AbstractComponent {

	/** Colors */
	public static final Color COLOR_HIGHLIGHT = Color.green;
	public static final Color COLOR_LINE = Color.red;
	public static final Color COLOR_BACKGROUND = Color.black;
	public static final Color COLOR_FOREGROUND = Color.green;
	
	/** Position */
	int x = 0, y = 0;
	/** Width and height */
	int width = 100, height = 20;
	/** Minimum and maximum values */
	int min = 0, max = 1000;
	/** Colors */
	Color line = COLOR_LINE;
	Color background = COLOR_BACKGROUND, foreground = COLOR_FOREGROUND;
	/** Text */
	String text = "";
	/** Progress size */
	int progress = 0;

	/**
	 * Constructor
	 * 
	 * @param container
	 *            Game container
	 */
	public Bar(GUIContext container) {
		super(container);
	}

	@Override
	/**
	 * Set location of the bar
	 * @param x x position
	 * @param y y position
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Set the color of the lines
	 * 
	 * @param color
	 *            Color of the lines
	 */
	public void setLineColor(Color color) {
		this.line = color;
	}

	/**
	 * Set the value of the bar
	 * 
	 * @param value
	 *            Value of the bar
	 */
	public void setValue(int value) {

		// Set text
		setText(Integer.toString(value));

		// Define progress size
		double interval = this.max - this.min;
		double progress = (value - this.min) / interval;
		progress = progress * (this.width - 2); 
		this.progress = (int) progress;

		// Update foreground color
		int percentage = (((value - this.min) * 100) / (this.max - this.min));
		if (percentage < 10)
			this.foreground = new Color(0, 255, 0);
		else if (percentage < 20)
			this.foreground = new Color(51, 255, 0);
		else if (percentage < 30)
			this.foreground = new Color(102, 255, 0);
		else if (percentage < 40)
			this.foreground = new Color(153, 255, 0);
		else if (percentage < 50)
			this.foreground = new Color(204, 255, 0);
		else if (percentage < 60)
			this.foreground = new Color(255, 255, 0);
		else if (percentage < 70)
			this.foreground = new Color(255, 204, 0);
		else if (percentage < 80)
			this.foreground = new Color(255, 153, 0);
		else if (percentage < 90)
			this.foreground = new Color(255, 102, 0);
		else if (percentage < 100)
			this.foreground = new Color(255, 51, 0);
	}

	/**
	 * Set text value
	 * 
	 * @param text
	 *            Text value
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get width of the bar
	 * 
	 * @return Width of the bar
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * Get height
	 * 
	 * @return Height of the bar
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Get x position of the bar
	 * 
	 * @return x position of the bar
	 */
	@Override
	public int getX() {
		return this.x;
	}

	/**
	 * Get y position of the bar
	 * 
	 * @return y position of the bar
	 */
	@Override
	public int getY() {
		return this.y;
	}
	
	/**
	 * Highlight bar
	 * 
	 * @param highlight True to highlight
	 */
	public void highlight(boolean highlight) {
		if (highlight)
			setLineColor(COLOR_HIGHLIGHT);
		else
			setLineColor(COLOR_LINE);
	}

	/**
	 * Render the bar
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {

		// Rectangle
		g.setColor(this.line);
		g.drawRect(this.x, this.y, this.width, this.height);
		// Progress
		g.setColor(this.foreground);
		g.fillRect(this.x + 1, this.y + 1, this.progress, this.height - 2);
	}
}
