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

//
//import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

import com.mouglotte.game.GameState;

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
	public static final int TILE_SIZE = 16;
	// Largeur et hauteur en zones
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;

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

	// Types de zones
	private Image[] tiles = new Image[4];
	// Buffer pour afficher la carte
	// private Image buffer;
	private ImageBuffer buffer;

	// Terrain
	private int[][] terrain = new int[WIDTH][HEIGHT];
	// Unités
	private int[][] units = new int[WIDTH][HEIGHT];
	// Visité
	private boolean[][] visited = new boolean[WIDTH][HEIGHT];

	// Pathfinder
	private PathFinder finder;
	//private Path path;

	// Constructeur
	public GameMap(GameState game) {

		// Jeu
		this.game = game;

		// Chargement des ressoures
		try {
			// tiles[GameMap.TREES] =
			// ImageIO.read(getResource("res/trees.png"));
			// tiles[GameMap.GRASS] =
			// ImageIO.read(getResource("res/grass.png"));
			// tiles[GameMap.WATER] =
			// ImageIO.read(getResource("res/water.png"));
			// tiles[GameMap.MOUGLOTTE] = ImageIO
			// .read(getResource("res/tank.png"));
			tiles[GameMap.TREES] = new Image(getResource("res/rocks.png"),
					"res/rocks.png", false);
			tiles[GameMap.GRASS] = new Image(getResource("res/grass.png"),
					"res/grass.png", false);
			// tiles[GameMap.WATER] = new
			// Image(getResource("res/water.png"),"res/water.png",false);
			// tiles[GameMap.MOUGLOTTE] = new
			// Image(getResource("res/boat.png"),"res/boat.png",false);
		} catch (IOException e) {
			System.err.println("Failed to load resources: " + e.getMessage());
			System.exit(0);
		} catch (SlickException e) {
			// e.printStackTrace();
		}

		// fillArea(0, 0, 5, 5, WATER);
		// fillArea(0, 5, 3, 10, WATER);
		// fillArea(0, 5, 3, 10, WATER);
		// fillArea(0, 15, 7, 15, WATER);
		// fillArea(7, 26, 22, 4, WATER);
//		 fillArea(17, 5, 10, 3, TREES);
//		 fillArea(20, 8, 5, 3, TREES);
//		 fillArea(8, 2, 7, 3, TREES);
//		 fillArea(10, 5, 3, 3, TREES);

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
		return HEIGHT;
	}

	// Récupération de la largeur en zones
	public int getWidthInTiles() {
		return WIDTH;
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

	// Affichage
	public void render(GUIContext c, Graphics g) throws SlickException {

		// // Création d'un buffer
		// if (buffer == null) {
		// //buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		// buffer = new ImageBuffer(600,600);
		// }
		// Graphics gBuff = this.buffer.getImage().getGraphics();
		Graphics gBuff = g;

		// Buffer déplacé de 50 (c'est pour ça les 50 partout)
		// gBuff.clearRect(0,0,600,600);
		//gBuff.clear();
		// gBuff.translate(50, 50);

		// Affichage de la carte
		for (int x = 0; x < this.getWidthInTiles(); x++) {
			for (int y = 0; y < this.getHeightInTiles(); y++) {
				gBuff.drawImage(this.tiles[this.getTerrain(x, y)], x
						* TILE_SIZE, y * TILE_SIZE, null);
				if (this.getUnit(x, y) != 0) {
					// gBuff.drawImage(tiles[this.getUnit(x,
					// y)],x*TILE_SIZE,y*TILE_SIZE,null);
				} else {
					// Matérialisation du chemin
//					if (this.path != null) {
//						if (this.path.contains(x, y)) {
//							gBuff.setColor(Color.blue);
//							gBuff.fillRect(x * TILE_SIZE + TILE_SIZE / 2,
//									y * TILE_SIZE + TILE_SIZE / 2, 7, 7);
//						}
//					}
				}
			}
		}

		// Si une unité est sélectionnée
		// if (selectedx != -1) {
		//
		// // Affichage d'une carré autour
		// gBuff.setColor(Color.black);
		// gBuff.drawRect(selectedx*TILE_SIZE, selectedy*TILE_SIZE, 15, 15);
		// gBuff.drawRect((selectedx*TILE_SIZE)-2, (selectedy*TILE_SIZE)-2, 19,
		// 19);
		// gBuff.setColor(Color.white);
		// gBuff.drawRect((selectedx*TILE_SIZE)-1, (selectedy*TILE_SIZE)-1, 17,
		// 17);
		// }

		// Affichage du buffer
		// g.drawImage(buffer.getImage(), 0, 0, null);
	}

	// Mise à jour
	public void update() {

		// Placement de la mouglotte (sur une zone)
		// units[this.game.getMouglotte().getX()][this.game.getMouglotte().getY()]
		// = MOUGLOTTE;
	}

	// Conversion

	// Trouver le chemin
	public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {

		// On convertit les x et y en position position dans les zones
		sx /= TILE_SIZE;
		sy /= TILE_SIZE;
		tx /= TILE_SIZE;
		ty /= TILE_SIZE;
//		sx = sx % TILE_SIZE > TILE_SIZE / 2 ? sx / TILE_SIZE - 1 : sx / TILE_SIZE;
//		sy = sy % TILE_SIZE > TILE_SIZE / 2 ? sy / TILE_SIZE - 1 : sy / TILE_SIZE;
//		tx = tx % TILE_SIZE > TILE_SIZE / 2 ? tx / TILE_SIZE - 1 : tx / TILE_SIZE;
//		ty = ty % TILE_SIZE > TILE_SIZE / 2 ? ty / TILE_SIZE - 1 : ty / TILE_SIZE;

		return this.finder.findPath(mover, sx, sy, tx, ty);
	}
	
//	// Chemin trouvé
//	public boolean pathFound() {
//		return this.path != null;
//	}
//
//	// Réinitialisation du chemin
//	public void clearPath() {
//		this.path = null;
//	}
}
