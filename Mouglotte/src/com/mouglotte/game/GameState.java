package com.mouglotte.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mouglotte.genetics.Genetics;
import com.mouglotte.map.GameMap;
import com.mouglotte.specy.Desire;
import com.mouglotte.specy.DesireType;
import com.mouglotte.specy.Mouglotte;
import com.mouglotte.ui.RightPanel;

public class GameState extends BasicGameState {

	// Pour les tests
	public static final int TIME_FACTOR = 3;

	// ID de l'état
	private static final EnumGameState ID = EnumGameState.GAME;

	// private int sensX = 1, sensY = 1;
	// private int x, y;

	// Panneau du bas
	// private BottomPanel bottomPanel;
	// Panneau de droite
	private RightPanel rightPanel;
	// Conteneur
	private GameContainer container;
	// Carte
	private GameMap map;

	private List<Mouglotte> mouglottes;

	private String deltaString = "";

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

		// Create map
		this.map = new GameMap(this);
		// Create right panel
		this.rightPanel = new RightPanel(this);

		// Pour les tests
		this.mouglottes = new ArrayList<Mouglotte>();
		this.mouglottes.add(new Mouglotte(this));
//		this.mouglottes.add(new Mouglotte(this));
//		this.mouglottes.add(new Mouglotte(this));
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

		// deltaString = Integer.toString(minutes);
		this.rightPanel.update(container, delta);

		// Mise à jour de la carte
		this.map.update(container, delta);

		// Update mouglottes
		for (final Mouglotte mouglotte : this.mouglottes){
			mouglotte.update(container, delta);
		}
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

		// Render mouglottes
		for (final Mouglotte mouglotte : this.mouglottes){
			mouglotte.render(container, g);
		}
		// this.mouglotte2.render(container, g);

		g.drawString(this.deltaString, 300, 10);
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
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {

		if (button == 0)
			mouseLeftClicked(x, y, clickCount);
		else if (button == 1)
			mouseRightClicked(x, y);
	}

	/**
	 * Mouse left-clicked event
	 * 
	 * @param x
	 *            x position of the mouse
	 * @param y
	 *            y position of the mouse
	 * @param clickCount
	 *            Number of clicks
	 */
	public void mouseLeftClicked(int x, int y, int clickCount) {

	}

	/**
	 * Mouse right-clicked event
	 * 
	 * @param x
	 *            x position of the mouse
	 * @param y
	 *            y position of the mouse
	 * @param clickCount
	 *            Number of clicks
	 */
	public void mouseRightClicked(int x, int y) {

		// S'il y a une unité ici
		// if (this.map.getUnit(x, y) != 0) {

		// Ici on va gérer des actions : SOCIAL, LOVE, FIGHT

		// S'il n'y a pas d'unité ici
		// } else {

		// Si une unité est sélectionnée
		// if (this.mouglotte.isSelected()) {
		//
		// // Réinitialisation des zones visitées
		// this.map.clearVisited();
		//
		// // Recherche du chemin
		// this.mouglotte.setPath(this.map.findPath(new UnitMover(3),
		// this.mouglotte.getX(), this.mouglotte.getY(), x, y));
		// // path = finder.findPath(
		// // new UnitMover(map.getUnit(selectedx, selectedy)),
		// // selectedx, selectedy, x, y);
		//
		// // Si le chemin est trouvé
		// // if (this.mouglotte.hasPath()) {
		//
		// // Déplacement de l'unité
		// // int unit = map.getUnit(selectedx, selectedy);
		// // map.setUnit(selectedx, selectedy, 0);
		// // map.setUnit(x, y, unit);
		// // selectedx = x;
		// // selectedy = y;
		// // // Réinitialisation du dernier chemin recherché
		// // lastFindX = -1;
		//
		// // Déplacement instantané
		// // this.mouglotte.setLocation(x, y);
		//
		// // Réinitialisation du chemin
		// // this.map.clearPath();
		// // }
		// }
	}

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
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {

	}

	/**
	 * Get selected mouglotte
	 * 
	 * @return Selected mouglotte
	 */
	public Mouglotte getSelectedMouglotte() {
		
		for (final Mouglotte mouglotte : this.mouglottes){
			if (mouglotte.isSelected()) return mouglotte;
		}
		return null;
	}
}
