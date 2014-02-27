package com.mouglotte.ui;

import java.awt.Color;

import javax.swing.JProgressBar;

/**
 * ProgressBar class
 * 
 * @author A178414
 * 
 */
public class ProgressBar extends JProgressBar {

	/**
	 * Constructor
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value
	 */
	public ProgressBar(int min, int max) {

		setBackground(Color.BLACK);
		setForeground(new java.awt.Color(0, 255, 0));
		setMinimum(min);
		setMaximum(max);
		setStringPainted(true);
	}

	/**
	 * Set value of the bar
	 * 
	 * @param value
	 *            Value of the bar
	 */
	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub
		super.setValue(value);

		// Mise à jour du libellé
		setString(Integer.toString(value));

		// Mise à jour de la couleur
		int percentage = (value - getMinimum()) * 100
				/ (getMaximum() - getMinimum());
		if (percentage < 10)
			setForeground(new java.awt.Color(0, 255, 0));
		else if (percentage < 20)
			setForeground(new java.awt.Color(51, 255, 0));
		else if (percentage < 30)
			setForeground(new java.awt.Color(102, 255, 0));
		else if (percentage < 40)
			setForeground(new java.awt.Color(153, 255, 0));
		else if (percentage < 50)
			setForeground(new java.awt.Color(204, 255, 0));
		else if (percentage < 60)
			setForeground(new java.awt.Color(255, 255, 0));
		else if (percentage < 70)
			setForeground(new java.awt.Color(255, 204, 0));
		else if (percentage < 80)
			setForeground(new java.awt.Color(255, 153, 0));
		else if (percentage < 90)
			setForeground(new java.awt.Color(255, 102, 0));
		else if (percentage < 100)
			setForeground(new java.awt.Color(255, 51, 0));
	}

}
