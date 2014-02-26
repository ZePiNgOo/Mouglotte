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
	// Nombre de zones (tiles) affichées
	public static final int DISPLAYED_TILES = 8;

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

	private float offsetX, offsetY;
	private int tileOffsetX, tileOffsetY;

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

	// /** The width of the display in tiles */
	// private int widthInTiles;
	// /** The height of the display in tiles */
	// private int heightInTiles;
	//
	// /** The offset from the centre of the screen to the top edge in tiles */
	// private int topOffsetInTiles;
	// /** The offset from the centre of the screen to the left edge in tiles */
	// private int leftOffsetInTiles;

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
		// this.widthInTiles = this.game.getContainer().getWidth()
		// / this.map.getTileWidth();
		// this.heightInTiles = this.game.getContainer().getHeight()
		// / this.map.getTileHeight();
		// this.topOffsetInTiles = this.heightInTiles / 2;
		// this.leftOffsetInTiles = this.widthInTiles / 2;

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

	// Récupération de la taille de la carte en pixels
	public int getWidth() {
		return this.map.getWidth() * this.map.getTileWidth();
	}
	
	// Récupération de la taille de la carte en pixels
	public int getheight() {
		return this.map.getHeight() * this.map.getTileHeight();
	}
	
	// Récupération de la hauteur en zones
	public int getHeightInTiles() {
		return this.map.getHeight();
	}

	// Récupération de la largeur en zones
	public int getWidthInTiles() {
		return this.map.getWidth();
	}
	
	// Ligne de la zone
	public int getTileRow(int y) {
		return (y - (int) this.offsetY) / this.map.getTileHeight()
				+ this.tileOffsetY;
	}

	// Colonne de la zone
	public int getTileColumn(int x) {
		return (x - (int) this.offsetX) / this.map.getTileWidth()
				+ this.tileOffsetX;
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

		// Calcul du scrolling de la carte
		scrolling(container, delta);
	}

	// Calcul du scrolling de la carte
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

	// Affichage
	public void render(GUIContext container, Graphics g) throws SlickException {

		// Affichage d'une partie de la carte
		// render(x,y,sx,sy,width,height)
		this.map.render((int) this.offsetX, (int) this.offsetY,
				this.tileOffsetX, this.tileOffsetY, DISPLAYED_TILES,
				DISPLAYED_TILES);
		// - x :
		// - y :
		// - sx : tile de départ
		// - sy : tile de départ
		// - width : nombre de tiles affichés
		// - height : nombre de tiles affichés

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

		// Décalage de tout ce qui va être affiché après
		// Cela permet que les choses restent à leur place malgré le scrolling
		g.translate(this.offsetX - this.tileOffsetX * this.map.getTileWidth(),
				this.offsetY - this.tileOffsetY * this.map.getTileHeight());

		// A appeler si on veut annuler le translate et donc ne plus prendre en
		// compte le scrolling
		// g.resetTransform();
	}

	// Trouver le chemin
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

	// Recherche à côté
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
