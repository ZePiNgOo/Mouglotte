package com.mouglotte.entities;

import com.mouglotte.game.GameState;
import com.mouglotte.map.Tile;

public abstract class WorkingEntity extends Entity {



	/** Work type */
	protected WorkType type;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 * @param type
	 *            Type of work
	 */
	public WorkingEntity(GameState game, Tile tile, WorkType type) {

		super(game,tile);

		// Work type
		this.type = type;
	}

	@Override
	public void eventRealSecond() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventMinute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventHour() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventMonth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventSeason() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventYear() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
//	protected void mouseLeftClicked(int x, int y, int clickCount) {
	protected void mouseLeftClicked(int x, int y) {

		// If mouse is on the mushroom
		if (this.over)
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi déselectionner ce qui l'était
	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
//	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
