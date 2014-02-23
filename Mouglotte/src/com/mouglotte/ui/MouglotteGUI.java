package com.mouglotte.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class MouglotteGUI {

	protected Graphics g;
	protected String name;

	public MouglotteGUI(GameContainer c, String name) throws SlickException {
		g = c.getGraphics();
		this.name = name;
	}

	public abstract void drawGUI(GameContainer container, Graphics g);

	public abstract void update(GameContainer container, int state);

	//public static void setMessage(String message, Joueur joueur);
}
