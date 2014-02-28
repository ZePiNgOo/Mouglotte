package com.mouglotte.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mouglotte.genetics.Genetics;
import com.mouglotte.map.GameMap;
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

	private Mouglotte mouglotte;
	// private Mouglotte mouglotte2;

	private String deltaString = "";
	private String infoString = "";

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

		// this.map.init() pourrait être utile

		// Pour les tests
		this.mouglotte = new Mouglotte(this);
		this.mouglotte.setLocation(15, 15);
		// this.mouglotte2 = new Mouglotte(this);
		// this.mouglotte2.setLocation(100, 100);
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

		// Mise à jour de la mouglotte
		this.mouglotte.update(container, delta);

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

		this.mouglotte.render(container, g);
		// this.mouglotte2.render(container, g);

		g.drawString(this.deltaString, 300, 10);
		g.drawString(this.infoString, 10, 300);
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

		// Si la souris est sortie on ne fait rien
		if ((newx < 0) || (newy < 0)
				|| (newx >= this.map.getWidthInTiles() * GameMap.TILE_SIZE)
				|| (newy >= this.map.getHeightInTiles() * GameMap.TILE_SIZE)) {
			return;
		}

		// Si une unité est sélectionnée
		if (this.mouglotte.isSelected()) {

			// // Recherche du chemin
			// this.map.findPath(new UnitMover(3), this.mouglotte.getX(),
			// this.mouglotte.getY(), newx, newy);
		}
		this.infoString = Integer.toString(newx) + "," + Integer.toString(newy)
				+ " => " + Integer.toString(newx / GameMap.TILE_SIZE) + ","
				+ Integer.toString(newy / GameMap.TILE_SIZE);
	}

	/**
	 * Get selected mouglotte
	 * 
	 * @return Selected mouglotte
	 */
	public Mouglotte getSelectedMouglotte() {
		return this.mouglotte.isSelected() ? this.mouglotte : null;
	}
}
