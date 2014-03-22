package com.mouglotte.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mouglotte.entities.MouglotteEntity;
import com.mouglotte.entities.Relations;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Spawner;
import com.mouglotte.specy.Mouglotte;
import com.mouglotte.time.TimeKeeper;
import com.mouglotte.ui.RightPanel;

public class GameState extends BasicGameState {

	// Pour les tests
	public static final int TIME_FACTOR = 3;

	/** State ID */
	private static final EnumGameState ID = EnumGameState.GAME;

	/** Panel on the right */
	private RightPanel rightPanel;
	/** Game container */
	private GameContainer container;
	/** Time keeper */
	private TimeKeeper timeKeeper;
	/** Game map */
	private GameMap map;
	/** Spawner */
	private Spawner spawner;
	
	private ArrayList<MouglotteEntity> mouglottes;

	/**
	 * Get game state ID
	 * 
	 * @return Game state ID
	 */
	@Override
	public int getID() {
		return GameState.ID.getValue();
	}

	/**
	 * Get game container
	 * 
	 * @return Game container
	 */
	public GameContainer getContainer() {
		return this.container;
	}
	
	/**
	 * Get selected mouglotte
	 * 
	 * @return Selected mouglotte
	 */
	public Mouglotte getSelectedMouglotte() {

		for (final MouglotteEntity mouglotte : this.mouglottes) {
			if (mouglotte.isSelected())
				return mouglotte.getMouglotte();
		}
		return null;
	}

	/**
	 * Get map
	 * 
	 * @return Map
	 */
	public GameMap getMap() {
		return this.map;
	}

	/**
	 * Initialize game
	 * 
	 * @param container
	 *            Game container
	 * @param game
	 *            General game
	 * @throws SlickException
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Game container
		this.container = container;

		// Initialize genetics
		Genetics.initialize();
		// Intialize relations between entities
		Relations.initialize();

		// Create right panel
		this.rightPanel = new RightPanel(this);
		// Create time keeper
		this.timeKeeper = new TimeKeeper();
		// Create map
		this.map = new GameMap(this);
		// Create spawner
		this.spawner = new Spawner(this.map);
		
		// Init map
		this.map.init();

		// Pour les tests
		this.mouglottes = new ArrayList<MouglotteEntity>();
		this.mouglottes.add(new MouglotteEntity(this, this.map.getTile(2, 2)));
//		this.mouglottes.add(new MouglotteEntity(this, this.map.getTile(4, 4)));
	}

	/**
	 * Update game
	 * 
	 * @param container
	 *            Game container
	 * @param game
	 *            General game
	 * @param delta
	 *            Delta time since last call
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		// Time keeper
		this.timeKeeper.update(container, delta);
		// Right panel
		this.rightPanel.update(container, delta);
		// Map
		this.map.update(container, delta);
		// Spawner
		this.spawner.update(container, delta);

		// // Mouglottes
		// for (final MouglotteEntity mouglotte : this.mouglottes){
		// mouglotte.update(container, delta);
		// }
	}

	/**
	 * Render game
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		this.rightPanel.render(container, g);

		g.setAntiAlias(true);
		// g.setColor(Color.white);
		// g.fillRect(0, 0, container.getWidth(), container.getHeight());
		// g.setColor(Color.black);

		// this.bottomPanel.drawGUI(container, g);
		// this.rightPanel.drawGUI(container, g);

		// Affichage de la carte
		// Contient le calcul du décalage du au scrolling, la méthode doit être
		// la première appelée
		this.map.render(container, g);

		// Effectué sur le tile
//		// Render mouglottes
//		for (final MouglotteEntity mouglotte : this.mouglottes) {
//			mouglotte.render(container, g);
//		}

		g.drawString(this.timeKeeper.getTime(), 300, 10);
	}

	/**
	 * Mouse clicked event
	 * 
	 * @param button
	 *            Button clicked
	 * @param x
	 *            x position of the mouse
	 * @param y
	 *            y position of the mouse
	 * @param clickCount
	 *            Number of clicks
	 */
//	@Override
//	public void mouseClicked(int button, int x, int y, int clickCount) {
//
////		// Mouse clicked on map
////		if (this.map.contains(x, y)) {
////
////			this.map.mouseClicked(button, x, y, clickCount);
////		}
////		// Mouse clicked on right panel
////		// else if (this.rightPanel.contains(x,y)) {
////		//
////		// }
//
//	}

	// /**
	// * Mouse left-clicked event
	// *
	// * @param x
	// * x position of the mouse
	// * @param y
	// * y position of the mouse
	// * @param clickCount
	// * Number of clicks
	// */
	// public void mouseLeftClicked(int x, int y, int clickCount) {
	//
	//
	// }
	//
	// /**
	// * Mouse right-clicked event
	// *
	// * @param x
	// * x position of the mouse
	// * @param y
	// * y position of the mouse
	// * @param clickCount
	// * Number of clicks
	// */
	// public void mouseRightClicked(int x, int y) {
	//
	// // S'il y a une unité ici
	// // if (this.map.getUnit(x, y) != 0) {
	//
	// // Ici on va gérer des actions : SOCIAL, LOVE, FIGHT
	//
	// // S'il n'y a pas d'unité ici
	// // } else {
	//
	// // Si une unité est sélectionnée
	// // if (this.mouglotte.isSelected()) {
	// //
	// // // Réinitialisation des zones visitées
	// // this.map.clearVisited();
	// //
	// // // Recherche du chemin
	// // this.mouglotte.setPath(this.map.findPath(new UnitMover(3),
	// // this.mouglotte.getX(), this.mouglotte.getY(), x, y));
	// // // path = finder.findPath(
	// // // new UnitMover(map.getUnit(selectedx, selectedy)),
	// // // selectedx, selectedy, x, y);
	// //
	// // // Si le chemin est trouvé
	// // // if (this.mouglotte.hasPath()) {
	// //
	// // // Déplacement de l'unité
	// // // int unit = map.getUnit(selectedx, selectedy);
	// // // map.setUnit(selectedx, selectedy, 0);
	// // // map.setUnit(x, y, unit);
	// // // selectedx = x;
	// // // selectedy = y;
	// // // // Réinitialisation du dernier chemin recherché
	// // // lastFindX = -1;
	// //
	// // // Déplacement instantané
	// // // this.mouglotte.setLocation(x, y);
	// //
	// // // Réinitialisation du chemin
	// // // this.map.clearPath();
	// // // }
	// // }
	// }

	/**
	 * Mouse moved event
	 * 
	 * @param oldx
	 *            Old x position of the mouse
	 * @param oldy
	 *            Old y position of the mouse
	 * @param newx
	 *            Current x position of the mouse
	 * @param newy
	 *            Current y position of the mouse
	 */
//	@Override
//	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
//
////		// Mouse clicked on map
////		if (this.map.contains(newx, newy)) {
////
////			this.map.mouseMoved(oldx, oldy, newx, newy);
////		}
////		// Mouse clicked on right panel
////		// else if (this.rightPanel.contains(x,y)) {
////		//
////		// }
//	}
}
