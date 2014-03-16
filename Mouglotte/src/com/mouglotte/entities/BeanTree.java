package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.mouglotte.game.GameState;

public class BeanTree extends Entity {

	/** Ages (in month) */
	private final static int YOUNG_AGE = 1;
	private final static int ADULT_AGE = 30;
	
	public BeanTree(GameState game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventMinute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventHour() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventDay() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventMonth() {

		// age en mois pour beantree
		this.age++;
	}

	@Override
	protected void eventSeason() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseLeftClicked(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Grow
	 * @param time Time in days
	 */
	private void grow(int time) {
		
		// pousse en fonction de son âge : tous les jours quand il est petit, tous les mois ensuite
		
		if (this.age <= YOUNG_AGE)
			growUp();
		else if (this.age <= ADULT_AGE && time == 20)
			growUp();
		else if (this.age > ADULT_AGE)
		
		switch (time) {
		// Every day growth
		case 1:
			break;
		// Every month growth
		case 20:
			break;
		}
	}

}
