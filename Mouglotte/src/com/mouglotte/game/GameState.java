package com.mouglotte.game;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
	public static final int TIME_FACTOR = 3;

	// ID de l'état
	private static final EnumGameState ID = EnumGameState.GAME;

	// private int sensX = 1, sensY = 1;
	// private int x, y;

	// Panneau du bas
	private BottomPanel bottomPanel;
	// Panneau de droite
	private RightPanel rightPanel;
	// Carte
	private GameMap map;

	private int pastTime = 0;
	private int minutes = 0;
	private int hours = 0;
	private int days = 0;
	private Mouglotte mouglotte;

	private String deltaString = "";
	private String infoString = "";

	@Override
	// Récupération de l'ID du l'état
	public int getID() {
		return GameState.ID.getValue();
	}

	@Override
	// Initialisation
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Initialisation de la génétique
		Genetics.initialize();

		this.bottomPanel = new BottomPanel(container, "bottomPanel");
		this.rightPanel = new RightPanel(container, "rightPanel", 1);

		// Création de la carte
		this.map = new GameMap(this);
		// this.map.init() pourrait être utile

		// Pour les tests
		this.mouglotte = new Mouglotte(container);
		this.mouglotte.setLocation(15, 15);

	}

	@Override
	// Mise à jour
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		// Input input = container.getInput();
		//
		// if (input.isKeyDown(Keyboard.KEY_UP)) {
		//
		// }
		// if (input.isKeyDown(Keyboard.KEY_DOWN)) {
		//
		// }
		// if (input.isKeyDown(Keyboard.KEY_LEFT)) {
		//
		// }
		// if (input.isKeyDown(Keyboard.KEY_RIGHT)) {
		//
		// }

		this.pastTime += delta;
		this.pastTime *= TIME_FACTOR;

		// On fait un truc toutes les 3 secondes réelles
		// = minute mouglotte
		if (this.pastTime >= 3000) {

			this.minutes++;
			this.mouglotte.eventMinute();
			this.pastTime -= 3000;

			// Test
			// this.mouglotte.setLocation(this.mouglotte.getX()+10,this.mouglotte.getY()+10);
		}

		// On fait un truc toutes les 3 minutes réelles
		// = heure mouglotte
		if (this.minutes >= 60) {

			this.hours++;
			this.mouglotte.eventHour();
			this.minutes = 0;
		}

		// On fait un truc toutes les heures réelles
		// = jour mouglotte
		if (this.hours >= 20) {

			this.days++;
			this.mouglotte.eventDay();
			this.hours = 0;
		}

		deltaString = Integer.toString(minutes);

		// Mise à jour de la carte
		this.map.update();

	}
	
	@Override
	// Affichage
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		g.setAntiAlias(true);
		// g.setColor(Color.white);
		// g.fillRect(0, 0, container.getWidth(), container.getHeight());
		// g.setColor(Color.black);

//		this.bottomPanel.drawGUI(container, g);
//		this.rightPanel.drawGUI(container, g);

		switch (GameState.ID.getValue()) {
		// Démarrage
		case 1:

			// gestion d'un bouton "Jouer" pour réellement lancer l'action;
			// si clic dessus alors on passe etatJeu = Bataille !!
			// on ne veut pas de comportement souris sur la carte !!!

			break;
		// Jeu
		case 2:
			// gestion comportement souris
			// action tir sur une case
			// tour par tour
			// IA ordinateur
			// gestion tirs effectuée, tirs touché
			// mise à jour

			break;
		}

		this.map.render(container, g);
		this.mouglotte.render(container, g);

		g.drawString(this.deltaString, 10, 10);
		g.drawString(this.infoString, 10, 300);
	}

	@Override
	// Bouton souris cliqué
	public void mouseClicked(int button, int x, int y, int clickCount) {

		if (button == 0)
			mouseLeftClicked(x, y, clickCount);
		else if (button == 1)
			mouseRightClicked(x, y);
	}

	// Bouton souris cliqué bouton gauche
	public void mouseLeftClicked(int x, int y, int clickCount) {

	}

	// Bouton souris cliqué bouton droit
	public void mouseRightClicked(int x, int y) {

		// Si la souris est sortie on ne fait rien
		if ((x < 0) || (y < 0)
				|| (x >= this.map.getWidthInTiles() * GameMap.TILE_SIZE)
				|| (y >= this.map.getHeightInTiles() * GameMap.TILE_SIZE)) {
			return;
		}

		// S'il y a une unité ici
		// if (this.map.getUnit(x, y) != 0) {

		// Ici on va gérer des actions : SOCIAL, LOVE, FIGHT

		// S'il n'y a pas d'unité ici
		// } else {

		// Si une unité est sélectionnée
		if (this.mouglotte.isSelected()) {

			// Réinitialisation des zones visitées
			this.map.clearVisited();

			// Recherche du chemin
			this.mouglotte.setPath(this.map.findPath(new UnitMover(3),
					this.mouglotte.getX(), this.mouglotte.getY(), x, y));
			// path = finder.findPath(
			// new UnitMover(map.getUnit(selectedx, selectedy)),
			// selectedx, selectedy, x, y);

			// Si le chemin est trouvé
			// if (this.mouglotte.hasPath()) {

			// Déplacement de l'unité
			// int unit = map.getUnit(selectedx, selectedy);
			// map.setUnit(selectedx, selectedy, 0);
			// map.setUnit(x, y, unit);
			// selectedx = x;
			// selectedy = y;
			// // Réinitialisation du dernier chemin recherché
			// lastFindX = -1;

			// Déplacement instantané
			// this.mouglotte.setLocation(x, y);

			// Réinitialisation du chemin
			// this.map.clearPath();
			// }
		}
		// }
	}

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

			// Recherche du chemin
			this.map.findPath(new UnitMover(3), this.mouglotte.getX(),
					this.mouglotte.getY(), newx, newy);
		}
		this.infoString = Integer.toString(newx) + "," + Integer.toString(newy)
				+ " => " + Integer.toString(newx / GameMap.TILE_SIZE) + ","
				+ Integer.toString(newy / GameMap.TILE_SIZE);
	}

	// Pour les tests
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}
}
