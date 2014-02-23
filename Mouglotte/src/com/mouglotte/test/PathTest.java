package com.mouglotte.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mouglotte.map.AStarPathFinder;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Path;
import com.mouglotte.map.PathFinder;
import com.mouglotte.map.UnitMover;

/**
 * A simple test to show some path finding at unit
 * movement for a tutorial at http://www.cokeandcode.com
 * 
 * @author Kevin Glass
 */
public class PathTest extends JFrame {

	/** The map on which the units will move */
	private GameMap map = new GameMap();
	/** The path finder we'll use to search our map */
	private PathFinder finder;
	/** The last path found for the current unit */
	private Path path;
	
	/** The list of tile images to render the map */
	private Image[] tiles = new Image[4];
	/** The offscreen buffer used for rendering in the wonder world of Java 2D */
	private Image buffer;
	
	/** The x coordinate of selected unit or -1 if none is selected */
	private int selectedx = -1;
	/** The y coordinate of selected unit or -1 if none is selected */
	private int selectedy = -1;
	
	/** The x coordinate of the target of the last path we searched for - used to cache and prevent constantly re-searching */
	private int lastFindX = -1;
	/** The y coordinate of the target of the last path we searched for - used to cache and prevent constantly re-searching */
	private int lastFindY = -1;
	
	/**
	 * Create a new test game for the path finding tutorial
	 */
	public PathTest() {
		super("Path Finding Example");
	
		try {
			tiles[GameMap.TREES] = ImageIO.read(getResource("res/trees.png"));
			tiles[GameMap.GRASS] = ImageIO.read(getResource("res/grass.png"));
			tiles[GameMap.WATER] = ImageIO.read(getResource("res/water.png"));
			tiles[GameMap.MOUGLOTTE] = ImageIO.read(getResource("res/tank.png"));
		} catch (IOException e) {
			System.err.println("Failed to load resources: "+e.getMessage());
			System.exit(0);
		}
		
		finder = new AStarPathFinder(map, 500, true);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				if (SwingUtilities.isLeftMouseButton(e)) handleLeftClick(e);
				else if (SwingUtilities.isRightMouseButton(e)) handleRightClick(e);
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				handleMouseMoved(e.getX(), e.getY());
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setSize(600,600);
		setResizable(false);
		setVisible(true);
	}
	
	// Chargement des ressources
	private InputStream getResource(String ref) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ref);
		if (in != null) {
			return in;
		}
		
		return new FileInputStream(ref);
	}

	// Déplacement de la souris
	private void handleMouseMoved(int x, int y) {
		
		
		x -= 50;
		y -= 50;
		x /= 16;
		y /= 16;
		
		// Si la souris est sortie on ne fait rien
		if ((x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles())) {
			return;
		}
		
		// Si une unité est sélectionnée
		if (selectedx != -1) {
			
			// Si la position cible a changé
			if ((lastFindX != x) || (lastFindY != y)) {
				
				// Sauvegarde de la position cible
				lastFindX = x;
				lastFindY = y;
				
				// Recherche du chemin
				path = finder.findPath(new UnitMover(map.getUnit(selectedx, selectedy)), 
									   selectedx, selectedy, x, y);
				repaint(0);
			}
		}
	}
	
	// Clic du bouton gauche de la souris
	private void handleLeftClick(MouseEvent e) {
			
		int x = e.getX();
		int y = e.getY();
		x -= 50;
		y -= 50;
		x /= 16;
		y /= 16;
		
		// Si la souris est sortie on ne fait rien
		if ((x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles())) {
			return;
		}
		
		// S'il y a une unité à cet endroit
		if (map.getUnit(x, y) != 0) {
			
			// On la sélectionne
			selectedx = x;
			selectedy = y;
			// Réinitialisation du dernier chemin cherché
			lastFindX = - 1;
		
			// S'il n'y a pas d'unité
		} else {
			
			// On déselectionne
			selectedx = -1;
			// Réinitialisation du dernier chemin cherché
			lastFindX = - 1;
			path = null;
		}
		
		repaint(0);
	}
	
	// Clic du bouton droit de la souris
	private void handleRightClick(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		x -= 50;
		y -= 50;
		x /= 16;
		y /= 16;
		
		// Si la souris est sortie on ne fait rien
		if ((x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles())) {
			return;
		}
		
		// S'il y a une unité ici
		if (map.getUnit(x, y) != 0) {
			
			// Ici on va gérer des actions : SOCIAL, LOVE, FIGHT

		// S'il n'y a pas d'unité ici
		} else {
			
			// Si une unité est sélectionnée
			if (selectedx != -1) {
				
				// Réinitialisation des zones visitées
				map.clearVisited();
				// Recherche du chemin
				path = finder.findPath(new UnitMover(map.getUnit(selectedx, selectedy)), 
						   			   selectedx, selectedy, x, y);
				
				// Si le chemin est trouvé
				if (path != null) {
					
					// Réinitialisation du chemin
					path = null;
					
					// Déplacement de l'unité
					int unit = map.getUnit(selectedx, selectedy);
					map.setUnit(selectedx, selectedy, 0);
					map.setUnit(x,y,unit);
					selectedx = x;
					selectedy = y;
					// Réinitialisation du dernier chemin recherché
					lastFindX = - 1;
				}
			}
		}
		
		repaint(0);
	}
	
	// Affichage
	public void paint(Graphics graphics) {	
		
		// Création d'un buffer
		if (buffer == null) {
			buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);			
		}
		Graphics g = buffer.getGraphics();
		
		// Buffer déplacé de 50 (c'est pour ça les 50 partout)
		g.clearRect(0,0,600,600);
		g.translate(50, 50);
		
		// Affichage de la carte
		for (int x=0;x<map.getWidthInTiles();x++) {
			for (int y=0;y<map.getHeightInTiles();y++) {
				g.drawImage(tiles[map.getTerrain(x, y)],x*16,y*16,null);
				if (map.getUnit(x, y) != 0) {
					g.drawImage(tiles[map.getUnit(x, y)],x*16,y*16,null);
				} else {
					if (path != null) {
						if (path.contains(x, y)) {
							g.setColor(Color.blue);
							g.fillRect((x*16)+4, (y*16)+4,7,7);
						}
					}	
				}
			}
		}

		// Si une unité est sélectionnée
		if (selectedx != -1) {

			// Affichage d'une carré autour
			g.setColor(Color.black);
			g.drawRect(selectedx*16, selectedy*16, 15, 15);
			g.drawRect((selectedx*16)-2, (selectedy*16)-2, 19, 19);
			g.setColor(Color.white);
			g.drawRect((selectedx*16)-1, (selectedy*16)-1, 17, 17);
		}

		// Affichage du buffer
		graphics.drawImage(buffer, 0, 0, null);
	}
	
	// Main
	public static void main(String[] argv) {
		PathTest test = new PathTest();		
	}
}
