package com.mouglotte.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.mouglotte.game.GameState;

public abstract class MouglotteGUI {

	// Jeu
	protected GameState game;

	public MouglotteGUI(GameState game) throws SlickException {
		this.game = game;
	}

	public abstract void update(GameContainer container, int delta);

	public abstract void render(GameContainer container, Graphics g);

	//public static void setMessage(String message, Joueur joueur);
}
