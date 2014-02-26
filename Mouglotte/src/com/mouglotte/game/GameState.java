package com.mouglotte.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mouglotte.genetics.Genetics;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.UnitMover;
import com.mouglotte.specy.Mouglotte;
import com.mouglotte.ui.BottomPanel;
import com.mouglotte.ui.RightPanel;

public class GameState extends BasicGameState {

	// Pour les tests
	public static final int TIME_FACTOR = 1;

	// ID de l'�tat
	private static final EnumGameState ID = EnumGameState.GAME;

	// private int sensX = 1, sensY = 1;
	// private int x, y;

	// Panneau du bas
	//private BottomPanel bottomPanel;
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

	@Override
	// R�cup�ration de l'ID du l'�tat
	public int getID() {
		return GameState.ID.getValue();
	}

	// R�cup�ration du conteneur
	public GameContainer getContainer() {
		return this.container;
	}

	// R�cup�ration de la carte
	public GameMap getMap() {
		return this.map;
	}

	@Override
	// Initialisation
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Conteneur
		this.container = container;

		// Initialisation de la g�n�tique
		Genetics.initialize();

		//this.bottomPanel = new BottomPanel(container, "bottomPanel");
		this.rightPanel = new RightPanel(this);

		// Cr�ation de la carte
		this.map = new GameMap(this);
		// this.map.init() pourrait �tre utile

		// Pour les tests
		this.mouglotte = new Mouglotte(this);
		this.mouglotte.setLocation(15, 15);
		// this.mouglotte2 = new Mouglotte(this);
		// this.mouglotte2.setLocation(100, 100);
	}

	@Override
	// Mise � jour
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		// deltaString = Integer.toString(minutes);
		this.rightPanel.update(container, delta);

		// Mise � jour de la carte
		this.map.update(container, delta);

		// Mise � jour de la mouglotte
		this.mouglotte.update(container, delta);

	}

	@Override
	// Affichage
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
		// Contient le calcul du d�calage du au scrolling, la m�thode doit �tre
		// la premi�re appel�e
		this.map.render(container, g);

		this.mouglotte.render(container, g);
		// this.mouglotte2.render(container, g);

		g.drawString(this.deltaString, 300, 10);
		g.drawString(this.infoString, 10, 300);
	}

	@Override
	// Bouton souris cliqu�
	public void mouseClicked(int button, int x, int y, int clickCount) {

		if (button == 0)
			mouseLeftClicked(x, y, clickCount);
		else if (button == 1)
			mouseRightClicked(x, y);
	}

	// Bouton souris cliqu� bouton gauche
	public void mouseLeftClicked(int x, int y, int clickCount) {

	}

	// Bouton souris cliqu� bouton droit
	public void mouseRightClicked(int x, int y) {

		// S'il y a une unit� ici
		// if (this.map.getUnit(x, y) != 0) {

		// Ici on va g�rer des actions : SOCIAL, LOVE, FIGHT

		// S'il n'y a pas d'unit� ici
		// } else {

		// Si une unit� est s�lectionn�e
		// if (this.mouglotte.isSelected()) {
		//
		// // R�initialisation des zones visit�es
		// this.map.clearVisited();
		//
		// // Recherche du chemin
		// this.mouglotte.setPath(this.map.findPath(new UnitMover(3),
		// this.mouglotte.getX(), this.mouglotte.getY(), x, y));
		// // path = finder.findPath(
		// // new UnitMover(map.getUnit(selectedx, selectedy)),
		// // selectedx, selectedy, x, y);
		//
		// // Si le chemin est trouv�
		// // if (this.mouglotte.hasPath()) {
		//
		// // D�placement de l'unit�
		// // int unit = map.getUnit(selectedx, selectedy);
		// // map.setUnit(selectedx, selectedy, 0);
		// // map.setUnit(x, y, unit);
		// // selectedx = x;
		// // selectedy = y;
		// // // R�initialisation du dernier chemin recherch�
		// // lastFindX = -1;
		//
		// // D�placement instantan�
		// // this.mouglotte.setLocation(x, y);
		//
		// // R�initialisation du chemin
		// // this.map.clearPath();
		// // }
		// }
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {

		// Si la souris est sortie on ne fait rien
		if ((newx < 0)
				|| (newy < 0)
				|| (newx >= this.map.getWidthInTiles() * this.map.getTileSize())
				|| (newy >= this.map.getHeightInTiles()
						* this.map.getTileSize())) {
			return;
		}

		// Si une unit� est s�lectionn�e
		if (this.mouglotte.isSelected()) {

			// // Recherche du chemin
			// this.map.findPath(new UnitMover(3), this.mouglotte.getX(),
			// this.mouglotte.getY(), newx, newy);
		}
		this.infoString = Integer.toString(newx) + "," + Integer.toString(newy)
				+ " => " + Integer.toString(newx / this.map.getTileSize())
				+ "," + Integer.toString(newy / this.map.getTileSize());
	}

	// R�cup�ration de la mouglotte s�lectionn�e
	public Mouglotte getSelectedMouglotte() {
		return this.mouglotte.isSelected() ? this.mouglotte : null;
	}
}
