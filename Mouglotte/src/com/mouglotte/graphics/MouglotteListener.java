package com.mouglotte.graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

public class MouglotteListener implements ComponentListener {

	Graphics g;

	// Constructeur
	public MouglotteListener(GameContainer c) {
		g = c.getGraphics();
	}

	@Override
	// Activation de la mouglotte
	public void componentActivated(AbstractComponent source) {

		g.drawString("la case " + source.getClass().getName() + " est activée",
				200, 200);
	}
}
