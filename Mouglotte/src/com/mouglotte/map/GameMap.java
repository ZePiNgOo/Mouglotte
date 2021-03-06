package com.mouglotte.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Mover;

import com.mouglotte.entities.BeanTree;
import com.mouglotte.entities.Mushroom;
import com.mouglotte.entities.Tree;
import com.mouglotte.game.GameState;

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
	public static float offsetX, offsetY;
	public static float tileOffsetX, tileOffsetY;

	// /** Objects */
	// private int[][] objects;
	// /** Entities */
	// private Entity[][] entities;
	/** Tiles visited by path finder */
	private boolean[][] visited;
	/** Tiles */
	private Tile[][] tiles;

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

		// // Objects
		// this.objects = new int[getWidthInTiles()][getHeightInTiles()];
		// // Entities
		// this.entities = new Entity[getWidthInTiles()][getHeightInTiles()];
		// Visited tiles by the path finder
		this.visited = new boolean[getWidthInTiles()][getHeightInTiles()];
		// Tiles
		this.tiles = new Tile[getWidthInTiles()][getHeightInTiles()];
		// Instantiation of the path finder
		this.finder = new AStarPathFinder(this, 500, true);
	}

	/**
	 * Get game
	 * 
	 * @return Game
	 */
	public GameState getGame() {
		return this.game;
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
		return (int) ((y - (int) GameMap.offsetY) / this.map.getTileHeight() + GameMap.tileOffsetY);
	}

	/**
	 * Get tile column index from a x coordinate
	 * 
	 * @param x
	 *            x position
	 * @return Column index of the tile
	 */
	public int getTileColumn(int x) {
		return (int) ((x - (int) GameMap.offsetX) / this.map.getTileWidth() + GameMap.tileOffsetX);
	}

	/**
	 * Get tile at the coordinates
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return Tile at these coordinates
	 */
	public Tile getTileAtPosition(int x, int y) {
		int i = getTileColumn(x);
		int j = getTileRow(y);
		if (i >= 0 && i <= getWidthInTiles() && j >= 0
				&& j <= getHeightInTiles())
			return this.tiles[i][j];
		else
			return null;
	}

	/**
	 * Get tile
	 * 
	 * @param i
	 *            Colum index
	 * @param j
	 *            Row index
	 * @return Tile
	 */
	public Tile getTile(int i, int j) {
		if (i >= 0 && i <= getWidthInTiles() && j >= 0
				&& j <= getHeightInTiles())
			return this.tiles[i][j];
		else
			return null;
	}

	/**
	 * Init map
	 */
	public void init() {

		// Create tiles
		for (int i = 0; i < this.tiles.length; i++) {
			for (int j = 0; j < this.tiles[0].length; j++) {
				this.tiles[i][j] = new Tile(i, j);
			}
		}

		// Trees
//		new Tree(this.game, getTile(5, 5));
//		new Tree(this.game, getTile(7, 8));
//		new Tree(this.game, getTile(8, 8));
//		new Tree(this.game, getTile(8, 9));
//		new Tree(this.game, getTile(9, 8));

		// Mushrooms
		new Mushroom(this.game, getTile(2, 3));
		new Mushroom(this.game, getTile(6, 13));
		new Mushroom(this.game, getTile(11, 5));
		
		// BeanTree
		new BeanTree(this.game,getTile(4, 4),5);
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

	// // R�cup�ration du terrain dans cette zone
	// public int getTerrain(int x, int y) {
	// return terrain[x][y];
	// }

	// // R�cup�ration de l'unit� dans cette zone
	// public int getUnit(int x, int y) {
	// return units[x][y];
	// }

	// // D�finition de l'emplacement de l'unit�
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

		// // Blocking objects
		// if (objects[i][j] == WATER || objects[i][j] == TREES)
		// return true;
		//
		// // Blocking entities
		// if (entities[i][j] != null)
		// return true;

		if (this.tiles[i][j].isBlocked())
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
	public void update(GameContainer container, long delta)
			throws SlickException {

		for (int i = 0; i < getWidthInTiles(); i++)
			for (int j = 0; j < getHeightInTiles(); j++) {
				getTile(i, j).update(container, delta);
			}

		// Scroll map
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
	private void scrolling(GameContainer container, long delta) {

		// Le scrolling r�pond aux touches et � la souris
		// - offsetX et offsetY permettent de d�caler la fen�tre de mani�re plus
		// fluide
		// - tileOffsetX et tileOffsetY permettent de d�caler la premi�re zone
		// affich�e
		final int MOUSE_SCROLL = 1;

		if ((container.getInput().isKeyDown(Input.KEY_RIGHT))
				|| (container.getInput().getMouseX() >= container.getWidth()
						- MOUSE_SCROLL)) {
			GameMap.offsetX -= delta / 3.0f;
			if (GameMap.offsetX < 0
					&& GameMap.tileOffsetX >= this.map.getWidth()
							- DISPLAYED_TILES)
				GameMap.offsetX = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_LEFT))
				|| (container.getInput().getMouseX() <= MOUSE_SCROLL)) {
			GameMap.offsetX += delta / 3.0f;
			if (GameMap.offsetX > 0 && GameMap.tileOffsetX <= 0)
				GameMap.offsetX = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_UP))
				|| (container.getInput().getMouseY() <= MOUSE_SCROLL)) {
			GameMap.offsetY += delta / 3.0f;
			if (GameMap.offsetY > 0 && GameMap.tileOffsetY <= 0)
				GameMap.offsetY = 0;
		}
		if ((container.getInput().isKeyDown(Input.KEY_DOWN))
				|| (container.getInput().getMouseY() >= container.getHeight()
						- MOUSE_SCROLL)) {
			GameMap.offsetY -= delta / 3.0f;
			if (GameMap.offsetY < 0
					&& GameMap.tileOffsetY >= this.map.getHeight()
							- DISPLAYED_TILES)
				GameMap.offsetY = 0;
		}

		// Smooth
		if (GameMap.offsetX < -GameMap.TILE_SIZE) {
			GameMap.tileOffsetX++;
			GameMap.offsetX = 0;
		}
		if (GameMap.offsetX > 0) {
			GameMap.tileOffsetX--;
			GameMap.offsetX = -GameMap.TILE_SIZE;
		}
		if (GameMap.offsetY < -GameMap.TILE_SIZE) {
			GameMap.tileOffsetY++;
			GameMap.offsetY = 0;
		}
		if (GameMap.offsetY > 0) {
			GameMap.tileOffsetY--;
			GameMap.offsetY = -GameMap.TILE_SIZE;
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
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		// Render a part of the map
		// render(x,y,sx,sy,width,height)
		this.map.render((int) GameMap.offsetX, (int) GameMap.offsetY,
				(int) GameMap.tileOffsetX, (int) GameMap.tileOffsetY,
				DISPLAYED_TILES, DISPLAYED_TILES);
		// - x : x position to render the first tile
		// - y : y position to render the first tile
		// - sx : column index of begining tile
		// - sy : row index of begining tile
		// - width : number of tiles rendered horizontally
		// - height : number of tiles rendered vertically

		Color color = g.getColor();
		g.setColor(Color.red);

		// Offset du scrolling
		g.drawString("Offset: " + GameMap.offsetX + "," + GameMap.offsetY
				+ " / " + GameMap.tileOffsetX + "," + GameMap.tileOffsetY, 300,
				10);

		// Position de la souris
		g.drawString("Mouse: " + container.getInput().getMouseX() + ","
				+ container.getInput().getMouseY() + "/"
				+ convScrollX(container.getInput().getMouseX()) + ","
				+ convScrollY(container.getInput().getMouseY()), 300, 25);

		// Tile en cours
		Tile tile = getTileAtPosition(container.getInput().getMouseX(),
				container.getInput().getMouseY());
		g.drawString("Tile:" + tile.getColumn() + "," + tile.getRow() + "/"
				+ tile.getX() + "," + tile.getY(), 300, 40);

		// Rectangle rouge au milieu de l'�cran
		g.fillRect(container.getWidth() / 2, container.getHeight() / 2, 10, 10);

		// Highlight mouse overed tile
		highlightTile(container, g);

		g.setColor(color);

		// Translate everything rendered after this
		// So things stays at the right place despite of scrolling
		g.translate(
				GameMap.offsetX - GameMap.tileOffsetX * this.map.getTileWidth(),
				GameMap.offsetY - GameMap.tileOffsetY
						* this.map.getTileHeight());

		// Render objects and entities
		// Render line by line to respect z order
		for (int i = (int) GameMap.tileOffsetX - 1; i <= GameMap.tileOffsetX
				+ DISPLAYED_TILES; i++)
			for (int j = (int) GameMap.tileOffsetY - 1; j <= GameMap.tileOffsetY
					+ DISPLAYED_TILES; j++) {
				// Render object first because an entity must be displayed over
				// an object
				// if (objects[i][j] != 0)
				// g.fillRect((x1, y1, width, height);
				if (i >= 0 && j >= 0 && i < getWidthInTiles()
						&& i < getHeightInTiles())
					getTile(i, j).render(container, g);
				// if (entities[i][j] != null)
				// entities[i][j].render(container, g);
			}

		// A appeler si on veut annuler le translate et donc ne plus prendre en
		// compte le scrolling
		// g.resetTransform();
	}

	/**
	 * Highlight mouse overed tile
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	private void highlightTile(GameContainer container, Graphics g) {

		Tile tile = getTileAtPosition(container.getInput().getMouseX(),
				container.getInput().getMouseY());

		g.drawRect(convScrollX(tile.getX()), convScrollY(tile.getY()),
				this.map.getTileWidth(), this.map.getTileHeight());
		g.drawString(convScrollX(tile.getX()) + "," + convScrollY(tile.getY()),
				convScrollX(tile.getX()) + GameMap.TILE_SIZE + 2,
				convScrollY(tile.getY()) + GameMap.TILE_SIZE + 2);
	}

	/**
	 * Find a path
	 * 
	 * @param mover
	 *            Moving entity
	 * @param from
	 *            From tile
	 * @param to
	 *            To tile
	 * @return The path
	 */
	public Path findPath(Mover mover, Tile from, Tile to) {
		return this.finder.findPath(mover, from.getColumn(), from.getRow(),
				to.getColumn(), to.getRow());
	}

	/**
	 * Convert to scrolled position
	 * 
	 * @param x
	 *            x position to convert
	 * @return Converted x position
	 */
	public static int convScrollX(int x) {
		return (int) (x - GameMap.tileOffsetX * GameMap.TILE_SIZE + GameMap.offsetX);
		// return (int) (x + GameMap.tileOffsetX * GameMap.TILE_SIZE -
		// GameMap.offsetX);
	}

	/**
	 * Convert to scrolled position
	 * 
	 * @param y
	 *            position to convert
	 * @return Converted y position
	 */
	public static int convScrollY(int y) {
		return (int) (y - GameMap.tileOffsetY * GameMap.TILE_SIZE + GameMap.offsetY);
		// return (int) (y + GameMap.tileOffsetY * GameMap.TILE_SIZE -
		// GameMap.offsetY);
	}

	/**
	 * Convert to not scrolled position
	 * 
	 * @param x
	 *            x position to convert
	 * @return Converted x position
	 */
	public static int convNoScrollX(int x) {
		return (int) (x + GameMap.tileOffsetX * GameMap.TILE_SIZE - GameMap.offsetX);
		// return (int) (x - GameMap.tileOffsetX * GameMap.TILE_SIZE +
		// GameMap.offsetX);
	}

	/**
	 * Convert to not scrolled position
	 * 
	 * @param y
	 *            position to convert
	 * @return Converted y position
	 */
	public static int convNoScrollY(int y) {
		return (int) (y + GameMap.tileOffsetY * GameMap.TILE_SIZE - GameMap.offsetY);
		// return (int) (y - GameMap.tileOffsetY * GameMap.TILE_SIZE +
		// GameMap.offsetY);
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
	// public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	//
	// // // Current tile where the mouse is
	// // Tile tile = getTileAtPosition(newx, newy);
	// //
	// // // Get all the tiles around the mouse
	// // for (int i = tile.getColumn() - 1; i <= tile.getColumn() + 1; i++) {
	// // for (int j = tile.getRow() - 1; j <= tile.getRow() + 1; j++) {
	// //
	// // // Tile is on the map
	// // if ((i >= 0) && (i < getWidthInTiles()) && (j >= 0)
	// // && (i < getHeightInTiles())) {
	// //
	// // // Read through entities on the tile
	// // for (Entity entity : tile.getEntities()) {
	// // if (entity.contains(newx, newy)) {
	// // entity.setMouseOver(true);
	// // return;
	// // }
	// // }
	// // }
	// // }
	// // }
	// }

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
	// public void mouseClicked(int button, int x, int y, int clickCount) {
	//
	// // // Mouse clicked on map
	// // if (this.map.contains(x, y)) {
	// //
	// // this.map.mouseClicked(x, y, clickcount);
	// // if (button == 0)
	// // this.map.mouseLeftClicked(x, y, clickCount);
	// // else if (button == 1)
	// // this.map.mouseRightClicked(x, y);
	// // }
	// // // Mouse clicked on right panel
	// // // else if (this.rightPanel.contains(x,y)) {
	// // //
	// // // }
	// }

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
	// public void mouseLeftClicked(int x, int y, int clickCount) {
	//
	// }

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
	// public void mouseRightClicked(int x, int y) {
	//
	// // S'il y a une unit� ici
	// // if (this.map.getUnit(x, y) != 0) {
	//
	// // Ici on va g�rer des actions : SOCIAL, LOVE, FIGHT
	//
	// // S'il n'y a pas d'unit� ici
	// // } else {
	//
	// // Si une unit� est s�lectionn�e
	// // if (this.mouglotte.isSelected()) {
	// //
	// // // R�initialisation des zones visit�es
	// // this.map.clearVisited();
	// //
	// // // Recherche du chemin
	// // this.mouglotte.setPath(this.map.findPath(new UnitMover(3),
	// // this.mouglotte.getX(), this.mouglotte.getY(), x, y));
	// // // path = finder.findPath(
	// // // new UnitMover(map.getUnit(selectedx, selectedy)),
	// // // selectedx, selectedy, x, y);
	// //
	// // // Si le chemin est trouv�
	// // // if (this.mouglotte.hasPath()) {
	// //
	// // // D�placement de l'unit�
	// // // int unit = map.getUnit(selectedx, selectedy);
	// // // map.setUnit(selectedx, selectedy, 0);
	// // // map.setUnit(x, y, unit);
	// // // selectedx = x;
	// // // selectedy = y;
	// // // // R�initialisation du dernier chemin recherch�
	// // // lastFindX = -1;
	// //
	// // // D�placement instantan�
	// // // this.mouglotte.setLocation(x, y);
	// //
	// // // R�initialisation du chemin
	// // // this.map.clearPath();
	// // // }
	// // }
	// }
}
