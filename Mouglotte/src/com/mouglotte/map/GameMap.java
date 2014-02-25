package com.mouglotte.map;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.tiled.TiledMap;

import com.mouglotte.game.GameState;
import com.mouglotte.specy.MemoryType;

/**
 * The data map from our example game. This holds the state and context of each
 * tile on the map. It also implements the interface required by the path
 * finder. It's implementation of the path finder related methods add specific
 * handling for the types of units and terrain in the example game.
 * 
 * @author Kevin Glass
 */
public class GameMap implements TileBasedMap {

	// Taille d'une zone (tile)
	// public static final int TILE_SIZE = 16;
	// Largeur et hauteur en zones
	// public static final int WIDTH = 20;
	// public static final int HEIGHT = 20;

	// Type de terrain
	public static final int GRASS = 0;
	public static final int WATER = 1;
	public static final int TREES = 2;
	// public static final int PLANE = 3;
	// public static final int BOAT = 4;
	// public static final int TANK = 5;
	public static final int MOUGLOTTE = 3;

	// Jeu
	private GameState game;
	// Carte
	private TiledMap map;

	// Types de zones
	private Image[] tiles = new Image[4];
	// Buffer pour afficher la carte
	// private Image buffer;
	private ImageBuffer buffer;

	private float totoX = 20, totoY = 20;
	private int mapX, mapY;

	// Terrain
	private int[][] terrain;
	// Unités
	private int[][] units;
	// Visité
	private boolean[][] visited;
	/**
	 * The collision map indicating which tiles block movement - generated based
	 * on tile properties
	 */
	private boolean[][] blocked;

	/** The width of the display in tiles */
	private int widthInTiles;
	/** The height of the display in tiles */
	private int heightInTiles;

	/** The offset from the centre of the screen to the top edge in tiles */
	private int topOffsetInTiles;
	/** The offset from the centre of the screen to the left edge in tiles */
	private int leftOffsetInTiles;

	// Pathfinder
	private PathFinder finder;

	// private Path path;

	// Constructeur
	public GameMap(GameState game) throws SlickException {

		// Jeu
		this.game = game;
		// Carte
		this.map = new TiledMap("res/test.tmx");

		// build a collision map based on tile properties in the TileD map
		this.blocked = new boolean[this.map.getWidth()][this.map.getHeight()];
		for (int x = 0; x < this.map.getWidth(); x++) {
			for (int y = 0; y < this.map.getHeight(); y++) {
				int tileID = this.map.getTileId(x, y, 0);
				String value = this.map.getTileProperty(tileID, "blocked",
						"false");
				if ("true".equals(value)) {
					this.blocked[x][y] = true;
				}
			}
		}

		// caculate some layout values for rendering the tilemap. How many tiles
		// do we need to render to fill the screen in each dimension and how far
		// is
		// it from the centre of the screen
		this.widthInTiles = this.game.getContainer().getWidth()
				/ this.map.getTileWidth();
		this.heightInTiles = this.game.getContainer().getHeight()
				/ this.map.getTileHeight();
		this.topOffsetInTiles = this.heightInTiles / 2;
		this.leftOffsetInTiles = this.widthInTiles / 2;

		// // Chargement des ressoures
		// try {
		// tiles[GameMap.TREES] = new Image(getResource("res/rocks.png"),
		// "res/rocks.png", false);
		// tiles[GameMap.GRASS] = new Image(getResource("res/grass.png"),
		// "res/grass.png", false);
		// } catch (IOException e) {
		// System.err.println("Failed to load resources: " + e.getMessage());
		// System.exit(0);
		// } catch (SlickException e) {
		// }
		//
		// fillArea(2, 5, 2, 3, TREES);
		// fillArea(8, 10, 3, 1, TREES);
		// fillArea(15, 4, 2, 2, TREES);
		// fillArea(3, 15, 1, 4, TREES);

		// Terrain
		terrain = new int[getWidthInTiles()][getHeightInTiles()];
		// Unités
		units = new int[getWidthInTiles()][getHeightInTiles()];
		// Visité
		visited = new boolean[getWidthInTiles()][getHeightInTiles()];

		// Instanciation du Pathfinder
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

	// Récupération de la taille d'une zone
	public int getTileSize() {
		return this.map.getTileHeight();
	}

	// Contrôle sur les coordonnées sont dans la carte
	public boolean contains(int x, int y) {
		if ((x > 0) && (y > 0)
				&& (x < getWidthInTiles() * this.map.getTileWidth())
				&& (y < getHeightInTiles() * this.map.getTileHeight()))
			return true;
		else
			return false;
	}

	// Pour les tests
	// Remplissage d'une zone avec le bon terrain
	private void fillArea(int x, int y, int width, int height, int type) {

		for (int xp = x; xp < x + width; xp++) {
			for (int yp = y; yp < y + height; yp++) {
				terrain[xp][yp] = type;
			}
		}
	}

	// Réinitialisation des zones visitées
	public void clearVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	// Cette zone est-elle visitée
	public boolean visited(int x, int y) {
		return visited[x][y];
	}

	// Récupération du terrain dans cette zone
	public int getTerrain(int x, int y) {
		return terrain[x][y];
	}

	// Récupération de l'unité dans cette zone
	public int getUnit(int x, int y) {
		return units[x][y];
	}

	// Définition de l'emplacement de l'unité
	public void setUnit(int x, int y, int unit) {
		units[x][y] = unit;
	}

	// Cette zone est-elle un obstacle ?
	public boolean blocked(Mover mover, int x, int y) {

		// S'il y a une unité dessus elle bloque
		if (getUnit(x, y) != 0) {
			return true;
		}

		// int unit = ((UnitMover) mover).getType();

		// Actuellement l'eau et les arbres sont bloquants
		if (terrain[x][y] == WATER || terrain[x][y] == TREES)
			return true;

		// Sinon ça passe
		return false;
	}

	// Coût pour passer dans cette zone
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {

		// Toujours 1
		return 1;
	}

	// Récupération de la hauteur en zones
	public int getHeightInTiles() {
		// return HEIGHT;
		return this.map.getHeight();
	}

	// Récupération de la largeur en zones
	public int getWidthInTiles() {
		// return WIDTH;
		return this.map.getWidth();
	}

	// Chargement des ressources
	private InputStream getResource(String ref) throws IOException {

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(ref);
		if (in != null) {
			return in;
		}

		return new FileInputStream(ref);
	}

	// Définition de la zone comme visitée
	public void pathFinderVisited(int x, int y) {
		this.visited[x][y] = true;
	}

	// Mise à jour
	public void update(GameContainer container, int delta)
			throws SlickException {

		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			// this.totoX = this.totoX < getWidthInTiles() ? this.totoX
			// + this.map.getWidth() : getWidthInTiles();
			this.totoX -= delta / 3.0f;
			if (this.totoX > getWidthInTiles()) this.totoX = getWidthInTiles();
		}
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			// this.totoX = this.totoX > 0 ? this.totoX - this.map.getWidth() :
			// 0;
			this.totoX += delta / 3.0f;
			if (this.totoX < 0) this.totoX = 0;
		}
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			// this.totoY = this.totoY < getHeightInTiles() ? this.totoY
			// + this.map.getHeight() : getHeightInTiles();
			this.totoY += delta / 3.0f;
			if (this.totoY < 0) this.totoY = 0;
		}
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			// this.totoY = this.totoY > 0 ? this.totoY - this.map.getHeight() :
			// 0;
			this.totoY -= delta / 3.0f;
			if (this.totoY > getHeightInTiles()) this.totoY = getHeightInTiles();
		}
		
		// Smooth
		if (this.totoX < 0) {
			this.mapX++;
			this.totoX = 32;
		}
		if (this.totoX > 32) {
			this.mapX--;
			this.totoX = 0;
		}
		if (this.totoY < 0) {
			this.mapY++;
			this.totoY = 32;
		}
		if (this.totoY > 32) {
			this.mapY--;
			this.totoY = 0;
		}
	}

	// Affichage
	public void render(GUIContext container, Graphics g) throws SlickException {

		// Graphics gBuff = g;
		//
		// // Affichage de la carte
		// for (int x = 0; x < this.getWidthInTiles(); x++) {
		// for (int y = 0; y < this.getHeightInTiles(); y++) {
		// gBuff.drawImage(this.tiles[this.getTerrain(x, y)], x
		// * TILE_SIZE, y * TILE_SIZE, null);
		// }
		// }

		int playerTileX = (int) this.totoX - 32;
		int playerTileY = (int) this.totoY - 32;
		int playerTileOffsetX = (int) ((playerTileX - this.totoX) * this.map
				.getTileWidth());
		int playerTileOffsetY = (int) ((playerTileY - this.totoY) * this.map
				.getTileHeight());
		// int playerTileOffsetX = 5;
		// int playerTileOffsetY = 5;

		// render the section of the map that should be visible. Notice the -1
		// and +3 which renders
		// a little extra map around the edge of the screen to cope with tiles
		// scrolling on and off
		// the screen
//		this.map.render(playerTileOffsetX - (10 / 2), playerTileOffsetY
//				- (10 / 2), playerTileX - this.leftOffsetInTiles - 1,
//				playerTileY - this.topOffsetInTiles - 1, this.widthInTiles + 3,
//				this.heightInTiles + 3);
		this.map.render(playerTileX, playerTileY, this.mapX, this.mapY, this.mapX+26, this.mapY+26 );
		//this.map.render((int)totoX, (int)totoY,0,0,5,5);

		Color color = g.getColor();
		g.setColor(Color.red);
		g.drawString(this.totoX+","+this.totoY, this.game.getContainer().getWidth()/2+10, this.game.getContainer().getHeight()/2);
		g.fillRect(this.game.getContainer().getWidth()/2, this.game.getContainer().getHeight()/2, 10, 10);
		g.setColor(color);
		
		// draw entities relative to the player that must appear in the centre
		// of the screen
//		g.translate(400 - (int) (this.totoX * 32),
//				300 - (int) (this.totoY * 32));
//		
//		g.resetTransform();
	}

	// Trouver le chemin
	public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {

		// On convertit les x et y en position position dans les zones
		sx /= this.map.getTileWidth();
		sy /= this.map.getTileHeight();
		tx /= this.map.getTileWidth();
		ty /= this.map.getTileHeight();
		// sx = sx % TILE_SIZE > TILE_SIZE / 2 ? sx / TILE_SIZE - 1 : sx /
		// TILE_SIZE;
		// sy = sy % TILE_SIZE > TILE_SIZE / 2 ? sy / TILE_SIZE - 1 : sy /
		// TILE_SIZE;
		// tx = tx % TILE_SIZE > TILE_SIZE / 2 ? tx / TILE_SIZE - 1 : tx /
		// TILE_SIZE;
		// ty = ty % TILE_SIZE > TILE_SIZE / 2 ? ty / TILE_SIZE - 1 : ty /
		// TILE_SIZE;

		return this.finder.findPath(mover, sx, sy, tx, ty);
	}

	// Recherche à côté
	public Tile searchNear(MemoryType type, int x, int y) {

		Random r = new Random();

		// Pour les tests, pour le moment une chance sur 10 de trouver ce qu'on
		// cherche
		if (r.nextInt(10) == 0)
			return new Tile(x, y, 0, 0, 1);
		else
			return null;
	}

	// // Chemin trouvé
	// public boolean pathFound() {
	// return this.path != null;
	// }
	//
	// // Réinitialisation du chemin
	// public void clearPath() {
	// this.path = null;
	// }
}
