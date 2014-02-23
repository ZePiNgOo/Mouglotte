package com.mouglotte.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

import com.mouglotte.game.GameState;

public class BottomPanel extends MouglotteGUI implements ComponentListener {

	private MouseOverArea play;

	public BottomPanel(GameContainer c, String name) throws SlickException {

		super(c, name);

		// Création du bouton
		this.play = new MouseOverArea(c, new Image("res/bouton.png"), 300, 450,
				this);
		this.play.setNormalColor(new Color(0.7f, 0.7f, 0.7f, 1f));
		this.play.setMouseOverColor(new Color(0.9f, 0.9f, 0.9f, 1f));
	}

	@Override
	public void componentActivated(AbstractComponent source) {

		// Passage à l'état 2 : le jeu
		if (source.equals(this.play))
			GameState.setGameState(2);
	}

	@Override
	public void drawGUI(GameContainer container, Graphics g) {

		switch (GameState.getGameState()) {
		case 1:
			play.render(container, g);
			break;
		}
	}

	@Override
	public void update(GameContainer container, int state) {


	}

}
