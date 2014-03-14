package com.mouglotte.entities;

import com.mouglotte.game.GameState;

public abstract class WorkingEntity extends Entity {

	/** Work type */
	protected WorkType type;
	
	public WorkingEntity(GameState game, WorkType type) {

		super(game);

		// Work type
		this.type = type;
	}

	@Override
	protected void mouseLeftClicked(int x, int y) {
		
		// If mouse is on the mushroom
		if (this.over)
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi déselectionner ce qui l'était
	}
}
