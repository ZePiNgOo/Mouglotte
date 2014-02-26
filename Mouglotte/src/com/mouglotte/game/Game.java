package com.mouglotte.game;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	// Titre
	private static final String TITLE = "Mouglotte";
	// Taille de l'écran
	public static final int SCREEN_WIDTH = 700;
	public static final int SCREEN_HEIGHT = 512; // 8 tiles

	// Constructeur
	public Game(String name) {
		super(name);
	}

	@Override
	// Initialisation de la liste des états
	public void initStatesList(GameContainer container) throws SlickException {

		if (container instanceof AppGameContainer) {
			
			addState(new MenuState());
			addState(new GameState());
			container.setShowFPS(true);
			enterState(EnumGameState.MENU.getValue());
		}
	}

	// Main
	public static void main(String[] args) throws LWJGLException {
		try {
			AppGameContainer container = new AppGameContainer(new Game(TITLE));
			//Display.setDisplayMode(new DisplayMode(640,480));
			container.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			container.setVSync(true);
			container.setMultiSample(64);
			container.setTargetFrameRate(60);
			container.setVerbose(true);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
