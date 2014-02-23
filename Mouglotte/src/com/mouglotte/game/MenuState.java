package com.mouglotte.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState implements ComponentListener {

	// ID de l'état
	private static final EnumGameState ID = EnumGameState.MENU;

	// This is the game
	private StateBasedGame game;

	// Bouton Jouer
	private MouseOverArea btPlay;

	@Override
	// Récupération de l'ID de l'état
	public int getID() {
		return MenuState.ID.getValue();
	}

	@Override
	// Initialisation
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		this.game = game;

		// Création du bouton Jouer
		this.btPlay = new MouseOverArea(container, new Image("res/button.png"),
				100, 50, this);
		this.btPlay.setMouseOverImage(new Image("res/buttonOver.png"));
		this.btPlay.setMouseDownImage(new Image("res/buttonPressed.png"));
	}

	@Override
	// Mise à jour
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	// Affichage
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.btPlay.render(container, g);
	}

	@Override
	public void componentActivated(AbstractComponent comp) {
	}

	@Override
	// Clic de la souris
	public void mouseClicked(int button, int x, int y, int clickCount) {

		if (x >= this.btPlay.getX()
				&& x <= this.btPlay.getX() + this.btPlay.getWidth()
				&& y >= this.btPlay.getY()
				&& y <= this.btPlay.getY() + this.btPlay.getHeight())
			btPlayClicked(button);
	}

	// Bouton btPlay cliqué
	private void btPlayClicked(int button) {

		if (button == 0) {
			// Passage à l'état GAME
			this.game.enterState(EnumGameState.GAME.getValue());
		}

	}
}
