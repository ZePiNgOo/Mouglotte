package com.mouglotte.map;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.tiled.TiledMap;

import com.mouglotte.game.GameState;
import com.mouglotte.specy.MemoryType;

/**
 * Map for the game.
 * 
 * @author Julien Miltat
 */
public class GameMap implements TileBasedMap {

	/** Number of displayed tiles */
	public static final int DISPLAYED_TILES = 8;
	/** Tile size */
	public static int TILE_SIZE;

	// Type de terrain
	public static final int GRASS = 0;
	public static final int WATER = 1;
	public static final int TREES = 2;

	/** Game */
	private GameState game;
	/** Tiled map */
	private TiledMap map;

	/** Offsets for scrolling */
	private float offsetX, offsetY;
	private int tileOffsetX, tileOffsetY;

	/** Objects */
	private int[][] objects;
	/** Entities */
	private Object[][] entities;
	/** Tiles visited by path finder */
	private boolean[][] visited;

	/** Pathfinder */
	private PathFinder finder;

	// private Path path;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            GameState
	 * @throws SlickException
	 */
	public GameMap(GameState game) throws SlickException {

		// Game
		this.game = game;
		// Map
		this.map = new TiledMap("res/test.tmx");
		// Get tile size
		GameMap.TILE_SIZE = this.map.getTileWidth();

		// build a collision map based on tile properties in the TileD map
		// this.blocked = new
		// boolean[this.map.getWidth()][this.map.getHeight()];
		// for (int x = 0; x < this.map.getWidth(); x++) {
		// for (int y = 0; y < this.map.getHeight(); y++) {
		// int tileID = this.map.getTileId(x, y, 0);
		// String value = this.map.getTileProperty(tileID, "blocked",
		// "false");
		// if ("true".equals(value)) {
		// this.blocked[x][y] = true;
		// }
		// }
		// }

		// // Terrain
		// terrain = new int[getWidthInTiles()][getHeightInTiles()];
		// // Unités
		// units = new int[getWidthInTiles()][getHeightInTiles()];
		// // Visité
		// visited = new boolean[getWidthInTiles()][getHeightInTiles()];
		// Objects
		objects = new int[getWidthInTiles()][getHeightInTiles()];
		// Entities
		entities = new Object[getWidthInTiles()][getHeightInTiles()];
		// Visited tiles bu the path finder
		visited = new boolean[getWidthInTiles()][getHeightInTiles()];

		// Instantiation of the path finder
		this.finder = new AStarPathFinder(this, 500, true);

		// addMouseListener(new MouseAdapter() {
		// public void mousePressed(MouseEvent e) {
		//
		// if (SwingUtilities.isLeftMouseButton(e)) handleLeftClick(e);
		// else if (SwingUtilities.isRightMouseButton(e)) handleRightClick(e);
		// }
		// });
		// addMouseMotionListener(new MouseMotionListener() {
		// public void mouseDragged(MouseEvent e) {
		// }
		//
		// public void mouseMoved(MouseEvent e) {
		// handleMouseMoved(e.getX(), e.getY());
		// }
		// });
		// addWindowListener(new WindowAdapter() {
		// public void windowClosing(WindowEvent e) {
		// System.exit(0);
		// }
		// });

		// setSize(600, 600);
		// setResizable(false);
		// setVisible(true);
	}

	/**
	 * Get map width in pixels
	 * 
	 * @return Map width in pixels
	 */
	public int getWidth() {
		return this.map.getWidth() * this.map.getTileWidth();
	}

	/**
	 * Get map height in pixels
	 * 
	 * @return Map height in pixels
	 */
	public int getheight() {
		return this.map.getHeight() * this.map.getTileHeight();
	}

	/**
	 * Get map width in tiles
	 * 
	 * @return Map width in tiles
	 */
	public int getWidthInTiles() {
		return this.map.getWidth();
	}

	/**
	 * Get map height in tiles
	 * 
	 * @return Map height in tiles
	 */
	public int getHeightInTiles() {
		return this.map.getHeight();
	}

	/**
	 * Get tile row index from a y coordinate
	 * 
	 * @param y
	 *            y position
	 * @return Row index of the tile
	 */
	public int getTileRow(int y) {
		return (y - (int) this.offsetY) / this.map.getTileHeight()
				+ this.tileOffsetY;
	}

	/**
	 * Get tile column index from a x coordinate
	 * 
	 * @param x
	 *            x position
	 * @return Column index of the tile
	 */
	public int getTileColumn(int x) {
		return (x - (int) this.offsetX) / this.map.getTileWidth()
				+ this.tileOffsetX;
	}

	/**
	 * Control if the coordinates are in the map
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 */
	public boolean contains(int x, int y) {
		if ((x > 0) && (y > 0)
				&& (x < getWidthInTiles() * this.map.getTileWidth())
				&& (y < getHeightInTiles() * this.map.getTileHeight()))
			return true;
		else
			return false;
	}

	/**
	 * Initialize tiles visited by the path finer
	 */
	public void clearVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	/**
	 * has this tile been visited by the path finder ?
	 * 
	 * @param i
	 *            Column index of the tile
	 * @param j
	 *            Row index of the tile
	 */
	public boolean visited(int i, int j) {
		return visited[i][j];
	}

	// // Récupération du terrain dans cette zone
	// public int getTerrain(int x, int y) {
	// return terrain[x][y];
	// }

	// // Récupération de l'unité dans cette zone
	// public int getUnit(int x, int y) {
	// return units[x][y];
	// }

	// // Définition de l'emplacement de l'unité
	// public void setUnit(int x, int y, int unit) {
	// units[x][y] = unit;
	// }

	/**
	 * Is this tile blocked ?
	 * 
	 * @param mover
	 *            Moving entity
	 * @param i
	 *            Column index of the tile
	 * @param j
	 *            Row index of the tile
	 * @return true if the tile is blocked
	 */
	public boolean blocked(Mover mover, int i, int j) {

		// Blocking objects
		if (objects[i][j] == WATER || objects[i][j] == TREES)
			return true;

		// Blocking entities
		if (entities[i][j] != null)
			return true;

		// Else tile is not blocked
		return false;
	}

	/**
	 * Get movement cost of moving to this tile
	 * 
	 * @param mover
	 *            Moving entity
	 * @param si
	 *            Column index of the tile we're moving from
	 * @param sj
	 *            Row index of the tile we're moving from
	 * @param ti
	 *            Column index of the tile we're moving to
	 * @param tj
	 *            Row index of the tile we're moving to
	 * @return Movement cost
	 */
	public float getCost(Mover mover, int si, int sj, int ti, int tj) {

		// Always 1
		return 1;
	}

	// // Chargement des ressources
	// private InputStream getResource(String ref) throws IOException {
	//
	// InputStream in = Thread.currentThread().getContextClassLoader()
	// .getResourceAsStream(ref);
	// if (in != null) {
	// return in;
	// }
	//
	// return new FileInputStream(ref);
	// }

	/**
	 * Define the tile as visited
	 * 
	 * @param i
	 *            Column index of the tile
	 * @param j
	 *            Row index of the tile
	 */
	public void pathFinderVisited(int i, int j) {
		this.visited[j][j] = true;
	}

	/**
	 * Update map
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 * @throws SlickException
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {

		// Calcul du scrolling de la carte
		scrolling(container, delta);
	}

	/**
	 * Calculate the scrolling of the map. Sets the values of offsetX, offsetY,
	 * tileOffsetX and tileOffsetY
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta between last call
	 */
	private void scrolling(GameContainer container, int delta) {

		// Le scrolling répond aux touches et à la souris
		// - offsetX et offsetY permettent de décaler la fenêtre de manière plus
		// fluide
		// - tileOffsetX et tileOffsetY permettent de décaler la première zone
		// affichée

		if ((container.getInput().isKeyDown(Input.KEY_RIGHT))
				|| (container.getInput().getMouseX() >= container.getWidth() - 10)) {
			this.offsetX -= delta / 3.0f;
			if (this.offsetX < 0
					&& this.tileOffsetX >= this.map.getWidth()
							- DISPLAYED_TILES)
				this.offsetX = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_LEFT))
				|| (container.getInput().getMouseX() <= 10)) {
			this.offsetX += delta / 3.0f;
			if (this.offsetX > 0 && this.tileOffsetX <= 0)
				this.offsetX = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_UP))
				|| (container.getInput().getMouseY() <= 10)) {
			this.offsetY += delta / 3.0f;
			if (this.offsetY > 0 && this.tileOffsetY <= 0)
				this.offsetY = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_DOWN))
				|| (container.getInput().getMouseY() >= container.getHeight() - 10)) {
			this.offsetY -= delta / 3.0f;
			if (this.offsetY < 0
					&& this.tileOffsetY >= this.map.getHeight()
							- DISPLAYED_TILES)
				this.offsetY = 0;
		}

		// Smooth
		if (this.offsetX < -64) {
			this.tileOffsetX++;
			this.offsetX = 0;
		}
		if (this.offsetX > 0) {
			this.tileOffsetX--;
			this.offsetX = -64;
		}
		if (this.offsetY < -64) {
			this.tileOffsetY++;
			this.offsetY = 0;
		}
		if (this.offsetY > 0) {
			this.tileOffsetY--;
			this.offsetY = -64;
		}
	}

	/**
	 * Render map
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 * @throws SlickException
	 */
	public void render(GUIContext container, Graphics g) throws SlickException {

		// Render a part of the map
		// render(x,y,sx,sy,width,height)
		this.map.render((int) this.offsetX, (int) this.offsetY,
				this.tileOffsetX, this.tileOffsetY, DISPLAYED_TILES,
				DISPLAYED_TILES);
		// - x : x position to render the first tile
		// - y : y position to render the first tile
		// - sx : column index of begining tile
		// - sy : row index of begining tile
		// - width : number of tiles rendered horizontally
		// - height : number of tiles rendered vertically

		Color color = g.getColor();
		g.setColor(Color.red);
		g.drawString(this.offsetX + "," + this.offsetY + " / "
				+ this.tileOffsetX + "," + this.tileOffsetY,
				container.getWidth() / 2 + 10, container.getHeight() / 2);
		g.drawString(container.getInput().getMouseX() + ","
				+ container.getInput().getMouseY() + " / "
				+ getTileColumn(container.getInput().getMouseX()) + ","
				+ getTileRow(container.getInput().getMouseY()),
				container.getWidth() / 2 + 10, container.getHeight() / 2 + 15);

		g.fillRect(container.getWidth() / 2, container.getHeight() / 2, 10, 10);
		g.setColor(color);

		// Translate everything rendered after this
		// So things stays at the right place despite of scrolling
		g.translate(this.offsetX - this.tileOffsetX * this.map.getTileWidth(),
				this.offsetY - this.tileOffsetY * this.map.getTileHeight());

		// Highlight mouse overed tile
		highlightTile(container, g);

		// Render objects and entities
		// Render line by line to respect z order
		for (int i = this.tileOffsetX; i < this.tileOffsetX + DISPLAYED_TILES; i++)
			for (int j = this.tileOffsetY; j < this.tileOffsetY
					+ DISPLAYED_TILES; j++) {
				// Render object first because an entity must be displayed over
				// an object
				// if (objects[i][j] != 0)
				// g.fillRect((x1, y1, width, height);
				// if (entities[i][j] != null)
				// entities[i][j].render(container, g);
			}

		// A appeler si on veut annuler le translate et donc ne plus prendre en
		// compte le scrolling
		// g.resetTransform();
	}

	/**
	 * Highlight mouse overed tile
	 */
	private void highlightTile(GUIContext container, Graphics g) {

		int i = getTileColumn(container.getInput().getMouseX());
		int j = getTileRow(container.getInput().getMouseY());
		g.drawRect(i * this.map.getTileWidth() + 1,
				j * this.map.getTileHeight() + 1, this.map.getTileWidth(),
				this.map.getTileHeight());
	}

	/**
	 * Find a path
	 * 
	 * @param mover
	 *            Moving entity
	 * @param si
	 *            Column index of the tile we're moving from
	 * @param sj
	 *            Row index of the tile we're moving from
	 * @param ti
	 *            Column index of the tile we're moving to
	 * @param tj
	 *            Row index of the tile we're moving to
	 * @return The path
	 */
	public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {

		// On convertit les x et y en position position dans les zones
		// sx /= this.map.getTileWidth();
		// sy /= this.map.getTileHeight();
		// tx /= this.map.getTileWidth();
		// ty /= this.map.getTileHeight();
		sx = getTileColumn(sx);
		sy = getTileRow(sy);
		tx = getTileColumn(tx);
		ty = getTileRow(ty);

		return this.finder.findPath(mover, sx, sy, tx, ty);
	}

	/**
	 * Search for something next to a position
	 * 
	 * @param type
	 *            Type of the thing searched
	 * @param i
	 *            Column index of the tile to search from
	 * @param j
	 *            Row index of the tile to search from
	 * @return A tile containing the searched thing
	 */
	public Tile searchNear(MemoryType type, int x, int y) {

		Random r = new Random();

		// Pour les tests, pour le moment une chance sur 10 de trouver ce qu'on
		// cherche
		// La recherche doit se faire dans le champ de vision autour du point
		if (r.nextInt(10) == 0)
			return new Tile(x, y, 0, 0, 1);
		else
			return null;
	}
}
